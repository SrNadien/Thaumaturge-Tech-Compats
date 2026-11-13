package nadiendev.ThaumaturgeTechCompats.focus;

import nadiendev.ThaumaturgeTechCompats.focus.datagen.FocusContentProvider;
import nadiendev.ThaumaturgeTechCompats.focus.datagen.FocusItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class FocusIntegration {
    private FocusIntegration() {
    }

    public static void register(IEventBus modBus) {
        FocusItems.ITEMS.register(modBus);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        output.accept(FocusItems.MATRIX_FOCUS.get());
        output.accept(FocusItems.BLAZE_CUBE_FOCUS.get());
        output.accept(FocusItems.INFINITY_FOCUS.get());
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new FocusItemModelProvider(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new FocusContentProvider(output));
    }
}
