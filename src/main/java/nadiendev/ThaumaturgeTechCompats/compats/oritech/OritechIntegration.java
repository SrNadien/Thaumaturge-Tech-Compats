package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import nadiendev.ThaumaturgeTechCompats.compats.oritech.processing.OritechProcessingIntegration;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

import java.util.ArrayList;
import java.util.List;

import static nadiendev.ThaumaturgeTechCompats.tiercontent.ContentJson.MOD;

public final class OritechIntegration {
    private static final ResourceKey<BlockEntityType<?>> ADDON_ENTITY = ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(OritechFeatures.MOD_ID, "addon_entity"));

    private OritechIntegration() {
    }

    public static void register(IEventBus modBus) {
        if (!OritechFeatures.loaded() || !OritechFeatures.master()) {
            return;
        }
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.STARTUP, OritechConfig.SPEC, OritechConfig.FILE_NAME);
        OritechRegistry.registerAll();
        OritechRegistry.BLOCKS.register(modBus);
        OritechRegistry.ITEMS.register(modBus);
        OritechRegistry.PLACEMENTS.register(modBus);
        modBus.addListener(OritechIntegration::addValidBlocks);
        NeoForge.EVENT_BUS.addListener(NodeProduction::onServerStarted);
        NeoForge.EVENT_BUS.addListener(NodeProduction::onDatapackSync);
        if (FMLEnvironment.getDist().isClient()) {
            NeoForge.EVENT_BUS.addListener(NodeProductionClient::onRecipesUpdated);
        }
    }

    public static List<Identifier> blockIds() {
        List<Identifier> ids = new ArrayList<>();
        for (OritechTier tier : OritechTier.values()) {
            for (OritechAddon addon : OritechAddon.values()) {
                ids.add(Identifier.fromNamespaceAndPath(MOD, addon.blockName(tier)));
            }
        }
        return ids;
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        if (!OritechFeatures.loaded() || !OritechFeatures.master()) {
            return;
        }
        for (Item item : OritechRegistry.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new OritechAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new OritechContent(output));
        OritechProcessingIntegration.addProviders(event);
    }

    private static void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        List<Block> blocks = OritechRegistry.addonBlocks();
        if (!blocks.isEmpty()) {
            event.modify(ADDON_ENTITY, blocks.toArray(Block[]::new));
        }
    }
}
