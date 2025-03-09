package net.abrikoos.lockout_bingo.networkv2.get;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GetPlayerCompass() implements CustomPayload {

    public static final Id<GetPlayerCompass> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "get_compass"));


    public static PacketCodec<RegistryByteBuf, GetPlayerCompass> CODEC = new PacketCodec<RegistryByteBuf, GetPlayerCompass>() {
        @Override
        public void encode(RegistryByteBuf buf, GetPlayerCompass value) {
        }

        @Override
        public GetPlayerCompass decode(RegistryByteBuf buf) {
            return new GetPlayerCompass();
        }
    };


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
