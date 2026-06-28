package net.abrikoos.blockout.server.goals.advancement;

import net.abrikoos.blockout.server.gamestate.GameState;
import net.minecraft.util.Identifier;

public class FrighteningGoal extends GetAdvancementGoal{

    public FrighteningGoal(int id) {
        super(id, Identifier.of("adventure/very_very_frightening"));
        GameState.enableThunderyWeatherCycle();
    }
}
