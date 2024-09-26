package net.abrikoos.lockout_bingo.server.goals.breed;

import net.abrikoos.lockout_bingo.server.goals.advancement.MultiCriterionAdvancementGoal;
import net.minecraft.util.Identifier;

public class BreedMultiAnimalsGoal extends MultiCriterionAdvancementGoal {

    public BreedMultiAnimalsGoal(int id, int count) {
        super(id, Identifier.of("minecraft", "husbandry/breed_all_animals"), count);
    }
}
