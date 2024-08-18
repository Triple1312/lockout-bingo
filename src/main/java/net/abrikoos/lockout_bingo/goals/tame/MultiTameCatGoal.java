package net.abrikoos.lockout_bingo.goals.tame;

import net.abrikoos.lockout_bingo.goals.advancement.MultiAdvancementGoal;
import net.minecraft.util.Identifier;

public class MultiTameCatGoal extends MultiAdvancementGoal {
    protected MultiTameCatGoal(int id,  int count) {
        super(id, Identifier.of("minecraft:","husbandry/complete_catalogue"), count);
    }
}
