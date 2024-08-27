package net.abrikoos.lockout_bingo.goals.obtain;


import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class DontObtainItemGoal extends LockoutGoal {
    Item item;
    public DontObtainItemGoal(int id, Item item) {
        super(id);
        this.item = item;
        ServerTickEvents.START_SERVER_TICK.register(this::checkCompletion);
    }

    private void checkCompletion(MinecraftServer minecraftserver) {
        if (completed != null) { return; }
        for (ServerPlayerEntity player : minecraftserver.getPlayerManager().getPlayerList()) {
            PlayerInventory inventory = player.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.getStack(i).getItem() == item) {
                    LockoutLogger.log("Player " + player.getName().toString() + " has obtained " + item.getName().toString());
                    completed = player;
                    this.notifyListeners(new LockoutGoalEvent(player.getUuidAsString(), "enemy", this.id));
                    return;
                }
            }
        }
    }

    @Override
    public String name() {
        return "Dont Obtain " + item.getName().toString();
    }

    @Override
    public String recipiant() {
        return "enemy";
    }

}
