package dev.darmstadtium271.gtmplusplus.common.machine.power;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigInteger;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EnergyMeterMachine extends TieredIOPartMachine {

    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(EnergyMeterMachine.class,
            TieredIOPartMachine.MANAGED_FIELD_HOLDER);
    private final ConditionalSubscriptionHandler subscriptionHandler;
    @Getter
    private final NotifiableEnergyContainer energyContainer;

    private static final long MAX_EU = 10_000_000;
    private static final int DECIMAL = 10;
    @Persisted(key = "total_energy")
    @DescSynced
    @Getter
    private String totalEnergy;
    @Persisted(key = "current_power")
    @DescSynced
    @Getter
    private long current_power;

    private static final long[] modes = { 1, 1_000, 1_000_000, 1_000_000_000 };
    private static final String[] modeNames = { "EU", "kEU", "MEU", "GEU" };
    @Persisted(key = "mode")
    @DescSynced
    @Getter
    private int mode;

    public EnergyMeterMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier, IO.BOTH);
        this.workingEnabled = true;
        this.mode = 0;
        this.energyContainer = new NotifiableEnergyContainer(this, GTValues.V[getTier()] * 16L, GTValues.V[getTier()],
                GTValues.V[getTier()], GTValues.V[getTier()], GTValues.V[getTier()]);
        reinitializeEnergyContainer();
        clear();
        this.subscriptionHandler = new ConditionalSubscriptionHandler(this, this::update, () -> true);
    }

    protected void reinitializeEnergyContainer() {
        this.energyContainer.resetBasicInfo(GTValues.V[getTier()] * 16L, GTValues.V[getTier()], GTValues.V[getTier()],
                GTValues.V[getTier()], GTValues.V[getTier()]);
        this.energyContainer.setSideInputCondition(s -> s == this.getFrontFacing().getOpposite());
        if (this.isWorkingEnabled()) {
            this.energyContainer
                    .setSideOutputCondition(s -> s != getFrontFacing().getOpposite() && s != getFrontFacing());
        } else {
            this.energyContainer.setSideOutputCondition(s -> false);
        }
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        subscriptionHandler.initialize(getLevel());
    }

    // Display operations
    private String totalEnergyToDisplay() {
        return String.valueOf((double) (DECIMAL > modes[mode] ?
                new BigInteger(totalEnergy).multiply(BigInteger.valueOf(DECIMAL / modes[mode])) :
                new BigInteger(totalEnergy).divide(BigInteger.valueOf(modes[mode] / DECIMAL)))
                .mod(BigInteger.valueOf(MAX_EU * DECIMAL)).longValue() / DECIMAL);
    }

    private String averagePowerToDisplay() {
        return String.valueOf((double) ((DECIMAL > modes[mode] ? current_power * (DECIMAL / modes[mode]) :
                current_power / (modes[mode] / DECIMAL)) % (MAX_EU * DECIMAL)) / DECIMAL);
    }

    // Data
    private void clear() {
        this.totalEnergy = "0";
        this.current_power = 0;
    }

    private void update() {
        if (getOffsetTimer() % 20 != 0) return;
        long val = this.energyContainer.getOutputPerSec();
        this.totalEnergy = new BigInteger(this.totalEnergy).add(BigInteger.valueOf(val)).toString();
        this.current_power = this.energyContainer.getOutputPerSec();
        subscriptionHandler.updateSubscription();
    }

    // GUI
    private static final int TOTAL_WIDTH = 160;
    private static final int TOTAL_HEIGHT = 70;
    private static final int GAP = 4;
    private static final int TEXT_HEIGHT = 10;
    private static final int CLEAR_BUTTON_WIDTH_HEIGHT = 16;

    @Override
    public Widget createUIWidget() {
        var group = new WidgetGroup(0, 0, TOTAL_WIDTH, TOTAL_HEIGHT);
        var total_energy_group = new WidgetGroup(GAP, GAP, TOTAL_WIDTH - 2 * GAP, TOTAL_HEIGHT - 2 * GAP);
        total_energy_group.addWidget(new LabelWidget(GAP, GAP, "gtmplusplus.gui.energy_meter.total_energy"))
                .addWidget(new WidgetGroup(GAP, GAP + TEXT_HEIGHT, TOTAL_WIDTH - 2 * GAP - 2 * GAP,
                        CLEAR_BUTTON_WIDTH_HEIGHT)
                        .addWidget(new LabelWidget(0, GAP, this::totalEnergyToDisplay))
                        .addWidget(
                                new TextTextureWidget(TOTAL_WIDTH - 2 * GAP - 2 * GAP - CLEAR_BUTTON_WIDTH_HEIGHT - 20,
                                        1, 20, CLEAR_BUTTON_WIDTH_HEIGHT, modeNames[mode]))
                        .addWidget(new ButtonWidget(TOTAL_WIDTH - 2 * GAP - 2 * GAP - CLEAR_BUTTON_WIDTH_HEIGHT, 0,
                                CLEAR_BUTTON_WIDTH_HEIGHT, CLEAR_BUTTON_WIDTH_HEIGHT, GuiTextures.BUTTON,
                                (data) -> clear())
                                .appendHoverTooltips("gtmplusplus.gui.energy_meter.clear")))
                .addWidget(new LabelWidget(GAP, GAP + TEXT_HEIGHT + CLEAR_BUTTON_WIDTH_HEIGHT,
                        "gtmplusplus.gui.energy_meter.current_power"))
                .addWidget(new WidgetGroup(GAP, GAP + 2 * TEXT_HEIGHT + CLEAR_BUTTON_WIDTH_HEIGHT,
                        TOTAL_WIDTH - 2 * GAP - 2 * GAP, CLEAR_BUTTON_WIDTH_HEIGHT)
                        .addWidget(new LabelWidget(0, GAP, this::averagePowerToDisplay))
                        .addWidget(new TextTextureWidget(
                                TOTAL_WIDTH - 2 * GAP - 2 * GAP - 20 - CLEAR_BUTTON_WIDTH_HEIGHT, 1,
                                20 + CLEAR_BUTTON_WIDTH_HEIGHT, CLEAR_BUTTON_WIDTH_HEIGHT, modeNames[mode] + "/s")))
                .setBackground(GuiTextures.DISPLAY);
        group.addWidget(total_energy_group).setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    // Interaction

    private void cycleMode(boolean isRollBack) {
        mode = (mode + (isRollBack ? modes.length - 1 : 1)) % modes.length;
        if (!Objects.requireNonNull(getLevel()).isClientSide) {
            notifyBlockUpdate();
            markDirty();
        }
    }

    @Override
    protected InteractionResult onSoftMalletClick(Player playerIn, InteractionHand hand, Direction gridSide,
                                                  BlockHitResult hitResult) {
        InteractionResult superReturn = super.onSoftMalletClick(playerIn, hand, gridSide, hitResult);
        reinitializeEnergyContainer();
        return superReturn;
    }

    @Override
    protected InteractionResult onWrenchClick(Player playerIn, InteractionHand hand, Direction gridSide,
                                              BlockHitResult hitResult) {
        InteractionResult superReturn = super.onWrenchClick(playerIn, hand, gridSide, hitResult);
        reinitializeEnergyContainer();
        return superReturn;
    }

    @Override
    protected InteractionResult onScrewdriverClick(Player playerIn, InteractionHand hand, Direction gridSide,
                                                   BlockHitResult hitResult) {
        InteractionResult superReturn = super.onScrewdriverClick(playerIn, hand, gridSide, hitResult);
        cycleMode(playerIn.isShiftKeyDown());
        playerIn.sendSystemMessage(
                Component.translatable("gtmplusplus.machine.energy_meter.display_unit", modeNames[mode]));
        return superReturn;
    }
}
