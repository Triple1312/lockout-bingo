package net.abrikoos.lockout_bingo.goals.advancement;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.listeners.AdvancementListener;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.block.ComposterBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;


/*
these are for goals that are checked by an advancement
they dont need to be an advancement, just use the progress of an advancement
 */

public abstract class AdvancementGoal extends LockoutGoal {
    Identifier advancementId;
    String recipiant;
    String criterionName;

    protected AdvancementGoal(int id, Identifier advancementid, String recipiant, String criterionName){
        super(id);
        this.advancementId = advancementid;
        this.recipiant = recipiant;
        this.criterionName = criterionName;
        AdvancementListener.subscribe(this::onPlayerAdvancement);
    }

    protected void onPlayerAdvancement(ServerPlayerEntity player, Advancement advancement, String criterionName) {
        try {
            if (completed != null) { return; }
            Identifier translatedkey = translateAdvancementTitleKey(advancement);
            if (this.advancementId == null && translatedkey == null) {
                if (validateProgress(null, criterionName)) {
                    completed = player;
                    this.notifyListeners(new LockoutGoalEvent(player.getName().getString(), this.recipiant, this.id)); // how Do I ignore the ally part

                }
            }
            else if (translatedkey != null && translatedkey.equals(this.advancementId)) { // should not be a problem
                AdvancementProgress progress = player.getAdvancementTracker().getProgress(new AdvancementEntry(advancementId, advancement)); //todo only checks id anyway
                if (validateProgress(progress, criterionName)) {
                    completed = player;
                    this.notifyListeners(new LockoutGoalEvent(player.getName().getString(), this.recipiant, this.id));

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            LockoutLogger.log("onplayerAdvancement in GetAdvancementGoal failed with " + this.advancementId.toString());
        }
    }

    protected boolean validateProgress(AdvancementProgress progress, String criterionName) {
        return this.criterionName.equals(criterionName);
    }


    protected Identifier translateAdvancementTitleKey(Advancement advancement) {
        try {
            String key = advancement.display().get().getTitle().getContent().toString();
            List<String> split = new java.util.ArrayList<>(List.of(key.split("\\.")));
            split.removeFirst(); split.removeLast();
            String ret = String.join("/", split);
            return Identifier.of("minecraft", ret); //todo what if other mod ?
        }
        catch (Exception e) {
            return null;
        }



    }

    @Override
    public void destory() {
        super.destory();
        AdvancementListener.unsubscribe(this::onPlayerAdvancement);
    }
}
