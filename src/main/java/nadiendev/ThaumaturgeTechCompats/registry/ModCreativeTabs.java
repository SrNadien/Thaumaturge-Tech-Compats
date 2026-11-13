package nadiendev.ThaumaturgeTechCompats.registry;

import de.dafuqs.fractal.api.CreativeSubTab;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ThaumaturgeTechCompats.MOD_ID);

    private static final Map<CreativeParent, List<CreativeSubTab>> CHILDREN = new EnumMap<>(CreativeParent.class);

    public static final CreativeModeTab MAIN_TAB = create(CreativeParent.MAIN, () -> ModItems.AKASHIC_RECORD.get());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = register(CreativeParent.MAIN, MAIN_TAB);

    private ModCreativeTabs() {
    }

    private static CreativeModeTab create(CreativeParent parent, Supplier<? extends ItemLike> icon) {
        CreativeModeTab tab = CreativeModeTab.builder()
                .title(Component.translatable(parent.key()))
                .icon(() -> new ItemStack(icon.get()))
                .displayItems((parameters, output) -> {
                    output.accept(icon.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                    for (CreativeSubTab subTab : CHILDREN.get(parent)) {
                        subTab.buildContents(parameters);
                        output.acceptAll(subTab.getSearchTabDisplayItems(), CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
                    }
                })
                .build();
        CHILDREN.put(parent, ModCreativeSubTabs.build(parent, tab));
        return tab;
    }

    private static DeferredHolder<CreativeModeTab, CreativeModeTab> register(CreativeParent parent, CreativeModeTab tab) {
        if (CHILDREN.get(parent).isEmpty()) {
            return null;
        }
        return TABS.register(parent.id(), () -> tab);
    }
}
