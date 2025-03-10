package net.abrikoos.lockout_bingo.server.gamestate;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.item.LockoutModItems;
import net.abrikoos.lockout_bingo.networkv2.game.GameStartPacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalBoardUpdatePacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalInfoPacket;
import net.abrikoos.lockout_bingo.networkv2.game.StartGameRequestPacket;
import net.abrikoos.lockout_bingo.networkv2.team.Colors;
import net.abrikoos.lockout_bingo.networkv2.team.PlayerData;
import net.abrikoos.lockout_bingo.networkv2.team.ServerTeamRegV2;
import net.abrikoos.lockout_bingo.networkv2.team.TeamData;
import net.abrikoos.lockout_bingo.server.builder.ReworkedLockoutBuilder;
import net.abrikoos.lockout_bingo.server.goals.GoalFactory;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.server.listeners.*;
import net.abrikoos.lockout_bingo.client.modes.random_block_finder.RandomBlockFinder;
import net.abrikoos.lockout_bingo.networkv2.compass.CompassPlayerPosition;
import net.abrikoos.lockout_bingo.networkv2.compass.PlayersPositionPacket;
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

    public static List<LockoutGoal> goals = new ArrayList<>();

    public static GameStartPacket info = GameStartPacket.empty();

    public static MinecraftServer server;

    public static List<Integer> teamIndexes = new ArrayList<>();

    public static RandomBlockFinder rbf;

    public static ServerTeamRegV2 teamRegistry = new ServerTeamRegV2();


    public static GoalBoardUpdatePacket board() {
        return info.board();
    }

    public static void playerServerJoin(ServerPlayerEntity player) {
        try {
            teamRegistry.getPlayerDataByUUID(player.getUuid().toString());
            teamRegistry.playerHasConnected(player.getUuid().toString());
        }
        catch (Exception e) {
            teamRegistry.addPlayerData(new PlayerData(player.getUuid().toString(), player.getName().getString(), true));
        }
        if (inGame()) {
            ServerPlayNetworking.send(player, board());
        }
    }

    public static void playerServerLeave(ServerPlayerEntity player) {
        try {
            teamRegistry.playerHasDisconnected(player.getUuid().toString());
        }
        catch (Exception e) {
            LockoutLogger.log("Error removing player from team registry");
        }
    }

    public static void playerJoinTeam(String puuid, String team) {
        try {
            teamRegistry.addPlayerToTeam(puuid, team);
        }
        catch (Exception e) {
            LockoutLogger.log("Error adding player to team");
        }
    }

    public static void playerJoinTeam(ServerPlayerEntity player, String team) {
        try {
            teamRegistry.addPlayerToTeam(player.getUuid().toString(), team);
        }
        catch (Exception e) {
            LockoutLogger.log("Error adding player to team");
        }
    }

    public static List<ServerPlayerEntity> players() {
        return server.getPlayerManager().getPlayerList();
    }

    public static void addTeam(String name) {
        try {
            teamRegistry.createNewTeam(name);
        }
        catch (Exception e) {

        }
    }

    public static void removeTeam(String name) {
        try {
            teamRegistry.removeTeam(name);
        }
        catch (Exception e) {

        }
    }

    public static TeamData getTeam(String teamUUID) throws Exception {
        return teamRegistry.getTeamDataByUUID(teamUUID);
    }

    public static void changeTeamId(String teamUUID, int colorId) throws Exception {
        teamRegistry.changeTeamColor(teamUUID, colorId);
    }

    public static void goalComplete(String playerName, int goal) {
        if (goal >= goals.size()) {
            LockoutLogger.log("Goal index out of bounds of goals list");
            return;
        }
        goals.get(goal).complete(playerName);
    }

    public static void newLockout(StartGameRequestPacket packet) {
        if (!goals.isEmpty()) {
            destroyGame();
        }

        ReworkedLockoutBuilder builder = new ReworkedLockoutBuilder(packet);
        long startTime = System.currentTimeMillis();
        int freezetime = 60000;

        info = new GameStartPacket("lockout", packet.teamUUIDs().get(0), packet.teamUUIDs().get(1), builder.packet, startTime, freezetime);

        for (GoalInfoPacket goal : builder.packet.goals()) {
            LockoutGoal lg = GoalFactory.buildGoal(goal.goalID(), goal.goalIndex());
            goals.add(lg);
        }

        for (LockoutGoal goal : goals) {
            goal.subscribe(GameState::onGoalComplete);
        }


        // reset all player stats
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


        // send new game packet to all players
        for (ServerPlayerEntity player : players()) {
            ServerPlayNetworking.send(player, info);
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
        PlayerDamageListener.clear();
        ComposterUseListener.clear();
        UseCauldronListener.clear();
        TameListener.clear();
        PlayerInventoryListener.clear();
        ServerTickListener.clear();
    }

    public static String onGoalComplete(LockoutGoalEvent event) {

        // new data for board
        List<GoalInfoPacket> new_goals_data = getBoard().goals();
        if (event.goalId >= new_goals_data.size()) {
            throw new IllegalStateException("Goal index out of bounds of goals list");
        }
        List<Integer> new_scores = new ArrayList<>();
        int justcompletedgoal = event.goalId;

        // update goal data
        GoalInfoPacket goalinfopacket = new_goals_data.get(event.goalId);
        TeamData goal_recipiant_team = null;
        try {
            String goalTeam = teamRegistry.getTeamDataByPlayerUUID(event.puuid).teamUUID;
            if (Objects.equals(event.recipiant, "ally")) {
                goal_recipiant_team = teamRegistry.getTeamDataByPlayerUUID(event.puuid);
            }
            else {
                for (TeamData team : teamRegistry.teams) { // todo not compatible with more than 2 teams
                    if (!team.teamUUID.equals(goalTeam)) {
                        goal_recipiant_team = team;
                        break;
                    }
                }
            }

        }
        catch (Exception e) {
            LockoutLogger.log("Error getting team for player " + event.puuid);
        }
        new_goals_data.set(event.goalId, new GoalInfoPacket( goalinfopacket.goalName(), goalinfopacket.goalID(), event.goalId, event.puuid, goal_recipiant_team.teamUUID, goal_recipiant_team.teamColor));

        // calculate scores
        for (String teamUUID: GameState.info.teamUUIDs()) {
            int goal_completed_count = 0;
            for (GoalInfoPacket goal : new_goals_data) {
                if (Objects.equals(goal.completedTeamUUID(), teamUUID)) {
                    goal_completed_count++;
                }
            }
            new_scores.add(goal_completed_count);
        }

        GameState.info.updateBoard(new GoalBoardUpdatePacket(new_goals_data, justcompletedgoal, new_scores));

        // send new board to all players
        String playerName = getPlayerByUUID(event.puuid).getName().getString();
        for (ServerPlayerEntity player : players()) {
            try {
                ServerPlayNetworking.send(player, board());
                player.sendMessage(Text.literal(playerName).withColor(Colors.get(goal_recipiant_team.teamColor)).append(Text.of(" has completed goal: " + goalinfopacket.goalName())));
            } catch (Exception e) {
                LockoutLogger.log("Error sending packet to player " + player.getName().getString());
            }
        }
        return event.recipiant;
    }

    public static GoalBoardUpdatePacket getBoard() {
        return board();
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


























