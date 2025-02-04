package net.abrikoos.lockout_bingo.server.builder;

import net.abrikoos.lockout_bingo.networkv2.game.GameStartPacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalBoardUpdatePacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalInfoPacket;
import net.abrikoos.lockout_bingo.networkv2.game.StartGameRequestPacket;
import net.abrikoos.lockout_bingo.server.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalTag;

import java.util.ArrayList;

public class ReworkedBuilder {

    StartGameRequestPacket info;

    public GoalBoardUpdatePacket packet;

    ArrayList<GoalListItem> items = new ArrayList<>(GoalItemRegistry.getInstance().items);

    int left_redstone = 2;
    int left_silk_touch = 1;
    int left_die = 3;
    int left_dont = 1;
    int left_breed = 3;
    int left_obtain = 5;
    int left_kill = 4;
    int left_eat = 5;
    int left_effect = 2;
    int left_tools = 2;
    int left_brew = 2;
    int left_armor = 2;
    int left_movement = 1;
    int left_ride = 1;
    int left_lvl = 2;
    int left_use = 2;
    int left_tame = 2;
    int left_biomes = 1;
    int left_wool = 1;
    int left_end = 3;
    int left_nether = 12;

    public ReworkedBuilder(StartGameRequestPacket info){
        this.info = info;
        this.packet = generateLockoutBoard();
    }


    protected void removeGoalsWithTag(LockoutGoalTag tag) {
        this.items.removeIf(goal -> goal.tags.contains(tag));
    }


    private GoalListItem getRandomGoal() {
        int randomIndex = (int) (Math.random() * items.size());
        GoalListItem goal = items.get(randomIndex);

        for (int i = 0; i < goal.tags.size(); i++) {
            switch (goal.tags.get(i)) {
                case end:
                    left_end--;
                    if (left_end <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.end);
                    }
                    break;
                case nether:
                    left_nether--;
                    if (left_nether <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.nether);
                    }
                    break;
                case redstone:
                    left_redstone--;
                    if (left_redstone <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.redstone);
                    }
                    break;
                case silk_touch:
                    left_silk_touch--;
                    if (left_silk_touch <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.silk_touch);
                    }
                    break;
                case die:
                    left_die--;
                    if (left_die <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.die);
                    }
                    break;
                case dont:
                    left_dont--;
                    if (left_dont <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.dont);
                    }
                    break;
                case breed:
                    left_breed--;
                    if (left_breed <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.breed);
                    }
                    break;
                case obtain:
                    left_obtain--;
                    if (left_obtain <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.obtain);
                    }
                    break;
                case kill:
                    left_kill--;
                    if (left_kill <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.kill);
                    }
                    break;
                case eat:
                    left_eat--;
                    if (left_eat <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.eat);
                    }
                    break;
                case effect:
                    left_effect--;
                    if (left_effect <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.effect);
                    }
                    break;
                case tools:
                    left_tools--;
                    if (left_tools <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.tools);
                    }
                    break;
                case brew:
                    left_brew--;
                    if (left_brew <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.brew);
                    }
                    break;
                case armor:
                    left_armor--;
                    if (left_armor <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.armor);
                    }
                    break;
                case movement:
                    left_movement--;
                    if (left_movement <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.movement);
                    }
                    break;
                case ride:
                    left_ride--;
                    if (left_ride <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.ride);
                    }
                    break;
                case lvl:
                    left_lvl--;
                    if (left_lvl <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.lvl);
                    }
                    break;
                case use:
                    left_use--;
                    if (left_use <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.use);
                    }
                    break;
                case tame:
                    left_tame--;
                    if (left_tame <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.tame);
                    }
                    break;
                case biomes:
                    left_biomes--;
                    if (left_biomes <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.biomes);
                    }
                    break;
                case wool:
                    left_wool--;
                    if (left_wool <= 0) {
                        removeGoalsWithTag(LockoutGoalTag.wool);
                    }
                    break;
            }
        }
        return goal;
    }


    private GoalBoardUpdatePacket generateLockoutBoard(){

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


        ArrayList<GoalInfoPacket> goalPackets = new ArrayList<>();

        for (int i = 0; i < info.goalCount(); i++) {
            GoalListItem goal = getRandomGoal();
            goalPackets.add(new GoalInfoPacket(goal.name, i, "00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000", 0));
        }

        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(0);
        scores.add(0);

        return new GoalBoardUpdatePacket( goalPackets, -1,scores );
    }


}
