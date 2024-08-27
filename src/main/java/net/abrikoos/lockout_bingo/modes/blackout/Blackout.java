package net.abrikoos.lockout_bingo.modes.blackout;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.builder.LockoutGoalBuilder;
import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class Blackout {
    public List<LockoutGoal> goals;
    public List<ServerPlayerEntity> players;
    public BlackoutStartGameInfo info;

    public Blackout(List<ServerPlayerEntity> players, LockoutGoalBuilder builder) {
        this.info = builder.generateBoard();
        this.goals = builder.buildBoard(this.info);
        for (LockoutGoal goal : goals) {
            goal.subscribe(this::onGoalComplete);
        }
        this.players = players;
    }

    public void destroy() {
        for (LockoutGoal goal : goals) {
            goal.destory();
        }
    }

    public String onGoalComplete(LockoutGoalEvent event) {
        LockoutLogger.log("Goal " + event.goalId + " completed by " + event.puuid + " for " + event.recipiant);
        String[] board = new String[25];
        for (int i = 0; i < 25; i++) {
            board[i] = this.goals.get(i).completed == null ? "00000000-0000-0000-000000000000" : "11111111-1111-1111-111111111111";
        }
        LockoutUpdateBoardInfo update = new LockoutUpdateBoardInfo(board);
        LockoutUpdateBoardPacket packet = new LockoutUpdateBoardPacket(update);
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player,packet);
        }
        return "";
    }



}
