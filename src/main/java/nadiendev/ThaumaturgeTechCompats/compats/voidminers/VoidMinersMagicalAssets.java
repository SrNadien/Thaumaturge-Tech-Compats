package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class VoidMinersMagicalAssets implements DataProvider {
    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;
    private static final String VOID_MINERS = VoidMinersMagicalIntegration.MOD_ID;

    private final PackOutput output;

    public VoidMinersMagicalAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            add(tasks, cache, tier.minerBlock(), controllerModel(tier, "miner_base"));
            add(tasks, cache, tier.solarBlock(), controllerModel(tier, "solar_panel_base"));
            add(tasks, cache, tier.minerFrameBlock(), frameModel(tier));
            add(tasks, cache, tier.solarFrameBlock(), frameModel(tier));
            for (MagicalUpgrade upgrade : MagicalUpgrade.values()) {
                add(tasks, cache, upgrade.blockName(tier), upgradeModel(tier, upgrade));
            }
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats void miners assets";
    }

    private void add(List<CompletableFuture<?>> tasks, CachedOutput cache, String name, JsonObject model) {
        tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/blockstates/" + name + ".json", blockState(name)));
        tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/block/" + name + ".json", model));
        tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/models/item/" + name + ".json", itemModel(name)));
    }

    private static String texture(VoidMinersMagicalTier tier, String name) {
        return MOD + ":block/voidminers/" + tier.id() + "/" + name;
    }

    private static JsonObject blockState(String name) {
        JsonObject variant = new JsonObject();
        variant.addProperty("model", MOD + ":block/" + name);
        JsonObject variants = new JsonObject();
        variants.add("", variant);
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }

    private static JsonObject controllerModel(VoidMinersMagicalTier tier, String base) {
        JsonObject textures = new JsonObject();
        textures.addProperty("1", texture(tier, "miner"));
        JsonObject json = new JsonObject();
        json.addProperty("parent", VOID_MINERS + ":block/" + base);
        json.add("textures", textures);
        return json;
    }

    private static JsonObject frameModel(VoidMinersMagicalTier tier) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", texture(tier, "frame"));
        textures.addProperty("layer1", VOID_MINERS + ":block/_core/frame");
        textures.addProperty("layer2", VOID_MINERS + ":block/_core/cover");
        JsonObject json = new JsonObject();
        json.addProperty("parent", VOID_MINERS + ":block/_template/triple_layer");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject upgradeModel(VoidMinersMagicalTier tier, MagicalUpgrade upgrade) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", VOID_MINERS + ":block/_core/" + upgrade.icon());
        textures.addProperty("layer1", VOID_MINERS + ":block/_core/modifier");
        textures.addProperty("layer2", VOID_MINERS + ":block/_core/cover");
        textures.addProperty("layer3", texture(tier, "core"));
        JsonObject json = new JsonObject();
        json.addProperty("parent", VOID_MINERS + ":block/_template/quad_layer");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject itemModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", MOD + ":block/" + name);
        return json;
    }
}
