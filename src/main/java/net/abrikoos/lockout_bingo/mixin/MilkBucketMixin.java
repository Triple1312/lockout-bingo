package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.listeners.items.MilkBucketUseListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkBucketMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    public void use(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        MilkBucketUseListener.registerEvent(world, (PlayerEntity) user);
    }
}
