package net.abrikoos.lockout_bingo.networkv2.get;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GetGameInfo() implements CustomPayload {

    public static final Id<GetGameInfo> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "get_game_info"));


    public static PacketCodec<RegistryByteBuf, GetGameInfo> CODEC = new PacketCodec<RegistryByteBuf, GetGameInfo>() {
        @Override
        public void encode(RegistryByteBuf buf, GetGameInfo value) {
        }

        @Override
        public GetGameInfo decode(RegistryByteBuf buf) {
            return new GetGameInfo();
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
