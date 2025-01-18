package net.abrikoos.lockout_bingo.networkv2.team;


import java.util.List;

// This class only exists so I dont accidentally use Server methods on the client
public class ServerTeamRegV2 extends TeamRegV2 {

    public void addPlayerToTeam(String playerUUID, String teamUUID) throws Exception {
        PlayerData player = getPlayerDataByUUID(playerUUID); // just so that it throws if player not found
        TeamData team = getTeamDataByUUID(teamUUID);
        team.addPlayer(playerUUID);
    }

    public void addPlayerData(PlayerData player) {
        this.players.add(player);
    }

    public void playerHasConnected(String playerUUID) throws Exception {
        try {
            PlayerData player = getPlayerDataByUUID(playerUUID);
            player.connected = true;
        }
        catch (Exception e) {
            throw new Exception("Player not found when trying to set connected. \n You need to add the player to the registry first.");
        }
    }

    public void playerHasDisconnected(String playerUUID) throws Exception {
        try {
            PlayerData player = getPlayerDataByUUID(playerUUID);
            player.connected = false;
        }
        catch (Exception e) {
            throw new Exception("Player not found when trying to set disconnected. \n You need to add the player to the registry first.");
        }
    }

    public void addTeamData(TeamData team) {
        this.teams.add(team);
    }

    public void createNewTeam(String teamName) {
        String newUUID = java.util.UUID.randomUUID().toString();
        List<Integer> possibleColors = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (TeamData team : teams) {
            possibleColors.remove(team.teamColor);
        }
        int color = possibleColors.getFirst();
        this.addTeamData(new TeamData(teamName, color, newUUID));
    }
}
