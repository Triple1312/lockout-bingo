package net.abrikoos.lockout_bingo.client.gui.goalExplanations;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class GoalTextExplanation extends GoalExplanation {

    public String title;

    public String description;

    public GoalTextExplanation(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void renderWidget(DrawContext context, TextRenderer textRenderer) {

    }
}
