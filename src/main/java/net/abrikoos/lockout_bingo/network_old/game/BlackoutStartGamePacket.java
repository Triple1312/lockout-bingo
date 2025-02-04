package net.abrikoos.lockout_bingo.network.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record BlackoutStartGamePacket(
        BlackoutStartGameInfo goalboard
) implements CustomPayload {

    public final static Id<BlackoutStartGamePacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "blackout_start_game"));
    public static final PacketCodec<RegistryByteBuf, BlackoutStartGamePacket> CODEC = PacketCodec.tuple(
            BlackoutStartGameInfo.PACKET_CODEC, BlackoutStartGamePacket::goalboard, BlackoutStartGamePacket::new
    );



    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

