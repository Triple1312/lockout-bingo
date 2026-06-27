package net.abrikoos.lockout_bingo.networkv2.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GamePauseUpdatePacket(boolean paused, long pauseOffset) implements CustomPayload {

    public static final Id<GamePauseUpdatePacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "game_pause_update"));

    public static final PacketCodec<RegistryByteBuf, GamePauseUpdatePacket> CODEC = new PacketCodec<>() {
        @Override
        public GamePauseUpdatePacket decode(RegistryByteBuf buf) {
            boolean paused = buf.readBoolean();
            long pauseOffset = buf.readLong();
            return new GamePauseUpdatePacket(paused, pauseOffset);
        }

        @Override
        public void encode(RegistryByteBuf buf, GamePauseUpdatePacket value) {
            buf.writeBoolean(value.paused());
            buf.writeLong(value.pauseOffset());
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
