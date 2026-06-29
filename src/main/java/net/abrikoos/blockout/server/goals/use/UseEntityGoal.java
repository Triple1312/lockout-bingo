package net.abrikoos.blockout.server.goals.use;

import net.abrikoos.blockout.server.goals.BlockoutGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UseEntityGoal extends BlockoutGoal {

    public record InteractContext(PlayerEntity player, Entity entity, Hand hand) {}

    public static final List<Consumer<InteractContext>> entityInteractListeners = new ArrayList<>();

    EntityType<?> entityType;
    Item item;

    public UseEntityGoal(int id, EntityType<?> entityType, Item item) {
        super(id);
        this.entityType = entityType;
        this.item = item;
        entityInteractListeners.add(this::checkCompletion);
    }

    private void checkCompletion(InteractContext ctx) {
        if (entityType == ctx.entity().getType() && ctx.player().getStackInHand(ctx.hand()).getItem().equals(item)) {
            this.completed(ctx.player());
        }
    }
}
