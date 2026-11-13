package nadiendev.ThaumaturgeTechCompats.compats.common;

import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TechCategory implements DataProvider {
    public static final String ID = "tech";
    public static final String HEADER = "tech";
    public static final String CATEGORY_ID = ThaumaturgeTechCompats.MOD_ID + ":" + ID;
    public static final String HEADER_ID = ThaumaturgeTechCompats.MOD_ID + ":" + HEADER;

    private static final String MOD = ThaumaturgeTechCompats.MOD_ID;

    private final PackOutput output;

    public TechCategory(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(save(cache, "thaumaturge/research_category/" + ID, category()));
        tasks.add(save(cache, "thaumaturge/research_entry/" + HEADER, header()));
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats tech category";
    }

    public static void english(LanguageProvider lang) {
        lang.add("research_category." + MOD + "." + ID, "Tech");
        lang.add("research." + MOD + "." + HEADER + ".title", "Technology");
        lang.add("research." + MOD + "." + HEADER + ".stage_0",
                "Machines and magic do not have to be enemies. Wherever a tool of iron and gears meets the aura, I should be able to bend one to the other.");
        lang.add("research." + MOD + "." + HEADER + ".stage_1",
                "Everything that joins my thaumaturgy to the technology of other mods is gathered in this tab.");
    }

    public static void spanish(LanguageProvider lang) {
        lang.add("research_category." + MOD + "." + ID, "Tech");
        lang.add("research." + MOD + "." + HEADER + ".title", "Tecnología");
        lang.add("research." + MOD + "." + HEADER + ".stage_0",
                "Las máquinas y la magia no tienen por qué ser enemigas. Allí donde una herramienta de hierro y engranajes se encuentre con el aura, debería poder doblegar una a la otra.");
        lang.add("research." + MOD + "." + HEADER + ".stage_1",
                "Todo lo que une mi taumaturgia con la tecnología de otros mods está reunido en esta pestaña.");
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonObject json) {
        return ContentJson.save(output, cache, "data/" + MOD + "/" + path + ".json", json);
    }

    private static JsonObject category() {
        JsonObject json = new JsonObject();
        json.addProperty("background", "thaumaturge:textures/gui/gui_research_back_3_smooth.png");
        json.add("formula", ContentJson.aspects(
                "thaumaturge:machina", 20,
                "thaumaturge:potentia", 10,
                "thaumaturge:motus", 5,
                "thaumaturge:instrumentum", 5,
                "thaumaturge:fabrico", 5,
                "thaumaturge:metallum", 5));
        json.addProperty("icon", MOD + ":textures/research/cat_tech.png");
        json.addProperty("index", 10);
        json.addProperty("overlay_background", "thaumaturge:textures/gui/gui_research_back_over.png");
        return json;
    }

    private static JsonObject header() {
        JsonObject json = ContentJson.research(
                HEADER,
                List.of("thaumaturge:first_steps"),
                0, 0,
                "thaumaturge:mechanism_complex",
                List.of(),
                1,
                ContentJson.aspects("thaumaturge:machina", 4, "thaumaturge:potentia", 3),
                1);
        json.addProperty("category", CATEGORY_ID);
        return json;
    }
}
