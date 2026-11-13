package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.buuz135.industrial.block.IndustrialBlock;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.LaserMachine;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MagicalFluidLaserBaseBlock extends IndustrialBlock<MagicalFluidLaserBaseTile> {
    public MagicalFluidLaserBaseBlock() {
        super(LaserMachine.FLUID_BASE.blockName(), MagicalLaserRegistry.properties(), MagicalFluidLaserBaseTile.class, null);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<MagicalFluidLaserBaseTile> getTileEntityFactory() {
        return MagicalFluidLaserBaseTile::new;
    }
}
