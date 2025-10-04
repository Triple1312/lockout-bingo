package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract  class ServerPlayerMixin {

    @Shadow
    private BlockPos spawnPointPosition;

//    @Inject(method="getSpawnPointPosition", at=@At("RETURN"), cancellable = true)
//    public void getSpawnPointPosition(CallbackInfoReturnable<BlockPos> cir) {
//        ServerPlayerEntity target = GameState.getPlayerRespawnTarget(((ServerPlayerEntity)(Object)this).getUuidAsString());
//        if (target != null) {
//            cir.setReturnValue(target.getBlockPos());
//        }
//    }


}
