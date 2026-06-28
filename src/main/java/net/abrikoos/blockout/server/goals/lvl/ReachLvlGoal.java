package net.abrikoos.blockout.server.goals.lvl;

import net.abrikoos.blockout.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;

public class ReachLvlGoal extends StatGoal {

    final int lvl;

    public ReachLvlGoal(int id, int lvl) {
        super(id);
        this.lvl = lvl;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.experienceLevel >= lvl;
    }
}
