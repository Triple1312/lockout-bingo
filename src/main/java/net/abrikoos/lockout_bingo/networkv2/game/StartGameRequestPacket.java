package net.abrikoos.lockout_bingo.networkv2.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record StartGameRequestPacket(
        String gameMode,
        List<String> teamUUIDs,
        int difficulty,
        int goalCount,
        List<String> disabledGoals,
        List<String> disabledModifiers

) implements CustomPayload {

    public static final Id<StartGameRequestPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "start_game_request_v2"));


    public static StartGameRequestPacket empty() {
        return new StartGameRequestPacket("", new ArrayList<>(), 0, 0, new ArrayList<>(), new ArrayList<>());
    }

    public static PacketCodec<RegistryByteBuf, StartGameRequestPacket> CODEC = new PacketCodec<RegistryByteBuf, StartGameRequestPacket>() {
        @Override
        public StartGameRequestPacket decode(RegistryByteBuf buf) {
            int gameModeCharacterCount = buf.readByte();
            String gameMode = buf.readCharSequence(gameModeCharacterCount, java.nio.charset.StandardCharsets.UTF_8).toString();
            int teamCount = buf.readByte();
            List<String> teamUUIDs = new ArrayList<>();
            for (int i = 0; i < teamCount; i++) {
                teamUUIDs.add(buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString());
            }
            int difficulty = buf.readByte();
            int goalCount = buf.readByte();
            int disabledGoalCategoryCounts = buf.readByte();
            List<String> disabledGoals = new ArrayList<>();
            for (int i = 0; i < disabledGoalCategoryCounts; i++) {
                int goalNameCharacterCount = buf.readByte();
                disabledGoals.add(buf.readCharSequence(goalNameCharacterCount, java.nio.charset.StandardCharsets.UTF_8).toString());
            }
            int modifierCount = buf.readByte();
            List<String> disabledModifiers = new ArrayList<>();
            for (int i = 0; i < modifierCount; i++) {
                int modifierNameCharacterCount = buf.readByte();
                disabledModifiers.add(buf.readCharSequence(modifierNameCharacterCount, java.nio.charset.StandardCharsets.UTF_8).toString());
            }
            return new StartGameRequestPacket( gameMode, teamUUIDs,  difficulty,  goalCount,  disabledGoals,  disabledModifiers);
        }

        @Override
        public void encode(RegistryByteBuf buf, StartGameRequestPacket value) {
            buf.writeByte(value.gameMode.length());
            buf.writeCharSequence(value.gameMode, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeByte(value.teamUUIDs.size());
            for (String teamUUID : value.teamUUIDs) {
                buf.writeCharSequence(teamUUID, java.nio.charset.StandardCharsets.UTF_8);
            }
            buf.writeByte(value.difficulty);
            buf.writeByte(value.goalCount);
            buf.writeByte(value.disabledGoals.size());
            for (String goal : value.disabledGoals) {
                buf.writeByte(goal.length());
                buf.writeCharSequence(goal, java.nio.charset.StandardCharsets.UTF_8);
            }
            buf.writeByte(value.disabledModifiers.size());
            for (String modifier : value.disabledModifiers) {
                buf.writeByte(modifier.length());
                buf.writeCharSequence(modifier, java.nio.charset.StandardCharsets.UTF_8);
            }
        }
    };





    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
