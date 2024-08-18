package net.abrikoos.lockout_bingo.goals.movement;

import net.abrikoos.lockout_bingo.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class DontJumpGoal extends StatGoal {
    public DontJumpGoal(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.JUMP)) > 0;
    }

    @Override
    public String recipiant() {
        return "enemy";
    }
}
