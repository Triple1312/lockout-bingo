package net.abrikoos.lockout_bingo.server.goals.armor;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.PlayerInventoryListener;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class WearArmorComponentGoal extends LockoutGoal {

    ComponentType<?> componentType;
    int amount;

    public WearArmorComponentGoal(int id, ComponentType<?> componentType, int amount) {
        super(id);
        this.componentType = componentType;
        this.amount = amount;
        PlayerInventoryListener.subscribe(this::validateProgress);
    }

    private void validateProgress(PlayerEntity playerEntity, ItemStack itemStack, Integer integer, Boolean added) {
        if (completed != null) { return; }
        if (added) {
            if (itemStack.getItem() instanceof ArmorItem armor) {
                int count = 0;
                for (ItemStack armorStack : playerEntity.getArmorItems()) {
                    if (armorStack.getItem() instanceof ArmorItem) {
                        if (armorStack.getComponents().contains(componentType)) {
                            count++;
                        }
                    }
                }
                if (count >= amount) {
                    this.completed(playerEntity);
                }
//                if(itemStack.getComponents().contains(DataComponentTypes.TRIM)) {
//                    this.completed(playerEntity);
//                }
            }
        }
    }
}
