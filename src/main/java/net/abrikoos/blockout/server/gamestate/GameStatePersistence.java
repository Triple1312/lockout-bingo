package net.abrikoos.blockout.server.gamestate;

import com.google.gson.*;
import net.abrikoos.blockout.BlockoutLogger;
import net.abrikoos.blockout.chunkgenerators.CustomWorldManager;
import net.abrikoos.blockout.networkv2.game.GameStartPacket;
import net.abrikoos.blockout.networkv2.game.GoalBoardUpdatePacket;
import net.abrikoos.blockout.networkv2.game.GoalInfoPacket;
import net.abrikoos.blockout.networkv2.team.PlayerData;
import net.abrikoos.blockout.networkv2.team.ServerTeamRegV2;
import net.abrikoos.blockout.networkv2.team.TeamData;
import net.abrikoos.blockout.server.goals.CompletedGoalPlaceholder;
import net.abrikoos.blockout.server.goals.GoalFactory;
import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameStatePersistence {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String NULL_UUID = "00000000-0000-0000-0000-000000000000";

    private static Path savePath(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.ROOT).resolve("blockout_state.json");
    }

    public static void save(MinecraftServer server) {
        if (!GameState.inGame()) {
            try { Files.deleteIfExists(savePath(server)); } catch (IOException ignored) {}
            return;
        }
        try {
            JsonObject root = new JsonObject();

            GameStartPacket info = GameState.info;
            root.addProperty("game_mode", info.game_mode());
            root.addProperty("team1", info.team1());
            root.addProperty("team2", info.team2());
            root.addProperty("startTime", info.startTime());
            root.addProperty("freezeTime", info.freezeTime());
            root.addProperty("teammateRespawn", info.teammateRespawn);
            root.addProperty("paused", GameState.paused);
            root.addProperty("pauseOffset", GameState.pauseOffset);

            Long seed = CustomWorldManager.getCustomSeed(CustomWorldManager.CUSTOM_OVERWORLD);
            root.addProperty("gameSeed", seed != null ? seed : 0L);

            GoalBoardUpdatePacket board = info.board();
            root.addProperty("justCompletedGoal", board.justCompletedGoal());
            JsonArray scoresArr = new JsonArray();
            board.scores().forEach(scoresArr::add);
            root.add("scores", scoresArr);

            JsonArray goalsArr = new JsonArray();
            for (GoalInfoPacket g : board.goals()) {
                JsonObject gObj = new JsonObject();
                gObj.addProperty("goalName", g.goalName());
                gObj.addProperty("goalID", g.goalID());
                gObj.addProperty("goalIndex", g.goalIndex());
                gObj.addProperty("completedPlayerUUID", g.completedPlayerUUID());
                gObj.addProperty("completedTeamUUID", g.completedTeamUUID());
                gObj.addProperty("color", g.color());
                goalsArr.add(gObj);
            }
            root.add("goals", goalsArr);

            JsonArray teamsArr = new JsonArray();
            for (TeamData t : GameState.teamRegistry.teams) {
                JsonObject tObj = new JsonObject();
                tObj.addProperty("teamName", t.teamName);
                tObj.addProperty("teamUUID", t.teamUUID);
                tObj.addProperty("teamColor", t.teamColor);
                JsonArray puuids = new JsonArray();
                t.playerUUIDs.forEach(puuids::add);
                tObj.add("playerUUIDs", puuids);
                teamsArr.add(tObj);
            }
            root.add("teams", teamsArr);

            JsonArray playersArr = new JsonArray();
            for (PlayerData p : GameState.teamRegistry.players) {
                JsonObject pObj = new JsonObject();
                pObj.addProperty("puuid", p.puuid);
                pObj.addProperty("name", p.name);
                playersArr.add(pObj);
            }
            root.add("players", playersArr);

            Files.writeString(savePath(server), GSON.toJson(root));
            BlockoutLogger.log("Game state saved.");
        } catch (Exception e) {
            BlockoutLogger.log("Failed to save game state: " + e.getMessage());
        }
    }

    public static void load(MinecraftServer server) {
        Path path = savePath(server);
        if (!Files.exists(path)) return;
        try {
            JsonObject root = JsonParser.parseString(Files.readString(path)).getAsJsonObject();

            // Rebuild team registry without triggering network sends
            ServerTeamRegV2 registry = new ServerTeamRegV2();
            for (JsonElement te : root.getAsJsonArray("teams")) {
                JsonObject t = te.getAsJsonObject();
                TeamData team = new TeamData(
                        t.get("teamName").getAsString(),
                        t.get("teamColor").getAsInt(),
                        t.get("teamUUID").getAsString());
                t.getAsJsonArray("playerUUIDs").forEach(p -> team.playerUUIDs.add(p.getAsString()));
                registry.teams.add(team);
            }
            for (JsonElement pe : root.getAsJsonArray("players")) {
                JsonObject p = pe.getAsJsonObject();
                registry.players.add(new PlayerData(
                        p.get("puuid").getAsString(),
                        p.get("name").getAsString(),
                        false));
            }

            // Rebuild board and goal list
            String gameMode = root.get("game_mode").getAsString();
            boolean isDropShuffle = "dropshuffle".equals(gameMode);

            List<GoalInfoPacket> goalInfos = new ArrayList<>();
            List<BlockoutGoal> goals = new ArrayList<>();
            for (JsonElement ge : root.getAsJsonArray("goals")) {
                JsonObject g = ge.getAsJsonObject();
                String goalName  = g.get("goalName").getAsString();
                String goalID    = g.get("goalID").getAsString();
                int goalIndex    = g.get("goalIndex").getAsInt();
                String cPlayer   = g.get("completedPlayerUUID").getAsString();
                String cTeam     = g.get("completedTeamUUID").getAsString();
                int color        = g.get("color").getAsInt();
                goalInfos.add(new GoalInfoPacket(goalName, goalID, goalIndex, cPlayer, cTeam, color));

                boolean completed = !NULL_UUID.equals(cTeam);
                if (completed) {
                    goals.add(new CompletedGoalPlaceholder(goalIndex));
                } else if (isDropShuffle) {
                    goals.add(GoalFactory.buildObtainGoal(Identifier.of(goalID), goalIndex));
                } else {
                    try {
                        goals.add(GoalFactory.buildGoal(goalID, goalIndex));
                    } catch (Exception e) {
                        BlockoutLogger.log("Unknown goal on load '" + goalID + "' — skipping with placeholder");
                        goals.add(new CompletedGoalPlaceholder(goalIndex));
                    }
                }
            }

            List<Integer> scores = new ArrayList<>();
            root.getAsJsonArray("scores").forEach(s -> scores.add(s.getAsInt()));
            int justCompleted = root.get("justCompletedGoal").getAsInt();
            GoalBoardUpdatePacket board = new GoalBoardUpdatePacket(goalInfos, justCompleted, scores);

            GameStartPacket packet = new GameStartPacket(
                    gameMode,
                    root.get("team1").getAsString(),
                    root.get("team2").getAsString(),
                    board,
                    root.get("startTime").getAsLong(),
                    root.get("freezeTime").getAsInt(),
                    root.get("teammateRespawn").getAsBoolean(),
                    root.get("paused").getAsBoolean(),
                    root.get("pauseOffset").getAsLong());

            // Recreate custom worlds with the original seed
            long seed = root.get("gameSeed").getAsLong();
            CustomWorldManager.GeneratorMode genMode = switch (gameMode) {
                case "single_block" -> CustomWorldManager.GeneratorMode.SINGLE_BLOCK;
                case "block_swap"   -> CustomWorldManager.GeneratorMode.BLOCK_SWAP;
                default             -> CustomWorldManager.GeneratorMode.NORMAL;
            };
            CustomWorldManager.createWorlds(server, seed, genMode);

            GameState.resumeGame(packet, goals, registry);
            BlockoutLogger.log("Game state loaded.");
        } catch (Exception e) {
            BlockoutLogger.log("Failed to load game state: " + e.getMessage());
        }
    }
}
