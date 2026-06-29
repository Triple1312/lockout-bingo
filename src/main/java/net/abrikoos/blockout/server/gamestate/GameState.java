package net.abrikoos.blockout.server.gamestate;

import net.abrikoos.blockout.BlockoutLogger;
import net.abrikoos.blockout.item.BlockoutModItems;
import net.abrikoos.blockout.networkv2.game.*;
import net.abrikoos.blockout.networkv2.team.Colors;
import net.abrikoos.blockout.networkv2.team.PlayerData;
import net.abrikoos.blockout.networkv2.team.ServerTeamRegV2;
import net.abrikoos.blockout.networkv2.team.TeamData;
import net.abrikoos.blockout.chunkgenerators.CustomWorldManager;
import net.abrikoos.blockout.server.ThunderyWeatherCycle;
import net.abrikoos.blockout.server.builder.BlockDropChangeBuilder;
import net.abrikoos.blockout.server.builder.EvenMoreReworkedBlockoutBuilder;
import net.abrikoos.blockout.server.builder.ReworkedBlockoutBuilder;
import net.abrikoos.blockout.server.goals.GoalFactory;
import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.abrikoos.blockout.server.listeners.*;
import net.abrikoos.blockout.client.modes.random_block_finder.RandomBlockFinder;
import net.abrikoos.blockout.networkv2.compass.CompassPlayerPosition;
import net.abrikoos.blockout.networkv2.compass.PlayersPositionPacket;
import net.abrikoos.blockout.network.NetworkBridge;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.structure.StructureStart;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.minecraft.world.gen.structure.StructureType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.time.LocalTime;
import java.util.*;


public class GameState {

    public static List<BlockoutGoal> goals = new ArrayList<>();

    public static GameStartPacket info = GameStartPacket.empty();

    public static MinecraftServer server;

    public static List<Integer> teamIndexes = new ArrayList<>();

    public static RandomBlockFinder rbf;

    public static ServerTeamRegV2 teamRegistry = new ServerTeamRegV2();

    public static boolean teamCombinedScore = false;

    public static GoalBoardUpdatePacket board() {
        return info.board();
    }

    public static List<StructureLocationPacket> structures = new ArrayList<>();

    public static Map<String, String> playerRespawnTargets = new HashMap<>();

    public static boolean increasedSkulls = false;

    public static ThunderyWeatherCycle thunderyWeatherCycle = null;

    public static boolean paused = false;

    public static long pauseStartTime = 0;

    public static long pauseOffset = 0;

    public static void playerServerJoin(ServerPlayerEntity player) {
        try {
            teamRegistry.getPlayerDataByUUID(player.getUuid().toString());
            teamRegistry.playerHasConnected(player.getUuid().toString());
        }
        catch (Exception e) {
            teamRegistry.addPlayerData(new PlayerData(player.getUuid().toString(), player.getName().getString(), true));
        }
        if (inGame()) {
            NetworkBridge.sendToPlayer(player,info);
        }
    }

    public static void playerServerLeave(ServerPlayerEntity player) {
        try {
            teamRegistry.playerHasDisconnected(player.getUuid().toString());
        }
        catch (Exception e) {
            BlockoutLogger.log("Error removing player from team registry");
        }
    }

    public static void registerPlayerRespawnTarget(String puuid, String targetPuuid) {
        playerRespawnTargets.put(puuid, targetPuuid);
    }

    public static @Nullable ServerPlayerEntity getPlayerRespawnTarget(String puuid) {
        String targetPuuid = playerRespawnTargets.get(puuid);
        if (targetPuuid == null) {
            return null;
        }
        var returnedPlayer = getPlayerByUUID(targetPuuid);
        registerPlayerRespawnTarget(puuid, null);
        return returnedPlayer;
    }

    public static void registerStructure(@NotNull StructureStart structure) {
        int r, g, b;
        var structKey = server.getOverworld().getRegistryManager().get(RegistryKeys.STRUCTURE).getKey(structure.getStructure());
        if (structKey.isEmpty()) {
            return;
        }

        if (structKey.get().equals(StructureKeys.TRIAL_CHAMBERS)) {
            r = 245; g = 159; b = 39;
        }
        else if(structKey.get().equals(StructureKeys.DESERT_PYRAMID)) {
            r = 222; g = 192; b = 20;
        }
        else if(structKey.get().equals(StructureKeys.JUNGLE_PYRAMID)) {
            r = 58; g = 97; b = 48;
        }
        else if(structKey.get().equals(StructureKeys.IGLOO)) {
            r = 180; g = 63; b = 45;
        }
        else if(structKey.get().equals(StructureKeys.MONUMENT)) {
            r = 43; g = 87; b = 189;
        }
//        else if(structKey.get().equals(StructureKeys.SHIPWRECK)) {
//            r = 139; g = 69; b = 19;
//        }
        else if(structKey.get().equals(StructureKeys.SWAMP_HUT)) {
            r = 98; g = 150; b = 86;
        }
        else if(structKey.get().equals(StructureKeys.MANSION)) {
            r = 89; g = 53; b = 15;
        }
        else if(structKey.get().equals(StructureKeys.FORTRESS)) {
            r = 33; g = 23; b = 15;
        }
//        else if(structure.getStructure().getType() == StructureType.JIGSAW) {
//            r = 178; g = 34; b = 34;
//        }
//        else if(structure.getStructure().getType() == StructureType.RUINED_PORTAL) {
//            r = 105; g = 105; b = 105;
//        }
        else if(structKey.get().equals(StructureKeys.END_CITY)) {
            r = 107; g = 44; b = 105;
        }
        else if(structKey.get().equals(StructureKeys.ANCIENT_CITY)) {
            r = 41; g = 33; b = 41;
        }
        else {
            return;
        }
        var box = structure.getBoundingBox();
        structures.add(new StructureLocationPacket(r, g, b, box.getCenter().getX(), box.getCenter().getY(), box.getCenter().getZ()));
        sendStructuresLocationToAll();
    }

    public static void sendStructuresLocation(ServerPlayerEntity player) {
        NetworkBridge.sendToPlayer(player,new StructureLocationsPacket(structures));
    }

    public static void sendStructuresLocationToAll() {
        for (ServerPlayerEntity player : players()) {
            NetworkBridge.sendToPlayer(player,new StructureLocationsPacket(structures));
        }
    }

    public static void enableThunderyWeatherCycle() {
        if (thunderyWeatherCycle == null) {
            thunderyWeatherCycle = new ThunderyWeatherCycle();
        }
    }



//    public static void playerJoinTeam(String puuid, String team) {
//        try {
//            teamRegistry.addPlayerToTeam(puuid, team);
//        }
//        catch (Exception e) {
//            BlockoutLogger.log("Error adding player to team");
//        }
//    }
//
//    public static void playerJoinTeam(ServerPlayerEntity player, String team) {
//        try {
//            teamRegistry.addPlayerToTeam(player.getUuid().toString(), team);
//        }
//        catch (Exception e) {
//            BlockoutLogger.log("Error adding player to team");
//        }
//    }

    public static List<ServerPlayerEntity> players() {
        return server.getPlayerManager().getPlayerList();
    }

    public static void addTeam(String name) {
        try {
            teamRegistry.createNewTeam(name);
        }
        catch (Exception ignored) {

        }
    }

    public static void removeTeam(String name) {
        try {
            teamRegistry.removeTeam(name);
        }
        catch (Exception ignored) {

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
            BlockoutLogger.log("Goal index out of bounds of goals list");
            return;
        }
        goals.get(goal).complete(playerName);
    }

    public static void StartGame(StartGameRequestPacket packet) {
        if (!goals.isEmpty()) {
            destroyGame();
        }
        long startTime = System.currentTimeMillis();
        int freezetime = 60000;

        switch (packet.gameMode()) {
            case "lockout":
                newLockout(packet);
                break;
            case "single_block":
                newSingleBlock(packet);
                break;
            case "block_swap":
                newBlockSwap(packet);
                break;
            case "dropshuffle":
                newDropShuffle(packet);
                break;
        }

        goals.forEach(goal -> goal.subscribe(GameState::onGoalComplete));

        GameSettings.settings = packet;

        // reset all player stats
        players().forEach(player -> {
            player.getInventory().clear();
            PlayerAdvancementTracker tracker = player.getAdvancementTracker();
            MinecraftServer playerServer = player.getServer();
            assert playerServer != null;
            ServerAdvancementLoader loader = playerServer.getAdvancementLoader();
            loader.getAdvancements().forEach(advancement ->
                advancement.value().criteria().forEach((criterion, conditions) ->
                    tracker.revokeCriterion(advancement, criterion)));
            StatHandler sh = player.getStatHandler();
            Stats.CUSTOM.forEach(stat -> sh.setStat(player, stat, 0));
            player.heal(player.getMaxHealth());
            player.giveItemStack(BlockoutModItems.PLAYER_TRACKING_COMPASS.getDefaultStack());
        });

        // create custom world and teleport players that are in a playing team
        long gameSeed = new Random().nextLong();
        CustomWorldManager.GeneratorMode genMode = switch (packet.gameMode()) {
            case "single_block" -> CustomWorldManager.GeneratorMode.SINGLE_BLOCK;
            case "block_swap"   -> CustomWorldManager.GeneratorMode.BLOCK_SWAP;
            default             -> CustomWorldManager.GeneratorMode.NORMAL;
        };
        CustomWorldManager.createWorlds(server, gameSeed, genMode);

        List<ServerPlayerEntity> gamePlayers = new ArrayList<>();
        packet.teamUUIDs().forEach(teamUUID -> {
            try {
                TeamData team = teamRegistry.getTeamDataByUUID(teamUUID);
                for (String playerUUID : team.playerUUIDs) {
                    ServerPlayerEntity p = getPlayerByUUID(playerUUID);
                    if (p != null) gamePlayers.add(p);
                }
            } catch (Exception e) {
                BlockoutLogger.log("Error getting team for teleport: " + e.getMessage());
            }
        });
        CustomWorldManager.teleportPlayers(server, gamePlayers);

        // send new game packet to all players
        players().forEach(player -> NetworkBridge.sendToPlayer(player, info));

    }

    private static void newDropShuffle(StartGameRequestPacket packet) {

        GoalBoardUpdatePacket gbup = BlockDropChangeBuilder.generateDropShuffleBoard(packet);
        long startTime = System.currentTimeMillis();
        int freezetime = 60000;
        info = new GameStartPacket("dropshuffle", packet.teamUUIDs().get(0), packet.teamUUIDs().get(1), gbup, startTime, freezetime, packet.teammateRespawn());

        for (GoalInfoPacket goal : gbup.goals()) {
            BlockoutGoal lg = GoalFactory.buildObtainGoal(Identifier.of(goal.goalID()), goal.goalIndex());
            goals.add(lg);
        }
    }

    private static void newLockout(StartGameRequestPacket packet) {

        EvenMoreReworkedBlockoutBuilder builder = new EvenMoreReworkedBlockoutBuilder(packet);
        long startTime = System.currentTimeMillis();
        int freezetime = 60000;

        info = new GameStartPacket("lockout", packet.teamUUIDs().get(0), packet.teamUUIDs().get(1), builder.packet, startTime, freezetime, packet.teammateRespawn());

        for (GoalInfoPacket goal : builder.packet.goals()) {
            BlockoutGoal lg = GoalFactory.buildGoal(goal.goalID(), goal.goalIndex());
            goals.add(lg);
        }
    }

    private static void newSingleBlock(StartGameRequestPacket packet) {
        EvenMoreReworkedBlockoutBuilder builder = new EvenMoreReworkedBlockoutBuilder(packet);
        long startTime = System.currentTimeMillis();
        int freezetime = 60000;

        info = new GameStartPacket("single_block", packet.teamUUIDs().get(0), packet.teamUUIDs().get(1), builder.packet, startTime, freezetime, packet.teammateRespawn());

        for (GoalInfoPacket goal : builder.packet.goals()) {
            BlockoutGoal lg = GoalFactory.buildGoal(goal.goalID(), goal.goalIndex());
            goals.add(lg);
        }
    }

    private static void newBlockSwap(StartGameRequestPacket packet) {
        EvenMoreReworkedBlockoutBuilder builder = new EvenMoreReworkedBlockoutBuilder(packet);
        long startTime = System.currentTimeMillis();
        int freezetime = 60000;

        info = new GameStartPacket("block_swap", packet.teamUUIDs().get(0), packet.teamUUIDs().get(1), builder.packet, startTime, freezetime, packet.teammateRespawn());

        for (GoalInfoPacket goal : builder.packet.goals()) {
            BlockoutGoal lg = GoalFactory.buildGoal(goal.goalID(), goal.goalIndex());
            goals.add(lg);
        }
    }

    public static void newRBF() {

    }

    public static void destroyGame() {
        for (BlockoutGoal goal : goals) {
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
        BlockDropChangeBuilder.resetHashMap();
        goals.clear();
        increasedSkulls = false;
        paused = false;
        pauseStartTime = 0;
        pauseOffset = 0;
    }

    public static String onGoalComplete(BlockoutGoalEvent event) {
        if (paused) return null;

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
            BlockoutLogger.log("Error getting team for player " + event.puuid);
        }
        new_goals_data.set(event.goalId, new GoalInfoPacket( goalinfopacket.goalName(), goalinfopacket.goalID(), event.goalId, event.puuid, goal_recipiant_team.teamUUID, goal_recipiant_team.teamColor));

        // calculate scores
        GameState.info.teamUUIDs().forEach(teamUUID -> {
            int count = (int) new_goals_data.stream()
                    .filter(goal -> Objects.equals(goal.completedTeamUUID(), teamUUID))
                    .count();
            new_scores.add(count);
        });

        GameState.info.updateBoard(new GoalBoardUpdatePacket(new_goals_data, justcompletedgoal, new_scores));

        // send new board to all players
        String playerName = getPlayerByUUID(event.puuid).getName().getString();
        final TeamData finalRecipiantTeam = goal_recipiant_team;
        players().forEach(player -> {
            try {
                NetworkBridge.sendToPlayer(player, board());
                player.sendMessage(Text.literal(playerName).withColor(Colors.get(finalRecipiantTeam.teamColor)).append(Text.of(" has completed goal: " + goalinfopacket.goalName())));
            } catch (Exception e) {
                BlockoutLogger.log("Error sending packet to player " + player.getName().getString());
            }
        });
        return event.recipiant;
    }

    public static GoalBoardUpdatePacket getBoard() {
        return board();
    }


    public static void pause() {
        if (!inGame() || paused) return;
        paused = true;
        pauseStartTime = System.currentTimeMillis();
        info.updatePauseState(true, pauseOffset);
        GamePauseUpdatePacket packet = new GamePauseUpdatePacket(true, pauseOffset);
        for (ServerPlayerEntity player : players()) {
            NetworkBridge.sendToPlayer(player,packet);
        }
        for (ServerPlayerEntity player : players()) {
            player.sendMessage(Text.literal("[Lockout] Game paused."));
        }
    }

    public static void resume() {
        if (!inGame() || !paused) return;
        pauseOffset += System.currentTimeMillis() - pauseStartTime;
        paused = false;
        pauseStartTime = 0;
        info.updatePauseState(false, pauseOffset);
        GamePauseUpdatePacket packet = new GamePauseUpdatePacket(false, pauseOffset);
        for (ServerPlayerEntity player : players()) {
            NetworkBridge.sendToPlayer(player,packet);
        }
        for (ServerPlayerEntity player : players()) {
            player.sendMessage(Text.literal("[Lockout] Game resumed."));
        }
    }

    public static void resumeGame(GameStartPacket packet, List<BlockoutGoal> loadedGoals, ServerTeamRegV2 registry) {
        if (!goals.isEmpty()) {
            destroyGame();
        }
        teamRegistry = registry;
        info = packet;
        goals = new ArrayList<>(loadedGoals);
        paused = packet.paused();
        pauseOffset = packet.pauseOffset();
        pauseStartTime = 0;

        for (BlockoutGoal goal : goals) {
            if (!(goal instanceof net.abrikoos.blockout.server.goals.CompletedGoalPlaceholder)) {
                goal.subscribe(GameState::onGoalComplete);
            }
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
                NetworkBridge.sendToPlayer(player,new PlayersPositionPacket(pos));
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


























