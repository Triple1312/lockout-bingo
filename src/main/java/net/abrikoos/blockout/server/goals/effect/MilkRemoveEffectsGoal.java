package net.abrikoos.blockout.server.goals.effect;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.items.MilkBucketUseListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MilkRemoveEffectsGoal extends BlockoutGoal {

    public MilkRemoveEffectsGoal(int id) {
        super(id);
        MilkBucketUseListener.subscribe(this::validateProgress);
    }


    public void validateProgress(World world, PlayerEntity player) {
        if (completed != null) return;
        if (!player.getActiveStatusEffects().isEmpty()) {
            completed(player);
        }
    }
}
