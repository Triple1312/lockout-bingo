package net.abrikoos.lockout_bingo.networkv2.team;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.nio.charset.Charset;

public record RotateTeamColor(String teamuuid) implements CustomPayload {

    public static final Id<RotateTeamColor> ID = new Id<>(  Identifier.of("lockout-bingo", "rotate_team_color"));

    public static PacketCodec<RegistryByteBuf, RotateTeamColor> CODEC = new PacketCodec<RegistryByteBuf, RotateTeamColor>() {
        @Override
        public void encode(RegistryByteBuf buf, RotateTeamColor value) {
            buf.writeCharSequence(value.teamuuid(), Charset.defaultCharset());
        }

        @Override
        public RotateTeamColor decode(RegistryByteBuf buf) {
            String teamuuid = buf.readCharSequence(36, Charset.defaultCharset()).toString();
            return new RotateTeamColor(teamuuid);
        }
    };


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
