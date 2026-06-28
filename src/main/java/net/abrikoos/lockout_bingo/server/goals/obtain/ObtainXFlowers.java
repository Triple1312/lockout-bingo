package net.abrikoos.lockout_bingo.server.goals.obtain;

import net.abrikoos.lockout_bingo.registries.BlockGroups;
import net.abrikoos.lockout_bingo.registries.BlockRegistry;
import net.minecraft.block.Block;

import java.util.stream.Collectors;

public class ObtainXFlowers extends ObtainXofSetItemsGoal {
    public ObtainXFlowers(int id, int count) {
        super(id, BlockRegistry.get(BlockGroups.FLOWERS).stream()
                .map(Block::asItem)
                .collect(Collectors.toList()), count);
    }
}
