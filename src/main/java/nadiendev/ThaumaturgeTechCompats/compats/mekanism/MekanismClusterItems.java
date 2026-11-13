package nadiendev.ThaumaturgeTechCompats.compats.mekanism;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.common.ClusterMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MekanismClusterItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<ClusterMaterial, DeferredItem<Item>> HOLDERS = new LinkedHashMap<>();

    private MekanismClusterItems() {
    }

    public static void register(IEventBus modBus) {
        for (ClusterMaterial material : ClusterMaterial.ownMaterials()) {
            HOLDERS.put(material, ITEMS.registerSimpleItem(material.itemName()));
        }
        ITEMS.register(modBus);
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<Item> holder : HOLDERS.values()) {
            items.add(holder.get());
        }
        return items;
    }
}
