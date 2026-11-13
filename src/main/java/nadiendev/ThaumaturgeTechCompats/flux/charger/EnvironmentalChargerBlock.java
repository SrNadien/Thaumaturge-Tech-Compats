package nadiendev.ThaumaturgeTechCompats.flux.charger;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import nadiendev.ThaumaturgeTechCompats.flux.FluxConfig;
import nadiendev.ThaumaturgeTechCompats.flux.FluxTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.text.NumberFormat;

public class EnvironmentalChargerBlock extends BaseEntityBlock {
    public static final String MESSAGE = "message.thaumaturgetechcompats.environmental_charger.";
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final MapCodec<EnvironmentalChargerBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(),
            FluxTier.CODEC.fieldOf("tier").forGetter(EnvironmentalChargerBlock::tier)
    ).apply(instance, EnvironmentalChargerBlock::new));

    private final FluxTier tier;

    public EnvironmentalChargerBlock(Properties properties, FluxTier tier) {
        super(properties);
        this.tier = tier;
        registerDefaultState(stateDefinition.any().setValue(ACTIVE, false));
    }

    public FluxTier tier() {
        return tier;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnvironmentalChargerBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(type, ChargerRegistry.ENTITY.get(), EnvironmentalChargerBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof EnvironmentalChargerBlockEntity charger) {
            if (!level.isClientSide) {
                NumberFormat format = NumberFormat.getIntegerInstance();
                player.displayClientMessage(Component.translatable(MESSAGE + "status", getName(), Component.translatable(MESSAGE + "status." + charger.status(level).id())), false);
                player.displayClientMessage(Component.translatable(MESSAGE + "flux", format.format(charger.storage().stored()), format.format(charger.storage().capacity())), false);
                player.displayClientMessage(Component.translatable(MESSAGE + "area", FluxConfig.chargerRadius(tier), format.format(FluxConfig.chargerRate(tier))), false);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(ACTIVE) || !ChargerConfig.particles()) {
            return;
        }
        DustParticleOptions dust = new DustParticleOptions(Vec3.fromRGB24(tier.color()).toVector3f(), 0.8F);
        for (int i = 0; i < 2; i++) {
            double x = pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 2.2D;
            double y = pos.getY() + 0.5D + (random.nextDouble() - 0.5D) * 2.2D;
            double z = pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 2.2D;
            level.addParticle(dust, x, y, z, 0.0D, 0.02D, 0.0D);
        }
        if (random.nextInt(6) == 0) {
            level.addParticle(ParticleTypes.END_ROD, pos.getX() + 0.5D, pos.getY() + 1.1D, pos.getZ() + 0.5D, 0.0D, 0.03D, 0.0D);
        }
    }
}
