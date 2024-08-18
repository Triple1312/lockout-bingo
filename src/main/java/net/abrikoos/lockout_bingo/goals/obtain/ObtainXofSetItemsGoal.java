package net.abrikoos.lockout_bingo.goals.obtain;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.List;

public class ObtainXofSetItemsGoal extends LockoutGoal {
    int count;
    List<Item> items;

    public ObtainXofSetItemsGoal(int id, List<Item> items, int count) {
        super(id);
        this.items = items;
        this.count = count;
        ServerTickEvents.START_SERVER_TICK.register(this::checkCompletion);
    }


    private void checkCompletion(MinecraftServer minecraftserver) {
        if (completed != null) { return; }
        for (ServerPlayerEntity player : minecraftserver.getPlayerManager().getPlayerList()) {
            int[] itemchecks = new int[items.size()];
            PlayerInventory inventory = player.getInventory();
            for (int j = 0; j < items.size(); j++) {
                for (int i = 0; i < inventory.size(); i++) {
                    Item item = inventory.getStack(i).getItem();
                    if (inventory.getStack(i).getItem() == items.get(j)) {
                        itemchecks[j] = 1;
                        break;
                    }
                }
            }
            int sum = 0;
            for (int itemcheck : itemchecks) {
                sum += itemcheck;
            }
            if (sum >= count) {
                completed = player;
                this.notifyListeners(new LockoutGoalEvent(player.getNameForScoreboard(), "ally", this.id));
                return;
            }
        }
    }
}
