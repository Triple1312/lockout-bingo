package net.abrikoos.lockout_bingo.client.gui.goalExplanations;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class DrawCraftingGrid {

    int x;
    int y;
    List<ItemStack> stacks;


    DrawCraftingGrid(int x, int y, List<ItemStack> stacks) {
        this.x = x;
        this.y = y;
        this.stacks = stacks;
    }


    public void render(DrawContext ctx) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int slotX = this.x + col * 18; // 18px spacing like vanilla
                int slotY = this.y + row * 18;

                ctx.drawTexture(
                        Identifier.of("minecraft", "textures/gui/container/crafting_table.png"),
                        slotX, slotY, // position
                        7, 17, // u,v in texture
                        18, 18  // width,height
                );
                if (stacks.get(row * 3 + col) != null){
                    ctx.drawItem(stacks.get(row * 3 + col), slotX + 1, slotY + 1); // +1 to center in slot
                }
            }
        }
    }
}
