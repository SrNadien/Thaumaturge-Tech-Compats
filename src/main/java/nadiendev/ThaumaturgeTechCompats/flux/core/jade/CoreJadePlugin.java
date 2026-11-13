package nadiendev.ThaumaturgeTechCompats.flux.core.jade;

import nadiendev.ThaumaturgeTechCompats.flux.core.StorageCoreBlock;
import nadiendev.ThaumaturgeTechCompats.flux.core.StorageCoreBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class CoreJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(CoreJadeData.INSTANCE, StorageCoreBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(CoreJadeComponent.INSTANCE, StorageCoreBlock.class);
    }
}
