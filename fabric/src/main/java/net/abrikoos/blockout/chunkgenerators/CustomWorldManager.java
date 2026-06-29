package net.abrikoos.blockout.chunkgenerators;

import net.abrikoos.blockout.Blockout;
import net.abrikoos.blockout.BlockoutLogger;
import net.abrikoos.blockout.mixin.accessors.MinecraftServerAccessor;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.SpawnLocating;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.abrikoos.blockout.chunkgenerators.BlockoutNoiseGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomWorldManager {

    public static final RegistryKey<World> CUSTOM_OVERWORLD = RegistryKey.of(
            RegistryKeys.WORLD, Identifier.of(Blockout.MOD_ID, "custom_overworld"));
    public static final RegistryKey<World> CUSTOM_NETHER = RegistryKey.of(
            RegistryKeys.WORLD, Identifier.of(Blockout.MOD_ID, "custom_nether"));
    public static final RegistryKey<World> CUSTOM_END = RegistryKey.of(
            RegistryKeys.WORLD, Identifier.of(Blockout.MOD_ID, "custom_end"));

    private static final Set<RegistryKey<World>> CUSTOM_KEYS = Set.of(CUSTOM_OVERWORLD, CUSTOM_NETHER, CUSTOM_END);

    // Stored per player-UUID: the position they were at before being teleported into a custom world.
    private static final Map<String, SavedLocation> savedLocations = new HashMap<>();

    // Stored per player-UUID: the last position they were at inside the game dimensions.
    private static final Map<String, SavedLocation> savedInGameLocations = new HashMap<>();

    private static final Map<RegistryKey<World>, Long> customSeeds = new HashMap<>();

    public static boolean worldsActive = false;

    public static @Nullable Long getCustomSeed(RegistryKey<World> key) {
        return customSeeds.get(key);
    }

    public record SavedLocation(RegistryKey<World> world, double x, double y, double z, float yaw, float pitch) {}

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    public enum GeneratorMode { NORMAL, BLOCK_SWAP, SINGLE_BLOCK }

    /**
     * Creates the three custom worlds (overworld, nether, end) with the given seed.
     * @param mode controls which chunk generator the custom overworld uses; nether and end always use normal noise.
     */
    public static void createWorlds(MinecraftServer server, long seed, GeneratorMode mode) {
        long validSeed = findValidSeed(server, seed);
        spawnWorld(server, CUSTOM_OVERWORLD, DimensionTypes.OVERWORLD, validSeed, mode);
        spawnWorld(server, CUSTOM_NETHER,    DimensionTypes.THE_NETHER, validSeed, GeneratorMode.NORMAL);
        spawnWorld(server, CUSTOM_END,       DimensionTypes.THE_END,    validSeed, GeneratorMode.NORMAL);
        worldsActive = true;
        BlockoutLogger.log("Custom worlds created with seed " + validSeed + " (" + mode + ")");
    }

    /**
     * Returns the first seed >= the given seed for which block (0, 0) in the overworld
     * is not an ocean or river biome. Tries up to 100 candidates before giving up.
     */
    private static long findValidSeed(MinecraftServer server, long seed) {
        var lookup = server.getRegistryManager().createRegistryLookup();
        var settings = lookup.getOrThrow(RegistryKeys.CHUNK_GENERATOR_SETTINGS)
                             .getOrThrow(ChunkGeneratorSettings.OVERWORLD).value();
        var biomeSource = MultiNoiseBiomeSource.create(
                lookup.getOrThrow(RegistryKeys.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
                      .getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD));
        var noiseParams = lookup.getOrThrow(RegistryKeys.NOISE_PARAMETERS);

        for (long candidate = seed; candidate < seed + 100; candidate++) {
            var sampler = NoiseConfig.create(settings, noiseParams, candidate).getMultiNoiseSampler();
            var biome = biomeSource.getBiome(0, BiomeCoords.fromBlock(64), 0, sampler);
            if (!biome.isIn(BiomeTags.IS_OCEAN) && !biome.isIn(BiomeTags.IS_RIVER)) {
                if (candidate != seed)
                    BlockoutLogger.log("Seed " + seed + " had ocean/river at origin, using " + candidate + " instead");
                return candidate;
            }
        }
        BlockoutLogger.log("Could not find valid seed after 100 attempts, using original: " + seed);
        return seed;
    }

    /** Convenience overload kept for existing call-sites. */
    public static void createWorlds(MinecraftServer server, long seed, boolean useBlockSwap) {
        createWorlds(server, seed, useBlockSwap ? GeneratorMode.BLOCK_SWAP : GeneratorMode.NORMAL);
    }

    /**
     * Saves each player's current position, then teleports them to the custom overworld spawn.
     * Only the positions saved here are used by returnPlayers().
     */
    public static void teleportPlayers(MinecraftServer server, List<ServerPlayerEntity> players) {
        ServerWorld customOverworld = server.getWorld(CUSTOM_OVERWORLD);
        if (customOverworld == null) {
            BlockoutLogger.log("Custom overworld does not exist — call createWorlds() first.");
            return;
        }
        for (ServerPlayerEntity player : players) {
            savedLocations.put(
                    player.getUuidAsString(),
                    new SavedLocation(
                            player.getServerWorld().getRegistryKey(),
                            player.getX(), player.getY(), player.getZ(),
                            player.getYaw(), player.getPitch()
                    )
            );
            teleportToWorldSpawn(player, customOverworld);
        }
    }

    /**
     * Teleports every player that was moved by teleportPlayers() back to their saved position.
     * Clears all saved positions afterwards.
     */
    public static void returnPlayers(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            SavedLocation loc = savedLocations.remove(player.getUuidAsString());
            if (loc == null) continue;

            ServerWorld target = server.getWorld(loc.world());
            if (target == null) target = server.getOverworld();

            player.teleportTo(new TeleportTarget(
                    target,
                    new Vec3d(loc.x(), loc.y(), loc.z()),
                    Vec3d.ZERO,
                    loc.yaw(), loc.pitch(),
                    TeleportTarget.NO_OP
            ));
        }
        // Discard entries for players who were offline when this was called.
        savedLocations.clear();
    }

    /**
     * Returns all players, then removes the three custom worlds from the server and deletes
     * their save directories so that the next createWorlds() produces a completely fresh world.
     */
    public static void removeWorlds(MinecraftServer server) {
        returnPlayers(server);
        evacuateStrandedPlayers(server);

        MinecraftServerAccessor accessor = (MinecraftServerAccessor) server;

        for (RegistryKey<World> key : List.of(CUSTOM_OVERWORLD, CUSTOM_NETHER, CUSTOM_END)) {
            if (!accessor.getWorlds().containsKey(key)) continue;
            accessor.getWorlds().remove(key);
            deleteWorldDirectory(accessor.getSession(), key);
        }

        customSeeds.clear();
        savedInGameLocations.clear();
        worldsActive = false;
        BlockoutLogger.log("Custom worlds removed.");
    }

    // -------------------------------------------------------------------------
    // Internal helpers
    // -------------------------------------------------------------------------

    private static ServerWorld spawnWorld(MinecraftServer server,
                                          RegistryKey<World> key,
                                          RegistryKey<DimensionType> dimTypeKey,
                                          long seed,
                                          GeneratorMode mode) {
        MinecraftServerAccessor accessor = (MinecraftServerAccessor) server;

        // Idempotent: skip if already exists.
        ServerWorld existing = server.getWorld(key);
        if (existing != null) return existing;

        customSeeds.put(key, seed);

        RegistryEntry<DimensionType> dimType = server.getRegistryManager()
                .get(RegistryKeys.DIMENSION_TYPE)
                .getEntry(dimTypeKey)
                .orElseThrow(() -> new IllegalStateException("Missing dimension type: " + dimTypeKey));

        ChunkGenerator generator = buildGenerator(server, dimTypeKey, mode);
        DimensionOptions options = new DimensionOptions(dimType, generator);

        ServerWorld overworld = server.getOverworld();
        ServerWorld world = new ServerWorld(
                server,
                accessor.getWorkerExecutor(),
                accessor.getSession(),
                (ServerWorldProperties) overworld.getLevelProperties(),
                key,
                options,
                NO_OP_LISTENER,
                false,
                seed,
                List.of(),
                false,
                null
        );

        accessor.getWorlds().put(key, world);
        return world;
    }

    private static ChunkGenerator buildGenerator(MinecraftServer server,
                                                  RegistryKey<DimensionType> dimTypeKey,
                                                  GeneratorMode mode) {
        var lookup = server.getRegistryManager().createRegistryLookup();

        RegistryEntryLookup<Biome> biomeLookup =
                lookup.getOrThrow(RegistryKeys.BIOME);
        RegistryEntryLookup<MultiNoiseBiomeSourceParameterList> paramLookup =
                lookup.getOrThrow(RegistryKeys.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);
        RegistryEntryLookup<ChunkGeneratorSettings> settingsLookup =
                lookup.getOrThrow(RegistryKeys.CHUNK_GENERATOR_SETTINGS);

        if (dimTypeKey.equals(DimensionTypes.THE_END)) {
            return new BlockoutNoiseGenerator(
                    TheEndBiomeSource.createVanilla(biomeLookup),
                    settingsLookup.getOrThrow(ChunkGeneratorSettings.END)
            );
        } else if (dimTypeKey.equals(DimensionTypes.THE_NETHER)) {
            return new BlockoutNoiseGenerator(
                    MultiNoiseBiomeSource.create(paramLookup.getOrThrow(MultiNoiseBiomeSourceParameterLists.NETHER)),
                    settingsLookup.getOrThrow(ChunkGeneratorSettings.NETHER)
            );
        } else {
            // Overworld
            var biomeSource = MultiNoiseBiomeSource.create(
                    paramLookup.getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD));
            var settings = settingsLookup.getOrThrow(ChunkGeneratorSettings.OVERWORLD);
            return switch (mode) {
                case BLOCK_SWAP    -> new BlockSwapChunkGenerator(biomeSource, settings);
                case SINGLE_BLOCK  -> new SingleBlockChunkGenerator(biomeSource, settings);
                default            -> new BlockoutNoiseGenerator(biomeSource, settings);
            };
        }
    }

    /** Sends players still in any custom world to the overworld spawn (safety net for removeWorlds). */
    private static void evacuateStrandedPlayers(MinecraftServer server) {
        ServerWorld overworld = server.getOverworld();
        BlockPos spawn = overworld.getSpawnPos();
        Vec3d safePos = new Vec3d(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5);

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (CUSTOM_KEYS.contains(player.getServerWorld().getRegistryKey())) {
                player.teleportTo(new TeleportTarget(overworld, safePos, Vec3d.ZERO, 0f, 0f, TeleportTarget.NO_OP));
            }
        }
    }

    public static boolean isCustomDimension(RegistryKey<World> key) {
        return CUSTOM_KEYS.contains(key);
    }

    public static void respawnInCustomOverworld(ServerPlayerEntity player, MinecraftServer server) {
        ServerWorld customOverworld = server.getWorld(CUSTOM_OVERWORLD);
        if (customOverworld == null) return;
        teleportToWorldSpawn(player, customOverworld);
    }

    /** Saves the player's current position and teleports them into the game dimensions.
     *  Restores their last known in-game position if they have been in before. */
    public static void teleportPlayerIn(ServerPlayerEntity player, MinecraftServer server) {
        ServerWorld customOverworld = server.getWorld(CUSTOM_OVERWORLD);
        if (customOverworld == null) return;
        // Only save the pre-game location the first time (don't overwrite it on re-entry).
        if (!savedLocations.containsKey(player.getUuidAsString())) {
            savedLocations.put(
                    player.getUuidAsString(),
                    new SavedLocation(
                            player.getServerWorld().getRegistryKey(),
                            player.getX(), player.getY(), player.getZ(),
                            player.getYaw(), player.getPitch()
                    )
            );
        }
        SavedLocation inGame = savedInGameLocations.get(player.getUuidAsString());
        if (inGame != null) {
            ServerWorld target = server.getWorld(inGame.world());
            if (target == null) target = customOverworld;
            player.teleportTo(new TeleportTarget(target, new Vec3d(inGame.x(), inGame.y(), inGame.z()), Vec3d.ZERO, inGame.yaw(), inGame.pitch(), TeleportTarget.NO_OP));
        } else {
            teleportToWorldSpawn(player, customOverworld);
        }
    }

    /** Saves the player's current in-game position then returns them to their pre-game position. */
    public static void returnPlayer(ServerPlayerEntity player, MinecraftServer server) {
        if (isCustomDimension(player.getServerWorld().getRegistryKey())) {
            savedInGameLocations.put(
                    player.getUuidAsString(),
                    new SavedLocation(
                            player.getServerWorld().getRegistryKey(),
                            player.getX(), player.getY(), player.getZ(),
                            player.getYaw(), player.getPitch()
                    )
            );
        }
        SavedLocation loc = savedLocations.remove(player.getUuidAsString());
        if (loc == null) {
            ServerWorld overworld = server.getOverworld();
            BlockPos spawn = overworld.getSpawnPos();
            player.teleportTo(new TeleportTarget(overworld, new Vec3d(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5), Vec3d.ZERO, 0f, 0f, TeleportTarget.NO_OP));
            return;
        }
        ServerWorld target = server.getWorld(loc.world());
        if (target == null) target = server.getOverworld();
        player.teleportTo(new TeleportTarget(target, new Vec3d(loc.x(), loc.y(), loc.z()), Vec3d.ZERO, loc.yaw(), loc.pitch(), TeleportTarget.NO_OP));
    }

    private static void teleportToWorldSpawn(ServerPlayerEntity player, ServerWorld world) {
        BlockPos spawnPos = findGoodSpawnPos(world);
        ChunkPos chunkPos = new ChunkPos(spawnPos);
        world.getChunk(chunkPos.x, chunkPos.z); // force-generate spawn chunk
        BlockPos safePos = SpawnLocating.findServerSpawnPoint(world, chunkPos);
        if (safePos == null) {
            int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, spawnPos.getX(), spawnPos.getZ());
            safePos = new BlockPos(spawnPos.getX(), y, spawnPos.getZ());
        }
        Vec3d pos = new Vec3d(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
        player.teleportTo(new TeleportTarget(world, pos, Vec3d.ZERO, 0f, 0f, TeleportTarget.NO_OP));
    }

    /** Searches outward from the world's default spawn for a biome that isn't an ocean or river. */
    private static BlockPos findGoodSpawnPos(ServerWorld world) {
        if (world.getChunkManager().getChunkGenerator() instanceof BlockoutNoiseGenerator gen) {
            return gen.findSpawnPos(world);
        }
        return world.getSpawnPos();
    }

    private static void deleteWorldDirectory(LevelStorage.Session session, RegistryKey<World> key) {
        // Custom dimensions are stored under <save>/dimensions/<namespace>/<path>/
        Identifier id = key.getValue();
        Path dir = session.getDirectory(net.minecraft.util.WorldSavePath.ROOT)
                .resolve("dimensions")
                .resolve(id.getNamespace())
                .resolve(id.getPath());
        if (!Files.exists(dir)) return;
        try {
            Files.walk(dir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                        try { Files.delete(p); } catch (IOException ignored) {}
                    });
            BlockoutLogger.log("Deleted world directory: " + dir);
        } catch (IOException e) {
            BlockoutLogger.log("Failed to delete world directory " + dir + ": " + e.getMessage());
        }
    }

    private static final WorldGenerationProgressListener NO_OP_LISTENER = new WorldGenerationProgressListener() {
        public void start(ChunkPos spawnPos) {}
        public void start() {}
        public void setChunkStatus(ChunkPos pos, @Nullable ChunkStatus status) {}
        public void stop() {}
    };
}
