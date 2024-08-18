package net.abrikoos.lockout_bingo.goals.breed;

import net.abrikoos.lockout_bingo.goals.advancement.AdvancementGoal;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.util.Identifier;

public class BreedAnimalGoal extends AdvancementGoal {


    public BreedAnimalGoal(int id, String animal) {
        super(id, Identifier.of("minecraft", "husbandry/breed_all_animals"), "ally", animal); // idk why it is "breed an animal"
    }

//    @Override
//    protected boolean validateProgress(AdvancementProgress progress, String criterionName) {
//        for ( String criteria : progress.getObtainedCriteria()) {
//            if (criteria.equals(this.animal)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
