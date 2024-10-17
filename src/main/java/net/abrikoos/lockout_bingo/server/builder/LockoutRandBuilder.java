package net.abrikoos.lockout_bingo.server.builder;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.network.game.LockoutStartGameInfo;
import net.abrikoos.lockout_bingo.server.goals.*;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;

import java.util.ArrayList;
import java.util.List;

public class LockoutRandBuilder extends LockoutGoalBuilder {

    protected static List<GoalListItem> items = new ArrayList<>();

    public BlackoutStartGameInfo generateBoard() {
        items = new ArrayList<>();
        items.addAll(GoalItemRegistry.getInstance().items);

        BlackoutStartGameInfo info = new BlackoutStartGameInfo(new GoalListItem[25], new ArrayList<>());
        for (int i = 0; i < 25; i++) {
            int randomIndex = (int) (Math.random() * items.size());
            GoalListItem goal = items.get(randomIndex);
            boolean same = false;
            List<GoalListItem> goals = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                goals.add(info.goals[j]);
                if (info.goals[j].id.equals(goal.id)) {
                    same = true;
                    LockoutLogger.log("goal removed because double");
                    break;
                }
            }
            goals.add(goal);
            if (!validateAddGoal(goals)) {
                i--;
                LockoutLogger.log("goal deleted because of validation");
                continue;
            }
            if (same) {
                i--;
            }
            else {
                info.goals[i] = goal;
            }
        }
        return info;
    }

    public List<LockoutGoal> buildBoard(List<GoalListItem> goals) {
        List<LockoutGoal> ret = new ArrayList<>();
        for (int i = 0; i < 25; i = ret.size()) {
            try {
                LockoutGoal goal = GoalFactory.buildGoal(goals.get(i).id, i);
                ret.add(goal);
            }
            catch (Exception e) {
                e.printStackTrace();
                LockoutLogger.log("Building goal " + goals.get(i).name + " failed");
            }
        }
        return ret;
    }

    protected static void removeGoalsWithTag(LockoutGoalTag tag) {
        items.removeIf(goal -> goal.tags.contains(tag));
    }

    protected static boolean validateAddGoal(List<GoalListItem> goals) {
        final int max_redstone = 2;
        final int max_silk_touch = 1;
        final int max_die = 3;
        final int max_dont = 1;
        final int max_breed = 3;
        final int max_obtain = 5;
        final int max_kill = 4;
        final int max_eat = 5;
        final int max_effect = 2;
        final int max_tools = 2;
        final int max_brew = 2;
        final int max_armor = 2;
        final int max_movement = 1;
        final int max_ride = 1;
        final int max_lvl = 2;
        final int max_use = 2;
        final int max_tame = 2;
        final int max_biomes = 1;
        final int max_wool = 1;


        int redstone_count = 0;
        int silk_touch_count = 0;
        int die_count = 0;
        int dont_count = 0;
        int breed_count = 0;
        int obtain_count = 0;
        int kill_count = 0;
        int eat_count = 0;
        int effect_count = 0;
        int tools_count = 0;
        int brew_count = 0;
        int armor_count = 0;
        int movement_count = 0;
        int ride_count = 0;
        int lvl_count = 0;
        int use_count = 0;
        int tame_count = 0;
        int biomes_count = 0;
        int wool_count = 0;

        for (GoalListItem goal : goals) {
            for (LockoutGoalTag tag : goal.tags) {
                switch (tag) {
                    case redstone:
                        redstone_count++;
                        break;
                    case die:
                        die_count++;
                        break;
                    case dont:
                        dont_count++;
                        break;
                    case eat:
                        eat_count++;
                        break;
                    case breed:
                        breed_count++;
                        break;
                    case kill:
                        kill_count++;
                        break;
                    case obtain:
                        obtain_count++;
                        break;
                    case silk_touch:
                        silk_touch_count++;
                        break;
                    case movement:
                        movement_count++;
                        break;
                    case armor:
                        armor_count++;
                        break;
                    case tools:
                        tools_count++;
                        break;
                    case ride:
                        ride_count++;
                        break;
                    case tame:
                        tame_count++;
                        break;
                    case use:
                        use_count++;
                        break;
                    case effect:
                        effect_count++;
                        break;
                    case brew:
                        brew_count++;
                        break;
                    case lvl:
                        lvl_count++;
                        break;
                    case biomes:
                        biomes_count++;
                        break;
                    case wool:
                        wool_count++;
                        break;
                    default:
                        break;
                }
            }
        }
        if (redstone_count > max_redstone) return false;
        else if (silk_touch_count > max_silk_touch) {
            removeGoalsWithTag(LockoutGoalTag.silk_touch); return false;
        }
        else if (die_count > max_die) {removeGoalsWithTag(LockoutGoalTag.die); return false;}
        else if (dont_count > max_dont) {removeGoalsWithTag(LockoutGoalTag.dont); return false;}
        else if (breed_count > max_breed) {removeGoalsWithTag(LockoutGoalTag.breed); return false;}
        else if (obtain_count > max_obtain) {removeGoalsWithTag(LockoutGoalTag.obtain); return false;}
        else if (kill_count > max_kill) {removeGoalsWithTag(LockoutGoalTag.kill); return false;}
        else if (eat_count > max_eat) {removeGoalsWithTag(LockoutGoalTag.eat); return false;}
        else if (effect_count > max_effect) {removeGoalsWithTag(LockoutGoalTag.effect); return false;}
        else if (tools_count > max_tools) {removeGoalsWithTag(LockoutGoalTag.tools); return false;}
        else if (brew_count > max_brew) {removeGoalsWithTag(LockoutGoalTag.brew); return false;}
        else if (armor_count > max_armor) {removeGoalsWithTag(LockoutGoalTag.armor); return false;}
        else if (movement_count > max_movement) {removeGoalsWithTag(LockoutGoalTag.movement); return false;}
        else if (ride_count > max_ride) {removeGoalsWithTag(LockoutGoalTag.ride); return false;}
        else if (lvl_count > max_lvl) {removeGoalsWithTag(LockoutGoalTag.lvl); return false;}
        else if (use_count > max_use) {removeGoalsWithTag(LockoutGoalTag.use); return false;}
        else if (tame_count > max_tame) {removeGoalsWithTag(LockoutGoalTag.tame); return false;}
        else if (biomes_count > max_biomes) {removeGoalsWithTag(LockoutGoalTag.biomes); return false;}
        else if (wool_count > max_wool) {removeGoalsWithTag(LockoutGoalTag.wool); return false;}
        return true;

    }
}






















