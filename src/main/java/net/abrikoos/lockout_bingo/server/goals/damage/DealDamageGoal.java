package net.abrikoos.lockout_bingo.server.goals.damage;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class DealDamageGoal extends StatGoal {

    int amount;

    public DealDamageGoal(int id, int amount) {
        super(id);
        this.amount = amount;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_DEALT)) >= amount;
    }
}
