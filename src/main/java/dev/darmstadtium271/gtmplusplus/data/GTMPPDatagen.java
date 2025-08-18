package dev.darmstadtium271.gtmplusplus.data;

import com.tterrag.registrate.providers.ProviderType;
import dev.darmstadtium271.gtmplusplus.data.lang.GTMPPLangHandler;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static dev.darmstadtium271.gtmplusplus.common.registry.GTMPPRegistration.REGISTRATE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GTMPPDatagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {}

    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, GTMPPLangHandler::init);
    }
}
