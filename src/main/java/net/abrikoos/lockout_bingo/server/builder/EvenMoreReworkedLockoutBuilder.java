package net.abrikoos.lockout_bingo.server.builder;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.networkv2.game.GoalBoardUpdatePacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalInfoPacket;
import net.abrikoos.lockout_bingo.networkv2.game.StartGameRequestPacket;
import net.abrikoos.lockout_bingo.server.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.server.goals.GoalListItemV2;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalTag;
import net.minecraft.entity.passive.VillagerEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class EvenMoreReworkedLockoutBuilder {

    StartGameRequestPacket info;

    public GoalBoardUpdatePacket packet;

    ArrayList<GoalListItemV2> items_diff1 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 1));
    ArrayList<GoalListItemV2> items_diff2 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 2));
    ArrayList<GoalListItemV2> items_diff3 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 3));
    ArrayList<GoalListItemV2> items_diff4 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 4));
    ArrayList<GoalListItemV2> items_diff5 = new ArrayList<>(GoalItemRegistry.getInstance().items.where(goal -> goal.difficulty == 5));

    float max_redstone = 2;
    float max_silk_touch = 1;
    float max_die = 3;
    float max_dont = 1;
    float max_breed = 3;
    float max_obtain = 5;
    float max_kill = 4;
    float max_eat = 5;
    float max_effect = 2;
    float max_tools = 2;
    float max_brew = 2;
    float max_armor = 2;
    float max_movement = 1;
    float max_ride = 1;
    float max_lvl = 2;
    float max_use = 2;
    float max_tame = 2;
    float max_biomes = 1;
    float max_wool = 1;
    float max_end = 6;
    float max_nether = 15;
    
    public EvenMoreReworkedLockoutBuilder(StartGameRequestPacket info) {
        this.info = info;
        setup_max(info);
        this.packet = generateLockoutBoard();
    }

    public void setup_max(StartGameRequestPacket packet){
        int goalCount = packet.goalCount();

        max_redstone = 2f/ 25 * goalCount;
        max_silk_touch = 1f/ 25 * goalCount;
        max_die = 3f/ 25 * goalCount;
        max_dont = 1f/ 25 * goalCount;
        max_breed = 3f/ 25 * goalCount;
        max_obtain = 5f/ 25 * goalCount;
        max_kill = 4f/ 25 * goalCount;
        max_eat = 5f/ 25 * goalCount;
        max_effect = 2f/ 25 * goalCount;
        max_tools = 2f/ 25 * goalCount;
        max_brew = 2f/ 25 * goalCount;
        max_armor = 2f/ 25 * goalCount;
        max_movement = 1f/ 25 * goalCount;
        max_ride = 1f/ 25 * goalCount;
        max_lvl = 2f/ 25 * goalCount;
        max_use = 2f/ 25 * goalCount;
        max_tame = 2f/ 25 * goalCount;
        max_biomes = 1f/ 25 * goalCount;
        max_wool = 1f/ 25 * goalCount;
        max_end = 6f/ 25 * goalCount;
        max_nether = 15f/ 25 * goalCount;
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
    
    private GoalListItemV2 getRandomGoal(int difficulty) {
        ArrayList<GoalListItemV2> items;
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
        GoalListItemV2 goal = items.get(randomIndex);

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
        float sigma = 0.8f + info.difficulty() * 0.2f ; // todo choose good value
        // todo diff uniform
        // todo diff random

        for (int i = 0; i < info.goalCount(); i++) {


            int difficulty = (int) Math.round(rand.nextGaussian() * sigma + mu);
            if (difficulty < 1) difficulty = 1;
            if (difficulty > 5) difficulty = 5;


            GoalListItemV2 goal = getRandomGoal(difficulty);
            if (goal == null) {
                boolean allEmpty = items_diff1.isEmpty() && items_diff2.isEmpty() && items_diff3.isEmpty() && items_diff4.isEmpty() && items_diff5.isEmpty();
                if (allEmpty) {
                    LockoutLogger.log("Ran out of goals entirely, generated " + i + " out of " + info.goalCount() + " goals");
                    break;
                }
                LockoutLogger.log("Not enough goals to generate board, generated " + i + " out of " + info.goalCount() + " goals for difficulty " + difficulty);
                i--;
                sigma += 0.05f;
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
