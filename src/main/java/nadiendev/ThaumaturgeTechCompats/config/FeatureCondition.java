package nadiendev.ThaumaturgeTechCompats.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;

public record FeatureCondition(String feature) implements ICondition {
    public static final MapCodec<FeatureCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("feature").forGetter(FeatureCondition::feature)
    ).apply(instance, FeatureCondition::new));

    @Override
    public boolean test(IContext context) {
        return CompatToggles.enabled(feature);
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
