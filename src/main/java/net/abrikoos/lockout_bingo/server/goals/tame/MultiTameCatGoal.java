package net.abrikoos.lockout_bingo.server.goals.tame;

import net.abrikoos.lockout_bingo.server.goals.advancement.MultiCriterionAdvancementGoal;
import net.minecraft.util.Identifier;

public class MultiTameCatGoal extends MultiCriterionAdvancementGoal {
    protected MultiTameCatGoal(int id,  int count) {
        super(id, Identifier.of("minecraft:","husbandry/complete_catalogue"), count);
    }
}
