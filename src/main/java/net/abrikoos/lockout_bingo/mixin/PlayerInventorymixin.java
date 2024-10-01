package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.listeners.PlayerInventoryListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventorymixin {

    @Shadow @Final public PlayerEntity player;

    @Shadow public abstract ItemStack getStack(int slot);

    @Inject(method = "addStack(ILnet/minecraft/item/ItemStack;)I", at = @At("HEAD"))
    private void onAddStack(int slot, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
//        PlayerInventorymixin inventory =  this;
//        PlayerInventoryListener.registerEvent(inventory.player, stack, true);
    }


    @Inject(method = "remove", at = @At("HEAD"))
    private void onRemoveStack(Predicate<ItemStack> shouldRemove, int maxCount, Inventory craftingInventory, CallbackInfoReturnable<Integer> cir) { // todo does not what I want
//        PlayerInventorymixin inventory =  this;
////        if (stack == ItemStack.EMPTY) {
////            return;
////        }
//        PlayerInventoryListener.registerEvent(inventory.player, ItemStack.EMPTY, false); // todo idk if return works
    }

    @Inject(method = "setStack(ILnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
    void onSetStack(int slot, ItemStack stack, CallbackInfo ci) {
        PlayerInventorymixin inventory =  this; // this works weird
        if (stack == inventory.getStack(slot)) {
            return;
        }
        boolean added = true;
        if (stack == ItemStack.EMPTY || stack.getItem() == Items.AIR) {
            added = false;
        }
        PlayerInventoryListener.registerEvent(inventory.player, stack, slot, added);
//        PlayerInventoryListener.registerEvent(inventory.player, stack, true);
    }


}
