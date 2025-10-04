package net.abrikoos.lockout_bingo.server.goals.armor;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.PlayerInventoryListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WearArmorSetGoal extends LockoutGoal {

    List<Equipment> armorPieces = new ArrayList<>();
    final int count;

    public WearArmorSetGoal(int id, List<Item> armorPieces, int count) {
        super(id);
        for (Item item : armorPieces) {
            if (!(item instanceof Equipment)) {
                throw new IllegalArgumentException("Item must be an Equipement");
            }
            else {
                this.armorPieces.add((Equipment) item);
            }
        }
        this.count = count;
        PlayerInventoryListener.subscribe(this::checkCompletion);
    }

    private void checkCompletion(PlayerEntity player, ItemStack stack, int slot, boolean added) {
        if (completed != null) { return; }
        if (added) {
            if (stack.getItem() instanceof Equipment armorItem) {
                if (armorPieces.contains(armorItem)) {
                    int sum = 0;
                    for (ItemStack armorStack : player.getArmorItems()) {
                        if (armorStack.getItem() instanceof ArmorItem armor) {
                            if (armorPieces.contains(armor)) {
                                sum++;
                            }
                        }
                    }
                    if (sum >= count) {
                        completed = player;
                        this.completed(player);
                    }
                }
            }
        }
    }
}
