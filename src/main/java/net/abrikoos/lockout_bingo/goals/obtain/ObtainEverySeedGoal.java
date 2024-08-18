package net.abrikoos.lockout_bingo.goals.obtain;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;

public class ObtainEverySeedGoal extends ObtainMultiItemGoal{
    public ObtainEverySeedGoal(int id) {
        super(id, List.of(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS));
    }
}
