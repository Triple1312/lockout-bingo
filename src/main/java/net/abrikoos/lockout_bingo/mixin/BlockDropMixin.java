package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.builder.BlockDropChangeBuilder;
import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

@Mixin(Block.class)
public class BlockDropMixin {

    @Inject(method = "getDroppedStacks", at = @At("TAIL"), cancellable = true)
    private static void getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (! BlockDropChangeBuilder.blockDropChanges.isEmpty()) {
//            RegistryKey<LootTable> lootTableKey = BlockDropChangeBuilder.blockDropChanges.get(state.getBlock());
//            if (lootTableKey != null) {
//                LootTable lootTable = builder.getWorld().getServer().getReloadableRegistries().getLootTable(lootTableKey);
//                LootContextParameterSet.Builder lootContextParameterSet = new LootContextParameterSet.Builder(GameState.server.getOverworld());
//                lootContextParameterSet.add(LootContextParameters.BLOCK_STATE, state);
//                cir.setReturnValue(lootTable.generateLoot(lootContextParameterSet.build(LootContextTypes.BLOCK)));
//            }
            List<ItemStack> drops = new ArrayList<>();
            for (ItemStack stack : cir.getReturnValue()) {
                if (BlockDropChangeBuilder.blockDropChanges.containsKey(stack.getItem())) { // should always be true
                    drops.add(new ItemStack(BlockDropChangeBuilder.blockDropChanges.get(stack.getItem()), stack.getCount()));
                } else {
                    drops.add(stack);
                }
            }
            cir.setReturnValue(drops);
        }
    }

    @Inject(method = "getDroppedStacks*", at = @At("TAIL"), cancellable = true)
    private static void getDroppedStacks2(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (! BlockDropChangeBuilder.blockDropChanges.isEmpty()) {
//            RegistryKey<LootTable> lootTableKey = BlockDropChangeBuilder.blockDropChanges.get(state.getBlock());
//            if (lootTableKey != null) {
//                LootTable lootTable = builder.getWorld().getServer().getReloadableRegistries().getLootTable(lootTableKey);
//                LootContextParameterSet.Builder lootContextParameterSet = new LootContextParameterSet.Builder(GameState.server.getOverworld());
//                lootContextParameterSet.add(LootContextParameters.BLOCK_STATE, state);
//                cir.setReturnValue(lootTable.generateLoot(lootContextParameterSet.build(LootContextTypes.BLOCK)));
//            }
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
