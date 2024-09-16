package net.abrikoos.lockout_bingo.gui.screens.newlockout;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class NewLockoutScreen extends Screen {
    NewLockoutState state;

    NewLockoutScreen() {
        super(Text.of("New Lockout Bingo"));
        this.stateChange(NewLockoutCubit.subscribe(this::stateChange));
    }


    public void stateChange(NewLockoutState newState) {
        state = newState;
    }













}
