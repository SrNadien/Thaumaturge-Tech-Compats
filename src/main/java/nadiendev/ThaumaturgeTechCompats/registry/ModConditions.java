package nadiendev.ThaumaturgeTechCompats.registry;

import com.mojang.serialization.MapCodec;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.FeatureCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class ModConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, ThaumaturgeTechCompats.MOD_ID);

    public static final Supplier<MapCodec<FeatureCondition>> FEATURE_ENABLED = CONDITIONS.register("feature_enabled", () -> FeatureCondition.CODEC);

    private ModConditions() {
    }
}
