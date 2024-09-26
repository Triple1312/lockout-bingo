package net.abrikoos.lockout_bingo.server.goals.tame;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.TameListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.lang.reflect.Type;

public class TameAnimalGoal extends LockoutGoal {
    final EntityType entityclass;

    public TameAnimalGoal(int id, EntityType entity) {
        super(id);
        this.entityclass = entity;
        TameListener.subscribe(this::validateProgress);
    }

    protected void validateProgress(PlayerEntity owner, TameableEntity entity) {
        if (this.completed != null) {
            return;
        }
        if (this.entityclass == entity.getType()) {
            this.completed(owner);
        }
    }





}
