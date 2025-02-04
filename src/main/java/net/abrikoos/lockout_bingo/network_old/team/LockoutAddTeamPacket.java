package net.abrikoos.lockout_bingo.network.team;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;


public record LockoutAddTeamPacket(String team) implements CustomPayload {

    public final static Id<LockoutAddTeamPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "add_team"));


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public final static PacketCodec<ByteBuf, LockoutAddTeamPacket> PACKET_CODEC = new PacketCodec<ByteBuf, LockoutAddTeamPacket>() {
        @Override
        public void encode(ByteBuf buf, LockoutAddTeamPacket value) {
            buf.writeCharSequence(value.team, java.nio.charset.StandardCharsets.UTF_8);
        }

        @Override
        public LockoutAddTeamPacket decode(ByteBuf byteBuf) {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            return new LockoutAddTeamPacket(new String(bytes, java.nio.charset.StandardCharsets.UTF_8));
        }
    };
}
