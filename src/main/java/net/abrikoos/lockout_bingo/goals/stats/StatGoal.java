package net.abrikoos.lockout_bingo.goals.stats;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.listeners.TickListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class StatGoal extends LockoutGoal {

    public StatGoal(int id) {
        super(id);
        TickListener.subscribe(this::checkCompletion);
    }

    private void checkCompletion(MinecraftServer minecraftserver) {
        if (completed != null) { return; }
        minecraftserver.getPlayerManager().getPlayerList().forEach(player -> {
            if (validateProgress(player)) {
                this.completed(player);
            }
        });
    }

    abstract protected boolean validateProgress(ServerPlayerEntity player);

    @Override
    public void destory() {
        TickListener.unsubscribe(this::checkCompletion);
    }

}
