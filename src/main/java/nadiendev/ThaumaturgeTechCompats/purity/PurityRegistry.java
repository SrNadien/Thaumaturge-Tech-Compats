package nadiendev.ThaumaturgeTechCompats.purity;

import com.leclowndu93150.thaumaturge.api.warp.WarpType;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.purity.biome.BiomePurificationPotionItem;
import nadiendev.ThaumaturgeTechCompats.purity.biome.ThrownBiomePurificationPotion;
import nadiendev.ThaumaturgeTechCompats.purity.warp.WarpPotionConfig;
import nadiendev.ThaumaturgeTechCompats.purity.warp.WarpPotionItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PurityRegistry {
    public static final String POTION_NAME = "purification_potion";
    public static final String BIOME_POTION_NAME = PurityToggles.BIOME_POTION;
    public static final String CLARITY_POTION_NAME = PurityToggles.CLARITY_POTION;
    public static final String ABSOLUTION_POTION_NAME = PurityToggles.ABSOLUTION_POTION;

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredItem<Item> PURIFICATION_POTION = ITEMS.registerItem(POTION_NAME,
            PurificationPotionItem::new, new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> BIOME_POTION = ITEMS.registerItem(BIOME_POTION_NAME,
            BiomePurificationPotionItem::new, new Item.Properties().stacksTo(16).rarity(Rarity.RARE));

    public static final DeferredItem<Item> CLARITY_POTION = ITEMS.registerItem(CLARITY_POTION_NAME,
            properties -> new WarpPotionItem(properties, WarpPotionConfig.CLARITY, WarpType.TEMPORARY),
            new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));

    public static final DeferredItem<Item> ABSOLUTION_POTION = ITEMS.registerItem(ABSOLUTION_POTION_NAME,
            properties -> new WarpPotionItem(properties, WarpPotionConfig.ABSOLUTION, WarpType.PERMANENT),
            new Item.Properties().stacksTo(16).rarity(Rarity.EPIC));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownPurificationPotion>> THROWN_POTION = ENTITIES.register(POTION_NAME,
            () -> EntityType.Builder.<ThrownPurificationPotion>of(ThrownPurificationPotion::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(POTION_NAME));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownBiomePurificationPotion>> THROWN_BIOME_POTION = ENTITIES.register(BIOME_POTION_NAME,
            () -> EntityType.Builder.<ThrownBiomePurificationPotion>of(ThrownBiomePurificationPotion::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(BIOME_POTION_NAME));

    private PurityRegistry() {
    }
}
