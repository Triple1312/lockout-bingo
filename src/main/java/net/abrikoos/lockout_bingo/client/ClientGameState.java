//package net.abrikoos.lockout_bingo.client;
//
//import net.abrikoos.lockout_bingo.client.gui.LockoutScreens;
//import net.abrikoos.lockout_bingo.client.gui.screens.CompleteFullScreenState;
//import net.abrikoos.lockout_bingo.client.modes.lockout.Lockout;
//import net.abrikoos.lockout_bingo.network.game.LockoutStartGameInfo;
//import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
//import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
//import net.abrikoos.lockout_bingo.util.BlockoutList;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.sound.SoundEvent;
//import net.minecraft.util.Identifier;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//@Environment(EnvType.CLIENT)
//public class ClientGameState {
//    static Lockout gameState; // todo needs to be LockoutGame in future
//    static public long gameStartTime;
//    static public boolean boardTimeOver = true;
////    static ScheduledExecutorService sheduler = Executors.newSingleThreadScheduledExecutor();
//    static int nextCountDownEvent;
//    static int countDownTime = 0;
//    static MinecraftClient client;
//    public static boolean compass_enabled = true;
//
//    public static void startLockout(LockoutStartGameInfo info) {
//        gameState = new Lockout(info);
//        client = MinecraftClient.getInstance();
//        gameStartTime = info.startTime;
//        nextCountDownEvent = 12;
//        countDownTime = 60;
//        boardTimeOver = false;
//        CompleteFullScreenState.selectedTab = 1;
//        LockoutScreens.completeFullScreen.init();
//        client.setScreen(LockoutScreens.completeFullScreen);
//    }
//
//    public static void endGame() {
//        gameState = null;
//    }
//
//    public static boolean isGameRunning() {
//        return gameState != null;
//    }
//
//    public static BlockoutList<GoalListItem> getGoals() {
//        return gameState.getGoals();
//    }
//
//    public static BlockoutList<UnitedTeamRegistry.Team> getTeams() {
//        return gameState.getTeams();
//    }
//
//    public static void updateBoard(LockoutUpdateBoardInfo lubi) {
//        gameState.lubi = lubi;
//    }
//
//    public static LockoutUpdateBoardInfo latestUpdate() {
//        return gameState.latestUpdate();
//    }
//
//    public static int getTeamScore(String teamName) {
//        int teamIndex = getTeams().firstIndexWhere(team -> team.teamName() == teamName);
//        return gameState.getScores().get(teamIndex);
//    }
//
//    public static int getTeamScore(int teamIndex) {
//        if (gameState.lubi == null) {
//            return 0;
//        }
//        int score = 0;
//        for (String goal : gameState.lubi.goals) {
//            if (goal != null) {
//                UTeamPlayer player = UnitedTeamRegistry.getTeamPlayerByUUID(UUID.fromString(goal));
//                if (player == null) {
//                    continue;
//                } else if ( player.teamIndex == teamIndex) {
//                    score++;
//                }
//            }
//        }
//        return score;
//    }
//
//    public static int countDown() {
//        if (boardTimeOver) {
//            return 0;
//        }
//        int s_time = (int) (60 - (getPlayTime()/1000));
//        if (s_time < nextCountDownEvent) {
//            if (countDownTime == 12) {
//                client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:finish_countdown")));
//                nextCountDownEvent = 3;
//            }
//            if (countDownTime == 3) {
//                client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:race_start")));
//                nextCountDownEvent = 0;
//            }
//            if (countDownTime <= 0) {
//                boardTimeOver = true;
//                MinecraftClient.getInstance().setScreen(null);
//            }
//        }
//        countDownTime = s_time;
//        return s_time;
//    }
//
//    public static long getPlayTime() {
//        MinecraftClient client = MinecraftClient.getInstance();
//        return System.currentTimeMillis() - gameStartTime;
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
