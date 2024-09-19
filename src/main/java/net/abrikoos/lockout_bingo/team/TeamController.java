package net.abrikoos.lockout_bingo.team;

import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.abrikoos.lockout_bingo.client.gui.LockoutScreens;
import net.abrikoos.lockout_bingo.client.TeamsChangeListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public class TeamController {

    public static void addTeam(String name) {
        TeamRegistry.addTeam(name);
    }

    public static void playerJoinTeam(String player_uuid, int teamIndex) {

        TeamPlayer player = PlayerTeamRegistry.getPlayerByUUID(player_uuid);
        if (player == null){
            PlayerTeamRegistry.updatePlayers();
            player = PlayerTeamRegistry.getPlayerByUUID(player_uuid);
        }
        if (player == null) {
            return;
        }
        if (player.teamIndex == teamIndex) { // try to put in same team
            return;
        }
        if (player.teamIndex != 0) { // remove from old team
            assert TeamRegistry.getTeam(player.teamIndex) != null;
            TeamRegistry.getTeam(player.teamIndex).removePlayer(player.getPlayerUUID().toString());
        }
        if (teamIndex == 0) { // if was not in team before
            player.teamIndex = 0;
            return;
        }
        LockoutTeam team = TeamRegistry.getTeam(teamIndex);
        assert team != null;
        team.addPlayer(player.getPlayerUUID().toString());
        player.teamIndex = teamIndex;
    }

    public static void refreshPlayers() {
        PlayerTeamRegistry.updatePlayers();
    }


    public static int playerGoalColor(String puuid) {
        return Colors.get(Objects.requireNonNull(PlayerTeamRegistry.getPlayerByUUID(puuid)).teamIndex);
    }

    public static void setAllTeams(List<LockoutTeam> teams) {
        TeamRegistry.setAllTeams(teams);
        PlayerTeamRegistry.setAllTeams(teams);
        if (teams.isEmpty()) {
            return;
        }
        for (LockoutTeam team : teams) {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(team.name));
        }
        try {
            if (MinecraftClient.getInstance().currentScreen == null) {
                return;
            }
            else if (Objects.equals(LockoutScreens.getCurrentScreen(), "main")){
                if (LockoutScreens.isOpen()) {
                    LockoutScreens.open();
                }

            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        TeamsChangeListener.registerEvent(teams);

    }


    // todo unuseded
    public static void changeTeamId(int teamindex, int newId) {
        LockoutTeam team = TeamRegistry.getTeam(teamindex);
        if (team == null) {
            return;
        }
        team.teamId = newId;
        for (String playeruuid : team.playeruuids) {
            PlayerTeamRegistry.getPlayerByUUID(playeruuid).teamIndex = newId;
        }
        GameState.sendAllTeams();
    }

}
