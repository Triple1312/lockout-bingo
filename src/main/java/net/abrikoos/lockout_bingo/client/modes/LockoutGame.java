package net.abrikoos.lockout_bingo.client.modes;

import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.team.LockoutTeam;

import java.util.List;

public abstract class LockoutGame {

    public abstract List<GoalListItem> getGoals();

    public abstract String name();

    public abstract List<LockoutTeam> getTeams();

    public abstract List<Integer> getScores();



}
