package net.abrikoos.lockout_bingo.networkv2.team;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record RemovePlayerFromTeamV2(String puuid) implements CustomPayload {

    public static final Id<RemovePlayerFromTeamV2> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "remove_player_from_team_v2"));

    public static PacketCodec<RegistryByteBuf, RemovePlayerFromTeamV2> CODEC = new PacketCodec<RegistryByteBuf, RemovePlayerFromTeamV2>() {
        @Override
        public void encode(RegistryByteBuf buf, RemovePlayerFromTeamV2 value) {
            buf.writeCharSequence(value.puuid, java.nio.charset.StandardCharsets.UTF_8);
        }

        @Override
        public RemovePlayerFromTeamV2 decode(RegistryByteBuf buf) {
            String puuid = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            String teamuuid = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            return new RemovePlayerFromTeamV2(puuid);
        }
    };



    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
