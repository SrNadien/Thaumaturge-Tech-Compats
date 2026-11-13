package nadiendev.ThaumaturgeTechCompats.taintedfood;

import com.leclowndu93150.thaumaturge.registry.TTMobEffects;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class TaintedFoodItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<TaintedFoodType, DeferredItem<Item>> FOODS = new EnumMap<>(TaintedFoodType.class);

    private TaintedFoodItems() {
    }

    public static void register(IEventBus modBus) {
        FoodProperties placeholder = new FoodProperties(1, 0.1F, false);
        for (TaintedFoodType type : TaintedFoodType.values()) {
            if (!TaintedFoodConfig.foodEnabled(type)) {
                continue;
            }
            FOODS.put(type, ITEMS.registerItem(type.id(), properties -> new TaintedFoodItem(type, properties.food(placeholder))));
        }
        ITEMS.register(modBus);
        modBus.addListener(EventPriority.LOWEST, TaintedFoodItems::onModifyDefaults);
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<Item> holder : FOODS.values()) {
            items.add(holder.get());
        }
        return items;
    }

    private static void onModifyDefaults(ModifyDefaultComponentsEvent event) {
        for (Map.Entry<TaintedFoodType, DeferredItem<Item>> entry : FOODS.entrySet()) {
            Optional<Item> original = BuiltInRegistries.ITEM.getOptional(entry.getKey().original());
            if (original.isEmpty()) {
                continue;
            }
            DataComponentMap components = original.get().components();
            FoodProperties food = components.get(DataComponents.FOOD);
            if (food == null) {
                continue;
            }
            FoodProperties tainted = taint(food);
            Consumable consumable = taint(components.getOrDefault(DataComponents.CONSUMABLE, Consumables.DEFAULT_FOOD));
            UseRemainder remainder = components.get(DataComponents.USE_REMAINDER);
            int stackSize = components.getOrDefault(DataComponents.MAX_STACK_SIZE, 64);
            Rarity rarity = components.get(DataComponents.RARITY);
            event.modify(entry.getValue().get(), builder -> {
                builder.set(DataComponents.FOOD, tainted);
                builder.set(DataComponents.CONSUMABLE, consumable);
                if (remainder != null) {
                    builder.set(DataComponents.USE_REMAINDER, remainder);
                }
                builder.set(DataComponents.MAX_STACK_SIZE, stackSize);
                if (rarity != null) {
                    builder.set(DataComponents.RARITY, rarity);
                }
            });
        }
    }

    private static FoodProperties taint(FoodProperties food) {
        int nutrition = Math.max(1, Math.round(food.nutrition() * TaintedFoodConfig.hungerMultiplier()));
        float saturation = food.saturation() * TaintedFoodConfig.saturationMultiplier();
        return new FoodProperties(nutrition, saturation, food.canAlwaysEat());
    }

    private static Consumable taint(Consumable consumable) {
        List<ConsumeEffect> effects = new ArrayList<>(consumable.onConsumeEffects());
        if (TaintedFoodConfig.effectDuration() > 0 && TaintedFoodConfig.effectChance() > 0.0F) {
            effects.add(new ApplyStatusEffectsConsumeEffect(List.of(effect()), TaintedFoodConfig.effectChance()));
        }
        return new Consumable(consumable.consumeSeconds(), consumable.animation(), consumable.sound(), consumable.hasConsumeParticles(), effects);
    }

    private static MobEffectInstance effect() {
        Identifier id = TaintedFoodConfig.effectId();
        Optional<Holder.Reference<MobEffect>> found = BuiltInRegistries.MOB_EFFECT.get(id);
        Holder<MobEffect> holder = found.isPresent() ? found.get() : TTMobEffects.FLUX_TAINT;
        return new MobEffectInstance(holder, TaintedFoodConfig.effectDuration(), TaintedFoodConfig.effectAmplifier());
    }
}
