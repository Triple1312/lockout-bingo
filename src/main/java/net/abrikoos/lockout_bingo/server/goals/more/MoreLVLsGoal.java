package net.abrikoos.lockout_bingo.server.goals.more;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.TickListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

public class MoreLVLsGoal extends LockoutGoal {

    PlayerEntity holder = null;

    public MoreLVLsGoal(int id) {
        super(id);
        TickListener.subscribe(this::validateProgress);
    }

    public void validateProgress(MinecraftServer server) {
        int max = 0;
        PlayerEntity max_holder = null;
        for (PlayerEntity p : server.getPlayerManager().getPlayerList()) {
            if (p.experienceLevel > max) {
                max_holder = p;
                max = p.experienceLevel;
            }
            else if(p.experienceLevel == max && p == holder) {
                max_holder = p;
            }
        }

        if (max == 0 && holder != null) {
            completed(null);
            holder = null;
        }
        else if( holder != max_holder) {
            completed(max_holder);
        }
    }
}
