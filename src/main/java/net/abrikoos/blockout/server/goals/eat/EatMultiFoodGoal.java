package net.abrikoos.blockout.server.goals.eat;

import net.abrikoos.blockout.server.goals.advancement.MultiCriterionAdvancementGoal;
import net.minecraft.util.Identifier;

public class EatMultiFoodGoal extends MultiCriterionAdvancementGoal {


    public EatMultiFoodGoal(int id, int count) {
        super(id, Identifier.of("minecraft","husbandry/balanced_diet"), count);
    }
}
