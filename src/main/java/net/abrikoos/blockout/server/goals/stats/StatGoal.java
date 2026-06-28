package net.abrikoos.blockout.server.goals.stats;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.TickListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class StatGoal extends BlockoutGoal {

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
