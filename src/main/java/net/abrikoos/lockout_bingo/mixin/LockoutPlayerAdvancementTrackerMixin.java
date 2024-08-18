package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.goals.advancement.PlayerAdvancementTrackerMixin;
import net.abrikoos.lockout_bingo.listeners.AdvancementListener;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public class LockoutPlayerAdvancementTrackerMixin {
    @Shadow private ServerPlayerEntity owner;

    @Inject(method = "grantCriterion", at = @At("HEAD"))
    private void onGrantCriterion(AdvancementEntry advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
        try {
            AdvancementListener.registerEvent(owner, advancement.value(), criterionName);
        }
        catch (Exception e) {
            e.printStackTrace();
            LockoutLogger.log("onGrantCriterion in LockoutPlayerAdvancementTrackerMixin failed with ");
        }

    }

}
