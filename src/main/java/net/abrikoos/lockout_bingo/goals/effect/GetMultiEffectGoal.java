package net.abrikoos.lockout_bingo.goals.effect;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.listeners.PlayerEffectListener;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class GetMultiEffectGoal extends LockoutGoal {

    int count;

    public GetMultiEffectGoal(int id, int count) {
        super(id);
        this.count = count;
        PlayerEffectListener.subscribe(this::checkCompletion);
    }


    private void checkCompletion(PlayerEntity player, StatusEffectInstance effect) {
        if (completed != null) { return; }
        if (player.getActiveStatusEffects().size() >= count) {
            this.completed(player);
        }
    }

    @Override
    public void destory() {
        PlayerEffectListener.unsubscribe(this::checkCompletion);
    }
}
