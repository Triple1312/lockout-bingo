package net.abrikoos.lockout_bingo.goals;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class LockoutGoal {
    public static int difficulty;
    public static String dimension; // make enum // selects the dimension where the goal is easiest to achieve
    public PlayerEntity completed = null;
    public static boolean couldStalemate;
    public int id;
    List<Function<LockoutGoalEvent, String>> listeners = new ArrayList<>();


    public LockoutGoal(int id) { this.id = id; }

    public void subscribe(Function<LockoutGoalEvent, String> callback) {
        listeners.add(callback);
    }

    protected void notifyListeners(LockoutGoalEvent event) {
        for (Function<LockoutGoalEvent, String> listener : listeners) {
            listener.apply(event);
        }
    }

    public String name() {
        return "";
    }

    public String description() {
        return "";
    }

    public String tip() {
        return "";
    }

    public void destory() {
//        listeners.clear();
    }

    public String recipiant() {
        return "ally";
    }

    protected void completed(PlayerEntity player) {
        completed = player;
        notifyListeners(new LockoutGoalEvent(player.getUuidAsString(), recipiant(), id));
        destory();
    }
}
