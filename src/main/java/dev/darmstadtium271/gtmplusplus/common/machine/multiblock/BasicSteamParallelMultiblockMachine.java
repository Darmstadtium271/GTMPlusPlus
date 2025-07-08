package dev.darmstadtium271.gtmplusplus.common.machine.multiblock;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@Getter
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BasicSteamParallelMultiblockMachine extends SteamParallelMultiblockMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            BasicSteamParallelMultiblockMachine.class, SteamParallelMultiblockMachine.MANAGED_FIELD_HOLDER);
    @Persisted
    @DescSynced
    protected final boolean isHighPressure;

    protected BasicSteamParallelMultiblockMachine(IMachineBlockEntity holder, boolean isHighPressure, Object... args) {
        super(holder, args);
        this.isHighPressure = isHighPressure;
    }

    public static BasicSteamParallelMultiblockMachine lowPressure(IMachineBlockEntity holder, Object... args) {
        return new BasicSteamParallelMultiblockMachine(holder, false, args);
    }

    public static BasicSteamParallelMultiblockMachine highPressure(IMachineBlockEntity holder, Object... args) {
        return new BasicSteamParallelMultiblockMachine(holder, true, args);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public IGuiTexture getScreenTexture() {
        return GuiTextures.DISPLAY_STEAM.get(isHighPressure);
    }
}
