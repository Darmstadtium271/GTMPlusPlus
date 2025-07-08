package dev.darmstadtium271.gtmplusplus.client.renderer.machine.power;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableTieredHullMachineRenderer;
import dev.darmstadtium271.gtmplusplus.GTMPlusPlus;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static com.gregtechceu.gtceu.client.renderer.machine.OverlayEnergyIORenderer.ENERGY_IN_1A;
import static com.gregtechceu.gtceu.client.renderer.machine.OverlayEnergyIORenderer.ENERGY_OUT_1A;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EnergyMeterRenderer extends WorkableTieredHullMachineRenderer {

    public EnergyMeterRenderer(int tier) {
        super(tier, GTMPlusPlus.id("block/machines/energy_meter"));
    }

    @Override
    public void renderMachine(List<BakedQuad> quads, MachineDefinition definition, @Nullable MetaMachine machine,
                              Direction frontFacing, @Nullable Direction side, RandomSource rand,
                              @Nullable Direction modelFacing, ModelState modelState) {
        super.renderMachine(quads, definition, machine, frontFacing, side, rand, modelFacing, modelState);
        if (side == frontFacing.getClockWise())
            ENERGY_IN_1A.renderOverlay(quads, modelFacing, modelState, 2);
        else if (side == frontFacing.getCounterClockWise())
            ENERGY_OUT_1A.renderOverlay(quads, modelFacing, modelState, 2);
    }
}
