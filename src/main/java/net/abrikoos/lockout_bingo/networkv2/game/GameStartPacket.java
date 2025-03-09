package net.abrikoos.lockout_bingo.networkv2.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.List;

public class GameStartPacket implements CustomPayload {

    String game_mode;
    String team1;
    String team2;
    GoalBoardUpdatePacket board;
    long startTime;
    int freezeTime;

    public GameStartPacket(String game_mode, String team1, String team2, GoalBoardUpdatePacket board, long startTime, int freezeTime) {
        this.game_mode = game_mode;
        this.team1 = team1;
        this.team2 = team2;
        this.board = board;
        this.startTime = startTime;
        this.freezeTime = freezeTime;
    }

    public String game_mode() {
        return game_mode;
    }

    public String team1() {
        return team1;
    }

    public String team2() {
        return team2;
    }

    public GoalBoardUpdatePacket board() {
        return board;
    }

    public long startTime() {
        return startTime;
    }

    public int freezeTime() {
        return freezeTime;
    }

    public void updateBoard(GoalBoardUpdatePacket board) {
        this.board = board;
    }

    public List<String> teamUUIDs() { // todo update when more teams available
        return List.of(team1, team2);
    }

    public static final Id<GameStartPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "game_start"));

    public static GameStartPacket empty() {
        return new GameStartPacket("", "00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000", GoalBoardUpdatePacket.empty(), 0, 0);
    }

    public static PacketCodec<RegistryByteBuf, GameStartPacket> CODEC = new PacketCodec<RegistryByteBuf, GameStartPacket>() {
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
