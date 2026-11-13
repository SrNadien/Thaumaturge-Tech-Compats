package nadiendev.ThaumaturgeTechCompats.flux.field.client;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.flux.field.FlightThrusterItem;
import nadiendev.ThaumaturgeTechCompats.flux.field.ThrusterInput;
import nadiendev.ThaumaturgeTechCompats.flux.field.ThrusterInputPayload;
import nadiendev.ThaumaturgeTechCompats.flux.field.ThrusterInputs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public final class ThrusterClient {
    private static volatile ThrusterInput current = ThrusterInput.NONE;

    private ThrusterClient() {
    }

    @EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, value = Dist.CLIENT)
    public static final class Setup {
        private Setup() {
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> ThrusterInputs.clientSource(player -> player == Minecraft.getInstance().player ? current : ThrusterInput.NONE));
        }
    }

    @EventBusSubscriber(modid = ThaumaturgeTechCompats.MOD_ID, value = Dist.CLIENT)
    public static final class Ticks {
        private Ticks() {
        }

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            boolean wearing = player != null && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof FlightThrusterItem;
            boolean playing = wearing && minecraft.screen == null && !minecraft.isPaused();
            ThrusterInput next = new ThrusterInput(playing && minecraft.options.keyJump.isDown(), playing && minecraft.options.keyUp.isDown());
            if (next.equals(current)) {
                return;
            }
            current = next;
            if (minecraft.getConnection() != null) {
                ClientPacketDistributor.sendToServer(new ThrusterInputPayload(next.up(), next.forward()));
            }
        }
    }
}
