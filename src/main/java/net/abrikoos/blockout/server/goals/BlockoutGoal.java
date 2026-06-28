package net.abrikoos.blockout.server.goals;

import com.mojang.authlib.GameProfile;
import net.abrikoos.blockout.server.gamestate.GameState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class BlockoutGoal {
    public static int difficulty;
    public static String dimension; // make enum // selects the dimension where the goal is easiest to achieve
    public PlayerEntity completed = null;
    public static boolean couldStalemate;
    public int id;
    List<Function<BlockoutGoalEvent, String>> listeners = new ArrayList<>();


    public BlockoutGoal(int id) { this.id = id; }

    public void subscribe(Function<BlockoutGoalEvent, String> callback) {
        listeners.add(callback);
    }

    protected void notifyListeners(BlockoutGoalEvent event) {
        for (Function<BlockoutGoalEvent, String> listener : listeners) {
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
        listeners.clear();
    }

    public String recipiant() {
        return "ally";
    }

//    abstract public void checkComplete();

    protected void completed(PlayerEntity player) {
        completed = player;
        if (player == null) {
            notifyListeners(new BlockoutGoalEvent("00000000-0000-0000-0000-000000000000", recipiant(), id));
        }
        else {
            notifyListeners(new BlockoutGoalEvent(player.getUuidAsString(), recipiant(), id));
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
//        if (player == null) {
//            return;
//        }
        completed(player);
    }
}
