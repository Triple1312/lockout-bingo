package net.abrikoos.lockout_bingo.server.goals.mine;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class MineDiamondOre extends StatGoal {

    public MineDiamondOre(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.MINED.getOrCreateStat(Blocks.DIAMOND_ORE)) > 0 || player.getStatHandler().getStat(Stats.MINED.getOrCreateStat(Blocks.DEEPSLATE_DIAMOND_ORE)) > 0;
    }
}
