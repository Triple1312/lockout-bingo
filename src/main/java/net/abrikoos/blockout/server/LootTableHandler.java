package net.abrikoos.blockout.server;

import net.abrikoos.blockout.server.gamestate.GameState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TridentItem;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class LootTableHandler {

    public static Random rng = new Random();

    public static List<ItemStack> editLootDrops(LootTable loottable, LootContextParameterSet params, List<ItemStack> generatedLoot){
        Supplier<Entity> attacker = () -> params.get(LootContextParameters.ATTACKING_ENTITY);
        Supplier<Entity> target = () -> params.get(LootContextParameters.THIS_ENTITY);
        Supplier<DamageSource> damageSource = () -> params.get(LootContextParameters.DAMAGE_SOURCE);
        Supplier<ItemStack> tool = () -> params.get(LootContextParameters.TOOL);
        Supplier<PlayerEntity> last_player = () -> params.get(LootContextParameters.LAST_DAMAGE_PLAYER);
        try {
            switch (target.get()) {
                case DrownedEntity drowned:
                    for (Iterator<ItemStack> it = drowned.getHandItems().iterator(); it.hasNext(); ) {
                        ItemStack handItem = it.next();
                        if (handItem.getItem() instanceof TridentItem) {
                           if (generatedLoot.stream().anyMatch(stack -> stack.getItem() instanceof TridentItem)) {
                               break;
                           }
                            else if (rng.nextInt(3) == 0) {
                                generatedLoot.add(new ItemStack(Items.TRIDENT, 1));
                            }
                            break;
                        }
                        break;
                    }
                    break;
                case WitherSkeletonEntity witherSkeleton:
                    if (GameState.increasedSkulls) {
                        if (generatedLoot.stream().noneMatch(stack -> stack.getItem() == Items.WITHER_SKELETON_SKULL)) {
                            if (rng.nextFloat() < 0.2f) {
                                generatedLoot.add(new ItemStack(Items.WITHER_SKELETON_SKULL, 1));
                            }
                        }
                        break;
                    }
                default:
                    break;
            }
        }
        catch (Exception e) {
        }



        return generatedLoot;
    }


}
