package net.abrikoos.lockout_bingo.goals.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;

public class DontGetEffectGoal extends GetEffectGoal{
    @Override
    public String recipiant() {
        return "enemy";
    }

    public DontGetEffectGoal(int id, RegistryEntry<StatusEffect> effect) {
        super(id, effect);
    }
}
