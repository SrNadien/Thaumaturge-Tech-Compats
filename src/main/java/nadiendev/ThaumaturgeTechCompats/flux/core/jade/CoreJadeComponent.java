package nadiendev.ThaumaturgeTechCompats.flux.core.jade;

import nadiendev.ThaumaturgeTechCompats.compats.jade.JadeConfig;
import nadiendev.ThaumaturgeTechCompats.flux.core.CoreStatus;
import nadiendev.ThaumaturgeTechCompats.flux.core.CoreTooltip;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum CoreJadeComponent implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public ResourceLocation getUid() {
        return CoreJadeData.UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (!JadeConfig.enabled() || !data.contains(CoreJadeData.ROOT)) {
            return;
        }
        CoreStatus status = CoreStatus.fromTag(data.getCompound(CoreJadeData.ROOT));
        for (Component line : CoreTooltip.jadeLines(status)) {
            tooltip.add(line);
        }
    }
}
