package net.abrikoos.lockout_bingo.listeners;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TickListener {

    public static final List<Consumer<MinecraftServer>> listeners = new ArrayList<>();

    private static boolean instanciated = false;

    private static void init() {
        if (!instanciated) {
            ServerTickEvents.START_SERVER_TICK.register(TickListener::registerEvent);
            instanciated = true;
        }
    }


    public static void registerEvent(MinecraftServer server) {
        for (Consumer<MinecraftServer> listener : listeners) {
            listener.accept(server);
        }
    }


    public static void subscribe(Consumer<MinecraftServer> listener) {
        init();
        listeners.add(listener);
    }

    public static void unsubscribe(Consumer<MinecraftServer> listener) {
        listeners.remove(listener);
    }

    public static void clear() {
        listeners.clear();
    }

}
