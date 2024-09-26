package net.abrikoos.lockout_bingo.server.goals.mine;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class MineGoal extends StatGoal {
    final Block block;
    final int amount;

    public MineGoal(int id, Block block, int amount) {
        super(id);
        this.block = block;
        this.amount = amount;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.MINED.getOrCreateStat(Blocks.DIAMOND_ORE)) >= amount;
    }
}
