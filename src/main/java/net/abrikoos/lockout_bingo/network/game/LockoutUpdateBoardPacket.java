package net.abrikoos.lockout_bingo.network.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record LockoutUpdateBoardPacket(
        LockoutUpdateBoardInfo goalboard
) implements CustomPayload {

    public final static Id<LockoutUpdateBoardPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "update_board"));
    public static final PacketCodec<RegistryByteBuf, LockoutUpdateBoardPacket> CODEC = PacketCodec.tuple(
            LockoutUpdateBoardInfo.PACKET_CODEC, LockoutUpdateBoardPacket::goalboard, LockoutUpdateBoardPacket::new
    );



    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

