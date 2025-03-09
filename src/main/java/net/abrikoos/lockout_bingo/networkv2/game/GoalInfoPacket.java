package net.abrikoos.lockout_bingo.networkv2.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GoalInfoPacket( String goalName, String goalID, int goalIndex, String completedPlayerUUID, String completedTeamUUID, int color) implements CustomPayload {
    public static final Id<GoalInfoPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "goal_info_v2"));

    public static final PacketCodec<RegistryByteBuf, GoalInfoPacket> CODEC = new PacketCodec<RegistryByteBuf, GoalInfoPacket>() {
        @Override
        public GoalInfoPacket decode(RegistryByteBuf buf) {
            int goalNameCharacterCount = buf.readByte();
            String goalName = buf.readCharSequence(goalNameCharacterCount, java.nio.charset.StandardCharsets.UTF_8).toString();
            int goalIdCharacterCount = buf.readByte();
            String goalID = buf.readCharSequence(goalIdCharacterCount, java.nio.charset.StandardCharsets.UTF_8).toString();
            int goalIndex = buf.readByte();
            String completedPlayerUUID = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            String completedTeamUUID = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            int color = buf.readByte();
            return new GoalInfoPacket(goalName, goalID, goalIndex, completedPlayerUUID, completedTeamUUID, color);
        }

        @Override
        public void encode(RegistryByteBuf buf, GoalInfoPacket value) {
            buf.writeByte(value.goalName.length());
            buf.writeCharSequence(value.goalName, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeByte(value.goalID.length());
            buf.writeCharSequence(value.goalID, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeByte(value.goalIndex);
            buf.writeCharSequence(value.completedPlayerUUID, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeCharSequence(value.completedTeamUUID, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeByte(value.color);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
