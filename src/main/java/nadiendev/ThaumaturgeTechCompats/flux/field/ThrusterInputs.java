package nadiendev.ThaumaturgeTechCompats.flux.field;

import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public final class ThrusterInputs {
    private static volatile Function<Player, ThrusterInput> clientSource = player -> ThrusterInput.NONE;

    private ThrusterInputs() {
    }

    public static void clientSource(Function<Player, ThrusterInput> source) {
        clientSource = source;
    }

    public static ThrusterInput client(Player player) {
        return clientSource.apply(player);
    }
}
