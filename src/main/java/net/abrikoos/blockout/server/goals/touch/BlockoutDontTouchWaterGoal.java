package net.abrikoos.blockout.server.goals.touch;

import net.abrikoos.blockout.server.goals.advancement.AdvancementGoal;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.util.Identifier;

public class BlockoutDontTouchWaterGoal extends AdvancementGoal {


    protected BlockoutDontTouchWaterGoal(int id) {
        super(id, null, "enemy", "in_water");
    }

    @Override
    protected boolean validateProgress(AdvancementProgress progress, String criterionName) {
        return criterionName.equals("in_water");
    }
}
