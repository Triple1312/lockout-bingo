package net.abrikoos.lockout_bingo.server.listeners;

import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventoryListener {

    private final List<TriConsumer<PlayerEntity, ItemStack, Boolean>> listeners;

    private static PlayerInventoryListener instance;

    private PlayerInventoryListener() {
        listeners = new ArrayList<>();
    }

    public static void subscribe(TriConsumer<PlayerEntity, ItemStack, Boolean> listener) {
        getInstance().listeners.add(listener);
    }

    public static void unsubscribe(TriConsumer<PlayerEntity, ItemStack, Boolean> listener) {
        getInstance().listeners.remove(listener);
    }

    public static PlayerInventoryListener getInstance() {
        if (instance == null) {
            instance = new PlayerInventoryListener();
        }
        return instance;
    }

    public static void registerEvent(PlayerEntity player, ItemStack stack, boolean added) {
        for (TriConsumer<PlayerEntity, ItemStack, Boolean> listener : getInstance().listeners) {
            listener.accept(player, stack, added);
        }
    }

    public static void clear() {
        instance = new PlayerInventoryListener();
    }
}
