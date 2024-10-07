package net.abrikoos.lockout_bingo.mixin;


import net.abrikoos.lockout_bingo.server.listeners.UseCauldronListener;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.block.AbstractCauldronBlock.class)
public class AbstractCauldronMixin {

    @Inject(method="onUseWithItem", at = @At("RETURN"))
    public void useItemOnCauldron(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ItemActionResult> cir) {
        if (cir.getReturnValue() == ItemActionResult.SUCCESS) {
            UseCauldronListener.registerEvent(stack, state, world, pos, player, hand, hit);
        }
    }
}
