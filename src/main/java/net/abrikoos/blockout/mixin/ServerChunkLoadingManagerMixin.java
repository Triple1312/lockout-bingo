package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.server.builder.BlockSwapBuilder;
import net.abrikoos.blockout.server.gamestate.GameState;
import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerChunkLoadingManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.BoundedRegionArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkHolder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkGenerationStep;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(ServerChunkLoadingManager.class)
public abstract class ServerChunkLoadingManagerMixin {

//    @Inject(method = "generate", at = @At("RETURN"))
//    private void onChunkGenerated(AbstractChunkHolder chunkHolder, ChunkGenerationStep step, BoundedRegionArray<AbstractChunkHolder> chunks, CallbackInfoReturnable<CompletableFuture<Chunk>> cir) {
//        cir.getReturnValue().thenAcceptAsync(chunk -> {
//            if (chunk == null) {
//                return;
//            }
//            if (chunk instanceof WorldChunk) {
//                ServerWorld world = (ServerWorld) ((WorldChunk) chunk).getWorld();
//                world.getServer().execute(() -> modifyChunk(chunk, world));
//            }
//        });
//
//    }
//
//
//    private void modifyChunk(Chunk chunk, ServerWorld world) {
//        ChunkPos chunkPos = chunk.getPos();
//        BlockPos.Mutable pos = new BlockPos.Mutable();
//
//        for(int x = 0; x < 16; x++) {
//            for(int z = 0; z < 16; z++) {
//                for(int y = chunk.getBottomY(); y < chunk.getTopY(); y++) {
//                    try {
//                        pos.set(chunkPos.getStartX() + x, y, chunkPos.getStartZ() + z);
//                        BlockState state = chunk.getBlockState(pos);
//                        if (state.isAir()) {
//                            continue;
//                        }
//                        if (BlockSwapBuilder.blockSwapEnabled()) {
//                            BlockSwapBuilder.swapBlocks();
//                        }
//                        // Do something with the block at pos
//                        chunk.setBlockState(pos, BlockSwapBuilder.getSwappedBlock(state.getBlock()).getDefaultState(), false);
//                    }
//                    catch (Exception ignored) {
//                        System.out.println("Error modifying block");
//                    }
//                }
//            }
//        }
//
//    }

}
