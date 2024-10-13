package net.abrikoos.lockout_bingo.server.gamestate;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.modes.team.LockoutTeamDataClass;
import net.abrikoos.lockout_bingo.item.LockoutModItems;
import net.abrikoos.lockout_bingo.server.builder.LockoutFinalBuilder;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.server.listeners.*;
import net.abrikoos.lockout_bingo.client.modes.random_block_finder.RandomBlockFinder;
import net.abrikoos.lockout_bingo.network.compass.CompassPlayerPosition;
import net.abrikoos.lockout_bingo.network.compass.PlayersPositionPacket;
import net.abrikoos.lockout_bingo.network.game.*;
import net.abrikoos.lockout_bingo.team.Colors;
import net.abrikoos.lockout_bingo.team.UnitedTeamRegistry;
import net.abrikoos.lockout_bingo.util.BlockoutList;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.LocalTime;
import java.util.*;


public class GameState {

//    public static List<LockoutTeam> teams = new ArrayList<>();
//
//    public static List<ServerPlayerEntity> players = new ArrayList<>();

    public static List<LockoutGoal> goals = new ArrayList<>();

    public static LockoutStartGameInfo info;

    public static MinecraftServer server;

    public static List<Integer> teamIndexes = new ArrayList<>();

    public static RandomBlockFinder rbf;


    public static void playerServerJoin(ServerPlayerEntity player) {
        UnitedTeamRegistry.addPlayer(player);
//        UnitedTeamRegistry.sendState(player);
        UnitedTeamRegistry.sendState(player);
        if (inGame()) {
            ServerPlayNetworking.send(player, new LockoutStartGamePacket(new LockoutStartGameInfo(info.teams, info.goals, false, System.currentTimeMillis())));
            ServerPlayNetworking.send(player, new LockoutUpdateBoardPacket(getBoard()));
        }
    }

    public static void playerServerLeave(ServerPlayerEntity player) {
        UnitedTeamRegistry.disconnectPlayer(player.getUuid());
    }

    public static void playerJoinTeam(String puuid, int team) {
        UnitedTeamRegistry.playerJoinTeam(UUID.fromString(puuid), team);
//        for (ServerPlayerEntity plr : server.getPlayerManager().getPlayerList()) {
//            UnitedTeamRegistry.sendState(plr);
//        }
    }

    public static void playerJoinTeam(ServerPlayerEntity player, int team) {
        UnitedTeamRegistry.playerJoinTeam(player.getUuid(), team);
//        for (ServerPlayerEntity plr : server.getPlayerManager().getPlayerList()) {
//            UnitedTeamRegistry.sendState(plr);
//        }
    }

    public static List<ServerPlayerEntity> players() {
        return server.getPlayerManager().getPlayerList();
    }

    public static void addTeam(String name) {
        try {
            UnitedTeamRegistry.addTeam(name);
        }
        catch (Exception e) {

        }
//        List<String> teamnames = new ArrayList<>();
//        for (LockoutTeam team : teams) {
//            teamnames.add(team.name);
//            if (team.name.equals(name)) {
//                return;
//            }
//        }
//
//
//        int id = 1;
//        for (int i = 1; i < 11; i++) {
//            boolean found = false;
//            for (LockoutTeam team : teams) {
//                if (team.teamId == i) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                id = i;
//                break;
//            }
//        }
//
//
//        LockoutTeam team = new LockoutTeam(name, id);
//        teams.add(team);
//        AllTeamsPacket packet = new AllTeamsPacket(teams);
//        for (ServerPlayerEntity player : players) {
//            ServerPlayNetworking.send(player, packet);
//        }
    }

    public static void removeTeam(String name) {
        UnitedTeamRegistry.removeTeam(name);
    }

//    public static void sendAllTeams() {
//        AllTeamsPacket packet = new AllTeamsPacket(teams);
//        for (ServerPlayerEntity player : players) {
//            ServerPlayNetworking.send(player, packet);
//        }
//    }

    public static UnitedTeamRegistry.Team getTeam(int teamindex) {
        return UnitedTeamRegistry.getTeam(teamindex);
    }

    public static void changeTeamId(int teamindex, int newId) {
        UnitedTeamRegistry.changeTeamIndex(teamindex, newId);
//        LockoutTeam team = getTeam(teamindex);
//        LockoutTeam checkTeam = getTeam(newId);
//        if (checkTeam!= null) {
//            return;
//        }
//        if (team == null) {
//            return;
//        }
//        team.teamId = newId;
//        sendAllTeams();
    }

    public static void goalComplete(String playerName, int goal) {
        if (goal >= goals.size()) {
            LockoutLogger.log("Goal index out of bounds of goals list");
            return;
        }
        goals.get(goal).complete(playerName);
    }

    public static void newLockout(CreateLockoutPacket packet) {
        if (!goals.isEmpty()) {
            destroyGame();
        }

        LockoutFinalBuilder builder = new LockoutFinalBuilder(packet);
        LockoutStartGameInfo li = builder.generateLockoutBoard();
        goals = builder.buildBoard(Arrays.asList(li.goals));
        for (int teamindex : packet.teams()) {
            assert getTeam(teamindex) != null;
            li.teams.add(new LockoutTeamDataClass(getTeam(teamindex).teamName(), UnitedTeamRegistry.getTeamPlayers(teamindex).fold(new BlockoutList<String>(), (s, t) -> {s.add(t.getName()); return s;})));
        }
        info = li;


        for (LockoutGoal goal : goals) {
            goal.subscribe(GameState::onGoalComplete);
        }

        for (ServerPlayerEntity player : players()) {
            player.getInventory().clear();
//            player.giveItemStack(LockoutModItems.PLAYER_TRACKING_COMPASS.getDefaultStack());

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
            player.heal(player.getMaxHealth());
            player.giveItemStack(LockoutModItems.PLAYER_TRACKING_COMPASS.getDefaultStack());
        }

        LockoutStartGamePacket pt = new LockoutStartGamePacket(li);
        for (ServerPlayerEntity player : players()) {
            ServerPlayNetworking.send(player, pt);
        }
    }

    public static void newRBF() {

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
//        String[] board = new String[25];
//        if (goals.size() < 25) {
//            return "";
//        }
//        for (int i = 0; i < 25; i++) {
//            board[i] = goals.get(i).completed == null ?
//                    "00000000-0000-0000-0000-000000000000"
//                    : goals.get(i).recipiant() == "ally" ?
//                        goals.get(i).completed.getUuidAsString()
//                        : "11111111-1111-1111-1111-111111111111";
//        }
        LockoutUpdateBoardInfo update = getBoard();
        LockoutUpdateBoardPacket packet = new LockoutUpdateBoardPacket(update);
        String playerName = getPlayerByUUID(event.puuid).getName().getString();
        for (ServerPlayerEntity player : players()) {
            ServerPlayNetworking.send(player, packet);
            player.sendMessage(
                    Text.empty().append("Goal \"")
                        .append(Text.literal(GameState.goals.get(event.goalId).name()))
                        .append("\" completed by ")
                        .append(Text.literal(playerName).withColor(Colors.getPlayerColor(event.puuid))),
                    false);
        }
        return "";
    }

    public static LockoutUpdateBoardInfo getBoard() {
        String[] board = new String[25];
        if (goals.size() < 25) {
            return new LockoutUpdateBoardInfo(board);
        }
        for (int i = 0; i < 25; i++) {
            board[i] = goals.get(i).completed == null ?
                    "00000000-0000-0000-0000-000000000000"
                    : goals.get(i).recipiant() == "ally" ?
                        goals.get(i).completed.getUuidAsString()
                        : "11111111-1111-1111-1111-111111111111";
        }
        return new LockoutUpdateBoardInfo(board);
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

    public static boolean inGame() {
        return !goals.isEmpty();
    }

    public static void sendPlayerPositions(MinecraftServer server) {
        if (LocalTime.now().getNano() % 50 < 10 && GameState.inGame()) {
            List<CompassPlayerPosition> pos = new ArrayList<>();
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                pos.add(CompassPlayerPosition.fromServerPlayer(player));
            }

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ServerPlayNetworking.send(player, new PlayersPositionPacket(pos));
            }
        }
    }

    public static @Nullable ServerPlayerEntity getPlayerByUUID(String uuid) {
        for (ServerPlayerEntity p : players()) {
            if (Objects.equals(p.getUuidAsString(), uuid)) {
                return p;
            }
        }
        return null;
    }


}


























