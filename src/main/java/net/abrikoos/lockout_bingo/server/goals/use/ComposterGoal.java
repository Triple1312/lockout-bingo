package net.abrikoos.lockout_bingo.server.goals.use;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.ComposterUseListener;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComposterGoal extends LockoutGoal {

    public ComposterGoal(int id) {
        super(id);
        ComposterUseListener.subscribe(this::validateProgress);
    }

    public void validateProgress(Entity player, BlockState state, World world, BlockPos pos) {
        if (completed != null) return;
        if (player instanceof PlayerEntity)
            completed((PlayerEntity) player);
    }

}
