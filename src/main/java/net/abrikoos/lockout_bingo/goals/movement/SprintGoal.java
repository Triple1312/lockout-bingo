package net.abrikoos.lockout_bingo.goals.movement;

import net.abrikoos.lockout_bingo.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class SprintGoal extends StatGoal {
    int cm_criterion;


    public SprintGoal(int id, int cm) {
        super(id);
        cm_criterion = cm;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.SPRINT_ONE_CM)) >= cm_criterion;
    }

}
