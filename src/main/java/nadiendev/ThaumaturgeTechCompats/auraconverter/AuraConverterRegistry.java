package nadiendev.ThaumaturgeTechCompats.auraconverter;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.auraconverter.gui.AuraConverterMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class AuraConverterRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ThaumaturgeTechCompats.MOD_ID);

    private static final Map<AuraConverterTier, DeferredBlock<AuraConverterBlock>> CONVERTERS = new EnumMap<>(AuraConverterTier.class);
    private static final Map<AuraConverterTier, DeferredItem<AuraConverterItem>> CONVERTER_ITEMS = new EnumMap<>(AuraConverterTier.class);

    static {
        for (AuraConverterTier tier : AuraConverterTier.values()) {
            DeferredBlock<AuraConverterBlock> block = BLOCKS.registerBlock(tier.blockName(), properties -> new AuraConverterBlock(properties, tier), () -> BlockBehaviour.Properties.of()
                    .mapColor(tier == AuraConverterTier.BRASS ? MapColor.GOLD : MapColor.COLOR_PURPLE)
                    .strength(3.5F + tier.ordinal(), 15.0F * (tier.ordinal() + 1))
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops());
            CONVERTERS.put(tier, block);
            CONVERTER_ITEMS.put(tier, ITEMS.registerItem(tier.blockName(), properties -> new AuraConverterItem(block.get(), properties.useBlockDescriptionPrefix())));
        }
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AuraConverterBlockEntity>> ENTITY = BLOCK_ENTITIES.register("aura_converter",
            () -> new BlockEntityType<>(AuraConverterBlockEntity::new, blocks().toArray(Block[]::new)));

    public static final DeferredHolder<MenuType<?>, MenuType<AuraConverterMenu>> MENU = MENUS.register("aura_converter",
            () -> IMenuTypeExtension.<AuraConverterMenu>create(AuraConverterMenu::new));

    private AuraConverterRegistry() {
    }

    public static DeferredBlock<AuraConverterBlock> block(AuraConverterTier tier) {
        return CONVERTERS.get(tier);
    }

    public static DeferredItem<AuraConverterItem> item(AuraConverterTier tier) {
        return CONVERTER_ITEMS.get(tier);
    }

    public static List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        for (AuraConverterTier tier : AuraConverterTier.values()) {
            blocks.add(CONVERTERS.get(tier).get());
        }
        return blocks;
    }
}
