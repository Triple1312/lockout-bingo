package net.abrikoos.blockout;

import net.abrikoos.blockout.network.NetworkBridge;
import net.abrikoos.blockout.networkv2.compass.AskCompassPacket;
import net.abrikoos.blockout.networkv2.compass.PlayersPositionPacket;
import net.abrikoos.blockout.networkv2.game.*;
import net.abrikoos.blockout.networkv2.get.GetBoard;
import net.abrikoos.blockout.networkv2.get.GetGameInfo;
import net.abrikoos.blockout.networkv2.get.GetTeamData;
import net.abrikoos.blockout.networkv2.team.*;
import net.abrikoos.blockout.platform.PlatformHelperImpl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class BlockoutFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Wire up the server-to-client sender
        NetworkBridge.setToPlayer((player, payload) -> ServerPlayNetworking.send(player, payload));

        // Register all payload types before receivers
        registerPayloadTypes();

        // Register C2S packet receivers (codec already registered via PayloadTypeRegistry)
        ServerPlayNetworking.registerGlobalReceiver(GetBoard.ID,
            (payload, ctx) -> Blockout.handleGetBoard(ctx.server(), ctx.player()));

        ServerPlayNetworking.registerGlobalReceiver(GetGameInfo.ID,
            (payload, ctx) -> Blockout.handleGetGameInfo(ctx.server(), ctx.player()));

        ServerPlayNetworking.registerGlobalReceiver(GetTeamData.ID,
            (payload, ctx) -> Blockout.handleGetTeamData(ctx.server(), ctx.player()));

        ServerPlayNetworking.registerGlobalReceiver(RotateTeamColor.ID,
            (payload, ctx) -> Blockout.handleRotateTeamColor(ctx.server(), payload));

        ServerPlayNetworking.registerGlobalReceiver(AddPlayerToTeamV2.ID,
            (payload, ctx) -> Blockout.handleAddPlayerToTeam(ctx.server(), ctx.player(), payload));

        ServerPlayNetworking.registerGlobalReceiver(AddTeamV2.ID,
            (payload, ctx) -> Blockout.handleAddTeam(ctx.server(), payload));

        ServerPlayNetworking.registerGlobalReceiver(ChangeTeamColorV2.ID,
            (payload, ctx) -> Blockout.handleChangeTeamColor(ctx.server(), payload));

        ServerPlayNetworking.registerGlobalReceiver(StartGameRequestPacket.ID,
            (payload, ctx) -> Blockout.handleStartGame(ctx.server(), payload));

        ServerPlayNetworking.registerGlobalReceiver(RemoveTeamV2.ID,
            (payload, ctx) -> Blockout.handleRemoveTeam(ctx.server(), payload));

        ServerPlayNetworking.registerGlobalReceiver(RemovePlayerFromTeamV2.ID,
            (payload, ctx) -> Blockout.handleRemovePlayerFromTeam(ctx.server(), ctx.player(), payload));

        ServerPlayNetworking.registerGlobalReceiver(AskCompassPacket.ID,
            (payload, ctx) -> Blockout.handleAskCompass(ctx.server(), ctx.player()));

        ServerPlayNetworking.registerGlobalReceiver(TeammateRespawnRequestPacket.ID,
            (payload, ctx) -> Blockout.handleTeammateRespawnRequest(ctx.server(), ctx.player(), payload));

        ServerPlayNetworking.registerGlobalReceiver(EndGamePacket.ID,
            (payload, ctx) -> Blockout.handleEndGame(ctx.server(), ctx.player()));

        // Register commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            Blockout.registerCommands(dispatcher, registryAccess));

        // Platform-specific loot table modifications
        PlatformHelperImpl.registerLootModifications();

        Blockout.init();
    }

    private void registerPayloadTypes() {
        // C2S
        PayloadTypeRegistry.playC2S().register(GetBoard.ID, GetBoard.CODEC);
        PayloadTypeRegistry.playC2S().register(GetGameInfo.ID, GetGameInfo.CODEC);
        PayloadTypeRegistry.playC2S().register(GetTeamData.ID, GetTeamData.CODEC);
        PayloadTypeRegistry.playC2S().register(RotateTeamColor.ID, RotateTeamColor.CODEC);
        PayloadTypeRegistry.playC2S().register(AddPlayerToTeamV2.ID, AddPlayerToTeamV2.CODEC);
        PayloadTypeRegistry.playC2S().register(AddTeamV2.ID, AddTeamV2.CODEC);
        PayloadTypeRegistry.playC2S().register(ChangeTeamColorV2.ID, ChangeTeamColorV2.CODEC);
        PayloadTypeRegistry.playC2S().register(StartGameRequestPacket.ID, StartGameRequestPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(RemoveTeamV2.ID, RemoveTeamV2.CODEC);
        PayloadTypeRegistry.playC2S().register(RemovePlayerFromTeamV2.ID, RemovePlayerFromTeamV2.CODEC);
        PayloadTypeRegistry.playC2S().register(AskCompassPacket.ID, AskCompassPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(TeammateRespawnRequestPacket.ID, TeammateRespawnRequestPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(EndGamePacket.ID, EndGamePacket.CODEC);

        // S2C
        PayloadTypeRegistry.playS2C().register(TeamRegV2.ID, TeamRegV2.CODEC);
        PayloadTypeRegistry.playS2C().register(GameStartPacket.ID, GameStartPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(GoalBoardUpdatePacket.ID, GoalBoardUpdatePacket.CODEC);
        PayloadTypeRegistry.playS2C().register(StructureLocationsPacket.ID, StructureLocationsPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(PlayersPositionPacket.ID, PlayersPositionPacket.CODEC);
    }
}
