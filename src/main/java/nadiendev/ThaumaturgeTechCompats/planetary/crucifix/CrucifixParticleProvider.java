package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class CrucifixParticleProvider extends ParticleDescriptionProvider {
    public CrucifixParticleProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(CrucifixRegistry.STAR_SPARKLE.get(), ThaumaturgeTechCompats.id("star_sparkle"), 4, false);
        spriteSet(CrucifixRegistry.STAR_FRAGMENT.get(), ThaumaturgeTechCompats.id("star_sparkle"), 4, false);
    }
}
