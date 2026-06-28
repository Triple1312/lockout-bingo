package net.abrikoos.blockout.server.goals.damage;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.PlayerDamageListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;

public class SnowBallHitGoal  extends BlockoutGoal {


    public SnowBallHitGoal(int id) {
        super(id);
        PlayerDamageListener.subscribe(this::validateProgress);
    }

    @Override
    public String recipiant() {
        return "enemy";
    }

    public void validateProgress(PlayerEntity player, DamageSource source, float amount) {
        if (this.completed != null) return;
        if(source.getSource() instanceof SnowballEntity)
            this.completed(player);
    }
}
