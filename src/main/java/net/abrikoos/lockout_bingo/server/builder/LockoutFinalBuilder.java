package net.abrikoos.lockout_bingo.server.builder;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.network.game.CreateLockoutPacket;
import net.abrikoos.lockout_bingo.network.game.LockoutStartGameInfo;
import net.abrikoos.lockout_bingo.server.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoalTag;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.SERVER)
public class LockoutFinalBuilder extends LockoutRandBuilder {

    CreateLockoutPacket info;

    public LockoutFinalBuilder(CreateLockoutPacket info) {
        this.info = info;
    }

    public LockoutStartGameInfo generateLockoutBoard() {
        items = new ArrayList<>();
        items.addAll(GoalItemRegistry.getInstance().items);
        for (int goaltype : info.disabledGoalTypes()) {
            switch (goaltype) {
                case 0:
                    removeGoalsWithTag(LockoutGoalTag.end);
                    break;
                case 1:
                    removeGoalsWithTag(LockoutGoalTag.nether);
                    break;
                case 2:
                    removeGoalsWithTag(LockoutGoalTag.redstone);
                    break;
                case 3:
                    removeGoalsWithTag(LockoutGoalTag.die);
                    break;
                case 4:
                    removeGoalsWithTag(LockoutGoalTag.dont);
                    break;
                case 5:
                    removeGoalsWithTag(LockoutGoalTag.biomes);
                    break;
                case 6:
                    removeGoalsWithTag(LockoutGoalTag.advancement);
                    break;
                case 7:
                    removeGoalsWithTag(LockoutGoalTag.eat);
                    break;
                case 8:
                    removeGoalsWithTag(LockoutGoalTag.kill);
                    break;
                case 9:
                    removeGoalsWithTag(LockoutGoalTag.movement);
                    break;
                case 10:
                    removeGoalsWithTag(LockoutGoalTag.breed);
                    break;
                case 11:
                    removeGoalsWithTag(LockoutGoalTag.obtain);
                    break;
                case 12:
                    removeGoalsWithTag(LockoutGoalTag.armor);
                    break;
                case 13:
                    removeGoalsWithTag(LockoutGoalTag.tools);
                    break;
                case 14:
                    removeGoalsWithTag(LockoutGoalTag.ride);
                    break;
            }
        }
        // todo I'm ignoring difficulty for now
        // todo I'm ignoring goal count for now
        LockoutStartGameInfo info = new LockoutStartGameInfo(new ArrayList<>(), new GoalListItem[25], true, System.currentTimeMillis() );
        for (int i = 0; i < 25; i++) {
            int randomIndex = (int) (Math.random() * items.size());
            GoalListItem goal = items.get(randomIndex);
            LockoutLogger.log("constructing goal " + goal.name);
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
