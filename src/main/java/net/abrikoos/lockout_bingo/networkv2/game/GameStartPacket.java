package net.abrikoos.lockout_bingo.networkv2.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GameStartPacket(String game_mode, String team1, String team2, GoalBoardUpdatePacket board, long startTime, int freezeTime) implements CustomPayload {

    public static final Id<GameStartPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "game_start"));

    public static final PacketCodec<RegistryByteBuf, GameStartPacket> CODEC = new PacketCodec<RegistryByteBuf, GameStartPacket>() {
        @Override
        public GameStartPacket decode(RegistryByteBuf buf) {
            int gameModeCharacterCount = buf.readByte();
            String gameMode = buf.readCharSequence(gameModeCharacterCount, java.nio.charset.StandardCharsets.UTF_8).toString();
            String team1 = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            String team2 = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            GoalBoardUpdatePacket board = GoalBoardUpdatePacket.CODEC.decode(buf);
            long startTime = buf.readLong();
            int freezeTime = buf.readInt();
            return new GameStartPacket(gameMode, team1, team2,  board, startTime, freezeTime);
        }

        @Override
        public void encode(RegistryByteBuf buf, GameStartPacket value) {
            buf.writeByte(value.game_mode().length());
            buf.writeCharSequence(value.game_mode(), java.nio.charset.StandardCharsets.UTF_8);
            buf.writeCharSequence(value.team1(), java.nio.charset.StandardCharsets.UTF_8);
            buf.writeCharSequence(value.team2(), java.nio.charset.StandardCharsets.UTF_8);
            GoalBoardUpdatePacket.CODEC.encode(buf, value.board());
            buf.writeLong(value.startTime());
            buf.writeInt(value.freezeTime());
        }
    };


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
