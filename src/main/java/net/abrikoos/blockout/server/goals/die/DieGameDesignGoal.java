package net.abrikoos.blockout.server.goals.die;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class DieGameDesignGoal extends DieGoal{

    public DieGameDesignGoal(int id) {
        super(id);
    }

    @Override
    public void validateProgress(ServerPlayerEntity player, DamageSource source) {
        if (completed != null) return;
        super.validateProgress(player, source);
        if (Objects.equals(source.getType().deathMessageType().name(), "INTENTIONAL_GAME_DESIGN")) completed(player);
    }


}
