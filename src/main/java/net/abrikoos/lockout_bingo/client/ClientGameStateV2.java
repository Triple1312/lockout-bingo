package net.abrikoos.lockout_bingo.client;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.gui.LockoutScreens;
import net.abrikoos.lockout_bingo.client.gui.screens.CompleteFullScreenState;
import net.abrikoos.lockout_bingo.networkv2.game.GameStartPacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalBoardUpdatePacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalInfoPacket;
import net.abrikoos.lockout_bingo.networkv2.get.GetGameInfo;
import net.abrikoos.lockout_bingo.networkv2.team.TeamData;
import net.abrikoos.lockout_bingo.networkv2.team.TeamRegV2;
import net.abrikoos.lockout_bingo.server.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.server.goals.GoalListBlockItem;
import net.abrikoos.lockout_bingo.util.BlockoutList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class ClientGameStateV2 {
    static GameStartPacket game;
    @NotNull public static TeamRegV2 teamReg = new TeamRegV2();
    @NotNull public static MinecraftClient client = MinecraftClient.getInstance();

    static public long gameStartTime;
    static public boolean boardTimeOver = true;
    static int nextCountDownEvent;
    static int countDownTime = 0;
    public static boolean compass_enabled = true;
    static boolean countTimeSoundPlayed = false;
    static boolean countTimeSoundPlayed2 = false;

    public static List<GoalListItem> goals = new ArrayList<>();

    public static void startGame(GameStartPacket packet) {
        game = packet;
        // means its not a real game
        if (Objects.equals(packet.game_mode(), "")) {
            return;
        }
        goals.clear();
        buildGameBoard(packet);
        gameStartTime = game.startTime() + game.freezeTime();
        boardTimeOver = false;
        CompleteFullScreenState.selectedTab = 1;
        LockoutScreens.completeFullScreen.init();
        client.setScreen(LockoutScreens.completeFullScreen);
    }

    public static void updateBoard(GoalBoardUpdatePacket packet) {
        int justCompletedGoal = packet.justCompletedGoal();
        if (game == null) {
            ClientPlayNetworking.send(new GetGameInfo());
            return;
        }
        int oldJustCompletedGoal = game.board().justCompletedGoal();
        TeamData playerTeam;
        try {
            playerTeam = teamReg.getTeamDataByPlayerUUID(client.player.getUuidAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        for (int score : packet.scores()) {
//            if (score > packet.goals().size() / packet.scores().size()) {
//                if (playerTeam.teamUUID.equals(packet.goals().get(justCompletedGoal).completedTeamUUID()))        {
//                    client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_success")));
//                }
//                else {
//                    client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_fail")));
//                }
//            }
//        }
        // this is for higher/lower goals where highest count can swap between players in the same team
        if (justCompletedGoal == oldJustCompletedGoal && packet.goals().get(justCompletedGoal).completedTeamUUID() == packet.goals().get(oldJustCompletedGoal).completedTeamUUID()) {

        }
        else {
            try {
                if (playerTeam.teamUUID.equals(packet.goals().get(justCompletedGoal).completedTeamUUID())) {
                    client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_success")));
                }
                else {
                    client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_fail")));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        game.updateBoard(packet);
        boardListeners.forEach(listener -> listener.accept(packet));
    }

    public static void updateTeamReg(TeamRegV2 teamReg) {
        ClientGameStateV2.teamReg = teamReg;
        teamListeners.forEach(listener -> listener.accept(teamReg));
    }

    public static GoalBoardUpdatePacket getBoard() {
        return game.board();
    }

    public static List<GoalInfoPacket> getGoals() {
        return game.board().goals();
    }

    public static int getGoalCount() {
        return game.board().goals().size();
    }

    public static long gameTimeLength() {
        return System.currentTimeMillis() - game.startTime();
    }

    public static List<TeamData> getTeams() {
        try {
            List<TeamData> teams = new ArrayList<>();
            teams.add(teamReg.getTeamDataByUUID(game.team1()));
            teams.add(teamReg.getTeamDataByUUID(game.team2()));
            return teams;
        }
        catch (Exception e) { // this should never throw an exception here
            LockoutLogger.log("Error getting teams in game");
            LockoutLogger.log("Check if game exists!");
            return new ArrayList<>();
        }
    }

    public static boolean isGameRunning() {
        return game != null && !game.board().goals().isEmpty();
    }

    public static boolean gameHasStarted() {
        if (boardTimeOver) return true;
        if (gameStartTime - 1000 < System.currentTimeMillis()) {
            if (!boardTimeOver) {
                client.setScreen(null);
            }
            boardTimeOver = true;
            return true;
        }
        return false;
    }

    public static int countDownTime() {
        int time = (int) (gameStartTime - System.currentTimeMillis() ) / 1000;
        if (time < 0) return 0;
        if (time < 13 && !countTimeSoundPlayed) {
            countTimeSoundPlayed = true;
            client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:finish_countdown")));
        }
        if (time < 4 && ! countTimeSoundPlayed2) {
            countTimeSoundPlayed2 = true;
            assert client.player != null;
            client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:race_start")));
        }
        return time;
    }



    static BlockoutList<Consumer<TeamRegV2>> teamListeners = new BlockoutList<>();

    public static void subscribeToTeamsUpdate(Consumer<TeamRegV2> listener) {
        teamListeners.add(listener);
    }

    static BlockoutList<Consumer<GoalBoardUpdatePacket>> boardListeners = new BlockoutList<>();

    public static void subscribeToBoardUpdate(Consumer<GoalBoardUpdatePacket> listener) {
        boardListeners.add(listener);
    }

    public static void buildGameBoard(GameStartPacket gsp) {
        switch (gsp.game_mode()) {
            case "lockout":
                for (GoalInfoPacket goal : gsp.board().goals()) { // todo maybe in future redo with every board update
                    goals.add(GoalItemRegistry.getGoal(goal.goalID()));
                }
                break;
            case "dropshuffle":
                for (GoalInfoPacket goal : gsp.board().goals()) {
                    goals.add(new GoalListBlockItem(goal.goalName(), "", 1,List.of(), goal.goalID(), List.of(Registries.ITEM.get(Identifier.of(goal.goalID())).getDefaultStack())));
                }
                break;
        }

    }






}
