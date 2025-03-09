package net.abrikoos.lockout_bingo.networkv2.team;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.fabricmc.api.Environment;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class TeamRegV2 implements CustomPayload {

    public static final Id<TeamRegV2> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "team_reg_v2"));

    public List<TeamData> teams = new ArrayList<>();

    public List<PlayerData> players = new ArrayList<>();

    public TeamRegV2 build(List<TeamData> teams, List<PlayerData> players) {
        TeamRegV2 teamRegV2 = new TeamRegV2();
        teamRegV2.teams = teams;
        teamRegV2.players = players;
        return teamRegV2;
    }

    public static PacketCodec<RegistryByteBuf, TeamRegV2> CODEC = new PacketCodec<RegistryByteBuf, TeamRegV2>() {
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

    public TeamData getTeamDataByPlayerUUID(String uuid) throws Exception {
        for (TeamData team : teams) {
            if (team.playerUUIDs.contains(uuid)) {
                return team;
            }
        }
        throw new Exception("Team not found");
    }

    public List<PlayerData> getPlayersDataByTeamUUID(String uuid) throws Exception {
        List<PlayerData> teamPlayers = new ArrayList<>();
        List<String> playersTeamUUIDs = this.getTeamDataByUUID(uuid).playerUUIDs;
        for (PlayerData player : players) {
            if (playersTeamUUIDs.contains(player.puuid)) {
                teamPlayers.add(player);
            }
        }
        return teamPlayers;
    }

    public boolean isTeamEmpty(String teamUUID) throws Exception {
        return getPlayersDataByTeamUUID(teamUUID).isEmpty();
    }

    public int teamCount() {
        return teams.size();
    }

    public boolean playerInTeam(String playerUUID) {
        for (TeamData team : teams) {
            if (team.playerUUIDs.contains(playerUUID)) {
                return true;
            }
        }
        return false;
    }

    public TeamData getTeamDataByTeamName(String teamName) throws Exception {
        for (TeamData team : teams) {
            if (team.teamName.equals(teamName)) {
                return team;
            }
        }
        throw new Exception("Team not found");
    }

    public boolean isPlayerConnected(String uuid) throws Exception {
        return getPlayerDataByUUID(uuid).connected;
    }

    public boolean isColorInUse(int color) {
        for (TeamData team : teams) {
            if (team.teamColor == color) {
                return true;
            }
        }
        return false;
    }















}
