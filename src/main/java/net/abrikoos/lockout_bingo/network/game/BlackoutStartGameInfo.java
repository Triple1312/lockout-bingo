package net.abrikoos.lockout_bingo.network.game;

import io.netty.buffer.ByteBuf;
import net.abrikoos.lockout_bingo.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.goals.GoalListItem;
import net.minecraft.network.codec.PacketCodec;

public class BlackoutStartGameInfo {
    public final GoalListItem[] goals;

    public BlackoutStartGameInfo(GoalListItem[] goals) {
        this.goals = goals;
    }


    public static final PacketCodec<ByteBuf, BlackoutStartGameInfo> PACKET_CODEC = new PacketCodec<ByteBuf, BlackoutStartGameInfo>() {
        @Override
        public void encode(ByteBuf buf, BlackoutStartGameInfo value) {
            for (GoalListItem goal : value.goals) {
                goal.encode(buf);
            }
        }

        @Override
        public BlackoutStartGameInfo decode(ByteBuf byteBuf) {
            BlackoutStartGameInfo info = new BlackoutStartGameInfo(new GoalListItem[25]);

            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            int counter = 0;

            for (int i = 0; i < 25; i++) { // if needed, change 25 to first byte in future
                int stringlength = bytes[counter];
                byte[] stringbytes = new byte[stringlength];
                for (int j = 0; j < stringlength; j++) {
                    stringbytes[j] = bytes[counter + 1 + j];
                }
                String name = new String(stringbytes);
                counter += stringlength + 1;
                info.goals[i] = GoalItemRegistry.getGoal(name);
            }
            return info;
        }
    };
}
