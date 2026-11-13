package nadiendev.ThaumaturgeTechCompats.flux.charger;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public final class ChargerConfig {
    public static final String FILE_NAME = "thaumaturgetechcompats-flux-charger.toml";
    public static final ModConfigSpec SPEC;

    private static final long CAPACITY_TICKS = 5_000L;
    private static final int DEFAULT_INTERVAL = 5;
    private static final long DEFAULT_FLUX_PER_CENTIVIS = 20L;
    private static final long DEFAULT_FLUX_PER_VIS = 2_000L;

    private static final Map<FluxTier, ModConfigSpec.LongValue> CAPACITY = new EnumMap<>(FluxTier.class);
    private static final ModConfigSpec.IntValue INTERVAL;
    private static final ModConfigSpec.BooleanValue PARTICLES;
    private static final ModConfigSpec.BooleanValue WANDS;
    private static final ModConfigSpec.LongValue FLUX_PER_CENTIVIS;
    private static final ModConfigSpec.BooleanValue VIS_ITEMS;
    private static final ModConfigSpec.LongValue FLUX_PER_VIS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("general");
        INTERVAL = builder.comment("Ticks between two recharge passes of an Environmental Charger. Every pass gives each item the Dimensional Flux of that many ticks of the rate of its tier.")
                .defineInRange("interval", DEFAULT_INTERVAL, 1, 200);
        PARTICLES = builder.comment("An Environmental Charger that is recharging something gives out soft particles.")
                .define("particles", true);
        builder.pop();
        builder.push("capacity");
        for (FluxTier tier : FluxTier.values()) {
            CAPACITY.put(tier, builder.comment("Dimensional Flux the internal store of an Environmental Charger of this tier can hold.")
                    .defineInRange(tier.id(), defaultCapacity(tier), 1L, Long.MAX_VALUE / 4));
        }
        builder.pop();
        builder.push("wands");
        WANDS = builder.comment("The charger also recharges the wands, staves and sceptres of Thaumaturge, converting Dimensional Flux into vis of their primal aspects.")
                .define("enabled", true);
        FLUX_PER_CENTIVIS = builder.comment("Dimensional Flux that every hundredth of a vis in a wand costs.")
                .defineInRange("flux_per_centivis", DEFAULT_FLUX_PER_CENTIVIS, 1L, 1_000_000_000L);
        builder.pop();
        builder.push("vis_items");
        VIS_ITEMS = builder.comment("The charger also recharges the other vis rechargeable items of Thaumaturge and its addons.")
                .define("enabled", true);
        FLUX_PER_VIS = builder.comment("Dimensional Flux that every vis in such an item costs.")
                .defineInRange("flux_per_vis", DEFAULT_FLUX_PER_VIS, 1L, 1_000_000_000L);
        builder.pop();
        SPEC = builder.build();
    }

    private ChargerConfig() {
    }

    private static long defaultCapacity(FluxTier tier) {
        return tier.defaultChargerRate() * CAPACITY_TICKS;
    }

    public static boolean enabled(FluxTier tier) {
        for (FluxTier step = tier; step != null; step = step.previous()) {
            if (!CompatToggles.enabled(step.toggle())) {
                return false;
            }
        }
        return true;
    }

    public static long capacity(FluxTier tier) {
        return SPEC.isLoaded() ? CAPACITY.get(tier).get() : defaultCapacity(tier);
    }

    public static int interval() {
        return SPEC.isLoaded() ? INTERVAL.get() : DEFAULT_INTERVAL;
    }

    public static boolean particles() {
        return !SPEC.isLoaded() || PARTICLES.get();
    }

    public static boolean wands() {
        return !SPEC.isLoaded() || WANDS.get();
    }

    public static long fluxPerCentivis() {
        return SPEC.isLoaded() ? FLUX_PER_CENTIVIS.get() : DEFAULT_FLUX_PER_CENTIVIS;
    }

    public static boolean visItems() {
        return !SPEC.isLoaded() || VIS_ITEMS.get();
    }

    public static long fluxPerVis() {
        return SPEC.isLoaded() ? FLUX_PER_VIS.get() : DEFAULT_FLUX_PER_VIS;
    }
}
