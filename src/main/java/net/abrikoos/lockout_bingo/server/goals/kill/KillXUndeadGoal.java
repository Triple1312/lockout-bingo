package net.abrikoos.lockout_bingo.server.goals.kill;

import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

public class KillXUndeadGoal extends KillXMobsGoal{

    public KillXUndeadGoal(int id, int amount) {
        super(id, amount);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        StatHandler stats = player.getStatHandler();
        return stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.ZOMBIE))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.HUSK))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.DROWNED))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.PHANTOM))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.SKELETON))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.HUSK))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.SKELETON_HORSE))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.STRAY))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.WITHER))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.WITHER_SKELETON))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.ZOGLIN))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.ZOMBIE_HORSE))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.ZOMBIE_VILLAGER))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.ZOMBIFIED_PIGLIN))
                + stats.getStat(Stats.KILLED.getOrCreateStat(EntityType.BOGGED))
                >= amount;
    }
}
