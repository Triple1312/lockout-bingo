package net.abrikoos.lockout_bingo.team;

import net.abrikoos.lockout_bingo.gamestate.GameState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerTeamRegistry {

    private static List<TeamPlayer> teamPlayers = new ArrayList<>();

    public static void updatePlayers() {
        List<TeamPlayer> newlist = new ArrayList<>();
        for (PlayerListEntry player : MinecraftClient.getInstance().getNetworkHandler().getPlayerList()) {
            boolean found = false;
            for (TeamPlayer teamPlayer : teamPlayers) {
                if (teamPlayer.getPlayerUUID().equals(player.getProfile().getId())) {
                    newlist.add(teamPlayer);
                    found = true;
                    break;
                }
            }
            if (!found) {
                newlist.add(new TeamPlayer(player, 0));
            }
        }
        teamPlayers = newlist;
    }


    public static void updateServerPlayers() {
        List<TeamPlayer> newlist = new ArrayList<>();
        for (ServerPlayerEntity player : GameState.server.getPlayerManager().getPlayerList()) {
            boolean found = false;
            for (TeamPlayer teamPlayer : teamPlayers) {
                if (teamPlayer.getPlayerUUID().equals(player.getUuidAsString())) {
                    newlist.add(teamPlayer);
                    found = true;
                    break;
                }
            }
            if (!found) {
                newlist.add(new TeamPlayer(new PlayerListEntry(player.getGameProfile(), false), 0));
            }
        }
        teamPlayers = newlist;
    }

    public static int getTeamIndex(String player_uuid) {
        for (TeamPlayer teamPlayer : teamPlayers) {
            if (teamPlayer.getPlayerUUID().toString().equals(player_uuid)) {
                return teamPlayer.teamIndex;
            }
        }
        return 0;
    }

    public static List<TeamPlayer> getTeamPlayers(int teamIndex) {
        List<TeamPlayer> team = new ArrayList<>();
        for (TeamPlayer teamPlayer : teamPlayers) {
            if (teamPlayer.teamIndex == teamIndex) {
                team.add(teamPlayer);
            }
        }
        return team;
    }

    public static @Nullable TeamPlayer getPlayerByUUID(String player_uuid) {
        for (TeamPlayer teamPlayer : teamPlayers) {
            if (teamPlayer.getPlayerUUID().toString().equals(player_uuid)) {
                return teamPlayer;
            }
        }
        return null;
    }

    public static void setAllTeams(List<LockoutTeam> teams) {
        updatePlayers();
        for (TeamPlayer player: teamPlayers) {
            player.teamIndex = 0; // todo if player leaves, he will automatically get kicked out of team ?
        }
        for (LockoutTeam team: teams) {
            for (String playeruuid: team.playeruuids) {
                TeamPlayer player = getPlayerByUUID(playeruuid);
                if (player == null) {
                    continue;
                }
                player.teamIndex = team.teamId;
            }
        }
    }



}
