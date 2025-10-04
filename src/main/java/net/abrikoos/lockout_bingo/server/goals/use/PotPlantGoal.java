package net.abrikoos.lockout_bingo.server.goals.use;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class PotPlantGoal extends StatGoal {
    public PotPlantGoal(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.POT_FLOWER)) > 0;
    }
}
