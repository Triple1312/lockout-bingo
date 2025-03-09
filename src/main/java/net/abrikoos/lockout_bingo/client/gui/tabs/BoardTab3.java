package net.abrikoos.lockout_bingo.client.gui.tabs;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.client.gui.LockoutUtils;
import net.abrikoos.lockout_bingo.networkv2.compass.AskCompassPacket;
import net.abrikoos.lockout_bingo.networkv2.game.GoalInfoPacket;
import net.abrikoos.lockout_bingo.networkv2.team.Colors;
import net.abrikoos.lockout_bingo.server.goals.GoalItemRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
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
        CompassButton compassButton = new CompassButton(0, 0, 16, 16);


        public BoardTabWidget() {
            super(0, 0, 0, 0, Text.of("Board"));
            this.client = MinecraftClient.getInstance();
        }

        private boolean intersect(int x1, int y1, int x2, int y2, int mouseX, int mouseY) {
            return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
        }

        @Override
        public void onClick(double mouseX, double mouseY) {
            if (intersect(compassButton.getX(), compassButton.getY(), compassButton.getX() + compassButton.getWidth(), compassButton.getY() + compassButton.getHeight(), (int) mouseX, (int) mouseY)) {
                compassButton.onClick(mouseX, mouseY);
            }
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) { // this is for current 25 goals

            if (ClientGameStateV2.isGameRunning()) {
                int tabsizewidth = this.width;
                int tabsizeheight = this.height;
                int goalwidthheight = (int) (tabsizeheight * 0.1);
                int goalpadding = (int) (tabsizeheight * 0.03);
                int topX = tabsizewidth / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;
                int topY = tabsizeheight / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;

                List<GoalInfoPacket> goals = ClientGameStateV2.getGoals();
                for (int i = 0; i < goals.size(); i++) {
                    int x = i % 5;
                    int y = i / 5;
                    int goalTopX = topX + x * (goalwidthheight + goalpadding);
                    int goalTopY = topY + y * (goalwidthheight + goalpadding);
                    int color = goals.get(i).color();

                    context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, Colors.get(color) - 0x47000000);
                    try {
                        GoalItemRegistry.getGoal(goals.get(i).goalID()).draw(context, delta, goalTopX, goalTopY, goalwidthheight, goalwidthheight);
                    }
                    catch (Exception ignored) {
                        LockoutLogger.log("Error drawing goal " + goals.get(i).goalName() + "at boardtab");
                    }
                    if (intersect(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, mouseX, mouseY)) { // onhover

                        context.drawTextWithBackground(client.textRenderer, Text.of(goals.get(i).goalName()), goalTopX, goalTopY - goalpadding/2, 200, 0xffffffff);
                    }
                }

//                for (int i = 0; i < ClientGameStateV2.getGoals().size(); i++) {
//                    GoalInfoPacket goal = ClientGameStateV2.getGoals().get(i);
//                    int x = i % 5;
//                    int y = i / 5;
//                    int goalTopX = topX + x * (goalwidthheight + goalpadding);
//                    int goalTopY = topY + y * (goalwidthheight + goalpadding);
//                    int color;
//                    if (ClientGameStateV2.latestUpdate() == null) {
//                        color = 0xFF000000;
//                    }
//                    else {
//                        color = Colors.getPlayerColor(ClientGameState.latestUpdate().goals[i]);
//                    }
//                    context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, color - 0x47000000);
//
//                    goal.draw(context, delta/3, goalTopX, goalTopY, goalwidthheight, goalwidthheight);
//
//                    if (intersect(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, mouseX, mouseY)) { // onhover
//
//                        context.drawTextWithBackground(client.textRenderer, Text.of(goal.name), goalTopX, goalTopY - goalpadding/2, 200, 0xffffffff);
//                    }
//
//                }
                if(!ClientGameStateV2.gameHasStarted()) {

                    LockoutUtils.drawCenteredText(context, client.textRenderer, "Start in: " + ClientGameStateV2.countDownTime() + 's', tabsizewidth / 2, topY - goalwidthheight/2, 0xffffffff, true);
                } else if (ClientGameStateV2.compass_enabled) {
                    compassButton.setX(this.getX() + 16);
                    compassButton.setY(this.getY() + 16);
                    compassButton.render(context, mouseX, mouseY, delta);
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

        static class CompassButton extends ClickableWidget{

            public CompassButton(int x, int y, int width, int height) {
                super(x, y, width, height, Text.of(""));
            }

            @Override
            protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
                context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
                RenderSystem.enableBlend();
                context.drawBorder(this.getX(), this.getY(), this.width, this.height, 0xFFFFFFFF);
                context.drawTexture(Identifier.of("lockout-bingo", "textures/item/compass03.png"), this.getX(), this.getY(), 0, 0, this.width, this.height, 16, 16);
            }

            @Override
            protected void appendClickableNarrations(NarrationMessageBuilder builder) {

            }

            @Override
            public void onClick(double mouseX, double mouseY) {
                ClientPlayNetworking.send(new AskCompassPacket());
                MinecraftClient.getInstance().setScreen(null);
            }
        }
    }
}