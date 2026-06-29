package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.server.goals.use.UseEntityGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class UseEntityMixin {

    @Inject(method = "interact", at = @At("HEAD"))
    private void onInteractEntity(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        UseEntityGoal.InteractContext ctx = new UseEntityGoal.InteractContext(player, entity, hand);
        for (var listener : UseEntityGoal.entityInteractListeners) {
            listener.accept(ctx);
        }
    }
}
