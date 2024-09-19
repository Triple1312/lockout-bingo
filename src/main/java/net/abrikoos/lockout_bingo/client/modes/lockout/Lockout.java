package net.abrikoos.lockout_bingo.client.modes.lockout;

import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.client.modes.LockoutGame;
import net.abrikoos.lockout_bingo.network.game.LockoutStartGameInfo;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.abrikoos.lockout_bingo.team.TeamRegistry;

import java.util.ArrayList;
import java.util.List;

public class Lockout extends LockoutGame {
    public LockoutStartGameInfo lsgi;
    public List<LockoutTeam> teams;
    public LockoutUpdateBoardInfo lubi;

    public Lockout(LockoutStartGameInfo lsgi) {
        this.lsgi = lsgi;
        this.teams = new ArrayList<>();
        for (int i = 0; i < lsgi.teams.size(); i++) {
            teams.add(TeamRegistry.getTeamString(lsgi.teams.get(i).name()));
        }
    }

    @Override
    public List<GoalListItem> getGoals() {
        return List.of(lsgi.goals);
    }

    public LockoutUpdateBoardInfo latestUpdate() {
        return lubi;
    }

    @Override
    public String name() {
        return "Lockout";
    }

    @Override
    public List<LockoutTeam> getTeams() {
        return teams;
    }

    @Override
    public List<Integer> getScores() {
        List<Integer> scores = new ArrayList<>();
        for (LockoutTeam team : teams) {
            int score = 0;
            for (String uuid : lubi.goals) {
                if (team.playeruuids.contains(uuid)) {
                    score++;
                }
            }
            scores.add(score);
        }
        return scores;
    }
}
