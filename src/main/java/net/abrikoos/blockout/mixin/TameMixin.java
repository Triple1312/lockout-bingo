package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.server.listeners.TameListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.annotation.Target;

@Mixin(TameableEntity.class)
public class TameMixin {

    @Inject(method = "setOwner", at = @At("RETURN"))
    private void onSetTamed(PlayerEntity player, CallbackInfo ci) {
        TameableEntity entity = (TameableEntity) (Object) this;
        TameListener.registerEvent(player, entity);
    }
}
