package nadiendev.ThaumaturgeTechCompats.planetary.crucifix.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class StarSparkleParticle extends SingleQuadParticle {
    private final SpriteSet sprites;

    protected StarSparkleParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz, SpriteSet sprites) {
        super(level, x, y, z, dx, dy, dz, sprites.first());
        this.sprites = sprites;
        this.xd = dx;
        this.yd = dy;
        this.zd = dz;
        this.friction = 0.9F;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.lifetime = 14 + this.random.nextInt(10);
        this.quadSize = 0.07F + this.random.nextFloat() * 0.06F;
        this.roll = this.oRoll = this.random.nextFloat() * 0.4F - 0.2F;
        setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.removed) {
            return;
        }
        float life = this.age / (float) this.lifetime;
        this.alpha = life < 0.6F ? 1.0F : 1.0F - (life - 0.6F) / 0.4F;
        setSpriteFromAge(this.sprites);
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Override
    public int getLightCoords(float partialTick) {
        return 0xF000F0;
    }

    public static final class FragmentProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public FragmentProvider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz, RandomSource random) {
            StarSparkleParticle particle = new StarSparkleParticle(level, x, y, z, dx, dy, dz, this.sprites);
            particle.friction = 0.96F;
            particle.gravity = 0.6F;
            particle.hasPhysics = true;
            particle.lifetime = 30 + particle.random.nextInt(20);
            particle.quadSize *= 1.3F;
            return particle;
        }
    }

    public static final class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz, RandomSource random) {
            return new StarSparkleParticle(level, x, y, z, dx, dy, dz, this.sprites);
        }
    }
}
