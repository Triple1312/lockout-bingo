package net.abrikoos.blockout.server.goals.kill;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class KillXWithYGoal extends BlockoutGoal {

    EntityType<? extends LivingEntity> toKill;

    EntityType<?> source;

    public KillXWithYGoal(int id, EntityType<? extends LivingEntity> toKill, EntityType<?> damageSource) {
        super(id);
        this.toKill = toKill;
        this.source = damageSource;
    }


    public void validateProgress(LivingEntity target, DamageSource source) {
        if (this.completed != null) {
            return;
        }
        try {
            if (!(source.getAttacker() instanceof PlayerEntity player)) {
                return;
            }

            // Check if tokill is right
            if (toKill != null && this.toKill != target.getType()){
                return;
            }
            // Check if source is right
            if (this.source != null && this.source != source.getSource().getType()){
                return;
            }
            this.completed(player);
        } catch (Exception e) {
            return;
        }

    }
}
