package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.server.listeners.EntityKillListener;
import net.abrikoos.blockout.server.listeners.PlayerDeathListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class EntityDeathMixin {

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void onDeathMixin(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;
        EntityKillListener.registerEvent(self, damageSource);
        if (self instanceof ServerPlayerEntity serverPlayer) {
            PlayerDeathListener.registerEvent(serverPlayer, damageSource);
        }
    }
}
