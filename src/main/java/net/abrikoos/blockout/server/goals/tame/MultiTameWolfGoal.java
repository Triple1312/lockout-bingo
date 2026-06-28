package net.abrikoos.blockout.server.goals.tame;

import net.abrikoos.blockout.server.goals.advancement.MultiCriterionAdvancementGoal;
import net.minecraft.util.Identifier;

public class MultiTameWolfGoal extends MultiCriterionAdvancementGoal {
    protected MultiTameWolfGoal(int id,  int count) {
        super(id, Identifier.of("minecraft:","husbandry/whole_pack"), count);
    }
}
