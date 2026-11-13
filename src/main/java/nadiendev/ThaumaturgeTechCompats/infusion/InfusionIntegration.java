package nadiendev.ThaumaturgeTechCompats.infusion;

import nadiendev.ThaumaturgeTechCompats.config.CompatToggles;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.mixins.BlockEntityTypeAccessor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class InfusionIntegration {
    public static final String MATRIX_TOGGLE = "infusion_matrix_variants";
    public static final String STONE_TOGGLE = "infusion_stone_variants";
    public static final String ALTAR_TOGGLE = "infusion_altar_variants";

    private static final ResourceKey<BlockEntityType<?>> MATRIX_ENTITY = ResourceKey.create(
            Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath("thaumaturge", "infusion_matrix"));
    private static final ResourceKey<BlockEntityType<?>> PEDESTAL_ENTITY = ResourceKey.create(
            Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath("thaumaturge", "pedestal"));

    private InfusionIntegration() {
    }

    public static boolean matricesEnabled() {
        return CompatToggles.enabled(MATRIX_TOGGLE);
    }

    public static boolean stonesEnabled() {
        return CompatToggles.enabled(STONE_TOGGLE);
    }

    public static boolean altarEnabled() {
        return CompatToggles.enabled(ALTAR_TOGGLE);
    }

    public static void register(IEventBus modBus) {
        boolean matrices = matricesEnabled();
        boolean stones = stonesEnabled();
        boolean altar = altarEnabled();
        if (!matrices && !stones && !altar) {
            return;
        }
        InfusionRegistry.register(modBus, matrices, stones, altar);
        if (matrices) {
            InfusionInstabilities.register(modBus);
        }
        if (matrices || altar) {
            modBus.addListener(InfusionIntegration::addValidBlocks);
        }
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, InfusionConfig.SPEC, InfusionConfig.FILE_NAME);
    }

    public static List<Block> blocks() {
        return InfusionRegistry.blocks();
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        for (Item item : InfusionRegistry.items()) {
            output.accept(item);
        }
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new InfusionAssets(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new InfusionContent(output));
    }

    private static void addValidBlocks(BlockEntityTypeAddBlocksEvent event) {
        addToFinal(MATRIX_ENTITY, InfusionRegistry.matrixBlocks());
        addTo(event, PEDESTAL_ENTITY, InfusionRegistry.altarBlocks(AltarPart.PEDESTAL));
    }

    private static void addToFinal(ResourceKey<BlockEntityType<?>> key, List<Block> blocks) {
        if (blocks.isEmpty()) {
            return;
        }
        BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional(key).ifPresent(type -> {
            Set<Block> valid = new HashSet<>(type.getValidBlocks());
            valid.addAll(blocks);
            ((BlockEntityTypeAccessor) type).neoforge$setValidBlocks(valid);
        });
    }

    private static void addTo(BlockEntityTypeAddBlocksEvent event, ResourceKey<BlockEntityType<?>> key, List<Block> blocks) {
        if (blocks.isEmpty()) {
            return;
        }
        event.modify(key, blocks.toArray(Block[]::new));
    }
}
