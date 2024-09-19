package net.abrikoos.lockout_bingo.server.goals.brew;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.ScreenSlotClickListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

// todo not working
public class BrewPotionGoal extends LockoutGoal {

    RegistryEntry<Potion> potion;

    public BrewPotionGoal(int id, RegistryEntry<Potion> potion) {
        super(id);
        this.potion = potion;
        ScreenSlotClickListener.subscribe(this::validateProgress);
    }

    private void validateProgress(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, ScreenHandlerType<?> type, Slot slot) {
        if (completed != null) { return; }
        if (type == ScreenHandlerType.BREWING_STAND && actionType == SlotActionType.PICKUP) {
            if (slotIndex == 0 || slotIndex == 1 || slotIndex == 2) {
                PotionItem potionItem = (PotionItem) slot.getStack().getItem();
                String[] x = potionItem.getTranslationKey(slot.getStack()).split("\\.");
                String[] y = this.potion.getIdAsString().split(":");
                if (y[1].equals(x[x.length - 1])) {
                    this.completed(player);
                }
            }
        }
    }


}
