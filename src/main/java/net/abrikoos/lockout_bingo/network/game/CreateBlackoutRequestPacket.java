package net.abrikoos.lockout_bingo.network.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record CreateBlackoutRequestPacket(
        int idk
) implements CustomPayload {

    public final static Id<CreateBlackoutRequestPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "blackout_request_start_game"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, CreateBlackoutRequestPacket> CODEC = new PacketCodec<RegistryByteBuf, CreateBlackoutRequestPacket>() {
        @Override
        public CreateBlackoutRequestPacket decode(RegistryByteBuf buf) {
            return new CreateBlackoutRequestPacket(buf.readInt());
        }

        @Override
        public void encode(RegistryByteBuf buf, CreateBlackoutRequestPacket value) {
            buf.writeInt(value.idk());
        }
    };


}
