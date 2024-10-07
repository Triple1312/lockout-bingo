package net.abrikoos.lockout_bingo.server.listeners.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;

public class MilkBucketUseListener {
    private final List<TriConsumer<World, PlayerEntity, Hand>> listeners;

    private static MilkBucketUseListener instance;

    private MilkBucketUseListener() {
        listeners = new ArrayList<>();
    }

    public static void subscribe(TriConsumer<World, PlayerEntity, Hand> listener) {
        getInstance().listeners.add(listener);
    }

    public static void unsubscribe(TriConsumer<World, PlayerEntity, Hand> listener) {
        getInstance().listeners.remove(listener);
    }

    public static MilkBucketUseListener getInstance() {
        if (instance == null) {
            instance = new MilkBucketUseListener();
        }
        return instance;
    }

    public static void registerEvent(World world, PlayerEntity player, Hand hand) {
        for (TriConsumer<World, PlayerEntity, Hand> listener : getInstance().listeners) {
            listener.accept(world, player, hand);
        }
    }

    public static void clear() {
        instance = new MilkBucketUseListener();
    }
}
