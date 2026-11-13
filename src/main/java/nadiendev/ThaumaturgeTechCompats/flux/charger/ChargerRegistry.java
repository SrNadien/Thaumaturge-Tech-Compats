package nadiendev.ThaumaturgeTechCompats.flux.charger;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ChargerRegistry {
    public static final String NAME = "environmental_charger";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);

    private static final Map<FluxTier, DeferredBlock<EnvironmentalChargerBlock>> CHARGERS = new EnumMap<>(FluxTier.class);
    private static final Map<FluxTier, DeferredItem<ChargerItem>> CHARGER_ITEMS = new EnumMap<>(FluxTier.class);

    static {
        for (FluxTier tier : FluxTier.values()) {
            DeferredBlock<EnvironmentalChargerBlock> block = BLOCKS.registerBlock(blockName(tier), properties -> new EnvironmentalChargerBlock(properties, tier), () -> BlockBehaviour.Properties.of()
                    .mapColor(tier == FluxTier.ALCHEMICAL_BRASS ? MapColor.GOLD : MapColor.COLOR_PURPLE)
                    .strength(3.5F + tier.ordinal(), 15.0F * (tier.ordinal() + 1))
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(EnvironmentalChargerBlock.ACTIVE) ? 8 : 0));
            CHARGERS.put(tier, block);
            CHARGER_ITEMS.put(tier, ITEMS.registerItem(blockName(tier), properties -> new ChargerItem(block.get(), properties.useBlockDescriptionPrefix().rarity(tier.rarity()))));
        }
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnvironmentalChargerBlockEntity>> ENTITY = BLOCK_ENTITIES.register(NAME,
            () -> new BlockEntityType<>(EnvironmentalChargerBlockEntity::new, blocks().toArray(Block[]::new)));

    private ChargerRegistry() {
    }

    public static String blockName(FluxTier tier) {
        return tier.id() + "_" + NAME;
    }

    public static DeferredBlock<EnvironmentalChargerBlock> block(FluxTier tier) {
        return CHARGERS.get(tier);
    }

    public static DeferredItem<ChargerItem> item(FluxTier tier) {
        return CHARGER_ITEMS.get(tier);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (FluxTier tier : FluxTier.values()) {
            blocks.add(CHARGERS.get(tier).get());
        }
        return blocks;
    }
}
