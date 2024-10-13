//package net.abrikoos.lockout_bingo.client;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Consumer;
//
//public class TeamsChangeListener {
//
//    public static final List<Consumer<List<LockoutTeam>>> listeners = new ArrayList<>();
//
//    public static void subscribe(Consumer<List<LockoutTeam>> listener) {
//        listeners.add(listener);
//    }
//
//    public static void registerEvent(List<LockoutTeam> teams) {
//        for (Consumer<List<LockoutTeam>> listener : listeners) {
//            listener.accept(teams);
//        }
//    }
//}
