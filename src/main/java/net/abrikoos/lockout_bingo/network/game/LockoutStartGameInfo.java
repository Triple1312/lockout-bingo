package net.abrikoos.lockout_bingo.network.game;

import net.abrikoos.lockout_bingo.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.goals.GoalListItem;
import net.abrikoos.lockout_bingo.modes.team.LockoutTeamDataClass;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.util.ArrayList;
import java.util.List;

public class LockoutStartGameInfo {
    public List<LockoutTeamDataClass> teams;
    public final GoalListItem[] goals;
    public List<Integer> disabledGoalTypes;
    public List<Integer> disabledModifiers;

    public LockoutStartGameInfo(List<LockoutTeamDataClass> teams, GoalListItem[] goals, List<Integer> disabledGoalTypes, List<Integer> disabledModifiers) {
        this.teams = teams;
        this.goals = goals;
        this.disabledGoalTypes = disabledGoalTypes;
        this.disabledModifiers = disabledModifiers;

    }

    public static final PacketCodec<RegistryByteBuf, LockoutStartGameInfo> PACKET_CODEC = new PacketCodec<RegistryByteBuf, LockoutStartGameInfo>() {

        @Override
        public void encode(RegistryByteBuf buf, LockoutStartGameInfo value) {
            buf.writeByte(value.teams.size());

            for (LockoutTeamDataClass team : value.teams) {
                buf.writeByte(team.name().length());
                buf.writeCharSequence(team.name(), java.nio.charset.StandardCharsets.UTF_8);
                buf.writeByte(team.playernames().size());
                for (String player : team.playernames()) {
                    buf.writeByte(player.length());
                    buf.writeCharSequence(player, java.nio.charset.StandardCharsets.UTF_8);
                }
            }

            for (GoalListItem goal : value.goals) {
                buf.writeByte(goal.id.length());
                buf.writeCharSequence(goal.id, java.nio.charset.StandardCharsets.UTF_8);
            }

            buf.writeByte(value.disabledGoalTypes.size());
            for (Integer i : value.disabledGoalTypes) {
                buf.writeByte(i);
            }

            buf.writeByte(value.disabledModifiers.size());
            for (Integer i : value.disabledModifiers) {
                buf.writeByte(i);
            }

        }

        @Override
        public LockoutStartGameInfo decode(RegistryByteBuf buf) {
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            int counter = 0;
            List<LockoutTeamDataClass> teams = new ArrayList<>();

            int teamcount = bytes[counter];
            counter++;
            for (int i = 0; i < teamcount; i++) {
                int teamnamelength = bytes[counter];
                counter++;
                String teamname = new String(bytes, counter, teamnamelength);
                counter += teamnamelength;
                LockoutTeamDataClass t = new LockoutTeamDataClass(teamname, new ArrayList<>());

                int teamplayercount = bytes[counter];
                counter++;
                for (int j = 0; j < teamplayercount; j++) {
                    int stringlength = bytes[counter];
                    counter++;
                    String playername = new String(bytes, counter, stringlength);
                    t.playernames().add(playername);
                    counter += stringlength;
                }
                teams.add(t);
            }

            GoalListItem[] goals = new GoalListItem[25];
            for (int i = 0; i < 25; i++) { // if needed, change 25 to first byte in future
                int stringlength = bytes[counter];
                byte[] stringbytes = new byte[stringlength];
                for (int j = 0; j < stringlength; j++) {
                    stringbytes[j] = bytes[counter + 1 + j];
                }
                String name = new String(stringbytes);
                counter += stringlength + 1;
                goals[i] = GoalItemRegistry.getGoal(name);
            }
            int disabledGoalTypesSize = bytes[counter]; counter ++;
            List<Integer> disabledGoalTypes = new ArrayList<>();
            for (int i = 0; i < disabledGoalTypesSize; i++) {
                int disabledGoalType = bytes[counter]; counter ++;
                disabledGoalTypes.add(disabledGoalType);
            }

            int disabledModifiersSize = bytes[counter]; counter ++;
            List<Integer> disabledModifiers = new ArrayList<>();
            for (int i = 0; i < disabledModifiersSize; i++) {
                int disabledModifier = bytes[counter]; counter ++;
                disabledModifiers.add(disabledModifier);
            }

            return new LockoutStartGameInfo(teams, goals, disabledGoalTypes, disabledModifiers);
        }
    };


}
