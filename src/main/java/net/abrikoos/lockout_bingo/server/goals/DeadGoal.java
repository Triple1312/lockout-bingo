package net.abrikoos.lockout_bingo.server.goals;

public class DeadGoal extends LockoutGoal {

    String goalId;

    public DeadGoal(int id, String goalId) {
        super(id);
        this.goalId = goalId;
        complete("duishaoidh;laksdhjklsadhlsa");
    }
}
