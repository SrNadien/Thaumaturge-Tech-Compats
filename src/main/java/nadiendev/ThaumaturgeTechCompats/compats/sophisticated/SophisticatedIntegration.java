package nadiendev.ThaumaturgeTechCompats.compats.sophisticated;

import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.arcane.ArcaneUpgradeRegistry;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.backpack.BackpackTierRegistry;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.stack.StackUpgradeRegistry;
import nadiendev.ThaumaturgeTechCompats.compats.sophisticated.storage.StorageTierRegistry;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

public final class SophisticatedIntegration {
    public static final String CORE_ID = "sophisticatedcore";
    public static final String BACKPACKS_ID = "sophisticatedbackpacks";
    public static final String STORAGE_ID = "sophisticatedstorage";
    public static final String STACK_UPGRADES = "sophisticated_stack_upgrades";
    public static final String ARCANE_UPGRADE = "sophisticated_arcane_upgrade";
    public static final String BACKPACK_TIERS = "sophisticated_backpack_tiers";
    public static final String STORAGE_TIERS = "sophisticated_storage_tiers";
    public static final String WIDE_GUI = "sophisticated_wide_gui";
    public static final String ARCANE_UPGRADE_ITEM = "arcane_workbench_upgrade";

    private SophisticatedIntegration() {
    }

    public static boolean isLoaded(String modId) {
        return ModList.get() != null && ModList.get().isLoaded(modId);
    }

    public static boolean upgradeHostLoaded() {
        return isLoaded(CORE_ID) && (isLoaded(BACKPACKS_ID) || isLoaded(STORAGE_ID));
    }

    public static boolean stackActive() {
        return upgradeHostLoaded() && CompatToggles.enabled(STACK_UPGRADES);
    }

    public static boolean arcaneActive() {
        return upgradeHostLoaded() && CompatToggles.enabled(ARCANE_UPGRADE);
    }

    public static boolean backpackActive() {
        return isLoaded(CORE_ID) && isLoaded(BACKPACKS_ID) && CompatToggles.enabled(BACKPACK_TIERS);
    }

    public static boolean storageActive() {
        return isLoaded(CORE_ID) && isLoaded(STORAGE_ID) && CompatToggles.enabled(STORAGE_TIERS);
    }

    public static boolean wideGuiActive() {
        return isLoaded(CORE_ID) && CompatToggles.enabled(WIDE_GUI);
    }

    public static List<String> toggleIds() {
        return List.of(STACK_UPGRADES, ARCANE_UPGRADE, BACKPACK_TIERS, STORAGE_TIERS, WIDE_GUI);
    }

    public static void register(IEventBus modBus) {
        boolean stack = stackActive();
        boolean arcane = arcaneActive();
        boolean backpacks = backpackActive();
        boolean storage = storageActive();
        if (!stack && !arcane && !backpacks && !storage) {
            return;
        }
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        container.registerConfig(ModConfig.Type.STARTUP, SophisticatedConfig.SPEC, SophisticatedConfig.FILE_NAME);
        if (stack) {
            StackUpgradeRegistry.register(modBus);
        }
        if (arcane) {
            ArcaneUpgradeRegistry.register(modBus);
        }
        if (backpacks) {
            BackpackTierRegistry.register(modBus);
        }
        if (storage) {
            StorageTierRegistry.register(modBus);
        }
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (stackActive()) {
            StackUpgradeRegistry.fillCreativeTab(output);
        }
        if (arcaneActive()) {
            ArcaneUpgradeRegistry.fillCreativeTab(output);
        }
        if (backpackActive()) {
            BackpackTierRegistry.fillCreativeTab(output);
        }
        if (storageActive()) {
            StorageTierRegistry.fillCreativeTab(output);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        if (!isLoaded(CORE_ID)) {
            return;
        }
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new SophisticatedAssets(output));
        generator.addProvider(event.includeServer(), new SophisticatedContent(output));
    }
}
