package net.abrikoos.lockout_bingo.server.listeners;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;

public class PlayerDamageListener {

    private static final List<TriConsumer<PlayerEntity, DamageSource, Float>> listeners = new ArrayList<>();

    private static PlayerDamageListener instance;

    public PlayerDamageListener() {
    }

    public static void subscribe(TriConsumer<PlayerEntity, DamageSource, Float> listener) {
        listeners.add(listener);
    }

    public static void registerEvent(PlayerEntity player, DamageSource damageSource, float amount) {
        for (TriConsumer<PlayerEntity, DamageSource, Float> listener : listeners) {
            try {
                listener.accept(player, damageSource, amount);
            }
            catch (Exception e) {
                listeners.remove(listener);
            }
        }
    }

    public static PlayerDamageListener getInstance() {
        if (instance == null) {
            instance = new PlayerDamageListener();
        }
        return instance;
    }

    public static void clear() {
        instance = new PlayerDamageListener();
        listeners.clear();
    }



}
