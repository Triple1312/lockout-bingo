package net.abrikoos.lockout_bingo.server.listeners;

import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class TameListener {

    private final List<BiConsumer<PlayerEntity, TameableEntity>> listeners;

    private static TameListener instance;

    public TameListener() {
        this.listeners =  new ArrayList<>();;
    }

    public static void subscribe(BiConsumer<PlayerEntity, TameableEntity> listener) {
        getInstance().listeners.add(listener);
    }

    public static void unsubscribe(BiConsumer<PlayerEntity, TameableEntity> listener) {
        getInstance().listeners.remove(listener);
    }

    public static TameListener getInstance() {
        if (instance == null) {
            instance = new TameListener();
        }
        return instance;
    }

    public static void registerEvent(PlayerEntity player, TameableEntity entity) {
        for (BiConsumer<PlayerEntity, TameableEntity> listener : getInstance().listeners) {
            listener.accept(player, entity);
        }
    }

    public static void clear() {
        instance = new TameListener();
    }
}
