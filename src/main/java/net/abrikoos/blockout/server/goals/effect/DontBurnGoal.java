package net.abrikoos.blockout.server.goals.effect;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.TickListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class DontBurnGoal extends BlockoutGoal {

    public DontBurnGoal(int id) {
        super(id);
        TickListener.subscribe(this::validateProgress);
    }

    private void validateProgress(MinecraftServer minecraftServer) {
        if (completed != null) return;
        for (ServerPlayerEntity player :minecraftServer.getPlayerManager().getPlayerList()) {
            if (player.isOnFire()) {
                completed(player);
            }
        }
    }



}
