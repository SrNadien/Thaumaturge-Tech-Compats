package nadiendev.ThaumaturgeTechCompats.nodepower.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

final class RiftJarTube {
    private static final int SIDES = 6;
    private static final int RING_STRIDE = SIDES * 3;
    private static final float SWAY_UNITS = 0.05F;
    private static final float SWAY_RATE_X = 0.05F;
    private static final float SWAY_RATE_Y = 0.043F;
    private static final float SWAY_RATE_Z = 0.061F;
    private static final float SWAY_STEP = 0.45F;
    private static final float THROB_RATE = 0.17F;
    private static final float THROB_STEP = 0.6F;
    private static final float THROB_DEPTH = 0.12F;
    private static final double TANGENT_EPSILON = 1.0E-8D;
    private static final float VERTICAL_THRESHOLD = 0.9F;

    private final int count;
    private final float[] path;
    private final float[] radii;
    private final float[] rings;

    private RiftJarTube(int count, float[] path, float[] radii, float[] rings) {
        this.count = count;
        this.path = path;
        this.radii = radii;
        this.rings = rings;
    }

    static RiftJarTube build(RiftJarOutline outline, float time, float sway, float throb, float thickness) {
        int count = outline.count();
        float[] path = new float[count * 3];
        float[] radii = new float[count];
        float[] rings = new float[count * RING_STRIDE];
        float swayAmount = SWAY_UNITS * sway;
        float throbAmount = THROB_DEPTH * throb;
        for (int i = 0; i < count; i++) {
            float phase = i * SWAY_STEP;
            path[i * 3] = outline.x(i) + swayAmount * Mth.sin(time * SWAY_RATE_X + phase);
            path[i * 3 + 1] = outline.y(i) + swayAmount * Mth.sin(time * SWAY_RATE_Y + phase);
            path[i * 3 + 2] = outline.z(i) + swayAmount * Mth.sin(time * SWAY_RATE_Z + phase);
            radii[i] = outline.width(i) * thickness * (1.0F + throbAmount * Mth.sin(time * THROB_RATE + i * THROB_STEP));
        }
        buildRings(path, rings, count);
        return new RiftJarTube(count, path, radii, rings);
    }

    void write(VertexConsumer consumer, Matrix4f pose, float scale, boolean coloured, int color) {
        for (int ring = 0; ring < count - 1; ring++) {
            for (int side = 0; side < SIDES; side++) {
                int next = (side + 1) % SIDES;
                vertex(consumer, pose, ring, side, scale, coloured, color);
                vertex(consumer, pose, ring, next, scale, coloured, color);
                vertex(consumer, pose, ring + 1, next, scale, coloured, color);
                vertex(consumer, pose, ring + 1, side, scale, coloured, color);
            }
        }
    }

    private void vertex(VertexConsumer consumer, Matrix4f pose, int ring, int side, float scale, boolean coloured, int color) {
        int centre = ring * 3;
        int offset = ring * RING_STRIDE + side * 3;
        float radius = radii[ring] * scale;
        VertexConsumer vertex = consumer.addVertex(pose,
                path[centre] + rings[offset] * radius,
                path[centre + 1] + rings[offset + 1] * radius,
                path[centre + 2] + rings[offset + 2] * radius);
        if (coloured) {
            vertex.setColor(color);
        }
    }

    private static void buildRings(float[] path, float[] rings, int count) {
        double normalX = 0.0D;
        double normalY = 0.0D;
        double normalZ = 0.0D;
        double tangentX = 0.0D;
        double tangentY = 1.0D;
        double tangentZ = 0.0D;
        for (int i = 0; i < count; i++) {
            int before = Math.max(0, i - 1) * 3;
            int after = Math.min(count - 1, i + 1) * 3;
            double dx = path[after] - path[before];
            double dy = path[after + 1] - path[before + 1];
            double dz = path[after + 2] - path[before + 2];
            double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (length > TANGENT_EPSILON) {
                tangentX = dx / length;
                tangentY = dy / length;
                tangentZ = dz / length;
            }
            if (i == 0) {
                boolean vertical = Math.abs(tangentY) > VERTICAL_THRESHOLD;
                normalX = vertical ? 1.0D : 0.0D;
                normalY = vertical ? 0.0D : 1.0D;
                normalZ = 0.0D;
            }
            double along = normalX * tangentX + normalY * tangentY + normalZ * tangentZ;
            normalX -= along * tangentX;
            normalY -= along * tangentY;
            normalZ -= along * tangentZ;
            double normalLength = Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);
            if (normalLength < TANGENT_EPSILON) {
                boolean vertical = Math.abs(tangentY) > VERTICAL_THRESHOLD;
                normalX = vertical ? 1.0D - tangentX * tangentX : -tangentY * tangentX;
                normalY = vertical ? -tangentX * tangentY : 1.0D - tangentY * tangentY;
                normalZ = vertical ? -tangentX * tangentZ : -tangentY * tangentZ;
                normalLength = Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);
            }
            normalX /= normalLength;
            normalY /= normalLength;
            normalZ /= normalLength;
            double binormalX = tangentY * normalZ - tangentZ * normalY;
            double binormalY = tangentZ * normalX - tangentX * normalZ;
            double binormalZ = tangentX * normalY - tangentY * normalX;
            for (int side = 0; side < SIDES; side++) {
                double angle = side * (Math.PI * 2.0D) / SIDES;
                double cos = Math.cos(angle);
                double sin = Math.sin(angle);
                int offset = i * RING_STRIDE + side * 3;
                rings[offset] = (float) (normalX * cos + binormalX * sin);
                rings[offset + 1] = (float) (normalY * cos + binormalY * sin);
                rings[offset + 2] = (float) (normalZ * cos + binormalZ * sin);
            }
        }
    }
}
