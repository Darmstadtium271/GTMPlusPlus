package dev.darmstadtium271.gtmplusplus.config;

import dev.darmstadtium271.gtmplusplus.GTMPlusPlus;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = GTMPlusPlus.MODID)
public class GTMPPConfig {

    public static GTMPPConfig INSTANCE;

    public static void init() {
        INSTANCE = Configuration.registerConfig(GTMPPConfig.class, ConfigFormats.json()).getConfigInstance();
    }
}
