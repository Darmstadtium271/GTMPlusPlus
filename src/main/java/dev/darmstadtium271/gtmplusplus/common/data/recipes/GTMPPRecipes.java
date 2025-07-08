package dev.darmstadtium271.gtmplusplus.common.data.recipes;

import dev.darmstadtium271.gtmplusplus.common.data.recipes.machine.SteamMultiblockCraftingRecipes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class GTMPPRecipes {

    public static void addRecipes(Consumer<FinishedRecipe> provider) {
        SteamMultiblockCraftingRecipes.addRecipes(provider);
    }
}
