package net.abrikoos.blockout.server.goals.die;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.abrikoos.blockout.server.listeners.PlayerDeathListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class DieGoal extends BlockoutGoal {

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
