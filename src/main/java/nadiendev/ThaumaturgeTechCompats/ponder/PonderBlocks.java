package nadiendev.ThaumaturgeTechCompats.ponder;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public final class PonderBlocks {
    private PonderBlocks() {
    }

    public static Block block(String id) {
        ResourceLocation key = ResourceLocation.parse(id);
        return BuiltInRegistries.BLOCK.getOptional(key).orElseThrow(() -> new IllegalStateException("Unknown block " + id));
    }

    public static BlockState state(String id, String... properties) {
        Block block = block(id);
        BlockState state = block.defaultBlockState();
        for (int i = 0; i + 1 < properties.length; i += 2) {
            Property<?> property = block.getStateDefinition().getProperty(properties[i]);
            if (property == null) {
                throw new IllegalStateException("Unknown property " + properties[i] + " on " + id);
            }
            state = apply(state, property, properties[i + 1]);
        }
        return state;
    }

    public static ItemStack stack(String id) {
        ResourceLocation key = ResourceLocation.parse(id);
        Item item = BuiltInRegistries.ITEM.getOptional(key).orElseThrow(() -> new IllegalStateException("Unknown item " + id));
        return new ItemStack(item);
    }

    private static <T extends Comparable<T>> BlockState apply(BlockState state, Property<T> property, String value) {
        T parsed = property.getValue(value).orElseThrow(() -> new IllegalStateException("Bad value " + value + " for " + property.getName()));
        return state.setValue(property, parsed);
    }
}
