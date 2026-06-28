package net.abrikoos.blockout.networkv2.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record EndGamePacket() implements CustomPayload {

    public static final Id<EndGamePacket> ID = new CustomPayload.Id<>(Identifier.of("blockout", "end_game"));

    public static final PacketCodec<RegistryByteBuf, EndGamePacket> CODEC = new PacketCodec<>() {
        @Override
        public EndGamePacket decode(RegistryByteBuf buf) { return new EndGamePacket(); }
        @Override
        public void encode(RegistryByteBuf buf, EndGamePacket value) {}
    };

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
