package net.abrikoos.lockout_bingo.server.goals.tools;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

import java.util.ArrayList;
import java.util.List;

public class BreakItemsGoal extends StatGoal {

    List<Item> breakables;
    int count;

    public BreakItemsGoal(int id, Item breakable, int count) {
        super(id);
        this.breakables = new ArrayList<>(); this.breakables.add(breakable);
        this.count = count;
    }

    public BreakItemsGoal(int id, List<Item> breakables, int count) {
        super(id);
        this.breakables = breakables;
        this.count = count;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        StatHandler handler = player.getStatHandler();
        int broken_count = 0;
        for (Item item: breakables) {
            broken_count += handler.getStat(Stats.BROKEN.getOrCreateStat(item));
        }
        return broken_count >= count;

    }
}
