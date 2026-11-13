package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.stack;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedConfig;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public final class StackUpgradeRegistry {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<SophisticatedTier, DeferredItem<TierStackUpgradeItem>> UPGRADES = new EnumMap<>(SophisticatedTier.class);

    static {
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            UPGRADES.put(tier, ITEMS.registerItem(tier.stackUpgradeName(),
                    properties -> new TierStackUpgradeItem(tier, SophisticatedConfig.stackMultiplier(tier), UpgradeLimits.INSTANCE),
                    new Item.Properties()));
        }
    }

    private StackUpgradeRegistry() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        modBus.addListener(StackUpgradeRegistry::applyRarity);
    }

    private static void applyRarity(ModifyDefaultComponentsEvent event) {
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            event.modify(UPGRADES.get(tier).get(), builder -> builder.set(DataComponents.RARITY, tier.rarity()));
        }
    }

    public static Item item(SophisticatedTier tier) {
        return UPGRADES.get(tier).get();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (SophisticatedTier tier : SophisticatedTier.values()) {
            output.accept(UPGRADES.get(tier).get());
        }
    }
}
