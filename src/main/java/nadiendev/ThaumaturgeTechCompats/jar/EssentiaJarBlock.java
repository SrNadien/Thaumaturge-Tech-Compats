package nadiendev.ThaumaturgeTechCompats.jar;

import com.leclowndu93150.thaumaturge.content.essentia.jar.AbstractBlockEntityJar;
import com.leclowndu93150.thaumaturge.content.essentia.jar.AbstractBlockJar;
import com.leclowndu93150.thaumaturge.registry.TTBlockEntities;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class EssentiaJarBlock extends AbstractBlockJar {
    public static final MapCodec<EssentiaJarBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(),
            JarTier.CODEC.fieldOf("tier").forGetter(EssentiaJarBlock::tier)
    ).apply(instance, EssentiaJarBlock::new));

    private final JarTier tier;

    public EssentiaJarBlock(Properties properties, JarTier tier) {
        super(properties);
        this.tier = tier;
    }

    public JarTier tier() {
        return tier;
    }

    @Override
    protected MapCodec<EssentiaJarBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntityType<AbstractBlockEntityJar> jarType() {
        return TTBlockEntities.JAR.get();
    }

    @Override
    public int jarCapacity() {
        return JarConfig.capacity(tier);
    }
}
