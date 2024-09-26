package net.abrikoos.lockout_bingo.server.goals.eat;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.TickListener;
import net.minecraft.server.MinecraftServer;

public class EmptyHungerGoal extends LockoutGoal {
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
