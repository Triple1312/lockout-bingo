package net.abrikoos.blockout.server.goals.use;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.ComposterUseListener;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComposterGoal extends BlockoutGoal {

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
