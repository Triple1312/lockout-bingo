package net.abrikoos.lockout_bingo.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.block.ComposterBlock.class)
public class ComposterMixin {

    @Inject(method = "emptyFullComposter", at = @At("HEAD"))
    private static void emptyFullComposter(Entity user, BlockState state, World world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {


    }
}
