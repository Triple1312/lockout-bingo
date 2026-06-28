package net.abrikoos.blockout.server.goals;


public class BlockoutGoalEvent {
    public String puuid;
    public String recipiant;
    public int goalId;

    public BlockoutGoalEvent(String puuid, String recipiant, int goalId) {
        this.puuid = puuid;
        this.recipiant = recipiant;
        this.goalId = goalId;
    }
}
