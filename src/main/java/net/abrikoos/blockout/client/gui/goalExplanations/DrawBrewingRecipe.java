package net.abrikoos.blockout.client.gui.goalExplanations;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

public class DrawBrewingRecipe implements RecipeWidget {

    private final List<ItemStack> ingredients;
    private final List<String> resultNames;

    // ingredients: item added at each step
    // resultNames: name of the potion produced by each step (same size as ingredients)
    DrawBrewingRecipe(List<Item> ingredients, List<String> resultNames) {
        this.ingredients = ingredients.stream().map(Item::getDefaultStack).toList();
        this.resultNames = resultNames;
    }

    @Override
    public int render(DrawContext ctx, int x, int y) {
        var tr = MinecraftClient.getInstance().textRenderer;

        // Row 0: starting Water Bottle
        drawSlot(ctx, x, y);
        ctx.drawItem(new ItemStack(Items.GLASS_BOTTLE), x + 1, y + 1);
        ctx.drawText(tr, "Water Bottle", x + 22, y + 5, 0xF0F0F0, false);

        int rowY = y + 20;

        for (int i = 0; i < ingredients.size(); i++) {
            ctx.drawText(tr, "+", x + 1, rowY + 5, 0xFFFFFF, false);
            drawSlot(ctx, x + 10, rowY);
            ctx.drawItem(ingredients.get(i), x + 11, rowY + 1);
            ctx.drawText(tr, "→ " + resultNames.get(i), x + 32, rowY + 5, 0xF0F0F0, false);
            rowY += 20;
        }

        return 18 + ingredients.size() * 20;
    }

    private void drawSlot(DrawContext ctx, int x, int y) {
        ctx.fill(x, y, x + 18, y + 18, 0xFF373737);
        ctx.fill(x + 1, y + 1, x + 17, y + 17, 0xFF8B8B8B);
    }
}
