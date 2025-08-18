package dev.darmstadtium271.gtmplusplus;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import dev.darmstadtium271.gtmplusplus.common.data.recipes.GTMPPRecipes;
import dev.darmstadtium271.gtmplusplus.common.registry.GTMPPRegistration;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@GTAddon
public class GTMPPAddon implements IGTAddon {

    @Override
    public GTRegistrate getRegistrate() {
        return GTMPPRegistration.REGISTRATE;
    }

    @Override
    public void initializeAddon() {}

    @Override
    public String addonModId() {
        return GTMPlusPlus.MODID;
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        GTMPPRecipes.addRecipes(provider);
        IGTAddon.super.addRecipes(provider);
    }
}
