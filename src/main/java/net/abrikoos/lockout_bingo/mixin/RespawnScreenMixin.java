package net.abrikoos.lockout_bingo.mixin;

import net.minecraft.client.gui.screen.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DeathScreen.class)
public abstract class RespawnScreenMixin {

    @Shadow protected abstract void init();

//    @Inject(method = "render", at = @At("HEAD"))
//    void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        int teamId = PlayerTeamRegistry.getTeamIndex(client.player.getUuidAsString());
//        if (teamId != 0) {
//            if (client.player.requestRespawn(); == null)
//        }
//
//        this.init();
//
//    }




}
