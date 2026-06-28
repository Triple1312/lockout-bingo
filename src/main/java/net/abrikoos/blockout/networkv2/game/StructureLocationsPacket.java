package net.abrikoos.blockout.networkv2.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.List;

public record StructureLocationsPacket(List<StructureLocationPacket> structures) implements CustomPayload {
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final Id<StructureLocationsPacket> ID = new CustomPayload.Id<>(Identifier.of("blockout", "structure_locations"));

    public static final PacketCodec<RegistryByteBuf, StructureLocationsPacket> CODEC = new PacketCodec<RegistryByteBuf, StructureLocationsPacket>() {
        @Override
        public StructureLocationsPacket decode(RegistryByteBuf buf) {
            int structureCount = buf.readInt();
            List<StructureLocationPacket> structures = new java.util.ArrayList<>();
            for (int i = 0; i < structureCount; i++) {
                structures.add(StructureLocationPacket.CODEC.decode(buf));
            }
            return new StructureLocationsPacket(structures);
        }

        @Override
        public void encode(RegistryByteBuf buf, StructureLocationsPacket value) {
            buf.writeInt(value.structures.size());
            for (StructureLocationPacket structure : value.structures) {
                StructureLocationPacket.CODEC.encode(buf, structure);
            }
        }
    };








}
