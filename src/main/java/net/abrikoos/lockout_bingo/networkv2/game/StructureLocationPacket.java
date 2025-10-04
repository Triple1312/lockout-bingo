package net.abrikoos.lockout_bingo.networkv2.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record StructureLocationPacket(int r, int g, int b, int x, int y, int z) implements CustomPayload {
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final Id<StructureLocationPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "structure_location"));

    public static final PacketCodec<RegistryByteBuf, StructureLocationPacket> CODEC = new PacketCodec<RegistryByteBuf, StructureLocationPacket>() {
        @Override
        public StructureLocationPacket decode(RegistryByteBuf buf) {
            int r = buf.readByte();
            int g = buf.readByte();
            int b = buf.readByte();
            int x = buf.readInt();
            int y = buf.readInt();
            int z = buf.readInt();
            return new StructureLocationPacket(r, g, b, x, y, z);
        }

        @Override
        public void encode(RegistryByteBuf buf, StructureLocationPacket value) {
            buf.writeInt(value.r());
            buf.writeInt(value.g());
            buf.writeInt(value.b());
            buf.writeInt(value.x());
            buf.writeInt(value.y());
            buf.writeInt(value.z());
        }
    };


}
