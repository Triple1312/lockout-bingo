package net.abrikoos.lockout_bingo.server.goals.obtain;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.server.listeners.ServerTickListener;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ObtainItemGoal extends LockoutGoal {
    Item item;
    int count;
    public ObtainItemGoal(int id, Item item) {
        super(id);
        this.item = item;
        this.count = 1;
        ServerTickListener.subscribe(this::checkCompletion);
    }

    private void checkCompletion(MinecraftServer minecraftserver) {
        if (completed != null) { return; }
        for (ServerPlayerEntity player : minecraftserver.getPlayerManager().getPlayerList()) {
            PlayerInventory inventory = player.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.getStack(i).getItem() == item && inventory.getStack(i).getCount() >= this.count) {
                    this.completed(player);
                }
            }
        }
    }

    @Override
    public String name() {
        return "Obtain " + item.getName().toString();
    }

    @Override
    public void destory() {
        ServerTickListener.unsubscribe(this::checkCompletion);
    }

}
