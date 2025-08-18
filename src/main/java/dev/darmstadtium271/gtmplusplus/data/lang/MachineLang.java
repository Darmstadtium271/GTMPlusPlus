package dev.darmstadtium271.gtmplusplus.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class MachineLang {

    protected static void init(RegistrateLangProvider provider) {
        // Steam Multiblocks
        provider.add("gtmplusplus.machine.large_steam_compressor.tooltip", "Compress Everything");
        provider.add("gtmplusplus.machine.large_steam_forge_hammer.tooltip", "Wham! Wham!");
        provider.add("gtmplusplus.machine.large_steam_alloy_smelter.tooltip",
                "Flawless Mastery Forged in the Alloy Smelter");
        provider.add("gtmplusplus.machine.large_steam_ore_washer.tooltip", "Steam Washing");
        provider.add("gtmplusplus.machine.large_steam_centrifuge.tooltip", "Do Not Input Liquids");
        provider.add("gtmplusplus.machine.large_steam_mixer.tooltip", "Not for Muddying Water");
        provider.add("gtmplusplus.machine.steam_multiblocks.processing_velocity.tooltip",
                "25% faster than using single block steam machines of the same pressure");
        provider.add("gtmplusplus.machine.steam_multiblocks.steam_consumption.tooltip",
                "Only consumes steam at 88.8% of the L/s normally required");
        provider.add("gtmplusplus.machine.steam_multiblocks.max_parallel.tooltip",
                "Processes up to %1$s items at once");

        // Energy Meter
        provider.add("gtmplusplus.gui.energy_meter.total_energy", "Total Energy");
        provider.add("gtmplusplus.gui.energy_meter.current_power", "Current Power");
        provider.add("gtmplusplus.gui.energy_meter.clear", "Clear");
    }
}
