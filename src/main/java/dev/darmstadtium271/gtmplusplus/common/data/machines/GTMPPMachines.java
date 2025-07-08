package dev.darmstadtium271.gtmplusplus.common.data.machines;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.client.renderer.machine.QuantumTankRenderer;
import com.gregtechceu.gtceu.common.machine.storage.QuantumTankMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import dev.darmstadtium271.gtmplusplus.client.renderer.machine.power.EnergyMeterRenderer;
import dev.darmstadtium271.gtmplusplus.common.machine.power.EnergyMeterMachine;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fluids.FluidType;

import static com.gregtechceu.gtceu.api.GTValues.ALL_TIERS;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.TANK_TOOLTIPS;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.registerTieredMachines;
import static dev.darmstadtium271.gtmplusplus.GTMPlusPlus.GTMPPRegistrate.REGISTRATE;

public class GTMPPMachines {

    public static final MachineDefinition ULV_SUPER_TANK = REGISTRATE
            .machine("ulv_super_tank",
                    (holder) -> new QuantumTankMachine(holder, GTValues.ULV, 32 * FluidType.BUCKET_VOLUME))
            .tier(GTValues.ULV)
            .blockProp(BlockBehaviour.Properties::dynamicShape)
            .rotationState(RotationState.ALL)
            .renderer(() -> new QuantumTankRenderer(GTValues.ULV))
            .hasTESR(true)
            .tooltipBuilder(TANK_TOOLTIPS)
            .tooltips(Component.translatable("gtceu.machine.quantum_tank.tooltip"),
                    Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity",
                            FormattingUtil.formatNumbers(8 * FluidType.BUCKET_VOLUME)))
            .register();

    public static final MachineDefinition[] ENERGY_METER = registerTieredMachines("energy_meter",
            EnergyMeterMachine::new,
            (tier, builder) -> builder.rotationState(RotationState.NON_Y_AXIS)
                    .abilities(PartAbility.PASSTHROUGH_HATCH)
                    .renderer(() -> new EnergyMeterRenderer(tier))
                    .register(),
            ALL_TIERS);

    public static void init() {
        GTMPPMultiblocks.init();
    }
}
