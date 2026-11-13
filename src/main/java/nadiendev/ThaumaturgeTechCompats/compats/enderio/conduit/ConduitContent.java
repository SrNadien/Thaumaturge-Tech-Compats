package nadiendev.ThaumaturgeTechCompats.compats.enderio.conduit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOJson;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ConduitContent implements DataProvider {
    public static final String ENTRY = "enderio_conduits";
    public static final int COLUMN = 71;

    private static final String MOD = EnderIOJson.MOD;
    private static final String BINDER = "enderio:conduit_binder";
    private static final String CONDUIT = "enderio:conduit";
    private static final int RESULT_COUNT = 8;

    private final PackOutput output;

    public ConduitContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (ConduitKind kind : ConduitKind.values()) {
            for (EnderIOTier tier : EnderIOTier.values()) {
                tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/arcane_workbench/" + kind.itemName(tier) + ".json", arcane(kind, tier)));
            }
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY + ".json", entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO conduits";
    }

    private static JsonObject catalyst(ConduitKind kind, EnderIOTier tier) {
        EnderIOTier previous = tier.previous();
        JsonObject json = new JsonObject();
        json.addProperty("type", CONDUIT);
        json.addProperty("conduit_type", previous == null ? kind.base() : MOD + ":" + kind.name(previous));
        return json;
    }

    private static JsonObject result(ConduitKind kind, EnderIOTier tier) {
        JsonObject components = new JsonObject();
        components.addProperty(CONDUIT, MOD + ":" + kind.name(tier));
        JsonObject stack = ContentJson.result(CONDUIT, RESULT_COUNT);
        stack.add("components", components);
        return stack;
    }

    private static String crystal(ConduitKind kind) {
        return switch (kind) {
            case ENERGY -> "thaumaturge:ignis";
            case ITEM -> "thaumaturge:aer";
            case FLUID -> "thaumaturge:aqua";
        };
    }

    private static JsonObject arcane(ConduitKind kind, EnderIOTier tier) {
        JsonObject key = new JsonObject();
        key.add("P", ContentJson.item(tier.plate()));
        key.add("E", ContentJson.item(kind.extra()));
        key.add("B", ContentJson.item(BINDER));
        key.add("C", catalyst(kind, tier));
        String special = switch (tier) {
            case THAUMIUM -> tier.plate();
            case ALCHEMICAL_BRASS -> EnderIOJson.MECHANISM_SIMPLE;
            case VOID_METAL -> EnderIOJson.VOID_SEED;
        };
        key.add("M", ContentJson.item(special));
        int scale = tier.ordinal() + 1;
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("PEP", "BCB", "PMP"), MOD + ":" + ENTRY, 40 * scale, result(kind, tier));
        json.add("crystals", ContentJson.aspects(crystal(kind), 1 + scale, "thaumaturge:ordo", scale));
        json.add("neoforge:conditions", EnderIOJson.conditions(EnderIOIntegration.CONDUITS));
        return json;
    }

    private static JsonObject entry() {
        List<String> recipes = new ArrayList<>();
        for (ConduitKind kind : ConduitKind.values()) {
            for (EnderIOTier tier : EnderIOTier.values()) {
                recipes.add(MOD + ":arcane_workbench/" + kind.itemName(tier));
            }
        }
        return EnderIOJson.entry(
                ENTRY,
                EnderIOJson.conditions(EnderIOIntegration.CONDUITS),
                List.of(TechCategory.HEADER_ID, EnderIOJson.INFUSION_ENTRY),
                COLUMN,
                "enderio:conduit_binder",
                recipes,
                EnderIOJson.CATEGORY_ARTIFICE, 2, 1,
                ContentJson.aspects("thaumaturge:machina", 6, "thaumaturge:potentia", 5, "thaumaturge:motus", 4),
                2);
    }
}
