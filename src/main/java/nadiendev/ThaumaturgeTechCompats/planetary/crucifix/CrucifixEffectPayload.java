package nadiendev.ThaumaturgeTechCompats.planetary.crucifix;

import nadiendev.ThaumaturgeTechCompats.ThaumaturgeTechCompats;
import nadiendev.ThaumaturgeTechCompats.planetary.PlanetaryTier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CrucifixEffectPayload(int playerId, int targetId, int displayId, Phase phase, boolean success, long startTick,
                                    int bindTicks, PlanetaryTier tier) implements CustomPacketPayload {
    public enum Phase {
        START,
        BANISH,
        SHATTER,
        CANCEL
    }

    public static final Type<CrucifixEffectPayload> TYPE = new Type<>(ThaumaturgeTechCompats.id("crucifix_effect"));

    public static final StreamCodec<FriendlyByteBuf, CrucifixEffectPayload> STREAM_CODEC = StreamCodec.of(
            (buffer, payload) -> {
                buffer.writeVarInt(payload.playerId);
                buffer.writeVarInt(payload.targetId);
                buffer.writeVarInt(payload.displayId);
                buffer.writeEnum(payload.phase);
                buffer.writeBoolean(payload.success);
                buffer.writeVarLong(payload.startTick);
                buffer.writeVarInt(payload.bindTicks);
                buffer.writeEnum(payload.tier);
            },
            buffer -> new CrucifixEffectPayload(buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt(),
                    buffer.readEnum(Phase.class), buffer.readBoolean(), buffer.readVarLong(), buffer.readVarInt(),
                    buffer.readEnum(PlanetaryTier.class)));

    @Override
    public Type<CrucifixEffectPayload> type() {
        return TYPE;
    }
}
