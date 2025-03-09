package net.abrikoos.lockout_bingo.server.goals.die;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.server.listeners.PlayerDeathListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class DieGoal extends LockoutGoal {

    public DieGoal(int id) {
        super(id);
        PlayerDeathListener.subscribe(this::validateProgress);
    }


    public void validateProgress(ServerPlayerEntity player, DamageSource source) {
        if (completed != null) return;
        this.completed(player);

    }

    @Override
    public void destory() {
        super.destory();
        PlayerDeathListener.unsubscribe(this::validateProgress);
    }

}
