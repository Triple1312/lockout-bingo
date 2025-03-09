package net.abrikoos.lockout_bingo.networkv2.team;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record RemoveTeamV2(String uuid) implements CustomPayload {


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final Id<RemoveTeamV2> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "remove_team_v2"));

    public static PacketCodec<RegistryByteBuf, RemoveTeamV2> CODEC = new PacketCodec<RegistryByteBuf, RemoveTeamV2>() {
        @Override
        public void encode(RegistryByteBuf buf, RemoveTeamV2 value) {
            buf.writeCharSequence(value.uuid, java.nio.charset.StandardCharsets.UTF_8);
        }

        @Override
        public RemoveTeamV2 decode(RegistryByteBuf buf) {
            String uuid = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            return new RemoveTeamV2(uuid);
        }
    };



}
