package net.abrikoos.lockout_bingo.networkv2.get;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GetTeamData() implements CustomPayload {

    public static final Id<GetTeamData> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "get_team_data"));


    public static PacketCodec<RegistryByteBuf, GetTeamData> CODEC = new PacketCodec<RegistryByteBuf, GetTeamData>() {
        @Override
        public void encode(RegistryByteBuf buf, GetTeamData value) {
        }

        @Override
        public GetTeamData decode(RegistryByteBuf buf) {
            return new GetTeamData();
        }
    };


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
