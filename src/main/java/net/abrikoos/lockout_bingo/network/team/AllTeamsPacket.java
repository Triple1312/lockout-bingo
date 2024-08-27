package net.abrikoos.lockout_bingo.network.team;

import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record AllTeamsPacket(List<LockoutTeam> teams) implements CustomPayload {

    public final static Id<AllTeamsPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "all_teams"));

    public static final PacketCodec<RegistryByteBuf, AllTeamsPacket> PACKET_CODEC = new PacketCodec<RegistryByteBuf, AllTeamsPacket>() {

        @Override
        public void encode(RegistryByteBuf buf, AllTeamsPacket value) {
            buf.writeByte(value.teams.size());
            for (LockoutTeam team : value.teams) {
//                buf.writeByte(team.packetSize());
                LockoutTeam.PACKET_CODEC.encode(buf, team);
            }
        }

        @Override
        public AllTeamsPacket decode(RegistryByteBuf buf) {
            List<LockoutTeam> teams = new ArrayList<>();
            byte teamcount = buf.readByte();
            for (int i = 0; i < teamcount; i++) {
                LockoutTeam tea = LockoutTeam.PACKET_CODEC.decode(buf);
                teams.add(tea);
            }
            return new AllTeamsPacket(teams);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
