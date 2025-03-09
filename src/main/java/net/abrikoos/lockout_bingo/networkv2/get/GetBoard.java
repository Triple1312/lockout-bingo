package net.abrikoos.lockout_bingo.networkv2.get;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GetBoard() implements CustomPayload {

    public static final Id<GetBoard> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "get_board"));


    public static PacketCodec<RegistryByteBuf, GetBoard> CODEC = new PacketCodec<RegistryByteBuf, GetBoard>() {
        @Override
        public void encode(RegistryByteBuf buf, GetBoard value) {
        }

        @Override
        public GetBoard decode(RegistryByteBuf buf) {
            return new GetBoard();
        }
    };



    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
