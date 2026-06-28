package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.chunkgenerators.BlockSwapChunkGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ChunkRegion.class)
public abstract class ChunkRegionMixin {

    @Shadow private ServerWorld world;

    // Guards against re-entry when we call setBlockState with the swapped state.
    @Unique private boolean swapGuard = false;

    @Inject(
        method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void applyBlockSwap(BlockPos pos, BlockState state, int flags, int maxUpdateDepth,
                                CallbackInfoReturnable<Boolean> cir) {
        if (swapGuard) return;
        ChunkGenerator gen = world.getChunkManager().getChunkGenerator();
        if (!(gen instanceof BlockSwapChunkGenerator bsGen)) return;
        Map<Block, Block> map = bsGen.getSwapMap();
        if (map == null) return;
        Block swapped = map.get(state.getBlock());
        if (swapped == null) return;
        swapGuard = true;
        boolean result = ((ChunkRegion) (Object) this).setBlockState(pos, swapped.getDefaultState(), flags, maxUpdateDepth);
        swapGuard = false;
        cir.setReturnValue(result);
    }
}
