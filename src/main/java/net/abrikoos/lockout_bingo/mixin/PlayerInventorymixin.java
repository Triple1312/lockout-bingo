package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.listeners.PlayerInventoryListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventorymixin {

    @Shadow @Final public PlayerEntity player;

    @Shadow public abstract ItemStack getStack(int slot);

    @Inject(method = "addStack(Lnet/minecraft/item/ItemStack;)I", at = @At("HEAD"))
    private void onAddStack(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        PlayerInventorymixin inventory =  this;
        PlayerInventoryListener.registerEvent(inventory.player, stack, true);
    }


    @Inject(method = "removeStack(I)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"))
    private void onRemoveStack(int slot, CallbackInfoReturnable<ItemStack> cir) {
        PlayerInventorymixin inventory =  this;
        ItemStack stack = inventory.getStack(slot);
        if (stack == ItemStack.EMPTY) {
            return;
        }
        PlayerInventoryListener.registerEvent(inventory.player, stack, false); // todo idk if return works
    }

}
