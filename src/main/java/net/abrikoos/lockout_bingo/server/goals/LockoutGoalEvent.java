package net.abrikoos.lockout_bingo.server.goals;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class LockoutGoalEvent {
    public String puuid;
    public String recipiant;
    public int goalId;

    public LockoutGoalEvent(String puuid, String recipiant, int goalId) {
        this.puuid = puuid;
        this.recipiant = recipiant;
        this.goalId = goalId;
    }
}
