package net.abrikoos.lockout_bingo.client.modes;

import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.team.UnitedTeamRegistry;
import net.abrikoos.lockout_bingo.util.BlockoutList;

import java.util.List;

public abstract class LockoutGame {

    public abstract BlockoutList<GoalListItem> getGoals();

    public abstract String name();

    public abstract BlockoutList<UnitedTeamRegistry.Team> getTeams();

    public abstract BlockoutList<Integer> getScores();



}
