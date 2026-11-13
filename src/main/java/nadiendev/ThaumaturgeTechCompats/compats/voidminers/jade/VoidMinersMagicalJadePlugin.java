package nadiendev.ThaumaturgeTechCompats.compats.voidminers.jade;

import nadiendev.ThaumaturgeTechCompats.compats.voidminers.MagicalMinerBlockEntity;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.MagicalSolarBlockEntity;
import nadiendev.ThaumaturgeTechCompats.compats.voidminers.VoidMinersMagicalIntegration;
import net.minecraft.world.level.block.Block;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class VoidMinersMagicalJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        if (!VoidMinersMagicalIntegration.isLoaded()) {
            return;
        }
        registration.registerBlockDataProvider(VoidMinersMagicalJadeData.INSTANCE, MagicalMinerBlockEntity.class);
        registration.registerBlockDataProvider(VoidMinersMagicalJadeData.INSTANCE, MagicalSolarBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        if (!VoidMinersMagicalIntegration.isLoaded()) {
            return;
        }
        registration.registerBlockComponent(VoidMinersMagicalJadeComponent.INSTANCE, Block.class);
    }
}
