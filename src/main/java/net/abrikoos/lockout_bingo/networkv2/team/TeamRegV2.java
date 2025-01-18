package net.abrikoos.lockout_bingo.networkv2.team;

import net.fabricmc.api.Environment;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class TeamRegV2 implements CustomPayload {

    public static final Id<TeamData> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "team_reg_v2"));

    public List<TeamData> teams = new ArrayList<>();

    public List<PlayerData> players = new ArrayList<>();

    public TeamRegV2 build(List<TeamData> teams, List<PlayerData> players) {
        TeamRegV2 teamRegV2 = new TeamRegV2();
        teamRegV2.teams = teams;
        teamRegV2.players = players;
        return teamRegV2;
    }

    public PacketCodec<RegistryByteBuf, TeamRegV2> CODEC = new PacketCodec<RegistryByteBuf, TeamRegV2>() {
        @Override
        public TeamRegV2 decode(RegistryByteBuf buf) {
            int teamCount = buf.readByte();
            TeamRegV2 teamRegV2 = new TeamRegV2();
            for (int i = 0; i < teamCount; i++) {
                teamRegV2.teams.add(TeamData.CODEC.decode(buf));
            }
            int playerCount = buf.readByte();
            for (int i = 0; i < playerCount; i++) {
                teamRegV2.players.add(PlayerData.CODEC.decode(buf));
            }
            return teamRegV2;

        }

        @Override
        public void encode(RegistryByteBuf buf, TeamRegV2 value) {
            buf.writeByte(value.teams.size());
            for (TeamData team : value.teams) {
                TeamData.CODEC.encode(buf, team);
            }
            buf.writeByte(value.players.size());
            for (PlayerData player : value.players) {
                PlayerData.CODEC.encode(buf, player);
            }
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }


    public PlayerData getPlayerDataByUUID(String uuid) throws Exception {
        for (PlayerData player : players) {
            if (player.puuid.equals(uuid)) {
                return player;
            }
        }
        throw new Exception("Player not found");
    }

    public TeamData getTeamDataByUUID(String uuid) throws Exception {
        for (TeamData team : teams) {
            if (team.teamUUID.equals(uuid)) {
                return team;
            }
        }
        throw new Exception("Team not found");
    }












}
