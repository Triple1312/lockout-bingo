package net.abrikoos.lockout_bingo.server.goals.kill;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.EntityKillListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

public class KillEntityGoal extends LockoutGoal {

    final EntityType entityType;

    public KillEntityGoal(int id, EntityType entity) {
        super(id);
        this.entityType = entity;
        EntityKillListener.subscribe(this::validateProgress);
    }

    protected void validateProgress(PlayerEntity player, Entity entity) {
        if (this.completed != null) {
            return;
        }
        if (this.entityType == entity.getType()) {
            this.completed(player);
        }
    }
}
