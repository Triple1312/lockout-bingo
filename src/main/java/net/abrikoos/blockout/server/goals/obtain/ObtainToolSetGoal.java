package net.abrikoos.blockout.server.goals.obtain;

import net.abrikoos.blockout.registries.ItemGroups;
import net.abrikoos.blockout.registries.ItemRegistry;

public class ObtainToolSetGoal extends ObtainXofSetItemsGoal {
    public ObtainToolSetGoal(int id, String material) {
        super(id, ItemRegistry.get(switch (material) {
            case "wooden"    -> ItemGroups.WOODEN_TOOLS;
            case "stone"     -> ItemGroups.STONE_TOOLS;
            case "iron"      -> ItemGroups.IRON_TOOLS;
            case "golden"    -> ItemGroups.GOLDEN_TOOLS;
            case "diamond"   -> ItemGroups.DIAMOND_TOOLS;
            case "netherite" -> ItemGroups.NETHERITE_TOOLS;
            default -> throw new IllegalArgumentException("Unknown tool material: " + material);
        }), 5);
    }
}
