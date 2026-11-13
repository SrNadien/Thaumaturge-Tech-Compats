package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class FarmersDelightTaintedItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<FarmersDelightFood, DeferredItem<Item>> FOODS = new EnumMap<>(FarmersDelightFood.class);
    private static final Map<FarmersDelightKnife, DeferredItem<Item>> KNIVES = new EnumMap<>(FarmersDelightKnife.class);
    private static DeferredItem<Item> fluxKnife;

    private static final float KNIFE_BASE_DAMAGE = 0.5F;

    private FarmersDelightTaintedItems() {
    }

    public static void register(IEventBus modBus, boolean foods, boolean knives, boolean fluxKnifeEnabled) {
        if (foods) {
            FoodProperties placeholder = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build();
            for (FarmersDelightFood food : FarmersDelightFood.values()) {
                if (FarmersDelightTaintedConfig.foodEnabled(food)) {
                    FOODS.put(food, ITEMS.register(food.id(), () -> new FarmersDelightFoodItem(food, foodProperties(food, placeholder))));
                }
            }
        }
        if (knives) {
            for (FarmersDelightKnife knife : FarmersDelightKnife.values()) {
                if (FarmersDelightTaintedConfig.knifeEnabled(knife)) {
                    KNIVES.put(knife, ITEMS.register(knife.id(), () -> new ThaumaturgeKnifeItem(knife, knifeProperties(knife))));
                }
            }
        }
        if (fluxKnifeEnabled) {
            fluxKnife = ITEMS.register(FluxKnife.ID, () -> new FluxKnifeItem(fluxKnifeProperties()));
        }
        ITEMS.register(modBus);
        modBus.addListener(EventPriority.LOWEST, FarmersDelightTaintedItems::onModifyDefaults);
    }

    public static List<Item> foods() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<Item> holder : FOODS.values()) {
            items.add(holder.get());
        }
        return items;
    }

    public static List<Item> knives() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<Item> holder : KNIVES.values()) {
            items.add(holder.get());
        }
        return items;
    }

    public static Item fluxKnife() {
        return fluxKnife.get();
    }

    private static Item.Properties fluxKnifeProperties() {
        return new Item.Properties().rarity(FluxKnife.RARITY).attributes(fluxKnifeAttributes());
    }

    private static ItemAttributeModifiers fluxKnifeAttributes() {
        return DiggerItem.createAttributes(FluxKnife.INSTANCE, KNIFE_BASE_DAMAGE, FarmersDelightTaintedConfig.fluxKnifeAttackSpeed());
    }

    private static Item.Properties foodProperties(FarmersDelightFood food, FoodProperties placeholder) {
        Item.Properties properties = new Item.Properties().food(placeholder);
        if (food.bowl()) {
            properties.craftRemainder(Items.BOWL);
        }
        return properties;
    }

    private static Item.Properties knifeProperties(FarmersDelightKnife knife) {
        return new Item.Properties().rarity(knife.rarity()).attributes(attributes(knife));
    }

    private static ItemAttributeModifiers attributes(FarmersDelightKnife knife) {
        return DiggerItem.createAttributes(knife, KNIFE_BASE_DAMAGE, FarmersDelightTaintedConfig.attackSpeed(knife));
    }

    private static void onModifyDefaults(ModifyDefaultComponentsEvent event) {
        modifyFluxKnife(event);
        for (Map.Entry<FarmersDelightFood, DeferredItem<Item>> entry : FOODS.entrySet()) {
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
            int stackSize = components.getOrDefault(DataComponents.MAX_STACK_SIZE, 64);
            Rarity rarity = components.get(DataComponents.RARITY);
            event.modify(entry.getValue().get(), builder -> {
                builder.set(DataComponents.FOOD, tainted);
                builder.set(DataComponents.MAX_STACK_SIZE, stackSize);
                if (rarity != null) {
                    builder.set(DataComponents.RARITY, rarity);
                }
            });
        }
        for (Map.Entry<FarmersDelightKnife, DeferredItem<Item>> entry : KNIVES.entrySet()) {
            FarmersDelightKnife knife = entry.getKey();
            ItemAttributeModifiers attributes = attributes(knife);
            Tool tool = knife.createToolProperties(ModTags.Blocks.MINEABLE_WITH_KNIFE);
            int durability = FarmersDelightTaintedConfig.durability(knife);
            event.modify(entry.getValue().get(), builder -> {
                builder.set(DataComponents.MAX_DAMAGE, durability);
                builder.set(DataComponents.ATTRIBUTE_MODIFIERS, attributes);
                builder.set(DataComponents.TOOL, tool);
            });
        }
    }

    private static void modifyFluxKnife(ModifyDefaultComponentsEvent event) {
        if (fluxKnife == null) {
            return;
        }
        ItemAttributeModifiers attributes = fluxKnifeAttributes();
        Tool tool = FluxKnife.INSTANCE.createToolProperties(ModTags.Blocks.MINEABLE_WITH_KNIFE);
        int durability = FarmersDelightTaintedConfig.fluxKnifeDurability();
        event.modify(fluxKnife.get(), builder -> {
            builder.set(DataComponents.MAX_DAMAGE, durability);
            builder.set(DataComponents.ATTRIBUTE_MODIFIERS, attributes);
            builder.set(DataComponents.TOOL, tool);
        });
    }

    private static FoodProperties taint(FoodProperties food) {
        int nutrition = Math.max(1, Math.round(food.nutrition() * FarmersDelightTaintedConfig.hungerMultiplier()));
        float saturation = food.saturation() * FarmersDelightTaintedConfig.saturationMultiplier();
        List<FoodProperties.PossibleEffect> effects = new ArrayList<>(food.effects());
        if (FarmersDelightTaintedConfig.foodEffectDuration() > 0 && FarmersDelightTaintedConfig.foodEffectChance() > 0.0F) {
            effects.add(new FoodProperties.PossibleEffect(
                    () -> FarmersDelightTaintedEffect.create(
                            FarmersDelightTaintedConfig.foodEffectId(),
                            FarmersDelightTaintedConfig.foodEffectDuration(),
                            FarmersDelightTaintedConfig.foodEffectAmplifier()),
                    FarmersDelightTaintedConfig.foodEffectChance()));
        }
        return new FoodProperties(nutrition, saturation, food.canAlwaysEat(), food.eatSeconds(), food.usingConvertsTo(), effects);
    }
}
