package net.abrikoos.lockout_bingo.networkv2.team;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AddTeamV2(String name) implements CustomPayload {

    public static final Id<AddTeamV2> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "add_team_v2"));

    public static PacketCodec<RegistryByteBuf, AddTeamV2> CODEC = new PacketCodec<RegistryByteBuf, AddTeamV2>() {
        @Override
        public void encode(RegistryByteBuf buf, AddTeamV2 value) {
            buf.writeByte(value.name.length());
            buf.writeCharSequence(value.name, java.nio.charset.StandardCharsets.UTF_8);
        }

        @Override
        public AddTeamV2 decode(RegistryByteBuf buf) {
            int nameCharacterCount = buf.readByte();
            String name = buf.readCharSequence(nameCharacterCount, java.nio.charset.StandardCharsets.UTF_8).toString();
            return new AddTeamV2(name);
        }
    };


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
