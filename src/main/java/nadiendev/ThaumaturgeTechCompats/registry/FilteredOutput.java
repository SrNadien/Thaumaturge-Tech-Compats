package nadiendev.ThaumaturgeTechCompats.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Predicate;

public final class FilteredOutput implements CreativeModeTab.Output {
    private final CreativeModeTab.Output delegate;
    private final Predicate<String> paths;

    public FilteredOutput(CreativeModeTab.Output delegate, Predicate<String> paths) {
        this.delegate = delegate;
        this.paths = paths;
    }

    @Override
    public void accept(ItemStack stack, CreativeModeTab.TabVisibility visibility) {
        if (allowed(stack.getItem())) {
            delegate.accept(stack, visibility);
        }
    }

    @Override
    public void accept(ItemLike item, CreativeModeTab.TabVisibility visibility) {
        if (allowed(item.asItem())) {
            delegate.accept(item, visibility);
        }
    }

    private boolean allowed(ItemLike item) {
        return paths.test(BuiltInRegistries.ITEM.getKey(item.asItem()).getPath());
    }
}
