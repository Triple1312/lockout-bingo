package net.abrikoos.lockout_bingo.goals.effect;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.listeners.PlayerEffectListener;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

public class GetEffectGoal extends LockoutGoal {

    private final RegistryEntry<StatusEffect> effect;

    public GetEffectGoal(int id, RegistryEntry<StatusEffect> effect) {
        super(id);
        this.effect = effect;
        PlayerEffectListener.subscribe(this::checkCompletion);
    }

    private void checkCompletion(PlayerEntity player, StatusEffectInstance effect) {
        if (completed != null) { return; }
        if (this.effect == effect.getEffectType()) {
            this.completed(player);
        }
    }

    @Override
    public void destory() {
        PlayerEffectListener.unsubscribe(this::checkCompletion);
    }



}
