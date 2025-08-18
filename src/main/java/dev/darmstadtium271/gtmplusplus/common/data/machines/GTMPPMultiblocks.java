package dev.darmstadtium271.gtmplusplus.common.data.machines;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderHelper;
import com.gregtechceu.gtceu.common.block.BoilerFireboxType;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.machines.GTMultiMachines;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;
import com.gregtechceu.gtceu.config.ConfigHolder;
import dev.darmstadtium271.gtmplusplus.GTMPlusPlus;
import dev.darmstadtium271.gtmplusplus.common.machine.multiblock.BasicSteamParallelMultiblockMachine;
import dev.darmstadtium271.gtmplusplus.common.machine.multiblock.LargeSteamOreWasherMachine;
import dev.darmstadtium271.gtmplusplus.common.machine.recipemodifier.HighPressureSteamParallelRecipeModifier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Objects;

import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.createWorkableCasingMachineModel;
import static dev.darmstadtium271.gtmplusplus.common.registry.GTMPPRegistration.REGISTRATE;

public class GTMPPMultiblocks {

    public static void init() {
        SteamMultiblocks.init();
    }

    // Steam Multiblocks
    // Inspired By GT5U&GTNH
    public static class SteamMultiblocks {

        public static final MultiblockMachineDefinition HP_STEAM_GRINDER = REGISTRATE
                .multiblock("hp_steam_grinder", BasicSteamParallelMultiblockMachine::highPressure)
                .langValue("High Pressure Steam Grinder")
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_STEEL_SOLID)
                .recipeType(GTRecipeTypes.MACERATOR_RECIPES)
                .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
                .addOutputLimit(ItemRecipeCapability.CAP, 1)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("XXX", "XXX", "XXX")
                        .aisle("XXX", "X#X", "XXX")
                        .aisle("XXX", "XSX", "XXX")
                        .where('S', Predicates.controller(Predicates.blocks(definition.getBlock())))
                        .where('#', Predicates.air())
                        .where('X',
                                Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(14)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                        GTMPlusPlus.id("block/multiblock/hp_steam_grinder"))
                .tooltips(Component.translatable("gtceu.machine.steam_grinder.tooltip"),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MultiblockMachineDefinition HP_STEAM_OVEN = REGISTRATE
                .multiblock("hp_steam_oven", BasicSteamParallelMultiblockMachine::highPressure)
                .langValue("High Pressure Steam Oven")
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_STEEL_SOLID)
                .recipeType(GTRecipeTypes.FURNACE_RECIPES)
                .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
                .addOutputLimit(ItemRecipeCapability.CAP, 1)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("FFF", "XXX", " X ")
                        .aisle("FFF", "X#X", " X ")
                        .aisle("FFF", "XSX", " X ")
                        .where('S', Predicates.controller(blocks(definition.getBlock())))
                        .where('#', Predicates.air())
                        .where(' ', Predicates.any())
                        .where('X', blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(6)
                                .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)))
                        .where('F', blocks(FIREBOX_STEEL.get())
                                .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .modelProperty(RecipeLogic.STATUS_PROPERTY, RecipeLogic.Status.IDLE)
                .model(createWorkableCasingMachineModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                        GTMPlusPlus.id("block/multiblock/hp_steam_oven"))
                        .andThen(b -> b.addDynamicRenderer(
                                () -> DynamicRenderHelper.makeBoilerPartRender(
                                        BoilerFireboxType.STEEL_FIREBOX, CASING_STEEL_SOLID))))
                .tooltips(Component.translatable("gtceu.machine.steam_oven.tooltip"),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_STEAM_COMPRESSOR = REGISTRATE
                .multiblock("large_steam_compressor", BasicSteamParallelMultiblockMachine::lowPressure)
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_BRONZE_BRICKS)
                .recipeType(GTRecipeTypes.COMPRESSOR_RECIPES)
                .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle("CCC", "CCC", "CCC")
                        .aisle("CCC", "C#C", "CCC")
                        .aisle("CCC", "C#C", "CCC")
                        .aisle("CCC", "CSC", "CCC")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("C",
                                Predicates.blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(28)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .tooltips(
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                        GTCEu.id("block/machines/compressor"))
                .register();
        public static final MachineDefinition LARGE_HP_STEAM_COMPRESSOR = REGISTRATE
                .multiblock("large_hp_steam_compressor", BasicSteamParallelMultiblockMachine::highPressure)
                .langValue("Large High Pressure Steam Compressor")
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_STEEL_SOLID)
                .recipeType(GTRecipeTypes.COMPRESSOR_RECIPES)
                .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle("CCC", "CCC", "CCC")
                        .aisle("CCC", "C#C", "CCC")
                        .aisle("CCC", "C#C", "CCC")
                        .aisle("CCC", "CSC", "CCC")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("C",
                                Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get()).setMinGlobalLimited(28)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                        GTCEu.id("block/machines/compressor"))
                .tooltips(Component.translatable("gtmplusplus.machine.large_steam_compressor.tooltip"),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_STEAM_FORGE_HAMMER = REGISTRATE
                .multiblock("large_steam_forge_hammer", BasicSteamParallelMultiblockMachine::lowPressure)
                .rotationState(RotationState.NON_Y_AXIS)
                .appearanceBlock(CASING_BRONZE_BRICKS)
                .recipeType(GTRecipeTypes.FORGE_HAMMER_RECIPES)
                .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle("CCC", "CCC", "CCC", "CCC", "CCC")
                        .aisle("CCC", "C#C", "CIC", "CTC", "CPC")
                        .aisle("CCC", "CSC", "CCC", "CCC", "CCC")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("C", Predicates.blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(30)
                                .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .where("P", Predicates.blocks(CASING_BRONZE_GEARBOX.get()))
                        .where("T", Predicates.blocks(CASING_BRONZE_PIPE.get()))
                        .where("I", Predicates.blocks(Blocks.IRON_BLOCK))
                        .build())
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                        GTCEu.id("block/machines/forge_hammer"))
                .tooltips(
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_HP_STEAM_FORGE_HAMMER = REGISTRATE
                .multiblock("large_hp_steam_forge_hammer", BasicSteamParallelMultiblockMachine::highPressure)
                .langValue("Large High Pressure Steam Forge Hammer")
                .rotationState(RotationState.NON_Y_AXIS)
                .appearanceBlock(CASING_STEEL_SOLID)
                .recipeType(GTRecipeTypes.FORGE_HAMMER_RECIPES)
                .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle("CCC", "CCC", "CCC", "CCC", "CCC")
                        .aisle("CCC", "C#C", "CIC", "CTC", "CPC")
                        .aisle("CCC", "CSC", "CCC", "CCC", "CCC")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("C", Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(30)
                                .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .where("P", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                        .where("T", Predicates.blocks(CASING_STEEL_PIPE.get()))
                        .where("I",
                                Predicates.blocks(Objects
                                        .requireNonNull(
                                                GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.block,
                                                        GTMaterials.Steel))
                                        .get()))
                        .build())
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                        GTCEu.id("block/machines/forge_hammer"))
                .tooltips(Component.translatable("gtmplusplus.machine.large_steam_forge_hammer.tooltip"),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_STEAM_ALLOY_SMELTER = REGISTRATE
                .multiblock("large_steam_alloy_smelter", BasicSteamParallelMultiblockMachine::lowPressure)
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_BRONZE_BRICKS)
                .recipeType(GTRecipeTypes.ALLOY_SMELTER_RECIPES)
                .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle("FFF", "CCC", "CCC")
                        .aisle("FFF", "C#C", "CCC")
                        .aisle("FFF", "C#C", "CCC")
                        .aisle("FFF", "CSC", "CCC")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("F", Predicates.blocks(FIREBOX_BRONZE.get()).setExactLimit(11)
                                .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .where("C",
                                Predicates.blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(11)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)))
                        .build())
                .tooltips(
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .modelProperty(RecipeLogic.STATUS_PROPERTY, RecipeLogic.Status.IDLE)
                .model(createWorkableCasingMachineModel(
                        GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                        GTCEu.id("block/machines/alloy_smelter"))
                        .andThen(b -> b.addDynamicRenderer(
                                () -> DynamicRenderHelper.makeBoilerPartRender(
                                        BoilerFireboxType.BRONZE_FIREBOX, CASING_BRONZE_BRICKS))))
                .register();
        public static final MachineDefinition LARGE_HP_STEAM_ALLOY_SMELTER = REGISTRATE
                .multiblock("large_hp_steam_alloy_smelter", BasicSteamParallelMultiblockMachine::highPressure)
                .langValue("Large High Pressure Steam Alloy Smelter")
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_STEEL_SOLID)
                .recipeType(GTRecipeTypes.ALLOY_SMELTER_RECIPES)
                .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle("FFF", "CCC", "CCC")
                        .aisle("FFF", "C#C", "CCC")
                        .aisle("FFF", "C#C", "CCC")
                        .aisle("FFF", "CSC", "CCC")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("F", Predicates.blocks(FIREBOX_STEEL.get()).setExactLimit(11)
                                .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .where("C",
                                Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(11)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)))
                        .build())
                .modelProperty(RecipeLogic.STATUS_PROPERTY, RecipeLogic.Status.IDLE)
                .model(createWorkableCasingMachineModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                        GTCEu.id("block/machines/alloy_smelter"))
                        .andThen(b -> b.addDynamicRenderer(
                                () -> DynamicRenderHelper.makeBoilerPartRender(
                                        BoilerFireboxType.STEEL_FIREBOX, CASING_STEEL_SOLID))))
                .tooltips(Component.translatable("gtmplusplus.machine.large_steam_alloy_smelter.tooltip"),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_STEAM_ORE_WASHER = REGISTRATE
                .multiblock("large_steam_ore_washer", LargeSteamOreWasherMachine::lowPressure)
                .rotationState(RotationState.NON_Y_AXIS)
                .appearanceBlock(CASING_BRONZE_BRICKS)
                .recipeType(GTRecipeTypes.ORE_WASHER_RECIPES)
                .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
                .allowExtendedFacing(false)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle("CCCCC", "CCCCC", "CCCCC")
                        .aisle("CCCCC", "CPPPC", "C###C")
                        .aisle("CCCCC", "C###C", "C###C")
                        .aisle("CCCCC", "CPPPC", "C###C")
                        .aisle("CCCCC", "CCSCC", "CCCCC")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("P", Predicates.blocks(CASING_BRONZE_PIPE.get()))
                        .where("C",
                                Predicates.blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(49)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.blocks(
                                                PartAbility.IMPORT_FLUIDS.getBlocks(GTValues.ULV).toArray(Block[]::new))
                                                .setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .hasBER(true)
                .modelProperty(RecipeLogic.STATUS_PROPERTY, RecipeLogic.Status.IDLE)
                .model(createWorkableCasingMachineModel(
                        GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                        GTCEu.id("block/machines/ore_washer"))
                        .andThen(b -> b.addDynamicRenderer(DynamicRenderHelper::makeRecipeFluidAreaRender)))
                .tooltips(
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_HP_STEAM_ORE_WASHER = REGISTRATE
                .multiblock("large_hp_steam_ore_washer", LargeSteamOreWasherMachine::highPressure)
                .langValue("Large High Pressure Steam Ore Washer")
                .rotationState(RotationState.NON_Y_AXIS)
                .appearanceBlock(CASING_STEEL_SOLID)
                .recipeType(GTRecipeTypes.ORE_WASHER_RECIPES)
                .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
                .allowExtendedFacing(false)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle("CCCCC", "CCCCC", "CCCCC")
                        .aisle("CCCCC", "CPPPC", "C###C")
                        .aisle("CCCCC", "C###C", "C###C")
                        .aisle("CCCCC", "CPPPC", "C###C")
                        .aisle("CCCCC", "CCSCC", "CCCCC")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("P", Predicates.blocks(CASING_STEEL_PIPE.get()))
                        .where("C",
                                Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get()).setMinGlobalLimited(49)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.blocks(
                                                PartAbility.IMPORT_FLUIDS.getBlocks(GTValues.ULV).toArray(Block[]::new))
                                                .setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .hasBER(true)
                .modelProperty(RecipeLogic.STATUS_PROPERTY, RecipeLogic.Status.IDLE)
                .model(createWorkableCasingMachineModel(
                        GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                        GTCEu.id("block/machines/ore_washer"))
                        .andThen(b -> b.addDynamicRenderer(DynamicRenderHelper::makeRecipeFluidAreaRender)))
                .tooltips(Component.translatable("gtmplusplus.machine.large_steam_ore_washer.tooltip"),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_STEAM_CENTRIFUGE = REGISTRATE
                .multiblock("large_steam_centrifuge", BasicSteamParallelMultiblockMachine::lowPressure)
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_BRONZE_BRICKS)
                .recipeType(GTRecipeTypes.CENTRIFUGE_RECIPES)
                .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle(" CCC ", " CCC ", " CCC ", " CCC ", " CCC ")
                        .aisle("CCCCC", "CGBGC", "CP#PC", "CG#GC", "CCCCC")
                        .aisle("CCCCC", "CBGBC", "C#P#C", "C#G#C", "CCCCC")
                        .aisle("CCCCC", "CGBGC", "CP#PC", "CG#GC", "CCCCC")
                        .aisle(" CCC ", " CSC ", " CCC ", " CCC ", " CCC ")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("P", Predicates.blocks(CASING_BRONZE_PIPE.get()))
                        .where("G", Predicates.blocks(CASING_BRONZE_GEARBOX.get()))
                        .where("B", Predicates.blocks(BRONZE_HULL.get()))
                        .where("C",
                                Predicates.blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(62)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.blocks(
                                                PartAbility.EXPORT_FLUIDS.getBlocks(GTValues.ULV).toArray(Block[]::new))
                                                .setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                        GTMPlusPlus.id("block/multiblock/large_steam_centrifuge"))
                .tooltips(
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_HP_STEAM_CENTRIFUGE = REGISTRATE
                .multiblock("large_hp_steam_centrifuge", BasicSteamParallelMultiblockMachine::highPressure)
                .langValue("Large High Pressure Steam Centrifuge")
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_STEEL_SOLID)
                .recipeType(GTRecipeTypes.CENTRIFUGE_RECIPES)
                .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle(" CCC ", " CCC ", " CCC ", " CCC ", " CCC ")
                        .aisle("CCCCC", "CGBGC", "CP#PC", "CG#GC", "CCCCC")
                        .aisle("CCCCC", "CBGBC", "C#P#C", "C#P#C", "CCCCC")
                        .aisle("CCCCC", "CGBGC", "CP#PC", "CG#GC", "CCCCC")
                        .aisle(" CCC ", " CSC ", " CCC ", " CCC ", " CCC ")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("P", Predicates.blocks(CASING_STEEL_PIPE.get()))
                        .where("G", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                        .where("B", Predicates.blocks(STEEL_HULL.get()))
                        .where("C",
                                Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(62)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.blocks(
                                                PartAbility.EXPORT_FLUIDS.getBlocks(GTValues.ULV).toArray(Block[]::new))
                                                .setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                        GTMPlusPlus.id("block/multiblock/large_steam_centrifuge"))
                .tooltips(Component.translatable("gtmplusplus.machine.large_steam_centrifuge.tooltip"),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_STEAM_MIXER = REGISTRATE
                .multiblock("large_steam_mixer", BasicSteamParallelMultiblockMachine::lowPressure)
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_BRONZE_BRICKS)
                .recipeType(GTRecipeTypes.MIXER_RECIPES)
                .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle(" CCC ", " CCC ", " CCC ", " CCC ")
                        .aisle("CCCCC", "CI#IC", "C#F#C", "CCCCC")
                        .aisle("CCCCC", "C#G#C", "CFPFC", "CCCCC")
                        .aisle("CCCCC", "CI#IC", "C#F#C", "CCCCC")
                        .aisle(" CCC ", " CSC ", " CCC ", " CCC ")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("P", Predicates.blocks(CASING_BRONZE_PIPE.get()))
                        .where("G", Predicates.blocks(CASING_BRONZE_GEARBOX.get()))
                        .where("F",
                                Predicates.blocks(Objects
                                        .requireNonNull(
                                                GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt,
                                                        GTMaterials.Bronze))
                                        .get()))
                        .where("I", Predicates.blocks(Blocks.IRON_BLOCK))
                        .where("C",
                                Predicates.blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(44)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.blocks(
                                                PartAbility.IMPORT_FLUIDS.getBlocks(GTValues.ULV).toArray(Block[]::new))
                                                .setPreviewCount(1))
                                        .or(Predicates.blocks(
                                                PartAbility.EXPORT_FLUIDS.getBlocks(GTValues.ULV).toArray(Block[]::new))
                                                .setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                        GTMPlusPlus.id("block/multiblock/large_steam_mixer"))
                .tooltips(
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();
        public static final MachineDefinition LARGE_HP_STEAM_MIXER = REGISTRATE
                .multiblock("large_hp_steam_mixer", BasicSteamParallelMultiblockMachine::highPressure)
                .langValue("Large High Pressure Steam Mixer")
                .rotationState(RotationState.ALL)
                .appearanceBlock(CASING_STEEL_SOLID)
                .recipeType(GTRecipeTypes.MIXER_RECIPES)
                .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
                .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                        .aisle(" CCC ", " CCC ", " CCC ", " CCC ")
                        .aisle("CCCCC", "CI#IC", "C#F#C", "CCCCC")
                        .aisle("CCCCC", "C#G#C", "CFPFC", "CCCCC")
                        .aisle("CCCCC", "CI#IC", "C#F#C", "CCCCC")
                        .aisle(" CCC ", " CSC ", " CCC ", " CCC ")
                        .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                        .where("#", Predicates.air())
                        .where("P", Predicates.blocks(CASING_BRONZE_PIPE.get()))
                        .where("G", Predicates.blocks(CASING_BRONZE_GEARBOX.get()))
                        .where("F",
                                Predicates.blocks(Objects
                                        .requireNonNull(
                                                GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt,
                                                        GTMaterials.Bronze))
                                        .get()))
                        .where("I",
                                Predicates.blocks(Objects
                                        .requireNonNull(
                                                GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.block,
                                                        GTMaterials.Steel))
                                        .get()))
                        .where("C",
                                Predicates.blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(44)
                                        .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                        .or(Predicates.blocks(
                                                PartAbility.IMPORT_FLUIDS.getBlocks(GTValues.ULV).toArray(Block[]::new))
                                                .setPreviewCount(1))
                                        .or(Predicates.blocks(
                                                PartAbility.EXPORT_FLUIDS.getBlocks(GTValues.ULV).toArray(Block[]::new))
                                                .setPreviewCount(1))
                                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                        .build())
                .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                        GTMPlusPlus.id("block/multiblock/large_steam_mixer"))
                .tooltips(Component.translatable("gtmplusplus.machine.large_steam_mixer.tooltip"),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                                .withStyle(ChatFormatting.GRAY),
                        Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY))
                .register();

        private static void init() {
            GTMultiMachines.STEAM_GRINDER.setTooltipBuilder(((itemStack, components) -> {
                components
                        .add(Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY));
                components.add(Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                        .withStyle(ChatFormatting.GRAY));
                components
                        .add(Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY));
            }));
            GTMultiMachines.STEAM_OVEN.setTooltipBuilder(((itemStack, components) -> {
                components
                        .add(Component.translatable("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip")
                                .withStyle(ChatFormatting.GRAY));
                components.add(Component.translatable("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip")
                        .withStyle(ChatFormatting.GRAY));
                components
                        .add(Component
                                .translatable("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                                        ConfigHolder.INSTANCE.machines.steamMultiParallelAmount)
                                .withStyle(ChatFormatting.GRAY));
            }));
        }
    }
}
