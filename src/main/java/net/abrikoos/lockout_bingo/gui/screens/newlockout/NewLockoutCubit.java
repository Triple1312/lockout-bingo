package net.abrikoos.lockout_bingo.gui.screens.newlockout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NewLockoutCubit {
    private static NewLockoutState state = new NewLockoutState.DefaultNewLockoutState();



    public static List<Consumer<NewLockoutState>> consumers = new ArrayList<>();

    public static NewLockoutState subscribe(Consumer<NewLockoutState> callback) {
        consumers.add(callback);
        return state;
    }






}
