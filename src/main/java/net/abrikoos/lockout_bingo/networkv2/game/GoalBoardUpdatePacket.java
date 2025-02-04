package net.abrikoos.lockout_bingo.networkv2.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record GoalBoardUpdatePacket( List<GoalInfoPacket> goals, int justCompletedGoal, List<Integer> scores) implements CustomPayload {
    public static final Id<GoalBoardUpdatePacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "goal_board_update_v2"));

    public static final PacketCodec<RegistryByteBuf, GoalBoardUpdatePacket> CODEC = new PacketCodec<RegistryByteBuf, GoalBoardUpdatePacket>() {
        @Override
        public GoalBoardUpdatePacket decode(RegistryByteBuf buf) {
            int goalCount = buf.readByte();
            List<GoalInfoPacket> goals = new ArrayList<>();
            for (int i = 0; i < goalCount; i++) {
                goals.add(GoalInfoPacket.CODEC.decode(buf));
            }
            int justCompletedGoal = buf.readByte();
            int scoreCount = buf.readByte();
            List<Integer> scores = new ArrayList<>();
            for (int i = 0; i < scoreCount; i++) {
                scores.add((int) buf.readByte());
            }
            return new GoalBoardUpdatePacket( goals, justCompletedGoal, scores);
        }

        @Override
        public void encode(RegistryByteBuf buf, GoalBoardUpdatePacket value) {
            buf.writeByte(value.goals.size());
            for (GoalInfoPacket goal : value.goals) {
                GoalInfoPacket.CODEC.encode(buf, goal);
            }
            buf.writeByte(value.justCompletedGoal);
            buf.writeByte(value.scores.size());
            for (int score : value.scores) {
                buf.writeByte(score);
            }
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return null;
    }
}
