package nadiendev.ThaumaturgeTechCompats.infusion;

import com.leclowndu93150.thaumaturge.content.infusion.instability.InstabilityContext;
import com.leclowndu93150.thaumaturge.content.infusion.instability.InstabilityEffect;
import com.leclowndu93150.thaumaturge.content.infusion.instability.InstabilityEffectType;

public record VariantInstabilityEffect(InstabilityKind kind) implements InstabilityEffect {
    @Override
    public InstabilityEffectType<?> type() {
        return InfusionInstabilities.type(kind);
    }

    @Override
    public void apply(InstabilityContext context) {
        MatrixInstability.apply(kind, context);
    }
}
