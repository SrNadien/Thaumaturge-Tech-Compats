package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.planetary.crucifix.client.CrucifixClient;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class CrucifixNetwork {
    private static final String VERSION = "1";

    private CrucifixNetwork() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(VERSION);
        registrar.playToClient(CrucifixEffectPayload.TYPE, CrucifixEffectPayload.STREAM_CODEC,
                (payload, context) -> CrucifixClient.accept(payload));
    }
}
