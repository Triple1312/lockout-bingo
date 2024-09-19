package net.abrikoos.lockout_bingo.server.goals.movement;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class DontCrouch extends StatGoal {

    public DontCrouch(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.SNEAK_TIME)) > 0;
    }

    @Override
    public String recipiant() {
        return "enemy";
    }

}
