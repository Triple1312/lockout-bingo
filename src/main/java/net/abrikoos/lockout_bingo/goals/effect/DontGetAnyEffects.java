package net.abrikoos.lockout_bingo.goals.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;

public class DontGetAnyEffects extends GetMultiEffectGoal{

    public DontGetAnyEffects(int id) {
        super(id, 1);
    }

    @Override
    public String recipiant() {
        return "enemy";
    }
}
