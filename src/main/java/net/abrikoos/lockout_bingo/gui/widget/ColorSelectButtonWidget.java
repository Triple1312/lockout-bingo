package net.abrikoos.lockout_bingo.gui.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class ColorSelectButtonWidget extends ClickableWidget {

    int color;
    Consumer<Integer> consumer;

    public ColorSelectButtonWidget(int x, int y, int widthHeight, int color, Consumer<Integer> consumer) {
        super(x, y, widthHeight, widthHeight, Text.of(""));
        this.color = color;
        this.consumer = consumer;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, this.color);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        consumer.accept(color);
    }
}
