package net.abrikoos.lockout_bingo.network.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record CreateLockoutPacket(
        List<Integer> teams,
        int difficulty,
        boolean end,
        boolean nether
) implements CustomPayload {

    public final static Id<CreateLockoutPacket> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "create_lockout"));



    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }


    public static final PacketCodec<RegistryByteBuf, CreateLockoutPacket> CODEC = new PacketCodec<RegistryByteBuf, CreateLockoutPacket>() {
        @Override
        public CreateLockoutPacket decode(RegistryByteBuf buf) {
            List<Integer> ts = new ArrayList<>();
            int teamcount = buf.readByte();
            for (int i = 0; i < teamcount; i++) {
//                int teamnamelength = buf.readByte();
//                String teamname = buf.readCharSequence(teamnamelength, java.nio.charset.StandardCharsets.UTF_8).toString();
                ts.add((int) buf.readByte());
            }
            int difficulty = buf.readByte();
            boolean end = buf.readBoolean();
            boolean nether = buf.readBoolean();


            return new CreateLockoutPacket(
                    ts,
                    difficulty,
                    end,
                    nether
            );
        }

        @Override
        public void encode(RegistryByteBuf buf, CreateLockoutPacket value) {
            buf.writeByte(value.teams().size());
            for (Integer team : value.teams) {
                buf.writeByte(team);
//                buf.writeByte(team.length());
//                buf.writeCharSequence(team, java.nio.charset.StandardCharsets.UTF_8);
            }
            buf.writeByte(value.difficulty());
            buf.writeBoolean(value.end());
            buf.writeBoolean(value.nether());
        }
    };
}
