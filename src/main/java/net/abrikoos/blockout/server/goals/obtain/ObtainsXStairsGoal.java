package net.abrikoos.blockout.server.goals.obtain;

import net.abrikoos.blockout.registries.BlockGroups;
import net.abrikoos.blockout.registries.BlockRegistry;
import net.minecraft.block.Block;

import java.util.stream.Collectors;

public class ObtainsXStairsGoal extends ObtainXofSetItemsGoal {
    public ObtainsXStairsGoal(int id, int count) {
        super(id, BlockRegistry.get(BlockGroups.STAIRS).stream()
                .map(Block::asItem)
                .collect(Collectors.toList()), count);
    }
}
