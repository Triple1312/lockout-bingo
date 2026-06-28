package net.abrikoos.blockout.server.goals.advancement;

import net.abrikoos.blockout.BlockoutLogger;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.abrikoos.blockout.server.listeners.AdvancementListener;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class GetAdvancementGoal extends AdvancementGoal{

    public GetAdvancementGoal(int id, Identifier advancementId) {
        super(id, advancementId, "ally", "");
        AdvancementListener.subscribe(this::onPlayerAdvancement);
    }
    
    protected void onPlayerAdvancement(ServerPlayerEntity player, Advancement advancement, String criterionName) {
        if (completed != null) { return; }
        try {
            if (this.advancementId == null && super.translateAdvancementTitleKey(advancement) == null) {return;}
            if (translateAdvancementTitleKey(advancement) != null && translateAdvancementTitleKey(advancement).equals(this.advancementId)) {
                AdvancementProgress progress = player.getAdvancementTracker().getProgress(new AdvancementEntry(advancementId, advancement)); // todo only checks id anyway
                if (!progress.isDone()) {
                    return;
                }
                completed = player;
                this.notifyListeners(new BlockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            BlockoutLogger.log("onplayerAdvancement in GetAdvancementGoal failed with " + this.advancementId.toString() );
        }
    }

    @Override
    protected boolean validateProgress(AdvancementProgress progress, String criterionName) {
        return true;
    }

}
