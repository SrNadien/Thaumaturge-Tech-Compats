package nadiendev.ThaumaturgeTechCompats.compats.botania;

import nadiendev.ThaumaturgeTechCompats.compats.botania.spreader.BotaniaSpreaderAssets;
import nadiendev.ThaumaturgeTechCompats.compats.botania.spreader.BotaniaSpreaderConfig;
import nadiendev.ThaumaturgeTechCompats.compats.botania.spreader.BotaniaSpreaderContent;
import nadiendev.ThaumaturgeTechCompats.compats.botania.spreader.BotaniaSpreaderRegistry;
import nadiendev.ThaumaturgeTechCompats.compats.botania.spreader.SpreaderTier;
import nadiendev.ThaumaturgeTechCompats.compats.botania.wand.BotaniaWandConfig;
import nadiendev.ThaumaturgeTechCompats.compats.botania.wand.BotaniaWandContent;
import nadiendev.ThaumaturgeTechCompats.compats.botania.wand.BotaniaWandItemModels;
import nadiendev.ThaumaturgeTechCompats.compats.botania.wand.BotaniaWandRegistry;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.ModList;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;

public final class BotaniaIntegration {
    public static final String MOD_ID = "botania";
    public static final String TOGGLE = "botania";
    public static final String SPREADERS = "botania_spreaders";
    public static final String WAND = "botania_wand";
    public static final String ASPECTS = "botania_aspects";

    private BotaniaIntegration() {
    }

    public static boolean isLoaded() {
        return ModList.get() != null && ModList.get().isLoaded(MOD_ID);
    }

    public static boolean active() {
        return isLoaded() && CompatToggles.enabled(TOGGLE);
    }

    public static boolean spreadersActive() {
        return active() && CompatToggles.enabled(SPREADERS);
    }

    public static boolean wandActive() {
        return active() && CompatToggles.enabled(WAND);
    }

    public static boolean aspectsActive() {
        return active() && CompatToggles.enabled(ASPECTS);
    }

    public static boolean spreaderTierEnabled(SpreaderTier tier) {
        for (SpreaderTier current = tier; current != null; current = current.previous()) {
            if (!CompatToggles.enabled(current.toggle())) {
                return false;
            }
        }
        return spreadersActive();
    }

    public static List<String> toggleIds() {
        List<String> ids = new ArrayList<>();
        ids.add(TOGGLE);
        ids.add(SPREADERS);
        for (SpreaderTier tier : SpreaderTier.values()) {
            ids.add(tier.toggle());
        }
        ids.add(WAND);
        ids.add(ASPECTS);
        return ids;
    }

    public static void register(IEventBus modBus) {
        if (!active()) {
            return;
        }
        if (spreadersActive()) {
            ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, BotaniaSpreaderConfig.SPEC, BotaniaSpreaderConfig.FILE_NAME);
            BotaniaSpreaderRegistry.register(modBus);
        }
        if (wandActive()) {
            ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.STARTUP, BotaniaWandConfig.SPEC, BotaniaWandConfig.FILE_NAME);
            BotaniaWandRegistry.register(modBus);
        }
    }

    public static List<Block> blocks() {
        return spreadersActive() ? BotaniaSpreaderRegistry.blocks() : List.of();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (spreadersActive()) {
            for (Item item : BotaniaSpreaderRegistry.items()) {
                output.accept(item);
            }
        }
        if (wandActive()) {
            BotaniaWandRegistry.fillCreativeTab(output);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new BotaniaSpreaderAssets(output));
        generator.addProvider(event.includeClient(), new BotaniaWandItemModels(output));
        generator.addProvider(event.includeServer(), new BotaniaSpreaderContent(output));
        generator.addProvider(event.includeServer(), new BotaniaWandContent(output));
    }
}
