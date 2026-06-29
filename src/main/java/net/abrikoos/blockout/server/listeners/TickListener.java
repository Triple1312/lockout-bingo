package net.abrikoos.blockout.server.listeners;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TickListener {

    public static final List<Consumer<MinecraftServer>> listeners = new ArrayList<>();

    public static void registerEvent(MinecraftServer server) {
        for (Consumer<MinecraftServer> listener : listeners) {
            listener.accept(server);
        }
    }

    public static void subscribe(Consumer<MinecraftServer> listener) {
        listeners.add(listener);
    }

    public static void unsubscribe(Consumer<MinecraftServer> listener) {
        listeners.remove(listener);
    }

    public static void clear() {
        listeners.clear();
    }
}
