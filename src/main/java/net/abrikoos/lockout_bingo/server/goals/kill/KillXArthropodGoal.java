package net.abrikoos.lockout_bingo.server.goals.kill;

import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

public class KillXArthropodGoal extends KillXMobsGoal{

    public KillXArthropodGoal(int id, int amount) {
        super(id, amount);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        StatHandler stats = player.getStatHandler();
        return stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.BEE))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.CAVE_SPIDER))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.ENDERMITE))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.SILVERFISH))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.SPIDER))
                >= amount;
    }
}
