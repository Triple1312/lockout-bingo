package net.abrikoos.lockout_bingo.server.goals.kill;

import net.abrikoos.lockout_bingo.server.goals.advancement.MultiCriterionAdvancementGoal;
import net.minecraft.util.Identifier;

public class MultiKillHostilesGoal extends MultiCriterionAdvancementGoal {

    public MultiKillHostilesGoal(int id, int count) {
        super(id, Identifier.of("minecraft","adventure/kill_all_mobs"), count);
    }
}
