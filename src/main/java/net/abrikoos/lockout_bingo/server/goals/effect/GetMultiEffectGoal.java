package net.abrikoos.lockout_bingo.server.goals.effect;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.server.listeners.TickListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

public class GetMultiEffectGoal extends LockoutGoal {

    int count;

    public GetMultiEffectGoal(int id, int count) {
        super(id);
        this.count = count;
        TickListener.subscribe(this::checkCompletion);
    }

    private void checkCompletion(MinecraftServer minecraftServer) {
        for (PlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
            if (player.getActiveStatusEffects().size() >= count) {
                this.completed(player);
            }
        }
    }


//    private void checkCompletion(PlayerEntity player, StatusEffectInstance effect) {
//        if (completed != null) { return; }
//        if (player.getActiveStatusEffects().size() >= count) {
//            this.completed(player);
//        }
//    }

    @Override
    public void destory() {
        TickListener.unsubscribe(this::checkCompletion);
    }
}
