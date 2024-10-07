package net.abrikoos.lockout_bingo.server.goals.effect;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.items.MilkBucketUseListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MilkRemoveEffectsGoal extends LockoutGoal {

    public MilkRemoveEffectsGoal(int id) {
        super(id);
        MilkBucketUseListener.subscribe(this::validateProgress);
    }


    public void validateProgress(World world, PlayerEntity player, Hand hand) {
        if (completed != null) return;
        if (!player.getActiveStatusEffects().isEmpty()) {
            completed(player);
        }
    }
}
