package net.abrikoos.lockout_bingo.server.goals.inventory;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.PlayerInventoryListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FillUniqueItemsInventoryGoal extends LockoutGoal {

    public FillUniqueItemsInventoryGoal(int id) {
        super(id);
        PlayerInventoryListener.subscribe(this::validateProgress);
    }

    public void validateProgress(PlayerEntity player, ItemStack stack, int slot, Boolean added) {
        if (this.completed != null) {
            return;
        }
        if (player.getInventory().size() < 36) {
            return;
        }
        List<Item> uniqueItems = new ArrayList<>();
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (itemStack.isEmpty()) {
                return;
            }
            if (!uniqueItems.contains(itemStack.getItem())) {
                uniqueItems.add(itemStack.getItem());
            }
            else {
                return;
            }
        }
        this.completed(player);
    }
}
