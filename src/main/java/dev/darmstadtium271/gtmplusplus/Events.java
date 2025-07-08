package dev.darmstadtium271.gtmplusplus;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import dev.darmstadtium271.gtmplusplus.common.data.machines.GTMPPMachines;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class Events {

    public static void init(IEventBus modBus) {
        modBus.addGenericListener(MachineDefinition.class, Events::registerMachines);
        modBus.addListener(Events::registerMaterials);
        modBus.addListener(Events::modifyMaterials);
        modBus.addGenericListener(GTRecipeType.class, Events::registerRecipeTypes);
    }

    public static void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        GTMPPMachines.init();
    }

    public static void registerMaterials(MaterialEvent event) {}

    public static void modifyMaterials(PostMaterialEvent event) {}

    public static void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {}
}
