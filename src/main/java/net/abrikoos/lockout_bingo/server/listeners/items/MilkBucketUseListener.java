package net.abrikoos.lockout_bingo.server.listeners.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.ArrayList;
import java.util.List;

public class MilkBucketUseListener {
    private final List<BiConsumer<World, PlayerEntity>> listeners;

    private static MilkBucketUseListener instance;

    private MilkBucketUseListener() {
        listeners = new ArrayList<>();
    }

    public static void subscribe(BiConsumer<World, PlayerEntity> listener) {
        getInstance().listeners.add(listener);
    }

    public static void unsubscribe(BiConsumer<World, PlayerEntity> listener) {
        getInstance().listeners.remove(listener);
    }

    public static MilkBucketUseListener getInstance() {
        if (instance == null) {
            instance = new MilkBucketUseListener();
        }
        return instance;
    }

    public static void registerEvent(World world, PlayerEntity player) {
        for (BiConsumer<World, PlayerEntity> listener : getInstance().listeners) {
            listener.accept(world, player);
        }
    }

    public static void clear() {
        instance = new MilkBucketUseListener();
    }
}
