package net.abrikoos.lockout_bingo.goals.tame;

import net.abrikoos.lockout_bingo.goals.advancement.MultiAdvancementGoal;
import net.minecraft.util.Identifier;

public class MultiTameWolfGoal extends MultiAdvancementGoal {
    protected MultiTameWolfGoal(int id,  int count) {
        super(id, Identifier.of("minecraft:","husbandry/whole_pack"), count);
    }
}
