package dev.darmstadtium271.gtmplusplus.api.collection.octree;

import com.google.common.collect.ImmutableList;
import dev.darmstadtium271.gtmplusplus.api.collection.valuetypes.LocationRelatedValue;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiFunction;

public class Octree<T extends LocationRelatedValue> {

    public static final int MIN_CUBE = 16;
    public static final int RANGE = 1 << 26;
    public static final int MAX_ELEMENT = 4;

    private final OctreeNode<T> octreeRoot;

    public Octree(int start, int end, BiFunction<T, T, T> VALUE_MERGING_FUNCTION) {
        octreeRoot = new OctreeNode<>(new Vec3(start, start, start), new Vec3(end, end, end), VALUE_MERGING_FUNCTION);
    }

    public Octree(BiFunction<T, T, T> VALUE_MERGING_FUNCTION) {
        this(-RANGE, RANGE, VALUE_MERGING_FUNCTION);
    }

    public void insert(T value) {
        octreeRoot.insert(value);
    }

    public void remove(Vec3 pos) {
        octreeRoot.remove(pos);
    }

    public ImmutableList<T> getValues() {
        ImmutableList.Builder<T> builder = ImmutableList.builder();
        octreeRoot.values.forEach((pos, value) -> builder.add(value));
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
            if ((xStart <= currentNode.getStartPos().x && currentNode.getEndPos().x <= xEnd) &&
                    (yStart <= currentNode.getStartPos().y && currentNode.getEndPos().y <= yEnd) &&
                    (zStart <= currentNode.getStartPos().z && currentNode.getEndPos().z <= zEnd)) {
                result.addAll(currentNode.values.values());
            } else if ((xEnd < currentNode.getStartPos().x || currentNode.getEndPos().x <= xStart) ||
                    (yEnd < currentNode.getStartPos().y || currentNode.getEndPos().y <= yStart) ||
                    (zEnd < currentNode.getStartPos().z || currentNode.getEndPos().z <= zStart)) {
            } else if (!currentNode.values.isEmpty()) {
                if (currentNode.childNodes.isEmpty())
                    currentNode.values.forEach((key, value) -> {
                        if (xStart <= key.x && key.x < xEnd && yStart <= key.y && key.y < yEnd && zStart <= key.z &&
                                key.z < zEnd)
                            result.add(value);
                    });
                else {
                    for (var childNode : currentNode.childNodes) {
                        if (!childNode.values.isEmpty()) queue.add(childNode);
                    }
                }
            }
        }
        return result.build();
    }
}
