package nadiendev.ThaumaturgeTechCompats.wandtier;

import com.leclowndu93150.thaumaturge.api.wands.WandCap;
import com.leclowndu93150.thaumaturge.api.wands.WandRod;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class WandTierParts {
    public static final Identifier NEUTRONIUM_RESEARCH = ThaumaturgeTechCompats.id("neutronium_wand");
    public static final Identifier MATRIX_RESEARCH = ThaumaturgeTechCompats.id("matrix_wand");

    public static final int MATRIX_ROD_CAPACITY = 1000;
    public static final int MATRIX_ROD_CRAFT_COST = 120;
    public static final int NEUTRONIUM_ROD_CAPACITY = 5000;
    public static final int NEUTRONIUM_ROD_CRAFT_COST = 250;
    public static final float NEUTRONIUM_CAP_COST_MODIFIER = 0.5F;
    public static final int NEUTRONIUM_CAP_CRAFT_COST = 150;

    public static final DeferredRegister<WandRod> RODS = DeferredRegister.create(WandRod.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<WandCap> CAPS = DeferredRegister.create(WandCap.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredHolder<WandRod, WandRod> MATRIX_ROD = RODS.register("matrix", () -> new WandRod(
            MATRIX_ROD_CAPACITY,
            MATRIX_ROD_CRAFT_COST,
            ThaumaturgeTechCompats.id("textures/models/wand_rod_matrix.png"),
            null,
            true,
            false,
            false,
            MATRIX_RESEARCH));

    public static final DeferredHolder<WandRod, WandRod> NEUTRONIUM_ROD = RODS.register("neutronium", () -> new WandRod(
            NEUTRONIUM_ROD_CAPACITY,
            NEUTRONIUM_ROD_CRAFT_COST,
            ThaumaturgeTechCompats.id("textures/models/wand_rod_neutronium.png"),
            null,
            false,
            false,
            false,
            NEUTRONIUM_RESEARCH));

    public static final DeferredHolder<WandCap, WandCap> NEUTRONIUM_CAP = CAPS.register("neutronium", () -> new WandCap(
            NEUTRONIUM_CAP_COST_MODIFIER,
            List.of(),
            0.0F,
            NEUTRONIUM_CAP_CRAFT_COST,
            ThaumaturgeTechCompats.id("textures/models/wand_cap_neutronium.png")));

    private WandTierParts() {
    }
}
