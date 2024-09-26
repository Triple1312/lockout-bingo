package net.abrikoos.lockout_bingo.network.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class GivePlayerCompass implements CustomPayload {

    public final static Id<GivePlayerCompass> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "give_player_compass"));

    public static final PacketCodec<RegistryByteBuf, GivePlayerCompass> CODEC = new PacketCodec<RegistryByteBuf, GivePlayerCompass>() {
        @Override
        public GivePlayerCompass decode(RegistryByteBuf buf) {
            return new GivePlayerCompass();
        }

        @Override
        public void encode(RegistryByteBuf buf, GivePlayerCompass value) {
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
