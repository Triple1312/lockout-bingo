package net.abrikoos.lockout_bingo.server.goals.use;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.ScreenSlotClickListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;


public class UseBlockGoal extends LockoutGoal {

    ScreenHandlerType<?> fsh;
    int slot;

    public UseBlockGoal(int id, ScreenHandlerType<?> fsh, int slot) {
        super(id);
        this.fsh = fsh;
        this.slot = slot;
        ScreenSlotClickListener.subscribe(this::validateProgress);
    }

    private void validateProgress(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, ScreenHandlerType<?> type, Slot slot) {
        if (completed != null) { return; }
        if (type == fsh && actionType == SlotActionType.PICKUP && slotIndex == this.slot && slot.hasStack()) {
            this.completed(player);
        }
    }

    @Override
    public void destory() {
        ScreenSlotClickListener.unsubscribe(this::validateProgress);
    }

}
