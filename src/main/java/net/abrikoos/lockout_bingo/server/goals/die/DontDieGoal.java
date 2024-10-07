package net.abrikoos.lockout_bingo.server.goals.die;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class DontDieGoal extends DieGoal{

    @Override
    public String recipiant() {return "enemy";}

    public DontDieGoal(int id) {
        super(id);
    }

    @Override
    public void validateProgress(ServerPlayerEntity player, DamageSource source) {
        super.validateProgress(player, source);
        completed(player);

    }
}
