package net.abrikoos.lockout_bingo.server.goals.use;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class UseEntityGoal extends LockoutGoal {

    EntityType<?> entityType;
    Item item;

    public UseEntityGoal(int id, EntityType<?> entityType, Item item) {
        super(id);
        this.entityType = entityType;
        this.item = item;
        UseEntityCallback.EVENT.register(this::checkCompletion);
    }

    private ActionResult checkCompletion(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult entityHitResult) {
        if (entityType == entity.getType() && player.getStackInHand(hand).getItem().equals(item)) {
            this.completed(player);
        }
        return ActionResult.PASS;
    }


}
