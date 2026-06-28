package net.abrikoos.blockout.client.gui.goalExplanations;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class DrawCraftingGrid implements RecipeWidget {

    // x and y kept for backwards-compatible constructor, but y is ignored — callers pass y at render time.
    int x;
    List<ItemStack> stacks;
    ItemStack result;


    DrawCraftingGrid(int x, int y, List<Item> stacks, Item result) {
        this.x = x;
        this.stacks = stacks.stream().map(item -> item == null ? null : item.getDefaultStack()).toList();
        this.result = result == null ? null : result.getDefaultStack();
    }


    @Override
    public int render(DrawContext ctx, int x, int y) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int slotX = x + col * 18;
                int slotY = y + row * 18;
                drawSlot(ctx, slotX, slotY);
                if (stacks.get(row * 3 + col) != null) {
                    ctx.drawItem(stacks.get(row * 3 + col), slotX + 1, slotY + 1);
                }
            }
        }

        int arrowX = x + 3 * 18 + 5;
        int arrowY = y + 18 + 5;
        ctx.drawText(MinecraftClient.getInstance().textRenderer, ">", arrowX, arrowY, 0xFFFFFF, false);

        int resultX = x + 3 * 18 + 20;
        int resultY = y + 18;
        drawSlot(ctx, resultX, resultY);
        if (result != null) {
            ctx.drawItem(result, resultX + 1, resultY + 1);
        }

        return 54; // 3 rows * 18px
    }

    private void drawSlot(DrawContext ctx, int x, int y) {
        ctx.fill(x, y, x + 18, y + 18, 0xFF373737);
        ctx.fill(x + 1, y + 1, x + 17, y + 17, 0xFF8B8B8B);
    }
}
