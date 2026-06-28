package net.abrikoos.blockout.server.goals.die;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.abrikoos.blockout.server.listeners.PlayerDeathListener;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class DieFromEntityGoal extends BlockoutGoal {

    EntityType<?> entity;

    public DieFromEntityGoal(int id, EntityType entity) {
        super(id);
        this.entity = entity;
        PlayerDeathListener.subscribe(this::validateProgress);
    }

    protected void validateProgress(ServerPlayerEntity player, DamageSource source) {
        if (completed != null) {return;}
        if (source.getAttacker() == null) {return;}
        if (Objects.requireNonNull(source.getAttacker()).getType() == entity) {
            completed = player;
            this.notifyListeners(new BlockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
        }
    }
}
