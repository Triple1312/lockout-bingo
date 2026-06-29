package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.server.gamestate.GameState;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class ServerPlayNetworkHandlerMixin {

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
        GameState.playerServerJoin(player);
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void onPlayerDisconnect(ServerPlayerEntity player, CallbackInfo ci) {
        GameState.playerServerLeave(player);
    }
}
