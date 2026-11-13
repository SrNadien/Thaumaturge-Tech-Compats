package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.module.BlockWithTile;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.LaserMachine;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class MagicalLaserRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);

    private static final DeferredHolder<Block, Block> DRILL_BLOCK = BLOCKS.<Block>register(LaserMachine.DRILL.blockName(), MagicalLaserDrillBlock::new);
    private static final DeferredHolder<Block, Block> ORE_BASE_BLOCK = BLOCKS.<Block>register(LaserMachine.ORE_BASE.blockName(), MagicalOreLaserBaseBlock::new);
    private static final DeferredHolder<Block, Block> FLUID_BASE_BLOCK = BLOCKS.<Block>register(LaserMachine.FLUID_BASE.blockName(), MagicalFluidLaserBaseBlock::new);

    private static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> DRILL_TYPE = BLOCK_ENTITIES.<BlockEntityType<?>>register(LaserMachine.DRILL.blockName(),
            () -> BlockEntityType.Builder.of(MagicalLaserDrillTile::new, DRILL_BLOCK.get()).build(null));
    private static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> ORE_BASE_TYPE = BLOCK_ENTITIES.<BlockEntityType<?>>register(LaserMachine.ORE_BASE.blockName(),
            () -> BlockEntityType.Builder.of(MagicalOreLaserBaseTile::new, ORE_BASE_BLOCK.get()).build(null));
    private static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> FLUID_BASE_TYPE = BLOCK_ENTITIES.<BlockEntityType<?>>register(LaserMachine.FLUID_BASE.blockName(),
            () -> BlockEntityType.Builder.of(MagicalFluidLaserBaseTile::new, FLUID_BASE_BLOCK.get()).build(null));

    public static final BlockWithTile DRILL = new BlockWithTile(DRILL_BLOCK, DRILL_TYPE);
    public static final BlockWithTile ORE_BASE = new BlockWithTile(ORE_BASE_BLOCK, ORE_BASE_TYPE);
    public static final BlockWithTile FLUID_BASE = new BlockWithTile(FLUID_BASE_BLOCK, FLUID_BASE_TYPE);

    private static final DeferredItem<BlockItem> DRILL_ITEM = ITEMS.registerItem(LaserMachine.DRILL.blockName(), properties -> new BlockItem(DRILL_BLOCK.get(), properties));
    private static final DeferredItem<BlockItem> ORE_BASE_ITEM = ITEMS.registerItem(LaserMachine.ORE_BASE.blockName(), properties -> new BlockItem(ORE_BASE_BLOCK.get(), properties));
    private static final DeferredItem<BlockItem> FLUID_BASE_ITEM = ITEMS.registerItem(LaserMachine.FLUID_BASE.blockName(), properties -> new BlockItem(FLUID_BASE_BLOCK.get(), properties));

    private MagicalLaserRegistry() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BLOCK_ENTITIES.register(modBus);
        modBus.addListener(MagicalLaserRegistry::registerCapabilities);
    }

    static BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE);
    }

    public static List<Block> blocks() {
        return List.of(DRILL_BLOCK.get(), ORE_BASE_BLOCK.get(), FLUID_BASE_BLOCK.get());
    }

    public static Item item(LaserMachine machine) {
        return switch (machine) {
            case DRILL -> DRILL_ITEM.get();
            case ORE_BASE -> ORE_BASE_ITEM.get();
            case FLUID_BASE -> FLUID_BASE_ITEM.get();
        };
    }

    @SuppressWarnings("unchecked")
    private static BlockEntityType<BlockEntity> type(DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> holder) {
        return (BlockEntityType<BlockEntity>) holder.get();
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> base : List.of(ORE_BASE_TYPE, FLUID_BASE_TYPE)) {
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, type(base),
                    (entity, side) -> entity instanceof ActiveTile<?> tile ? tile.getItemHandler(side) : null);
        }
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, type(FLUID_BASE_TYPE),
                (entity, side) -> entity instanceof ActiveTile<?> tile ? tile.getFluidHandler(side) : null);
    }
}
