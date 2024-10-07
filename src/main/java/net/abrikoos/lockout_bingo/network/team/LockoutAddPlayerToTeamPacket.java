package net.abrikoos.lockout_bingo.network.team;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.nio.charset.Charset;
import java.util.UUID;

public record LockoutAddPlayerToTeamPacket(int teamid, String uuid) implements CustomPayload {
    public final static Id<LockoutAddPlayerToTeamPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "add_to_team"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public final static PacketCodec<ByteBuf, LockoutAddPlayerToTeamPacket> PACKET_CODEC = new PacketCodec<ByteBuf, LockoutAddPlayerToTeamPacket>() {
        @Override
        public void encode(ByteBuf buf, LockoutAddPlayerToTeamPacket value) {
            buf.writeInt(value.teamid); buf.writeCharSequence(value.uuid, Charset.defaultCharset());
        }

        @Override
        public LockoutAddPlayerToTeamPacket decode(ByteBuf byteBuf) {
            return new LockoutAddPlayerToTeamPacket(byteBuf.readInt(), byteBuf.readCharSequence(36, Charset.defaultCharset()).toString());
        }
    };
}
