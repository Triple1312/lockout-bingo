package net.abrikoos.lockout_bingo.server.listeners;

import net.abrikoos.lockout_bingo.util.HeptConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.SERVER)
public class UseCauldronListener {

    private final List<HeptConsumer<ItemStack, BlockState, World, BlockPos, PlayerEntity, Hand, BlockHitResult>> listeners;

    private static UseCauldronListener instance;

    private UseCauldronListener() {listeners = new ArrayList<>();}

    public static void subscribe(HeptConsumer<ItemStack, BlockState, World, BlockPos, PlayerEntity, Hand, BlockHitResult> listener) {
        getInstance().listeners.add(listener);
    }

    public static UseCauldronListener getInstance() {
        if (instance == null) {
            instance = new UseCauldronListener();
        }
        return instance;
    }

    public static void registerEvent(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        for (HeptConsumer<ItemStack, BlockState, World, BlockPos, PlayerEntity, Hand, BlockHitResult> listener : getInstance().listeners) {
            listener.accept(stack, state, world, pos, player, hand, hit);
        }
    }

    public static void clear() { instance = null;}
}
