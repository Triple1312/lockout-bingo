package net.abrikoos.blockout.server.goals.die;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.abrikoos.blockout.server.listeners.PlayerDeathListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;

public class DieFromEnderPearl extends BlockoutGoal {

    public DieFromEnderPearl(int id) {
        super(id);
        PlayerDeathListener.subscribe(this::validateProgress);
    }

    private void validateProgress(ServerPlayerEntity serverPlayerEntity, DamageSource damageSource) {
        if (completed != null) return;
        // when you die from an enderpearl throw Minecraft says you die from fall damage but your fall distance is 0
        if (damageSource.isIn(DamageTypeTags.IS_FALL) && serverPlayerEntity.fallDistance == 0.0f) {
            completed = serverPlayerEntity;
            this.notifyListeners(new BlockoutGoalEvent(serverPlayerEntity.getUuidAsString(), "ally", this.id));
        }
    }
}
