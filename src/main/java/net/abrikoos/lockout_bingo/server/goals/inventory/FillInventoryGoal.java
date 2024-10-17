package net.abrikoos.lockout_bingo.server.goals.inventory;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.PlayerInventoryListener;
import net.abrikoos.lockout_bingo.util.BlockoutList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;

public class FillInventoryGoal extends LockoutGoal {

    public FillInventoryGoal(int id) {
        super(id);
        PlayerInventoryListener.subscribe(this::validateProgress);
    }

    public void validateProgress(PlayerEntity player, ItemStack stack, Integer integer, Boolean added) {
        if (this.completed != null) {
            return;
        }
        DefaultedList<ItemStack> inventory = player.getInventory().main;
        BlockoutList<ItemStack> blockoutList = new BlockoutList<>();
        for (ItemStack itemStack : inventory) {
            if (itemStack.isEmpty() || itemStack.getItem().equals(Items.AIR)) {
                continue;
            }
            else {
                blockoutList.add(itemStack);
            }
        }
        int size = blockoutList.size();
        if (size < 36) {
            return;
        }
        this.completed(player);
    }

}
