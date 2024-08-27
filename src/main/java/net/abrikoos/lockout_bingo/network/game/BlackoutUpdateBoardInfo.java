package net.abrikoos.lockout_bingo.network.game;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;

import java.nio.charset.Charset;

public class BlackoutUpdateBoardInfo {
    public final int[] goals;

    public BlackoutUpdateBoardInfo(int[] goals) {
        this.goals = goals;
    }


    public static final PacketCodec<ByteBuf, LockoutUpdateBoardInfo> PACKET_CODEC = new PacketCodec<ByteBuf, LockoutUpdateBoardInfo>() {
        @Override
        public void encode(ByteBuf buf, LockoutUpdateBoardInfo value) {
            for (String goal : value.goals) {
                buf.writeCharSequence(goal, Charset.defaultCharset());
            }
        }

        @Override
        public LockoutUpdateBoardInfo decode(ByteBuf byteBuf) {
            String[] goals = new String[byteBuf.readableBytes() / 36];
            for (int i = 0; i < goals.length; i++) {
                goals[i] = byteBuf.readCharSequence(36, Charset.defaultCharset()).toString();
            }
            return new LockoutUpdateBoardInfo(goals);
        }
    };
}
