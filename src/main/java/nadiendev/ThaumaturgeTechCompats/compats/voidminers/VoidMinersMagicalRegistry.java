package nadiendev.ThaumaturgeTechCompats.compats.voidminers;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class VoidMinersMagicalRegistry {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    private static final Map<VoidMinersMagicalTier, DeferredBlock<MagicalMinerBlock>> MINERS = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, DeferredBlock<MagicalFrameBlock>> MINER_FRAMES = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, DeferredBlock<MagicalSolarBlock>> SOLARS = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, DeferredBlock<MagicalFrameBlock>> SOLAR_FRAMES = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final Map<VoidMinersMagicalTier, Map<MagicalUpgrade, DeferredBlock<MagicalModifierBlock>>> UPGRADES = new EnumMap<>(VoidMinersMagicalTier.class);
    private static final List<DeferredItem<Item>> ITEM_LIST = new ArrayList<>();

    private VoidMinersMagicalRegistry() {
    }

    public static void register(IEventBus modBus) {
        for (VoidMinersMagicalTier tier : VoidMinersMagicalTier.values()) {
            if (VoidMinersMagicalIntegration.minerTierEnabled(tier)) {
                DeferredBlock<MagicalMinerBlock> miner = BLOCKS.registerBlock(tier.minerBlock(), properties -> new MagicalMinerBlock(properties, tier), () -> controllerProperties());
                DeferredBlock<MagicalFrameBlock> frame = BLOCKS.registerBlock(tier.minerFrameBlock(), properties -> new MagicalFrameBlock(properties, tier), () -> frameProperties());
                MINERS.put(tier, miner);
                MINER_FRAMES.put(tier, frame);
                addItem(tier, tier.minerBlock(), miner);
                addItem(tier, tier.minerFrameBlock(), frame);
            }
            if (VoidMinersMagicalIntegration.solarTierEnabled(tier)) {
                DeferredBlock<MagicalSolarBlock> solar = BLOCKS.registerBlock(tier.solarBlock(), properties -> new MagicalSolarBlock(properties, tier), () -> controllerProperties());
                DeferredBlock<MagicalFrameBlock> frame = BLOCKS.registerBlock(tier.solarFrameBlock(), properties -> new MagicalFrameBlock(properties, tier), () -> frameProperties());
                SOLARS.put(tier, solar);
                SOLAR_FRAMES.put(tier, frame);
                addItem(tier, tier.solarBlock(), solar);
                addItem(tier, tier.solarFrameBlock(), frame);
            }
            registerUpgrades(tier, false, VoidMinersMagicalIntegration.minerUpgradesEnabled(tier));
            registerUpgrades(tier, true, VoidMinersMagicalIntegration.solarUpgradesEnabled(tier));
        }
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
    }

    private static void registerUpgrades(VoidMinersMagicalTier tier, boolean solar, boolean enabled) {
        if (!enabled) {
            return;
        }
        Map<MagicalUpgrade, DeferredBlock<MagicalModifierBlock>> blocks = UPGRADES.computeIfAbsent(tier, key -> new EnumMap<>(MagicalUpgrade.class));
        for (MagicalUpgrade upgrade : MagicalUpgrade.of(solar)) {
            DeferredBlock<MagicalModifierBlock> block = BLOCKS.registerBlock(upgrade.blockName(tier), properties -> new MagicalModifierBlock(properties, tier, upgrade), () -> frameProperties());
            blocks.put(upgrade, block);
            addItem(tier, upgrade.blockName(tier), block);
        }
    }

    private static void addItem(VoidMinersMagicalTier tier, String name, DeferredBlock<? extends Block> block) {
        ITEM_LIST.add(ITEMS.registerItem(name, properties -> new MagicalTierItem(block.get(), properties.useBlockDescriptionPrefix().rarity(tier.rarity()), tier)));
    }

    private static BlockBehaviour.Properties controllerProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_PURPLE)
                .strength(5.0F, 6.0F)
                .requiresCorrectToolForDrops()
                .noOcclusion();
    }

    private static BlockBehaviour.Properties frameProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_PURPLE)
                .strength(5.0F, 6.0F)
                .requiresCorrectToolForDrops();
    }

    public static Block miner(VoidMinersMagicalTier tier) {
        DeferredBlock<MagicalMinerBlock> block = MINERS.get(tier);
        return block == null ? null : block.get();
    }

    public static Block minerFrame(VoidMinersMagicalTier tier) {
        DeferredBlock<MagicalFrameBlock> block = MINER_FRAMES.get(tier);
        return block == null ? null : block.get();
    }

    public static Block solar(VoidMinersMagicalTier tier) {
        DeferredBlock<MagicalSolarBlock> block = SOLARS.get(tier);
        return block == null ? null : block.get();
    }

    public static Block solarFrame(VoidMinersMagicalTier tier) {
        DeferredBlock<MagicalFrameBlock> block = SOLAR_FRAMES.get(tier);
        return block == null ? null : block.get();
    }

    public static Block upgrade(VoidMinersMagicalTier tier, MagicalUpgrade upgrade) {
        Map<MagicalUpgrade, DeferredBlock<MagicalModifierBlock>> blocks = UPGRADES.get(tier);
        DeferredBlock<MagicalModifierBlock> block = blocks == null ? null : blocks.get(upgrade);
        return block == null ? null : block.get();
    }

    public static List<Block> controllers() {
        List<Block> blocks = new ArrayList<>();
        for (DeferredBlock<MagicalMinerBlock> block : MINERS.values()) {
            blocks.add(block.get());
        }
        for (DeferredBlock<MagicalSolarBlock> block : SOLARS.values()) {
            blocks.add(block.get());
        }
        return blocks;
    }

    public static List<Block> blocks() {
        List<Block> blocks = controllers();
        for (DeferredBlock<MagicalFrameBlock> block : MINER_FRAMES.values()) {
            blocks.add(block.get());
        }
        for (DeferredBlock<MagicalFrameBlock> block : SOLAR_FRAMES.values()) {
            blocks.add(block.get());
        }
        for (Map<MagicalUpgrade, DeferredBlock<MagicalModifierBlock>> upgrades : UPGRADES.values()) {
            for (DeferredBlock<MagicalModifierBlock> block : upgrades.values()) {
                blocks.add(block.get());
            }
        }
        return blocks;
    }

    public static List<Item> items() {
        List<Item> items = new ArrayList<>();
        for (DeferredItem<Item> item : ITEM_LIST) {
            items.add(item.get());
        }
        return items;
    }
}
