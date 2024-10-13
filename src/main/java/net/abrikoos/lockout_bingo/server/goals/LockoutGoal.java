package net.abrikoos.lockout_bingo.server.goals;

import com.mojang.authlib.GameProfile;
import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.abrikoos.lockout_bingo.team.UTeamPlayer;
import net.abrikoos.lockout_bingo.team.UnitedTeamRegistry;
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
        if (player == null) {
            notifyListeners(new LockoutGoalEvent("00000000-0000-0000-0000-000000000000", recipiant(), id));
        }
        else {
            notifyListeners(new LockoutGoalEvent(player.getUuidAsString(), recipiant(), id));
        }

//        destory();
    }

    public void complete(String playername) {
        ServerPlayerEntity player = null;

        for (ServerPlayerEntity p : GameState.players()) {
            if (p.getName().equals(playername)) {
                player = p;
                break;
            }
        }
        if (player == null) {
            return;
        }
        completed(player);
    }
}
