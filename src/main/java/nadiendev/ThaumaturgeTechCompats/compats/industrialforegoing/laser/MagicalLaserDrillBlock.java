package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.buuz135.industrial.block.IndustrialBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.LaserMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MagicalLaserDrillBlock extends IndustrialBlock<MagicalLaserDrillTile> {
    private static final double PARTICLE_SPEED = 10.0D;

    public MagicalLaserDrillBlock() {
        super(LaserMachine.DRILL.blockName(), MagicalLaserRegistry.properties(), MagicalLaserDrillTile.class, null);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<MagicalLaserDrillTile> getTileEntityFactory() {
        return MagicalLaserDrillTile::new;
    }

    @Override
    public RotationType getRotationType() {
        return RotatableBlock.RotationType.SIX_WAY;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!(level.getBlockEntity(pos) instanceof MagicalLaserDrillTile drill) || drill.getTarget().equals(BlockPos.ZERO) || !drill.isSpawnParticles()) {
            return;
        }
        Vec3i normal = drill.getFacingDirection().getOpposite().getNormal();
        BlockPos target = drill.getTarget();
        Vec3 start = new Vec3(pos.getX(), pos.getY(), pos.getZ()).add(0.5D, 0.5D, 0.5D)
                .add(normal.getX() / 2.0D, normal.getY() / 2.0D, normal.getZ() / 2.0D);
        Vec3 path = start.vectorTo(new Vec3(target.getX(), target.getY(), target.getZ()).add(0.5D, 0.5D, 0.5D));
        level.addParticle(ParticleTypes.WITCH, start.x(), start.y(), start.z(),
                path.x / PARTICLE_SPEED, path.y / PARTICLE_SPEED, path.z / PARTICLE_SPEED);
    }
}
