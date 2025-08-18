package dev.darmstadtium271.gtmplusplus.api.collection.valuetypes;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

@Getter
public abstract class LocationRelatedValue {

    private final BlockPos pos;

    public LocationRelatedValue(BlockPos pos) {
        this.pos = pos;
    }

    public Vec3 getVec3Pos() {
        return pos.getCenter();
    }
}
