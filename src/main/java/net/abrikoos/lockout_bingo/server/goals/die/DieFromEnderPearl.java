package net.abrikoos.lockout_bingo.server.goals.die;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.server.listeners.PlayerDeathListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;

public class DieFromEnderPearl extends LockoutGoal {

    public DieFromEnderPearl(int id) {
        super(id);
        PlayerDeathListener.subscribe(this::validateProgress);
    }

    private void validateProgress(ServerPlayerEntity serverPlayerEntity, DamageSource damageSource) {
        if (completed != null) return;
        // when you die from an enderpearl throw Minecraft says you die from fall damage but your fall distance is 0
        if (damageSource.isIn(DamageTypeTags.IS_FALL) && serverPlayerEntity.fallDistance == 0.0f) {
            completed = serverPlayerEntity;
            this.notifyListeners(new LockoutGoalEvent(serverPlayerEntity.getUuidAsString(), "ally", this.id));
        }
    }
}
