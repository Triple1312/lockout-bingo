package net.abrikoos.lockout_bingo.networkv2.team;


import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;

import java.util.List;

// This class only exists so I dont accidentally use Server methods on the client
public class ServerTeamRegV2 extends TeamRegV2 {

    MinecraftServer server;

    public ServerTeamRegV2(MinecraftServer server) {
        this.server = server;
    }

    public void addPlayerToTeam(String playerUUID, String teamUUID) throws Exception {
        PlayerData player = getPlayerDataByUUID(playerUUID); // just so that it throws if player not found
        TeamData team = getTeamDataByUUID(teamUUID);
        team.addPlayer(playerUUID);
        sendClientsUpdate();
    }

    public void addPlayerData(PlayerData player) {
        this.players.add(player);
        sendClientsUpdate();
    }

    public void playerHasConnected(String playerUUID) throws Exception {
        try {
            PlayerData player = getPlayerDataByUUID(playerUUID);
            player.connected = true;
            sendClientsUpdate();
        }
        catch (Exception e) {
            throw new Exception("Player not found when trying to set connected. \n You need to add the player to the registry first.");
        }
    }

    public void playerHasDisconnected(String playerUUID) throws Exception {
        try {
            PlayerData player = getPlayerDataByUUID(playerUUID);
            player.connected = false;
            sendClientsUpdate();
        }
        catch (Exception e) {
            throw new Exception("Player not found when trying to set disconnected. \n You need to add the player to the registry first.");
        }
    }

    public void addTeamData(TeamData team) {
        this.teams.add(team);
        sendClientsUpdate();
    }

    // todo check dup name
    public void createNewTeam(String teamName) {
        String newUUID = java.util.UUID.randomUUID().toString();
        List<Integer> possibleColors = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (TeamData team : teams) {
            possibleColors.remove(team.teamColor);
        }
        int color = possibleColors.getFirst();
        this.addTeamData(new TeamData(teamName, color, newUUID));
        // client update sent in addTeamData
    }

    public void removeTeam(String teamUUID) {
        this.teams.removeIf(team -> team.teamUUID.equals(teamUUID));
        sendClientsUpdate();
    }


    public void sendClientsUpdate() {
        server.getPlayerManager().getPlayerList().forEach(player -> {
            ServerPlayNetworking.send(player, this);
        });
    }

    public void changeTeamColor(String teamUUID, int newColor) {
        try {
            TeamData team = getTeamDataByUUID(teamUUID);
            team.teamColor = newColor;
            sendClientsUpdate();
        }
        catch (Exception e) {
            // todo log
        }
    }
}
