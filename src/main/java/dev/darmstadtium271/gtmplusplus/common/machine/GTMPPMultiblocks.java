package dev.darmstadtium271.gtmplusplus.common.machine;

import static com.gregtechceu.gtceu.common.data.GTBlocks.*;

import static dev.darmstadtium271.gtmplusplus.GTMPlusPlus.GTMPPRegistrate.REGISTRATE;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.client.renderer.machine.LargeBoilerRenderer;
import com.gregtechceu.gtceu.common.block.BoilerFireboxType;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;

import dev.darmstadtium271.gtmplusplus.common.machine.recipemodifier.HighPressureSteamParallelRecipeModifier;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;

import java.util.Objects;

public class GTMPPMultiblocks {

    // Steam Multiblocks
    public static final MultiblockMachineDefinition HP_STEAM_GRINDER = REGISTRATE
            .multiblock("hp_steam_grinder", SteamParallelMultiblockMachine::new)
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
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                    GTCEu.id("block/multiblock/steam_grinder"))
            .tooltips(Component.translatable("gtceu.machine.steam_grinder.tooltip"),
                    Component.translatable("gtmplusplus.machine.steam_hp_multiblocks.tooltip"))
            .register();
    public static final MachineDefinition LARGE_STEAM_COMPRESSOR = REGISTRATE
            .multiblock("large_steam_compressor", SteamParallelMultiblockMachine::new)
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
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/compressor"))
            .register();
    public static final MachineDefinition LARGE_HP_STEAM_COMPRESSOR = REGISTRATE
            .multiblock("large_hp_steam_compressor", SteamParallelMultiblockMachine::new)
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
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                    GTCEu.id("block/machines/compressor"))
            .tooltips(Component.translatable("gtmplusplus.machine.large_steam_compressor.tooltip"),
                    Component.translatable("gtmplusplus.machine.steam_hp_multiblocks.tooltip"))
            .register();
    public static final MachineDefinition LARGE_STEAM_FORGE_HAMMER = REGISTRATE
            .multiblock("large_steam_forge_hammer", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.FORGE_HAMMER_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                    .aisle(" GGG ", "     ", "     ", "     ", "     ", "     ", "     ")
                    .aisle("GCCCG", "C###C", "C###C", "C###C", "C###C", "CCCCC", "  C  ")
                    .aisle("GCCCG", "#####", "#####", "#####", "#III#", "CPTPC", " CTC ")
                    .aisle("GCCCG", "C###C", "C###C", "C###C", "C###C", "CCCCC", "  C  ")
                    .aisle(" GSG ", "     ", "     ", "     ", "     ", "     ", "     ")
                    .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                    .where("#", Predicates.air())
                    .where("G",
                            Predicates.blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(0)
                                    .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                    .where("C", Predicates.blocks(CASING_BRONZE_BRICKS.get()))
                    .where("P", Predicates.blocks(CASING_BRONZE_GEARBOX.get()))
                    .where("T", Predicates.blocks(CASING_BRONZE_PIPE.get()))
                    .where("I", Predicates.blocks(Blocks.IRON_BLOCK))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    GTCEu.id("block/machines/forge_hammer"))
            .register();
    public static final MachineDefinition LARGE_HP_STEAM_FORGE_HAMMER = REGISTRATE
            .multiblock("large_hp_steam_forge_hammer", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(CASING_STEEL_SOLID)
            .recipeType(GTRecipeTypes.FORGE_HAMMER_RECIPES)
            .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
            .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                    .aisle(" GGG ", "     ", "     ", "     ", "     ", "     ", "     ")
                    .aisle("GCCCG", "C###C", "C###C", "C###C", "C###C", "CCCCC", "  C  ")
                    .aisle("GCCCG", "#####", "#####", "#####", "#III#", "CPTPC", " CTC ")
                    .aisle("GCCCG", "C###C", "C###C", "C###C", "C###C", "CCCCC", "  C  ")
                    .aisle(" GSG ", "     ", "     ", "     ", "     ", "     ", "     ")
                    .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                    .where("#", Predicates.air())
                    .where("G",
                            Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(0)
                                    .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                    .where("C", Predicates.blocks(CASING_STEEL_SOLID.get()))
                    .where("P", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("T", Predicates.blocks(CASING_STEEL_PIPE.get()))
                    .where("I",
                            Predicates.blocks(Objects
                                    .requireNonNull(
                                            GTMaterialBlocks.MATERIAL_BLOCKS.get(TagPrefix.block, GTMaterials.Steel))
                                    .get()))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                    GTCEu.id("block/machines/forge_hammer"))
            .tooltips(Component.translatable("gtmplusplus.machine.large_steam_forge_hammer.tooltip"),
                    Component.translatable("gtmplusplus.machine.steam_hp_multiblocks.tooltip"))
            .register();
    public static final MachineDefinition LARGE_STEAM_ALLOY_SMELTER = REGISTRATE
            .multiblock("large_steam_alloy_smelter", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.ALLOY_SMELTER_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                    .aisle("FFF", "CCC", "CCC")
                    .aisle("FFF", "C#C", "CCC")
                    .aisle("FFF", "CSC", "CCC")
                    .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                    .where("#", Predicates.air())
                    .where("F", Predicates.blocks(FIREBOX_BRONZE.get())
                            .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                    .where("C",
                            Predicates.blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(6)
                                    .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)))
                    .build())
            .renderer(() -> new LargeBoilerRenderer(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),
                    BoilerFireboxType.BRONZE_FIREBOX,
                    GTCEu.id("block/multiblock/steam_oven")))
            .register();
    public static final MachineDefinition LARGE_HP_STEAM_ALLOY_SMELTER = REGISTRATE
            .multiblock("large_hp_steam_alloy_smelter", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(CASING_STEEL_SOLID)
            .recipeType(GTRecipeTypes.ALLOY_SMELTER_RECIPES)
            .recipeModifier(HighPressureSteamParallelRecipeModifier::recipeModifier, true)
            .pattern(multiblockMachineDefinition -> FactoryBlockPattern.start()
                    .aisle("FFF", "CCC", "CCC")
                    .aisle("FFF", "C#C", "CCC")
                    .aisle("FFF", "CSC", "CCC")
                    .where("S", Predicates.controller(Predicates.blocks(multiblockMachineDefinition.getBlock())))
                    .where("#", Predicates.air())
                    .where("F", Predicates.blocks(FIREBOX_STEEL.get())
                            .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                    .where("C",
                            Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(6)
                                    .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)))
                    .build())
            .renderer(() -> new LargeBoilerRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                    BoilerFireboxType.STEEL_FIREBOX,
                    GTCEu.id("block/multiblock/steam_oven")))
            .tooltips(Component.translatable("gtmplusplus.machine.large_steam_alloy_smelter.tooltip"),
                    Component.translatable("gtmplusplus.machine.steam_hp_multiblocks.tooltip"))
            .register();

    public static void init() {}
}
