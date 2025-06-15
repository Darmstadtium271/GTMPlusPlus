package dev.darmstadtium271.gtmplusplus;

import static dev.darmstadtium271.gtmplusplus.GTMPlusPlus.GTMPPRegistrate.REGISTRATE;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.tterrag.registrate.util.entry.RegistryEntry;

import dev.darmstadtium271.gtmplusplus.common.machine.GTMPPMultiblocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Mod(GTMPlusPlus.MODID)
public class GTMPlusPlus {

    public static final String MODID = "gtmplusplus";
    public static final String NAME = "GTM++";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static IEventBus modBus;
    public static final RegistryEntry<CreativeModeTab> MAIN_TAB = REGISTRATE
            .defaultCreativeTab("main_tab", (builder) -> builder
                    .displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("main_tab", REGISTRATE))
                    .title(REGISTRATE.addLang("itemGroup",
                            Objects.requireNonNull(ResourceLocation.tryBuild(MODID, "main_tab")), "GTMPlusPlus"))
                    .icon(GTMPPMultiblocks.LARGE_STEAM_COMPRESSOR::asStack).build())
            .register();

    public GTMPlusPlus(FMLJavaModLoadingContext context) {
        modBus = context.getModEventBus();
        ConfigHolder.init();
        GTMPPRegistrate.init();
        Events.init(modBus);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.tryBuild(MODID, FormattingUtil.toLowerCaseUnder(path));
    }

    public static class GTMPPRegistrate {

        public static GTRegistrate REGISTRATE = GTRegistrate.create(MODID);

        public static void init() {
            REGISTRATE.registerRegistrate();
            REGISTRATE.creativeModeTab(MAIN_TAB);
        }
    }
}
