package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.BlockoutLogger;
import net.abrikoos.blockout.server.listeners.AdvancementListener;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public class BlockoutPlayerAdvancementTrackerMixin {
    @Shadow private ServerPlayerEntity owner;

    @Inject(method = "grantCriterion", at = @At("RETURN"))
    private void onGrantCriterion(AdvancementEntry advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
        try {
            AdvancementListener.registerEvent(owner, advancement.value(), criterionName);
        }
        catch (Exception e) {
            e.printStackTrace();
            BlockoutLogger.log("onGrantCriterion in BlockoutPlayerAdvancementTrackerMixin failed with ");
        }

    }

}
