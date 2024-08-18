package net.abrikoos.lockout_bingo.goals.die;

import net.abrikoos.lockout_bingo.goals.stats.StatGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class DieByGoal extends StatGoal {

    EntityType<?> type;
    int count;

    public DieByGoal(int id, EntityType<?> type, int count) {
        super(id);
        this.type = type;
        this.count = count;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.KILLED_BY.getOrCreateStat(type)) >= count;
    }
}
