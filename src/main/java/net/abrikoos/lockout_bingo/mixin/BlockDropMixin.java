package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.builder.BlockDropChangeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

@Mixin(AbstractBlock.class)
public class BlockDropMixin {

    @Inject(method = "getDroppedStacks", at = @At("TAIL"), cancellable = true)
    private void getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (! BlockDropChangeBuilder.blockDropChanges.isEmpty()) {
            List<ItemStack> drops = new ArrayList<>();
            for (ItemStack stk : cir.getReturnValue()) {
                if (BlockDropChangeBuilder.blockDropChanges.containsKey(stk.getItem())) { // should always be true
                    drops.add(new ItemStack(BlockDropChangeBuilder.blockDropChanges.get(stk.getItem()), stk.getCount()));
                } else {
                    drops.add(stk);
                }
            }
            cir.setReturnValue(drops);
        }
    }
}
