package net.abrikoos.lockout_bingo.builder;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;

import java.util.List;

abstract public class LockoutGoalBuilder {

    public BlackoutStartGameInfo generateBoard() {
        return null;
    }

    public List<LockoutGoal> buildBoard(BlackoutStartGameInfo info) {
        return null;
    }
}