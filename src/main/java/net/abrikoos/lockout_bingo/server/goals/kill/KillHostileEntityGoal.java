package net.abrikoos.lockout_bingo.server.goals.kill;

import net.abrikoos.lockout_bingo.server.goals.advancement.AdvancementGoal;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class KillHostileEntityGoal extends AdvancementGoal {

    public KillHostileEntityGoal(int id, String criterionName) {
        super(id, Identifier.of("minecraft", "adventure/kill_all_mobs"), "ally", criterionName);
    }

//    @Override
//    protected boolean validateProgress(AdvancementProgress progress, String criterionName) { // todo does not work I thinkk
//        if (Objects.equals(hostmob, criterionName)) {
//            return true;
//        }
//        for ( String criteria : progress.getObtainedCriteria()) {
//            if (criteria.equals(this.hostmob)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
