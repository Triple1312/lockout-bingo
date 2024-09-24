package net.abrikoos.lockout_bingo;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.abrikoos.lockout_bingo.item.LockoutModItems;
import net.abrikoos.lockout_bingo.server.listeners.EntityKillListener;
import net.abrikoos.lockout_bingo.server.listeners.PlayerDeathListener;
import net.abrikoos.lockout_bingo.server.listeners.TickListener;
import net.abrikoos.lockout_bingo.network.compass.PlayersPositionPacket;
import net.abrikoos.lockout_bingo.network.game.*;
import net.abrikoos.lockout_bingo.network.team.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.Enchantments;
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
		PayloadTypeRegistry.playS2C().register(PlayersPositionPacket.ID, PlayersPositionPacket.CODEC);
		PayloadTypeRegistry.playC2S().register(ChangeTeamIdPacket.ID, ChangeTeamIdPacket.PACKET_CODEC);


		PayloadTypeRegistry.playC2S().register(LockoutAddTeamPacket.ID, LockoutAddTeamPacket.PACKET_CODEC);
		PayloadTypeRegistry.playC2S().register(CreateBlackoutRequestPacket.ID, CreateBlackoutRequestPacket.CODEC);
		PayloadTypeRegistry.playC2S().register(LockoutJoinTeamPacket.ID, LockoutJoinTeamPacket.PACKET_CODEC);
		PayloadTypeRegistry.playC2S().register(CreateLockoutPacket.ID, CreateLockoutPacket.CODEC);
		PayloadTypeRegistry.playC2S().register(LockoutRemoveTeamPacket.ID, LockoutRemoveTeamPacket.PACKET_CODEC);

		LockoutLogger.log("Hello Fabric world!");
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("blackout").executes(context -> {
				context.getSource().sendFeedback(() -> Text.literal("Called /blackout with no arguments."), false);
//				GameState.newBlackout();
				return 1;
			}));
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("goalComplete")
					.then(CommandManager.argument("team", IntegerArgumentType.integer())
							.then(CommandManager.argument("goal", IntegerArgumentType.integer())
									.executes(context -> {
										GameState.goalComplete(context.getArgument("team", Integer.class), context.getArgument("goal", Integer.class));
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

		ServerPlayNetworking.registerGlobalReceiver(LockoutAddTeamPacket.ID, (payload, client) -> {
			GameState.addTeam(payload.team());
			LockoutLogger.log("Added team " + payload.team());
		});

		ServerPlayNetworking.registerGlobalReceiver(CreateBlackoutRequestPacket.ID, (payload, client) -> {
//			GameState.newBlackout();
			LockoutLogger.log("Started blackout game");
		});

		ServerPlayNetworking.registerGlobalReceiver(LockoutJoinTeamPacket.ID, (payload, client) -> {
			GameState.playerJoinTeam(client.player(), payload.teamid());
			LockoutLogger.log("Player " + client.player().getUuidAsString() + " joined team " + payload.teamid());
		});

		ServerPlayNetworking.registerGlobalReceiver(CreateLockoutPacket.ID, (payload, client) -> {
			GameState.newLockout(payload);
			LockoutLogger.log("Started lockout game");
		});

		ServerPlayNetworking.registerGlobalReceiver(LockoutRemoveTeamPacket.ID, (payload, client) -> {
			GameState.removeTeam(payload.team());
			LockoutLogger.log("Removed team " + payload.team());
		});

		ServerPlayNetworking.registerGlobalReceiver(ChangeTeamIdPacket.ID, (payload, client) -> {
			GameState.changeTeamId(payload.oldIndex(), payload.newIndex());
		});

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





}