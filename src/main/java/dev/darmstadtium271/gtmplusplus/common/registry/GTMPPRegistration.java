package dev.darmstadtium271.gtmplusplus.common.registry;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import dev.darmstadtium271.gtmplusplus.GTMPlusPlus;

public class GTMPPRegistration {

    public static GTRegistrate REGISTRATE = GTRegistrate.create(GTMPlusPlus.MODID);

    public static void init() {
        REGISTRATE.registerRegistrate();
        REGISTRATE.creativeModeTab(GTMPlusPlus.MAIN_TAB);
    }
}
