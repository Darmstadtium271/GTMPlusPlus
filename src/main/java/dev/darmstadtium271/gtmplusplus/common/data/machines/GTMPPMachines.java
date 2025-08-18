package dev.darmstadtium271.gtmplusplus.common.data.machines;

import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.client.model.machine.overlays.EnergyIOOverlay;
import com.gregtechceu.gtceu.client.model.machine.overlays.WorkableOverlays;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import dev.darmstadtium271.gtmplusplus.GTMPlusPlus;
import dev.darmstadtium271.gtmplusplus.common.machine.power.EnergyMeterMachine;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.client.model.machine.overlays.EnergyIOOverlay.IN_OVERLAYS_FOR_AMP;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.OVERLAY_PREFIX;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.addWorkableOverlays;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;
import static dev.darmstadtium271.gtmplusplus.common.data.machines.GTMPPMachineUtils.*;

public class GTMPPMachines {

    // public static final MachineDefinition ULV_SUPER_TANK = REGISTRATE
    // .machine("ulv_super_tank",
    // (holder) -> new QuantumTankMachine(holder, GTValues.ULV, 32 * FluidType.BUCKET_VOLUME))
    // .tier(GTValues.ULV)
    // .blockProp(BlockBehaviour.Properties::dynamicShape)
    // .rotationState(RotationState.ALL)
    // .renderer(() -> new QuantumTankRenderer(GTValues.ULV))
    // .hasTESR(true)
    // .tooltipBuilder(TANK_TOOLTIPS)
    // .tooltips(Component.translatable("gtceu.machine.quantum_tank.tooltip"),
    // Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity",
    // FormattingUtil.formatNumbers(8 * FluidType.BUCKET_VOLUME)))
    // .register();

    public static final MachineDefinition[] ENERGY_METER = registerTieredMachines("energy_meter",
            EnergyMeterMachine::new,
            (tier, machineBuilder) -> machineBuilder.rotationState(RotationState.NON_Y_AXIS)
                    .abilities(PartAbility.PASSTHROUGH_HATCH)
                    .model((ctx, prov, modelBuilder) -> {
                        modelBuilder.forAllStates(renderState -> {
                            final EnergyIOOverlay energyIn = IN_OVERLAYS_FOR_AMP.get(1);
                            return addWorkableOverlays(
                                    WorkableOverlays.get(GTMPlusPlus.id("block/machines/energy_meter"),
                                            prov.getExistingFileHelper()),
                                    RecipeLogic.Status.IDLE,
                                    prov.models().nested()
                                            .parent(GTMachineModels.tieredHullModel(prov.models(), modelBuilder))
                                            .texture(OVERLAY_PREFIX + WorkableOverlays.OverlayFace.BACK.getName(),
                                                    energyIn.getIoPart())
                                            .texture(OVERLAY_PREFIX + WorkableOverlays.OverlayFace.BACK.getName() +
                                                    "_emissive", energyIn.getTintedPart()));
                        });
                        modelBuilder.addReplaceableTextures("bottom", "top", "side");
                    })
                    .langValue("%s %s %s".formatted(VLVH[tier], toEnglishName("energy_meter"), VLVT[tier]))
                    .register(),
            ALL_TIERS);

    public static void init() {
        GTMPPMultiblocks.init();
    }
}
