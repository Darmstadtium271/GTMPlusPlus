package dev.darmstadtium271.gtmplusplus.api.collection.octree;

import com.google.common.collect.ImmutableList;
import dev.darmstadtium271.gtmplusplus.api.collection.valuetypes.LocationRelatedValue;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.function.BiFunction;

public class Octree<T extends LocationRelatedValue> {

    public static final int MIN_CUBE = 16;
    public static final int RANGE = 1 << 26;
    public static final int MAX_ELEMENT = 4;

    private final BiFunction<T, T, T> VALUE_MERGING_FUNCTION;

    private final OctreeNode<T> octreeRoot;
    private final Map<Vec3, T> values = new HashMap<>();

    public Octree(int start, int end, BiFunction<T, T, T> VALUE_MERGING_FUNCTION) {
        this.VALUE_MERGING_FUNCTION = VALUE_MERGING_FUNCTION;
        octreeRoot = new OctreeNode<>(new Vec3(start, start, start), new Vec3(end, end, end), VALUE_MERGING_FUNCTION);
    }

    public Octree(BiFunction<T, T, T> VALUE_MERGING_FUNCTION) {
        this(-RANGE, RANGE, VALUE_MERGING_FUNCTION);
    }

    public void insert(T value) {
        octreeRoot.insert(value);
        if (values.computeIfPresent(value.getVec3Pos(), (key, item) -> VALUE_MERGING_FUNCTION.apply(item, value)) ==
                null) {
            values.put(value.getVec3Pos(), value);
        }
    }

    public void remove(Vec3 pos) {
        octreeRoot.remove(pos);
        values.remove(pos);
    }

    public ImmutableList<T> getValues() {
        ImmutableList.Builder<T> builder = ImmutableList.builder();
        values.forEach((pos, value) -> builder.add(value));
        return builder.build();
    }

    public ImmutableList<T> getValuesByRange(Vec3 pos, long range) {
        double xStart = pos.x - range;
        double yStart = pos.y - range;
        double zStart = pos.z - range;
        double xEnd = pos.x + range;
        double yEnd = pos.y + range;
        double zEnd = pos.z + range;
        ImmutableList.Builder<T> result = ImmutableList.builder();
        Queue<OctreeNode<T>> queue = new LinkedList<>();

        queue.add(octreeRoot);
        while (!queue.isEmpty()) {
            var currentNode = queue.remove();
            for (var childNode : currentNode.childNodes) {
                if ((xStart <= childNode.getStartPos().x && childNode.getEndPos().x <= xEnd) &&
                        (yStart <= childNode.getStartPos().y && childNode.getEndPos().y <= yEnd) &&
                        (zStart <= childNode.getStartPos().z && childNode.getEndPos().z <= zEnd)) {
                    result.addAll(childNode.values.values());
                } else if ((xEnd < childNode.getStartPos().x || childNode.getEndPos().x <= xStart) ||
                        (yEnd < childNode.getStartPos().y || childNode.getEndPos().y <= yStart) ||
                        (zEnd < childNode.getStartPos().z || childNode.getEndPos().z <= zStart)) {} else {
                            if (!childNode.values.isEmpty()) queue.add(childNode);
                        }
            }
        }
        return result.build();
    }
}
