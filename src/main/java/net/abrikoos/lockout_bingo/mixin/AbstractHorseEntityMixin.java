package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.listeners.TameListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin {

    @Inject(method = "setTame", at = @At("RETURN"))
    public void setTame(boolean tame, CallbackInfo ci) {
        if (tame) {
            AbstractHorseEntity entity = (AbstractHorseEntity) (Object) this;
            LivingEntity owner = entity.getOwner();
            if (owner instanceof PlayerEntity player) {
                TameListener.registerEvent(player, entity);
            }
        }
    }
}
