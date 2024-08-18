package net.abrikoos.lockout_bingo.gamestate;

import net.abrikoos.lockout_bingo.builder.LockoutRandBuilder;
import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.listeners.*;
import net.abrikoos.lockout_bingo.modes.team.LockoutTeam;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGamePacket;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardPacket;
import net.abrikoos.lockout_bingo.network.team.AllTeamsPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    public static List<LockoutTeam> teams = new ArrayList<>();

    public static List<ServerPlayerEntity> players = new ArrayList<>();

    public static List<LockoutGoal> goals = new ArrayList<>();

    public static BlackoutStartGameInfo info;


    public static void playerServerJoin(ServerPlayerEntity player) {
        players.add(player);
    }

    public static void playerServerLeave(ServerPlayerEntity player) {
        players.remove(player);
    }

    public static void createNewGame(GameType type, ClientPlayerEntity client, GameSettings settings) {

    }

    public static void addTeam(String name) {
        List<String> teamnames = new ArrayList<>();
        for (LockoutTeam team : teams) {
            teamnames.add(team.name);
            if (team.name.equals(name)) {
                return;
            }
        }
        LockoutTeam team = new LockoutTeam(name);
        teams.add(team);
        teamnames.add(name);
        AllTeamsPacket packet = new AllTeamsPacket(teamnames);
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, packet);
        }
    }

    public static void newBlackout() {
        if (!goals.isEmpty()) {
            destroyGame();
        }
        LockoutRandBuilder builder = new LockoutRandBuilder();
        info = builder.generateBoard();
        goals = builder.buildBoard(info);
        for (LockoutGoal goal : goals) {
            goal.subscribe(GameState::onGoalComplete);
        }

        for (ServerPlayerEntity player : players) {
            player.getInventory().clear();

            PlayerAdvancementTracker tracker = player.getAdvancementTracker();
            MinecraftServer server = player.getServer();
            assert server != null;
            ServerAdvancementLoader loader = server.getAdvancementLoader();
            for (AdvancementEntry advancement : loader.getAdvancements()) {
                advancement.value().criteria().forEach((criterion, conditions) -> {
                    tracker.revokeCriterion(advancement, criterion);
                });
            }

            StatHandler sh = player.getStatHandler();
            for (Stat<?> stat : Stats.CUSTOM) {
                sh.setStat(player, stat, 0);
            } //todo needs to remove more stuff
        }

        BlackoutStartGamePacket packet = new BlackoutStartGamePacket(info);
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, packet);
        }
    }

    public static void destroyGame() {
        for (LockoutGoal goal : goals) {
            goal.destory();
        }
        EntityKillListener.clear();
        TickListener.clear();
        AdvancementListener.clear();
        ScreenSlotClickListener.clear();
        PlayerEffectListener.clear();
        PlayerDeathListener.clear();
    }

    public static String onGoalComplete(LockoutGoalEvent event) {
        int[] board = new int[25];
        if (goals.size() < 25) {
            return "";
        }
        for (int i = 0; i < 25; i++) {
            board[i] = goals.get(i).completed == null ? 0 : goals.get(i).recipiant() == "ally" ? 1 : 2;
        }
        LockoutUpdateBoardInfo update = new LockoutUpdateBoardInfo(board);
        LockoutUpdateBoardPacket packet = new LockoutUpdateBoardPacket(update);
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, packet);
        }
        return "";
    }

    protected void resetPlayerStats(ServerPlayerEntity player) {
        player.getInventory().clear();
        player.getAdvancementTracker().clearCriteria();
        StatHandler sh = player.getStatHandler();
        for (Stat<?> stat : Stats.CUSTOM) {
            sh.setStat(player, stat, 0);
        }

        // Reset block stats
        for (Block block : Registries.BLOCK) {
            Stat<Block> stat = Stats.MINED.getOrCreateStat(block);
            sh.setStat(player ,stat, 0);
        }

        // Reset item stats
        for (Item item : Registries.ITEM) {
            Stat<Item> usedStat = Stats.USED.getOrCreateStat(item);
            sh.setStat(player, usedStat, 0);

            Stat<Item> brokenStat = Stats.BROKEN.getOrCreateStat(item);
            sh.setStat(player, brokenStat, 0);

            Stat<Item> pickedUpStat = Stats.PICKED_UP.getOrCreateStat(item);
            sh.setStat(player, pickedUpStat, 0);

            Stat<Item> droppedStat = Stats.DROPPED.getOrCreateStat(item);
            sh.setStat(player, droppedStat, 0);
        }

        // Reset entity stats (kills, killed by)
        for (EntityType<?> entityType : Registries.ENTITY_TYPE) {
            Stat<EntityType<?>> killedStat = Stats.KILLED.getOrCreateStat(entityType);
            sh.setStat(player, killedStat, 0);

            Stat<EntityType<?>> killedByStat = Stats.KILLED_BY.getOrCreateStat(entityType);
            sh.setStat(player, killedByStat, 0);
        }

    }


}


























