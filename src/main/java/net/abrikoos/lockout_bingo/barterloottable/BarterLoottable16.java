//package net.abrikoos.lockout_bingo.barterloottable;
//
//import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
//import net.minecraft.loot.LootPool;
//import net.minecraft.loot.provider.number.LootNumberProvider;
//import net.minecraft.util.Identifier;
//
//
//public class BarterLoottable16 {
//
//    private static final Identifier BARTER_LOOT_TABLE_ID = Identifier.of("minecraft", "gameplay/piglin_bartering");
//
//    public static void moifyLootTable() {
//
//        LootTableEvents.REPLACE.register((key, tableBuilder, source, registries) -> {
//            if(BARTER_LOOT_TABLE_ID.equals()) {
//                tableBuilder.generateLoot(
//
//                );
//            }
//        });
//
//
//
//    }
//}
