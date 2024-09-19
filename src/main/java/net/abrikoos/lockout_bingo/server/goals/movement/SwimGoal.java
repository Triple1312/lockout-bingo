package net.abrikoos.lockout_bingo.server.goals.movement;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class SwimGoal extends StatGoal {
    int cm_criterion;


    public SwimGoal(int id, int cm) {
        super(id);
        cm_criterion = cm;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.SWIM_ONE_CM)) >= cm_criterion;
    }
}
