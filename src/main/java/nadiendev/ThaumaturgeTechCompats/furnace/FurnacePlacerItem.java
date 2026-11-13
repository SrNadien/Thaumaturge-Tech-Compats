package nadiendev.ThaumaturgeTechCompats.furnace;

import com.leclowndu93150.thaumaturge.api.recipe.Blueprint;
import com.leclowndu93150.thaumaturge.content.recipe.dust.ItemMultiblockPlacer;
import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class FurnacePlacerItem extends ItemMultiblockPlacer {
    private final ResourceKey<Blueprint> blueprint;

    public FurnacePlacerItem(Block block, String blueprintName, Item.Properties properties) {
        super(block, properties);
        this.blueprint = ResourceKey.create(Blueprint.REGISTRY_KEY, ThaumaturgeTechCompats.id(blueprintName));
    }

    @Override
    protected ResourceKey<Blueprint> blueprint() {
        return blueprint;
    }
}
