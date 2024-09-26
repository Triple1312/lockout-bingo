package net.abrikoos.lockout_bingo.server.goals.inventory;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class FillInventoryGoal extends LockoutGoal {

    public FillInventoryGoal(int id) {
        super(id);
    }

    public void validateProgress(PlayerEntity player, ItemStack stack, Boolean added) {
        if (this.completed != null) {
            return;
        }

        if (player.getInventory().size() < 36) {
            return;
        }
        this.completed(player);

    }

}
