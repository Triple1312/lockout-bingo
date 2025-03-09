package net.abrikoos.lockout_bingo.server.listeners;

import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeathListener {

    private static final List<BiConsumer<ServerPlayerEntity, DamageSource>> listeners = new ArrayList<>();

    private static PlayerDeathListener instance;

    public PlayerDeathListener() {
        ServerLivingEntityEvents.AFTER_DEATH.register(PlayerDeathListener::registerEvent);
    }

    private static void registerEvent(LivingEntity livingEntity, DamageSource damageSource) {
        if (! (livingEntity instanceof ServerPlayerEntity)) {
            return;
        }
        for (BiConsumer<ServerPlayerEntity, DamageSource> listener : listeners) {
            try {
                listener.accept((ServerPlayerEntity) livingEntity, damageSource);
            }
            catch (Exception e) {
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
