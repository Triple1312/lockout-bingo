package net.abrikoos.lockout_bingo.goals.advancement;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.listeners.AdvancementListener;
import net.minecraft.advancement.Advancement;
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
                completed = player;
                this.notifyListeners(new LockoutGoalEvent(player.getNameForScoreboard(), "ally", this.id));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            LockoutLogger.log("onplayerAdvancement in GetAdvancementGoal failed with " + this.advancementId.toString() );
        }
    }

    @Override
    protected boolean validateProgress(AdvancementProgress progress, String criterionName) {
        return true;
    }

}
