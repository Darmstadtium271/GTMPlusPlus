package dev.darmstadtium271.gtmplusplus.client.renderer.machine.multiblock.steam;

import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.client.renderer.block.FluidBlockRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.gregtechceu.gtceu.client.util.RenderUtil;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.darmstadtium271.gtmplusplus.common.machine.multiblock.LargeSteamOreWasherMachine;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.RenderTypeHelper;

// Modified from LargeChemicalBathRenderer
public class LargeSteamOreWasherRenderer extends WorkableCasingMachineRenderer {

    private final FluidBlockRenderer fluidBlockRenderer;
    private Fluid cachedFluid;
    private ResourceLocation cachedRecipe;

    public LargeSteamOreWasherRenderer(ResourceLocation baseCasing, ResourceLocation workableModel) {
        super(baseCasing, workableModel);

        fluidBlockRenderer = FluidBlockRenderer.Builder.create()
                .setFaceOffset(-0.125f)
                .setForcedLight(LightTexture.FULL_BRIGHT)
                .getRenderer();
    }

    @Override
    public int getViewDistance() {
        return 32;
    }

    @Override
    public boolean isGlobalRenderer(BlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean hasTESR(BlockEntity blockEntity) {
        return true;
    }

    @Override
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource buffer,
                       int combinedLight, int combinedOverlay) {
        super.render(blockEntity, partialTicks, stack, buffer, combinedLight, combinedOverlay);

        if (!ConfigHolder.INSTANCE.client.renderer.renderFluids) return;
        if (blockEntity instanceof MetaMachineBlockEntity mm) {
            if (mm.metaMachine instanceof LargeSteamOreWasherMachine machine) {
                var lastRecipe = machine.recipeLogic.getLastRecipe();
                if (lastRecipe == null) {
                    cachedRecipe = null;
                    cachedFluid = null;
                } else if (machine.getOffsetTimer() % 20 == 0 || lastRecipe.id != cachedRecipe) {
                    cachedRecipe = lastRecipe.id;
                    if (machine.isActive()) {
                        cachedFluid = RenderUtil.getRecipeFluidToRender(lastRecipe);
                    } else {
                        cachedFluid = null;
                    }
                }

                if (cachedFluid == null) {
                    return;
                }

                stack.pushPose();
                var pose = stack.last().pose();

                var fluidRenderType = ItemBlockRenderTypes.getRenderLayer(cachedFluid.defaultFluidState());
                var consumer = buffer.getBuffer(RenderTypeHelper.getEntityRenderType(fluidRenderType, false));

                var up = RelativeDirection.UP.getRelative(machine.getFrontFacing(), machine.getUpwardsFacing(),
                        machine.isFlipped());
                if (up.getAxis() != Direction.Axis.Y) up = up.getOpposite();
                fluidBlockRenderer.drawPlane(up, machine.getFluidBlockOffsets(), pose, consumer, cachedFluid,
                        RenderUtil.FluidTextureType.STILL, combinedOverlay, machine.getPos());

                stack.popPose();
            }
        }
    }
}
