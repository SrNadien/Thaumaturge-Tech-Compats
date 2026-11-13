package nadiendev.ThaumaturgeTechCompats.compats.enderio;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public final class EnderIORegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<GrindingBall, DeferredItem<Item>> BALLS = new EnumMap<>(GrindingBall.class);

    private EnderIORegistry() {
    }

    public static void registerBalls() {
        for (GrindingBall ball : GrindingBall.values()) {
            BALLS.put(ball, ITEMS.registerSimpleItem(ball.itemName()));
        }
    }

    public static Map<GrindingBall, DeferredItem<Item>> balls() {
        return BALLS;
    }
}
