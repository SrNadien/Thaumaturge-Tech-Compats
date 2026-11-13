package nadiendev.ThaumaturgeTechCompats.infusion.client;

import com.leclowndu93150.thaumaturge.api.recipe.IInfusionRecipe;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionTiers;
import nadiendev.ThaumaturgeTechCompats.infusion.InfusionTranslations;
import nadiendev.ThaumaturgeTechCompats.infusion.MatrixVariant;
import net.minecraft.network.chat.Component;

import java.util.List;

public final class InfusionInstabilityDisplay {
    private static final ThreadLocal<MatrixVariant> CURRENT = new ThreadLocal<>();

    private InfusionInstabilityDisplay() {
    }

    public static MatrixVariant tierOf(IInfusionRecipe recipe) {
        return InfusionTiers.variantOf(recipe.resultItem().getItem());
    }

    public static void begin(IInfusionRecipe recipe) {
        MatrixVariant variant = tierOf(recipe);
        if (variant == null) {
            CURRENT.remove();
        } else {
            CURRENT.set(variant);
        }
    }

    public static void end() {
        CURRENT.remove();
    }

    public static Component name() {
        MatrixVariant variant = CURRENT.get();
        return variant == null ? null : InfusionTranslations.instabilityName(variant);
    }

    public static Component tierName(IInfusionRecipe recipe) {
        MatrixVariant variant = tierOf(recipe);
        return variant == null ? null : InfusionTranslations.instabilityName(variant);
    }

    public static List<Component> tooltip(IInfusionRecipe recipe) {
        MatrixVariant variant = tierOf(recipe);
        return variant == null ? List.of() : InfusionTranslations.instabilityTooltip(variant);
    }
}
