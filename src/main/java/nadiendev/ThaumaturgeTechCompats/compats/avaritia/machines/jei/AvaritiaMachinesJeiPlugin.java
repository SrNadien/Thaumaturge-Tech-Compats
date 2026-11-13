package nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.jei;

import committee.nova.mods.avaritia.compat.jei.category.CompressorCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.AvaritiaMachinesIntegration;
import nadiendev.ThaumaturgeTechCompats.compats.avaritia.machines.AvaritiaMachinesRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

@JeiPlugin
public class AvaritiaMachinesJeiPlugin implements IModPlugin {
    private static final Identifier ID = ThaumaturgeTechCompats.id("avaritia_machines");
    private static final String COLLECTOR_INFO = "jei.tooltip.avaritia.neutron_collector";

    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (!AvaritiaMachinesIntegration.enabled()) {
            return;
        }
        for (Block compressor : AvaritiaMachinesRegistry.compressors()) {
            registration.addRecipeCatalyst(compressor, CompressorCategory.RECIPE_TYPE);
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (!AvaritiaMachinesIntegration.enabled()) {
            return;
        }
    }
}
