package net.abrikoos.lockout_bingo.goals.eat;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.goals.advancement.AdvancementGoal;
import net.abrikoos.lockout_bingo.listeners.AdvancementListener;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class EatFoodGoal extends AdvancementGoal {

    public EatFoodGoal(int id, Item foodItem) {
        super(id, Identifier.of("minecraft","husbandry/balanced_diet"), "ally", foodItem.getTranslationKey().split("\\.")[2]);
    }

}
