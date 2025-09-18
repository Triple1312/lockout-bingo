package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.item.LockoutModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ItemEntity.class)
public abstract class CompassVanishMixin {
    @Shadow public abstract ItemStack getStack();


    @Inject(method = "tick", at = @At("HEAD"))
    private void vanishCustomItem(CallbackInfo ci) {
        Entity self = (Entity)(Object)this;
        if (!self.getWorld().isClient) {
            if (getStack().isOf(LockoutModItems.PLAYER_TRACKING_COMPASS)) {
                ((ItemEntity)(Object)this).discard(); // poof
            }
        }
    }

}
