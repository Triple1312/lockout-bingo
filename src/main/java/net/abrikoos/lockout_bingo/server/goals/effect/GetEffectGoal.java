package net.abrikoos.lockout_bingo.server.goals.effect;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.TickListener;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
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
        if (completed != null) return;
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
