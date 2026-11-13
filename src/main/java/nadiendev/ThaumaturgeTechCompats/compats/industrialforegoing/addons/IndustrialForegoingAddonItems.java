package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.addons;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class IndustrialForegoingAddonItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<String, Entry> ENTRIES = new LinkedHashMap<>();

    private IndustrialForegoingAddonItems() {
    }

    public static void register(IEventBus modBus) {
        for (AddonTier tier : AddonTier.values()) {
            for (AddonKind kind : AddonKind.values()) {
                if (!CompatToggles.enabled(IndustrialForegoingAddonsIntegration.toggleId(kind, tier))) {
                    continue;
                }
                String id = IndustrialForegoingAddonsIntegration.itemId(kind, tier);
                DeferredItem<Item> holder = ITEMS.register(id, () -> new IndustrialForegoingAddonItem(kind, tier, new Item.Properties().stacksTo(16)));
                ENTRIES.put(id, new Entry(kind, tier, holder));
            }
        }
        ITEMS.register(modBus);
        modBus.addListener(IndustrialForegoingAddonItems::onModifyDefaults);
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (Entry entry : ENTRIES.values()) {
            items.add(entry.holder().get());
        }
        return items;
    }

    private static void onModifyDefaults(ModifyDefaultComponentsEvent event) {
        for (Entry entry : ENTRIES.values()) {
            event.modify(entry.holder().get(), builder -> augment(entry, builder));
        }
    }

    @SuppressWarnings("unchecked")
    private static void augment(Entry entry, DataComponentPatch.Builder builder) {
        Optional<DataComponentType<?>> type = BuiltInRegistries.DATA_COMPONENT_TYPE
                .getOptional(ResourceLocation.parse(IndustrialForegoingAddonsIntegration.TITANIUM_AUGMENTS));
        if (type.isEmpty()) {
            return;
        }
        DataComponentType<Map<String, Float>> augments = (DataComponentType<Map<String, Float>>) type.get();
        float value = IndustrialForegoingAddonsConfig.value(entry.kind(), entry.tier());
        builder.set(augments, Map.of(entry.kind().augment(), value));
    }

    private record Entry(AddonKind kind, AddonTier tier, DeferredItem<Item> holder) {
    }
}
