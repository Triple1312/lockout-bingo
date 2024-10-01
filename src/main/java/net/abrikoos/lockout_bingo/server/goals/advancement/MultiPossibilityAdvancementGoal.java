package net.abrikoos.lockout_bingo.server.goals.advancement;

import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class MultiPossibilityAdvancementGoal extends LockoutGoal {
    private final List<LockoutGoal> goals;


    public MultiPossibilityAdvancementGoal(int id, List<LockoutGoal> goals) {
        super(id);
        this.goals = goals;
        for (LockoutGoal goal : goals) {
            goal.subscribe(this::completedSubGoal);
        }
    }

    private String completedSubGoal(LockoutGoalEvent lockoutGoalEvent) {
        if (completed != null) {
            return "";
        }
        ServerPlayerEntity player = null;
        for (ServerPlayerEntity p : GameState.players) {
            if (p.getName().getString().equals(lockoutGoalEvent.puuid)) {
                player = p;
                break;
            }
        }
        if (player == null) {
            return "";
        }
        this.completed = player;
        this.notifyListeners(lockoutGoalEvent);
        for (LockoutGoal goal : goals) {
            goal.destory();
        }
        goals.clear();
        return "";
    }
}
