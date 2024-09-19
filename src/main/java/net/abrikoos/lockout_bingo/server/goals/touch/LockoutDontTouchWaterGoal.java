package net.abrikoos.lockout_bingo.server.goals.touch;

import net.abrikoos.lockout_bingo.server.goals.advancement.AdvancementGoal;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.util.Identifier;

public class LockoutDontTouchWaterGoal extends AdvancementGoal {


    protected LockoutDontTouchWaterGoal(int id) {
        super(id, null, "enemy", "in_water");
    }

    @Override
    protected boolean validateProgress(AdvancementProgress progress, String criterionName) {
        return criterionName.equals("in_water");
    }
}
