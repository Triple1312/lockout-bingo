package net.abrikoos.lockout_bingo.server.goals.armor;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.PlayerInventoryListener;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class WearColoredArmorGoal extends LockoutGoal {

    public WearColoredArmorGoal(int id) {
        super(id);
        PlayerInventoryListener.subscribe(this::validateProgress);
    }

    private void validateProgress(PlayerEntity playerEntity, ItemStack itemStack, Integer integer, Boolean added) {
        if (completed != null) { return; }
        if (added) {
            if (itemStack.getItem() instanceof ArmorItem armor) {
                if(itemStack.getComponents().contains(DataComponentTypes.DYED_COLOR)) {
                    this.completed(playerEntity);
                }
            }
        }


    }



}
