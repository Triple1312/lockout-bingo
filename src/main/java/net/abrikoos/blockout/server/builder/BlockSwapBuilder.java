package net.abrikoos.blockout.server.builder;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;

import java.util.*;

public class BlockSwapBuilder {

    public static Map<Block, Block> swappedBlocks = new HashMap<>();

    public static void swapBlocks() {
        List<Block> blocks = new ArrayList<>();
        Registries.BLOCK.forEach((block) -> {
            if (!(block instanceof AirBlock)) {
                blocks.add(block);
            }
        });
        Collections.shuffle(blocks);
        if (blocks.size() % 2 != 0) {
            swappedBlocks.put(blocks.getFirst(), blocks.getFirst());
            blocks.removeFirst();
        }

        while (!blocks.isEmpty()) {
            Block first = blocks.getFirst();
            blocks.removeFirst();
            Block second = blocks.getFirst();
            blocks.removeFirst();
            swappedBlocks.put(first, second);
            swappedBlocks.put(second, first);
        }
    }

    public static boolean blockSwapEnabled() {
        return swappedBlocks.isEmpty();
    }

    public static void resetBlocks() {
        swappedBlocks.clear();
    }

    public static Block getSwappedBlock(Block block) {
        return swappedBlocks.get(block);
    }

}
