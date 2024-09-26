package net.abrikoos.lockout_bingo.server.goals.tame;

import net.abrikoos.lockout_bingo.server.goals.advancement.MultiCriterionAdvancementGoal;
import net.minecraft.util.Identifier;

public class MultiTameWolfGoal extends MultiCriterionAdvancementGoal {
    protected MultiTameWolfGoal(int id,  int count) {
        super(id, Identifier.of("minecraft:","husbandry/whole_pack"), count);
    }
}
