package net.abrikoos.lockout_bingo.server.goals.armor;

import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.PlayerInventoryListener;
import net.abrikoos.lockout_bingo.server.listeners.TickListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WearCarvedPumpkinGoal extends LockoutGoal {

    Map<PlayerEntity, Integer> playerTimes;
    int seconds;


    public WearCarvedPumpkinGoal(int id, int seconds) {
        super(id);
        this.seconds = seconds;
        playerTimes = new HashMap<>();
        TickListener.subscribe(this::checkCompletion);
        PlayerInventoryListener.subscribe(this::validateProgress);
    }


    public void validateProgress(PlayerEntity player, ItemStack stack, int slot, boolean added) {
        if (completed != null) return;
        if (stack.getItem() == Items.CARVED_PUMPKIN) {
            boolean has = false;
            List<Item> list = new ArrayList<>();
            for (ItemStack itemStack : player.getArmorItems()) {
                list.add(itemStack.getItem());
            }
            boolean containsCP = list.contains(Items.CARVED_PUMPKIN);
            boolean containsKey = playerTimes.containsKey(player);
            if ( containsCP && !containsKey) {
                playerTimes.computeIfAbsent(player, k -> Math.toIntExact(GameState.server.getOverworld().getTime() / 20 + seconds));
            } else if (!containsCP && containsKey) {
                playerTimes.remove(player);
            }
        }
    }

    public void checkCompletion(MinecraftServer mcserver) {
        if (completed != null) return;
        int time = (int) (mcserver.getOverworld().getTime() /20);
        playerTimes.forEach(
            (p, i) -> {
                if (i < time) this.completed(p);
            }
        );
    }
}
