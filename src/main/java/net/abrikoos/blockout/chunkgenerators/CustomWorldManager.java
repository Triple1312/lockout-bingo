package net.abrikoos.blockout.chunkgenerators;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// Compilation stub — real implementation lives in the Fabric module.
// NeoForge has its own stub in neoforge/src/.../chunkgenerators/CustomWorldManager.java.
// This class is excluded from common's namedElements jar (see common/build.gradle).
public class CustomWorldManager {

    public enum GeneratorMode { NORMAL, BLOCK_SWAP, SINGLE_BLOCK }

    public static final RegistryKey<World> CUSTOM_OVERWORLD = null;
    public static final RegistryKey<World> CUSTOM_NETHER = null;
    public static final RegistryKey<World> CUSTOM_END = null;

    public static boolean worldsActive = false;

    public static @Nullable Long getCustomSeed(RegistryKey<World> key) { return null; }

    public static void createWorlds(MinecraftServer server, long seed, GeneratorMode mode) {}
    public static void createWorlds(MinecraftServer server, long seed, boolean useBlockSwap) {}
    public static void teleportPlayers(MinecraftServer server, List<ServerPlayerEntity> players) {}
    public static void removeWorlds(MinecraftServer server) {}
    public static void teleportPlayerIn(ServerPlayerEntity player, MinecraftServer server) {}
    public static void returnPlayer(ServerPlayerEntity player, MinecraftServer server) {}
    public static void respawnInCustomOverworld(ServerPlayerEntity player, MinecraftServer server) {}
    public static boolean isCustomDimension(RegistryKey<World> key) { return false; }
}
