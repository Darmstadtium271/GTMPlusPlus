package dev.darmstadtium271.gtmplusplus.common.data.recipes.machine;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static dev.darmstadtium271.gtmplusplus.common.data.machines.multiblocks.SteamMultiblocks.*;

public class SteamMultiblockCraftingRecipes {

    public static void addRecipes(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, "hp_steam_grinder",
                new ItemStack(HP_STEAM_GRINDER.getItem()),
                "SGS",
                "SMS",
                "SGS",
                'S', new ItemStack(CASING_STEEL_SOLID),
                'G', new MaterialEntry(gear, Potin),
                'M', new ItemStack(GTMachines.STEAM_MACERATOR.second().getItem()));
        VanillaRecipeHelper.addShapedRecipe(provider, "hp_steam_oven",
                new ItemStack(HP_STEAM_OVEN.getItem()),
                "SGS",
                "FMF",
                "SGS",
                'S', new ItemStack(CASING_STEEL_SOLID),
                'G', new MaterialEntry(gear, Invar),
                'F', new ItemStack(FIREBOX_STEEL),
                'M', new ItemStack(GTMachines.STEAM_FURNACE.second().getItem()));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_steam_compressor",
                new ItemStack(LARGE_STEAM_COMPRESSOR.getItem()),
                "SGS",
                "SMS",
                "SGS",
                'S', new ItemStack(CASING_BRONZE_BRICKS),
                'G', new MaterialEntry(gear, Potin),
                'M', new ItemStack(GTMachines.STEAM_COMPRESSOR.first().getItem()));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_hp_steam_compressor",
                new ItemStack(LARGE_HP_STEAM_COMPRESSOR.getItem()),
                "SGS",
                "SMS",
                "SGS",
                'S', new ItemStack(CASING_STEEL_SOLID),
                'G', new MaterialEntry(gear, Potin),
                'M', new ItemStack(GTMachines.STEAM_COMPRESSOR.second().getItem()));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_steam_forge_hammer",
                new ItemStack(LARGE_STEAM_FORGE_HAMMER.getItem()),
                "SGS",
                "SMS",
                "SGS",
                'S', new ItemStack(CASING_BRONZE_BRICKS),
                'G', new MaterialEntry(gear, Potin),
                'M', new ItemStack(GTMachines.STEAM_HAMMER.first().getItem()));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_hp_steam_forge_hammer",
                new ItemStack(LARGE_HP_STEAM_FORGE_HAMMER.getItem()),
                "SGS",
                "SMS",
                "SGS",
                'S', new ItemStack(CASING_STEEL_SOLID),
                'G', new MaterialEntry(gear, Potin),
                'M', new ItemStack(GTMachines.STEAM_HAMMER.second().getItem()));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_steam_alloy_smelter",
                new ItemStack(LARGE_STEAM_ALLOY_SMELTER.getItem()),
                "SGS",
                "SMS",
                "SGS",
                'S', new ItemStack(CASING_BRONZE_BRICKS),
                'G', new MaterialEntry(gear, Invar),
                'M', new ItemStack(GTMachines.STEAM_ALLOY_SMELTER.first().getItem()));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_hp_steam_alloy_smelter",
                new ItemStack(LARGE_HP_STEAM_ALLOY_SMELTER.getItem()),
                "SGS",
                "SMS",
                "SGS",
                'S', new ItemStack(CASING_STEEL_SOLID),
                'G', new MaterialEntry(gear, Invar),
                'M', new ItemStack(GTMachines.STEAM_ALLOY_SMELTER.second().getItem()));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_steam_ore_washer",
                new ItemStack(LARGE_STEAM_ORE_WASHER.getItem()),
                "SGS",
                "RPR",
                "SGS",
                'S', new ItemStack(CASING_BRONZE_BRICKS),
                'G', new MaterialEntry(gear, Potin),
                'R', new MaterialEntry(rotor, Tin),
                'P', new MaterialEntry(plate, Rubber));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_hp_steam_ore_washer",
                new ItemStack(LARGE_HP_STEAM_ORE_WASHER.getItem()),
                "SGS",
                "RPR",
                "SGS",
                'S', new ItemStack(CASING_STEEL_SOLID),
                'G', new MaterialEntry(gear, Potin),
                'R', new MaterialEntry(rotor, Tin),
                'P', new MaterialEntry(plate, Rubber));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_steam_centrifuge",
                new ItemStack(LARGE_STEAM_CENTRIFUGE.getItem()),
                "SGS",
                "PBP",
                "SGS",
                'S', new ItemStack(CASING_BRONZE_BRICKS),
                'G', new MaterialEntry(gear, Potin),
                'P', new MaterialEntry(plate, WroughtIron),
                'B', new ItemStack(CASING_BRONZE_GEARBOX));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_hp_steam_centrifuge",
                new ItemStack(LARGE_HP_STEAM_CENTRIFUGE.getItem()),
                "SGS",
                "PBP",
                "SGS",
                'S', new ItemStack(CASING_STEEL_SOLID),
                'G', new MaterialEntry(gear, Potin),
                'P', new MaterialEntry(plate, WroughtIron),
                'B', new ItemStack(CASING_STEEL_GEARBOX));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_steam_mixer",
                new ItemStack(LARGE_STEAM_MIXER.getItem()),
                "SGS",
                "RBR",
                "SGS",
                'S', new ItemStack(CASING_BRONZE_BRICKS),
                'G', new MaterialEntry(gear, Potin),
                'R', new MaterialEntry(rotor, Tin),
                'B', new MaterialEntry(block, Glass));
        VanillaRecipeHelper.addShapedRecipe(provider, "large_hp_steam_mixer",
                new ItemStack(LARGE_HP_STEAM_MIXER.getItem()),
                "SGS",
                "RBR",
                "SGS",
                'S', new ItemStack(CASING_STEEL_SOLID),
                'G', new MaterialEntry(gear, Potin),
                'R', new MaterialEntry(rotor, Tin),
                'B', new MaterialEntry(block, Glass));
    }
}
