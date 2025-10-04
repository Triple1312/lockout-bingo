package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.listeners.EntityKillListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class EntityDeathMixin {

    @Inject(method = "onDeath", at = @At("TAIL") )
    private void onDeathMixin(DamageSource damageSource, CallbackInfo ci) {
        EntityKillListener.registerEvent((LivingEntity)(Object)this, damageSource);
    }







}
