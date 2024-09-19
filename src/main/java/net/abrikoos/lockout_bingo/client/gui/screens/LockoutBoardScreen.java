package net.abrikoos.lockout_bingo.client.gui.screens;

import net.abrikoos.lockout_bingo.client.gui.LockoutScreens;
import net.abrikoos.lockout_bingo.client.gui.widget.BoardWidget;
import net.abrikoos.lockout_bingo.client.modes.lockout.Lockout;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.function.Function;

public class LockoutBoardScreen extends Screen {

    public BoardWidget boardwidget;

    public Lockout board;

    private final Function<Void, Void> leaveScreen;

    public LockoutBoardScreen(Function<Void, Void> leaveScreen) { // todo add button to get compass again // todo make reroll button for start
        super(Text.of("Lockout Board"));
        this.leaveScreen = leaveScreen;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.clearChildren();
        this.addDrawableChild(
            ButtonWidget.builder(
                Text.of("Back"), (btn) -> {
                        assert client != null;
                        client.setScreen(LockoutScreens.mainScreen);

//                            this.client.setScreen(new JoinServerScreen()); // todo no screen needed
                }
            ).dimensions(40, 40, 120, 20).build()
        );
        super.render(context, mouseX, mouseY, delta);
        this.boardwidget.drawScreen(context, this,  mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_B) {
            this.leaveScreen.apply(null);
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.leaveScreen.apply(null);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void setBoardwidget(BoardWidget boardwidget) {
        this.boardwidget = boardwidget;
    }

    public boolean initialized() {
        return this.boardwidget != null;
    }
}
