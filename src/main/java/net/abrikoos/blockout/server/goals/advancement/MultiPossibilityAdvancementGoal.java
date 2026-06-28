package net.abrikoos.blockout.server.goals.advancement;

import net.abrikoos.blockout.server.gamestate.GameState;
import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class MultiPossibilityAdvancementGoal extends BlockoutGoal {
    private final List<BlockoutGoal> goals;


    public MultiPossibilityAdvancementGoal(int id, List<BlockoutGoal> goals) {
        super(id);
        this.goals = goals;
        for (BlockoutGoal goal : goals) {
            goal.subscribe(this::completedSubGoal);
        }
    }

    private String completedSubGoal(BlockoutGoalEvent BlockoutGoalEvent) {
        if (completed != null) {
            return "";
        }
        ServerPlayerEntity player = null;
        for (ServerPlayerEntity p : GameState.players()) {
            if (p.getName().getString().equals(BlockoutGoalEvent.puuid)) {
                player = p;
                break;
            }
        }
        if (player == null) {
            return "";
        }
        this.completed = player;
        this.notifyListeners(BlockoutGoalEvent);
        for (BlockoutGoal goal : goals) {
            goal.destory();
        }
        goals.clear();
        return "";
    }
}
