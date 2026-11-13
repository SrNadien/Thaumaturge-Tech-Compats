package nadiendev.ThaumaturgeTechCompats.compats.create.seal;

import com.leclowndu93150.thaumaturge.api.golems.seals.SealFilterMode;
import com.leclowndu93150.thaumaturge.api.golems.seals.SealPlacement;
import com.leclowndu93150.thaumaturge.api.golems.seals.SealType;
import com.leclowndu93150.thaumaturge.content.golem.ItemSealPlacer;
import com.leclowndu93150.thaumaturge.content.golem.seals.behavior.ItemMatchSettings;
import com.leclowndu93150.thaumaturge.registry.TTGolemTraits;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateConfig;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.create.CreateToggles;
import nadiendev.ThaumaturgeTechCompats.compats.create.variant.CasingVariant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CreateSeals {
    public static final String KEY = "create";
    public static final String ITEM_NAME = "seal_" + KEY;
    public static final int FILTER_SLOTS = 9;

    public static final DeferredRegister<SealType> SEALS = DeferredRegister.create(SealType.REGISTRY_KEY, ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredItem<ItemSealPlacer> ITEM = ITEMS.registerItem(ITEM_NAME,
            properties -> new ItemSealPlacer(ThaumaturgeTechCompats.id(KEY), properties));

    public static final DeferredHolder<SealType, SealType> SEAL = SEALS.register(KEY, CreateSeals::build);

    private static final SealPlacement PLACEMENT = (level, pos, face) -> {
        if (!CreateToggles.enabled(CreateToggles.SEAL)) {
            return false;
        }
        if (CreateConfig.sealAnyBlock()) {
            return true;
        }
        Block block = level.getBlockState(pos).getBlock();
        if (block instanceof CasingVariant) {
            return true;
        }
        Identifier key = BuiltInRegistries.BLOCK.getKey(block);
        return CreateIntegration.MOD_ID.equals(key.getNamespace());
    };

    private CreateSeals() {
    }

    private static SealType build() {
        return SealType.builder(CreateSealBehavior::new)
                .filter(FILTER_SLOTS, SealFilterMode.LIMITS_WHEN_WHITELIST)
                .settings(ItemMatchSettings.ALL)
                .settings(CreateSealBehavior.INSERT, CreateSealBehavior.EXTRACT, CreateSealBehavior.OPERATE, CreateSealBehavior.SNEAKING)
                .showSettings()
                .requires(TTGolemTraits.DEFT)
                .forbids(TTGolemTraits.CLUMSY)
                .placement(PLACEMENT)
                .placer(ITEM)
                .build();
    }
}
