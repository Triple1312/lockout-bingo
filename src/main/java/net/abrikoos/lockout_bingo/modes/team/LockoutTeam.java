package net.abrikoos.lockout_bingo.modes.team;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class LockoutTeam {
    public String name;
    public List<ServerPlayerEntity> players;



    public LockoutTeam(String name) {
        this.name = name;
    }

    public void addPlayer(ServerPlayerEntity player) {
        if (this.players.contains(player)) {
            return;
        }
        this.players.add(player);
    }
}
