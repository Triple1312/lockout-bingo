package net.abrikoos.lockout_bingo.networkv2.compass;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record PlayersPositionPacket(
        List<CompassPlayerPosition> positions
) implements CustomPayload {

    public final static Id<PlayersPositionPacket> ID = new Id<>(Identifier.of("lockout-bingo", "players_position"));


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, PlayersPositionPacket> CODEC = new PacketCodec<RegistryByteBuf, PlayersPositionPacket>() {

        @Override
        public void encode(RegistryByteBuf buf, PlayersPositionPacket value) {
            buf.writeByte(value.positions().size());
            for (CompassPlayerPosition position : value.positions()) {
                CompassPlayerPosition.PACKET_CODEC.encode(buf, position);
            }
        }

        @Override
        public PlayersPositionPacket decode(RegistryByteBuf buf) {
            List<CompassPlayerPosition> positions = new ArrayList<>();
            int size = buf.readByte();
            for (int i = 0; i < size; i++) {
                positions.add(CompassPlayerPosition.PACKET_CODEC.decode(buf));
            }
            return new PlayersPositionPacket(positions);
        }
    };
}
