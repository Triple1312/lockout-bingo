package net.abrikoos.lockout_bingo.server.builder;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.networkv2.game.GameStartPacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalInfoPacket;
import net.abrikoos.lockout_bingo.networkv2.game.StartGameRequestPacket;
import net.abrikoos.lockout_bingo.server.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalTag;
import net.abrikoos.lockout_bingo.util.BlockoutList;

import java.util.ArrayList;
import java.util.List;

//public class LockoutFinalBuilder extends LockoutRandBuilder {
//
//    StartGameRequestPacket info;
//
//    final int max_redstone = 2;
//    final int max_silk_touch = 1;
//    final int max_die = 3;
//    final int max_dont = 1;
//    final int max_breed = 3;
//    final int max_obtain = 5;
//    final int max_kill = 4;
//    final int max_eat = 5;
//    final int max_effect = 2;
//    final int max_tools = 2;
//    final int max_brew = 2;
//    final int max_armor = 2;
//    final int max_movement = 1;
//    final int max_ride = 1;
//    final int max_lvl = 2;
//    final int max_use = 2;
//    final int max_tame = 2;
//    final int max_biomes = 1;
//    final int max_wool = 1;
//
//
//    public LockoutFinalBuilder(StartGameRequestPacket info) {
//        this.info = info;
//    }
//
//    public GameStartPacket generateLockoutBoard() {
//        items = new ArrayList<>();
//        items.addAll(GoalItemRegistry.getInstance().items);
//        for (String goaltype : info.disabledGoals()) {
//            switch (goaltype) {
//                case "end":
//                    removeGoalsWithTag(LockoutGoalTag.end);
//                    break;
//                case "nether":
//                    removeGoalsWithTag(LockoutGoalTag.nether);
//                    break;
//                case "redstone":
//                    removeGoalsWithTag(LockoutGoalTag.redstone);
//                    break;
//                case "die":
//                    removeGoalsWithTag(LockoutGoalTag.die);
//                    break;
//                case "dont":
//                    removeGoalsWithTag(LockoutGoalTag.dont);
//                    break;
//                case "biomes":
//                    removeGoalsWithTag(LockoutGoalTag.biomes);
//                    break;
//                case "advancement":
//                    removeGoalsWithTag(LockoutGoalTag.advancement);
//                    break;
//                case "eat":
//                    removeGoalsWithTag(LockoutGoalTag.eat);
//                    break;
//                case "kill":
//                    removeGoalsWithTag(LockoutGoalTag.kill);
//                    break;
//                case "movement":
//                    removeGoalsWithTag(LockoutGoalTag.movement);
//                    break;
//                case "breed":
//                    removeGoalsWithTag(LockoutGoalTag.breed);
//                    break;
//                case "obtain":
//                    removeGoalsWithTag(LockoutGoalTag.obtain);
//                    break;
//                case "armor":
//                    removeGoalsWithTag(LockoutGoalTag.armor);
//                    break;
//                case "tools":
//                    removeGoalsWithTag(LockoutGoalTag.tools);
//                    break;
//                case "ride":
//                    removeGoalsWithTag(LockoutGoalTag.ride);
//                    break;
//            }
//        }
//        // todo I'm ignoring difficulty for now
//        // todo I'm ignoring goal count for now
//
//        List<GoalInfoPacket> goals = new ArrayList<>();
//        for (int i = 0; i < info.goalCount(); i++) {
//            int randomIndex = (int) (Math.random() * items.size());
//            GoalListItem goal = items.get(randomIndex);
//            items.remove(randomIndex);
//            goals.add(new GoalInfoPacket(goal.id, i, "00000000-0000-0000-000000000000", "00000000-0000-0000-000000000000", 0));
//
//
//
//        }
//
//
//        GameStartPacket info = new GameStartPacket("Lockout", new GoalListItem[25], true, System.currentTimeMillis() );
//        for (int i = 0; i < 25; i++) {
//            int randomIndex = (int) (Math.random() * items.size());
//            GoalListItem goal = items.get(randomIndex);
//            LockoutLogger.log("constructing goal " + goal.name);
//            boolean same = false;
//            List<GoalListItem> goals = new ArrayList<>();
//            for (int j = 0; j < i; j++) {
//                goals.add(info.goals[j]);
//                if (info.goals[j].id.equals(goal.id)) {
//                    same = true;
//                    break;
//                }
//            }
//            goals.add(goal);
//            if (!validateAddGoal(goals)) {
//                i--;
//                continue;
//            }
//            if (same) {
//                i--;
//            }
//            else {
//                info.goals[i] = goal;
//            }
//        }
//
//
//        return info;
//    }
//
//
//
//}
