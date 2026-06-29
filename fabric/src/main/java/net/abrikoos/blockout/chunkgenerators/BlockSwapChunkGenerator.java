package net.abrikoos.blockout.chunkgenerators;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.EmptyBlockView;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.*;

public class BlockSwapChunkGenerator extends BlockoutNoiseGenerator {

    public static final MapCodec<BlockSwapChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(g -> g.biomeSource),
            ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(g -> g.getSettings())
        ).apply(instance, instance.stable(BlockSwapChunkGenerator::new))
    );

    private volatile Map<Block, Block> swapMap = null;

    public Map<Block, Block> getSwapMap() {
        return swapMap;
    }

    public BlockSwapChunkGenerator(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> settings) {
        super(biomeSource, settings);
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    private static List<Block> getSwappableBlocks() {
        List<Block> result = new ArrayList<>();
        for (Block block : Registries.BLOCK) {
            if (block == Blocks.AIR || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR) continue;
            if (block instanceof ShulkerBoxBlock) continue;
            if (hasFullFace(block)) result.add(block);
        }
        return result;
    }

    private static boolean hasFullFace(Block block) {
        try {
            VoxelShape shape = block.getDefaultState()
                    .getCollisionShape(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);
            for (Direction dir : Direction.values()) {
                if (Block.isFaceFullSquare(shape, dir)) return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    private Map<Block, Block> buildSwapMap(long seed) {
        List<Block> blocks = new ArrayList<>(getSwappableBlocks());
        Collections.shuffle(blocks, new Random(seed));

        Map<Block, Block> map = new HashMap<>();
        for (int i = 0; i + 1 < blocks.size(); i += 2) {
            Block a = blocks.get(i);
            Block b = blocks.get(i + 1);
            map.put(a, b);
            map.put(b, a);
        }
        return map;
    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures,
                             NoiseConfig noiseConfig, Chunk chunk) {
        super.buildSurface(region, structures, noiseConfig, chunk);

        if (swapMap == null) {
            synchronized (this) {
                if (swapMap == null) {
                    swapMap = buildSwapMap(region.getSeed());
                }
            }
        }

        BlockPos.Mutable pos = new BlockPos.Mutable();
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = chunk.getBottomY(); y < chunk.getTopY(); y++) {
                    pos.set(startX + x, y, startZ + z);
                    BlockState state = chunk.getBlockState(pos);
                    Block swapped = swapMap.get(state.getBlock());
                    if (swapped != null) {
                        chunk.setBlockState(pos, swapped.getDefaultState(), false);
                    }
                }
            }
        }
    }
}
