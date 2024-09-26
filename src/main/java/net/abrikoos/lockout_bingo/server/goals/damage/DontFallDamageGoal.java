package net.abrikoos.lockout_bingo.server.goals.damage;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.PlayerDamageListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;

public class DontFallDamageGoal extends LockoutGoal {

    public DontFallDamageGoal(int id) {
        super(id);

        PlayerDamageListener.subscribe(this::validateProgress);
    }

    public void validateProgress(PlayerEntity player, DamageSource source, float amount) {
        if (this.completed != null) return;
        if(source.getTypeRegistryEntry() == DamageTypes.FALL)
            this.completed(player);
    }


}
