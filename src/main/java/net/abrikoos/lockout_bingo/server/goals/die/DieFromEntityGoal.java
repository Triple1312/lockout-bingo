package net.abrikoos.lockout_bingo.server.goals.die;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.server.listeners.PlayerDeathListener;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class DieFromEntityGoal extends LockoutGoal {

    EntityType<?> entity;

    public DieFromEntityGoal(int id, EntityType entity) {
        super(id);
        this.entity = entity;
        PlayerDeathListener.subscribe(this::validateProgress);
    }

    protected void validateProgress(ServerPlayerEntity player, DamageSource source) {
        if (completed != null) {return;}
        if (Objects.requireNonNull(source.getAttacker()).getType() == entity) {
            completed = player;
            this.notifyListeners(new LockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
        }
    }
}
