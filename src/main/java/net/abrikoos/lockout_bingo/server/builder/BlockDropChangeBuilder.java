package net.abrikoos.lockout_bingo.server.builder;

import net.abrikoos.lockout_bingo.mixin.accessors.CombinedEntryMixin;
import net.abrikoos.lockout_bingo.mixin.accessors.ItemEntryMixin;
import net.abrikoos.lockout_bingo.mixin.accessors.LootTableAccessor;
import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;

import java.util.*;

public class BlockDropChangeBuilder {





    public static Map<Item, Item> blockDropChanges = new HashMap<>();


    public static void shuffleBlockDrops() {
        HashSet<Item> droppableItems = new HashSet<>();
        MinecraftServer server = GameState.server;


        for (Block block : Registries.BLOCK) {
            RegistryKey<LootTable> lootTableId = block.getLootTableKey();
            LootTable lootTable = server.getReloadableRegistries().getLootTable(lootTableId);
            List<LootPool> pools = ((LootTableAccessor) lootTable).getPools();
            for (LootPool pool : pools) {
                for (LootPoolEntry entry : pool.entries) {
                    if (entry instanceof ItemEntry) {
                        droppableItems.add(((ItemEntryMixin) entry).getItem().value());
                    }
                    else if (entry instanceof AlternativeEntry) {

                        for (LootPoolEntry alternativeEntry : ((CombinedEntryMixin) entry).getChildren()) {
                            if (alternativeEntry instanceof ItemEntry) {
                                droppableItems.add(((ItemEntryMixin) alternativeEntry).getItem().value());
                            }
                        }
                    }
                    else {
                        System.out.println("Not an ItemEntry");
                    }
                }
            }
        }

        List<Item> droppableItemsList = new ArrayList<>(droppableItems);
        List<Item> droppableItemsList2 = new ArrayList<>(droppableItemsList);
        Collections.shuffle(droppableItemsList2);
        for (int i = 0; i < droppableItemsList.size(); i++) {
            BlockDropChangeBuilder.blockDropChanges.put(droppableItemsList.get(i), droppableItemsList2.get(i));
        }
        System.out.println("Shuffled block drops");
    }

    public void resetHashMap() {
        blockDropChanges.clear();
    }

}
