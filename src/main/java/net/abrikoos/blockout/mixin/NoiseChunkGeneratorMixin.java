package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.server.builder.BlockSwapBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin {
//
//    @Inject(method = "populateNoise", at = @At("TAIL"))
//    private void populateNoise(Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk, CallbackInfoReturnable<Chunk> cir) {
//        if (BlockSwapBuilder.blockSwapEnabled()) {
//            BlockSwapBuilder.swapBlocks();
//        }
//
//        if (chunk instanceof ProtoChunk protoChunk){
//            ChunkPos chunkPos = chunk.getPos();
//            BlockPos.Mutable pos = new BlockPos.Mutable();
//
//            for(int x = 0; x < 16; x++) {
//                for(int z = 0; z < 16; z++) {
//                    for(int y = chunk.getBottomY(); y < chunk.getTopY(); y++) {
//                        try {
//                            pos.set(chunkPos.getStartX() + x, y, chunkPos.getStartZ() + z);
//                            BlockState state = protoChunk.getBlockState(pos);
//                            if (state.isAir()) {
//                                continue;
//                            }
//                            BlockState newState = BlockSwapBuilder.getSwappedBlock(state.getBlock()).getDefaultState();
//                            protoChunk.setBlockState(pos, newState, false);
//                        }
//                        catch (Exception ignored) {
//                            System.out.println("Error swapping block");
//                        }
//                    }
//                }
//            }
//        }
//    }
}
