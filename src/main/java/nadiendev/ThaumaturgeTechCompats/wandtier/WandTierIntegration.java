package nadiendev.ThaumaturgeTechCompats.wandtier;

import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.content.wands.WandVisHelper;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import nadiendev.ThaumaturgeTechCompats.registry.ModWandParts;
import nadiendev.ThaumaturgeTechCompats.wandtier.datagen.WandTierContentProvider;
import nadiendev.ThaumaturgeTechCompats.wandtier.datagen.WandTierItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class WandTierIntegration {
    private WandTierIntegration() {
    }

    public static void register(IEventBus modBus) {
        WandTierItems.ITEMS.register(modBus);
        WandTierParts.RODS.register(modBus);
        WandTierParts.CAPS.register(modBus);
    }

    public static void fillCreativeTab(CreativeModeTab.Output output) {
        output.accept(WandTierItems.MATRIX_WAND_ROD.get());
        output.accept(WandTierItems.MATRIX_WAND_CAP.get());
        output.accept(WandTierItems.NEUTRONIUM_WAND_ROD.get());
        output.accept(WandTierItems.NEUTRONIUM_WAND_CAP.get());
        output.accept(WandTierItems.INFINITY_WAND_CAP.get());

        ItemStack neutronium = ItemWand.create(TTItems.WAND.get(), WandTierParts.NEUTRONIUM_CAP.get(), WandTierParts.NEUTRONIUM_ROD.get(), false);
        WandVisHelper.fill(neutronium);
        output.accept(neutronium);

        ItemStack matrix = ItemWand.create(TTItems.WAND.get(), ModWandParts.MATRIX_CAP.get(), WandTierParts.MATRIX_ROD.get(), false);
        WandVisHelper.fill(matrix);
        output.accept(matrix);
    }

    public static void addProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        generator.addProvider(event instanceof GatherDataEvent.Client, new WandTierItemModelProvider(output));
        generator.addProvider(event instanceof GatherDataEvent.Server, new WandTierContentProvider(output));
    }
}
