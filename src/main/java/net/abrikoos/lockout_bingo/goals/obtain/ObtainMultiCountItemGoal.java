package net.abrikoos.lockout_bingo.goals.obtain;

import net.minecraft.item.Item;

public class ObtainMultiCountItemGoal extends ObtainItemGoal{
    public ObtainMultiCountItemGoal(int id,Item item, int count) {
        super(id, item);
        this.count = count;
    }
}
