package nadiendev.ThaumaturgeTechCompats.nodepower.client;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class RiftJarOutline {
    private static final int ARM_STEPS = 7;
    private static final double STEP_LENGTH = 0.2D;
    private static final double TIP_LENGTH = 0.1D;
    private static final double TURN_DEVIATION = 0.33D;
    private static final double MAX_ELEVATION = Math.PI / 2.0D;
    private static final int CACHE_SIZE = 64;

    private static final Map<Long, RiftJarOutline> CACHE = new LinkedHashMap<>(16, 0.75F, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, RiftJarOutline> eldest) {
            return size() > CACHE_SIZE;
        }
    };

    private final float[] points;
    private final float[] widths;

    private RiftJarOutline(float[] points, float[] widths) {
        this.points = points;
        this.widths = widths;
    }

    static RiftJarOutline of(long seed) {
        RiftJarOutline outline = CACHE.get(seed);
        if (outline == null) {
            outline = generate(seed);
            CACHE.put(seed, outline);
        }
        return outline;
    }

    int count() {
        return widths.length;
    }

    float x(int index) {
        return points[index * 3];
    }

    float y(int index) {
        return points[index * 3 + 1];
    }

    float z(int index) {
        return points[index * 3 + 2];
    }

    float width(int index) {
        return widths[index];
    }

    private static RiftJarOutline generate(long seed) {
        RandomSource random = RandomSource.create(seed);
        double yaw = random.nextDouble() * Math.PI * 2.0D;
        double pitch = random.nextGaussian() * TURN_DEVIATION;
        Arm first = growArm(random, yaw, pitch);
        Arm second = growArm(random, yaw + Math.PI, -pitch);
        List<Vec3> positions = new ArrayList<>();
        List<Float> sizes = new ArrayList<>();
        for (int i = second.positions().size() - 1; i >= 0; i--) {
            positions.add(second.positions().get(i));
            sizes.add(second.sizes().get(i));
        }
        positions.addAll(first.positions());
        sizes.addAll(first.sizes());
        return normalise(positions, sizes);
    }

    private static Arm growArm(RandomSource random, double yaw, double pitch) {
        List<Vec3> positions = new ArrayList<>();
        List<Float> sizes = new ArrayList<>();
        Vec3 position = Vec3.ZERO;
        Vec3 direction = Vec3.ZERO;
        for (int i = 0; i < ARM_STEPS; i++) {
            yaw += random.nextGaussian() * TURN_DEVIATION;
            pitch = Mth.clamp(pitch + random.nextGaussian() * TURN_DEVIATION, -MAX_ELEVATION, MAX_ELEVATION);
            float flat = Mth.cos((float) pitch);
            direction = new Vec3(flat * Mth.cos((float) yaw), Mth.sin((float) pitch), flat * Mth.sin((float) yaw));
            position = position.add(direction.scale(STEP_LENGTH));
            positions.add(position);
            sizes.add(Math.max(0.0F, 1.0F - i / (float) ARM_STEPS));
        }
        positions.add(position.add(direction.scale(TIP_LENGTH)));
        sizes.add(0.0F);
        return new Arm(positions, sizes);
    }

    private static RiftJarOutline normalise(List<Vec3> positions, List<Float> sizes) {
        Vec3 min = positions.get(0);
        Vec3 max = positions.get(0);
        for (Vec3 position : positions) {
            min = new Vec3(Math.min(min.x, position.x), Math.min(min.y, position.y), Math.min(min.z, position.z));
            max = new Vec3(Math.max(max.x, position.x), Math.max(max.y, position.y), Math.max(max.z, position.z));
        }
        Vec3 centre = min.add(max).scale(0.5D);
        double radius = 0.0D;
        for (Vec3 position : positions) {
            radius = Math.max(radius, position.distanceTo(centre));
        }
        if (radius < 1.0E-6D) {
            radius = 1.0D;
        }
        float[] points = new float[positions.size() * 3];
        float[] widths = new float[positions.size()];
        for (int i = 0; i < positions.size(); i++) {
            Vec3 relative = positions.get(i).subtract(centre).scale(1.0D / radius);
            points[i * 3] = (float) relative.x;
            points[i * 3 + 1] = (float) relative.y;
            points[i * 3 + 2] = (float) relative.z;
            widths[i] = sizes.get(i);
        }
        return new RiftJarOutline(points, widths);
    }

    private record Arm(List<Vec3> positions, List<Float> sizes) {
    }
}
