package net.abrikoos.blockout.server.goals.die;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.abrikoos.blockout.server.listeners.PlayerDeathListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;

public class DieByTag extends BlockoutGoal {

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
            this.notifyListeners(new BlockoutGoalEvent(serverPlayerEntity.getUuidAsString(), "ally", this.id));
        }
    }
}
