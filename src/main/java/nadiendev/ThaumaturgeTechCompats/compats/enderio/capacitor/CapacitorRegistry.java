package nadiendev.ThaumaturgeTechCompats.compats.enderio.capacitor;

import com.enderio.enderio.content.capacitors.CapacitorItem;
import com.enderio.enderio.init.EIODataComponents;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.enderio.EnderIOTier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public final class CapacitorRegistry {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<EnderIOTier, DeferredItem<CapacitorItem>> CAPACITORS = new EnumMap<>(EnderIOTier.class);

    static {
        for (EnderIOTier tier : EnderIOTier.values()) {
            CAPACITORS.put(tier, ITEMS.registerItem(tier.capacitorName(),
                    properties -> new CapacitorItem(properties.component(EIODataComponents.CAPACITOR_DATA, CapacitorConfig.data(tier))),
                    () -> new Item.Properties().rarity(tier.rarity())));
        }
    }

    private CapacitorRegistry() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (EnderIOTier tier : EnderIOTier.values()) {
            output.accept(CAPACITORS.get(tier).get());
        }
    }
}
