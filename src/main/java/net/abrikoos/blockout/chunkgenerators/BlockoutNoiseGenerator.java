package net.abrikoos.blockout.chunkgenerators;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.BiomeSupplier;
import net.minecraft.world.chunk.BelowZeroRetrogen;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.StructureWeightSampler;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes;
import net.minecraft.world.gen.densityfunction.DensityFunctions;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.noise.NoiseRouter;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A faithful re-implementation of NoiseChunkGenerator with every private method
 * exposed as protected, so subclasses can override any step of world generation.
 *
 * Extending NoiseChunkGenerator (rather than ChunkGenerator) means CarverContext
 * accepts `this` directly without any extra workarounds.
 *
 * Methods widened via blockout.accesswidener:
 *   ChunkNoiseSampler.sampleBlockState()
 *   ChunkNoiseSampler.getHorizontalCellBlockCount()
 *   ChunkNoiseSampler.getVerticalCellBlockCount()
 *   ChunkNoiseSampler.createMultiNoiseSampler(NoiseRouter, List)
 *   DensityFunctionTypes$Beardifier (class)
 */
public class BlockoutNoiseGenerator extends NoiseChunkGenerator {

    public static final MapCodec<BlockoutNoiseGenerator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(g -> g.biomeSource),
                    ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(g -> g.getSettings())
            ).apply(instance, instance.stable(BlockoutNoiseGenerator::new))
    );

    private static final BlockState AIR = Blocks.AIR.getDefaultState();

    private final Supplier<AquiferSampler.FluidLevelSampler> fluidSampler;

    public BlockoutNoiseGenerator(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> settings) {
        super(biomeSource, settings);
        this.fluidSampler = Suppliers.memoize(() -> createFluidLevelSamplerInternal(settings.value()));
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    // ── fluid ─────────────────────────────────────────────────────────────────

    protected static AquiferSampler.FluidLevelSampler createFluidLevelSamplerInternal(ChunkGeneratorSettings settings) {
        AquiferSampler.FluidLevel lava = new AquiferSampler.FluidLevel(-54, Blocks.LAVA.getDefaultState());
        int seaLevel = settings.seaLevel();
        AquiferSampler.FluidLevel water = new AquiferSampler.FluidLevel(seaLevel, settings.defaultFluid());
        return (x, y, z) -> y < Math.min(-54, seaLevel) ? lava : water;
    }

    // ── noise sampler ─────────────────────────────────────────────────────────

    protected ChunkNoiseSampler createChunkNoiseSamplerInternal(Chunk chunk, StructureAccessor world, Blender blender, NoiseConfig noiseConfig) {
        return ChunkNoiseSampler.create(
                chunk,
                noiseConfig,
                StructureWeightSampler.createStructureWeightSampler(world, chunk.getPos()),
                this.getSettings().value(),
                this.fluidSampler.get(),
                blender
        );
    }

    // ── populateBiomes ────────────────────────────────────────────────────────

    @Override
    public CompletableFuture<Chunk> populateBiomes(NoiseConfig noiseConfig, Blender blender, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.supplyAsync(Util.debugSupplier("init_biomes", () -> {
            populateBiomesInternal(blender, noiseConfig, structureAccessor, chunk);
            return chunk;
        }), Util.getMainWorkerExecutor());
    }

    protected void populateBiomesInternal(Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        BiomeSupplier biomeSupplier = BelowZeroRetrogen.getBiomeSupplier(blender.getBiomeSupplier(this.biomeSource), chunk);
        chunk.populateBiomes(biomeSupplier, noiseConfig.getMultiNoiseSampler());
    }

    // ── buildSurface ──────────────────────────────────────────────────────────

    // The parent's buildSurface(ChunkRegion,...) calls this.buildSurface(Chunk,...) via
    // virtual dispatch, so we only need to override the second form.
    @VisibleForTesting
    @Override
    public void buildSurface(
            Chunk chunk,
            HeightContext heightContext,
            NoiseConfig noiseConfig,
            StructureAccessor structureAccessor,
            BiomeAccess biomeAccess,
            Registry<Biome> biomeRegistry,
            Blender blender
    ) {
        ChunkNoiseSampler sampler = chunk.getOrCreateChunkNoiseSampler(
                c -> createChunkNoiseSamplerInternal(c, structureAccessor, blender, noiseConfig)
        );
        ChunkGeneratorSettings settings = this.getSettings().value();
        noiseConfig.getSurfaceBuilder().buildSurface(
                noiseConfig, biomeAccess, biomeRegistry,
                settings.usesLegacyRandom(), heightContext,
                chunk, sampler, settings.surfaceRule()
        );
    }

    // ── carve ─────────────────────────────────────────────────────────────────

    // `this` extends NoiseChunkGenerator, so CarverContext accepts it directly.
    @Override
    public void carve(
            ChunkRegion chunkRegion,
            long seed,
            NoiseConfig noiseConfig,
            BiomeAccess biomeAccess,
            StructureAccessor structureAccessor,
            Chunk chunk,
            GenerationStep.Carver carverStep
    ) {
        BiomeAccess biomeAccess2 = biomeAccess.withSource(
                (bx, by, bz) -> this.biomeSource.getBiome(bx, by, bz, noiseConfig.getMultiNoiseSampler())
        );
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(RandomSeed.getSeed()));
        ChunkPos chunkPos = chunk.getPos();
        ChunkNoiseSampler cns = chunk.getOrCreateChunkNoiseSampler(
                c -> createChunkNoiseSamplerInternal(c, structureAccessor, Blender.getBlender(chunkRegion), noiseConfig)
        );
        AquiferSampler aquifer = cns.getAquiferSampler();
        CarverContext carverContext = new CarverContext(
                this, chunkRegion.getRegistryManager(), chunk.getHeightLimitView(),
                cns, noiseConfig, this.getSettings().value().surfaceRule()
        );
        CarvingMask carvingMask = ((ProtoChunk) chunk).getOrCreateCarvingMask(carverStep);

        for (int dz = -8; dz <= 8; dz++) {
            for (int dx = -8; dx <= 8; dx++) {
                ChunkPos neighborPos = new ChunkPos(chunkPos.x + dx, chunkPos.z + dz);
                Chunk neighbor = chunkRegion.getChunk(neighborPos.x, neighborPos.z);
                @SuppressWarnings("deprecation")
                GenerationSettings genSettings = neighbor.getOrCreateGenerationSettings(
                        () -> this.getGenerationSettings(
                                this.biomeSource.getBiome(
                                        BiomeCoords.fromBlock(neighborPos.getStartX()), 0,
                                        BiomeCoords.fromBlock(neighborPos.getStartZ()),
                                        noiseConfig.getMultiNoiseSampler()
                                )
                        )
                );
                Iterable<RegistryEntry<ConfiguredCarver<?>>> carvers = genSettings.getCarversForStep(carverStep);
                int index = 0;
                for (RegistryEntry<ConfiguredCarver<?>> entry : carvers) {
                    ConfiguredCarver<?> carver = entry.value();
                    chunkRandom.setCarverSeed(seed + index, neighborPos.x, neighborPos.z);
                    if (carver.shouldCarve(chunkRandom)) {
                        carver.carve(carverContext, chunk, biomeAccess2::getBiome, chunkRandom, aquifer, neighborPos, carvingMask);
                    }
                    index++;
                }
            }
        }
    }

    // ── populateNoise ─────────────────────────────────────────────────────────

    @Override
    public CompletableFuture<Chunk> populateNoise(Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        GenerationShapeConfig shape = this.getSettings().value().generationShapeConfig().trimHeight(chunk.getHeightLimitView());
        int minY = shape.minimumY();
        int minCellY = MathHelper.floorDiv(minY, shape.verticalCellBlockCount());
        int cellHeight = MathHelper.floorDiv(shape.height(), shape.verticalCellBlockCount());
        if (cellHeight <= 0) return CompletableFuture.completedFuture(chunk);

        return CompletableFuture.supplyAsync(Util.debugSupplier("wgen_fill_noise", () -> {
            int topSection = chunk.getSectionIndex(cellHeight * shape.verticalCellBlockCount() - 1 + minY);
            int bottomSection = chunk.getSectionIndex(minY);
            Set<ChunkSection> locked = Sets.newHashSet();
            for (int i = topSection; i >= bottomSection; i--) {
                ChunkSection s = chunk.getSection(i);
                s.lock();
                locked.add(s);
            }
            Chunk result;
            try {
                result = populateNoiseInternal(blender, structureAccessor, noiseConfig, chunk, minCellY, cellHeight);
            } finally {
                for (ChunkSection s : locked) s.unlock();
            }
            return result;
        }), Util.getMainWorkerExecutor());
    }

    protected Chunk populateNoiseInternal(Blender blender, StructureAccessor structureAccessor, NoiseConfig noiseConfig, Chunk chunk, int minimumCellY, int cellHeight) {
        ChunkNoiseSampler cns = chunk.getOrCreateChunkNoiseSampler(
                c -> createChunkNoiseSamplerInternal(c, structureAccessor, blender, noiseConfig)
        );
        Heightmap oceanFloor = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap worldSurface = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
        ChunkPos chunkPos = chunk.getPos();
        int startX = chunkPos.getStartX();
        int startZ = chunkPos.getStartZ();
        AquiferSampler aquifer = cns.getAquiferSampler();
        cns.sampleStartDensity();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        // getHorizontalCellBlockCount / getVerticalCellBlockCount — widened via access widener
        int hCell = cns.getHorizontalCellBlockCount();
        int vCell = cns.getVerticalCellBlockCount();
        int hCells = 16 / hCell;

        for (int cellX = 0; cellX < hCells; cellX++) {
            cns.sampleEndDensity(cellX);
            for (int cellZ = 0; cellZ < hCells; cellZ++) {
                int sectionIdx = chunk.countVerticalSections() - 1;
                ChunkSection section = chunk.getSection(sectionIdx);

                for (int cellY = cellHeight - 1; cellY >= 0; cellY--) {
                    cns.onSampledCellCorners(cellY, cellZ);
                    for (int subY = vCell - 1; subY >= 0; subY--) {
                        int blockY = (minimumCellY + cellY) * vCell + subY;
                        int localY = blockY & 15;
                        int newIdx = chunk.getSectionIndex(blockY);
                        if (sectionIdx != newIdx) {
                            sectionIdx = newIdx;
                            section = chunk.getSection(newIdx);
                        }
                        cns.interpolateY(blockY, (double) subY / vCell);

                        for (int subX = 0; subX < hCell; subX++) {
                            int blockX = startX + cellX * hCell + subX;
                            int localX = blockX & 15;
                            cns.interpolateX(blockX, (double) subX / hCell);

                            for (int subZ = 0; subZ < hCell; subZ++) {
                                int blockZ = startZ + cellZ * hCell + subZ;
                                int localZ = blockZ & 15;
                                cns.interpolateZ(blockZ, (double) subZ / hCell);

                                // sampleBlockState — widened via access widener
                                BlockState state = cns.sampleBlockState();
                                if (state == null) state = this.getSettings().value().defaultBlock();
                                state = getBlockState(cns, blockX, blockY, blockZ, state);

                                if (state != AIR && !SharedConstants.isOutsideGenerationArea(chunk.getPos())) {
                                    section.setBlockState(localX, localY, localZ, state, false);
                                    oceanFloor.trackUpdate(localX, blockY, localZ, state);
                                    worldSurface.trackUpdate(localX, blockY, localZ, state);
                                    if (aquifer.needsFluidTick() && !state.getFluidState().isEmpty()) {
                                        mutable.set(blockX, blockY, blockZ);
                                        chunk.markBlockForPostProcessing(mutable);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            cns.swapBuffers();
        }
        cns.stopInterpolation();
        return chunk;
    }

    /**
     * Override this in subclasses to remap block states during noise-based terrain generation.
     * Called once per block position; return a different BlockState to change what gets placed.
     */
    protected BlockState getBlockState(ChunkNoiseSampler sampler, int x, int y, int z, BlockState state) {
        return state;
    }

    // ── heightmap ─────────────────────────────────────────────────────────────

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        return sampleHeightmapInternal(world, noiseConfig, x, z, null, heightmap.getBlockPredicate()).orElse(world.getBottomY());
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        MutableObject<VerticalBlockSample> result = new MutableObject<>();
        sampleHeightmapInternal(world, noiseConfig, x, z, result, null);
        return result.getValue();
    }

    protected OptionalInt sampleHeightmapInternal(
            HeightLimitView world,
            NoiseConfig noiseConfig,
            int x,
            int z,
            @Nullable MutableObject<VerticalBlockSample> columnSample,
            @Nullable Predicate<BlockState> stopPredicate
    ) {
        GenerationShapeConfig shape = this.getSettings().value().generationShapeConfig().trimHeight(world);
        int vCell = shape.verticalCellBlockCount();
        int minY = shape.minimumY();
        int minCellY = MathHelper.floorDiv(minY, vCell);
        int cellHeight = MathHelper.floorDiv(shape.height(), vCell);
        if (cellHeight <= 0) return OptionalInt.empty();

        BlockState[] states = null;
        if (columnSample != null) {
            states = new BlockState[shape.height()];
            columnSample.setValue(new VerticalBlockSample(minY, states));
        }

        int hCell = shape.horizontalCellBlockCount();
        int cellX = Math.floorDiv(x, hCell);
        int cellZ = Math.floorDiv(z, hCell);
        int startX = cellX * hCell;
        int startZ = cellZ * hCell;
        double xLerp = (double) Math.floorMod(x, hCell) / hCell;
        double zLerp = (double) Math.floorMod(z, hCell) / hCell;

        // DensityFunctionTypes.Beardifier — widened via access widener
        ChunkNoiseSampler cns = new ChunkNoiseSampler(
                1, noiseConfig, startX, startZ, shape,
                DensityFunctionTypes.Beardifier.INSTANCE,
                this.getSettings().value(),
                this.fluidSampler.get(),
                Blender.getNoBlending()
        );
        cns.sampleStartDensity();
        cns.sampleEndDensity(0);

        for (int cy = cellHeight - 1; cy >= 0; cy--) {
            cns.onSampledCellCorners(cy, 0);
            for (int sy = vCell - 1; sy >= 0; sy--) {
                int blockY = (minCellY + cy) * vCell + sy;
                cns.interpolateY(blockY, (double) sy / vCell);
                cns.interpolateX(x, xLerp);
                cns.interpolateZ(z, zLerp);
                // sampleBlockState — widened via access widener
                BlockState raw = cns.sampleBlockState();
                BlockState resolved = raw == null ? this.getSettings().value().defaultBlock() : raw;
                if (states != null) states[cy * vCell + sy] = resolved;
                if (stopPredicate != null && stopPredicate.test(resolved)) {
                    cns.stopInterpolation();
                    return OptionalInt.of(blockY + 1);
                }
            }
        }
        cns.stopInterpolation();
        return OptionalInt.empty();
    }

    // ── debug HUD ─────────────────────────────────────────────────────────────

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {
        DecimalFormat fmt = new DecimalFormat("0.000");
        NoiseRouter router = noiseConfig.getNoiseRouter();
        DensityFunction.UnblendedNoisePos noisePos = new DensityFunction.UnblendedNoisePos(pos.getX(), pos.getY(), pos.getZ());
        double ridges = router.ridges().sample(noisePos);
        text.add(
                "NoiseRouter T: " + fmt.format(router.temperature().sample(noisePos))
                        + " V: " + fmt.format(router.vegetation().sample(noisePos))
                        + " C: " + fmt.format(router.continents().sample(noisePos))
                        + " E: " + fmt.format(router.erosion().sample(noisePos))
                        + " D: " + fmt.format(router.depth().sample(noisePos))
                        + " W: " + fmt.format(ridges)
                        + " PV: " + fmt.format((double) DensityFunctions.getPeaksValleysNoise((float) ridges))
                        + " AS: " + fmt.format(router.initialDensityWithoutJaggedness().sample(noisePos))
                        + " N: " + fmt.format(router.finalDensity().sample(noisePos))
        );
    }

    // ── spawn ─────────────────────────────────────────────────────────────────

    /**
     * Finds the nearest spawn position that is not inside an ocean or river biome.
     * Searches up to 6400 blocks from the world's default spawn point.
     * Falls back to the default spawn if no valid position is found.
     */
    public BlockPos findSpawnPos(ServerWorld world) {
        BlockPos origin = world.getSpawnPos();
        var result = world.locateBiome(
                biome -> !biome.isIn(BiomeTags.IS_OCEAN) && !biome.isIn(BiomeTags.IS_RIVER),
                origin, 6400, 32, 64
        );
        return result != null ? result.getFirst() : origin;
    }

    // ── misc ──────────────────────────────────────────────────────────────────

    @Override
    public int getWorldHeight() {
        return this.getSettings().value().generationShapeConfig().height();
    }

    @Override
    public int getSeaLevel() {
        return this.getSettings().value().seaLevel();
    }

    @Override
    public int getMinimumY() {
        return this.getSettings().value().generationShapeConfig().minimumY();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void populateEntities(ChunkRegion region) {
        if (!this.getSettings().value().mobGenerationDisabled()) {
            ChunkPos chunkPos = region.getCenterPos();
            RegistryEntry<Biome> biome = region.getBiome(chunkPos.getStartPos().withY(region.getTopY() - 1));
            ChunkRandom random = new ChunkRandom(new CheckedRandom(RandomSeed.getSeed()));
            random.setPopulationSeed(region.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
            SpawnHelper.populateEntities(region, biome, chunkPos, random);
        }
    }
}
