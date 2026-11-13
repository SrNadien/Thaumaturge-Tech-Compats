package nadiendev.ThaumaturgeTechCompats.flux.field;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ThrusterInputPayload(boolean up, boolean forward) implements CustomPacketPayload {
    public static final Type<ThrusterInputPayload> TYPE = new Type<>(ThaumaturgeTechCompats.id("thruster_input"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ThrusterInputPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ThrusterInputPayload::up,
            ByteBufCodecs.BOOL, ThrusterInputPayload::forward,
            ThrusterInputPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
