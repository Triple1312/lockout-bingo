package net.abrikoos.lockout_bingo.network.game;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;

public class LockoutUpdateBoardInfo {
    public final int[] goals;

    public LockoutUpdateBoardInfo(int[] goals) {
        this.goals = goals;
    }


    public static final PacketCodec<ByteBuf, LockoutUpdateBoardInfo> PACKET_CODEC = new PacketCodec<ByteBuf, LockoutUpdateBoardInfo>() {
        @Override
        public void encode(ByteBuf buf, LockoutUpdateBoardInfo value) {
            for (int goal : value.goals) {
                buf.writeInt(goal);
            }
        }

        @Override
        public LockoutUpdateBoardInfo decode(ByteBuf byteBuf) {
            int[] goals = new int[byteBuf.readableBytes() / 4];
            for (int i = 0; i < goals.length; i++) {
                goals[i] = byteBuf.readInt();
            }
            return new LockoutUpdateBoardInfo(goals);
        }
    };
}
