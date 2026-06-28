package net.abrikoos.blockout.server.goals.effect;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.listeners.TickListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

public class GetMultiEffectGoal extends BlockoutGoal {

    int count;

    public GetMultiEffectGoal(int id, int count) {
        super(id);
        this.count = count;
        TickListener.subscribe(this::checkCompletion);
    }

    private void checkCompletion(MinecraftServer minecraftServer) {
        if (completed != null) { return; }
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
