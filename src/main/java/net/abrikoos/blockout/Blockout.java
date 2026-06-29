package net.abrikoos.blockout;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.abrikoos.blockout.chunkgenerators.CustomWorldManager;
import net.abrikoos.blockout.item.BlockoutModItems;
import net.abrikoos.blockout.network.NetworkBridge;
import net.abrikoos.blockout.networkv2.compass.AskCompassPacket;
import net.abrikoos.blockout.networkv2.game.*;
import net.abrikoos.blockout.networkv2.get.GetBoard;
import net.abrikoos.blockout.networkv2.get.GetGameInfo;
import net.abrikoos.blockout.networkv2.get.GetTeamData;
import net.abrikoos.blockout.networkv2.team.*;
import net.abrikoos.blockout.server.gamestate.GameState;
import net.abrikoos.blockout.server.listeners.EntityKillListener;
import net.abrikoos.blockout.server.listeners.PlayerDeathListener;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Blockout {

    public static final String MOD_ID = "blockout";

    public static PlayerDeathListener playerDeathListener = new PlayerDeathListener();
    public static EntityKillListener entityKillListener = new EntityKillListener();

    public static void init() {
        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "goal_complete"), SoundEvent.of(Identifier.of("blockout", "goal_complete")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "goal_fail"), SoundEvent.of(Identifier.of("blockout", "goal_fail")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "goal_success"), SoundEvent.of(Identifier.of("blockout", "goal_success")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "finish_countdown"), SoundEvent.of(Identifier.of("blockout", "finish_countdown")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "race_start"), SoundEvent.of(Identifier.of("blockout", "race_start")));

        BlockoutModItems.initialize();
        BlockoutLogger.log("Blockout initialized!");
    }

    // C2S packet handlers — called by platform-specific receiver registration

    public static void handleGetBoard(MinecraftServer server, ServerPlayerEntity player) {
        server.execute(() -> NetworkBridge.sendToPlayer(player, GameState.info.board()));
    }

    public static void handleGetGameInfo(MinecraftServer server, ServerPlayerEntity player) {
        server.execute(() -> NetworkBridge.sendToPlayer(player, GameState.info));
    }

    public static void handleGetTeamData(MinecraftServer server, ServerPlayerEntity player) {
        server.execute(() -> NetworkBridge.sendToPlayer(player, GameState.teamRegistry));
    }

    public static void handleRotateTeamColor(MinecraftServer server, RotateTeamColor payload) {
        server.execute(() -> GameState.teamRegistry.rotateTeamColor(payload.teamuuid()));
    }

    public static void handleAddPlayerToTeam(MinecraftServer server, ServerPlayerEntity player, AddPlayerToTeamV2 payload) {
        server.execute(() -> {
            try {
                boolean oldTeamPlaying = false;
                if (GameState.inGame() && CustomWorldManager.worldsActive) {
                    try {
                        String oldTeamUUID = GameState.teamRegistry.getTeamDataByPlayerUUID(payload.puuid()).teamUUID;
                        oldTeamPlaying = GameState.info.teamUUIDs().contains(oldTeamUUID);
                    } catch (Exception ignored) {}
                }
                try {
                    GameState.teamRegistry.removePlayerFromTeam(payload.puuid());
                } catch (Exception ignored) {}
                GameState.teamRegistry.addPlayerToTeam(payload.puuid(), payload.teamuuid());
                if (GameState.inGame() && CustomWorldManager.worldsActive) {
                    boolean newTeamPlaying = GameState.info.teamUUIDs().contains(payload.teamuuid());
                    ServerPlayerEntity p = player.getServer().getPlayerManager().getPlayer(java.util.UUID.fromString(payload.puuid()));
                    if (p != null) {
                        if (!oldTeamPlaying && newTeamPlaying) {
                            CustomWorldManager.teleportPlayerIn(p, player.getServer());
                        } else if (oldTeamPlaying && !newTeamPlaying) {
                            CustomWorldManager.returnPlayer(p, player.getServer());
                        }
                    }
                }
            } catch (Exception e) {
                BlockoutLogger.log("Error adding player to team: " + e.getMessage());
            }
        });
    }

    public static void handleAddTeam(MinecraftServer server, AddTeamV2 payload) {
        server.execute(() -> GameState.teamRegistry.createNewTeam(payload.name()));
    }

    public static void handleChangeTeamColor(MinecraftServer server, ChangeTeamColorV2 payload) {
        server.execute(() -> {
            try {
                GameState.teamRegistry.changeTeamColor(payload.teamuuid(), payload.color());
            } catch (Exception e) {
                BlockoutLogger.log("Error changing team color: " + e.getMessage());
            }
        });
    }

    public static void handleStartGame(MinecraftServer server, StartGameRequestPacket payload) {
        server.execute(() -> GameState.StartGame(payload));
    }

    public static void handleRemoveTeam(MinecraftServer server, RemoveTeamV2 payload) {
        server.execute(() -> GameState.teamRegistry.removeTeam(payload.uuid()));
    }

    public static void handleRemovePlayerFromTeam(MinecraftServer server, ServerPlayerEntity player, RemovePlayerFromTeamV2 payload) {
        server.execute(() -> {
            try {
                if (GameState.inGame() && CustomWorldManager.worldsActive) {
                    try {
                        String oldTeamUUID = GameState.teamRegistry.getTeamDataByPlayerUUID(payload.puuid()).teamUUID;
                        if (GameState.info.teamUUIDs().contains(oldTeamUUID)) {
                            ServerPlayerEntity p = player.getServer().getPlayerManager().getPlayer(java.util.UUID.fromString(payload.puuid()));
                            if (p != null) CustomWorldManager.returnPlayer(p, player.getServer());
                        }
                    } catch (Exception ignored) {}
                }
                GameState.teamRegistry.removePlayerFromTeam(payload.puuid());
            } catch (Exception e) {
                BlockoutLogger.log("Error removing player from team: " + e.getMessage());
            }
        });
    }

    public static void handleAskCompass(MinecraftServer server, ServerPlayerEntity player) {
        server.execute(() -> {
            if (!player.getInventory().contains(BlockoutModItems.PLAYER_TRACKING_COMPASS.getDefaultStack())) {
                player.giveItemStack(BlockoutModItems.PLAYER_TRACKING_COMPASS.getDefaultStack());
            }
        });
    }

    public static void handleTeammateRespawnRequest(MinecraftServer server, ServerPlayerEntity player, TeammateRespawnRequestPacket payload) {
        server.execute(() -> GameState.registerPlayerRespawnTarget(player.getUuidAsString(), payload.playerUUID()));
    }

    public static void handleEndGame(MinecraftServer server, ServerPlayerEntity player) {
        server.execute(() -> {
            if (!GameState.inGame()) return;
            GameState.destroyGame();
            CustomWorldManager.removeWorlds(player.getServer());
            GameStartPacket emptyPacket = GameStartPacket.empty();
            for (ServerPlayerEntity p : player.getServer().getPlayerManager().getPlayerList()) {
                p.getInventory().remove(stack -> stack.isOf(BlockoutModItems.PLAYER_TRACKING_COMPASS), Integer.MAX_VALUE, p.playerScreenHandler.getCraftingInput());
                NetworkBridge.sendToPlayer(p, emptyPacket);
            }
        });
    }

    public static void handlePlayerRespawn(ServerPlayerEntity newPlayer) {
        if (!CustomWorldManager.worldsActive) return;
        if (CustomWorldManager.isCustomDimension(newPlayer.getServerWorld().getRegistryKey())) return;
        if (!GameState.teamRegistry.playerInTeam(newPlayer.getUuidAsString())) return;
        CustomWorldManager.respawnInCustomOverworld(newPlayer, newPlayer.getServer());
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(CommandManager.literal("blackout").executes(context -> {
            context.getSource().sendFeedback(() -> Text.literal("Called /blackout with no arguments."), false);
            return 1;
        }));

        dispatcher.register(CommandManager.literal("goalComplete")
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("goalId", IntegerArgumentType.integer())
                    .executes(context -> {
                        String playerName = EntityArgumentType.getPlayer(context, "player").getName().getString();
                        int goalId = IntegerArgumentType.getInteger(context, "goalId");
                        GameState.goalComplete(playerName, goalId);
                        return 1;
                    })))
        );
    }
}
