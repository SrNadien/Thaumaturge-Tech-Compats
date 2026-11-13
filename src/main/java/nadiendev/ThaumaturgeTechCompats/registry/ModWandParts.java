package nadiendev.ThaumaturgeTechCompats.registry;

import com.leclowndu93150.thaumaturge.api.wands.WandCap;
import com.leclowndu93150.thaumaturge.api.wands.WandRod;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.wand.InfinityRodUpdate;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class ModWandParts {
    public static final int INFINITY_ROD_CAPACITY = 9999999;
    public static final int INFINITY_ROD_CRAFT_COST = 9999;
    public static final float MATRIX_CAP_COST_MODIFIER = 0.0F;
    public static final int MATRIX_CAP_CRAFT_COST = 1000;
    public static final float INFINITY_CAP_COST_MODIFIER = 0.0F;
    public static final int INFINITY_CAP_CRAFT_COST = 2000;

    public static final DeferredRegister<WandRod> RODS = DeferredRegister.create(WandRod.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<WandCap> CAPS = DeferredRegister.create(WandCap.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredHolder<WandRod, WandRod> INFINITY_ROD = RODS.register("infinity", () -> new WandRod(
            INFINITY_ROD_CAPACITY,
            INFINITY_ROD_CRAFT_COST,
            ThaumaturgeTechCompats.id("textures/models/wand_rod_infinity.png"),
            new InfinityRodUpdate(),
            true,
            false,
            false));

    public static final DeferredHolder<WandCap, WandCap> MATRIX_CAP = CAPS.register("matrix", () -> new WandCap(
            MATRIX_CAP_COST_MODIFIER,
            List.of(),
            1.0F,
            MATRIX_CAP_CRAFT_COST,
            ThaumaturgeTechCompats.id("textures/models/wand_cap_matrix.png")));

    public static final DeferredHolder<WandCap, WandCap> INFINITY_CAP = CAPS.register("infinity", () -> new WandCap(
            INFINITY_CAP_COST_MODIFIER,
            List.of(),
            1.0F,
            INFINITY_CAP_CRAFT_COST,
            ThaumaturgeTechCompats.id("textures/models/wand_cap_crystal.png")));

    private ModWandParts() {
    }
}
