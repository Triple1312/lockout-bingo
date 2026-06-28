package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.server.gamestate.GameState;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method="<init>", at= @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        GameState.server = (MinecraftServer) (Object) this;
    }




}
