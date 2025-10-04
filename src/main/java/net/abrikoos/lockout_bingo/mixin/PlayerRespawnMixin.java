package net.abrikoos.lockout_bingo.mixin;


import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.minecraft.entity.Entity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public abstract class PlayerRespawnMixin {

    @Inject(method = "respawnPlayer", at = @At("TAIL") )
    private void onRespawnPlayer(ServerPlayerEntity player, boolean alive, Entity.RemovalReason removalReason, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        ServerPlayerEntity teleportTarget = GameState.getPlayerRespawnTarget(player.getUuidAsString());
        if (teleportTarget != null) {
            cir.getReturnValue().teleport(teleportTarget.getServerWorld(), teleportTarget.getX(), teleportTarget.getY(), teleportTarget.getZ(), teleportTarget.getYaw(), teleportTarget.getPitch());
        }
    }
}
