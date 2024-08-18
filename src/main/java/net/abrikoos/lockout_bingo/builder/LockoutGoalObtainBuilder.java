package net.abrikoos.lockout_bingo.builder;
import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.goals.eat.EatMultiFoodGoal;
import net.abrikoos.lockout_bingo.goals.obtain.ObtainItemGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;

public class LockoutGoalObtainBuilder extends LockoutGoalBuilder {


    @Override
    public List<LockoutGoal> buildBoard(BlackoutStartGameInfo info) {
        int sized = 5;
        int boardSize = 5;
//        if (size.isEmpty()) {
//            boardSize = size.get();
//        }
//        else {
//            boardSize = 5;
//        }
        List<Item> all_blocks = new ArrayList<>();
        for (Block block : Registries.BLOCK) {
            all_blocks.add(block.asItem());
        }
        List<LockoutGoal> goals = new ArrayList<>();
        for (int i = 0; i < boardSize * boardSize - 1; i++) {
            int randomIndex = (int) (Math.random() * all_blocks.size());
            goals.add(new ObtainItemGoal(i, all_blocks.get(randomIndex)));
            LockoutLogger.log("ObtainItemGoal: " + all_blocks.get(randomIndex).getName());
        }
        goals.add(new EatMultiFoodGoal(24,5 ));
        return goals;
    }
}
