package net.abrikoos.lockout_bingo.networkv2.team;


import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// This class only exists so I dont accidentally use Server methods on the client
public class ServerTeamRegV2 extends TeamRegV2 {



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
        List<Integer> possibleColors = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        for (TeamData team : teams) {
            possibleColors.removeIf(s -> s == team.teamColor);
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
        GameState.server.getPlayerManager().getPlayerList().forEach(player -> {
            ServerPlayNetworking.send(player, this);
        });
    }

    public void changeTeamColor(String teamUUID, int newColor) throws Exception {
        if (!colorIsAvailable(newColor)) {
            throw new IllegalArgumentException("Color is already in use");
        }
        TeamData team = getTeamDataByUUID(teamUUID);
        team.teamColor = newColor;
        sendClientsUpdate();
    }

    public boolean colorIsAvailable(int color) {
        for (TeamData team : teams) {
            if (team.teamColor == color) {
                return false;
            }
        }
        return true;
    }

    public void removePlayerFromTeam(String playerUUID) throws Exception {
        for (TeamData team : teams) {
            if (team.playerUUIDs.contains(playerUUID)) {
                team.removePlayer(playerUUID);
                sendClientsUpdate();
                return;
            }
        }
        throw new Exception("Player not found in any team");
    }
    public void rotateTeamColor(String teamUUID) {

        int colorAmount = Colors.colors.length;
        int currentTeamColor;
        try {
            currentTeamColor = getTeamDataByUUID(teamUUID).teamColor;
        }
        catch (Exception e) {
            LockoutLogger.log("Error rotating team color because team doesnt exist");
            return;
        }
        int iteratorcolor = currentTeamColor + 1;
        try {
            while (true) {
                if (iteratorcolor == currentTeamColor) { // means no colors are available
                    break;
                }

                if (iteratorcolor > colorAmount) {
                    iteratorcolor = 1;
                }

                if (!isColorInUse(iteratorcolor)) {
                getTeamDataByUUID(teamUUID).teamColor = iteratorcolor;
                sendClientsUpdate();
                break;
                }

                iteratorcolor++;
            }
        }
        catch (Exception e) {
            LockoutLogger.log("Error rotating team color");
        }

    }



}
