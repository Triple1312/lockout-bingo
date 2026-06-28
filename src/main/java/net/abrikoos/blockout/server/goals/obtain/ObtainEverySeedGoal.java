package net.abrikoos.blockout.server.goals.obtain;

import net.abrikoos.blockout.registries.ItemGroups;
import net.abrikoos.blockout.registries.ItemRegistry;

public class ObtainEverySeedGoal extends ObtainMultiItemGoal {
    public ObtainEverySeedGoal(int id) {
        super(id, ItemRegistry.get(ItemGroups.SEEDS));
    }
}
