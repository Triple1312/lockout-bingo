package net.abrikoos.lockout_bingo.server.goals.advancement;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.server.gamestate.GameSettings;
import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.goals.ProgressLockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.AdvancementListener;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

public class AdvancementCountGoal extends ProgressLockoutGoal {
    private int count;


    public AdvancementCountGoal(int id, int count) {
        super(id);
        this.count = count;
        AdvancementListener.subscribe(this::onPlayerAdvancement);
    }

    public void checkComplete(){
        if (completed != null) { return; }
        if (GameSettings.settings.teamCountValid()) {
            LockoutLogger.log("Team join goal not completed");
        }
        else{
            for (ServerPlayerEntity player : GameState.players()) {
                if (progress.getPlayersSize(player.getUuidAsString()) >= count) {
                    this.completed(player);
                    return;
                }
            }
        }
    }

    protected void onPlayerAdvancement(ServerPlayerEntity player, Advancement advancement, String criterionName) {
        if (completed != null) { return; }

        if (advancement.display().isEmpty()) {
            return; // ignore advancements without display (like recipes)
        }

        Collection<AdvancementEntry> advancements = player.server.getAdvancementLoader().getAdvancements();
        for (AdvancementEntry entry : advancements) {
            if (entry.value().name() == advancement.name()){
                AdvancementProgress progress = player.getAdvancementTracker().getProgress(entry);
                if (progress.isDone()) {
                    this.progress.addEntry(player.getUuidAsString(), advancement.name().toString()); // advancement.name() should always exist
                    this.checkComplete();
                    return;
                }
            }
        }
    }
}
