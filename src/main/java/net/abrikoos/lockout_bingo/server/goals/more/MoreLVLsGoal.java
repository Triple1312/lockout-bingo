package net.abrikoos.lockout_bingo.server.goals.more;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.TickListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

//todo maybe dont need ticklistener
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
//            else if(p.experienceLevel == max && p == holder) {
//                max_holder = p;
//            }
        }

        if (max_holder == null) {
            if (holder == null) {
                return;
            }
            holder = null;
            completed(null);
            return;

        }
        if (holder == null) {
            holder = max_holder;
            completed(max_holder);
        }
        else if (holder.experienceLevel < max) {
            completed(max_holder);
            holder = max_holder;
        }
    }
}
