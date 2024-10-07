package net.abrikoos.lockout_bingo.server.listeners;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.SERVER)
public class ScreenSlotClickListener {
    private final List<HexConsumer<Integer, Integer, SlotActionType, PlayerEntity, ScreenHandlerType<?>, Slot>> listeners;
    
    private static ScreenSlotClickListener instance;
    
    public ScreenSlotClickListener() {
        this.listeners = new ArrayList<>();
    }
    
    public static void subscribe(HexConsumer<Integer, Integer, SlotActionType, PlayerEntity, ScreenHandlerType<?>, Slot> listener) {
        getInstance().listeners.add(listener);
    }
    
    public static ScreenSlotClickListener getInstance() {
        if (instance == null) {
            instance = new ScreenSlotClickListener();
        }
        return instance;
    }
    
    public static void registerEvent(int slotIndex, int button, SlotActionType actionType, PlayerEntity player , ScreenHandlerType<?> type, Slot slot){
        for (HexConsumer<Integer, Integer, SlotActionType, PlayerEntity, ScreenHandlerType<?>, Slot> listener : getInstance().listeners) {
            listener.accept(slotIndex, button, actionType, player, type, slot);
        }
    }

    public static void unsubscribe(HexConsumer<Integer, Integer, SlotActionType, PlayerEntity, ScreenHandlerType<?>, Slot> listener) {
        getInstance().listeners.remove(listener);
    }

    public static void clear() {
        instance = new ScreenSlotClickListener();
    }
}
