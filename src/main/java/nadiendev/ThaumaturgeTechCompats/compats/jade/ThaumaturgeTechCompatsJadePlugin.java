package nadiendev.ThaumaturgeTechCompats.compats.jade;

import net.minecraft.world.level.block.Block;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ThaumaturgeTechCompatsJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(TierDataProvider.INSTANCE, Block.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        for (JadeFamily family : JadeFamily.values()) {
            if (family.configurable()) {
                registration.addConfig(family.optionId(), true);
            }
        }
        registration.registerBlockComponent(TierComponentProvider.INSTANCE, Block.class);
    }
}
