package nadiendev.ThaumaturgeTechCompats.singularity;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class SingularityIntegration {
    private static final ResourceLocation SINGULARITY_ITEM = ResourceLocation.fromNamespaceAndPath("avaritia", "singularity");
    private static final ResourceLocation SINGULARITY_COMPONENT = ResourceLocation.fromNamespaceAndPath("avaritia", "singularity_id");

    private SingularityIntegration() {
    }

    public static void addProviders(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), new SingularityProvider(event.getGenerator().getPackOutput()));
    }

    @SuppressWarnings("unchecked")
    public static void fillCreativeTab(CreativeModeTab.Output output) {
        Item item = BuiltInRegistries.ITEM.get(SINGULARITY_ITEM);
        DataComponentType<?> component = BuiltInRegistries.DATA_COMPONENT_TYPE.get(SINGULARITY_COMPONENT);
        if (item == null || component == null || item == Items.AIR) {
            return;
        }
        for (SingularityKind kind : SingularityKind.values()) {
            ItemStack stack = new ItemStack(item);
            stack.set((DataComponentType<ResourceLocation>) component, ThaumaturgeTechCompats.id(kind.id()));
            output.accept(stack);
        }
    }
}
