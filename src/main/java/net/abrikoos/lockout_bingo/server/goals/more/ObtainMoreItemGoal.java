package net.abrikoos.lockout_bingo.server.goals.more;

import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.PlayerInventoryListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class ObtainMoreItemGoal extends LockoutGoal {

    final Item item;
    PlayerEntity holder = null;
    int holderCount = 0;


    public ObtainMoreItemGoal(int id, Item item) {
        super(id);
        this.item = item;
        PlayerInventoryListener.subscribe(this::validateProgress);
    }

    private void validateProgress(PlayerEntity playerEntity, ItemStack itemStack, Integer integer, Boolean added) {
        if (itemStack.getItem() == item) {
            if (added && playerEntity != holder) {
                int playerCount = 0;
                for (ItemStack stack : playerEntity.getInventory().main) {
                    if (stack != null && stack.getItem() == item) {
                        playerCount += stack.getCount();
                    }
                }
                if (playerCount > holderCount) {
                    this.completed(playerEntity);
                    this.holder = playerEntity;
                    this.holderCount = playerCount;
                }
            }
            else if(playerEntity == holder && !added) {
                fixNewHolder(GameState.server);
            }
        }
    }


    public void fixNewHolder(MinecraftServer server) { // todo could possibly be done with inventoryListener
        int max = 0;
        PlayerEntity max_player = null;
        for (PlayerEntity p : server.getPlayerManager().getPlayerList()) {
            int playerCount = 0;
            for (ItemStack stack : p.getInventory().main) {
                if (stack != null && stack.getItem() == item) {
                    playerCount += stack.getCount();
                }
            }
            if (playerCount > max) {
                max_player = p;
                max = playerCount;
            }
        }
        if (max_player != holder) {
            completed(max_player);
            this.holder = max_player;
            this.holderCount = max;
        }
    }
}
