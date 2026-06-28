package net.abrikoos.blockout.server.goals.die;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.abrikoos.blockout.server.goals.BlockoutGoalEvent;
import net.abrikoos.blockout.server.listeners.PlayerDeathListener;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;

public class DieFromWeaponGoal extends BlockoutGoal {

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
                this.notifyListeners(new BlockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
