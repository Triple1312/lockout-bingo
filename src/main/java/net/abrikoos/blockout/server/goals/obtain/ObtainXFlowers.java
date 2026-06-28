package net.abrikoos.blockout.server.goals.obtain;

import net.abrikoos.blockout.registries.BlockGroups;
import net.abrikoos.blockout.registries.BlockRegistry;
import net.minecraft.block.Block;

import java.util.stream.Collectors;

public class ObtainXFlowers extends ObtainXofSetItemsGoal {
    public ObtainXFlowers(int id, int count) {
        super(id, BlockRegistry.get(BlockGroups.FLOWERS).stream()
                .map(Block::asItem)
                .collect(Collectors.toList()), count);
    }
}
