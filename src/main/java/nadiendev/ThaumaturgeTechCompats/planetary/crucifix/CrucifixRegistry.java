package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class CrucifixRegistry {
    public static final String ITEM_SUFFIX = "_star_crucifix";
    public static final String DISPLAY_NAME = "star_crucifix_display";

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, ThaumaturgeTechCompats.MOD_ID);
    private static final Map<PlanetaryTier, DeferredItem<StarCrucifixItem>> CRUCIFIXES = new EnumMap<>(PlanetaryTier.class);

    public static final DeferredHolder<EntityType<?>, EntityType<CrucifixDisplayEntity>> DISPLAY = ENTITY_TYPES.register(DISPLAY_NAME, () -> EntityType.Builder
            .<CrucifixDisplayEntity>of(CrucifixDisplayEntity::new, MobCategory.MISC)
            .sized(CrucifixTiming.DISPLAY_SIZE, CrucifixTiming.DISPLAY_SIZE)
            .noSave()
            .noSummon()
            .fireImmune()
            .clientTrackingRange(8)
            .updateInterval(20)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, ThaumaturgeTechCompats.id(DISPLAY_NAME))));

    public static final DeferredHolder<SoundEvent, SoundEvent> BIND = sound("item.star_crucifix.bind");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHAINS = sound("item.star_crucifix.chains");
    public static final DeferredHolder<SoundEvent, SoundEvent> TREMBLE = sound("item.star_crucifix.tremble");
    public static final DeferredHolder<SoundEvent, SoundEvent> DRAG = sound("item.star_crucifix.drag");
    public static final DeferredHolder<SoundEvent, SoundEvent> BANISH = sound("item.star_crucifix.banish");
    public static final DeferredHolder<SoundEvent, SoundEvent> BREAK = sound("item.star_crucifix.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHATTER = sound("item.star_crucifix.shatter");
    public static final DeferredHolder<SoundEvent, SoundEvent> STRAIN = sound("item.star_crucifix.strain");
    public static final DeferredHolder<SoundEvent, SoundEvent> ROAR = sound("item.star_crucifix.roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRACK = sound("item.star_crucifix.crack");
    public static final DeferredHolder<SoundEvent, SoundEvent> EXPLODE = sound("item.star_crucifix.explode");

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> STAR_SPARKLE =
            PARTICLES.register("star_sparkle", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> STAR_FRAGMENT =
            PARTICLES.register("star_fragment", () -> new SimpleParticleType(false));

    private CrucifixRegistry() {
    }

    public static void register(IEventBus modBus) {
        for (PlanetaryTier tier : PlanetaryTier.values()) {
            if (!CrucifixIntegration.enabled(tier)) {
                continue;
            }
            Item.Properties properties = new Item.Properties().rarity(tier.rarity());
            if (tier.unbreakable()) {
                properties.stacksTo(1).fireResistant();
            } else {
                properties.durability(CrucifixConfig.durability(tier));
            }
            CRUCIFIXES.put(tier, ITEMS.registerItem(itemName(tier), item -> new StarCrucifixItem(item, tier), () -> properties));
        }
        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);
        SOUNDS.register(modBus);
        PARTICLES.register(modBus);
    }

    public static String itemName(PlanetaryTier tier) {
        return tier.id() + ITEM_SUFFIX;
    }

    public static boolean has(PlanetaryTier tier) {
        return CRUCIFIXES.containsKey(tier);
    }

    public static Item item(PlanetaryTier tier) {
        return CRUCIFIXES.get(tier).get();
    }

    public static List<PlanetaryTier> tiers() {
        return new ArrayList<>(CRUCIFIXES.keySet());
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<StarCrucifixItem> holder : CRUCIFIXES.values()) {
            items.add(holder.get());
        }
        return items;
    }

    private static DeferredHolder<SoundEvent, SoundEvent> sound(String name) {
        return SOUNDS.register(name, SoundEvent::createVariableRangeEvent);
    }
}
