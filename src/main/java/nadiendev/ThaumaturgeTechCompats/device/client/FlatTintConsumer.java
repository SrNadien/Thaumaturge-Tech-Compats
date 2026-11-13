package nadiendev.ThaumaturgeTechCompats.device.client;

import com.mojang.blaze3d.vertex.VertexConsumer;

public final class FlatTintConsumer implements VertexConsumer {
    private final VertexConsumer target;

    public FlatTintConsumer(VertexConsumer target) {
        this.target = target;
    }

    @Override
    public VertexConsumer addVertex(float x, float y, float z) {
        target.addVertex(x, y, z);
        return this;
    }

    @Override
    public VertexConsumer setColor(int red, int green, int blue, int alpha) {
        target.setColor(255, 255, 255, alpha);
        return this;
    }

    @Override
    public VertexConsumer setUv(float u, float v) {
        target.setUv(u, v);
        return this;
    }

    @Override
    public VertexConsumer setUv1(int u, int v) {
        target.setUv1(u, v);
        return this;
    }

    @Override
    public VertexConsumer setUv2(int u, int v) {
        target.setUv2(u, v);
        return this;
    }

    @Override
    public VertexConsumer setNormal(float x, float y, float z) {
        target.setNormal(x, y, z);
        return this;
    }
}
