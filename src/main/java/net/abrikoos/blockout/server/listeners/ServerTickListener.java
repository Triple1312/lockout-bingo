package net.abrikoos.blockout.server.listeners;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ServerTickListener {

    private final List<Consumer<MinecraftServer>> listeners = new ArrayList<>();

    private static ServerTickListener instance;

    private ServerTickListener() {
        TickListener.subscribe(this::registerEvent);
    }

    public static void subscribe(Consumer<MinecraftServer> listener) {
        ServerTickListener.getInstance().listeners.add(listener);
    }

    public void registerEvent(MinecraftServer server) {
        for (Consumer<MinecraftServer> listener : new ArrayList<>(ServerTickListener.getInstance().listeners)) {
            try {
                listener.accept(server);
            } catch (Exception e) {
                ServerTickListener.getInstance().listeners.remove(listener);
            }
        }
    }

    public static void clear() {
        ServerTickListener.getInstance().listeners.clear();
    }

    public static void unsubscribe(Consumer<MinecraftServer> listener) {
        ServerTickListener.getInstance().listeners.remove(listener);
    }

    public static ServerTickListener getInstance() {
        if (instance == null) {
            instance = new ServerTickListener();
        }
        return instance;
    }
}
