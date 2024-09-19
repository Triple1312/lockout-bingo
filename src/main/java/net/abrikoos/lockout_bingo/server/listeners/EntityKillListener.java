package net.abrikoos.lockout_bingo.server.listeners;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class EntityKillListener {
    private final List<BiConsumer<ServerPlayerEntity, LivingEntity>> listeners;

    private static EntityKillListener instance;

    public EntityKillListener() {
        this.listeners = new ArrayList<>();
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(EntityKillListener::registerEvent);
    }

    public static void subscribe(BiConsumer<ServerPlayerEntity, LivingEntity> listener) {
        getInstance().listeners.add(listener);
    }

    public static EntityKillListener getInstance() {
        if (instance == null) {
            instance = new EntityKillListener();
        }
        return instance;
    }

    public static void registerEvent(ServerWorld serverWorld, Entity entity, LivingEntity target) {
        if (! (entity instanceof ServerPlayerEntity)) {
            return;
        }
        for (BiConsumer<ServerPlayerEntity, LivingEntity> listener : getInstance().listeners) {
            listener.accept((ServerPlayerEntity) entity, target);
        }
    }

    public static void clear() {
        instance = new EntityKillListener();
    }
}
