package dev.darmstadtium271.gtmplusplus.api.collection.octree;

import dev.darmstadtium271.gtmplusplus.api.collection.valuetypes.LocationRelatedValue;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class OctreeNode<T extends LocationRelatedValue> {

    private final BiFunction<T, T, T> VALUE_MERGING_FUNCTION;
    @Getter(value = AccessLevel.PROTECTED)
    private final Vec3 startPos, endPos;// [startPos,endPos)
    protected final List<OctreeNode<T>> childNodes = new ArrayList<>();
    protected final Map<Vec3, T> values = new HashMap<>();

    public OctreeNode(Vec3 startPos, Vec3 endPos, BiFunction<T, T, T> VALUE_MERGING_FUNCTION) {
        this.VALUE_MERGING_FUNCTION = VALUE_MERGING_FUNCTION;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    protected void insert(T value) {
        var pos = value.getVec3Pos();
        if (values.size() < Octree.MAX_ELEMENT || !split()) {
            if (values.computeIfPresent(pos, (key, item) -> VALUE_MERGING_FUNCTION.apply(item, value)) == null) {
                values.put(pos, value);
            }
        } else {
            for (var childNode : childNodes) {
                if (childNode.startPos.x <= pos.x && pos.x < childNode.endPos.x && childNode.startPos.y <= pos.y &&
                        pos.y < childNode.endPos.y && childNode.startPos.z <= pos.z && pos.z < childNode.endPos.z) {
                    childNode.insert(value);
                    if (values.computeIfPresent(pos, (key, item) -> VALUE_MERGING_FUNCTION.apply(item, value)) ==
                            null) {
                        values.put(pos, value);
                    }
                    break;
                }
            }
        }
    }

    protected void remove(Vec3 pos) {
        if (childNodes.isEmpty()) {
            values.remove(pos);
        } else {
            for (var childNode : childNodes) {
                if (childNode.startPos.x <= pos.x && pos.x < childNode.endPos.x && childNode.startPos.y <= pos.y &&
                        pos.y < childNode.endPos.y && childNode.startPos.z <= pos.z && pos.z < childNode.endPos.z) {
                    childNode.remove(pos);
                    values.remove(pos);
                    break;
                }
            }
        }
        if (values.isEmpty()) {
            this.childNodes.clear();
        }
    }

    protected boolean split() {
        if (!childNodes.isEmpty()) return false;
        Vec3 len = endPos.add(startPos.reverse());
        Vec3 midPos = new Vec3(Math.floor((startPos.x + endPos.x) / 2), Math.floor((startPos.y + endPos.y) / 2),
                Math.floor((startPos.z + endPos.z) / 2));
        int type = ((len.x > Octree.MIN_CUBE ? 1 : 0) << 2) | ((len.y > Octree.MIN_CUBE ? 1 : 0) << 1) |
                (len.z > Octree.MIN_CUBE ? 1 : 0);
        switch (type) {
            case 0b000: {
                return false;
            }
            case 0b100: {
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, startPos.z),
                        new Vec3(midPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(midPos.x, startPos.y, startPos.z),
                        new Vec3(endPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                break;
            }
            case 0b010: {
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, startPos.z),
                        new Vec3(endPos.x, midPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, midPos.y, startPos.z),
                        new Vec3(endPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                break;
            }
            case 0b001: {
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, midPos.z),
                        new Vec3(endPos.x, endPos.y, midPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, startPos.z),
                        new Vec3(endPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                break;
            }
            case 0b110: {
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, startPos.z),
                        new Vec3(midPos.x, midPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(midPos.x, startPos.y, startPos.z),
                        new Vec3(endPos.x, midPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, midPos.y, startPos.z),
                        new Vec3(midPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(midPos.x, midPos.y, startPos.z),
                        new Vec3(endPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                break;
            }
            case 0b101: {
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, startPos.z),
                        new Vec3(midPos.x, endPos.y, midPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(midPos.x, startPos.y, startPos.z),
                        new Vec3(endPos.x, endPos.y, midPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, midPos.z),
                        new Vec3(midPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(midPos.x, startPos.y, midPos.z),
                        new Vec3(endPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                break;
            }
            case 0b011: {
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, startPos.z),
                        new Vec3(endPos.x, midPos.y, midPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, midPos.y, startPos.z),
                        new Vec3(endPos.x, endPos.y, midPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, midPos.z),
                        new Vec3(endPos.x, midPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, midPos.y, midPos.z),
                        new Vec3(endPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                break;
            }
            case 0b111: {
                childNodes.add(new OctreeNode<>(startPos, midPos, VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(midPos.x, startPos.y, startPos.z),
                        new Vec3(endPos.x, midPos.y, midPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, midPos.y, startPos.z),
                        new Vec3(midPos.x, endPos.y, midPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, startPos.y, midPos.z),
                        new Vec3(midPos.x, midPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(midPos.x, midPos.y, startPos.z),
                        new Vec3(endPos.x, endPos.y, midPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(midPos.x, startPos.y, midPos.z),
                        new Vec3(endPos.x, midPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(startPos.x, midPos.y, midPos.z),
                        new Vec3(midPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                childNodes.add(new OctreeNode<>(new Vec3(midPos.x, midPos.y, midPos.z),
                        new Vec3(endPos.x, endPos.y, endPos.z), VALUE_MERGING_FUNCTION));
                break;
            }
        }
        values.forEach((pos, value) -> {
            for (var childNode : childNodes) {
                if (childNode.startPos.x <= pos.x && pos.x < childNode.endPos.x && childNode.startPos.y <= pos.y &&
                        pos.y < childNode.endPos.y && childNode.startPos.z <= pos.z && pos.z < childNode.endPos.z) {
                    childNode.insert(value);
                    break;
                }
            }
        });
        return true;
    }
}
