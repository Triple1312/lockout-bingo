package net.abrikoos.lockout_bingo.client.gui.goalExplanations;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class DrawCraftingGrid {

    int x;
    int y;
    List<ItemStack> stacks;
    ItemStack result;


    DrawCraftingGrid(int x, int y, List<Item> stacks, Item result) {
        this.x = x;
        this.y = y;
        this.stacks = stacks.stream().map(item -> item == null ? null : item.getDefaultStack()).toList();
        this.result = result == null? null : result.getDefaultStack();
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

        int arrowX = this.x + 3 * 18 + 4; // arrow after 3 slots + some padding
        int arrowY = this.y + 18; // center vertically to middle row
        ctx.drawTexture(
                Identifier.of("minecraft", "textures/gui/container/crafting_table.png"),
                arrowX, arrowY, // position
                176, 23, // u,v in texture
                24, 17  // width,height
        );

        int resultX = arrowX + 24 + 4; // after arrow + some padding
        int resultY = this.y + 18; // center vertically to middle row
        ctx.drawTexture(
                Identifier.of("minecraft", "textures/gui/container/crafting_table.png"),
                resultX, resultY, // position
                95, 17, // u,v in texture
                18, 18  // width,height
        );
        ctx.drawItem(result, resultX + 1, resultY + 1); // +1 to center in slot

    }
}
