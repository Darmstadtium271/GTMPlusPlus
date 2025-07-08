package dev.darmstadtium271.gtmplusplus.common.machine.power;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.texture.ResourceBorderTexture;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
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
    @Persisted(key = "is_active")
    @DescSynced
    @Getter
    private boolean isActive;

    private static final long[] modes = { 1, 1_000, 1_000_000, 1_000_000_000 };
    private static final String[] modeNames = { "EU", "kEU", "MEU", "GEU" };
    @Persisted(key = "mode")
    @DescSynced
    @Getter
    private int mode;

    public EnergyMeterMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier, IO.BOTH);
        this.isActive = true;
        this.mode = 0;
        this.energyContainer = new NotifiableEnergyContainer(this, GTValues.V[getTier()] * 16L, GTValues.V[getTier()],
                GTValues.V[getTier()], GTValues.V[getTier()], GTValues.V[getTier()]);
        reinitializeEnergyContainer();
        resetTotalEnergy();
        this.subscriptionHandler = new ConditionalSubscriptionHandler(this, this::update, this::isSubscriptionActive);
    }

    protected void reinitializeEnergyContainer() {
        this.energyContainer.resetBasicInfo(GTValues.V[getTier()] * 16L, GTValues.V[getTier()], GTValues.V[getTier()],
                GTValues.V[getTier()], GTValues.V[getTier()]);
        this.energyContainer.setSideInputCondition(s -> s == this.getFrontFacing().getClockWise());
        this.energyContainer.setSideOutputCondition(s -> s == this.getFrontFacing().getCounterClockWise());
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    private Boolean isSubscriptionActive() {
        return this.isActive && energyContainer.getOutputAmperage() != 0;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        subscriptionHandler.initialize(getLevel());
    }

    // Integer operations
    private String toDisplay() {
        return String.valueOf((double) (DECIMAL > modes[mode] ?
                new BigInteger(totalEnergy).multiply(BigInteger.valueOf(DECIMAL / modes[mode])) :
                new BigInteger(totalEnergy).divide(BigInteger.valueOf(modes[mode] / DECIMAL)))
                .mod(BigInteger.valueOf(MAX_EU * DECIMAL)).longValue() / DECIMAL);
    }

    // Data
    private void resetTotalEnergy() {
        this.totalEnergy = "0";
    }

    private void update() {
        if (getOffsetTimer() % 20 != 0) return;
        long val = this.energyContainer.getOutputPerSec();
        this.totalEnergy = new BigInteger(totalEnergy).add(BigInteger.valueOf(val)).toString();
        subscriptionHandler.updateSubscription();
    }

    // GUI
    @Override
    public Widget createUIWidget() {
        // var group = new WidgetGroup(0, 0, 89, 63);
        var group = new WidgetGroup(0, 0, 100, 70);
        group.addWidget(new ImageWidget(4, 4, 92, 62, GuiTextures.DISPLAY));
        group.addWidget(new LabelWidget(8, 8, "gtmplusplus.gui.energy_meter.total_energy"))
                .addWidget(new LabelWidget(8, 18, () -> toDisplay() + modeNames[mode]))
                .addWidget(
                        new ButtonWidget(62, 28, 35, 18,
                                new GuiTextureGroup(ResourceBorderTexture.BUTTON_COMMON,
                                        new TextTexture("gtmplusplus.gui.energy_meter.reset")),
                                (data) -> resetTotalEnergy()));
        // .addWidget(new LabelWidget(8, 28, () -> getFluidNameText(tankWidget).getString()));
        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    // Interaction
    private void cycleMode() {
        mode = (mode + 1) % modes.length;
        if (!Objects.requireNonNull(getLevel()).isClientSide) {
            notifyBlockUpdate();
            markDirty();
        }
    }

    @Override
    protected InteractionResult onSoftMalletClick(Player playerIn, InteractionHand hand, Direction gridSide,
                                                  BlockHitResult hitResult) {
        // isActive = !isActive;
        playerIn.sendSystemMessage(Component.literal(toDisplay() + modeNames[mode]));
        return InteractionResult.CONSUME;
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
        cycleMode();
        playerIn.sendSystemMessage(Component.literal(modeNames[mode]));
        return InteractionResult.CONSUME;
    }
}
