package net.abrikoos.lockout_bingo.client.gui.tabs;

import net.abrikoos.lockout_bingo.client.ClientGameState;
import net.abrikoos.lockout_bingo.client.gui.LockoutUtils;
import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.abrikoos.lockout_bingo.team.Colors;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class BoardTab3 implements Tab {
    BoardTabWidget boardTabWidget = new BoardTabWidget();

    @Override
    public Text getTitle() {
        return Text.of("Board");
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {
        consumer.accept(boardTabWidget);
    }

    @Override
    public void refreshGrid(ScreenRect tabArea) {
        boardTabWidget.setWidth(tabArea.width());
        boardTabWidget.setHeight(tabArea.height());
        boardTabWidget.setX(tabArea.getLeft());
        boardTabWidget.setY(tabArea.getTop());
    }

    class BoardTabWidget extends ClickableWidget {
        MinecraftClient client;

        public BoardTabWidget() {
            super(0, 0, 0, 0, Text.of("Board"));
            this.client = MinecraftClient.getInstance();
        }

        private boolean intersect(int x1, int y1, int x2, int y2, int mouseX, int mouseY) {
            return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) { // this is for current 25 goals

            if (ClientGameState.isGameRunning()) {
                int tabsizewidth = this.width;
                int tabsizeheight = this.height;
                int goalwidthheight = (int) (tabsizeheight * 0.1);
                int goalpadding = (int) (tabsizeheight * 0.03);
                int topX = tabsizewidth / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;
                int topY = tabsizeheight / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;

                for (int i = 0; i < ClientGameState.getGoals().size(); i++) {
                    GoalListItem goal = ClientGameState.getGoals().get(i);
                    int x = i % 5;
                    int y = i / 5;
                    int goalTopX = topX + x * (goalwidthheight + goalpadding);
                    int goalTopY = topY + y * (goalwidthheight + goalpadding);
                    int color;
                    if (ClientGameState.latestUpdate() == null) {
                        color = 0xFF000000;
                    }
                    else {
                        color = Colors.getPlayerColor(ClientGameState.latestUpdate().goals[i]);
                    }
                    context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, color - 0x47000000);

                    goal.draw(context, 0, goalTopX, goalTopY, goalwidthheight, goalwidthheight);

                    if (intersect(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, mouseX, mouseY)) { // onhover

                        context.drawTextWithBackground(client.textRenderer, Text.of(goal.name), goalTopX, goalTopY - goalpadding/2, 200, 0xffffffff);
                    }

                }
            }
            else {
                int tabsizewidth = this.width;
                int tabsizeheight = this.height;
                LockoutUtils.drawCenteredText(context, client.textRenderer, "Start a game to show the board", tabsizewidth / 2, tabsizeheight / 2, 0xffffffff, true);
//                context.drawText(client.textRenderer, Text.of("Start a game to show the board"), tabsizewidth / 2, tabsizeheight / 2, 0xffffffff, true);

                // todo draw text that no game
            }


        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {

        }
    }
}