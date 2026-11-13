package nadiendev.ThaumaturgeTechCompats.compats.botania.spreader.mixin;

import nadiendev.ThaumaturgeTechCompats.compats.botania.spreader.SpreaderStats;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import velrondevs.botania.common.block.block_entity.mana.ManaSpreaderBlockEntity;
import velrondevs.botania.common.block.mana.ManaSpreaderBlock;

@Mixin(value = ManaSpreaderBlockEntity.class, remap = false)
public abstract class ManaSpreaderBlockEntityMixin extends BlockEntity {
    protected ManaSpreaderBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Redirect(method = "getBurst", at = @At(value = "INVOKE", target = "Lvelrondevs/botania/common/block/mana/ManaSpreaderBlock$Variant;burstMana()I"))
    private int thaumaturgetechcompats$burstMana(ManaSpreaderBlock.Variant variant) {
        return getBlockState().getBlock() instanceof SpreaderStats stats ? stats.burstMana() : variant.burstMana();
    }

    @Redirect(method = "getBurst", at = @At(value = "INVOKE", target = "Lvelrondevs/botania/common/block/mana/ManaSpreaderBlock$Variant;preLossTicks()I"))
    private int thaumaturgetechcompats$preLossTicks(ManaSpreaderBlock.Variant variant) {
        return getBlockState().getBlock() instanceof SpreaderStats stats ? stats.preLossTicks() : variant.preLossTicks();
    }

    @Redirect(method = "getBurst", at = @At(value = "INVOKE", target = "Lvelrondevs/botania/common/block/mana/ManaSpreaderBlock$Variant;lossPerTick()F"))
    private float thaumaturgetechcompats$lossPerTick(ManaSpreaderBlock.Variant variant) {
        return getBlockState().getBlock() instanceof SpreaderStats stats ? stats.lossPerTick() : variant.lossPerTick();
    }

    @Redirect(method = "getBurst", at = @At(value = "INVOKE", target = "Lvelrondevs/botania/common/block/mana/ManaSpreaderBlock$Variant;motionModifier()F"))
    private float thaumaturgetechcompats$motionModifier(ManaSpreaderBlock.Variant variant) {
        return getBlockState().getBlock() instanceof SpreaderStats stats ? stats.motionModifier() : variant.motionModifier();
    }

    @Redirect(method = "getBurst", at = @At(value = "FIELD", target = "Lvelrondevs/botania/common/block/mana/ManaSpreaderBlock$Variant;color:I", opcode = Opcodes.GETFIELD))
    private int thaumaturgetechcompats$color(ManaSpreaderBlock.Variant variant) {
        return getBlockState().getBlock() instanceof SpreaderStats stats ? stats.burstColor() : variant.color;
    }

    @Redirect(method = "getMaxMana", at = @At(value = "INVOKE", target = "Lvelrondevs/botania/common/block/mana/ManaSpreaderBlock$Variant;manaCapacity()I"))
    private int thaumaturgetechcompats$manaCapacity(ManaSpreaderBlock.Variant variant) {
        return getBlockState().getBlock() instanceof SpreaderStats stats ? stats.manaCapacity() : variant.manaCapacity();
    }
}
