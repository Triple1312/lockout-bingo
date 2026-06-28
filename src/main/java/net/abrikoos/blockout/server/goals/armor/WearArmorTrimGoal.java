package net.abrikoos.blockout.server.goals.armor;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.PlayerInventoryListener;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class WearArmorTrimGoal extends BlockoutGoal {
    public WearArmorTrimGoal(int id) {
        super(id);
        PlayerInventoryListener.subscribe(this::validateProgress);
    }

    private void validateProgress(PlayerEntity playerEntity, ItemStack itemStack, Integer integer, Boolean added) {
        if (completed != null) { return; }
        if (added) {
            if (itemStack.getItem() instanceof ArmorItem armor) {
                for (ItemStack armorStack : playerEntity.getArmorItems()) {
                    if (armorStack.getItem() instanceof ArmorItem) {
                        if(armorStack.getComponents().contains(DataComponentTypes.TRIM)) {
                            this.completed(playerEntity);
                            return;
                        }
                    }
                }
//                if(itemStack.getComponents().contains(DataComponentTypes.TRIM)) {
//                    this.completed(playerEntity);
//                }
            }
        }
    }
}
