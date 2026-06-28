package net.abrikoos.blockout.chunkgenerators;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abrikoos.blockout.registries.BlockGroups;
import net.abrikoos.blockout.registries.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;

import java.util.List;
import java.util.function.Supplier;

public class SingleBlockChunkGenerator extends BlockoutNoiseGenerator {

    public static final MapCodec<SingleBlockChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(g -> g.biomeSource),
                    ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(g -> g.getSettings())
            ).apply(instance, instance.stable(SingleBlockChunkGenerator::new))
    );

    // Loaded lazily so BlockRegistry.get() runs after registries are populated.
    private static final Supplier<List<Block>> FULL_BLOCKS =
            Suppliers.memoize(() -> BlockRegistry.get(BlockGroups.FULL_BLOCKS));

    public SingleBlockChunkGenerator(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> settings) {
        super(biomeSource, settings);
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    /**
     * Returns one consistent block per chunk, chosen by hashing the chunk coordinates.
     * Every block in the same 16×16 column maps to the same block type.
     */
    @Override
    protected BlockState getBlockState(ChunkNoiseSampler sampler, int x, int y, int z, BlockState state) {
        BlockState proposed = super.getBlockState(sampler, x, y, z, state);
        if (proposed.isAir()) return proposed;
        if (y <= getMinimumY() + 4) return Blocks.BEDROCK.getDefaultState();

        int chunkX = x >> 4;
        int chunkZ = z >> 4;

        long seed = (long) chunkX * 341873128712L ^ (long) chunkZ * 132897987541L;
        seed ^= seed >>> 33;
        seed *= 0xff51afd7ed558ccdL;
        seed ^= seed >>> 33;

        List<Block> blocks = FULL_BLOCKS.get();
        int index = (int) Math.floorMod(seed, blocks.size());
        return blocks.get(index).getDefaultState();
    }
}
