package net.abrikoos.lockout_bingo.goals.biome;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class FindBiomeGoal extends LockoutGoal {
    public Identifier biome;

    public FindBiomeGoal(int id, Identifier biome) {
        super(id);
        this.biome = biome;
        ServerTickEvents.START_SERVER_TICK.register(this::checkCompletion);
    }

    private void checkCompletion(MinecraftServer minecraftServer) {
        if (completed != null) { return; }
        for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
            Biome b = player.getWorld().getBiome(player.getBlockPos()).value();
            Identifier biomeId = player.getWorld().getRegistryManager().get(RegistryKeys.BIOME).getId(b);
            assert biomeId != null;
            if (biomeId.equals(biome)) {
                this.notifyListeners(new LockoutGoalEvent(player.getNameForScoreboard(), "ally", this.id));
                completed = player;
            }
        }
    }

}
