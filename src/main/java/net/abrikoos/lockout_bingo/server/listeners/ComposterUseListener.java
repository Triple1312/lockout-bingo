package net.abrikoos.lockout_bingo.server.listeners;

import net.abrikoos.lockout_bingo.util.QuadConsumer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ComposterUseListener {
    private final List<QuadConsumer<Entity, BlockState, World, BlockPos>> listeners;

    private static ComposterUseListener instance;

    private ComposterUseListener() {
        listeners = new ArrayList<>();
    }

    public static void subscribe(QuadConsumer<Entity, BlockState, World, BlockPos> listener) {
        getInstance().listeners.add(listener);
    }

    public static void unsubscribe(QuadConsumer<Entity, BlockState, World, BlockPos> listener) {
        getInstance().listeners.remove(listener);
    }

    public static ComposterUseListener getInstance() {
        if (instance == null) {
            instance = new ComposterUseListener();
        }
        return instance;
    }

    public static void registerEvent(Entity entity, BlockState blockState, World world, BlockPos blockPos) {
        for (QuadConsumer<Entity, BlockState, World, BlockPos> listener : getInstance().listeners) {
            listener.accept(entity, blockState, world, blockPos);
        }
    }

    public static void clear() {
        instance = new ComposterUseListener();
    } // todo change to null

}
