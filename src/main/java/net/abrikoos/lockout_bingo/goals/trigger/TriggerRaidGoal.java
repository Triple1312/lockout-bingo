package net.abrikoos.lockout_bingo.goals.trigger;

import net.abrikoos.lockout_bingo.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class TriggerRaidGoal extends StatGoal {

    public TriggerRaidGoal(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.RAID_TRIGGER)) > 0;
    }
}
