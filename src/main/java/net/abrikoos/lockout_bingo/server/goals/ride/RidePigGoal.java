package net.abrikoos.lockout_bingo.server.goals.ride;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class RidePigGoal extends StatGoal {

    public RidePigGoal(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PIG_ONE_CM)) > 0;
    }
}
