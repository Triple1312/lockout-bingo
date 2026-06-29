package net.abrikoos.blockout.server.gamestate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.abrikoos.blockout.BlockoutLogger;
import net.abrikoos.blockout.networkv2.game.*;
import net.abrikoos.blockout.networkv2.team.PlayerData;
import net.abrikoos.blockout.networkv2.team.ServerTeamRegV2;
import net.abrikoos.blockout.networkv2.team.TeamData;
import net.abrikoos.blockout.server.goals.CompletedGoalPlaceholder;
import net.abrikoos.blockout.server.goals.GoalFactory;
import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameSaveManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String SAVE_FILE = "blockout_save.json";
    private static final String NULL_UUID = "00000000-0000-0000-0000-000000000000";

    static class SaveData {
        String gameMode;
        String team1;
        String team2;
        long startTime;
        int freezeTime;
        boolean teammateRespawn;
        long pauseOffset;
        long saveTime;
        List<SavedGoal> goals = new ArrayList<>();
        List<SavedTeam> teams = new ArrayList<>();
        List<SavedPlayer> players = new ArrayList<>();
    }

    static class SavedGoal {
        String goalID;
        String goalName;
        int goalIndex;
        String completedPlayerUUID;
        String completedTeamUUID;
        int color;
    }

    static class SavedTeam {
        String teamName;
        String teamUUID;
        int teamColor;
        List<String> playerUUIDs = new ArrayList<>();
    }

    static class SavedPlayer {
        String puuid;
        String name;
    }

    public static void save() {
        if (!GameState.inGame()) return;

        SaveData data = new SaveData();
        data.gameMode = GameState.info.game_mode();
        data.team1 = GameState.info.team1();
        data.team2 = GameState.info.team2();
        data.startTime = GameState.info.startTime();
        data.freezeTime = GameState.info.freezeTime();
        data.teammateRespawn = GameState.info.teammateRespawn;
        data.pauseOffset = GameState.paused
                ? GameState.pauseOffset + (System.currentTimeMillis() - GameState.pauseStartTime)
                : GameState.pauseOffset;
        data.saveTime = System.currentTimeMillis();

        GameState.info.board().goals().forEach(goal -> {
            SavedGoal sg = new SavedGoal();
            sg.goalID = goal.goalID();
            sg.goalName = goal.goalName();
            sg.goalIndex = goal.goalIndex();
            sg.completedPlayerUUID = goal.completedPlayerUUID();
            sg.completedTeamUUID = goal.completedTeamUUID();
            sg.color = goal.color();
            data.goals.add(sg);
        });

        GameState.teamRegistry.teams.forEach(team -> {
            SavedTeam st = new SavedTeam();
            st.teamName = team.teamName;
            st.teamUUID = team.teamUUID;
            st.teamColor = team.teamColor;
            st.playerUUIDs = new ArrayList<>(team.playerUUIDs);
            data.teams.add(st);
        });

        GameState.teamRegistry.players.forEach(player -> {
            SavedPlayer sp = new SavedPlayer();
            sp.puuid = player.puuid;
            sp.name = player.name;
            data.players.add(sp);
        });

        Path savePath = FabricLoader.getInstance().getGameDir().resolve(SAVE_FILE);
        try (Writer writer = Files.newBufferedWriter(savePath)) {
            GSON.toJson(data, writer);
            BlockoutLogger.log("Game saved to " + savePath);
        } catch (IOException e) {
            BlockoutLogger.log("Failed to save game: " + e.getMessage());
        }
    }

    public static void load() {
        Path savePath = FabricLoader.getInstance().getGameDir().resolve(SAVE_FILE);
        if (!Files.exists(savePath)) return;

        SaveData data;
        try (Reader reader = Files.newBufferedReader(savePath)) {
            data = GSON.fromJson(reader, SaveData.class);
        } catch (IOException e) {
            BlockoutLogger.log("Failed to load game save: " + e.getMessage());
            return;
        }

        long serverDowntime = System.currentTimeMillis() - data.saveTime;
        long totalPauseOffset = data.pauseOffset + serverDowntime;

        ServerTeamRegV2 registry = new ServerTeamRegV2();
        for (SavedTeam st : data.teams) {
            TeamData team = new TeamData(st.teamName, st.teamColor, st.teamUUID);
            team.playerUUIDs = new ArrayList<>(st.playerUUIDs);
            registry.teams.add(team);
        }
        for (SavedPlayer sp : data.players) {
            registry.players.add(new PlayerData(sp.puuid, sp.name, false));
        }

        List<GoalInfoPacket> goalPackets = new ArrayList<>();
        for (SavedGoal sg : data.goals) {
            goalPackets.add(new GoalInfoPacket(
                    sg.goalName, sg.goalID, sg.goalIndex,
                    sg.completedPlayerUUID, sg.completedTeamUUID, sg.color));
        }

        List<Integer> scores = new ArrayList<>();
        for (String teamUUID : List.of(data.team1, data.team2)) {
            int count = (int) goalPackets.stream()
                    .filter(gip -> Objects.equals(gip.completedTeamUUID(), teamUUID))
                    .count();
            scores.add(count);
        }

        GoalBoardUpdatePacket board = new GoalBoardUpdatePacket(goalPackets, -1, scores);
        // loaded game resumes paused so admin can /resume when players are ready
        GameStartPacket gameInfo = new GameStartPacket(
                data.gameMode, data.team1, data.team2,
                board, data.startTime, data.freezeTime,
                data.teammateRespawn, true, totalPauseOffset);

        List<BlockoutGoal> goals = new ArrayList<>();
        for (SavedGoal sg : data.goals) {
            BlockoutGoal goal;
            boolean completed = sg.completedTeamUUID != null && !sg.completedTeamUUID.equals(NULL_UUID);
            if (completed) {
                goal = new CompletedGoalPlaceholder(sg.goalIndex);
            } else {
                try {
                    goal = GoalFactory.buildGoal(sg.goalID, sg.goalIndex);
                } catch (Exception e) {
                    BlockoutLogger.log("Failed to rebuild goal '" + sg.goalID + "': " + e.getMessage());
                    goal = new CompletedGoalPlaceholder(sg.goalIndex);
                }
            }
            goals.add(goal);
        }

        GameState.resumeGame(gameInfo, goals, registry);

        try {
            Files.delete(savePath);
        } catch (IOException e) {
            BlockoutLogger.log("Failed to delete save file: " + e.getMessage());
        }

        BlockoutLogger.log("Game resumed from save (server was down for " + serverDowntime / 1000 + "s, paused until /resume)");
    }
}
