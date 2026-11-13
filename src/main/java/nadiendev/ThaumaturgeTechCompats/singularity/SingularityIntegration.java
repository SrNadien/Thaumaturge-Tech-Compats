package nadiendev.ThaumaturgeTechCompats.singularity;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class SingularityIntegration {
    private static final Identifier SINGULARITY_ITEM = Identifier.fromNamespaceAndPath("avaritia", "singularity");
    private static final Identifier SINGULARITY_COMPONENT = Identifier.fromNamespaceAndPath("avaritia", "singularity_id");

    private SingularityIntegration() {
    }

    public static void addProviders(GatherDataEvent event) {
        event.getGenerator().addProvider(event instanceof GatherDataEvent.Server, new SingularityProvider(event.getGenerator().getPackOutput()));
    }

    @SuppressWarnings("unchecked")
    public static void fillCreativeTab(CreativeModeTab.Output output) {
        Item item = BuiltInRegistries.ITEM.getValue(SINGULARITY_ITEM);
        DataComponentType<?> component = BuiltInRegistries.DATA_COMPONENT_TYPE.getValue(SINGULARITY_COMPONENT);
        if (item == null || component == null || item == Items.AIR) {
            return;
        }
        for (SingularityKind kind : SingularityKind.values()) {
            ItemStack stack = new ItemStack(item);
            stack.set((DataComponentType<Identifier>) component, ThaumaturgeTechCompats.id(kind.id()));
            output.accept(stack);
        }
    }
}
