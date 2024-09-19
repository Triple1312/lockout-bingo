package net.abrikoos.lockout_bingo.server.goals.eat;

import net.abrikoos.lockout_bingo.server.goals.advancement.AdvancementGoal;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class EatFoodGoal extends AdvancementGoal {

    public EatFoodGoal(int id, Item foodItem) {
        super(id, Identifier.of("minecraft","husbandry/balanced_diet"), "ally", foodItem.getTranslationKey().split("\\.")[2]);
    }

}
