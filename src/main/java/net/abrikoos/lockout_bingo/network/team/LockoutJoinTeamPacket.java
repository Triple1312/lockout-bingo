package net.abrikoos.lockout_bingo.network.team;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record LockoutJoinTeamPacket(int teamid) implements CustomPayload {
    public final static Id<LockoutJoinTeamPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "join_team"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public final static PacketCodec<ByteBuf, LockoutJoinTeamPacket> PACKET_CODEC = new PacketCodec<ByteBuf, LockoutJoinTeamPacket>() {
        @Override
        public void encode(ByteBuf buf, LockoutJoinTeamPacket value) {
            buf.writeInt(value.teamid);
        }

        @Override
        public LockoutJoinTeamPacket decode(ByteBuf byteBuf) {
            return new LockoutJoinTeamPacket(byteBuf.readInt());
        }
    };
}
