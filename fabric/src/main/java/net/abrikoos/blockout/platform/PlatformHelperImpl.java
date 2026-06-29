package net.abrikoos.blockout.platform;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetPotionLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.potion.Potions;

public class PlatformHelperImpl {
    public static void registerLootModifications() {
        LootTableEvents.REPLACE.register((key, original, source, registries) -> {
            if (LootTables.PIGLIN_BARTERING_GAMEPLAY.equals(key)) {
                return LootTable.builder().pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1.0F))
                                .with(ItemEntry.builder(Items.BOOK).weight(5).apply(new EnchantRandomlyLootFunction.Builder().option(registries.createRegistryLookup().getOrThrow(Enchantments.SOUL_SPEED.getRegistryRef()).getOrThrow(Enchantments.SOUL_SPEED))))
                                .with(ItemEntry.builder(Items.IRON_BOOTS).weight(8).apply(new EnchantRandomlyLootFunction.Builder().option(registries.createRegistryLookup().getOrThrow(Enchantments.SOUL_SPEED.getRegistryRef()).getOrThrow(Enchantments.SOUL_SPEED))))
                                .with(ItemEntry.builder(Items.POTION).weight(10).apply(SetPotionLootFunction.builder(Potions.FIRE_RESISTANCE)))
                                .with(ItemEntry.builder(Items.SPLASH_POTION).weight(10).apply(SetPotionLootFunction.builder(Potions.FIRE_RESISTANCE)))
                                .with(ItemEntry.builder(Items.IRON_NUGGET).weight(10).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(9.0F, 36.0F))))
                                .with(ItemEntry.builder(Items.ENDER_PEARL).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 8.0F))))
                                .with(ItemEntry.builder(Items.STRING).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0F, 24.0F))))
                                .with(ItemEntry.builder(Items.QUARTZ).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0F, 16.0F))))
                                .with(ItemEntry.builder(Items.OBSIDIAN).weight(40))
                                .with(ItemEntry.builder(Items.CRYING_OBSIDIAN).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 3.0F))))
                                .with(ItemEntry.builder(Items.LEATHER).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 10.0F))))
                                .with(ItemEntry.builder(Items.NETHER_BRICK).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 16.0F))))
                                .with(ItemEntry.builder(Items.GLOWSTONE_DUST).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(5.0F, 12.0F))))
                                .with(ItemEntry.builder(Items.GRAVEL).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0F, 16.0F))))
                                .with(ItemEntry.builder(Items.MAGMA_CREAM).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 6.0F))))
                                .with(ItemEntry.builder(Items.FIRE_CHARGE).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 5.0F))))
                                .with(ItemEntry.builder(Items.SOUL_SAND).weight(40).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 16.0F))))
                ).build();
            }
            return original;
        });
    }
}
