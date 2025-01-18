package net.abrikoos.lockout_bingo.networkv2.game;

import net.minecraft.network.packet.CustomPayload;

public record StartGameRequestPacket() implements CustomPayload {
    @Override
    public Id<? extends CustomPayload> getId() {
        return null;
    }
}
