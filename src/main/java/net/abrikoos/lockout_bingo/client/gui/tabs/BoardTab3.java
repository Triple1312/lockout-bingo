package net.abrikoos.lockout_bingo.client.gui.tabs;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.Board;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.client.gui.LockoutUtils;
import net.abrikoos.lockout_bingo.client.gui.goalExplanations.GoalExplanationRegistry;
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
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BoardTab3 implements Tab {
    BoardTabWidget boardTabWidget = new BoardTabWidget();

    protected List<ClickableWidget> children = new ArrayList<>();


    @Override
    public Text getTitle() {
        return Text.of("Board");
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {
        children.forEach(consumer);
    }

    @Override
    public void refreshGrid(ScreenRect tabArea) {
        boardTabWidget.setWidth(tabArea.width());
        boardTabWidget.setHeight(tabArea.height());
        boardTabWidget.setX(tabArea.getLeft());
        boardTabWidget.setY(tabArea.getTop());

        boardTabWidget.textFieldWidget.setX(tabArea.getRight() - 116);
        boardTabWidget.textFieldWidget.setY(tabArea.getTop() + 16);
        boardTabWidget.textSize.setX(tabArea.getRight() - 116);
        boardTabWidget.textSize.setY(tabArea.getTop() + 16 + 24);
    }

    public BoardTab3() {
        children.add(boardTabWidget);

        children.add(boardTabWidget.textFieldWidget);
        children.add(boardTabWidget.textSize);
    }

    class BoardTabWidget extends ClickableWidget {
        MinecraftClient client;
        CompassButton compassButton = new CompassButton(0, 0, 16, 16);

        public TextFieldWidget textFieldWidget;
        public ButtonWidget textSize;
        public float explanationtextsize = 2.3f;


        public BoardTabWidget() {
            super(0, 0, 0, 0, Text.of("Board"));
            this.client = MinecraftClient.getInstance();
            this.textFieldWidget = new TextFieldWidget(client.textRenderer, 500, 100, 100, 20, Text.of("3"));
            this.textFieldWidget.setText("2.3");
            this.textSize = ButtonWidget.builder(Text.of("Set text size"), button -> {
                try {
                    explanationtextsize = textFieldWidget.getText().isEmpty() ? 3 : Float.parseFloat(textFieldWidget.getText());
                }
                catch (NumberFormatException e) {
                    LockoutLogger.log("Invalid number format for text size: " + textFieldWidget.getText());
                }

            }).dimensions(500, 130, 100, 20).build();
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

        protected void drawGoalExplanation(DrawContext context) {
            final float SCALE = (float)  this.width / ( Board.getBoardSizePX()) / 3 * explanationtextsize;
            if (Board.selectedGoal != -1 && Board.selectedGoal < ClientGameStateV2.getGoals().size()) {
                GoalInfoPacket goal = ClientGameStateV2.getGoals().get(Board.selectedGoal);
                MatrixStack matrices = context.getMatrices();
                matrices.push();
                matrices.scale(SCALE, SCALE, 1f);
                GoalExplanationRegistry.getGoalExplanation(goal.goalId()).renderWidget(context, client.textRenderer, (int) (this.width * 0.3 / SCALE), Colors.get(goal.color()), goal.completedPlayerUUID());
                matrices.pop();
            }
        }

        protected void drawBoard(DrawContext context, int mouseX, int mouseY, float delta) {
            int screenwidth = this.width;
            int screenheight = this.height;
            int boardWidthHeightpx = Board.getBoardSizePX();
            final float SCALE = (float)  screenwidth / ( boardWidthHeightpx) /3;
            final int BOARD_OFFSSETX = (int) (screenwidth / 2 - boardWidthHeightpx * SCALE / 2);
            final int BOARD_OFFSSETY = (int) (screenheight / 2 - boardWidthHeightpx * SCALE / 2);
            MatrixStack matrices = context.getMatrices();
            matrices.push();
            matrices.translate(BOARD_OFFSSETX, BOARD_OFFSSETY, 0);
            matrices.scale(SCALE, SCALE, 1f);
            Board.drawBoard(context, delta, (int) ((mouseX - BOARD_OFFSSETX) / SCALE), (int) ((mouseY - BOARD_OFFSSETY) / SCALE));
            matrices.pop();
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (intersect(
                    compassButton.getX(), compassButton.getY(),
                    compassButton.getX() + compassButton.getWidth(),
                    compassButton.getY() + compassButton.getHeight(),
                    (int) mouseX, (int) mouseY)) {
                compassButton.onClick(mouseX, mouseY);
                return true;
            }
            return false;
        }



        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) { // this is for current 25 goals

            if (ClientGameStateV2.isGameRunning()) {
                drawBoard(context, mouseX, mouseY, delta);
                drawGoalExplanation(context);

//                this.textFieldWidget.renderWidget(context, mouseX, mouseY, delta);
//                this.textSize.render(context, mouseX, mouseY, delta);





                int tabsizewidth = this.width;
                int tabsizeheight = this.height;
                int goalwidthheight = (int) (tabsizeheight * 0.1);
                int goalpadding = (int) (tabsizeheight * 0.03);
                int topX = tabsizewidth / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;
                int topY = tabsizeheight / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;
//
//                List<GoalInfoPacket> goals = ClientGameStateV2.getGoals();
//                for (int i = 0; i < goals.size(); i++) {
//                    int x = i % 5;
//                    int y = i / 5;
//                    int goalTopX = topX + x * (goalwidthheight + goalpadding);
//                    int goalTopY = topY + y * (goalwidthheight + goalpadding);
//                    int color = goals.get(i).color();
//
//                    context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, Colors.get(color) - 0x47000000);
//                    try {
//                        ClientGameStateV2.goals.get(i).draw(context, delta, goalTopX, goalTopY, goalwidthheight, goalwidthheight);
//                    }
//                    catch (Exception ignored) {
//                        LockoutLogger.log("Error drawing goal " + goals.get(i).goalName() + "at boardtab");
//                    }
//                    if (intersect(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, mouseX, mouseY)) { // onhover
//
//                        context.drawTextWithBackground(client.textRenderer, Text.of(goals.get(i).goalName()), goalTopX, goalTopY - goalpadding/2, 200, 0xffffffff);
//                        GoalExplanationRegistry.getGoalExplanation(goals.get(i).goalId()).renderWidget(context, client.textRenderer, (int) (this.width * 0.3), Colors.get(color), goals.get(i).completedPlayerUUID());
//                    }
//                }

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
                context.drawTexture(Identifier.of("lockout-bingo", "textures/item/compass_20.png"), this.getX(), this.getY(), 0, 0, this.width, this.height, 16, 16);
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