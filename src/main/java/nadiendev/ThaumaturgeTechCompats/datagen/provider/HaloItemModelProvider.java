package nadiendev.ThaumaturgeTechCompats.datagen.provider;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HaloItemModelProvider implements DataProvider {
    private static final int NEUTRONIUM_HALO_COLOR = -1711276033;
    private static final int INFINITY_HALO_COLOR = -16777216;
    private static final List<String> INFINITY_ITEMS = List.of("infinity_plate", "infinity_scribing_tools", "infinity_focus", "infinity_wand_cap");
    private static final List<String> NEUTRONIUM_ITEMS = List.of("neutronium_plate", "neutronium_scribing_tools", "neutronium_wand_rod", "neutronium_wand_cap");

    private final PackOutput output;

    public HaloItemModelProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "extremely_primordial_pearl", "minecraft:item/generated", 0xFFC8A0FF, 4, true));
        tasks.add(save(cache, "akashic_record", "minecraft:item/generated", 0xFFA0E0FF, 3, false));
        for (String infinityItem : INFINITY_ITEMS) {
            tasks.add(save(cache, infinityItem, "minecraft:item/generated", INFINITY_HALO_COLOR, 10, true));
        }
        for (String neutroniumItem : NEUTRONIUM_ITEMS) {
            tasks.add(save(cache, neutroniumItem, neutroniumItem.endsWith("_rod") ? "minecraft:item/handheld" : "minecraft:item/generated", NEUTRONIUM_HALO_COLOR, 6, false, "avaritia:misc/halo_noise"));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats halo item models";
    }

    private CompletableFuture<?> save(CachedOutput cache, String item, String parent, int color, int size, boolean pulse) {
        return save(cache, item, parent, color, size, pulse, "avaritia:misc/halo");
    }

    private CompletableFuture<?> save(CachedOutput cache, String item, String parent, int color, int size, boolean pulse, String haloTexture) {
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", ThaumaturgeTechCompats.MOD_ID + ":item/" + item);
        textures.addProperty("halo", haloTexture);

        JsonObject halo = new JsonObject();
        halo.addProperty("texture", "#halo");
        halo.addProperty("color", color);
        halo.addProperty("size", size);
        halo.addProperty("pulse", pulse);

        JsonObject json = new JsonObject();
        json.addProperty("parent", parent);
        json.add("textures", textures);
        json.addProperty("loader", "avaritia:halo");
        json.add("halo", halo);

        Path path = output.getOutputFolder().resolve("assets/" + ThaumaturgeTechCompats.MOD_ID + "/models/item/" + item + ".json");
        return DataProvider.saveStable(cache, json, path);
    }
}
