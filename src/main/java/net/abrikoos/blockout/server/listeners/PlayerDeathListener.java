package net.abrikoos.blockout.server.listeners;

import net.abrikoos.blockout.server.gamestate.GameState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.ArrayList;
import java.util.List;

// Death events are delivered via EntityDeathMixin which calls registerEvent directly.
public class PlayerDeathListener {

    private static final List<BiConsumer<ServerPlayerEntity, DamageSource>> listeners = new ArrayList<>();

    private static PlayerDeathListener instance;

    public PlayerDeathListener() {
        // No event registration needed here — EntityDeathMixin calls registerEvent
    }

    public static void registerEvent(ServerPlayerEntity player, DamageSource damageSource) {
        for (BiConsumer<ServerPlayerEntity, DamageSource> listener : listeners) {
            try {
                listener.accept(player, damageSource);
            } catch (Exception e) {
                listeners.remove(listener);
            }
        }
    }

    public static void subscribe(BiConsumer<ServerPlayerEntity, DamageSource> listener) {
        listeners.add(listener);
    }

    public static PlayerDeathListener getInstance() {
        if (instance == null) {
            instance = new PlayerDeathListener();
        }
        return instance;
    }

    public static void clear() {
        instance = new PlayerDeathListener();
        listeners.clear();
    }

    public static void unsubscribe(BiConsumer<ServerPlayerEntity, DamageSource> listener) {
        getInstance().listeners.remove(listener);
    }
}
