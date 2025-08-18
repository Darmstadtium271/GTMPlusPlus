package dev.darmstadtium271.gtmplusplus.api.radiation;

import com.google.common.collect.ImmutableList;
import dev.darmstadtium271.gtmplusplus.api.collection.octree.Octree;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RadiationWorldSavedData extends SavedData {

    private static final String NAME = "gtmplusplus_radiation";
    private final long CALCULATE_RANGE = 1 << 10;
    private final Octree<RadiationSource> radiationSources = new Octree<>(RadiationSource.VALUE_MERGING_FUNCTION);
    @Getter
    private final ServerLevel serverLevel;

    public RadiationWorldSavedData(ServerLevel serverLevel) {
        this.serverLevel = serverLevel;
    }

    public RadiationWorldSavedData(ServerLevel serverLevel, CompoundTag tag) {
        this(serverLevel);
        ListTag radiationSourcesTag = tag.getList(NAME, Tag.TAG_COMPOUND);
        for (int index = 0; index < radiationSourcesTag.size(); ++index) {
            radiationSources.insert(RadiationSource.deserializeNBT(radiationSourcesTag.getCompound(index)));
        }
    }

    public static RadiationWorldSavedData getOrCreate(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(tag -> new RadiationWorldSavedData(level, tag),
                () -> new RadiationWorldSavedData(level), NAME);
    }

    public void addRadiationSource(RadiationSource source) {
        radiationSources.insert(source);
        setDirty();
    }

    public double getRadiation(Vec3 pos) {
        double res = RadiationSource.BACKGROUND_RADIATION;
        ImmutableList<RadiationSource> sources = radiationSources.getValuesByRange(pos, CALCULATE_RANGE);
        for (var source : sources) {
            if (source.getPos().closerToCenterThan(pos, 0.5)) {
                res += source.magnitude;
            } else {
                res += source.magnitude / 4 /
                        ((source.getVec3Pos().x - pos.x) * (source.getVec3Pos().x - pos.x) +
                                (source.getVec3Pos().y - pos.y) * (source.getVec3Pos().y - pos.y) +
                                (source.getVec3Pos().z - pos.z) * (source.getVec3Pos().z - pos.z));
            }
        }
        return res;
    }

    public ImmutableList<RadiationSource> getRadiationSources() {
        return radiationSources.getValues();
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ListTag radiationSourcesTag = new ListTag();
        for (var radiationSource : radiationSources.getValues()) {
            radiationSourcesTag.add(radiationSource.serializeNBT(new CompoundTag()));
        }
        compoundTag.put(NAME, radiationSourcesTag);
        return compoundTag;
    }
}
