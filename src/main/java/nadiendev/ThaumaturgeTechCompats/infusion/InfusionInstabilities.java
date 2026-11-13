package nadiendev.ThaumaturgeTechCompats.infusion;

import com.leclowndu93150.thaumaturge.content.infusion.instability.InstabilityEffectType;
import com.mojang.serialization.MapCodec;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public final class InfusionInstabilities {
    private static final DeferredRegister<InstabilityEffectType<?>> EFFECTS =
            DeferredRegister.create(InstabilityEffectType.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);
    private static final Map<InstabilityKind, DeferredHolder<InstabilityEffectType<?>, InstabilityEffectType<VariantInstabilityEffect>>> TYPES =
            new EnumMap<>(InstabilityKind.class);

    static {
        for (InstabilityKind kind : InstabilityKind.values()) {
            MapCodec<VariantInstabilityEffect> codec = MapCodec.unit(() -> new VariantInstabilityEffect(kind));
            TYPES.put(kind, EFFECTS.register(kind.registryName(), () -> new InstabilityEffectType<>(codec)));
        }
    }

    private InfusionInstabilities() {
    }

    public static void register(IEventBus modBus) {
        EFFECTS.register(modBus);
    }

    public static InstabilityEffectType<?> type(InstabilityKind kind) {
        return TYPES.get(kind).get();
    }
}
