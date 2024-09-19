package net.abrikoos.lockout_bingo.server.goals.obtain;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;

public class ObtainToolSetGoal extends ObtainXofSetItemsGoal{


    public ObtainToolSetGoal(int id, String material) {
        super(id, List.of(), 5);
        switch (material){
            case "wooden":
                this.items = List.of(Items.WOODEN_AXE, Items.WOODEN_HOE, Items.WOODEN_PICKAXE, Items.WOODEN_SHOVEL, Items.WOODEN_SWORD);
                break;
            case "stone":
                this.items = List.of(Items.STONE_AXE, Items.STONE_HOE, Items.STONE_PICKAXE, Items.STONE_SHOVEL, Items.STONE_SWORD);
                break;
            case "iron":
                this.items = List.of(Items.IRON_AXE, Items.IRON_HOE, Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_SWORD);
                break;
            case "golden":
                this.items = List.of(Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_SWORD);
                break;
            case "diamond":
                this.items = List.of(Items.DIAMOND_AXE, Items.DIAMOND_HOE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SHOVEL, Items.DIAMOND_SWORD);
                break;
            case "netherite":
                this.items = List.of(Items.NETHERITE_AXE, Items.NETHERITE_HOE, Items.NETHERITE_PICKAXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_SWORD);
                break;
        }

    }
}
