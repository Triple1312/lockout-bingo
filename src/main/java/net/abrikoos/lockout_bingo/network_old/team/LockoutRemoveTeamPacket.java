package net.abrikoos.lockout_bingo.network.team;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record LockoutRemoveTeamPacket(String team) implements CustomPayload {

    public final static Id<LockoutRemoveTeamPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "del_team"));


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public final static PacketCodec<ByteBuf, LockoutRemoveTeamPacket> PACKET_CODEC = new PacketCodec<ByteBuf, LockoutRemoveTeamPacket>() {
        @Override
        public void encode(ByteBuf buf, LockoutRemoveTeamPacket value) {
            buf.writeCharSequence(value.team, java.nio.charset.StandardCharsets.UTF_8);
        }

        @Override
        public LockoutRemoveTeamPacket decode(ByteBuf byteBuf) {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            return new LockoutRemoveTeamPacket(new String(bytes, java.nio.charset.StandardCharsets.UTF_8));
        }
    };
}
