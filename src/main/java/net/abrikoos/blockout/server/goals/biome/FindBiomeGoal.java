package net.abrikoos.blockout.server.goals.biome;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.abrikoos.blockout.server.listeners.TickListener;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class FindBiomeGoal extends BlockoutGoal {
    public Identifier biome;

    public FindBiomeGoal(int id, Identifier biome) {
        super(id);
        this.biome = biome;
        TickListener.subscribe(this::checkCompletion);
    }

    private void checkCompletion(MinecraftServer minecraftServer) {
        if (completed != null) { return; }
        for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
            Biome b = player.getWorld().getBiome(player.getBlockPos()).value();
            Identifier biomeId = player.getWorld().getRegistryManager().get(RegistryKeys.BIOME).getId(b);
            assert biomeId != null;
            if (biomeId.equals(biome)) {
                this.notifyListeners(new BlockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
                completed = player;
            }
        }
    }

}
