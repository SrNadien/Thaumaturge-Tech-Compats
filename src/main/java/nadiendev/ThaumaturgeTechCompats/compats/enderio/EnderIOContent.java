package nadiendev.ThaumaturgeTechCompats.compats.enderio;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nadiendev.ThaumaturgeTechCompats.compats.common.CompatJson;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;

public class EnderIOContent implements DataProvider {
    private static final String RECIPES = "data/" + MOD + "/recipe/compat/enderio/";

    private final PackOutput output;

    public EnderIOContent(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> tasks = new ArrayList<>();
        tasks.add(ContentJson.save(output, cache, "data/" + EnderIOIntegration.MOD_ID + "/data_maps/item/grinding_ball.json", grindingBallMap()));
        for (GrindingBall ball : GrindingBall.values()) {
            tasks.add(CompatJson.save(output, cache, RECIPES + ball.itemName() + ".json", grindingBallRecipe(ball)));
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Thaumaturge Tech Compats Ender IO content";
    }

    private static JsonArray conditions() {
        JsonArray conditions = CompatJson.conditions(EnderIOIntegration.MOD_ID);
        conditions.add(CompatJson.feature(CompatToggles.ENDERIO_GRINDING_BALLS));
        return conditions;
    }

    private static JsonObject grindingBallMap() {
        JsonObject values = new JsonObject();
        for (GrindingBall ball : GrindingBall.values()) {
            JsonObject stats = new JsonObject();
            stats.addProperty("BonusMultiplier", ball.bonusMultiplier());
            stats.addProperty("Durability", ball.durability());
            stats.addProperty("OutputMultiplier", ball.outputMultiplier());
            stats.addProperty("PowerUse", ball.powerUse());
            JsonObject entry = new JsonObject();
            entry.add("neoforge:conditions", conditions());
            entry.add("value", stats);
            values.add(MOD + ":" + ball.itemName(), entry);
        }
        JsonObject json = new JsonObject();
        json.add("values", values);
        return json;
    }

    private static JsonObject grindingBallRecipe(GrindingBall ball) {
        JsonObject key = new JsonObject();
        JsonObject ingot = new JsonObject();
        ingot.addProperty("tag", ball.ingotTag());
        key.add("I", ingot);
        JsonObject json = ContentJson.shaped("misc", key, ContentJson.strings(" I ", "III", " I "), MOD + ":" + ball.itemName(), GrindingBall.RECIPE_YIELD);
        json.add("neoforge:conditions", conditions());
        return json;
    }
}
