package net.abrikoos.blockout.server.goals.kill;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.EntityKillListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class KillEntityGoal extends BlockoutGoal {

    final EntityType<? extends LivingEntity> entityType;

    public KillEntityGoal(int id, EntityType<? extends LivingEntity> entity) {
        super(id);
        this.entityType = entity;
        EntityKillListener.subscribe(this::validateProgress);
    }

    protected void validateProgress(LivingEntity entity, DamageSource source) {
        if (this.completed != null) {
            return;
        }
        if (!(source.getAttacker() instanceof PlayerEntity player)) {
            return;
        }
        if (this.entityType == entity.getType()) {
            this.completed(player);
        }
    }
}
