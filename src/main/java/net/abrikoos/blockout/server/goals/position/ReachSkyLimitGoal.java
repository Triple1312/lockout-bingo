package net.abrikoos.blockout.server.goals.position;

import net.abrikoos.blockout.server.goals.stats.StatGoal;
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
