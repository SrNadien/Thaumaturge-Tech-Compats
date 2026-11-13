package nadiendev.ThaumaturgeTechCompats.compats.mekanism.tools;

import com.mojang.serialization.Codec;
import mekanism.common.integration.energy.EnergyCompatUtils;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class MekanismToolRegistry {
    public static final String ENERGY_NAME = "mekanism_tool_energy";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Long>> ENERGY = COMPONENTS.registerComponentType(ENERGY_NAME,
            builder -> builder.persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG));

    private static final Map<MekanismToolTier, Map<MekanismToolKind, DeferredItem<Item>>> TOOLS = new EnumMap<>(MekanismToolTier.class);

    private MekanismToolRegistry() {
    }

    public static String itemId(MekanismToolTier tier, MekanismToolKind kind) {
        return "energized_" + tier.id() + "_" + kind.id();
    }

    public static void register(IEventBus modBus) {
        for (MekanismToolTier tier : MekanismToolTier.values()) {
            if (!MekanismToolIntegration.tierEnabled(tier)) {
                continue;
            }
            Map<MekanismToolKind, DeferredItem<Item>> kinds = new EnumMap<>(MekanismToolKind.class);
            for (MekanismToolKind kind : MekanismToolKind.values()) {
                kinds.put(kind, ITEMS.register(itemId(tier, kind), () -> create(tier, kind)));
            }
            TOOLS.put(tier, kinds);
        }
        COMPONENTS.register(modBus);
        ITEMS.register(modBus);
        modBus.addListener(MekanismToolRegistry::onModifyDefaults);
        modBus.addListener(MekanismToolRegistry::onRegisterCapabilities);
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (Map<MekanismToolKind, DeferredItem<Item>> kinds : TOOLS.values()) {
            for (DeferredItem<Item> holder : kinds.values()) {
                items.add(holder.get());
            }
        }
        return items;
    }

    private static Item create(MekanismToolTier tier, MekanismToolKind kind) {
        Item.Properties properties = new Item.Properties().rarity(tier.rarity()).attributes(kind.attributes(tier));
        return switch (kind) {
            case PICKAXE -> new MekanismPickaxeItem(tier, properties);
            case AXE -> new MekanismAxeItem(tier, properties);
            case SHOVEL -> new MekanismShovelItem(tier, properties);
            case HOE -> new MekanismHoeItem(tier, properties);
            case SWORD -> new MekanismSwordItem(tier, properties);
        };
    }

    private static void onModifyDefaults(ModifyDefaultComponentsEvent event) {
        for (Map.Entry<MekanismToolTier, Map<MekanismToolKind, DeferredItem<Item>>> tierEntry : TOOLS.entrySet()) {
            MekanismToolTier tier = tierEntry.getKey();
            for (Map.Entry<MekanismToolKind, DeferredItem<Item>> kindEntry : tierEntry.getValue().entrySet()) {
                MekanismToolKind kind = kindEntry.getKey();
                event.modify(kindEntry.getValue().get(), builder -> builder
                        .remove(DataComponents.MAX_DAMAGE)
                        .remove(DataComponents.DAMAGE)
                        .set(DataComponents.TOOL, kind.tool(tier))
                        .set(DataComponents.ATTRIBUTE_MODIFIERS, kind.attributes(tier)));
            }
        }
    }

    private static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        for (Item item : items()) {
            EnergyCompatUtils.registerItemCapabilities(event, item, (stack, context) -> new MekanismToolHandler(stack));
        }
    }
}
