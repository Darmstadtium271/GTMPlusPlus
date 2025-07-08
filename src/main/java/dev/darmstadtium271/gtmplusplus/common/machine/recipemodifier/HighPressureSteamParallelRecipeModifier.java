package dev.darmstadtium271.gtmplusplus.common.machine.recipemodifier;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;

import net.minecraft.MethodsReturnNonnullByDefault;

import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class HighPressureSteamParallelRecipeModifier {

    /**
     * Recipe Modifier for <b>High Pressure Steam Multiblock Machines</b> - can be
     * used as a valid {@link RecipeModifier}
     * <p>
     * Recipe is rejected if tier is greater than LV
     * </p>
     * <p>
     * Recipe is parallelized up to the Multiblock's parallel limit. Then, duration
     * is multiplied by {@code 1.5×} and EUt is multiplied by
     * {@code (8/9) × parallels}, up to a cap of 32 EUt. Finally, duration is
     * divided by {@code 2} and EUt is multiplied by {@code 2}.
     * </p>
     * Modified from
     * {@link SteamParallelMultiblockMachine#recipeModifier(MetaMachine, GTRecipe)}
     *
     * @param machine
     *                a {@link SteamParallelMultiblockMachine}
     * @param recipe
     *                recipe
     * @return A {@link ModifierFunction} for the given Steam Multiblock Machine and
     *         recipe
     */
    public static ModifierFunction recipeModifier(@NotNull MetaMachine machine, @NotNull GTRecipe recipe) {
        if (!(machine instanceof SteamParallelMultiblockMachine steamMachine)) {
            return RecipeModifier.nullWrongType(SteamParallelMultiblockMachine.class, machine);
        }
        if (RecipeHelper.getRecipeEUtTier(recipe) > GTValues.LV) return ModifierFunction.NULL;
        // long eut = RecipeHelper.getInputEUt(recipe);
        long eut = recipe.getInputEUt();
        int parallelAmount = ParallelLogic.getParallelAmount(machine, recipe, steamMachine.getMaxParallels());
        double eutMultiplier = (eut * 0.8888 * parallelAmount <= 32) ? (0.8888 * parallelAmount) : (32.0 / eut);
        return ModifierFunction.builder().inputModifier(ContentModifier.multiplier(parallelAmount))
                .outputModifier(ContentModifier.multiplier(parallelAmount)).durationMultiplier(1.5 / 2)
                .eutMultiplier(eutMultiplier * 2).parallels(parallelAmount).build();
    }
}
