package net.abrikoos.lockout_bingo.server.goals.tools;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

public class BreakItemGoal extends StatGoal {

    Item breakable;
    int count;

    public BreakItemGoal(int id, Item breakable, int count) {
        super(id);
        this.breakable = breakable;
        this.count = count;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        StatHandler handler = player.getStatHandler();
        return handler.getStat(Stats.BROKEN.getOrCreateStat(breakable)) >= count;

    }
}
