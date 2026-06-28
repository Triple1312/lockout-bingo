package net.abrikoos.blockout.chunkgenerators;

import net.abrikoos.blockout.mixin.accessors.MinecraftServerAccessor;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DimensionManager {

    /**
     * Gets an existing custom world by id, or creates it using the given generator.
     * Call this on the server thread (e.g. from a command or game event handler).
     */
    public static ServerWorld getOrCreate(MinecraftServer server, Identifier id, ChunkGenerator generator) {
        RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, id);

        ServerWorld existing = server.getWorld(key);
        if (existing != null) return existing;

        MinecraftServerAccessor accessor = (MinecraftServerAccessor) server;
        ServerWorld overworld = server.getOverworld();

        RegistryEntry<DimensionType> dimType = server.getRegistryManager()
                .get(RegistryKeys.DIMENSION_TYPE)
                .getEntry(DimensionTypes.OVERWORLD)
                .orElseThrow();

        DimensionOptions options = new DimensionOptions(dimType, generator);

        ServerWorld world = new ServerWorld(
                server,
                accessor.getWorkerExecutor(),
                accessor.getSession(),
                (ServerWorldProperties) overworld.getLevelProperties(),
                key,
                options,
                NO_OP_LISTENER,
                false,           // not a debug world
                overworld.getSeed(),
                List.of(),       // no special spawners
                false,           // don't tick time independently
                null             // no saved random sequences
        );

        accessor.getWorlds().put(key, world);
        return world;
    }

    /** Teleports every online player into the given world at its spawn position. */
    public static void teleportAll(MinecraftServer server, ServerWorld target) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            teleport(player, target);
        }
    }

    /** Teleports a single player into the given world at its spawn position. */
    public static void teleport(ServerPlayerEntity player, ServerWorld target) {
        BlockPos spawnPos = target.getSpawnPos();
        int y = target.getTopY(net.minecraft.world.Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                spawnPos.getX(), spawnPos.getZ());
        Vec3d pos = new Vec3d(spawnPos.getX() + 0.5, y, spawnPos.getZ() + 0.5);
        player.teleportTo(new TeleportTarget(target, pos, Vec3d.ZERO, 0f, 0f, TeleportTarget.NO_OP));
    }

    private static final WorldGenerationProgressListener NO_OP_LISTENER = new WorldGenerationProgressListener() {
        public void start(ChunkPos spawnPos) {}
        public void start() {}
        public void setChunkStatus(ChunkPos pos, @Nullable ChunkStatus status) {}
        public void stop() {}
    };
}
