package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.listeners.TameListener;
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

    @Inject(method = "setTamed", at = @At("HEAD"))
    private void onSetTamed(boolean tamed, boolean updateAttributes, CallbackInfo ci) {
        if (!tamed) {

        }
        else {
            TameableEntity entity = (TameableEntity) (Object) this;
            LivingEntity owner = entity.getOwner();
            if (owner instanceof PlayerEntity player) {
                TameListener.registerEvent(player, entity);
            }
        }
    }
}
