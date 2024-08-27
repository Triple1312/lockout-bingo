package net.abrikoos.lockout_bingo.goals.effect;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.listeners.PlayerEffectListener;
import net.abrikoos.lockout_bingo.listeners.TickListener;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;

public class GetEffectGoal extends LockoutGoal {

    private final RegistryEntry<StatusEffect> effect;

    public GetEffectGoal(int id, RegistryEntry<StatusEffect> effect) {
        super(id);
        this.effect = effect;
        TickListener.subscribe(this::checkCompletion);
    }

    private void checkCompletion(MinecraftServer minecraftServer) {
        for (PlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
            if (player.hasStatusEffect(this.effect)) {
                this.completed(player);
            }
        }
    }

//    private void checkCompletion(PlayerEntity player, StatusEffectInstance effect) {
//        if (completed != null) { return; }
//        if (this.effect == effect.getEffectType()) {
//            this.completed(player);
//        }
//    }

    @Override
    public void destory() {
        TickListener.unsubscribe(this::checkCompletion);
    }



}
