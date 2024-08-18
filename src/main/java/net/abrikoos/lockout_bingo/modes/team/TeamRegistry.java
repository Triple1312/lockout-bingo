package net.abrikoos.lockout_bingo.modes.team;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TeamRegistry {

    public static List<LockoutTeamDataClass> teams = new ArrayList<>();

    private static final List<Function< List<LockoutTeamDataClass>, Void>>  listeners = new ArrayList<>();


    public static void subscribe(Function< List<LockoutTeamDataClass>, Void> listener) {
        listeners.add(listener);
    }

    public static void unsubscribe(Function< List<LockoutTeamDataClass>, Void> listener) {
        listeners.remove(listener);
    }


    public static void updateTeams(List<LockoutTeamDataClass> teamList) {
        teams = teamList;
        for (Function< List<LockoutTeamDataClass>, Void> listener : listeners) {
            listener.apply(teams);
        }
    }




}
