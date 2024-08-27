package net.abrikoos.lockout_bingo.network.game;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LockoutUpdateBoardInfo {
    public final String[] goals; // 36 bytes atm -> will become 128 bits

    public LockoutUpdateBoardInfo(String[] goals) {
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
            char[] goals = new char[byteBuf.readableBytes()];
            List<String> strgoals = new ArrayList<>();
            assert goals.length/36 == 25;
            for (int i = 0; i < goals.length/36; i++) { // goals.length/36 should be 25
                strgoals.add(String.valueOf(byteBuf.readCharSequence(36, Charset.defaultCharset())));
            }
            return new LockoutUpdateBoardInfo(strgoals.toArray(new String[0]));
        }
    };
}
