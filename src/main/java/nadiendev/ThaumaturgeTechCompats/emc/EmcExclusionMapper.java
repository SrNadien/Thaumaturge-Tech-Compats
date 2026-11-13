package nadiendev.ThaumaturgeTechCompats.emc;

import com.yaskulsky.equivox.api.mapper.EMCMapper;
import com.yaskulsky.equivox.api.mapper.IEMCMapper;
import com.yaskulsky.equivox.api.mapper.collector.IMappingCollector;
import com.yaskulsky.equivox.api.nss.NSSItem;
import com.yaskulsky.equivox.api.nss.NormalizedSimpleStack;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;

import java.util.Optional;

@EMCMapper(requiredMods = {"thaumaturge"})
public class EmcExclusionMapper implements IEMCMapper<NormalizedSimpleStack, Long> {
    @Override
    public String getName() {
        return "Thaumaturge Tech Compats exclusions";
    }

    @Override
    public String getTranslationKey() {
        return EmcIntegration.EXCLUSION_MAPPER_KEY;
    }

    @Override
    public String getDescription() {
        return "Removes the EMC of the items in the thaumaturgetechcompats:no_emc tag: creative and unbreakable items and wands and jars that hold a charge or contents.";
    }

    @Override
    public boolean isAvailable() {
        return EmcIntegration.enabled();
    }

    @Override
    public void addMappings(IMappingCollector<NormalizedSimpleStack, Long> mapper, ReloadableServerResources serverResources, RegistryAccess registryAccess, ResourceManager resourceManager) {
        if (!EmcIntegration.enabled()) {
            return;
        }
        Optional<HolderSet.Named<Item>> tag = BuiltInRegistries.ITEM.get(EmcIntegration.NO_EMC);
        if (tag.isEmpty()) {
            return;
        }
        for (Holder<Item> holder : tag.get()) {
            mapper.setValueAfter(NSSItem.createItem(holder), 0L);
        }
    }
}
