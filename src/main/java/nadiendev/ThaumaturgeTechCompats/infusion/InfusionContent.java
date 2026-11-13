package nadiendev.ThaumaturgeTechCompats.infusion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.AvaritiaMachineTier;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InfusionContent implements DataProvider {
    public static final String MATRIX_ENTRY = InfusionIntegration.MATRIX_TOGGLE;
    public static final String STONE_ENTRY = InfusionIntegration.STONE_TOGGLE;
    public static final String ALTAR_ENTRY = InfusionIntegration.ALTAR_TOGGLE;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String PEARL = MOD + ":extremely_primordial_pearl";
    private static final String BASE_MATRIX = "thaumaturge:infusion_matrix";
    private static final String BASE_PEDESTAL = "thaumaturge:pedestal_eldritch";
    private static final String BASE_PILLAR_STONE = "thaumaturge:stone_eldritch_tile";
    private static final String BASE_RESEARCH = "thaumaturge:infusion";
    private static final String BOOST_RESEARCH = "thaumaturge:infusion_boost";
    private static final String[] FACINGS = {"east", "north", "south", "west"};
    private static final char[] FACING_KEYS = {'E', 'N', 'S', 'W'};
    private static final int MATRIX_COLUMN = 6;
    private static final int STONE_COLUMN = 8;
    private static final int ALTAR_COLUMN = 10;
    private static final int ROW = 4;

    private final PackOutput output;

    public InfusionContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (MatrixVariant variant : MatrixVariant.values()) {
            String matrix = variant.matrixName();
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/loot_table/blocks/" + matrix + ".json", lootTable(matrix)));
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + matrix + ".json", matrixRecipe(variant)));
            for (StoneKind kind : StoneKind.values()) {
                String stone = variant.stoneName(kind);
                tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/loot_table/blocks/" + stone + ".json", lootTable(stone)));
                tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + stone + ".json", stoneRecipe(variant, kind)));
            }
            for (AltarPart part : AltarPart.values()) {
                String name = variant.altarName(part);
                tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/loot_table/blocks/" + name + ".json", lootTable(name)));
            }
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/arcane_workbench/" + variant.altarName(AltarPart.STONE) + ".json", altarStoneRecipe(variant)));
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + variant.altarName(AltarPart.PEDESTAL) + ".json", altarPedestalRecipe(variant)));
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/blueprint/" + altarStructure(variant) + ".json", altarBlueprint(variant)));
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/dust_trigger/" + altarStructure(variant) + ".json", altarDustTrigger(variant)));
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/tags/item/" + InfusionTiers.TAG_FOLDER + variant.id() + ".json", tierTag(variant)));
        }
        for (InstabilityKind kind : InstabilityKind.values()) {
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/instability_outcome/" + kind.registryName() + ".json", instabilityOutcome(kind)));
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + MATRIX_ENTRY + ".json", matrixEntry()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + STONE_ENTRY + ".json", stoneEntry()));
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ALTAR_ENTRY + ".json", altarEntry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    public static String altarStructure(MatrixVariant variant) {
        return variant.id() + "_infusion_altar";
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats infusion variants";
    }

    private static JsonArray conditions(String toggle) {
        JsonArray conditions = new JsonArray();
        conditions.add(CompatJson.feature(toggle));
        return conditions;
    }

    private static JsonObject tierTag(MatrixVariant variant) {
        JsonArray values = new JsonArray();
        for (AvaritiaMachineTier machine : AvaritiaMachineTier.values()) {
            if (machine.variant() == variant) {
                values.add(optionalItem(machine.collectorName()));
                values.add(optionalItem(machine.compressorName()));
            }
        }
        JsonObject json = new JsonObject();
        json.addProperty("replace", false);
        json.add("values", values);
        return json;
    }

    private static JsonObject optionalItem(String name) {
        JsonObject entry = new JsonObject();
        entry.addProperty("id", MOD + ":" + name);
        entry.addProperty("required", false);
        return entry;
    }

    private static JsonObject lootTable(String name) {
        JsonObject entry = new JsonObject();
        entry.addProperty("type", "minecraft:item");
        entry.addProperty("name", MOD + ":" + name);

        JsonArray entries = new JsonArray();
        entries.add(entry);

        JsonObject condition = new JsonObject();
        condition.addProperty("condition", "minecraft:survives_explosion");
        JsonArray poolConditions = new JsonArray();
        poolConditions.add(condition);

        JsonObject pool = new JsonObject();
        pool.addProperty("bonus_rolls", 0.0);
        pool.add("conditions", poolConditions);
        pool.add("entries", entries);
        pool.addProperty("rolls", 1.0);

        JsonArray pools = new JsonArray();
        pools.add(pool);

        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:block");
        json.add("pools", pools);
        json.addProperty("random_sequence", MOD + ":blocks/" + name);
        return json;
    }

    private static JsonArray ingots(MatrixVariant variant) {
        JsonArray components = new JsonArray();
        for (int i = 0; i < 4; i++) {
            components.add(ContentJson.item(variant.ingot()));
        }
        if (variant == MatrixVariant.INFINITY) {
            components.add(ContentJson.item(PEARL));
        }
        return components;
    }

    private static JsonObject matrixRecipe(MatrixVariant variant) {
        MatrixVariant previous = variant.previous();
        String catalyst = previous == null ? BASE_MATRIX : MOD + ":" + previous.matrixName();
        JsonArray aspects;
        int instability;
        switch (variant) {
            case MATRIX -> {
                aspects = ContentJson.aspects("thaumaturge:potentia", 24, "thaumaturge:lux", 16, "thaumaturge:ignis", 16, ContentJson.ASPECT_TERMINUS, 4);
                instability = 3;
            }
            case NEUTRONIUM -> {
                aspects = ContentJson.aspects("thaumaturge:potentia", 32, "thaumaturge:perditio", 32, "thaumaturge:metallum", 24, ContentJson.ASPECT_TERMINUS, 8);
                instability = 6;
            }
            default -> {
                aspects = ContentJson.aspects("thaumaturge:potentia", 48, "thaumaturge:alienis", 48, "thaumaturge:vacuos", 32, ContentJson.ASPECT_TERMINUS, 24);
                instability = 9;
            }
        }
        JsonObject json = ContentJson.infusion(aspects, ContentJson.item(catalyst), ingots(variant), instability, MOD + ":" + MATRIX_ENTRY, MOD + ":" + variant.matrixName());
        json.add("neoforge:conditions", conditions(InfusionIntegration.MATRIX_TOGGLE));
        return json;
    }

    private static JsonObject stoneRecipe(MatrixVariant variant, StoneKind kind) {
        MatrixVariant previous = variant.previous();
        String catalyst = previous == null ? kind.baseStone() : MOD + ":" + previous.stoneName(kind);
        String focus = kind == StoneKind.COST ? "thaumaturge:ordo" : "thaumaturge:motus";
        JsonArray aspects;
        int instability;
        switch (variant) {
            case MATRIX -> {
                aspects = ContentJson.aspects(focus, 16, "thaumaturge:praecantatio", 12, ContentJson.ASPECT_TERMINUS, 2);
                instability = 2;
            }
            case NEUTRONIUM -> {
                aspects = ContentJson.aspects(focus, 24, "thaumaturge:praecantatio", 20, ContentJson.ASPECT_TERMINUS, 4);
                instability = 4;
            }
            default -> {
                aspects = ContentJson.aspects(focus, 40, "thaumaturge:praecantatio", 32, ContentJson.ASPECT_TERMINUS, 12);
                instability = 6;
            }
        }
        JsonObject json = ContentJson.infusion(aspects, ContentJson.item(catalyst), ingots(variant), instability, MOD + ":" + STONE_ENTRY, MOD + ":" + variant.stoneName(kind));
        json.add("neoforge:conditions", conditions(InfusionIntegration.STONE_TOGGLE));
        return json;
    }

    private static JsonObject instabilityOutcome(InstabilityKind kind) {
        JsonObject effect = new JsonObject();
        effect.addProperty("type", MOD + ":" + kind.registryName());
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(InfusionIntegration.MATRIX_TOGGLE));
        json.add("effect", effect);
        json.addProperty("weight", kind.weight());
        return json;
    }

    private static String previousAltar(MatrixVariant variant, AltarPart part) {
        MatrixVariant previous = variant.previous();
        if (previous != null) {
            return MOD + ":" + previous.altarName(part);
        }
        return part == AltarPart.STONE ? BASE_PILLAR_STONE : BASE_PEDESTAL;
    }

    private static JsonObject altarStoneRecipe(MatrixVariant variant) {
        JsonObject key = new JsonObject();
        key.add("I", ContentJson.item(variant.ingot()));
        key.add("S", ContentJson.item(previousAltar(variant, AltarPart.STONE)));
        String middle = " ";
        if (variant == MatrixVariant.INFINITY) {
            key.add("P", ContentJson.item(PEARL));
            middle = "P";
        }
        int vis = switch (variant) {
            case MATRIX -> 200;
            case NEUTRONIUM -> 400;
            case INFINITY -> 800;
        };
        JsonObject json = ContentJson.arcaneShaped(key, ContentJson.strings("ISI", "S" + middle + "S", "ISI"),
                MOD + ":" + ALTAR_ENTRY, vis, ContentJson.result(MOD + ":" + variant.altarName(AltarPart.STONE), 4));
        json.add("neoforge:conditions", conditions(InfusionIntegration.ALTAR_TOGGLE));
        return json;
    }

    private static JsonObject altarPedestalRecipe(MatrixVariant variant) {
        JsonArray aspects;
        int instability;
        switch (variant) {
            case MATRIX -> {
                aspects = ContentJson.aspects("thaumaturge:ordo", 20, "thaumaturge:terra", 16, ContentJson.ASPECT_TERMINUS, 3);
                instability = 2;
            }
            case NEUTRONIUM -> {
                aspects = ContentJson.aspects("thaumaturge:ordo", 32, "thaumaturge:terra", 24, ContentJson.ASPECT_TERMINUS, 6);
                instability = 4;
            }
            default -> {
                aspects = ContentJson.aspects("thaumaturge:ordo", 48, "thaumaturge:terra", 40, ContentJson.ASPECT_TERMINUS, 14);
                instability = 6;
            }
        }
        JsonObject json = ContentJson.infusion(aspects, ContentJson.item(previousAltar(variant, AltarPart.PEDESTAL)), ingots(variant), instability,
                MOD + ":" + ALTAR_ENTRY, MOD + ":" + variant.altarName(AltarPart.PEDESTAL));
        json.add("neoforge:conditions", conditions(InfusionIntegration.ALTAR_TOGGLE));
        return json;
    }

    private static JsonObject blockSource(String block) {
        JsonObject source = new JsonObject();
        source.addProperty("type", "block");
        source.addProperty("block", block);
        return source;
    }

    private static JsonObject blueprintPart(String sourceBlock, JsonObject target) {
        JsonObject part = new JsonObject();
        part.add("source", blockSource(sourceBlock));
        if (target != null) {
            part.add("target", target);
        }
        return part;
    }

    private static JsonObject airTarget() {
        JsonObject target = new JsonObject();
        target.addProperty("type", "air");
        return target;
    }

    private static JsonObject pillarTarget(String pillar, String facing) {
        JsonObject properties = new JsonObject();
        properties.addProperty("facing", facing);
        JsonObject state = new JsonObject();
        state.addProperty("Name", pillar);
        state.add("Properties", properties);
        JsonObject target = new JsonObject();
        target.addProperty("type", "state");
        target.add("state", state);
        return target;
    }

    private static JsonObject altarBlueprint(MatrixVariant variant) {
        String stone = MOD + ":" + variant.altarName(AltarPart.STONE);
        String pillar = MOD + ":" + variant.altarName(AltarPart.PILLAR);
        String pedestal = MOD + ":" + variant.altarName(AltarPart.PEDESTAL);

        JsonObject keys = new JsonObject();
        keys.add("A", blueprintPart(stone, airTarget()));
        for (int i = 0; i < FACINGS.length; i++) {
            keys.add(String.valueOf(FACING_KEYS[i]), blueprintPart(stone, pillarTarget(pillar, FACINGS[i])));
        }
        keys.add("I", blueprintPart(BASE_MATRIX, null));
        keys.add("P", blueprintPart(pedestal, null));

        JsonArray pattern = new JsonArray();
        pattern.add(ContentJson.strings("   ", " I ", "   "));
        pattern.add(ContentJson.strings("A A", "   ", "A A"));
        pattern.add(ContentJson.strings("E N", " P ", "S W"));

        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(InfusionIntegration.ALTAR_TOGGLE));
        json.add("keys", keys);
        json.add("pattern", pattern);
        json.addProperty("x_size", 3);
        json.addProperty("y_size", 3);
        json.addProperty("z_size", 3);
        return json;
    }

    private static JsonObject altarDustTrigger(MatrixVariant variant) {
        JsonObject research = new JsonObject();
        research.addProperty("entry", MOD + ":" + ALTAR_ENTRY);
        JsonObject json = new JsonObject();
        json.add("neoforge:conditions", conditions(InfusionIntegration.ALTAR_TOGGLE));
        json.addProperty("type", "thaumaturge:dust_trigger_multiblock");
        json.addProperty("blueprint", MOD + ":" + altarStructure(variant));
        json.add("research", research);
        json.add("result", ContentJson.result(BASE_MATRIX, 1));
        return json;
    }

    private static JsonObject altarEntry() {
        List<String> recipes = new ArrayList<>();
        for (MatrixVariant variant : MatrixVariant.values()) {
            recipes.add(MOD + ":arcane_workbench/" + variant.altarName(AltarPart.STONE));
        }
        for (MatrixVariant variant : MatrixVariant.values()) {
            recipes.add(MOD + ":infusion/" + variant.altarName(AltarPart.PEDESTAL));
        }
        for (MatrixVariant variant : MatrixVariant.values()) {
            recipes.add(MOD + ":dust_trigger/" + altarStructure(variant));
        }
        JsonObject json = ContentJson.research(
                ALTAR_ENTRY,
                List.of(PEARL, BASE_RESEARCH),
                ALTAR_COLUMN, ROW,
                MOD + ":" + MatrixVariant.INFINITY.altarName(AltarPart.PEDESTAL),
                recipes,
                2,
                ContentJson.aspects("thaumaturge:ordo", 6, "thaumaturge:terra", 4, ContentJson.ASPECT_TERMINUS, 3),
                2);
        json.add("neoforge:conditions", conditions(InfusionIntegration.ALTAR_TOGGLE));
        return json;
    }

    private static JsonObject matrixEntry() {
        List<String> recipes = new ArrayList<>();
        for (MatrixVariant variant : MatrixVariant.values()) {
            recipes.add(MOD + ":infusion/" + variant.matrixName());
        }
        JsonObject json = ContentJson.research(
                MATRIX_ENTRY,
                List.of(PEARL, BASE_RESEARCH),
                MATRIX_COLUMN, ROW,
                MOD + ":" + MatrixVariant.INFINITY.matrixName(),
                recipes,
                3,
                ContentJson.aspects("thaumaturge:potentia", 6, "thaumaturge:praecantatio", 4, ContentJson.ASPECT_TERMINUS, 3),
                3);
        json.add("neoforge:conditions", conditions(InfusionIntegration.MATRIX_TOGGLE));
        return json;
    }

    private static JsonObject stoneEntry() {
        List<String> recipes = new ArrayList<>();
        for (StoneKind kind : StoneKind.values()) {
            for (MatrixVariant variant : MatrixVariant.values()) {
                recipes.add(MOD + ":infusion/" + variant.stoneName(kind));
            }
        }
        JsonObject json = ContentJson.research(
                STONE_ENTRY,
                List.of(PEARL, BOOST_RESEARCH),
                STONE_COLUMN, ROW,
                MOD + ":" + MatrixVariant.INFINITY.stoneName(StoneKind.SPEED),
                recipes,
                2,
                ContentJson.aspects("thaumaturge:praecantatio", 6, "thaumaturge:potentia", 4, ContentJson.ASPECT_TERMINUS, 3),
                2);
        json.add("neoforge:conditions", conditions(InfusionIntegration.STONE_TOGGLE));
        return json;
    }
}
