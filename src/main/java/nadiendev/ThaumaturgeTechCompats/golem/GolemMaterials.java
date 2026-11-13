package nadiendev.ThaumaturgeTechCompats.golem;

import com.leclowndu93150.thaumaturge.api.golems.GolemTrait;
import com.leclowndu93150.thaumaturge.api.golems.parts.GolemMaterial;
import com.leclowndu93150.thaumaturge.registry.TTGolemTraits;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class GolemMaterials {
    public static final int MATRIX_COLOR = 0x6ED0EC;
    public static final int NEUTRONIUM_COLOR = 0x3C4258;
    public static final int INFINITY_COLOR = 0xE8F0A8;

    public static final DeferredRegister<GolemMaterial> MATERIALS = DeferredRegister.create(GolemMaterial.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredHolder<GolemMaterial, GolemMaterial> MATRIX = MATERIALS.register("matrix", () -> new GolemMaterial(
            List.of(GolemResearch.ENTRY),
            ThaumaturgeTechCompats.id("textures/block/golem_matrix.png"),
            MATRIX_COLOR,
            30,
            12,
            5,
            () -> new ItemStack(GolemItems.MATRIX_PLATE.get()),
            () -> new ItemStack(TTItems.MECHANISM_COMPLEX.get()),
            traits(TTGolemTraits.LIGHT, TTGolemTraits.FIREPROOF, TTGolemTraits.BLASTPROOF)));

    public static final DeferredHolder<GolemMaterial, GolemMaterial> NEUTRONIUM = MATERIALS.register("neutronium", () -> new GolemMaterial(
            List.of(GolemResearch.ENTRY),
            ThaumaturgeTechCompats.id("textures/block/golem_neutronium.png"),
            NEUTRONIUM_COLOR,
            44,
            18,
            8,
            () -> new ItemStack(GolemItems.NEUTRONIUM_PLATE.get()),
            () -> new ItemStack(TTItems.MECHANISM_COMPLEX.get()),
            traits(TTGolemTraits.HEAVY, TTGolemTraits.FIREPROOF, TTGolemTraits.BLASTPROOF, TTGolemTraits.ARMORED)));

    public static final DeferredHolder<GolemMaterial, GolemMaterial> INFINITY = MATERIALS.register("infinity", () -> new GolemMaterial(
            List.of(GolemResearch.ENTRY),
            ThaumaturgeTechCompats.id("textures/block/golem_infinity.png"),
            INFINITY_COLOR,
            70,
            30,
            14,
            () -> new ItemStack(GolemItems.INFINITY_PLATE.get()),
            () -> new ItemStack(TTItems.MECHANISM_COMPLEX.get()),
            traits(TTGolemTraits.FIREPROOF, TTGolemTraits.BLASTPROOF, TTGolemTraits.ARMORED, TTGolemTraits.BRUTAL, TTGolemTraits.REPAIR)));

    private GolemMaterials() {
    }

    @SafeVarargs
    private static List<Holder<GolemTrait>> traits(Holder<GolemTrait>... holders) {
        return List.of(holders);
    }
}
