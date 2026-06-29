package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.server.gamestate.GameState;
import net.abrikoos.blockout.server.gamestate.GameStatePersistence;
import net.abrikoos.blockout.server.listeners.TickListener;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        GameState.server = (MinecraftServer) (Object) this;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        TickListener.registerEvent((MinecraftServer) (Object) this);
    }

    @Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z", shift = At.Shift.AFTER))
    private void onServerStarted(CallbackInfo ci) {
        GameStatePersistence.load((MinecraftServer) (Object) this);
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    private void onShutdown(CallbackInfo ci) {
        GameStatePersistence.save((MinecraftServer) (Object) this);
    }
}
