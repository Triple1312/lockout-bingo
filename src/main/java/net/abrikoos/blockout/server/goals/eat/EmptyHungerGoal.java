package net.abrikoos.blockout.server.goals.eat;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.TickListener;
import net.minecraft.server.MinecraftServer;

public class EmptyHungerGoal extends BlockoutGoal {
    public EmptyHungerGoal(int id) {
        super(id);
        TickListener.subscribe(this::validateProgress);
    }

    public void validateProgress(MinecraftServer server) {
        if (this.completed != null) return;
        for (var player : server.getPlayerManager().getPlayerList()) {
            if (player.getHungerManager().getFoodLevel() == 0) {
                this.completed(player);
            }
        }
    }
}
