package net.abrikoos.blockout.client.gui.goalExplanations;

import net.minecraft.client.gui.DrawContext;

public interface RecipeWidget {
    // Returns the height in pixels consumed by this widget.
    int render(DrawContext ctx, int x, int y);
}
