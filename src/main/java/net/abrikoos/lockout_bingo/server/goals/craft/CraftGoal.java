package net.abrikoos.lockout_bingo.server.goals.craft;

import net.abrikoos.lockout_bingo.server.goals.stats.StatGoal;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class CraftGoal extends StatGoal {

    final Item item;
    final int amount;

    public CraftGoal(int id, Item item) {
        super(id);
        this.item = item;
        this.amount = 1;
    }

    public CraftGoal(int id, Item item, int amount) {
        super(id);
        this.item = item;
        this.amount = amount;
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        int crafted = player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(this.item));
        return crafted >= this.amount;
    }
}
