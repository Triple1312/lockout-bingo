package net.abrikoos.lockout_bingo.client;

import net.abrikoos.lockout_bingo.client.modes.LockoutGame;
import net.abrikoos.lockout_bingo.client.modes.lockout.Lockout;
import net.abrikoos.lockout_bingo.network.game.LockoutStartGameInfo;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.abrikoos.lockout_bingo.team.PlayerTeamRegistry;
import net.abrikoos.lockout_bingo.team.TeamPlayer;
import net.abrikoos.lockout_bingo.team.TeamRegistry;
import net.minecraft.client.MinecraftClient;

import java.util.List;

public class ClientGameState {
    static Lockout gameState; // todo needs to be LockoutGame in future
    static double gameStartTime;

    public static void startLockout(LockoutStartGameInfo info) {
        gameState = new Lockout(info);
        gameStartTime = MinecraftClient.getInstance().world.getTime();
    }

    public static void endGame() {
        gameState = null;
    }

    public static boolean isGameRunning() {
        return gameState != null;
    }

    public static List<GoalListItem> getGoals() {
        return gameState.getGoals();
    }

    public static List<LockoutTeam> getTeams() {
        return gameState.getTeams();
    }

    public static void updateBoard(LockoutUpdateBoardInfo lubi) {
        gameState.lubi = lubi;
    }

    public static LockoutUpdateBoardInfo latestUpdate() {
        return gameState.latestUpdate();
    }

    public static int getTeamScore(String teamName) {
        assert TeamRegistry.getTeamString(teamName) != null;
        return getTeamScore(TeamRegistry.getTeamString(teamName).teamId);
    }

    public static int getTeamScore(int teamIndex) {
        if (gameState.lubi == null) {
            return 0;
        }
        int score = 0;
        for (String goal : gameState.lubi.goals) {
            if (goal != null) {
                TeamPlayer player = PlayerTeamRegistry.getPlayerByUUID(goal);
                if (player == null) {
                    continue;
                } else if ( player.teamIndex == teamIndex) {
                    score++;
                }
            }
        }
        return score;
    }


























}
