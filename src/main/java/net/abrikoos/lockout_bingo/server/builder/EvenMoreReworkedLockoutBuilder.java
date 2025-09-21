package net.abrikoos.lockout_bingo.server.builder;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.networkv2.game.GoalBoardUpdatePacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalInfoPacket;
import net.abrikoos.lockout_bingo.networkv2.game.StartGameRequestPacket;
import net.abrikoos.lockout_bingo.server.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EvenMoreReworkedLockoutBuilder {

    StartGameRequestPacket info;

    public GoalBoardUpdatePacket packet;

    ArrayList<GoalListItem> items_diff1 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 1));
    ArrayList<GoalListItem> items_diff2 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 2));
    ArrayList<GoalListItem> items_diff3 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 3));
    ArrayList<GoalListItem> items_diff4 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 4));
    ArrayList<GoalListItem> items_diff5 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 5));

    int max_redstone = 2;
    int max_silk_touch = 1;
    int max_die = 3;
    int max_dont = 1;
    int max_breed = 3;
    int max_obtain = 5;
    int max_kill = 4;
    int max_eat = 5;
    int max_effect = 2;
    int max_tools = 2;
    int max_brew = 2;
    int max_armor = 2;
    int max_movement = 1;
    int max_ride = 1;
    int max_lvl = 2;
    int max_use = 2;
    int max_tame = 2;
    int max_biomes = 1;
    int max_wool = 1;
    int max_end = 6;
    int max_nether = 15;
    
    public EvenMoreReworkedLockoutBuilder(StartGameRequestPacket info) {
        this.info = info;
        this.packet = generateLockoutBoard();
    }

    public void removeGoalsWithTag(LockoutGoalTag tag) {
        this.items_diff1.removeIf(goal -> goal.tags.contains(tag));
        this.items_diff2.removeIf(goal -> goal.tags.contains(tag));
        this.items_diff3.removeIf(goal -> goal.tags.contains(tag));
        this.items_diff4.removeIf(goal -> goal.tags.contains(tag));
        this.items_diff5.removeIf(goal -> goal.tags.contains(tag));
    }
    
    public void removeGoalId(String id) {
        this.items_diff1.removeIf(goal -> goal.id.equals(id));
        this.items_diff2.removeIf(goal -> goal.id.equals(id));
        this.items_diff3.removeIf(goal -> goal.id.equals(id));
        this.items_diff4.removeIf(goal -> goal.id.equals(id));
        this.items_diff5.removeIf(goal -> goal.id.equals(id));
    }
    
    private GoalListItem getRandomGoal(int difficulty) {
        ArrayList<GoalListItem> items;
        switch (difficulty) {
            case 1 -> items = items_diff1;
            case 2 -> items = items_diff2;
            case 3 -> items = items_diff3;
            case 4 -> items = items_diff4;
            case 5 -> items = items_diff5;
            default -> throw new IllegalStateException("Unexpected value: " + difficulty);
        }
        if (items.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * items.size());
        GoalListItem goal = items.get(randomIndex);

        for (int i = 0; i < goal.tags.size(); i++) {
            switch (goal.tags.get(i)) {
                case end:
                    max_end--;
                    if (max_end <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.end);
                    }
                    break;
                case nether:
                    max_nether--;
                    if (max_nether <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.nether);
                    }
                    break;
                case redstone:
                    max_redstone--;
                    if (max_redstone <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.redstone);
                    }
                    break;
                case silk_touch:
                    max_silk_touch--;
                    if (max_silk_touch <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.silk_touch);
                    }
                    break;
                case die:
                    max_die--;
                    if (max_die <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.die);
                    }
                    break;
                case dont:
                    max_dont--;
                    if (max_dont <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.dont);
                    }
                    break;
                case breed:
                    max_breed--;
                    if (max_breed <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.breed);
                    }
                    break;
                case obtain:
                    max_obtain--;
                    if (max_obtain <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.obtain);
                    }
                    break;
                case kill:
                    max_kill--;
                    if (max_kill <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.kill);
                    }
                    break;
                case eat:
                    max_eat--;
                    if (max_eat <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.eat);
                    }
                    break;
                case effect:
                    max_effect--;
                    if (max_effect <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.effect);
                    }
                    break;
                case tools:
                    max_tools--;
                    if (max_tools <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.tools);
                    }
                    break;
                case brew:
                    max_brew--;
                    if (max_brew <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.brew);
                    }
                    break;
                case armor:
                    max_armor--;
                    if (max_armor <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.armor);
                    }
                    break;
                case movement:
                    max_movement--;
                    if (max_movement <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.movement);
                    }
                    break;
                case ride:
                    max_ride--;
                    if (max_ride <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.ride);
                    }
                    break;
                case lvl:
                    max_lvl--;
                    if (max_lvl <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.lvl);
                    }
                    break;
                case use:
                    max_use--;
                    if (max_use <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.use);
                    }
                    break;
                case tame:
                    max_tame--;
                    if (max_tame <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.tame);
                    }
                    break;
                case biomes:
                    max_biomes--;
                    if (max_biomes <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.biomes);
                    }
                    break;
                case wool:
                    max_wool--;
                    if (max_wool <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.wool);
                    }
                    break;
            }
        }
        return goal;
    }

    private void removeDisabledGoals() {
        for (String goaltype : info.disabledGoals()) {
            switch (goaltype) {
                case "end":
                    removeGoalsWithTag(LockoutGoalTag.end);
                    break;
                case "nether":
                    removeGoalsWithTag(LockoutGoalTag.nether);
                    break;
                case "redstone":
                    removeGoalsWithTag(LockoutGoalTag.redstone);
                    break;
                case "die":
                    removeGoalsWithTag(LockoutGoalTag.die);
                    break;
                case "dont":
                    removeGoalsWithTag(LockoutGoalTag.dont);
                    break;
                case "biomes":
                    removeGoalsWithTag(LockoutGoalTag.biomes);
                    break;
                case "advancement":
                    removeGoalsWithTag(LockoutGoalTag.advancement);
                    break;
                case "eat":
                    removeGoalsWithTag(LockoutGoalTag.eat);
                    break;
                case "kill":
                    removeGoalsWithTag(LockoutGoalTag.kill);
                    break;
                case "movement":
                    removeGoalsWithTag(LockoutGoalTag.movement);
                    break;
                case "breed":
                    removeGoalsWithTag(LockoutGoalTag.breed);
                    break;
                case "obtain":
                    removeGoalsWithTag(LockoutGoalTag.obtain);
                    break;
                case "armor":
                    removeGoalsWithTag(LockoutGoalTag.armor);
                    break;
                case "tools":
                    removeGoalsWithTag(LockoutGoalTag.tools);
                    break;
                case "ride":
                    removeGoalsWithTag(LockoutGoalTag.ride);
                    break;
            }
        }
    }

    private GoalBoardUpdatePacket generateLockoutBoard() {
        removeDisabledGoals();
        ArrayList<GoalInfoPacket> goalPackets = new ArrayList<>();
        ArrayList<Integer> difficulties = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));

        Random rand = new Random();
        int mu = info.difficulty();
        float sigma = 1f; // todo choose good value
        // todo diff uniform
        // todo diff random

        for (int i = 0; i < info.goalCount(); i++) {

            int difficulty = (int) Math.round(rand.nextGaussian() * sigma + mu);
            if (difficulty < 1) difficulty = 1;
            if (difficulty > 5) difficulty = 5;


            GoalListItem goal = getRandomGoal(difficulty);
            if (goal == null) {
                LockoutLogger.log("Not enough goals to generate board, generated " + i + " out of " + info.goalCount() + " goals for difficulty " + difficulty);
                i--;
                continue;
            }
            difficulties.set(difficulty - 1, difficulties.get(difficulty - 1) + 1);
            goalPackets.add(new GoalInfoPacket(goal.name, goal.id, i, "00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000", 0));
            this.removeGoalId(goal.id);
        }

        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(0);
        scores.add(0);

        // todo only for testing, remove later
        LockoutLogger.log("Generated board with " + info.goalCount() + " goals and difficulty " + info.difficulty());
        LockoutLogger.log("difficulty 1 goal count: " + difficulties.get(0).toString());
        LockoutLogger.log("difficulty 2 goal count: " + difficulties.get(1).toString());
        LockoutLogger.log("difficulty 3 goal count: " + difficulties.get(2).toString());
        LockoutLogger.log("difficulty 4 goal count: " + difficulties.get(3).toString());
        LockoutLogger.log("difficulty 5 goal count: " + difficulties.get(4).toString());



        return new GoalBoardUpdatePacket( goalPackets, -1,scores );
    }
    
    
    
    
    
    
    
}
