package nadiendev.ThaumaturgeTechCompats.jar;

import com.leclowndu93150.thaumaturge.content.essentia.jar.JarItem;
import com.leclowndu93150.thaumaturge.registry.TTSoundTypes;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;

public final class JarRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumaturgeTechCompats.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumaturgeTechCompats.MOD_ID);

    private static final Map<JarTier, DeferredBlock<EssentiaJarBlock>> JAR_BLOCKS = new EnumMap<>(JarTier.class);
    private static final Map<JarTier, DeferredItem<JarItem>> JAR_ITEMS = new EnumMap<>(JarTier.class);

    public static final DeferredBlock<EssentiaJarBlock> MATRIX_JAR_BLOCK = registerBlock(JarTier.MATRIX);
    public static final DeferredBlock<EssentiaJarBlock> NEUTRONIUM_JAR_BLOCK = registerBlock(JarTier.NEUTRONIUM);
    public static final DeferredBlock<EssentiaJarBlock> INFINITY_JAR_BLOCK = registerBlock(JarTier.INFINITY);

    public static final DeferredItem<JarItem> MATRIX_JAR = registerItem(JarTier.MATRIX);
    public static final DeferredItem<JarItem> NEUTRONIUM_JAR = registerItem(JarTier.NEUTRONIUM);
    public static final DeferredItem<JarItem> INFINITY_JAR = registerItem(JarTier.INFINITY);

    private JarRegistry() {
    }

    public static DeferredBlock<EssentiaJarBlock> block(JarTier tier) {
        return JAR_BLOCKS.get(tier);
    }

    public static DeferredItem<JarItem> item(JarTier tier) {
        return JAR_ITEMS.get(tier);
    }

    private static DeferredBlock<EssentiaJarBlock> registerBlock(JarTier tier) {
        DeferredBlock<EssentiaJarBlock> block = BLOCKS.registerBlock(
                tier.jarName(),
                properties -> new EssentiaJarBlock(properties, tier),
                () -> BlockBehaviour.Properties.of()
                        .mapColor(MapColor.NONE)
                        .strength(0.3F)
                        .sound(TTSoundTypes.JAR.get())
                        .noOcclusion());
        JAR_BLOCKS.put(tier, block);
        return block;
    }

    private static DeferredItem<JarItem> registerItem(JarTier tier) {
        Item.Properties properties = new Item.Properties().rarity(tier.rarity());
        if (tier.fireResistant()) {
            properties.fireResistant();
        }
        DeferredBlock<EssentiaJarBlock> block = JAR_BLOCKS.get(tier);
        DeferredItem<JarItem> item = ITEMS.registerItem(tier.jarName(), itemProperties -> new JarItem(block.get(), itemProperties), () -> properties);
        JAR_ITEMS.put(tier, item);
        return item;
    }
}
