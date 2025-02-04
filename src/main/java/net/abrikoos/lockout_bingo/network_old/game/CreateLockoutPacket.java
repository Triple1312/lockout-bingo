package net.abrikoos.lockout_bingo.network.game;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/// Disabled goal types:
/// 0 - end
/// 1 - nether
/// 2 - redstone
/// 3 - death
/// 4 - opponent
/// 5 - biome
/// 6 - advancement
/// 7 - food
/// 8 - kill
/// 9 - movement
/// 10 - breed
/// 11 - obtain
/// 12 - armor
/// 13 - tools
/// 14 - ride


public record CreateLockoutPacket(
        List<Integer> teams,
        int difficulty,
        int goalCount,
        List<Integer> disabledGoalTypes,
        List<Integer> disabledModifiers
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
            int goalcount = buf.readByte();
            int dgoaltypes_count = buf.readByte();
            List<Integer> dgoaltypes = new ArrayList<>();
            for (int i = 0; i < dgoaltypes_count; i++) {
                dgoaltypes.add((int) buf.readByte());
            }

            int dmodifiers_count = buf.readByte();
            List<Integer> dmodifiers = new ArrayList<>();
            for (int i = 0; i < dmodifiers_count; i++) {
                dmodifiers.add((int) buf.readByte());
            }


            return new CreateLockoutPacket(
                    ts,
                    difficulty,
                    goalcount,
                    dgoaltypes,
                    dmodifiers
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
            buf.writeByte(value.goalCount());
            buf.writeByte(value.disabledGoalTypes().size());
            for (Integer dgt : value.disabledGoalTypes) {
                buf.writeByte(dgt);
            }
            buf.writeByte(value.disabledModifiers().size());
            for (Integer dm : value.disabledModifiers) {
                buf.writeByte(dm);
            }
        }
    };
}
