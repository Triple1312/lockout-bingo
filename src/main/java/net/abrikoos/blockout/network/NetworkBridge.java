package net.abrikoos.blockout.network;

import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NetworkBridge {
    private static BiConsumer<ServerPlayerEntity, CustomPayload> toPlayerFn;
    private static Consumer<CustomPayload> toServerFn;

    public static void setToPlayer(BiConsumer<ServerPlayerEntity, CustomPayload> fn) {
        toPlayerFn = fn;
    }

    public static void setToServer(Consumer<CustomPayload> fn) {
        toServerFn = fn;
    }

    public static void sendToPlayer(ServerPlayerEntity player, CustomPayload payload) {
        if (toPlayerFn != null) toPlayerFn.accept(player, payload);
    }

    public static void sendToServer(CustomPayload payload) {
        if (toServerFn != null) toServerFn.accept(payload);
    }
}
