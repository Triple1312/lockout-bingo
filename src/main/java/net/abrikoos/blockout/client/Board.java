package net.abrikoos.blockout.client;

import net.abrikoos.blockout.BlockoutLogger;

import net.abrikoos.blockout.networkv2.team.Colors;
import net.abrikoos.blockout.server.goals.GoalListItem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static int selectedGoal = -1;

    public static final float MARGIN_RATIO = 0.3f;
    public static final int GOAL_SIZE = 100;

    public static int getWidthHeight() {
        return switch (ClientGameStateV2.getGoals().size()) { // todo add every possible value
            case 9 -> 3;
            case 25 -> 5;
            case 49 -> 7;
            case 81 -> 9;
            case 121 -> 11;
            default -> 0;
        };
    }

    public static int getBoardSizePX() {
        return (int) (Board.GOAL_SIZE * (Board.getWidthHeight() * (1 + Board.MARGIN_RATIO) - Board.MARGIN_RATIO));
    }

    private static boolean intersect(int x1, int y1, int x2, int y2, int mouseX, int mouseY) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }

    public static void drawBoard(DrawContext ctx, float delta, int mouseX, int mouseY) {
        int widthHeight = getWidthHeight();
        Board.selectedGoal = -1;
        MatrixStack matrices = ctx.getMatrices();

        final var goals = ClientGameStateV2.getGoals();

        for (int y = 0; y < widthHeight; y++) {
            for (int x = 0; x < widthHeight; x++) {
                int color = goals.get(y * widthHeight + x).color();
                int offsetX = (int) (x * (GOAL_SIZE + GOAL_SIZE * MARGIN_RATIO));
                int offsetY = (int) (y * (GOAL_SIZE + GOAL_SIZE * MARGIN_RATIO));
                ctx.fill(offsetX, offsetY, offsetX+GOAL_SIZE, offsetY + GOAL_SIZE, Colors.get(color) - 0x47000000);
                try {
                    ClientGameStateV2.goals.get(y * widthHeight + x).draw(ctx, delta, offsetX, offsetY, GOAL_SIZE, GOAL_SIZE);
                }
                catch (Exception ignored) {
                    BlockoutLogger.log("Error drawing goal " + goals.get(y * widthHeight + x).goalName() + "at board");
                }
                if (mouseX != 0) { // then it would be hud
                    if (intersect(offsetX, offsetY, offsetX + GOAL_SIZE, offsetY + GOAL_SIZE, mouseX, mouseY)) { // onhover
                        matrices.push();
                        matrices.translate(offsetX, offsetY - 15, 100);
                        matrices.scale(3f, 3f, 1);
                        ctx.drawTextWithBackground(ClientGameStateV2.client.textRenderer, Text.of(goals.get(y * widthHeight + x).goalName()), 0, 0, 200, 0xffffffff);
//                        GoalExplanationRegistry.getGoalExplanation(goals.get(y * widthHeight + x).goalId()).renderWidget(ctx, ClientGameStateV2.client.textRenderer, (int) (GOAL_SIZE * 2.5), Colors.get(color), goals.get(y * widthHeight + x).completedPlayerUUID()); todo
                        matrices.pop();
                        Board.selectedGoal = y * widthHeight + x;
                    }

                }
            }
        }







    }

}
