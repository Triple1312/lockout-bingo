package net.abrikoos.lockout_bingo.server.goals.position;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.server.network.ServerPlayerEntity;

public class FallGoal extends StatGoal {

    private final int height;

    public FallGoal(int id, int height) {
        super(id);
        this.height = height;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.fallDistance >= height;
    }
}
