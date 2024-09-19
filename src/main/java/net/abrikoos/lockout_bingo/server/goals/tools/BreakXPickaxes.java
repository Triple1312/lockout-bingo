package net.abrikoos.lockout_bingo.server.goals.tools;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

public class BreakXPickaxes extends StatGoal {

    private final int amount;

    public BreakXPickaxes(int id, int amount) {
        super(id);
        this.amount = amount;

    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        StatHandler handler =player.getStatHandler();
        return handler.getStat(Stats.BROKEN.getOrCreateStat(Items.DIAMOND_PICKAXE)) + handler.getStat(Stats.BROKEN.getOrCreateStat(Items.WOODEN_PICKAXE)) + handler.getStat(Stats.BROKEN.getOrCreateStat(Items.NETHERITE_PICKAXE)) + handler.getStat(Stats.BROKEN.getOrCreateStat(Items.IRON_PICKAXE)) + handler.getStat(Stats.BROKEN.getOrCreateStat(Items.STONE_PICKAXE)) + handler.getStat(Stats.BROKEN.getOrCreateStat(Items.GOLDEN_PICKAXE)) >= amount;
    }
}
