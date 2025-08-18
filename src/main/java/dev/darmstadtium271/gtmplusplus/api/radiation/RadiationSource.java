package dev.darmstadtium271.gtmplusplus.api.radiation;

import dev.darmstadtium271.gtmplusplus.api.collection.valuetypes.LocationRelatedValue;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

import java.util.function.BiFunction;

public class RadiationSource extends LocationRelatedValue {

    public static final double DECAY_RATE = 0.9995;
    public static final double MIN_RADIATION_MAGNITUDE = 0.000_010;
    public static final double BACKGROUND_RADIATION = 0.000_000_143;

    public final double magnitude;
    public final long time;

    public static final BiFunction<RadiationSource, RadiationSource, RadiationSource> VALUE_MERGING_FUNCTION = (x,
                                                                                                                y) -> {
        if (x.time < y.time) {
            return new RadiationSource(x.magnitude * Math.pow(DECAY_RATE, y.time - x.time) + y.magnitude, y.time,
                    x.getPos());
        } else {
            return new RadiationSource(y.magnitude * Math.pow(DECAY_RATE, x.time - y.time) + x.magnitude, x.time,
                    x.getPos());
        }
    };

    public RadiationSource(double magnitude, long time, BlockPos pos) {
        super(pos);
        this.magnitude = magnitude;
        this.time = time;
    }

    public double getMagnitudeAfterDecay(long currentTime) {
        return magnitude * Math.pow(DECAY_RATE, currentTime - time);
    }

    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.put("source", NbtUtils.writeBlockPos(getPos()));
        tag.putDouble("magnitude", magnitude);
        tag.putLong("time", time);
        return tag;
    }

    public static RadiationSource deserializeNBT(CompoundTag tag) {
        return new RadiationSource(tag.getDouble("magnitude"), tag.getLong("time"),
                NbtUtils.readBlockPos(tag.getCompound("source")));
    }

    @Override
    public String toString() {
        return "Magnitude: " + magnitude + ", Time: " + time + ", Pos: " + getPos();
    }
}
