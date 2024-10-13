//package net.abrikoos.lockout_bingo.team;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class OldTeamRegistry {
//    private static List<LockoutTeam> teams = new ArrayList<>();
//
//    public static void addTeam(String name) {
//        int index = 1;
//        for (int i = 1; i < 11; i++) {
//            for (LockoutTeam team : teams) {
//                if (team.teamId == i) {
//                    index++;
//                }
//            }
//            if (index == i) {
//                break;
//            }
//        }
//        teams.add(new LockoutTeam(name, index));
//    }
//
//    public static LockoutTeam getTeam(int teamIndex) {
//        for (LockoutTeam team : teams) {
//            if (team.teamId == teamIndex) {
//                return team;
//            }
//        }
//        return null;
//    }
//
//    public static LockoutTeam getTeamString(String name) {
//        for (LockoutTeam team : teams) {
//            if (team.name.equals(name)) {
//                return team;
//            }
//        }
//        return null;
//    }
//
//    public static void setAllTeams(List<LockoutTeam> teams) {
//        OldTeamRegistry.teams = teams;
//    }
//
//    public static List<LockoutTeam> getTeams() {
//        return teams;
//    }
//
////    public static subscribe()
//
//
//}
