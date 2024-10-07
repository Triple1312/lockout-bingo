package net.abrikoos.lockout_bingo.server.listeners;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Environment(EnvType.SERVER)
public class PlayerEffectListener {

    private static final List<BiConsumer<PlayerEntity, StatusEffectInstance>> listeners = new ArrayList<>();



    public static void registerEvent(PlayerEntity player, StatusEffectInstance effectInstance) {
        for (BiConsumer<PlayerEntity, StatusEffectInstance> listener : listeners) {
            listener.accept(player, effectInstance);
        }
    }

    public static void subscribe(BiConsumer<PlayerEntity, StatusEffectInstance> listener) {
        listeners.add(listener);
    }

    public static void unsubscribe(BiConsumer<PlayerEntity, StatusEffectInstance> listener) {
        listeners.remove(listener);
    }

    public static void clear() {
        listeners.clear();
    }

}
