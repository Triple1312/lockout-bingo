package net.abrikoos.blockout.server.goals.tame;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.TameListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.lang.reflect.Type;

public class TameAnimalGoal extends BlockoutGoal {
    final EntityType entityclass;

    public TameAnimalGoal(int id, EntityType entity) {
        super(id);
        this.entityclass = entity;
        TameListener.subscribe(this::validateProgress);
    }

    protected void validateProgress(PlayerEntity owner, AnimalEntity entity) {
        if (this.completed != null) {
            return;
        }
        if (this.entityclass == entity.getType()) {
            this.completed(owner);
        }
    }





}
