package nadiendev.ThaumaturgeTechCompats.flux.field;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ShieldNetwork {
    private static final Map<ResourceKey<Level>, Set<ShieldGeneratorBlockEntity>> ACTIVE = new ConcurrentHashMap<>();

    private ShieldNetwork() {
    }

    public static void add(ResourceKey<Level> dimension, ShieldGeneratorBlockEntity shield) {
        ACTIVE.computeIfAbsent(dimension, key -> Collections.newSetFromMap(new ConcurrentHashMap<>())).add(shield);
    }

    public static void remove(ResourceKey<Level> dimension, ShieldGeneratorBlockEntity shield) {
        Set<ShieldGeneratorBlockEntity> shields = ACTIVE.get(dimension);
        if (shields != null) {
            shields.remove(shield);
        }
    }

    public static void clear() {
        ACTIVE.clear();
    }

    public static List<ShieldGeneratorBlockEntity> covering(Level level, Vec3 position) {
        Set<ShieldGeneratorBlockEntity> shields = ACTIVE.get(level.dimension());
        if (shields == null || shields.isEmpty()) {
            return List.of();
        }
        List<ShieldGeneratorBlockEntity> covering = new ArrayList<>();
        for (ShieldGeneratorBlockEntity shield : shields) {
            if (shield.isActive() && shield.covers(position)) {
                covering.add(shield);
            }
        }
        return covering;
    }

    public static List<ShieldGeneratorBlockEntity> all(Level level) {
        Set<ShieldGeneratorBlockEntity> shields = ACTIVE.get(level.dimension());
        return shields == null || shields.isEmpty() ? List.of() : new ArrayList<>(shields);
    }
}
