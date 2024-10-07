package net.abrikoos.lockout_bingo.server.goals.die;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class DieGameDesignGoal extends DieGoal{

    public DieGameDesignGoal(int id) {
        super(id);
    }

    @Override
    public void validateProgress(ServerPlayerEntity player, DamageSource source) {
        super.validateProgress(player, source);
        if (Objects.equals(source.getType().msgId(), "badRespawnPoint")) completed(player);
    }
}
