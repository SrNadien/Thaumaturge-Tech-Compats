package nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane;

import com.leclowndu93150.thaumaturge.api.recipe.RegisterWorkbenchAuraSourcesEvent;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.SophisticatedIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.stack.UpgradeLimits;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerRegistry;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;

public final class ArcaneUpgradeRegistry {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredItem<ArcaneUpgradeItem> UPGRADE = ITEMS.registerItem(SophisticatedIntegration.ARCANE_UPGRADE_ITEM,
            properties -> new ArcaneUpgradeItem(UpgradeLimits.INSTANCE), new Item.Properties());
    private static final UpgradeContainerType<ArcaneUpgradeWrapper, ArcaneUpgradeContainer> TYPE = new UpgradeContainerType<>(ArcaneUpgradeContainer::new);

    private ArcaneUpgradeRegistry() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        modBus.addListener(ArcaneUpgradeRegistry::registerContainer);
        modBus.addListener(ArcaneUpgradeRegistry::registerAuraSource);
        modBus.addListener(ArcaneUpgradeRegistry::applyRarity);
    }

    private static void applyRarity(ModifyDefaultComponentsEvent event) {
        event.modify(UPGRADE.get(), builder -> builder.set(DataComponents.RARITY, Rarity.RARE));
    }

    public static Item item() {
        return UPGRADE.get();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        output.accept(UPGRADE.get());
    }

    private static void registerContainer(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.MENU)) {
            return;
        }
        UpgradeContainerRegistry.register(UPGRADE.getId(), TYPE);
        if (FMLEnvironment.dist.isClient()) {
            ArcaneUpgradeClient.registerTab(TYPE);
        }
    }

    private static void registerAuraSource(RegisterWorkbenchAuraSourcesEvent event) {
        event.register(new ArcaneUpgradeAuraSource());
    }
}
