package net.abrikoos.lockout_bingo.server.goals.eat;

import net.abrikoos.lockout_bingo.server.goals.advancement.MultiAdvancementGoal;
import net.minecraft.util.Identifier;

public class EatMultiFoodGoal extends MultiAdvancementGoal {


    public EatMultiFoodGoal(int id, int count) {
        super(id, Identifier.of("minecraft","husbandry/balanced_diet"), count);
    }
}
