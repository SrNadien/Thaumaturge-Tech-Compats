package nadiendev.ThaumaturgeTechCompats.infusion;

import com.leclowndu93150.thaumaturge.content.infusion.instability.InstabilityEffect;
import com.leclowndu93150.thaumaturge.content.infusion.instability.InstabilityOutcome;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class InfusionOutcomes {
    private static final ThreadLocal<MatrixVariant> CURRENT = new ThreadLocal<>();

    private InfusionOutcomes() {
    }

    public static void begin(MatrixVariant variant) {
        CURRENT.set(variant);
    }

    public static void end() {
        CURRENT.remove();
    }

    public static Optional<InstabilityOutcome> roll(RegistryAccess access, RandomSource random) {
        MatrixVariant current = CURRENT.get();
        List<Holder.Reference<InstabilityOutcome>> candidates = access.lookupOrThrow(InstabilityOutcome.REGISTRY_KEY)
                .listElements()
                .filter(holder -> holder.value().weight() > 0 && allowed(holder.value().effect(), current))
                .sorted(Comparator.comparing((Holder.Reference<InstabilityOutcome> holder) -> holder.key().location()))
                .toList();
        int total = 0;
        for (Holder.Reference<InstabilityOutcome> holder : candidates) {
            total += holder.value().weight();
        }
        if (total <= 0) {
            return Optional.empty();
        }
        int pick = random.nextInt(total);
        for (Holder.Reference<InstabilityOutcome> holder : candidates) {
            pick -= holder.value().weight();
            if (pick < 0) {
                return Optional.of(holder.value());
            }
        }
        return Optional.empty();
    }

    private static boolean allowed(InstabilityEffect effect, MatrixVariant current) {
        if (effect instanceof VariantInstabilityEffect variantEffect) {
            return variantEffect.kind().variant() == current;
        }
        return current == null;
    }
}
