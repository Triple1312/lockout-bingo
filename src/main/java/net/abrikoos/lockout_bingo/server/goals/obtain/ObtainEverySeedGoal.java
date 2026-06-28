package net.abrikoos.lockout_bingo.server.goals.obtain;

import net.abrikoos.lockout_bingo.registries.ItemGroups;
import net.abrikoos.lockout_bingo.registries.ItemRegistry;

public class ObtainEverySeedGoal extends ObtainMultiItemGoal {
    public ObtainEverySeedGoal(int id) {
        super(id, ItemRegistry.get(ItemGroups.SEEDS));
    }
}
