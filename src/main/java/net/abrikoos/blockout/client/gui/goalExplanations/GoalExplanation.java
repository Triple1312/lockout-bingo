package net.abrikoos.blockout.client.gui.goalExplanations;

import net.abrikoos.blockout.BlockoutLogger;
import net.abrikoos.blockout.client.ClientGameStateV2;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.List;

public class GoalExplanation {

    public String title;

    public String description;

    public List<RecipeWidget> recipes;

    public static int top = 50;

    public GoalExplanation(String title, String description, List<RecipeWidget> recipes) {
        this.title = title;
        this.description = description;
        this.recipes = recipes;
    }

    public void renderWidget(DrawContext context, TextRenderer textRenderer, int width, int titleColor, String completedBy) {
        MatrixStack matrices = context.getMatrices();
        int lineY = top;
        matrices.push();
        matrices.scale(1.2f, 1.2f, 1.0f);
        Text title = Text.literal(this.title).styled(style -> style.withBold(true));
        if (!completedBy.equals("00000000-0000-0000-0000-000000000000")) {
            try {
                title = Text.literal(this.title + " (Completed by " + ClientGameStateV2.teamReg.getPlayerDataByUUID(completedBy).name + ")").styled(style -> style.withBold(true).withColor(titleColor));
            }
            catch (Exception e) {
                BlockoutLogger.log("Could not find player name for UUID " + completedBy);
            }
        }
        var titleLines = textRenderer.wrapLines(title, (int) (width / 1.2f));
        for (var line : titleLines) {
            context.drawText(textRenderer, line, 10, lineY, 0xFFFFFF, true);
            lineY += textRenderer.fontHeight;
        }
        matrices.pop();
        matrices.push();
        lineY += 20;
        Text body = Text.literal(this.description);
        var bodyLines = textRenderer.wrapLines(body, width);
        for (var line : bodyLines) {
            context.drawText(textRenderer, line, 10, lineY, 0xF0F0F0, false);
            lineY += textRenderer.fontHeight;
        }

        int recipeY = lineY + 8;
        for (RecipeWidget widget : recipes) {
            recipeY += widget.render(context, 10, recipeY) + 6;
        }

        matrices.pop();

        context.drawText(textRenderer, "0", 5, 0, 0xFF8888, false);
        context.drawText(textRenderer, "50", 5, 50, 0xFF8888, false);
        context.drawText(textRenderer, "100", 5, 100, 0xFF8888, false);
        context.drawText(textRenderer, "150", 5, 150, 0xFF8888, false);
        context.drawText(textRenderer, "200", 5, 200, 0xFF8888, false);
        context.drawText(textRenderer, "250", 5, 250, 0xFF8888, false);
        context.drawText(textRenderer, "300", 5, 300, 0xFF8888, false);
        context.drawText(textRenderer, "350", 5, 350, 0xFF8888, false);
        context.drawText(textRenderer, "400", 5, 400, 0xFF8888, false);
        context.drawText(textRenderer, "450", 5, 450, 0xFF8888, false);
        context.drawText(textRenderer, "500", 5, 500, 0xFF8888, false);
        context.drawText(textRenderer, "550", 5, 550, 0xFF8888, false);
        context.drawText(textRenderer, "600", 5, 600, 0xFF8888, false);
    }
}
