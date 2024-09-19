package net.abrikoos.lockout_bingo.server.goals.obtain;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class ObtainMultiItemGoal extends LockoutGoal {
    List<Item> items;

    public ObtainMultiItemGoal(int id, List<Item> items) {
        super(id);
        this.items = items;
        ServerTickEvents.START_SERVER_TICK.register(this::checkCompletion);
    }

    private void checkCompletion(MinecraftServer minecraftserver) {
        if (completed != null) { return; }
        for (ServerPlayerEntity player : minecraftserver.getPlayerManager().getPlayerList()) {
            int[] itemchecks = new int[items.size()];
            PlayerInventory inventory = player.getInventory();
            for (int j = 0; j < items.size(); j++) {
                for (int i = 0; i < inventory.size(); i++) {
                    if (inventory.getStack(i).getItem().getName() == items.get(j).getName()) {
                        itemchecks[j] = 1;
                        break;
                    }
                }
            }
            boolean allItemsFound = true;
            for (int itemcheck : itemchecks) {
                if (itemcheck == 0) {
                    allItemsFound = false;
                    break;
                }
            }
            if (allItemsFound) {
                this.notifyListeners(new LockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
                completed = player;
                return;
            }
        }

    }
}
