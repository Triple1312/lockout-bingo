package net.abrikoos.lockout_bingo.server.goals.breed;

import net.abrikoos.lockout_bingo.server.goals.advancement.AdvancementGoal;
import net.abrikoos.lockout_bingo.server.goals.advancement.MultiAdvancementGoal;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.util.Identifier;

public class BreedMultiAnimalsGoal extends MultiAdvancementGoal {

    public BreedMultiAnimalsGoal(int id, int count) {
        super(id, Identifier.of("minecraft", "husbandry/breed_all_animals"), count);
    }
}
