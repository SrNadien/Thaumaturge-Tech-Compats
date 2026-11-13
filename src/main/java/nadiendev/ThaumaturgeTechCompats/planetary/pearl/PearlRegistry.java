package nadiendev.ThaumaturgeTechCompats.planetary.pearl;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class PearlRegistry {
    public static final String SUFFIX = "_planetary_endest_pearl";
    public static final String PEARL_ENTITY_NAME = "planetary_pearl";
    public static final String PLANET_ENTITY_NAME = "planet";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);

    private static final Map<PlanetaryTier, DeferredItem<PlanetaryPearlItem>> PEARLS = new EnumMap<>(PlanetaryTier.class);

    public static final DeferredHolder<EntityType<?>, EntityType<PlanetaryPearlEntity>> PEARL_ENTITY = ENTITIES.register(PEARL_ENTITY_NAME,
            () -> EntityType.Builder.<PlanetaryPearlEntity>of(PlanetaryPearlEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(PEARL_ENTITY_NAME));

    public static final DeferredHolder<EntityType<?>, EntityType<PlanetEntity>> PLANET = ENTITIES.register(PLANET_ENTITY_NAME,
            () -> EntityType.Builder.<PlanetEntity>of(PlanetEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .noSave()
                    .noSummon()
                    .fireImmune()
                    .clientTrackingRange(8)
                    .updateInterval(Integer.MAX_VALUE)
                    .build(PLANET_ENTITY_NAME));

    static {
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            PEARLS.put(tier, ITEMS.register(name(tier), () -> new PlanetaryPearlItem(properties(tier), tier)));
        }
    }

    private PearlRegistry() {
    }

    public static String name(PlanetaryTier tier) {
        return tier.id() + SUFFIX;
    }

    public static DeferredItem<PlanetaryPearlItem> pearl(PlanetaryTier tier) {
        return PEARLS.get(tier);
    }

    public static List<DeferredItem<PlanetaryPearlItem>> pearls() {
        return List.copyOf(PEARLS.values());
    }

    private static Item.Properties properties(PlanetaryTier tier) {
        Item.Properties properties = new Item.Properties().rarity(tier.rarity()).stacksTo(1);
        return tier.unbreakable() ? properties.fireResistant() : properties.durability(PearlConfig.durability(tier));
    }
}
