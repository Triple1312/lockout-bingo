package net.abrikoos.lockout_bingo.server.goals.kill;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class KillXMobsGoal extends StatGoal {

    final int amount;

    public KillXMobsGoal(int id, int amount) {
        super(id);
        this.amount = amount;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.MOB_KILLS)) >= amount;
    }
}
