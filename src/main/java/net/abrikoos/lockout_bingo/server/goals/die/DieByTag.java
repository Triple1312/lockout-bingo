package net.abrikoos.lockout_bingo.server.goals.die;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.server.listeners.PlayerDeathListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;

public class DieByTag extends LockoutGoal {

    TagKey<DamageType> tag;

    public DieByTag(int id, TagKey<DamageType> tag) {
        super(id);
        this.tag = tag;
        PlayerDeathListener.subscribe(this::validateProgress);
    }

    private void validateProgress(ServerPlayerEntity serverPlayerEntity, DamageSource damageSource) {
        if (completed != null) {return;}
        if (damageSource.isIn(this.tag)) {
            completed = serverPlayerEntity;
            this.notifyListeners(new LockoutGoalEvent(serverPlayerEntity.getUuidAsString(), "ally", this.id));
        }
    }
}
