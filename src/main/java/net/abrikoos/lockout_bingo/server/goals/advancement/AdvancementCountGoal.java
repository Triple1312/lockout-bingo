package net.abrikoos.lockout_bingo.server.goals.advancement;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.AdvancementListener;
import net.abrikoos.lockout_bingo.team.UnitedTeamRegistry;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

public class AdvancementCountGoal extends LockoutGoal {
    private int count;


    public AdvancementCountGoal(int id, int count) {
        super(id);
        this.count = count;
        AdvancementListener.subscribe(this::onPlayerAdvancement);
    }

    protected void onPlayerAdvancement(ServerPlayerEntity player, Advancement advancement, String criterionName) {
        Collection<AdvancementEntry> advancements = player.server.getAdvancementLoader().getAdvancements();
        int completed = 0;
        for (AdvancementEntry entry : advancements) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(entry);
            if (progress.isDone()) {
                completed++;
                if (completed >= count) {
                    this.completed(player);
                    return;
                }
            }
        }
    }

}
