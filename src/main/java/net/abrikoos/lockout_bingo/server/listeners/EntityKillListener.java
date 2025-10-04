package net.abrikoos.lockout_bingo.server.listeners;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class EntityKillListener {
    private final List<BiConsumer<LivingEntity, DamageSource>> listeners;

    private static EntityKillListener instance;

    public EntityKillListener() {
        this.listeners = new ArrayList<>();
    }

    public static void subscribe(BiConsumer<LivingEntity, DamageSource> listener) {
        getInstance().listeners.add(listener);
    }

    public static EntityKillListener getInstance() {
        if (instance == null) {
            instance = new EntityKillListener();
        }
        return instance;
    }

    public static void registerEvent(LivingEntity target, DamageSource source) {
        for (BiConsumer<LivingEntity, DamageSource> listener : getInstance().listeners) {
            listener.accept(target, source);
        }
    }

    public static void clear() {
        instance = new EntityKillListener();
    }
}
