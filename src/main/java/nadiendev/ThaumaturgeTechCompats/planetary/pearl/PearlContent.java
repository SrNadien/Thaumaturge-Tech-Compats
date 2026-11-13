package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.compats.common.TechCategory;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PearlContent implements DataProvider {
    public static final String ENTRY_BASE = "planetary_endest_pearl";
    public static final String ENTRY_APEX = "planetary_endest_pearl_apex";
    public static final String SALIS_SUFFIX = "_salis_mundus";

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String ENDEST_PEARL = "avaritia:endest_pearl";
    private static final String PRIMORDIAL_ENTRY = MOD + ":extremely_primordial_pearl";
    private static final String ASPECT_COSMIC = MOD + ":cosmic";
    private static final String ASPECT_NUCLEAR = MOD + ":nuclear";
    private static final String ASPECT_SOL = MOD + ":sol";
    private static final int[] INSTABILITY = {1, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private static final int BASE_COLUMN = 63;
    private static final int APEX_COLUMN = 64;
    private static final PlanetaryTier FIRST_APEX = PlanetaryTier.THAUMIUM;

    private final PackOutput output;

    public PearlContent(PackOutput output) {
        this.output = output;
    }

    public static int salisAmount(PlanetaryTier tier) {
        return switch (tier) {
            case BLAZE_CUBE -> 2;
            case MATRIX -> 4;
            case NEUTRONIUM -> 6;
            case INFINITY -> 8;
            default -> 0;
        };
    }

    public static String salisItem(PlanetaryTier tier) {
        return MOD + ":" + tier.id() + SALIS_SUFFIX;
    }

    public static String entryOf(PlanetaryTier tier) {
        return tier.ordinal() >= FIRST_APEX.ordinal() ? ENTRY_APEX : ENTRY_BASE;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            if (tier.craftable()) {
                tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + PearlRegistry.name(tier) + ".json", infusion(tier)));
            }
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY_BASE + ".json", baseEntry()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY_APEX + ".json", apexEntry()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/damage_type/planetary.json", damageType()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/damage_type/planetary_true.json", damageType()));
        for (String tag : new String[]{"bypasses_armor", "bypasses_enchantments", "bypasses_resistance", "bypasses_effects", "bypasses_shield", "bypasses_cooldown", "always_hurts_ender_dragons"}) {
            tasks.add(ContentJson.save(output, cache, "data/minecraft/tags/damage_type/" + tag + ".json", damageTag()));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats planetary pearl content";
    }

    private static JsonObject damageType() {
        JsonObject json = new JsonObject();
        json.addProperty("exhaustion", 0.0F);
        json.addProperty("message_id", PlanetDamage.MESSAGE_ID);
        json.addProperty("scaling", "never");
        return json;
    }

    private static JsonObject damageTag() {
        JsonArray values = new JsonArray();
        values.add(PlanetDamage.PLANETARY_TRUE.location().toString());
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        json.add("values", values);
        return json;
    }

    private static JsonArray conditions(PlanetaryTier tier) {
        JsonArray conditions = new JsonArray();
        for (PlanetaryTier step = tier; step != null; step = step.previous()) {
            conditions.add(CompatJson.feature(step.pearlToggle()));
        }
        if (salisAmount(tier) > 0) {
            JsonObject exists = new JsonObject();
            exists.addProperty("type", "neoforge:item_exists");
            exists.addProperty("item", salisItem(tier));
            conditions.add(exists);
        }
        return conditions;
    }

    private static JsonArray aspects(PlanetaryTier tier) {
        int scale = 4 + tier.ordinal() * 3;
        List<Object> pairs = new ArrayList<>();
        pairs.add("thaumaturge:motus");
        pairs.add(scale);
        pairs.add("thaumaturge:alienis");
        pairs.add(scale);
        pairs.add("thaumaturge:praecantatio");
        pairs.add(Math.max(2, scale / 2));
        if (tier.ordinal() >= PlanetaryTier.DIAMOND.ordinal()) {
            pairs.add("thaumaturge:potentia");
            pairs.add(Math.max(2, scale / 2));
        }
        if (tier.ordinal() >= PlanetaryTier.THAUMIUM.ordinal()) {
            pairs.add("thaumaturge:auram");
            pairs.add(Math.max(2, scale / 2));
        }
        if (tier.ordinal() >= PlanetaryTier.VOID_METAL.ordinal()) {
            pairs.add("thaumaturge:vacuos");
            pairs.add(Math.max(2, scale / 2));
        }
        if (tier == PlanetaryTier.BLAZE_CUBE) {
            pairs.add(ASPECT_SOL);
            pairs.add(Math.max(2, scale / 2));
        }
        if (tier.ordinal() >= PlanetaryTier.BLAZE_CUBE.ordinal()) {
            pairs.add(ASPECT_COSMIC);
            pairs.add(Math.max(2, scale / 2));
        }
        if (tier.ordinal() >= PlanetaryTier.NEUTRONIUM.ordinal()) {
            pairs.add(ASPECT_NUCLEAR);
            pairs.add(Math.max(2, scale / 2));
        }
        if (tier == PlanetaryTier.INFINITY) {
            pairs.add(ContentJson.ASPECT_TERMINUS);
            pairs.add(Math.max(2, scale / 4));
        }
        return ContentJson.aspects(pairs.toArray());
    }

    private static JsonObject infusion(PlanetaryTier tier) {
        PlanetaryTier previous = tier.previous();
        String catalyst = previous == null ? ENDEST_PEARL : MOD + ":" + PearlRegistry.name(previous);
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item(tier.ingot()));
        }
        for (int i = 0; i < salisAmount(tier); i++) {
            components.add(ContentJson.item(salisItem(tier)));
        }
        JsonObject json = ContentJson.infusion(
                aspects(tier),
                ContentJson.item(catalyst),
                components,
                INSTABILITY[tier.ordinal()],
                MOD + ":" + entryOf(tier),
                MOD + ":" + PearlRegistry.name(tier));
        json.add("neoforge:conditions", conditions(tier));
        return json;
    }

    private static JsonObject entry(String name, String parent, int column, PlanetaryTier first, PlanetaryTier icon, List<PlanetaryTier> tiers, int warp, JsonArray notes, int complexity) {
        List<String> recipes = new ArrayList<>();
        for (PlanetaryTier tier : tiers) {
            recipes.add(MOD + ":infusion/" + PearlRegistry.name(tier));
        }
        JsonObject json = ContentJson.research(name, List.of(parent), column, 0, MOD + ":" + PearlRegistry.name(icon), recipes, warp, notes, complexity);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.feature(first.pearlToggle()));
        json.add("neoforge:conditions", conditions);
        return json;
    }

    private static JsonObject baseEntry() {
        List<PlanetaryTier> tiers = new ArrayList<>();
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            if (tier.craftable() && tier.ordinal() < FIRST_APEX.ordinal()) {
                tiers.add(tier);
            }
        }
        return entry(ENTRY_BASE, PRIMORDIAL_ENTRY, BASE_COLUMN, PlanetaryTier.COPPER, PlanetaryTier.COPPER, tiers, 2,
                ContentJson.aspects("thaumaturge:motus", 6, "thaumaturge:alienis", 6, "thaumaturge:praecantatio", 4), 2);
    }

    private static JsonObject apexEntry() {
        List<PlanetaryTier> tiers = new ArrayList<>();
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            if (tier.craftable() && tier.ordinal() >= FIRST_APEX.ordinal()) {
                tiers.add(tier);
            }
        }
        return entry(ENTRY_APEX, MOD + ":" + ENTRY_BASE, APEX_COLUMN, FIRST_APEX, PlanetaryTier.INFINITY, tiers, 3,
                ContentJson.aspects("thaumaturge:alienis", 8, "thaumaturge:potentia", 6, ContentJson.ASPECT_TERMINUS, 4), 3);
    }
}
