package net.abrikoos.lockout_bingo.server.listeners;

import net.abrikoos.lockout_bingo.util.QuadConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.SERVER)
public class PlayerInventoryListener {

    private final List<QuadConsumer<PlayerEntity, ItemStack, Integer, Boolean>> listeners;

    private static PlayerInventoryListener instance;

    private PlayerInventoryListener() {
        listeners = new ArrayList<>();
    }

    public static void subscribe(QuadConsumer<PlayerEntity, ItemStack, Integer, Boolean> listener) {
        getInstance().listeners.add(listener);
    }

    public static void unsubscribe(QuadConsumer<PlayerEntity, ItemStack, Integer, Boolean> listener) {
        getInstance().listeners.remove(listener);
    }

    public static PlayerInventoryListener getInstance() {
        if (instance == null) {
            instance = new PlayerInventoryListener();
        }
        return instance;
                }

        public static void registerEvent(PlayerEntity player, ItemStack stack, int slot, boolean added) { // todo does not work that well
        for (QuadConsumer<PlayerEntity, ItemStack, Integer, Boolean> listener : getInstance().listeners) {
            listener.accept(player, stack, slot, added);
        }
    }

    public static void clear() {
        instance = new PlayerInventoryListener();
    }
}
