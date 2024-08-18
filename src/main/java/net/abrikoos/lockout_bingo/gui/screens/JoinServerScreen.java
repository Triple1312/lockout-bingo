package net.abrikoos.lockout_bingo.gui.screens;

import net.abrikoos.lockout_bingo.gui.LockoutStartScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class JoinServerScreen extends Screen {
    public JoinServerScreen() {
        super(Text.of("Welcome to minecraft lockout bingo!"));
    }

    @Override
    protected void init() {
        ButtonWidget startButton = ButtonWidget.builder(
                Text.of("Create new lockout"), (btn) -> {
                    assert this.client != null;
//                    this.client.setScreen(new LockoutCreateScreen());
                }
        ).dimensions(40, 40, 120, 20).build();

        this.addDrawableChild(startButton);

        ButtonWidget createBlackoutButton = ButtonWidget.builder(
                Text.of("Create new blackout"), (btn) -> {
                    assert this.client != null;
                    this.client.setScreen(new LockoutStartScreen()); // todo change
                }
        ).dimensions(200, 40, 120, 20).build();

        this.addDrawableChild(createBlackoutButton);


        ButtonWidget createBingoButton = ButtonWidget.builder(
                Text.of("Create new bingo"), (btn) -> {
                    assert this.client != null;
                    this.client.setScreen(new LockoutStartScreen()); // todo change
                }
        ).dimensions(360, 40, 120, 20).build();
    }





}
