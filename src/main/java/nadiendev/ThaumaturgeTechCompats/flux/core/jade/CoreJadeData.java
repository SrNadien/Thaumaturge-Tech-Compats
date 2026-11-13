package nadiendev.ThaumaturgeTechCompats.flux.core.jade;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.core.StorageCoreBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public enum CoreJadeData implements IServerDataProvider<BlockAccessor> {
    INSTANCE;

    public static final Identifier UID = ThaumaturgeTechCompats.id("flux_storage_core");
    public static final String ROOT = "ThaumaturgeTechCompatsFluxCore";

    @Override
    public Identifier getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (accessor.getBlockEntity() instanceof StorageCoreBlockEntity core) {
            data.put(ROOT, core.status().toTag());
        }
    }
}
