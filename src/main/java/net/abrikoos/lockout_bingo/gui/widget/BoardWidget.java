package net.abrikoos.lockout_bingo.gui.widget;

import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;


public class BoardWidget {

    public final BlackoutStartGameInfo lsgi;
    public LockoutUpdateBoardInfo lubi;

    public BoardWidget(BlackoutStartGameInfo lsgi) {
        this.lsgi = lsgi;
        this.lubi = new LockoutUpdateBoardInfo(new int[25]);
    }

    public void setLubi(LockoutUpdateBoardInfo lubi) {
        this.lubi = lubi;
    }

    public void drawHud(DrawContext context) {
        if (lsgi == null) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        int screensizex = context.getScaledWindowWidth();
        int screensizey = context.getScaledWindowHeight();
        int goalwidthheight = (int) (screensizey * 0.06);
        int goalpadding = (int) (screensizey * 0.01);
        int topX = screensizex - 5 * (goalwidthheight + goalpadding) - goalpadding;
        int topY = 5;

        for (int i = 0; i < this.lsgi.goals.length; i++) {
            int x = i % 5;
            int y = i / 5;
//            int y = i - z;
            int goalTopX = topX + x * (goalwidthheight + goalpadding);
            int goalTopY = topY + y * (goalwidthheight + goalpadding);
            if (lubi.goals[i] == 0) {
                context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, 0x88000000);
            } else if (lubi.goals[i] == 1) {
                context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, 0xa87fffd4);
            } else if (lubi.goals[i] == 2) {
                context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, 0xa8ff947f);
            }
            lsgi.goals[i].draw(context, 0, goalTopX, goalTopY, goalwidthheight, goalwidthheight);
        }
    }

    public void drawScreen(DrawContext context, Screen screen, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        int screensizex = screen.width;
        int screensizey = screen.height;
        int goalwidthheight = (int) (screensizey * 0.1);
        int goalpadding = (int) (screensizey * 0.03);
        int topX = screensizex / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;
        int topY = screensizey / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;

        for (int i = 0; i < this.lsgi.goals.length; i++) {
            int x = i % 5;
            int y = i/5;
            int goalTopX = topX + x * (goalwidthheight + goalpadding);
            int goalTopY = topY + y * (goalwidthheight + goalpadding);
            if (lubi.goals[i] == 0) {
                context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, 0x88000000);
            }
            else if (lubi.goals[i] == 1) {
                context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, 0xa87fffd4);
            }
            else if (lubi.goals[i] == 2) {
                context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, 0xa8ff947f);
            }
            lsgi.goals[i].draw(context, delta, goalTopX, goalTopY, goalwidthheight, goalwidthheight);

            if (intersect(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, mouseX, mouseY)) { // onhover
//                context.fill(goalTopX - goalpadding - 5, goalTopY - goalpadding - 5, goalTopX + goalwidthheight + goalpadding + 100, goalTopY + goalwidthheight + goalpadding + 15, 0x80000000);

                context.drawTextWithBackground(client.textRenderer, Text.of(lsgi.goals[i].name), goalTopX, goalTopY - goalpadding/2, 200, 0xffffffff);
            }
        }
    }

    private boolean intersect(int x1, int y1, int x2, int y2, int mouseX, int mouseY) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }


}
