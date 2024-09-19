package net.abrikoos.lockout_bingo.server.goals.position;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;

public class ReachSkyLimitGoal extends StatGoal {

    public ReachSkyLimitGoal(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getY() >= 320;
    }
}
