package net.abrikoos.lockout_bingo.server.goals.movement;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class ElytraFlightGoal extends StatGoal {

    int distance;

    public ElytraFlightGoal(int id, int distance) {
        super(id);
        this.distance = distance;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.AVIATE_ONE_CM)) > distance;
    }
}
