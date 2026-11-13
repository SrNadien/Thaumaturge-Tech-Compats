package nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.magical;

import nadiendev.ThaumaturgeTechCompats.compats.ftbunearthed.FtbUnearthedIntegration;
import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

import java.util.ArrayList;
import java.util.List;

public final class MagicalUneartherIntegration {
    public static final String TOGGLE_BRASS = "ftbunearthed_magical_brass";
    public static final String TOGGLE_THAUMIUM = "ftbunearthed_magical_thaumium";
    public static final String TOGGLE_VOID = "ftbunearthed_magical_void";

    private static final ResourceKey<BlockEntityType<?>> CORE_ENTITY = ResourceKey.create(
            Registries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(FtbUnearthedIntegration.MOD_ID, "core"));

    private MagicalUneartherIntegration() {
    }

    public static List<String> toggleIds() {
        List<String> ids = new ArrayList<>();
        for (MagicalUneartherTier tier : MagicalUneartherTier.values()) {
            ids.add(tier.toggle());
        }
        return ids;
    }

    public static boolean active() {
        return FtbUnearthedIntegration.isLoaded()
                && CompatToggles.enabled(CompatToggles.FTB_UNEARTHED)
                && CompatToggles.enabled(CompatToggles.NODE_POWER);
    }

    public static boolean tierEnabled(MagicalUneartherTier tier) {
        for (MagicalUneartherTier current = tier; current != null; current = current.previous()) {
            if (!CompatToggles.enabled(current.toggle())) {
                return false;
            }
        }
        return active();
    }

    public static void register(IEventBus modBus, ModContainer container) {
        if (!active()) {
            return;
        }
        container.registerConfig(ModConfig.Type.COMMON, MagicalUneartherConfig.SPEC, MagicalUneartherConfig.FILE_NAME);
        MagicalUneartherRegistry.register(modBus);
        modBus.addListener(MagicalUneartherIntegration::addValidBlocks);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!active()) {
            return;
        }
        for (Item item : MagicalUneartherRegistry.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new MagicalUneartherAssets(output));
        generator.addProvider(event.includeServer(), new MagicalUneartherContent(output));
    }

    private static void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        List<Block> blocks = MagicalUneartherRegistry.blocks();
        if (!blocks.isEmpty()) {
            event.modify(CORE_ENTITY, blocks.toArray(Block[]::new));
        }
    }
}
