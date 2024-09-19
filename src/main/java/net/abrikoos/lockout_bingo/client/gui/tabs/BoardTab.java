package net.abrikoos.lockout_bingo.client.gui.tabs;

import net.abrikoos.lockout_bingo.client.gui.widget.GoalWidget;
import net.abrikoos.lockout_bingo.client.modes.LockoutGame;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BoardTab implements Tab {
    private final Text title = Text.of("Board");
    protected GridWidget grid = new GridWidget();

    private LockoutGame game;
    List<ClickableWidget> children = new ArrayList<>();
    final Screen parent;



    public BoardTab(Screen parent) { // todo make remove possible
        this.parent = parent;


        GridWidget.Adder adder = this.grid.createAdder(1);
        MinecraftClient client = MinecraftClient.getInstance();
        adder.add(new TextWidget(Text.of("Start a game to show the board"), client.textRenderer));


    }

    public void init() {
        this.grid = new GridWidget();
        GridWidget.Adder adder = this.grid.createAdder((int) Math.pow(game.getGoals().size(), 0.5));
        for (int i = 0; i < game.getGoals().size(); i++) {
            adder.add(GoalWidget.builder(game.getGoals().get(i), (int) (this.grid.getHeight() / (Math.pow(game.getGoals().size(), 0.5) + 3))).build());
        }
    }

    @Override
    public Text getTitle() {
        return null;
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {
        this.grid.forEachChild(consumer);
    }

    @Override
    public void refreshGrid(ScreenRect tabArea) {
        this.grid.refreshPositions();
        SimplePositioningWidget.setPos(this.grid, tabArea, 0.5F, 0.16666667F);
    }

    public void updateBoard(LockoutUpdateBoardInfo lubi) {

    }

    public void startGameInfo(LockoutGame game) {
        this.game = game;
        init();
    }

}
