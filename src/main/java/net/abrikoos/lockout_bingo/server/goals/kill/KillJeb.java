package net.abrikoos.lockout_bingo.server.goals.kill;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.server.listeners.EntityKillListener;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class KillJeb extends LockoutGoal {
    public KillJeb(int id) {
        super(id);
        EntityKillListener.subscribe(this::checkName);
    }

    protected void checkName(ServerPlayerEntity player, LivingEntity target) {
        if (completed != null) { return; }
        if (target.getType() != null && target.getType().equals(EntityType.SHEEP) && target.getName().getString().equals("jeb_")) {
            this.completed = player;
            this.notifyListeners(new LockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
        }
    }
}
