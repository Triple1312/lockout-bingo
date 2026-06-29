package net.abrikoos.blockout.server.goals.obtain;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.abrikoos.blockout.server.listeners.TickListener;

import java.util.List;

public class ObtainXofSetItemsGoal extends BlockoutGoal {
    int count;
    List<Item> items;

    public ObtainXofSetItemsGoal(int id, List<Item> items, int count) {
        super(id);
        this.items = items;
        this.count = count;
        TickListener.subscribe(this::checkCompletion);
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
                this.notifyListeners(new BlockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
                return;
            }
        }
    }
}
