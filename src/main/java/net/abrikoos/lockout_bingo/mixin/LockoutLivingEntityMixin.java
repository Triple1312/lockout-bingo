package net.abrikoos.lockout_bingo.mixin;


import net.abrikoos.lockout_bingo.listeners.PlayerEffectListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LockoutLivingEntityMixin {

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onPlayerDeath(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
//        if (entity instanceof ServerPlayerEntity player) { // todo is unreachable ?
//            EntityDeathListener.registerEvent((ServerPlayerEntity) entity, damageSource);
//        }
    }

    @Inject(method="setStatusEffect", at=@At("RETURN"))
    private void setStatusEffect(StatusEffectInstance effect, Entity source, CallbackInfo ci) {

    }



}
