package net.abrikoos.lockout_bingo.server.goals.die;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

public class DieMulGoal extends StatGoal {

    final int count;

    public DieMulGoal(int id, int count) {
        super(id);
        this.count = count;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        StatHandler handler =player.getStatHandler();
        return handler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)) >= count;
    }
}
