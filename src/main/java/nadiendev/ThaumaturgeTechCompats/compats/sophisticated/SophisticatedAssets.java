package nadiendev.ThaumaturgeTechCompats.compats.sophisticated;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SophisticatedAssets implements DataProvider {
    private static final String MOD = ContentJson.MOD;
    private static final String BACKPACKS = SophisticatedIntegration.BACKPACKS_ID;
    private static final String STORAGE = SophisticatedIntegration.STORAGE_ID;

    private final PackOutput output;

    public SophisticatedAssets(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            tasks.add(save(cache, "models/item/" + tier.stackUpgradeName(), generated(tier.stackUpgradeName())));
            tasks.add(save(cache, "models/item/" + tier.backpackName(), parent(MOD + ":block/" + tier.backpackName())));
            tasks.add(save(cache, "models/block/" + tier.backpackName(), backpackModel(tier)));
            template(cache, tasks, BACKPACKS, "blockstates/netherite_backpack", "blockstates/" + tier.backpackName(), tier);
            for (String name : barrelNames(tier)) {
                String source = name.replace(tier.id(), "netherite");
                template(cache, tasks, STORAGE, "blockstates/" + source, "blockstates/" + name, tier);
                template(cache, tasks, STORAGE, "models/block/" + source, "models/block/" + name, tier);
                template(cache, tasks, STORAGE, "models/block/flat/" + source, "models/block/flat/" + name, tier);
                tasks.add(save(cache, "models/item/" + name, parent(MOD + ":block/" + name)));
            }
            tasks.add(save(cache, "blockstates/" + tier.chestName(), variant(STORAGE + ":block/chest")));
            tasks.add(save(cache, "models/item/" + tier.chestName(), parent(STORAGE + ":item/chest")));
            tasks.add(save(cache, "blockstates/" + tier.shulkerBoxName(), variant(STORAGE + ":block/shulker_box")));
            tasks.add(save(cache, "models/item/" + tier.shulkerBoxName(), parent(STORAGE + ":item/shulker_box")));
        }
        tasks.add(save(cache, "models/item/" + SophisticatedIntegration.ARCANE_UPGRADE_ITEM, generated(SophisticatedIntegration.ARCANE_UPGRADE_ITEM)));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats sophisticated assets";
    }

    private static List<String> barrelNames(SophisticatedTier tier) {
        List<String> names = new ArrayList<>();
        names.add(tier.barrelName());
        for (int size = 1; size <= SophisticatedConfig.LIMITED_BARRELS; size++) {
            names.add(tier.limitedBarrelName(size));
        }
        return names;
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "assets/" + MOD + "/" + path + ".json", json);
    }

    private void template(CachedOutput cache, List<CompletableFuture<?>> tasks, String sourceMod, String sourcePath, String targetPath, SophisticatedTier tier) {
        String text = readResource("assets/" + sourceMod + "/" + sourcePath + ".json");
        if (text == null) {
            return;
        }
        Pattern pattern = Pattern.compile(Pattern.quote(sourceMod) + ":([a-z0-9_/]*netherite[a-z0-9_/]*)");
        Matcher matcher = pattern.matcher(text);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(result, Matcher.quoteReplacement(MOD + ":" + matcher.group(1).replace("netherite", tier.id())));
        }
        matcher.appendTail(result);
        JsonElement json = JsonParser.parseString(result.toString());
        tasks.add(ContentJson.save(output, cache, "assets/" + MOD + "/" + targetPath + ".json", json));
    }

    private static String readResource(String path) {
        ClassLoader loader = SophisticatedAssets.class.getClassLoader();
        try (InputStream stream = loader.getResourceAsStream(path)) {
            return stream == null ? null : new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            return null;
        }
    }

    private static JsonObject generated(String texture) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD + ":item/" + texture);
        JsonObject json = new JsonObject();
        json.addProperty("parent", "minecraft:item/generated");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject parent(String parent) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", parent);
        return json;
    }

    private static JsonObject backpackModel(SophisticatedTier tier) {
        JsonObject textures = new JsonObject();
        textures.addProperty("clips", MOD + ":block/" + tier.clipsTexture());
        JsonObject json = new JsonObject();
        json.addProperty("parent", BACKPACKS + ":block/backpack");
        json.add("textures", textures);
        return json;
    }

    private static JsonObject variant(String model) {
        JsonObject single = new JsonObject();
        single.addProperty("model", model);
        JsonObject variants = new JsonObject();
        variants.add("", single);
        JsonObject json = new JsonObject();
        json.add("variants", variants);
        return json;
    }
}
