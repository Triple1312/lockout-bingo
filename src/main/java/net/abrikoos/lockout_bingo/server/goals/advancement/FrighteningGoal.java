package net.abrikoos.lockout_bingo.server.goals.advancement;

import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.minecraft.util.Identifier;

public class FrighteningGoal extends GetAdvancementGoal{

    public FrighteningGoal(int id) {
        super(id, Identifier.of("adventure/very_very_frightening"));
        GameState.enableThunderyWeatherCycle();
    }
}
