package net.abrikoos.lockout_bingo.server.listeners;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ServerTickListener {

    private final List<Consumer<MinecraftServer>> listeners = new ArrayList<>();

    private static ServerTickListener instance;

    private ServerTickListener() {
        ServerTickEvents.START_SERVER_TICK.register(this::registerEvent);
    }

    public static void subscribe(Consumer<MinecraftServer> listener) {
        ServerTickListener.getInstance().listeners.add(listener);
    }

    public void registerEvent(MinecraftServer server) {
        for (Consumer<MinecraftServer> listener : ServerTickListener.getInstance().listeners) {
            try {
                listener.accept(server);
            }
            catch (Exception e) {
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
