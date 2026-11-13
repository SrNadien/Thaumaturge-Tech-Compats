package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID)
public final class ThrusterEvents {
    private static final Map<UUID, ThrusterState> CLIENT_STATES = new ConcurrentHashMap<>();
    private static final Map<UUID, ThrusterState> SERVER_STATES = new ConcurrentHashMap<>();

    private ThrusterEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        boolean client = player.level().isClientSide();
        Map<UUID, ThrusterState> states = client ? CLIENT_STATES : SERVER_STATES;
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!(stack.getItem() instanceof FlightThrusterItem item) || !FieldConfig.thrusterEnabled() || !FieldIntegration.thrusterEnabled()) {
            states.remove(player.getUUID());
            return;
        }
        ThrusterInput input = client ? ThrusterInputs.client(player) : ThrusterNetwork.server(player.getUUID());
        ThrusterState state = states.computeIfAbsent(player.getUUID(), id -> new ThrusterState());
        ThrusterLogic.tick(player, stack, item, state, input);
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (!FieldConfig.thrusterCancelFallDamage() || !FieldConfig.thrusterEnabled() || !FieldIntegration.thrusterEnabled()) {
            return;
        }
        ItemStack stack = event.getEntity().getItemBySlot(EquipmentSlot.CHEST);
        if (stack.getItem() instanceof FlightThrusterItem item && item.fluxStored(stack) > 0L) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID id = event.getEntity().getUUID();
        ThrusterNetwork.forget(id);
        SERVER_STATES.remove(id);
        CLIENT_STATES.remove(id);
    }
}
