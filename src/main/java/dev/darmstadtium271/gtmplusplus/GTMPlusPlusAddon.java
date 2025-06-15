package dev.darmstadtium271.gtmplusplus;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

@GTAddon
public class GTMPlusPlusAddon implements IGTAddon {

    @Override
    public GTRegistrate getRegistrate() {
        return GTMPlusPlus.GTMPPRegistrate.REGISTRATE;
    }

    @Override
    public void initializeAddon() {}

    @Override
    public String addonModId() {
        return GTMPlusPlus.MODID;
    }
}
