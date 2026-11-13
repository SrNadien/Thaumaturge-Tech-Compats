package nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight.mixin;

import nadiendev.ThaumaturgeTechCompats.taintedfood.farmersdelight.FluxKnifeCutting;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import java.util.List;

@Mixin(value = CuttingBoardBlockEntity.class, remap = false)
public abstract class CuttingBoardBlockEntityMixin {
    @Unique
    private ItemStack thaumaturgetechcompats$tool;

    @Inject(method = "processStoredItemUsingTool", at = @At("HEAD"), require = 0)
    private void thaumaturgetechcompats$rememberTool(ItemStack tool, Player player, CallbackInfoReturnable<Boolean> cir) {
        this.thaumaturgetechcompats$tool = tool;
    }

    @Inject(method = "processStoredItemUsingTool", at = @At("RETURN"), require = 0)
    private void thaumaturgetechcompats$forgetTool(ItemStack tool, Player player, CallbackInfoReturnable<Boolean> cir) {
        this.thaumaturgetechcompats$tool = null;
    }

    @Redirect(method = "lambda$processStoredItemUsingTool$2",
            at = @At(value = "INVOKE",
                    target = "Lvectorwing/farmersdelight/common/crafting/CuttingBoardRecipe;rollResults(Lnet/minecraft/util/RandomSource;ILnet/neoforged/neoforge/items/wrapper/RecipeWrapper;)Ljava/util/List;"),
            require = 0)
    private List<ItemStack> thaumaturgetechcompats$fluxCut(CuttingBoardRecipe recipe, RandomSource random, int fortune, RecipeWrapper wrapper) {
        List<ItemStack> results = recipe.rollResults(random, fortune, wrapper);
        if (!FluxKnifeCutting.active(this.thaumaturgetechcompats$tool)) {
            return results;
        }
        CuttingBoardBlockEntity board = (CuttingBoardBlockEntity) (Object) this;
        Level level = board.getLevel();
        if (level instanceof ServerLevel server) {
            return FluxKnifeCutting.apply(server, board.getBlockPos(), board.getStoredItem(), results);
        }
        return results;
    }
}
