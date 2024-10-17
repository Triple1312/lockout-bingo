package net.abrikoos.lockout_bingo.client.modes.lockout;

import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.client.modes.LockoutGame;
import net.abrikoos.lockout_bingo.network.game.LockoutStartGameInfo;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
import net.abrikoos.lockout_bingo.team.UnitedTeamRegistry;
import net.abrikoos.lockout_bingo.util.BlockoutList;

import java.util.*;

public class Lockout extends LockoutGame {
    public LockoutStartGameInfo lsgi;
    public BlockoutList<UnitedTeamRegistry.Team> teams;
    public LockoutUpdateBoardInfo lubi;
    public int goal_count = 25;

    public Lockout(LockoutStartGameInfo lsgi) {
        this.lsgi = lsgi;
        this.teams = UnitedTeamRegistry.getTeams().where(t-> lsgi.teams.any( u-> Objects.equals(u.name(), t.teamName())));
        this.lubi = generateEmptyBoard();
    }

    @Override
    public BlockoutList<GoalListItem> getGoals() {
        return new BlockoutList<>(List.of(lsgi.goals));
    }

    public LockoutUpdateBoardInfo latestUpdate() {
        return lubi;
    }

    @Override
    public String name() {
        return "Lockout";
    }

    @Override
    public BlockoutList<UnitedTeamRegistry.Team> getTeams() {
        return teams;
    }

    @Override
    public BlockoutList<Integer> getScores() {
        BlockoutList<String> goals = new BlockoutList<>(List.of(lubi.goals));
        return teams.fold(new BlockoutList<>(), (s, t) -> {
            s.add(goals.where( g-> UnitedTeamRegistry.getTeamPlayerByUUID(UUID.fromString(g)).teamIndex == t.teamId()).size());
            return s;
        });
    }

    public LockoutUpdateBoardInfo generateEmptyBoard() {
        String[] goals = new String[goal_count];
        Arrays.fill(goals, "00000000-0000-0000-0000-000000000000");
        return new LockoutUpdateBoardInfo(goals);
    }
}
