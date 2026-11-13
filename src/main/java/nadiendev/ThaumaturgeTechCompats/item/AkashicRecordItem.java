package nadiendev.ThaumaturgeTechCompats.item;

import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.research.pool.AspectPoolAccess;
import nadiendev.ThaumaturgeTechCompats.config.ThaumaturgeTechCompatsConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class AkashicRecordItem extends Item {
    public AkashicRecordItem(Properties properties) {
        super(properties.rarity(Rarity.RARE));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!ThaumaturgeTechCompatsConfig.AKASHIC_ENABLED.get()) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide() || !(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.SUCCESS;
        }

        Registry<IAspect> aspects = level.registryAccess().lookupOrThrow(IAspect.REGISTRY_KEY);
        int amount = ThaumaturgeTechCompatsConfig.AKASHIC_AMOUNT.get();
        for (Holder.Reference<IAspect> aspect : aspects.listElements().toList()) {
            AspectPoolAccess.grant(serverPlayer, aspect, amount);
        }
        serverPlayer.sendOverlayMessage(Component.translatable("message.thaumaturgetechcompats.akashic_record"));

        if (!player.getAbilities().instabuild || ThaumaturgeTechCompatsConfig.AKASHIC_CONSUMED_IN_CREATIVE.get()) {
            stack.shrink(1);
        }
        return InteractionResult.SUCCESS;
    }
}
