package net.abrikoos.lockout_bingo.networkv2.compass;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class AskCompassPacket implements CustomPayload {

    public static final Id<AskCompassPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "ask_compass"));

    public static  PacketCodec<RegistryByteBuf, AskCompassPacket> CODEC = new PacketCodec<RegistryByteBuf, AskCompassPacket>() {
        @Override
        public AskCompassPacket decode(RegistryByteBuf buf) {
            return new AskCompassPacket();
        }

        @Override
        public void encode(RegistryByteBuf buf, AskCompassPacket value) {
        }
    };


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
