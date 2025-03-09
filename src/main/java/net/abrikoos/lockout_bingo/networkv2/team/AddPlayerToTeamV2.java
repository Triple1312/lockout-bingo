package net.abrikoos.lockout_bingo.networkv2.team;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AddPlayerToTeamV2(String puuid, String teamuuid) implements CustomPayload {

    public static final Id<AddPlayerToTeamV2> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "add_player_to_team_v2"));

    public static PacketCodec<RegistryByteBuf, AddPlayerToTeamV2> CODEC = new PacketCodec<RegistryByteBuf, AddPlayerToTeamV2>() {
        @Override
        public void encode(RegistryByteBuf buf, AddPlayerToTeamV2 value) {
            buf.writeCharSequence(value.puuid, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeCharSequence(value.teamuuid, java.nio.charset.StandardCharsets.UTF_8);
        }

        @Override
        public AddPlayerToTeamV2 decode(RegistryByteBuf buf) {
            String puuid = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            String teamuuid = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            return new AddPlayerToTeamV2(puuid, teamuuid);
        }
    };


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
