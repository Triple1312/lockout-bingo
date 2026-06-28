package net.abrikoos.blockout.networkv2.team;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record TeammateRespawnRequestPacket(String playerUUID) implements CustomPayload {
    public static final CustomPayload.Id<TeammateRespawnRequestPacket> ID = new CustomPayload.Id<>(Identifier.of("blockout", "teammate_respawn_request"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static PacketCodec<RegistryByteBuf, TeammateRespawnRequestPacket> CODEC = new PacketCodec<RegistryByteBuf, TeammateRespawnRequestPacket>() {
        @Override
        public TeammateRespawnRequestPacket decode(RegistryByteBuf buf) {
            return new TeammateRespawnRequestPacket(buf.readString());
        }

        @Override
        public void encode(RegistryByteBuf buf, TeammateRespawnRequestPacket value) {
            buf.writeString(value.playerUUID());
        }
    };
}
