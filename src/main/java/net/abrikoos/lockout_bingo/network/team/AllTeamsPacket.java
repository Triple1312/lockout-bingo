package net.abrikoos.lockout_bingo.network.team;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record AllTeamsPacket(List<String> teamnames) implements CustomPayload {

    public final static Id<AllTeamsPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "all_teams"));

    public static final PacketCodec<RegistryByteBuf, AllTeamsPacket> PACKET_CODEC = new PacketCodec<RegistryByteBuf, AllTeamsPacket>() {

        @Override
        public void encode(RegistryByteBuf buf, AllTeamsPacket value) {
            buf.writeByte(value.teamnames.size());
            for (String team : value.teamnames) {
                buf.writeByte(team.length());
                buf.writeCharSequence(team, java.nio.charset.StandardCharsets.UTF_8);
            }
        }

        @Override
        public AllTeamsPacket decode(RegistryByteBuf buf) {
            List<String> teamnames = new ArrayList<>();
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            int counter = 1;
            for (int i = 0; i < bytes[0]; i++) {
                int stringlength = bytes[counter];
                byte[] stringbytes = new byte[stringlength];
                for (int j = 0; j < stringlength; j++) {
                    stringbytes[j] = bytes[counter + 1 + j];
                }
                String name = new String(stringbytes);
                teamnames.add(name);
                counter += stringlength + 1;

            }
            return new AllTeamsPacket(teamnames);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return null;
    }
}
