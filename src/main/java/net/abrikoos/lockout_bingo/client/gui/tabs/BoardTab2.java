package net.abrikoos.lockout_bingo.client.gui.tabs;

import net.abrikoos.lockout_bingo.client.gui.widget.GoalWidget;
import net.abrikoos.lockout_bingo.client.modes.LockoutGame;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BoardTab2 implements Tab {
    BoardWidget boardWidget;

    public BoardTab2() {
        this.boardWidget = new BoardWidget(0, 0, 0, 0, Text.of("Board"));
    }

    @Override
    public Text getTitle() {
        return Text.of("Board");
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {
        consumer.accept(boardWidget);
    }

    public void newGame(LockoutGame game) {
        boardWidget.newBoard(game);
    }

    public void endGame() {
        boardWidget.endGame();
    }

    public void updateBoard(LockoutUpdateBoardInfo lubi) {
        boardWidget.updateBoard(lubi);
    }

    @Override
    public void refreshGrid(ScreenRect tabArea) {
        boardWidget.setWidth(tabArea.width());
        boardWidget.setHeight(tabArea.height());
        boardWidget.setX(tabArea.getLeft());
        boardWidget.setY(tabArea.getTop());
        SimplePositioningWidget.setPos(boardWidget.grid, tabArea, 0.5F, 0.16666667F);
    }

    class BoardWidget extends ClickableWidget {
        List<GoalWidget> goals = new ArrayList<>();
        public GridWidget grid = new GridWidget();
        LockoutGame game;

        public boolean clicked(double mouseX, double mouseY) {
            return false;
        }


        public BoardWidget(int x, int y, int width, int height, Text message) {
            super(x, y, width, height, message);
            init();
        }

        public void init() {
            this.grid = new GridWidget();
            GridWidget.Adder adder = this.grid.createAdder(1);
            adder.add(new TextWidget(Text.of("Start a game to show the board"), MinecraftClient.getInstance().textRenderer));
        }

        public void newBoard(LockoutGame game) {
            this.game = game;
            this.grid = new GridWidget();
            GridWidget.Adder adder = this.grid.createAdder((int) Math.pow(game.getGoals().size(), 0.5));
            for (int i = 0; i < game.getGoals().size(); i++) {
                goals.add(adder.add(GoalWidget.builder(game.getGoals().get(i), (int) (this.grid.getHeight() / (Math.pow(game.getGoals().size(), 0.5) + 3))).build()));
            }
        }

        public void endGame() {
            this.goals.clear();
            this.game = null;
            init();
        }

        public void updateBoard(LockoutUpdateBoardInfo lubi) {
            for (int i = 0; i < this.goals.size(); i++) {
                this.goals.get(i).updateUUID(lubi.goals[i]);
            }
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            grid.forEachChild((widget) -> widget.render(context, mouseX, mouseY, delta));
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {

        }
    }
}
