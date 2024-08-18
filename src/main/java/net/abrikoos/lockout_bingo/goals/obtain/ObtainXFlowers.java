package net.abrikoos.lockout_bingo.goals.obtain;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;

public class ObtainXFlowers extends ObtainXofSetItemsGoal{
    public ObtainXFlowers(int id, int count) {
        super(id, List.of( Items.DANDELION, Items.POPPY, Items.BLUE_ORCHID, Items.ALLIUM, Items.AZURE_BLUET, Items.ORANGE_TULIP, Items.PINK_TULIP, Items.RED_TULIP, Items.WHITE_TULIP, Items.OXEYE_DAISY, Items.CORNFLOWER, Items.LILY_OF_THE_VALLEY, Items.TORCHFLOWER, Items.WITHER_ROSE, Items.LILAC, Items.ROSE_BUSH, Items.SUNFLOWER, Items.PEONY ), count);
    }
}