package net.abrikoos.lockout_bingo.builder;

import net.abrikoos.lockout_bingo.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.goals.GoalListItem;
import net.abrikoos.lockout_bingo.goals.LockoutGoalTag;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;

import java.util.ArrayList;
import java.util.List;

public class LockoutFinalBuilder extends LockoutRandBuilder {

    public boolean end;
    public boolean nether;
    public int difficulty;

    public LockoutFinalBuilder(int difficulty, boolean end, boolean nether) {
        this.difficulty = difficulty;
        this.end = end;
        this.nether = nether;
    }

    public BlackoutStartGameInfo generateBoard() {
        items = new ArrayList<>();
        items.addAll(GoalItemRegistry.getInstance().items);

        if (!end) {
            removeGoalsWithTag(LockoutGoalTag.end);
        }
        if (!nether) {
            removeGoalsWithTag(LockoutGoalTag.nether);
        }

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
                    break;
                }
            }
            goals.add(goal);
            if (!validateAddGoal(goals)) {
                i--;
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



}
