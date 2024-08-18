package net.abrikoos.lockout_bingo.network.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record LockoutStartGamePacket(
        LockoutStartGameInfo info
) implements CustomPayload {
    public final static Id<LockoutStartGamePacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "lockout_start_game"));
    public static final PacketCodec<RegistryByteBuf, LockoutStartGamePacket> CODEC = PacketCodec.tuple(
            LockoutStartGameInfo.PACKET_CODEC, LockoutStartGamePacket::info, LockoutStartGamePacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
