package net.abrikoos.lockout_bingo.goals.die;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.abrikoos.lockout_bingo.listeners.PlayerDeathListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;

public class DieFromWeaponGoal extends LockoutGoal {

    RegistryKey<DamageType> type;

    public DieFromWeaponGoal(int id, RegistryKey<DamageType> type) {
        super(id);
        this.type = type;
        PlayerDeathListener.subscribe(this::validateProgress);

    }

    protected void validateProgress(ServerPlayerEntity player, DamageSource source) {
        if (completed != null) {return;}
        try {
            RegistryEntry<DamageType> type = source.getTypeRegistryEntry();
            RegistryKey<DamageType> x = type.getKeyOrValue().orThrow();
            if (x == this.type) {
                completed = player;
                this.notifyListeners(new LockoutGoalEvent(player.getName().getString(), "ally", this.id));
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
