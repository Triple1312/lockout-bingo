package net.abrikoos.blockout.server.goals.kill;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.abrikoos.blockout.server.listeners.EntityKillListener;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class KillJeb extends BlockoutGoal {
    public KillJeb(int id) {
        super(id);
        EntityKillListener.subscribe(this::checkName);
    }

    protected void checkName(LivingEntity target, DamageSource source) {
        if (completed != null) { return; }
        if (target.getType() != null && target.getType().equals(EntityType.SHEEP) && target.getName().getString().equals("jeb_") && source.getAttacker() instanceof ServerPlayerEntity player) {
            this.completed = player;
            this.notifyListeners(new BlockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
        }
    }
}
