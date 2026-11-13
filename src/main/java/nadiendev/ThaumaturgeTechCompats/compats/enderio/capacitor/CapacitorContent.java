package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor;

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

public class CapacitorContent implements DataProvider {
    public static final String ENTRY = "enderio_capacitors";
    public static final int COLUMN = 72;

    private static final String MOD = EnderIOJson.MOD;
    private static final String BASE = "enderio:octadic_capacitor";
    private static final String VIBRANT_ALLOY = "enderio:vibrant_alloy_ingot";
    private static final String VIBRANT_CRYSTAL = "enderio:vibrant_crystal";

    private final PackOutput output;

    public CapacitorContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (EnderIOTier tier : EnderIOTier.values()) {
            tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/recipe/infusion/" + tier.capacitorName() + ".json", infusion(tier)));
        }
        tasks.add(ContentJson.save(output, cache, "data/" + MOD + "/thaumaturge/research_entry/" + ENTRY + ".json", entry()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO capacitors";
    }

    private static JsonObject infusion(EnderIOTier tier) {
        JsonArray components = new JsonArray();
        components.addAll(EnderIOJson.repeat(ContentJson.item(tier.plate()), 4));
        components.addAll(EnderIOJson.repeat(ContentJson.item(VIBRANT_ALLOY), 2));
        components.add(ContentJson.item(VIBRANT_CRYSTAL));
        switch (tier) {
            case ALCHEMICAL_BRASS -> components.add(ContentJson.item(EnderIOJson.MECHANISM_SIMPLE));
            case VOID_METAL -> components.add(ContentJson.item(EnderIOJson.VOID_SEED));
            default -> {
            }
        }
        EnderIOTier previous = tier.previous();
        String catalyst = previous == null ? BASE : MOD + ":" + previous.capacitorName();
        int scale = tier.ordinal() + 1;
        JsonObject json = ContentJson.infusion(
                ContentJson.aspects("thaumaturge:potentia", 24 * scale, "thaumaturge:metallum", 12 * scale, "thaumaturge:machina", 12 * scale),
                ContentJson.item(catalyst),
                components,
                3 * scale,
                MOD + ":" + ENTRY,
                MOD + ":" + tier.capacitorName());
        json.add("neoforge:conditions", EnderIOJson.conditions(EnderIOIntegration.CAPACITORS));
        return json;
    }

    private static JsonObject entry() {
        List<String> recipes = new ArrayList<>();
        for (EnderIOTier tier : EnderIOTier.values()) {
            recipes.add(MOD + ":infusion/" + tier.capacitorName());
        }
        return EnderIOJson.entry(
                ENTRY,
                EnderIOJson.conditions(EnderIOIntegration.CAPACITORS),
                List.of(TechCategory.HEADER_ID, EnderIOJson.INFUSION_ENTRY),
                COLUMN,
                MOD + ":" + EnderIOTier.VOID_METAL.capacitorName(),
                recipes,
                EnderIOJson.CATEGORY_ARTIFICE, 2, 1,
                ContentJson.aspects("thaumaturge:potentia", 6, "thaumaturge:machina", 5, "thaumaturge:metallum", 4),
                2);
    }
}
