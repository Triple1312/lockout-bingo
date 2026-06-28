package net.abrikoos.blockout.client.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class BlockoutUtils {

    public static void drawCenteredText(DrawContext context, TextRenderer textRenderer, String text, int x, int y, int color, boolean shadow) {
        Text textComponent = Text.literal(text).withColor(color);
        int textWidth = textRenderer.getWidth(textComponent);
        int textHeight = textRenderer.fontHeight;
        context.drawText(textRenderer, textComponent, x - textWidth / 2, y - textHeight / 2, color, shadow);
    }
}
