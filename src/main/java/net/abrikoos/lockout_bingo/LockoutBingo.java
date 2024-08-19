package net.abrikoos.lockout_bingo;

import net.abrikoos.lockout_bingo.gamestate.GameState;
import net.abrikoos.lockout_bingo.listeners.EntityKillListener;
import net.abrikoos.lockout_bingo.listeners.PlayerDeathListener;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGamePacket;
import net.abrikoos.lockout_bingo.network.game.CreateBlackoutRequestPacket;
import net.abrikoos.lockout_bingo.network.game.LockoutStartGamePacket;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardPacket;
import net.abrikoos.lockout_bingo.network.team.AllTeamsPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutAddTeamPacket;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class LockoutBingo implements ModInitializer {

	public static final String MOD_ID = "lockout-bingo";

	public static PlayerDeathListener playerDeathListener = new PlayerDeathListener();

	public static EntityKillListener entityKillListener = new EntityKillListener();

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playS2C().register(BlackoutStartGamePacket.ID, BlackoutStartGamePacket.CODEC);
		PayloadTypeRegistry.playS2C().register(LockoutUpdateBoardPacket.ID, LockoutUpdateBoardPacket.CODEC);
		PayloadTypeRegistry.playS2C().register(AllTeamsPacket.ID, AllTeamsPacket.PACKET_CODEC);
		PayloadTypeRegistry.playS2C().register(LockoutStartGamePacket.ID, LockoutStartGamePacket.CODEC);


		PayloadTypeRegistry.playC2S().register(LockoutAddTeamPacket.ID, LockoutAddTeamPacket.PACKET_CODEC);
		PayloadTypeRegistry.playC2S().register(CreateBlackoutRequestPacket.ID, CreateBlackoutRequestPacket.CODEC);

		LockoutLogger.log("Hello Fabric world!");
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("blackout").executes(context -> {
				context.getSource().sendFeedback(() -> Text.literal("Called /blackout with no arguments."), false);
				GameState.newBlackout();
				return 1;
			}));
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			GameState.playerServerJoin(handler.getPlayer());
		});
		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			GameState.playerServerLeave(handler.getPlayer());
		});



		ServerPlayNetworking.registerGlobalReceiver(LockoutAddTeamPacket.ID, (payload, client) -> {
			GameState.addTeam(payload.team());
			LockoutLogger.log("Added team " + payload.team());
		});

		ServerPlayNetworking.registerGlobalReceiver(CreateBlackoutRequestPacket.ID, (payload, client) -> {
			GameState.newBlackout();
			LockoutLogger.log("Started blackout game");
		});


//		LootTableEvents.MODIFY.register((resourceManager, tableBuilder, source, id) -> {
//			if (source.isBuiltin() && LootTables.PIGLIN_BARTERING_GAMEPLAY.equals(id)) {
//				LootPool.builder()
//						.with(ItemEntry.builder(Items.ENDER_PEARL ).weight(10))
//						.with(ItemEntry.builder(Items.POTIO));
//
//				LootPool.Builder poolbuilder = LootPool.builder()
//								.with(ItemEntry.builder(Items.ENCHANTED_BOOK).apply(EnchantRandomlyLootFunction.builder()
//										.add(Enchantments.SOUL_SPEED, 1)  // Adds Soul Speed 1
//										.add(Enchantments.SOUL_SPEED, 2))) // Adds Soul Speed 2
//								.with(ItemEntry.builder(Items.SPLASH_POTION).weight(10).apply(SetPotionLootFunction.builder(Potions.FIRE_RESISTANCE)))
//
//
//
//
//
//				tableBuilder.modifyPools( pool -> {
//					pool.with(ItemEntry.builder(Items.ENDER_PEARL).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.f, 8.f)))).build();
//					pool.with(ItemEntry.builder(Items.STRING).weight())
//				});
//			}
//		});


	}



}