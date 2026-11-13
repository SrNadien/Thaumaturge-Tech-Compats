package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

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

public class CrucifixContent implements DataProvider {
    public static final String ENTRY = "star_crucifix";
    public static final String SALIS_SUFFIX = "_salis_mundus";
    public static final int COLUMN = 65;
    public static final int ROW = 3;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String TOTEM = "minecraft:totem_of_undying";
    private static final String ASPECT_NUCLEAR = MOD + ":nuclear";
    private static final String ASPECT_COSMIC = MOD + ":cosmic";
    private static final String[] BYPASS_TAGS = {
            "bypasses_armor", "bypasses_shield", "bypasses_invulnerability", "bypasses_cooldown", "bypasses_effects",
            "bypasses_resistance", "bypasses_enchantments", "bypasses_wolf_armor", "no_knockback", "always_hurts_ender_dragons"
    };

    private final PackOutput output;

    public CrucifixContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            if (tier.craftable()) {
                tasks.add(save(cache, "data/" + MOD + "/recipe/infusion/" + CrucifixRegistry.itemName(tier) + ".json", infusion(tier)));
            }
        }
        tasks.add(save(cache, "data/" + MOD + "/damage_type/" + CrucifixDamageTypes.NAME + ".json", damageType()));
        for (String tag : BYPASS_TAGS) {
            tasks.add(save(cache, "data/minecraft/tags/damage_type/" + tag + ".json", tag(MOD + ":" + CrucifixDamageTypes.NAME)));
        }
        tasks.add(save(cache, "data/neoforge/tags/damage_type/is_magic.json", tag(MOD + ":" + CrucifixDamageTypes.NAME)));
        tasks.add(save(cache, "data/" + MOD + "/tags/entity_type/crucifix_immune.json",
                tag("#c:bosses", "minecraft:wither", "minecraft:ender_dragon", "minecraft:warden", "minecraft:elder_guardian")));
        tasks.add(save(cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY + ".json", entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats star crucifix content";
    }

    public static String salisItem(PlanetaryTier tier) {
        return MOD + ":" + tier.id() + SALIS_SUFFIX;
    }

    public static int salisCount(PlanetaryTier tier) {
        return switch (tier) {
            case BLAZE_CUBE -> 2;
            case MATRIX -> 4;
            case NEUTRONIUM -> 6;
            case INFINITY -> 8;
            default -> 0;
        };
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, path, json);
    }

    private static JsonObject tag(String... values) {
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        json.add("values", ContentJson.strings(values));
        return json;
    }

    private static JsonObject damageType() {
        JsonObject json = new JsonObject();
        json.addProperty("exhaustion", 0.0);
        json.addProperty("message_id", MOD + "." + CrucifixDamageTypes.NAME);
        json.addProperty("scaling", "never");
        return json;
    }

    private static JsonArray conditions(PlanetaryTier tier) {
        JsonArray conditions = new JsonArray();
        for (PlanetaryTier step : PlanetaryTier.values()) {
            if (!step.creative() && step.ordinal() <= tier.ordinal()) {
                conditions.add(CompatJson.feature(step.crucifixToggle()));
            }
        }
        if (salisCount(tier) > 0) {
            JsonObject exists = new JsonObject();
            exists.addProperty("type", "neoforge:item_exists");
            exists.addProperty("item", salisItem(tier));
            conditions.add(exists);
        }
        return conditions;
    }

    private static String catalyst(PlanetaryTier tier) {
        PlanetaryTier previous = tier.previous();
        return previous == null ? TOTEM : MOD + ":" + CrucifixRegistry.itemName(previous);
    }

    private static JsonObject infusion(PlanetaryTier tier) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item(tier.ingot()));
        }
        for (int i = 0; i < salisCount(tier); i++) {
            components.add(ContentJson.item(salisItem(tier)));
        }
        JsonObject json = ContentJson.infusion(aspects(tier), ContentJson.item(catalyst(tier)), components, instability(tier),
                MOD + ":" + ENTRY, MOD + ":" + CrucifixRegistry.itemName(tier));
        json.add("neoforge:conditions", conditions(tier));
        return json;
    }

    private static int instability(PlanetaryTier tier) {
        return switch (tier) {
            case COPPER -> 1;
            case IRON -> 2;
            case GOLD -> 3;
            case DIAMOND -> 4;
            case EMERALD -> 5;
            case NETHERITE, THAUMIUM -> 6;
            case ALCHEMICAL_BRASS -> 7;
            case VOID_METAL -> 8;
            case BLAZE_CUBE -> 9;
            case MATRIX -> 10;
            case NEUTRONIUM -> 11;
            case INFINITY, CREATIVE -> 12;
        };
    }

    private static JsonArray aspects(PlanetaryTier tier) {
        return switch (tier) {
            case COPPER -> ContentJson.aspects("thaumaturge:vinculum", 8, "thaumaturge:praemunio", 8, "thaumaturge:spiritus", 4);
            case IRON -> ContentJson.aspects("thaumaturge:vinculum", 12, "thaumaturge:praemunio", 12, "thaumaturge:spiritus", 8,
                    "thaumaturge:metallum", 4);
            case GOLD -> ContentJson.aspects("thaumaturge:vinculum", 16, "thaumaturge:praemunio", 12, "thaumaturge:spiritus", 12,
                    "thaumaturge:lux", 8);
            case DIAMOND -> ContentJson.aspects("thaumaturge:vinculum", 20, "thaumaturge:praemunio", 16, "thaumaturge:spiritus", 16,
                    "thaumaturge:lux", 12, "thaumaturge:auram", 8);
            case EMERALD -> ContentJson.aspects("thaumaturge:vinculum", 24, "thaumaturge:praemunio", 20, "thaumaturge:spiritus", 20,
                    "thaumaturge:lux", 16, "thaumaturge:auram", 12);
            case NETHERITE -> ContentJson.aspects("thaumaturge:vinculum", 28, "thaumaturge:praemunio", 24, "thaumaturge:mortuus", 20,
                    "thaumaturge:tenebrae", 16, "thaumaturge:ignis", 16);
            case THAUMIUM -> ContentJson.aspects("thaumaturge:vinculum", 32, "thaumaturge:praemunio", 28, "thaumaturge:praecantatio", 24,
                    "thaumaturge:auram", 20, "thaumaturge:spiritus", 20);
            case ALCHEMICAL_BRASS -> ContentJson.aspects("thaumaturge:vinculum", 36, "thaumaturge:mortuus", 28,
                    "thaumaturge:praecantatio", 28, "thaumaturge:exanimis", 20, "thaumaturge:potentia", 20);
            case VOID_METAL -> ContentJson.aspects("thaumaturge:vinculum", 40, "thaumaturge:vacuos", 32, "thaumaturge:tenebrae", 32,
                    "thaumaturge:mortuus", 28, "thaumaturge:alienis", 24);
            case BLAZE_CUBE -> ContentJson.aspects("thaumaturge:vinculum", 44, "thaumaturge:ignis", 40, "thaumaturge:mortuus", 32,
                    "thaumaturge:potentia", 32, "thaumaturge:vacuos", 24, ContentJson.ASPECT_TERMINUS, 8);
            case MATRIX -> ContentJson.aspects("thaumaturge:vinculum", 48, "thaumaturge:vitreus", 36, "thaumaturge:lux", 36,
                    "thaumaturge:auram", 32, "thaumaturge:ordo", 32, "thaumaturge:mortuus", 32, ContentJson.ASPECT_TERMINUS, 12);
            case NEUTRONIUM -> ContentJson.aspects("thaumaturge:vinculum", 56, "thaumaturge:mortuus", 40, "thaumaturge:ordo", 40,
                    "thaumaturge:potentia", 40, ASPECT_NUCLEAR, 32, "thaumaturge:vacuos", 32, ContentJson.ASPECT_TERMINUS, 16);
            case INFINITY, CREATIVE -> ContentJson.aspects("thaumaturge:vinculum", 64, ASPECT_COSMIC, 48, "thaumaturge:mortuus", 48,
                    "thaumaturge:ordo", 48, "thaumaturge:alienis", 40, ASPECT_NUCLEAR, 32, ContentJson.ASPECT_TERMINUS, 24);
        };
    }

    private static JsonObject entry() {
        List<String> recipes = new ArrayList<>();
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            if (tier.craftable()) {
                recipes.add(MOD + ":infusion/" + CrucifixRegistry.itemName(tier));
            }
        }
        JsonObject json = ContentJson.research(
                ENTRY,
                List.of(TechCategory.HEADER_ID),
                COLUMN, ROW,
                MOD + ":" + CrucifixRegistry.itemName(PlanetaryTier.INFINITY),
                recipes,
                2,
                ContentJson.aspects("thaumaturge:vinculum", 6, "thaumaturge:mortuus", 4, "thaumaturge:praemunio", 4),
                3);
        json.addProperty("category", TechCategory.CATEGORY_ID);
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.feature(PlanetaryTier.COPPER.crucifixToggle()));
        json.add("neoforge:conditions", conditions);
        return json;
    }
}
