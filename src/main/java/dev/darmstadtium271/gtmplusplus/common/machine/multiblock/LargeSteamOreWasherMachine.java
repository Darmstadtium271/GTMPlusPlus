package dev.darmstadtium271.gtmplusplus.common.machine.multiblock;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IFluidRenderMulti;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;

// Modified from LargeChemicalBathMachine
@Getter
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LargeSteamOreWasherMachine extends BasicSteamParallelMultiblockMachine implements IFluidRenderMulti {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            LargeSteamOreWasherMachine.class, BasicSteamParallelMultiblockMachine.MANAGED_FIELD_HOLDER);
    @DescSynced
    @RequireRerender
    @NotNull
    private Set<BlockPos> fluidBlockOffsets = new HashSet<>();

    protected LargeSteamOreWasherMachine(IMachineBlockEntity holder, boolean isHighPressure, Object... args) {
        super(holder, isHighPressure, args);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        IFluidRenderMulti.super.onStructureFormed();
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        IFluidRenderMulti.super.onStructureInvalid();
    }

    @NotNull
    @Override
    public Set<BlockPos> saveOffsets() {
        Direction up = RelativeDirection.UP.getRelative(getFrontFacing(), getUpwardsFacing(), isFlipped());
        Direction back = getFrontFacing().getOpposite();
        Direction clockWise = RelativeDirection.RIGHT.getRelative(getFrontFacing(), getUpwardsFacing(), isFlipped());
        Direction counterClockWise = RelativeDirection.LEFT.getRelative(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        BlockPos pos = getPos();
        BlockPos center = pos.relative(up);
        Set<BlockPos> offsets = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            center = center.relative(back);
            offsets.add(center.subtract(pos));
            offsets.add(center.relative(clockWise).subtract(pos));
            offsets.add(center.relative(counterClockWise).subtract(pos));
        }
        return offsets;
    }

    @NotNull
    @Override
    public Set<BlockPos> getFluidBlockOffsets() {
        return this.fluidBlockOffsets;
    }

    public void setFluidBlockOffsets(@NotNull final Set<BlockPos> fluidBlockOffsets) {
        if (fluidBlockOffsets == null) {
            throw new NullPointerException("fluidBlockOffsets is marked non-null but is null");
        }
        this.fluidBlockOffsets = fluidBlockOffsets;
    }
}
