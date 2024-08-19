package net.abrikoos.lockout_bingo.goals.position;

import net.abrikoos.lockout_bingo.goals.stats.StatGoal;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class ReachBedrockGoal extends StatGoal {

    public ReachBedrockGoal(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        if (player.getY() < -58) {
            World world = player.getWorld();
            return world.getBlockState(player.getBlockPos().down()).isOf(Blocks.BEDROCK);
        }
        return false;
    }
}
