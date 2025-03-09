package net.abrikoos.lockout_bingo.networkv2.team;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ChangeTeamColorV2(String teamuuid, int color) implements CustomPayload {

    public static final Id<ChangeTeamColorV2> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "change_team_color_v2"));


    public static PacketCodec<RegistryByteBuf, ChangeTeamColorV2> CODEC = new PacketCodec<RegistryByteBuf, ChangeTeamColorV2>() {
        @Override
        public void encode(RegistryByteBuf buf, ChangeTeamColorV2 value) {
            buf.writeCharSequence(value.teamuuid, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeByte(value.color);
        }

        @Override
        public ChangeTeamColorV2 decode(RegistryByteBuf buf) {
            String teamuuid = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            int color = buf.readByte();
            return new ChangeTeamColorV2(teamuuid, color);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
