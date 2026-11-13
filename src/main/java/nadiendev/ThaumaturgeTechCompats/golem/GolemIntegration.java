package nadiendev.ThaumaturgeTechCompats.golem;

import com.leclowndu93150.thaumaturge.api.golems.parts.GolemMaterial;
import com.leclowndu93150.thaumaturge.content.golem.GolemProperties;
import com.leclowndu93150.thaumaturge.registry.TTDataComponents;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import nadiendev.ThaumaturgeTechCompats.golem.datagen.GolemContentProvider;
import nadiendev.ThaumaturgeTechCompats.golem.datagen.GolemItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class GolemIntegration {
    private GolemIntegration() {
    }

    public static void register(IEventBus modBus) {
        GolemItems.ITEMS.register(modBus);
        GolemMaterials.MATERIALS.register(modBus);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        output.accept(GolemItems.MATRIX_PLATE.get());
        output.accept(GolemItems.NEUTRONIUM_PLATE.get());
        output.accept(GolemItems.INFINITY_PLATE.get());
        output.accept(placer(GolemMaterials.MATRIX.get()));
        output.accept(placer(GolemMaterials.NEUTRONIUM.get()));
        output.accept(placer(GolemMaterials.INFINITY.get()));
    }

    private static ItemStack placer(GolemMaterial material) {
        ItemStack stack = new ItemStack(TTItems.GOLEM_PLACER.get());
        stack.set(TTDataComponents.GOLEM_PROPERTIES.get(), GolemProperties.createDefault().withMaterial(material));
        return stack;
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new GolemItemModelProvider(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new GolemContentProvider(output));
    }
}
