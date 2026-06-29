package net.abrikoos.blockout.chunkgenerators;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// NeoForge no-op stub. Custom dimensions use Fabric-specific APIs and cannot run on NeoForge.
// All worldsActive-guarded code in Blockout.java is skipped because worldsActive stays false.
public class CustomWorldManager {

    public enum GeneratorMode { NORMAL, BLOCK_SWAP, SINGLE_BLOCK }

    public static final ResourceKey<Level> CUSTOM_OVERWORLD = null;
    public static final ResourceKey<Level> CUSTOM_NETHER = null;
    public static final ResourceKey<Level> CUSTOM_END = null;

    public static boolean worldsActive = false;

    public static @Nullable Long getCustomSeed(ResourceKey<Level> key) { return null; }

    public static void createWorlds(MinecraftServer server, long seed, GeneratorMode mode) {}
    public static void createWorlds(MinecraftServer server, long seed, boolean useBlockSwap) {}
    public static void teleportPlayers(MinecraftServer server, List<ServerPlayer> players) {}
    public static void removeWorlds(MinecraftServer server) {}
    public static void teleportPlayerIn(ServerPlayer player, MinecraftServer server) {}
    public static void returnPlayer(ServerPlayer player, MinecraftServer server) {}
    public static void respawnInCustomOverworld(ServerPlayer player, MinecraftServer server) {}
    public static boolean isCustomDimension(ResourceKey<Level> key) { return false; }
}
