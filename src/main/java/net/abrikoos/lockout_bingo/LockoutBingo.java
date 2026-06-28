package net.abrikoos.lockout_bingo;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.abrikoos.lockout_bingo.networkv2.compass.AskCompassPacket;
import net.abrikoos.lockout_bingo.networkv2.game.*;
import net.abrikoos.lockout_bingo.networkv2.get.GetBoard;
import net.abrikoos.lockout_bingo.networkv2.get.GetGameInfo;
import net.abrikoos.lockout_bingo.networkv2.get.GetTeamData;
import net.abrikoos.lockout_bingo.networkv2.team.*;
import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.abrikoos.lockout_bingo.item.LockoutModItems;
import net.abrikoos.lockout_bingo.server.listeners.EntityKillListener;
import net.abrikoos.lockout_bingo.server.listeners.PlayerDeathListener;
import net.abrikoos.lockout_bingo.server.listeners.TickListener;
import net.abrikoos.lockout_bingo.networkv2.compass.PlayersPositionPacket;
import net.fabricmc.api.ModInitializer;

import net.abrikoos.lockout_bingo.server.gamestate.GameStatePersistence;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.abrikoos.lockout_bingo.chunkgenerators.CustomWorldManager;
import net.abrikoos.lockout_bingo.networkv2.game.EndGamePacket;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetPotionLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.potion.Potions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


public class LockoutBingo implements ModInitializer {

	public static final String MOD_ID = "lockout-bingo";

	public static PlayerDeathListener playerDeathListener = new PlayerDeathListener();

	public static EntityKillListener entityKillListener = new EntityKillListener();


	@Override
	public void onInitialize() {

		PayloadTypeRegistry.playS2C().register(PlayersPositionPacket.ID, PlayersPositionPacket.CODEC);
		PayloadTypeRegistry.playS2C().register(GameStartPacket.ID, GameStartPacket.CODEC);
		PayloadTypeRegistry.playS2C().register(GoalBoardUpdatePacket.ID, GoalBoardUpdatePacket.CODEC);
		PayloadTypeRegistry.playS2C().register(TeamRegV2.ID, ServerTeamRegV2.CODEC);
		PayloadTypeRegistry.playS2C().register(StructureLocationPacket.ID, StructureLocationPacket.CODEC);
		PayloadTypeRegistry.playS2C().register(StructureLocationsPacket.ID, StructureLocationsPacket.CODEC);

		PayloadTypeRegistry.playC2S().register(GetTeamData.ID, GetTeamData.CODEC);
		PayloadTypeRegistry.playC2S().register(GetGameInfo.ID, GetGameInfo.CODEC);
		PayloadTypeRegistry.playC2S().register(GetBoard.ID, GetBoard.CODEC);
		PayloadTypeRegistry.playC2S().register(AddPlayerToTeamV2.ID, AddPlayerToTeamV2.CODEC);
		PayloadTypeRegistry.playC2S().register(AddTeamV2.ID, AddTeamV2.CODEC);
		PayloadTypeRegistry.playC2S().register(ChangeTeamColorV2.ID, ChangeTeamColorV2.CODEC);
		PayloadTypeRegistry.playC2S().register(RemovePlayerFromTeamV2.ID, RemovePlayerFromTeamV2.CODEC);
		PayloadTypeRegistry.playC2S().register(RemoveTeamV2.ID, RemoveTeamV2.CODEC);
		PayloadTypeRegistry.playC2S().register(StartGameRequestPacket.ID, StartGameRequestPacket.CODEC);
		PayloadTypeRegistry.playC2S().register(AskCompassPacket.ID, AskCompassPacket.CODEC);
		PayloadTypeRegistry.playC2S().register(RotateTeamColor.ID, RotateTeamColor.CODEC);
		PayloadTypeRegistry.playC2S().register(TeammateRespawnRequestPacket.ID, TeammateRespawnRequestPacket.CODEC);
		PayloadTypeRegistry.playC2S().register(EndGamePacket.ID, EndGamePacket.CODEC);



		LockoutLogger.log("Hello Fabric world!");
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("blackout").executes(context -> {
				context.getSource().sendFeedback(() -> Text.literal("Called /blackout with no arguments."), false);
//				GameState.newBlackout();
				return 1;
			}));
		});


		// todo check if this is still needed
//		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
//			// sends every team update
//			UnitedTeamRegistry.subscribe(u -> GameState.players().forEach(UnitedTeamRegistry::sendState));
//		}



		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
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
		});


		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			GameState.playerServerJoin(handler.getPlayer());
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			GameState.playerServerLeave(handler.getPlayer());
		});

		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			if (alive) return; // dimension change, not death
			if (!CustomWorldManager.worldsActive) return;
			if (CustomWorldManager.isCustomDimension(newPlayer.getServerWorld().getRegistryKey())) return;
			if (!GameState.teamRegistry.playerInTeam(newPlayer.getUuidAsString())) return;
			CustomWorldManager.respawnInCustomOverworld(newPlayer, newPlayer.getServer());
		});

		ServerPlayNetworking.registerGlobalReceiver(GetBoard.ID, (payload, context) -> {
			ServerPlayNetworking.send(context.player(), GameState.info.board());
		});

		ServerPlayNetworking.registerGlobalReceiver(GetGameInfo.ID, (payload, context) -> {
			ServerPlayNetworking.send(context.player(), GameState.info);
		});

		ServerPlayNetworking.registerGlobalReceiver(GetTeamData.ID, (payload, context) -> {
			ServerPlayNetworking.send(context.player(), GameState.teamRegistry);
		});

		ServerPlayNetworking.registerGlobalReceiver(RotateTeamColor.ID, (payload, context) -> {
			GameState.teamRegistry.rotateTeamColor(payload.teamuuid());
		});

		ServerPlayNetworking.registerGlobalReceiver(AddPlayerToTeamV2.ID, (payload, context) -> {
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
					net.minecraft.server.network.ServerPlayerEntity p = context.server().getPlayerManager().getPlayer(java.util.UUID.fromString(payload.puuid()));
					if (p != null) {
						if (!oldTeamPlaying && newTeamPlaying) {
							CustomWorldManager.teleportPlayerIn(p, context.server());
						} else if (oldTeamPlaying && !newTeamPlaying) {
							CustomWorldManager.returnPlayer(p, context.server());
						}
					}
				}
            } catch (Exception e) {
                LockoutLogger.log("Error adding player to team: " + e.getMessage());
            }
        });

		ServerPlayNetworking.registerGlobalReceiver(AddTeamV2.ID, (payload, context) -> {
			GameState.teamRegistry.createNewTeam(payload.name());
		});

		ServerPlayNetworking.registerGlobalReceiver(ChangeTeamColorV2.ID, (payload, context) -> {
			try {
				GameState.teamRegistry.changeTeamColor(payload.teamuuid(), payload.color());
			} catch (Exception e) {
				LockoutLogger.log("Error changing team color: " + e.getMessage());
			}
		});

		ServerPlayNetworking.registerGlobalReceiver(StartGameRequestPacket.ID, (payload, context) -> {
			GameState.StartGame(payload);
		});

		ServerPlayNetworking.registerGlobalReceiver(RemoveTeamV2.ID, (payload, context) -> {
			GameState.teamRegistry.removeTeam(payload.uuid());
		});

		ServerPlayNetworking.registerGlobalReceiver(RemovePlayerFromTeamV2.ID, (payload, context) -> {
			try {
				if (GameState.inGame() && CustomWorldManager.worldsActive) {
					try {
						String oldTeamUUID = GameState.teamRegistry.getTeamDataByPlayerUUID(payload.puuid()).teamUUID;
						if (GameState.info.teamUUIDs().contains(oldTeamUUID)) {
							net.minecraft.server.network.ServerPlayerEntity p = context.server().getPlayerManager().getPlayer(java.util.UUID.fromString(payload.puuid()));
							if (p != null) CustomWorldManager.returnPlayer(p, context.server());
						}
					} catch (Exception ignored) {}
				}
				GameState.teamRegistry.removePlayerFromTeam(payload.puuid());
			} catch (Exception e) {
				LockoutLogger.log("Error removing player from team: " + e.getMessage());
			}
		});

		ServerPlayNetworking.registerGlobalReceiver(AskCompassPacket.ID, (payload, client) -> {
			ServerPlayerEntity player = client.player();
			if (player.getInventory().contains(LockoutModItems.PLAYER_TRACKING_COMPASS.getDefaultStack())) {

			}
			else {
				player.giveItemStack(LockoutModItems.PLAYER_TRACKING_COMPASS.getDefaultStack());
			}
		});

		ServerPlayNetworking.registerGlobalReceiver(TeammateRespawnRequestPacket.ID, (payload, context) -> {
			GameState.registerPlayerRespawnTarget(context.player().getUuidAsString(), payload.playerUUID());
//			context.server().getPlayerManager().respawnPlayer(context.player(), false, Entity.RemovalReason.CHANGED_DIMENSION);
		});

		ServerPlayNetworking.registerGlobalReceiver(EndGamePacket.ID, (payload, context) -> {
			if (!GameState.inGame()) return;
			GameState.destroyGame();
			CustomWorldManager.removeWorlds(context.server());
			GameStartPacket emptyPacket = GameStartPacket.empty();
			for (net.minecraft.server.network.ServerPlayerEntity p : context.server().getPlayerManager().getPlayerList()) {
				p.getInventory().remove(stack -> stack.isOf(LockoutModItems.PLAYER_TRACKING_COMPASS), Integer.MAX_VALUE, p.playerScreenHandler.getCraftingInput());
				ServerPlayNetworking.send(p, emptyPacket);
			}
		});





//		ServerPlayNetworking.registerGlobalReceiver(GivePlayerCompass.ID, (payload, client) -> {
//			ServerPlayerEntity player = client.player();
//			if (player.getInventory().contains(LockoutModItems.PLAYER_TRACKING_COMPASS.getDefaultStack())) {
//
//			}
//			else {
//				player.giveItemStack(LockoutModItems.PLAYER_TRACKING_COMPASS.getDefaultStack());
//			}
//		});

//		ServerPlayNetworking.registerGlobalReceiver(LockoutAddTeamPacket.ID, (payload, client) -> {
//			GameState.addTeam(payload.team());
//			LockoutLogger.log("Added team " + payload.team());
//		});
//
//		ServerPlayNetworking.registerGlobalReceiver(CreateBlackoutRequestPacket.ID, (payload, client) -> {
////			GameState.newBlackout();
//			LockoutLogger.log("Started blackout game");
//		});
//
//		ServerPlayNetworking.registerGlobalReceiver(LockoutJoinTeamPacket.ID, (payload, client) -> {
//			GameState.playerJoinTeam(client.player(), payload.teamid());
//			LockoutLogger.log("Player " + client.player().getUuidAsString() + " joined team " + payload.teamid());
//		});
//
//		ServerPlayNetworking.registerGlobalReceiver(LockoutAddPlayerToTeamPacket.ID, (payload, client) -> {
//			if (payload.uuid() == null){return;}
//			GameState.playerJoinTeam(payload.uuid(),  payload.teamid());
//			LockoutLogger.log("player joined team from other player");
//		});
//
//		ServerPlayNetworking.registerGlobalReceiver(CreateLockoutPacket.ID, (payload, client) -> {
//			GameState.newLockout(payload);
//			LockoutLogger.log("Started lockout game");
//		});
//
//		ServerPlayNetworking.registerGlobalReceiver(LockoutRemoveTeamPacket.ID, (payload, client) -> {
//			GameState.removeTeam(payload.team());
//			LockoutLogger.log("Removed team " + payload.team());
//		});
//
//		ServerPlayNetworking.registerGlobalReceiver(ChangeTeamIdPacket.ID, (payload, client) -> {
//			GameState.changeTeamId(payload.oldIndex(), payload.newIndex());
//		});

		ServerLifecycleEvents.SERVER_STARTED.register(GameStatePersistence::load);
		ServerLifecycleEvents.SERVER_STOPPING.register(GameStatePersistence::save);

		LockoutModItems.initialize();

		TickListener.subscribe(GameState::sendPlayerPositions);

		LootTableEvents.REPLACE.register((key, original, source, registries) -> {
			if (LootTables.PIGLIN_BARTERING_GAMEPLAY.equals(key)) {
                return LootTable.builder().pool(
								LootPool.builder()
										.rolls(ConstantLootNumberProvider.create(1.0F))
										.with(ItemEntry.builder(Items.BOOK).weight(5).apply(new EnchantRandomlyLootFunction.Builder().option(registries.createRegistryLookup().getOrThrow(Enchantments.SOUL_SPEED.getRegistryRef()).getOrThrow(Enchantments.SOUL_SPEED))))
										.with(ItemEntry.builder(Items.IRON_BOOTS).weight(8).apply(new EnchantRandomlyLootFunction.Builder().option(registries.createRegistryLookup().getOrThrow(Enchantments.SOUL_SPEED.getRegistryRef()).getOrThrow(Enchantments.SOUL_SPEED))))
										.with(ItemEntry.builder(Items.POTION).weight(10).apply(SetPotionLootFunction.builder(Potions.FIRE_RESISTANCE)))
										.with(ItemEntry.builder(Items.SPLASH_POTION).weight(10).apply(SetPotionLootFunction.builder(Potions.FIRE_RESISTANCE)))
										.with(ItemEntry.builder(Items.IRON_NUGGET).weight(10).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(9.0F, 36.0F))))
										.with(ItemEntry.builder(Items.ENDER_PEARL).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 8.0F))))
										.with(ItemEntry.builder(Items.STRING).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0F, 24.0F))))
										.with(ItemEntry.builder(Items.QUARTZ).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0F, 16.0F))))
										.with(ItemEntry.builder(Items.OBSIDIAN).weight(40))
										.with(ItemEntry.builder(Items.CRYING_OBSIDIAN).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 3.0F))))
										.with(ItemEntry.builder(Items.LEATHER).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 10.0F))))
										.with(ItemEntry.builder(Items.NETHER_BRICK).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 16.0F))))
										.with(ItemEntry.builder(Items.GLOWSTONE_DUST).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(5.0F, 12.0F))))
										.with(ItemEntry.builder(Items.GRAVEL).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0F, 16.0F))))
										.with(ItemEntry.builder(Items.MAGMA_CREAM).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 6.0F))))
										.with(ItemEntry.builder(Items.FIRE_CHARGE).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 5.0F))))
										.with(ItemEntry.builder(Items.SOUL_SAND).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 16.0F))))
						).build();
			};
            return original;
        });



	}

	public void onServerStarted(MinecraftServer server) {
		GameState.server = server;
//		BlockDropChangeBuilder.shuffleBlockDrops();
	}





}