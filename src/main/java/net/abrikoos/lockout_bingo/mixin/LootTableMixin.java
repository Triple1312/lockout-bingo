package net.abrikoos.lockout_bingo.mixin;

import net.abrikoos.lockout_bingo.server.LootTableHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class LootTableMixin {

    @Unique private static final ThreadLocal<List<ItemStack>> LT_BUFFER = ThreadLocal.withInitial(ArrayList::new);


    @Redirect(
            method = "generateLoot(Lnet/minecraft/loot/context/LootContextParameterSet;JLjava/util/function/Consumer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/loot/LootTable;generateUnprocessedLoot(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V"
            )
    )
    private void lockout$wrapOut(LootTable instance, LootContext context, Consumer<ItemStack> lootConsumer) {
        java.util.function.Consumer<ItemStack> bufferOut = stack -> LT_BUFFER.get().add(stack.copy());
        instance.generateUnprocessedLoot(context, bufferOut);
    }

    @Inject(method = "generateLoot(Lnet/minecraft/loot/context/LootContextParameterSet;JLjava/util/function/Consumer;)V", at = @At("TAIL"))
    private void lockout$flushBuffer(LootContextParameterSet parameters, long seed, Consumer<ItemStack> lootConsumer, CallbackInfo ci) {
        List<ItemStack> buffer = LT_BUFFER.get();
        List<ItemStack> newDrops = LootTableHandler.editLootDrops((LootTable)(Object)this, parameters, buffer);
        for (ItemStack stack : newDrops) {
            lootConsumer.accept(stack);
        }
        buffer.clear();
    }


}
