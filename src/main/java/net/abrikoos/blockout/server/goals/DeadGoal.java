package net.abrikoos.blockout.server.goals;

public class DeadGoal extends BlockoutGoal {

    String goalId;

    public DeadGoal(int id, String goalId) {
        super(id);
        this.goalId = goalId;
        complete("duishaoidh;laksdhjklsadhlsa");
    }
}
