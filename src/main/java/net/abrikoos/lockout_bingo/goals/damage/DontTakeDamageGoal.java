package net.abrikoos.lockout_bingo.goals.damage;

import net.abrikoos.lockout_bingo.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class DontTakeDamageGoal extends StatGoal {
    int amount;

    public DontTakeDamageGoal(int id, int amount) {
        super(id);
        this.amount = amount;

    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_TAKEN)) >= amount;
    }

    @Override
    public String recipiant() {
        return "enemy";
    }
}
