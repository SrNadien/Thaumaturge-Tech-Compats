package nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.laser;

import com.buuz135.industrial.block.IndustrialBlock;
import nadiendev.ThaumaturgeTechCompats.compats.industrialforegoing.LaserMachine;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MagicalOreLaserBaseBlock extends IndustrialBlock<MagicalOreLaserBaseTile> {
    public MagicalOreLaserBaseBlock() {
        super(LaserMachine.ORE_BASE.blockName(), MagicalLaserRegistry.properties(), MagicalOreLaserBaseTile.class, null);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<MagicalOreLaserBaseTile> getTileEntityFactory() {
        return MagicalOreLaserBaseTile::new;
    }
}
