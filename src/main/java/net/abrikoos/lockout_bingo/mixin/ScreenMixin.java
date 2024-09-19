package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.listeners.ScreenSlotClickListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class ScreenMixin {

    @Shadow @Final private @Nullable ScreenHandlerType<?> type;
    @Shadow @Final private @Nullable DefaultedList<Slot> slots;

    @Inject( method = "internalOnSlotClick", at = @At("JUMP"))
    private void internalOnSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
//        ScreenHandler sh = (ScreenHandler) (Object) this;
        try {
            if (type != null && slots != null && slotIndex >= 0 && slotIndex < slots.size()) {
                ScreenSlotClickListener.registerEvent(slotIndex, button, actionType, player, type, slots.get(slotIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
