package net.abrikoos.lockout_bingo.goals.ride;

import net.abrikoos.lockout_bingo.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class RideHorseGoal extends StatGoal {

    public RideHorseGoal(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.HORSE_ONE_CM)) > 0;
    }
}
