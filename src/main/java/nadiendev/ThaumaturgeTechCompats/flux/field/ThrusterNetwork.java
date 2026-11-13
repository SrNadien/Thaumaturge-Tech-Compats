package nadiendev.ThaumaturgeTechCompats.flux.field;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ThrusterNetwork {
    private static final String VERSION = "1";
    private static final Map<UUID, ThrusterInput> SERVER_INPUT = new ConcurrentHashMap<>();

    private ThrusterNetwork() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(VERSION);
        registrar.playToServer(ThrusterInputPayload.TYPE, ThrusterInputPayload.CODEC, (payload, context) -> {
            ThrusterInput input = new ThrusterInput(payload.up(), payload.forward());
            if (input.equals(ThrusterInput.NONE)) {
                SERVER_INPUT.remove(context.player().getUUID());
            } else {
                SERVER_INPUT.put(context.player().getUUID(), input);
            }
        });
    }

    public static ThrusterInput server(UUID player) {
        return SERVER_INPUT.getOrDefault(player, ThrusterInput.NONE);
    }

    public static void forget(UUID player) {
        SERVER_INPUT.remove(player);
    }
}
