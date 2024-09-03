package net.abrikoos.lockout_bingo.network.team;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ChangeTeamIdPacket(int oldIndex, int newIndex) implements CustomPayload {

    public final static Id<ChangeTeamIdPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "change_team_id"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }



    public final static PacketCodec<ByteBuf, ChangeTeamIdPacket> PACKET_CODEC = new PacketCodec<ByteBuf, ChangeTeamIdPacket>() {
        @Override
        public void encode(ByteBuf buf, ChangeTeamIdPacket value) {
            buf.writeByte(value.oldIndex);
            buf.writeByte(value.newIndex);
        }

        @Override
        public ChangeTeamIdPacket decode(ByteBuf byteBuf) {
            return new ChangeTeamIdPacket(byteBuf.readByte(), byteBuf.readByte());
        }
    };



}
