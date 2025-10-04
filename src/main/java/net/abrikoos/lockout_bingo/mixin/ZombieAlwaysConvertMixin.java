package net.abrikoos.lockout_bingo.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieAlwaysConvertMixin {
    @Inject(method="onKilledOther", at=@At("RETURN"), cancellable = true)
    private void alwaysConvert(ServerWorld world, LivingEntity victim, CallbackInfoReturnable<Boolean> cir) {
        if (victim instanceof VillagerEntity) {
            cir.setReturnValue(true);
        }
    }
}
