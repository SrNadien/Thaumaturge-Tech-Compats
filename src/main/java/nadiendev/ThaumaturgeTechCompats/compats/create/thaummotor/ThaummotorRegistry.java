package nadiendev.ThaumaturgeTechCompats.compats.create.thaummotor;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ThaummotorRegistry {
    public static final String NAME = "thaummotor";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumaturgeTechCompats.MOD_ID);

    public static final DeferredBlock<ThaummotorBlock> THAUMMOTOR = BLOCKS.registerBlock(
            NAME,
            ThaummotorBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(3.5F, 6.0F)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .noOcclusion());

    public static final DeferredItem<BlockItem> THAUMMOTOR_ITEM = ITEMS.registerSimpleBlockItem(THAUMMOTOR);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ThaummotorBlockEntity>> THAUMMOTOR_ENTITY = BLOCK_ENTITIES.register(
            NAME,
            (Supplier<BlockEntityType<ThaummotorBlockEntity>>) () -> BlockEntityType.Builder.of(ThaummotorBlockEntity::new, THAUMMOTOR.get()).build(null));

    private ThaummotorRegistry() {
    }
}
