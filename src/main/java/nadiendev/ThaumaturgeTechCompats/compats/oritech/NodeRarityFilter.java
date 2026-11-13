package nadiendev.ThaumaturgeTechCompats.compats.oritech;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class NodeRarityFilter extends PlacementFilter {
    public static final MapCodec<NodeRarityFilter> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("node").forGetter(filter -> filter.node)
    ).apply(instance, NodeRarityFilter::new));

    private final String node;

    public NodeRarityFilter(String node) {
        this.node = node;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        OritechNode found = OritechNode.byId(node);
        int rarity = found == null ? 400 : OritechConfig.nodeRarity(found);
        return random.nextFloat() < 1.0F / rarity;
    }

    @Override
    public PlacementModifierType<?> type() {
        return OritechRegistry.NODE_RARITY.get();
    }
}
