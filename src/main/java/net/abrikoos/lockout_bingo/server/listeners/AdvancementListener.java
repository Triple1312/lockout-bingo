package net.abrikoos.lockout_bingo.server.listeners;

import net.minecraft.advancement.Advancement;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;

public class AdvancementListener {

    private final List<TriConsumer<ServerPlayerEntity, Advancement, String>> listeners;

    private static AdvancementListener instance;

    private AdvancementListener() {
        listeners = new ArrayList<>();
    }

    public static void subscribe(TriConsumer<ServerPlayerEntity, Advancement, String> listener) {
        getInstance().listeners.add(listener);
    }

    public static void unsubscribe(TriConsumer<ServerPlayerEntity, Advancement, String> listener) {
        getInstance().listeners.remove(listener);
    }

    public static AdvancementListener getInstance() {
        if (instance == null) {
            instance = new AdvancementListener();
        }
        return instance;
    }

    public static void registerEvent(ServerPlayerEntity player, Advancement advancement, String criterionName) {
        for (TriConsumer<ServerPlayerEntity, Advancement, String> listener : getInstance().listeners) {
            listener.accept(player, advancement, criterionName);
        }
    }

    public static void clear() {
        instance = new AdvancementListener();
    }




}
