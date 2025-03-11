package net.abrikoos.lockout_bingo.server.goals;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.util.BlockoutList;
import net.minecraft.block.Blocks;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import java.util.List;
import java.util.NoSuchElementException;

import static net.abrikoos.lockout_bingo.server.goals.LockoutGoalTag.*;

public class GoalItemRegistry {
    public BlockoutList<GoalListItem> items;

    private static GoalItemRegistry instance;

    public GoalItemRegistry() {
        items = new BlockoutList<>();

        items.add(new GoalListBlockItem("Opponent obtains crafting table", "An enemy obtains a crafting table", 3, List.of(dont), "no_crafting_table", List.of(Items.CRAFTING_TABLE.getDefaultStack())));
        items.add(new GoalListBlockItem("Opponent obtains obsidian", "opponent obtains obsidian", 2, List.of(dont), "no_obsidian", List.of(Items.OBSIDIAN.getDefaultStack())));
        items.add(new GoalListBlockItem("Opponent obtains netherrack", "opponent obtains netherrack", 4, List.of(dont), "no_netherrack", List.of(Items.NETHERRACK.getDefaultStack())));
        items.add(new GoalListBlockItem("Opponent obtains seeds", "opponent obtains seeds", 2, List.of(dont), "no_seeds", List.of(Items.WHEAT_SEEDS.getDefaultStack())));
        items.add(new GoalListBlockItem("Opponent gets hit by a snowball", "opponent gets hit by a snowball", 2, List.of(dont), "snowball_hit", List.of(Items.SNOWBALL.getDefaultStack())));
        items.add(new GoalListItem("Opponent takes fall damage", "opponent takes fall damage", 2, List.of(dont), "no_fall", List.of(Identifier.of("lockout-bingo:goalicon/other/fall_damage.png")))); // todo icon
        items.add(new GoalListItem("Opponent dies", "", 2, List.of(dont, die), "no_die", List.of(Identifier.of("lockout-bingo:goalicon/other/opponent.png")) ));
        items.add(new GoalListItem("Opponent dies 3 times", "", 2, List.of(dont, die, C3), "no_die_3", List.of(Identifier.of("lockout-bingo:goalicon/other/opponent.png")) ));
        items.add(new GoalListItem("Opponent catches fire", "", 3, List.of(dont), "no_fire", List.of(Identifier.of("lockout-bingo", "goalicon/other/fire.png"))));

        items.add(new GoalListBlockItem("obtain end crystal", "", 3, List.of(nether, obtain), "obtain_end_crystal", List.of(Items.END_CRYSTAL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain bell", "", 2, List.of(village, obtain), "obtain_bell", List.of(Items.BELL.getDefaultStack())));
        items.add(new GoalListItem("dont touch water", "dont touch water", 1, List.of(dont), "no_water", List.of(Identifier.of("lockout-bingo:goalicon/block/water.png")))); // todo icon
        items.add(new GoalListBlockItem("obtain a bottle o enchanting", "obtain bottle o enchanting", 1, List.of(obtain), "obtain_bottle_o_enchanting", List.of(Items.EXPERIENCE_BOTTLE.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a slime block", "", 1, List.of(obtain), "obtain_slime_block", List.of(Items.SLIME_BLOCK.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a cake", "", 3, List.of( obtain), "obtain_cake", List.of(Items.CAKE.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a soul lantern", "", 2, List.of(nether, obtain), "obtain_soul_lantern", List.of(Items.SOUL_LANTERN.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain heart of the sea", "", 2, List.of(obtain), "obtain_heart_of_the_sea", List.of(Items.HEART_OF_THE_SEA.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain sponge", "", 4, List.of(obtain), "obtain_sponge", List.of(Items.SPONGE.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain an end rod", "", 5, List.of(end, obtain), "obtain_end_rod", List.of(Items.END_ROD.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain sea lantern", "", 4, List.of(silk_touch, obtain), "obtain_sea_lantern", List.of(Items.SEA_LANTERN.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain mossy stone brick wall", "", 3, List.of( obtain), "obtain_mossy_stone_brick_wall", List.of(Items.MOSSY_STONE_BRICK_WALL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain crying obsidian", "", 2, List.of(nether, obtain), "obtain_crying_obsidian", List.of(Items.CRYING_OBSIDIAN.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain mushroom stem", "", 3, List.of(silk_touch, obtain), "obtain_mushroom_stem", List.of(Items.MUSHROOM_STEM.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a bubble coral block", "", 3, List.of(silk_touch, obtain), "obtain_coral_block", List.of(Items.BUBBLE_CORAL_BLOCK.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a shulker box", "", 5, List.of(end, obtain), "obtain_shulker_box", List.of(Items.SHULKER_BOX.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a bookshelf", "", 2, List.of( obtain), "obtain_bookshelf", List.of(Items.BOOKSHELF.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain flowering azelia", "", 3, List.of( obtain), "obtain_flowering_azelia", List.of(Items.FLOWERING_AZALEA.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain scaffolding", "", 3, List.of( obtain), "obtain_scaffolding", List.of(Items.SCAFFOLDING.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a bucket of powdered snow", "", 3, List.of( obtain), "obtain_snow_bucket", List.of(Items.POWDER_SNOW_BUCKET.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain ancient debris", "", 5, List.of(nether, obtain), "obtain_ancient_debris", List.of(Items.ANCIENT_DEBRIS.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain an ender chest", "", 4, List.of(nether, obtain), "obtain_ender_chest", List.of(Items.ENDER_CHEST.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain the dragon egg", "", 5, List.of(end, obtain), "obtain_dragon_egg", List.of(Items.DRAGON_EGG.getDefaultStack())));
        //        items.add(new GoalListItem("obtain dragons breath", "", 1, List.of(), "obtain_dragons_breath", List.of(Identifier.of("minecraft:textures/item/dragons_breath.png"))));
        items.add(new GoalListBlockItem("obtain TNT", "", 2, List.of( obtain), "obtain_tnt", List.of(Items.TNT.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain mud brick wall", "", 3, List.of( obtain), "obtain_mud_brick_wall", List.of(Items.MUD_BRICK_WALL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a calibrated sculk sensor", "", 4, List.of( obtain), "obtain_calibrated_sculk_sensor", List.of(Items.CALIBRATED_SCULK_SENSOR.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain blue ice", "", 4, List.of(silk_touch, obtain), "obtain_blue_ice", List.of(Items.BLUE_ICE.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a cobweb", "", 2, List.of( obtain), "obtain_cobweb", List.of(Items.COBWEB.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain goat horn", "", 3, List.of(obtain), "obtain_goat_horn", List.of(Items.GOAT_HORN.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a nautilus shell", "", 2, List.of(obtain), "obtain_nautilus_shell", List.of(Items.NAUTILUS_SHELL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain exposed cut copper stairs", "", 4, List.of(obtain), "obtain_exposed_copper_stairs", List.of(Items.EXPOSED_CUT_COPPER_STAIRS.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a copper bulb", "", 4, List.of(nether, obtain), "obtain_copper_bulb", List.of(Items.COPPER_BULB.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a daylight detector", "", 2, List.of(nether, obtain), "obtain_daylight_detector", List.of(Items.DAYLIGHT_DETECTOR.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a redstone repeater", "", 2, List.of(redstone, obtain), "obtain_repeater", List.of(Items.REPEATER.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a piston", "", 2, List.of(redstone, obtain), "obtain_piston", List.of(Items.PISTON.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a sticky piston", "", 3, List.of(redstone, obtain), "obtain_sticky_piston", List.of(Items.STICKY_PISTON.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a dispenser", "", 2, List.of(redstone, obtain), "obtain_dispenser", List.of(Items.DISPENSER.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain an activator rail", "", 2, List.of(redstone, obtain), "obtain_activator_rail", List.of(Items.ACTIVATOR_RAIL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a detector rail", "", 2, List.of(redstone, obtain), "obtain_detector_rail", List.of(Items.DETECTOR_RAIL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a powered rail", "", 3, List.of(redstone, obtain), "obtain_powered_rail", List.of(Items.POWERED_RAIL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a clock", "", 1, List.of(redstone, obtain), "obtain_clock", List.of(Items.CLOCK.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a comparator", "", 2, List.of(redstone, obtain), "obtain_comparator", List.of(Items.COMPARATOR.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain an observer", "", 3, List.of(redstone, obtain), "obtain_observer", List.of(Items.OBSERVER.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain every type of raw ore block", "", 4, List.of( obtain), "obtain_all_raw_ore_blocks", List.of(Items.RAW_IRON_BLOCK.getDefaultStack(), Items.RAW_COPPER_BLOCK.getDefaultStack(), Items.RAW_GOLD_BLOCK.getDefaultStack())));

        items.add(new GoalListBlockItem("obtain 15 unique types of stairs", "", 5, List.of(C15, unique, obtain), "stairs_15", List.of(Items.OAK_STAIRS.getDefaultStack(), Items.SMOOTH_QUARTZ_STAIRS.getDefaultStack(), Items.SMOOTH_RED_SANDSTONE_STAIRS.getDefaultStack(), Items.DEEPSLATE_TILE_STAIRS.getDefaultStack(), Items.CHERRY_STAIRS.getDefaultStack(), Items.STONE_BRICK_STAIRS.getDefaultStack(), Items.CUT_COPPER_STAIRS.getDefaultStack(), Items.POLISHED_GRANITE_STAIRS.getDefaultStack(), Items.BAMBOO_MOSAIC_STAIRS.getDefaultStack(), Items.END_STONE_BRICK_STAIRS.getDefaultStack(), Items.DARK_PRISMARINE_STAIRS.getDefaultStack(), Items.MOSSY_COBBLESTONE_STAIRS.getDefaultStack(), Items.WEATHERED_CUT_COPPER_STAIRS.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain 5 unique types of stairs", "", 2, List.of(C5, unique, obtain), "stairs_5", List.of(Items.OAK_STAIRS.getDefaultStack(), Items.SMOOTH_QUARTZ_STAIRS.getDefaultStack(), Items.SMOOTH_RED_SANDSTONE_STAIRS.getDefaultStack(), Items.DEEPSLATE_TILE_STAIRS.getDefaultStack(), Items.CHERRY_STAIRS.getDefaultStack(), Items.STONE_BRICK_STAIRS.getDefaultStack(), Items.CUT_COPPER_STAIRS.getDefaultStack(), Items.POLISHED_GRANITE_STAIRS.getDefaultStack(), Items.BAMBOO_MOSAIC_STAIRS.getDefaultStack(), Items.END_STONE_BRICK_STAIRS.getDefaultStack(), Items.DARK_PRISMARINE_STAIRS.getDefaultStack(), Items.MOSSY_COBBLESTONE_STAIRS.getDefaultStack(), Items.WEATHERED_CUT_COPPER_STAIRS.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain 10 unique types of stairs", "", 3, List.of(C10, unique, obtain), "stairs_10", List.of(Items.OAK_STAIRS.getDefaultStack(), Items.SMOOTH_QUARTZ_STAIRS.getDefaultStack(), Items.SMOOTH_RED_SANDSTONE_STAIRS.getDefaultStack(), Items.DEEPSLATE_TILE_STAIRS.getDefaultStack(), Items.CHERRY_STAIRS.getDefaultStack(), Items.STONE_BRICK_STAIRS.getDefaultStack(), Items.CUT_COPPER_STAIRS.getDefaultStack(), Items.POLISHED_GRANITE_STAIRS.getDefaultStack(), Items.BAMBOO_MOSAIC_STAIRS.getDefaultStack(), Items.END_STONE_BRICK_STAIRS.getDefaultStack(), Items.DARK_PRISMARINE_STAIRS.getDefaultStack(), Items.MOSSY_COBBLESTONE_STAIRS.getDefaultStack(), Items.WEATHERED_CUT_COPPER_STAIRS.getDefaultStack())));

        // tool goals
        items.add(new GoalListBlockItem("obtain a full set of wooden tools", "", 1, List.of(tools, obtain), "obtain_wooden_toolset", List.of(Items.WOODEN_HOE.getDefaultStack(),  Items.WOODEN_AXE.getDefaultStack(), Items.WOODEN_PICKAXE.getDefaultStack(), Items.WOODEN_SHOVEL.getDefaultStack(), Items.WOODEN_SWORD.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a full set of stone tools", "", 1, List.of(tools, obtain), "obtain_stone_toolset", List.of(Items.STONE_HOE.getDefaultStack(),  Items.STONE_AXE.getDefaultStack(), Items.STONE_PICKAXE.getDefaultStack(), Items.STONE_SHOVEL.getDefaultStack(), Items.STONE_SWORD.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a full set of iron tools", "", 3, List.of(tools, obtain), "obtain_iron_toolset", List.of(Items.IRON_HOE.getDefaultStack(),  Items.IRON_AXE.getDefaultStack(), Items.IRON_PICKAXE.getDefaultStack(), Items.IRON_SHOVEL.getDefaultStack(), Items.IRON_SWORD.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a full set of golden tools", "", 4, List.of(tools, obtain), "obtain_golden_toolset", List.of(Items.GOLDEN_HOE.getDefaultStack(),  Items.GOLDEN_AXE.getDefaultStack(), Items.GOLDEN_PICKAXE.getDefaultStack(), Items.GOLDEN_SHOVEL.getDefaultStack(), Items.GOLDEN_SWORD.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a full set of diamond tools", "", 5, List.of(tools, obtain), "obtain_diamond_toolset", List.of(Items.DIAMOND_HOE.getDefaultStack(),  Items.DIAMOND_AXE.getDefaultStack(), Items.DIAMOND_PICKAXE.getDefaultStack(), Items.DIAMOND_SHOVEL.getDefaultStack(), Items.DIAMOND_SWORD.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain every type of axe", "", 4, List.of(tools, obtain), "obtain_all_axes", List.of(Items.WOODEN_AXE.getDefaultStack(), Items.STONE_AXE.getDefaultStack(), Items.IRON_AXE.getDefaultStack(), Items.GOLDEN_AXE.getDefaultStack(), Items.DIAMOND_AXE.getDefaultStack(), Items.NETHERITE_AXE.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain every type of hoe", "", 4, List.of(tools, obtain), "obtain_all_hoes", List.of(Items.WOODEN_HOE.getDefaultStack(), Items.STONE_HOE.getDefaultStack(), Items.IRON_HOE.getDefaultStack(), Items.GOLDEN_HOE.getDefaultStack(), Items.DIAMOND_HOE.getDefaultStack(), Items.NETHERITE_HOE.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain every type of pickaxe", "", 4, List.of(tools, obtain), "obtain_all_pickaxes", List.of(Items.WOODEN_PICKAXE.getDefaultStack(), Items.STONE_PICKAXE.getDefaultStack(), Items.IRON_PICKAXE.getDefaultStack(), Items.GOLDEN_PICKAXE.getDefaultStack(), Items.DIAMOND_PICKAXE.getDefaultStack(), Items.NETHERITE_PICKAXE.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain every type of shovel", "", 4, List.of(tools, obtain), "obtain_all_shovels", List.of(Items.WOODEN_SHOVEL.getDefaultStack(), Items.STONE_SHOVEL.getDefaultStack(), Items.IRON_SHOVEL.getDefaultStack(), Items.GOLDEN_SHOVEL.getDefaultStack(), Items.DIAMOND_SHOVEL.getDefaultStack(), Items.NETHERITE_SHOVEL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain every type of sword", "", 4, List.of(tools, obtain), "obtain_all_swords", List.of(Items.WOODEN_SWORD.getDefaultStack(), Items.STONE_SWORD.getDefaultStack(), Items.IRON_SWORD.getDefaultStack(), Items.GOLDEN_SWORD.getDefaultStack(), Items.DIAMOND_SWORD.getDefaultStack(), Items.NETHERITE_SWORD.getDefaultStack())));
        //        items.add(new GoalListItem("obtain a full set of netherite tools", "", 1, List.of(tools), "obtain_netherite_toolset", List.of(Identifier.of("minecraft:textures/item/netherite_hoe.png"), Identifier.of("minecraft:textures/item/netherite_hoe.png"), Identifier.of("minecraft:textures/item/netherite_pickaxe.png"), Identifier.of("minecraft:textures/item/netherite_shovel.png"), Identifier.of("minecraft:textures/item/netherite_sword.png"))));

        // armor goals
        items.add(new GoalListBlockItem("wear a piece of chainmail armor", "", 2, List.of(armor), "wear_chainmail", List.of(Items.CHAINMAIL_HELMET.getDefaultStack(), Items.CHAINMAIL_CHESTPLATE.getDefaultStack(), Items.CHAINMAIL_LEGGINGS.getDefaultStack(), Items.CHAINMAIL_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("wear a full set of leather armor", "", 1, List.of(armor), "wear_full_leather",List.of(Items.LEATHER_HELMET.getDefaultStack(), Items.LEATHER_CHESTPLATE.getDefaultStack(), Items.LEATHER_LEGGINGS.getDefaultStack(), Items.LEATHER_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("wear a full set of iron armor", "", 3, List.of(armor), "wear_full_iron", List.of(Items.IRON_HELMET.getDefaultStack(), Items.IRON_CHESTPLATE.getDefaultStack(), Items.IRON_LEGGINGS.getDefaultStack(), Items.IRON_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("wear a full set of golden armor", "", 4, List.of(armor), "wear_full_golden", List.of(Items.GOLDEN_HELMET.getDefaultStack(), Items.GOLDEN_CHESTPLATE.getDefaultStack(), Items.GOLDEN_LEGGINGS.getDefaultStack(), Items.GOLDEN_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("wear a full set of diamond armor", "", 5, List.of(armor), "wear_full_diamond", List.of(Items.DIAMOND_HELMET.getDefaultStack(), Items.DIAMOND_CHESTPLATE.getDefaultStack(), Items.DIAMOND_LEGGINGS.getDefaultStack(), Items.DIAMOND_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("wear a piece of netherite armor", "", 6, List.of(armor), "wear_netherite", List.of(Items.NETHERITE_HELMET.getDefaultStack(), Items.NETHERITE_CHESTPLATE.getDefaultStack(), Items.NETHERITE_LEGGINGS.getDefaultStack(), Items.NETHERITE_BOOTS.getDefaultStack())));

        ItemStack red_leather_cap = Items.LEATHER_HELMET.getDefaultStack();red_leather_cap.applyChanges(ComponentChanges.builder().add(DataComponentTypes.BASE_COLOR, DyeColor.RED).build());ItemStack purple_leather_boots = Items.LEATHER_BOOTS.getDefaultStack();purple_leather_boots.applyChanges(ComponentChanges.builder().add(DataComponentTypes.BASE_COLOR, DyeColor.PURPLE).build());ItemStack green_leather_pants = Items.LEATHER_LEGGINGS.getDefaultStack();green_leather_pants.applyChanges(ComponentChanges.builder().add(DataComponentTypes.BASE_COLOR, DyeColor.GREEN).build());ItemStack orange_leather_chestplate = Items.LEATHER_CHESTPLATE.getDefaultStack();orange_leather_chestplate.applyChanges(ComponentChanges.builder().add(DataComponentTypes.BASE_COLOR, DyeColor.ORANGE).build());
        items.add(new GoalListBlockItem("wear a piece of colored armor", "", 2, List.of(armor), "wear_colored", List.of(red_leather_cap, purple_leather_boots, green_leather_pants, orange_leather_chestplate)));
        items.add(new GoalListItem("wear a trimmed armor piece", "", 3, List.of(armor), "wear_trimmed", List.of(Identifier.of("lockout-bingo:goalicon/item/armor/emerald_trimmed_iron_helmet.png"), Identifier.of("lockout-bingo:goalicon/item/armor/copper_trimmed_diamond_leggings.png"),Identifier.of("lockout-bingo:goalicon/item/armor/redstone_trimmed_gold_chestplate.png"), Identifier.of("lockout-bingo:goalicon/item/armor/quartz_trimmed_netherite_boots.png")) ));
        items.add(new GoalListItem("wear a fully trimmed armor set", "", 3, List.of(armor, C4), "wear_trimmed_set", List.of(Identifier.of("lockout-bingo:goalicon/item/armor/emerald_trimmed_iron_helmet.png"), Identifier.of("lockout-bingo:goalicon/item/armor/copper_trimmed_diamond_leggings.png"),Identifier.of("lockout-bingo:goalicon/item/armor/redstone_trimmed_gold_chestplate.png"), Identifier.of("lockout-bingo:goalicon/item/armor/quartz_trimmed_netherite_boots.png")) ));
        items.add(new GoalListBlockItem("wear a carved pumpkin for 1 minute", "", 3, List.of(armor), "wear_pumpkin", List.of(Items.CARVED_PUMPKIN.getDefaultStack()))); // todo change time

        items.add(new GoalListBlockItem("obtain 4 types of seeds", "", 3, List.of(C4, unique, obtain), "obtain_all_seeds", List.of(Items.WHEAT_SEEDS.getDefaultStack(), Items.BEETROOT_SEEDS.getDefaultStack(), Items.MELON_SEEDS.getDefaultStack(), Items.PUMPKIN_SEEDS.getDefaultStack(), Items.TORCHFLOWER_SEEDS.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain 6 types of flowers", "", 3, List.of(C6, unique, obtain), "obtain_6_flowers", List.of(Items.POPPY.getDefaultStack(), Items.DANDELION.getDefaultStack(), Items.BLUE_ORCHID.getDefaultStack(), Items.ALLIUM.getDefaultStack(), Items.AZURE_BLUET.getDefaultStack(), Items.RED_TULIP.getDefaultStack(), Items.ORANGE_TULIP.getDefaultStack(), Items.WHITE_TULIP.getDefaultStack(),  Items.OXEYE_DAISY.getDefaultStack(), Items.CORNFLOWER.getDefaultStack(), Items.LILY_OF_THE_VALLEY.getDefaultStack(), Items.WITHER_ROSE.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain black glazed terracotta", "", 2, List.of( obtain), "obtain_black_glazed_terracotta", List.of(Items.BLACK_GLAZED_TERRACOTTA.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain all saplings", "", 4, List.of(obtain), "obtain_all_saplings", List.of(Items.OAK_SAPLING.getDefaultStack(), Items.SPRUCE_SAPLING.getDefaultStack(), Items.BIRCH_SAPLING.getDefaultStack(), Items.JUNGLE_SAPLING.getDefaultStack(), Items.ACACIA_SAPLING.getDefaultStack(), Items.DARK_OAK_SAPLING.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain 2 music disks", "", 3, List.of(C2, obtain), "obtain_2_music_disks", List.of(Items.MUSIC_DISC_11.getDefaultStack(), Items.MUSIC_DISC_13.getDefaultStack(), Items.MUSIC_DISC_BLOCKS.getDefaultStack(), Items.MUSIC_DISC_CAT.getDefaultStack(), Items.MUSIC_DISC_CHIRP.getDefaultStack(), Items.MUSIC_DISC_FAR.getDefaultStack(), Items.MUSIC_DISC_MALL.getDefaultStack(), Items.MUSIC_DISC_MELLOHI.getDefaultStack(), Items.MUSIC_DISC_STAL.getDefaultStack(), Items.MUSIC_DISC_STRAD.getDefaultStack(), Items.MUSIC_DISC_WAIT.getDefaultStack(), Items.MUSIC_DISC_WARD.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain all types of horse armor", "", 4, List.of(nether, unique, obtain), "obtain_all_harmor", List.of(Items.IRON_HORSE_ARMOR.getDefaultStack(), Items.GOLDEN_HORSE_ARMOR.getDefaultStack(), Items.DIAMOND_HORSE_ARMOR.getDefaultStack())));

        items.add(new GoalListBlockItem("eat 5 unique foods", "", 1, List.of(C5, eat, unique), "eat_5", List.of(Items.APPLE.getDefaultStack())));
        items.add(new GoalListBlockItem("eat 10 unique foods", "", 2, List.of(C10, eat, unique), "eat_10", List.of(Items.APPLE.getDefaultStack())));
        items.add(new GoalListBlockItem("eat 15 unique foods", "", 3, List.of(C15, eat, unique), "eat_15", List.of(Items.APPLE.getDefaultStack())));
        items.add(new GoalListBlockItem("eat 20 unique foods", "", 4, List.of(C20, eat, unique), "eat_20", List.of(Items.APPLE.getDefaultStack())));
        items.add(new GoalListBlockItem("eat suspicious stew", "", 1, List.of(eat), "eat_suspicious_stew", List.of(Items.SUSPICIOUS_STEW.getDefaultStack())));
        items.add(new GoalListBlockItem("eat glow berries", "", 3, List.of(eat), "eat_glow_berries", List.of(Items.GLOW_BERRIES.getDefaultStack())));
        items.add(new GoalListBlockItem("eat rabbit stew", "", 2, List.of(eat), "eat_rabbit_stew", List.of(Items.RABBIT_STEW.getDefaultStack())));
        items.add(new GoalListBlockItem("eat beetroot soup", "", 4, List.of(eat), "eat_beetroot_soup", List.of(Items.BEETROOT_SOUP.getDefaultStack())));
        items.add(new GoalListBlockItem("eat honey bottle", "", 4, List.of(eat), "eat_honey_bottle", List.of(Items.HONEY_BOTTLE.getDefaultStack())));
//        items.add(new GoalListItem("eat honeycomb", "", 1, List.of(), "eat_honeycomb", List.of(Identifier.of("minecraft:textures/item/honeycomb.png"))));
        items.add(new GoalListBlockItem("eat golden apple", "", 3, List.of(eat), "eat_golden_apple", List.of(Items.GOLDEN_APPLE.getDefaultStack())));
        items.add(new GoalListBlockItem("eat a cookie", "", 2, List.of(eat), "eat_cookie", List.of(Items.COOKIE.getDefaultStack())));
        items.add(new GoalListBlockItem("eat pumpkin pie", "", 2, List.of(eat), "eat_pumpkin_pie", List.of(Items.PUMPKIN_PIE.getDefaultStack())));
        items.add(new GoalListBlockItem("eat poisonous potato", "", 1, List.of(eat), "eat_poisonous_potato", List.of(Items.POISONOUS_POTATO.getDefaultStack())));
        items.add(new GoalListBlockItem("eat dried kelp", "", 1, List.of(eat), "eat_dried_kelp", List.of(Items.DRIED_KELP.getDefaultStack())));
        items.add(new GoalListBlockItem("eat chorus fruit", "", 4, List.of(eat, end), "eat_chorus_fruit", List.of(Items.CHORUS_FRUIT.getDefaultStack())));

        items.add(new GoalListItem("breed hoglins", "", 3, List.of(breed, nether), "breed_hoglins", List.of(Identifier.of("lockout-bingo:goalicon/entity/hoglin.png"))));
        items.add(new GoalListItem("breed striders", "", 3, List.of(breed, nether), "breed_striders", List.of(Identifier.of("lockout-bingo:goalicon/entity/strider.png"))));
        items.add(new GoalListItem("breed axolotls", "", 4, List.of(breed), "breed_axolotls", List.of(Identifier.of("lockout-bingo:goalicon/entity/axolotl.png"))));
        items.add(new GoalListItem("breed bees", "", 1, List.of(breed), "breed_bees", List.of(Identifier.of("lockout-bingo:goalicon/entity/bee.png"))));
        items.add(new GoalListItem("breed goats", "", 3, List.of(breed), "breed_goats", List.of(Identifier.of("lockout-bingo:goalicon/entity/goat.png"))));
        items.add(new GoalListItem("breed foxes", "", 3, List.of(breed), "breed_foxes", List.of(Identifier.of("lockout-bingo:goalicon/entity/fox.png"))));
        items.add(new GoalListItem("breed cats", "", 2, List.of(breed), "breed_cats", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(new GoalListItem("breed wolves", "", 2, List.of(breed), "breed_wolves", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));
        items.add(new GoalListItem("breed parrots", "", 2, List.of(breed), "breed_parrots", List.of(Identifier.of("lockout-bingo:goalicon/entity/parrot.png"))));
        items.add(new GoalListItem("breed rabbits", "", 3, List.of(breed), "breed_rabbits", List.of(Identifier.of("lockout-bingo:goalicon/entity/rabbit.png"))));
        items.add(new GoalListItem("breed llamas", "", 3, List.of(breed), "breed_llamas", List.of(Identifier.of("lockout-bingo:goalicon/entity/lama.png"))));
        items.add(new GoalListItem("breed horses", "", 3, List.of(breed), "breed_horses", List.of(Identifier.of("lockout-bingo:goalicon/entity/horse.png"))));
        items.add(new GoalListItem("breed pigs", "", 1, List.of(breed), "breed_pigs", List.of(Identifier.of("lockout-bingo:goalicon/entity/pig.png"))));
        items.add(new GoalListItem("breed turtles", "", 2, List.of(breed), "breed_turtles", List.of(Identifier.of("lockout-bingo:goalicon/entity/turtle.png"))));
        items.add(new GoalListItem("breed a mule", "", 4, List.of(breed), "breed_mule", List.of(Identifier.of("lockout-bingo:goalicon/entity/mule.png"))));
        items.add(new GoalListItem("breed chickens", "", 1, List.of(breed), "breed_chickens", List.of(Identifier.of("lockout-bingo:goalicon/entity/chicken.png"))));
        items.add(new GoalListItem("breed cows", "", 1, List.of(breed), "breed_cows", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))));



        items.add(new GoalListItem("breed 5 unique animals", "", 1, List.of(breed, C5, unique), "breed_5", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))));
        items.add(new GoalListItem("breed 10 unique animals", "", 3, List.of(breed, C10, unique), "breed_10", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))));
        items.add(new GoalListItem("breed 15 unique animals", "", 4, List.of(breed, C15, unique), "breed_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))));

        items.add(new GoalListItem("kill a piglin brute", "", 4, List.of(kill, nether), "kill_piglin_brute", List.of(Identifier.of("lockout-bingo:goalicon/entity/piglin_brute.png"))));
        items.add(new GoalListItem("kill zoglin", "", 3, List.of(kill, nether), "kill_zoglin", List.of(Identifier.of("lockout-bingo:goalicon/entity/zoglin.png"))));
        items.add(new GoalListItem("kill an endermite", "", 4, List.of(kill), "kill_endermite", List.of(Identifier.of("lockout-bingo:goalicon/entity/endermite.png"))));
        items.add(new GoalListItem("kill a silverfish", "", 3, List.of(kill), "kill_silverfish", List.of(Identifier.of("lockout-bingo:goalicon/entity/silverfish.png"))));
        items.add(new GoalListItem("kill a breeze", "", 4, List.of(kill), "kill_breeze", List.of(Identifier.of("lockout-bingo:goalicon/entity/breeze.png")))); // Assuming 'breeze' is a fictional entity for the example
        items.add(new GoalListItem("kill an elder guardian", "", 3, List.of(kill), "kill_elder_guardian", List.of(Identifier.of("lockout-bingo:goalicon/entity/elder_guardian.png"))));
        items.add(new GoalListItem("kill a ghast", "", 2, List.of(kill, nether), "kill_ghast", List.of(Identifier.of("lockout-bingo:goalicon/entity/ghast.png"))));
        items.add(new GoalListItem("kill a zombie villager", "", 2, List.of(kill), "kill_zillager", List.of(Identifier.of("lockout-bingo:goalicon/entity/zillager.png"))));
        items.add(new GoalListItem("kill a witch", "", 3, List.of(kill), "kill_witch", List.of(Identifier.of("lockout-bingo:goalicon/entity/witch.png"))));
        items.add(new GoalListItem("kill a stray", "", 3, List.of(kill), "kill_stray", List.of(Identifier.of("lockout-bingo:goalicon/entity/stray.png"))));
        items.add(new GoalListItem("kill a snow golem", "", 3, List.of(kill), "kill_snow_golem", List.of(Identifier.of("lockout-bingo:goalicon/entity/snow_golem.png"))));
        items.add(new GoalListItem("kill 7 unique mobs", "", 1, List.of(kill, C7, unique), "kill_7", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"))));
        items.add(new GoalListItem("kill 10 unique mobs", "", 3, List.of(kill, C10, unique), "kill_10", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"))));
        items.add(new GoalListItem("kill 15 unique mobs", "", 4, List.of(kill, C15, unique), "kill_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"))));
        items.add(new GoalListItem("kill jeb_", "", 4, List.of(kill), "kill_jeb_", List.of(Identifier.of("lockout-bingo:goalicon/entity/jeb.png"))));

        items.add(new GoalListItem("kill 100 mobs", "", 4, List.of(kill, C100), "kill_100", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"), Identifier.of("lockout-bingo:goalicon/entity/spider.png"), Identifier.of("lockout-bingo:goalicon/entity/bee.png"), Identifier.of("lockout-bingo:goalicon/entity/sheep.png"), Identifier.of("lockout-bingo:goalicon/entity/drowned.png"), Identifier.of("lockout-bingo:goalicon/entity/enderman.png"))));
        items.add(new GoalListItem("kill 30 undead mobs", "", 3, List.of(kill, C30), "undead_30", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"), Identifier.of("lockout-bingo:goalicon/entity/zoglin.png"), Identifier.of("lockout-bingo:goalicon/entity/skeleton.png"), Identifier.of("lockout-bingo:goalicon/entity/zombified_piglin.png"), Identifier.of("lockout-bingo:goalicon/entity/phantom.png"), Identifier.of("lockout-bingo:goalicon/entity/bogged.png"), Identifier.of("lockout-bingo:goalicon/entity/zillager.png"))));
        items.add(new GoalListItem("kill 20 arthropods", "", 3, List.of(kill, C20), "arthropod_20", List.of(Identifier.of("lockout-bingo:goalicon/entity/spider.png"), Identifier.of("lockout-bingo:goalicon/entity/bee.png"), Identifier.of("lockout-bingo:goalicon/entity/endermite.png"), Identifier.of("lockout-bingo:goalicon/entity/cave_spider.png"), Identifier.of("lockout-bingo:goalicon/entity/silverfish.png"))));

        //use entity goals
        items.add(new GoalListItem("shear a bogged", "", 2, List.of(), "shear_bogged", List.of(Identifier.of("lockout-bingo:goalicon/entity/bogged.png"))));


        items.add(new GoalListItem("enter the nether", "", 2, List.of(advancement), "nether_adv", List.of(Identifier.of("lockout-bingo:goalicon/structures/nether_portal.png"))));
        items.add(new GoalListBlockItem("enter the end", "", 3, List.of(advancement, nether), "end_adv", List.of(Items.END_PORTAL_FRAME.getDefaultStack())));
        items.add(new GoalListBlockItem("use an enchanting table", "", 3, List.of(advancement), "enchanter_adv", List.of(Items.ENCHANTING_TABLE.getDefaultStack())));
        items.add(new GoalListBlockItem("zombie doctor", "", 4, List.of(advancement), "zombie_doc_adv", List.of(Items.GOLDEN_APPLE.getDefaultStack())));
        items.add(new GoalListBlockItem("enter the stronghold", "", 3, List.of(advancement, nether), "eye_spy_adv", List.of(Items.ENDER_EYE.getDefaultStack())));
        items.add(new GoalListBlockItem("kill a ghast with its own fireball", "", 3, List.of(advancement, nether), "return_to_sender_adv", List.of(Items.FIRE_CHARGE.getDefaultStack())));
        items.add(new GoalListBlockItem("find a bastion remnant", "", 3, List.of(advancement, nether), "those_were_the_days_adv", List.of(Items.POLISHED_BLACKSTONE_BRICKS.getDefaultStack()))); // This is a fictional texture path for a structure
        items.add(new GoalListBlockItem("find a nether fortress", "", 3, List.of(advancement, nether), "terrible_fortress_adv", List.of(Items.NETHER_BRICKS.getDefaultStack())));
        items.add(new GoalListBlockItem("travel 1km in the nether", "", 4, List.of(advancement, nether), "subspace_bubble_adv", List.of(Items.MAP.getDefaultStack())));
        items.add(new GoalListBlockItem("distract a piglin with gold", "", 2, List.of(advancement, nether), "oh_shiny_adv", List.of(Items.GOLD_INGOT.getDefaultStack())));
        items.add(new GoalListItem("ride a strider with a warped fungus on a stick", "", 4, List.of(ride, advancement, nether), "this_boat_has_legs_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/strider.png"))));
        items.add(new GoalListBlockItem("use a lodestone", "", 4, List.of(advancement, nether), "country_lode_adv", List.of(Items.LODESTONE.getDefaultStack())));
        items.add(new GoalListBlockItem("use a respawn anchor", "", 3, List.of(advancement, nether), "use_respawn_anchor_adv", List.of(Items.RESPAWN_ANCHOR.getDefaultStack())));
        items.add(new GoalListBlockItem("get a wither skeleton skull", "", 5, List.of(advancement, nether, obtain), "spooky_scary_skeletons_adv", List.of(Items.WITHER_SKELETON_SKULL.getDefaultStack())));
        items.add(new GoalListBlockItem("travel to every nether biome", "", 3, List.of(advancement, nether), "hot_tourist_adv", List.of(Items.NETHERITE_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("great view from above", "", 5, List.of(advancement, end), "great_view_adv", List.of(Items.ENDER_PEARL.getDefaultStack())));
        items.add(new GoalListBlockItem("what a deal", "", 1, List.of(advancement, village), "trade_adv", List.of(Items.EMERALD.getDefaultStack())));
        items.add(new GoalListBlockItem("slide in a honey block to break your fall", "", 5, List.of(advancement), "sticky_adv", List.of(Items.HONEY_BLOCK.getDefaultStack())));
        items.add(new GoalListBlockItem("kill a mob near a sculk catalyst", "", 3, List.of(advancement), "spreads_adv", List.of(Items.SCULK_CATALYST.getDefaultStack())));
        items.add(new GoalListItem("make a golem", "", 2, List.of(advancement), "hired_help_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/iron_golem.png"))));
        items.add(new GoalListBlockItem("arbalistic", "", 4, List.of(advancement), "arbalistic_adv", List.of(Items.CROSSBOW.getDefaultStack())));
        items.add(new GoalListBlockItem("walk on powdered snow with leather boots", "", 3, List.of(advancement), "walk_snow_adv", List.of(Items.LEATHER_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("shoot a bullseye with a bow and arrow", "", 2, List.of(advancement, redstone), "bullseye_adv", List.of(Items.TARGET.getDefaultStack())));
        items.add(new GoalListBlockItem("total beelocation", "", 3, List.of(advancement, silk_touch), "beelocation_adv", List.of(Items.BEE_NEST.getDefaultStack())));
        items.add(new GoalListBlockItem("get a tadpole in a bucket", "", 4, List.of(breed, advancement), "tadpole_bucket_adv", List.of(Items.TADPOLE_BUCKET.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a sniffer egg", "", 3, List.of(advancement, obtain), "sniffer_egg_adv", List.of(Items.SNIFFER_EGG.getDefaultStack())));
        items.add(new GoalListItem("tame 4 unique types of cats", "", 3, List.of(C4, tame, unique), "catalogue_4_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(new GoalListItem("time 3 unique types of wolves", "", 4, List.of(C3, tame, unique), "whole_pack_3_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));
        items.add(new GoalListBlockItem("tactical fishing", "", 1, List.of(advancement), "tactical_fishing_adv", List.of(Items.TROPICAL_FISH_BUCKET.getDefaultStack())));
        items.add(new GoalListBlockItem("scrape the wax off a copper block", "", 3, List.of(advancement), "wax_off_adv", List.of(Items.STONE_AXE.getDefaultStack())));
        items.add(new GoalListBlockItem("axolotl in a bucket", "", 3, List.of(advancement), "axolotl_bucket_adv", List.of(Items.AXOLOTL_BUCKET.getDefaultStack())));
        items.add(new GoalListBlockItem("Crafters crafting crafters", "", 3, List.of(advancement, redstone), "crafters_adv", List.of(Items.CRAFTER.getDefaultStack())));
        items.add(new GoalListBlockItem("use a trial key on a vault", "", 4, List.of(advancement), "trial_key_adv", List.of(Items.TRIAL_KEY.getDefaultStack())));
        items.add(new GoalListItem("use wolf armor on a wolf", "", 3, List.of(advancement), "wolf_armor_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/wolf_armor.png"))));
        items.add(new GoalListBlockItem("Sound of music: play a music disk in a meadow", "", 3, List.of(advancement), "sound_of_music_adv", List.of(Items.JUKEBOX.getDefaultStack())));
        items.add(new GoalListBlockItem("catch a fish", "", 1, List.of(advancement), "fishy_business_adv", List.of(Items.FISHING_ROD.getDefaultStack())));
        items.add(new GoalListItem("kill the dragon", "", 5, List.of(advancement, kill), "dragon_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/ender_dragon.png"))));
        items.add(new GoalListItem("get the sinper duel advancement", "", 4, List.of(advancement), "sniper_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/skeleton.png"))));
        items.add(new GoalListBlockItem("get any spyglass advancement", "", 3, List.of(advancement), "spyglass_adv", List.of(Items.SPYGLASS.getDefaultStack())));

        items.add(new GoalListItem("complete 15 advancements", "", 3, List.of(C15, advancement), "adv_15", List.of(Identifier.of("lockout-bingo:goalicon/advancement/challenge_done.png"))));
        items.add(new GoalListItem("complete 25 advancements", "", 3, List.of(C25, advancement), "adv_25", List.of(Identifier.of("lockout-bingo:goalicon/advancement/challenge_done.png"))));
        items.add(new GoalListItem("complete 35 advancements", "", 3, List.of(C35, advancement), "adv_35", List.of(Identifier.of("lockout-bingo:goalicon/advancement/challenge_done.png"))));
        // biome goals
        items.add(new GoalListItem("find an ice spikes biome", "", 3, List.of(biomes), "biome_ice_spikes", List.of(Identifier.of("lockout-bingo:goalicon/biome/ice_spikes.png"))));
        items.add(new GoalListItem("find a badlands biome", "", 3, List.of(biomes), "biome_badlands", List.of(Identifier.of("lockout-bingo:goalicon/biome/badlands.png"))));
        items.add(new GoalListBlockItem("find 15 unique biomes", "", 2, List.of(C15, biomes, unique), "biomes_15", List.of(Items.DIAMOND_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("find 20 unique biomes", "", 3, List.of(C20, biomes, unique), "biomes_20", List.of(Items.DIAMOND_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("find 25 unique biomes", "", 4, List.of(C25, biomes, unique), "biomes_25", List.of(Items.DIAMOND_BOOTS.getDefaultStack())));
        items.add(new GoalListBlockItem("find 30 unique biomes", "", 5, List.of(C30, biomes, unique), "biomes_30", List.of(Items.DIAMOND_BOOTS.getDefaultStack())));

        items.add(new GoalListItem("die by golem", "", 1, List.of(die), "die_golem", List.of(Identifier.of("lockout-bingo:goalicon/entity/iron_golem.png"))));
        items.add(new GoalListItem("die by llama", "", 2, List.of(die), "die_llama", List.of(Identifier.of("lockout-bingo:goalicon/entity/lama.png"))));
        items.add(new GoalListItem("die by bee sting", "", 1, List.of(die), "die_bee", List.of(Identifier.of("lockout-bingo:goalicon/entity/bee.png"))));

        items.add(new GoalListBlockItem("die by trident", "", 2, List.of(die), "die_trident", List.of(Items.TRIDENT.getDefaultStack())));
        items.add(new GoalListBlockItem("die by sweet berry bush", "", 2, List.of(die), "die_sweet_berry_bush", List.of(Items.SWEET_BERRIES.getDefaultStack())));
        items.add(new GoalListBlockItem("die by cactus", "", 3, List.of(die), "die_cactus", List.of(Items.CACTUS.getDefaultStack())));
        items.add(new GoalListBlockItem("die by anvil", "", 3, List.of(die), "die_anvil", List.of(Items.ANVIL.getDefaultStack())));
        items.add(new GoalListItem("die by falling stalactite", "", 2, List.of(die), "die_stalactite", List.of(Identifier.of("lockout-bingo:goalicon/block/stalactite.png"))));
        items.add(new GoalListItem("die by falling on a stalagmite", "", 2, List.of(die), "die_stalagmite", List.of(Identifier.of("lockout-bingo:goalicon/block/stalagmite.png"))));
        items.add(new GoalListBlockItem("die by fireworks", "", 3, List.of(die), "die_fireworks", List.of(Items.FIREWORK_ROCKET.getDefaultStack())));
        items.add(new GoalListBlockItem("freeze to death", "", 2, List.of(die), "die_freeze", List.of(Items.POWDER_SNOW_BUCKET.getDefaultStack())));
        items.add(new GoalListBlockItem("die by intentional game design", "", 2, List.of(die), "die_bed", List.of(Items.RED_BED.getDefaultStack(), Items.RESPAWN_ANCHOR.getDefaultStack())));
        items.add(new GoalListBlockItem("die by tnt minecart", "", 3, List.of(die), "die_tnt_minecart", List.of(Items.TNT_MINECART.getDefaultStack())));

        // ride goals
        items.add(new GoalListItem("ride a pig", "", 2, List.of(ride), "ride_pig", List.of(Identifier.of("lockout-bingo:goalicon/entity/pig.png"))));
        items.add(new GoalListItem("ride a horse", "", 1, List.of(ride), "ride_horse", List.of(Identifier.of("lockout-bingo:goalicon/entity/horse.png"))));
        items.add(new GoalListBlockItem("ride a minecart", "", 3, List.of(), "ride_minecart", List.of(Items.MINECART.getDefaultStack())));

        // damage goals
        items.add(new GoalListBlockItem("deal 200 damage", "", 2, List.of(C200), "damage_200", List.of(Items.DIAMOND_SWORD.getDefaultStack())));
        items.add(new GoalListBlockItem("deal 500 damage", "", 3, List.of(C500), "damage_500", List.of(Items.DIAMOND_SWORD.getDefaultStack())));

        items.add(new GoalListItem("take 200 damage", "", 2, List.of(C200), "damage_take_200", List.of(Identifier.of("lockout-bingo:goalicon/other/damage.png"))));

        // use goals
        items.add(new GoalListBlockItem("use a smithing table", "", 3, List.of(use), "use_smithing_table", List.of(Items.SMITHING_TABLE.getDefaultStack())));
        items.add(new GoalListBlockItem("use a grindstone", "", 2, List.of(use), "use_grindstone", List.of(Items.GRINDSTONE.getDefaultStack())));
        items.add(new GoalListBlockItem("use a blast furnace", "", 2, List.of(use), "use_blast_furnace", List.of(Items.BLAST_FURNACE.getDefaultStack())));
        items.add(new GoalListBlockItem("use a cartography table", "", 2, List.of(use), "use_cartography_table", List.of(Items.CARTOGRAPHY_TABLE.getDefaultStack())));
        items.add(new GoalListBlockItem("use a loom", "", 3, List.of(use), "use_loom", List.of(Items.LOOM.getDefaultStack())));
        items.add(new GoalListBlockItem("use a stonecutter", "", 1, List.of(use), "use_stonecutter", List.of(Items.STONECUTTER.getDefaultStack())));
        items.add(new GoalListBlockItem("use an anvil", "", 3, List.of(use), "use_anvil", List.of(Items.ANVIL.getDefaultStack())));
        items.add(new GoalListBlockItem("use a composter", "", 1, List.of(use), "use_composter", List.of(Items.COMPOSTER.getDefaultStack())));


        // mine goals
        items.add(new GoalListBlockItem("mine emerald ore", "", 5, List.of(), "mine_emerald", List.of(Items.EMERALD_ORE.getDefaultStack())));
        items.add(new GoalListBlockItem("mine diamond ore", "", 3, List.of(), "mine_diamond", List.of(Items.DIAMOND_ORE.getDefaultStack())));
        items.add(new GoalListBlockItem("mine mob spawner", "", 3, List.of(), "mine_spawner", List.of(Items.SPAWNER.getDefaultStack())));
        items.add(new GoalListBlockItem("mine a turtle egg", "", 4, List.of(), "mine_turtle_egg", List.of(Items.TURTLE_EGG.getDefaultStack())));

        // tame goals
        items.add(new GoalListItem("tame a cat", "", 2, List.of(tame), "tame_cat", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(new GoalListItem("tame a wolf", "", 3, List.of(tame), "tame_wolf", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));
        items.add(new GoalListItem("tame a horse", "", 1, List.of(tame), "tame_horse", List.of(Identifier.of("lockout-bingo:goalicon/entity/horse.png"))));
        items.add(new GoalListItem("tame a llama", "", 3, List.of(tame), "tame_llama", List.of(Identifier.of("lockout-bingo:goalicon/entity/lama.png"))));
        items.add(new GoalListItem("tame a parrot", "", 4, List.of(tame), "tame_parrot", List.of(Identifier.of("lockout-bingo:goalicon/entity/parrot.png"))));

        // effect goals
        items.add(new GoalListItem("get poisoned", "", 2, List.of(effect), "effect_poison", List.of(Identifier.of("lockout-bingo:goalicon/effect/poison.png"))));
        items.add(new GoalListItem("get weakness", "", 3, List.of(effect), "effect_weakness", List.of(Identifier.of("lockout-bingo:goalicon/effect/weakness.png"))));
        items.add(new GoalListItem("get jump boost", "", 3, List.of(effect), "effect_jump_boost", List.of(Identifier.of("lockout-bingo:goalicon/effect/jump_boost.png"))));
        items.add(new GoalListItem("get fire resistance", "", 4, List.of(effect, nether), "effect_fire_resistance", List.of(Identifier.of("lockout-bingo:goalicon/effect/fire_resistance.png"))));
        items.add(new GoalListItem("get infested", "", 3, List.of(effect, nether), "effect_infested", List.of(Identifier.of("lockout-bingo:goalicon/effect/infested.png"))));
        items.add(new GoalListItem("get glowing", "", 3, List.of(effect, nether), "effect_glowing", List.of(Identifier.of("lockout-bingo:goalicon/effect/glowing.png"))));
        items.add(new GoalListItem("get nausea", "", 3, List.of(effect), "effect_nausea", List.of(Identifier.of("lockout-bingo:goalicon/effect/nausea.png")))); // todo iconm
        items.add(new GoalListItem("get absorption", "", 3, List.of(effect), "effect_absorption", List.of(Identifier.of("lockout-bingo:goalicon/effect/absorption.png")))); // todo icon
        items.add(new GoalListItem("get mining fatigue", "", 3, List.of(effect), "effect_mining_fatigue", List.of(Identifier.of("lockout-bingo:goalicon/effect/mining_fatigue.png")))); // todo icon
        items.add(new GoalListItem("get bad omen", "", 3, List.of(effect), "effect_bad_omen", List.of(Identifier.of("lockout-bingo:goalicon/effect/bad_omen.png")))); // todo icon
        items.add(new GoalListBlockItem("have 3 active effects at once", "", 3, List.of(effect, C3), "effect_3", List.of(Items.MILK_BUCKET.getDefaultStack())));
        items.add(new GoalListBlockItem("have 6 active effects at once", "", 4, List.of(effect, C6), "effect_6", List.of(Items.MILK_BUCKET.getDefaultStack())));
        items.add(new GoalListBlockItem("dont get any effects", "", 2, List.of(effect, dont), "dont_effect", List.of(Items.MILK_BUCKET.getDefaultStack())));
        items.add(new GoalListItem("dont get glowing", "", 2, List.of(effect, dont), "dont_glowing", List.of(Identifier.of("lockout-bingo:goalicon/effect/glowing.png"))));
        items.add(new GoalListBlockItem("remove all effects using milk", "", 2, List.of(effect), "milk_effect", List.of(Items.MILK_BUCKET.getDefaultStack())));



        items.add(new GoalListBlockItem("break 5 pickaxes", "", 2, List.of(C5, broken), "break_5_pickaxes", List.of(Items.WOODEN_PICKAXE.getDefaultStack(), Items.STONE_PICKAXE.getDefaultStack(), Items.IRON_PICKAXE.getDefaultStack(), Items.GOLDEN_PICKAXE.getDefaultStack(), Items.DIAMOND_PICKAXE.getDefaultStack(), Items.NETHERITE_PICKAXE.getDefaultStack())));
        items.add(new GoalListBlockItem("break a diamond pickaxe", "", 4, List.of(broken), "break_diamond", List.of(Items.DIAMOND_PICKAXE.getDefaultStack())));


        items.add(new GoalListBlockItem("get them in a bucket !!", "", 5, List.of(), "get_them_in_a_bucket", List.of(Items.PUFFERFISH_BUCKET.getDefaultStack())));

        // level goals
        items.add(new GoalListItem("reach level 15", "", 2, List.of(lvl, C15), "lvl_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))));
        items.add(new GoalListItem("reach level 30", "", 4, List.of(lvl, C30), "lvl_30", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))));


        // position goals
        items.add(new GoalListBlockItem("reach bedrock", "", 1, List.of(), "reach_bedrock", List.of(Items.BEDROCK.getDefaultStack())));
        items.add(new GoalListItem("reach the sky limit", "", 1, List.of(), "reach_sky_limit", List.of(Identifier.of("lockout-bingo:goalicon/item/sun.png"))));
        items.add(new GoalListItem("fall for 300 blocks", "", 2, List.of(C300), "fall_300", List.of(Identifier.of("lockout-bingo:goalicon/other/fall.png"))));

        //wool goals
        items.add(new GoalListBlockItem("obtain a stack of lime wool", "", 3, List.of(wool, C64, obtain), "64_lime_wool", List.of(Items.LIME_WOOL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a stack of gray wool", "", 3, List.of(wool, C64, obtain), "64_gray_wool", List.of(Items.GRAY_WOOL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a stack of cyan wool", "", 3, List.of(wool, C64, obtain), "64_cyan_wool", List.of(Items.CYAN_WOOL.getDefaultStack())));
        items.add(new GoalListBlockItem("obtain a stack of brown wool", "", 3, List.of(wool, C64, obtain), "64_brown_wool", List.of(Items.BROWN_WOOL.getDefaultStack())));

        items.add(new GoalListBlockItem("duplicate a smithing template", "", 3, List.of(C2), "duplicate_smithing_template", List.of(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE.getDefaultStack()))); // todo nog bij voegen

        // movement goals
        items.add(new GoalListItem("sprint 1 km", "", 1, List.of(km1), "sprint_1km", List.of(Identifier.of("lockout-bingo:goalicon/effect/speed.png"))));
        items.add(new GoalListItem("swim 1 km", "", 3, List.of(km1), "swim_1km", List.of(Identifier.of("lockout-bingo:goalicon/effect/dolphins_grace.png"))));
        items.add(new GoalListItem("opponent sneaks", "", 2, List.of(dont), "dont_crouch", List.of(Identifier.of("lockout-bingo:goalicon/other/crouch.png"))));
        items.add(new GoalListItem("opponent jumps", "", 3, List.of(dont), "dont_jump", List.of(Identifier.of("lockout-bingo:goalicon/other/jump.png"))));


        // brew goals
        items.add(new GoalListItem("brew a potion of invisibility", "", 4, List.of(brew, nether), "brew_invis", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/invis.png"))));
        items.add(new GoalListItem("brew a potion of regeneration", "", 4, List.of(brew, nether), "brew_regen", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/regen.png"))));
        items.add(new GoalListItem("brew a potion of oozing", "", 4, List.of(brew, nether), "brew_oozing", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/oozing.png"))));
        items.add(new GoalListItem("brew a potion of weaving", "", 4, List.of(brew, nether), "brew_weaving", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/weaving.png"))));
        items.add(new GoalListItem("brew a potion of leaping", "", 4, List.of(brew, nether), "brew_leaping", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/leaping.png"))));
        items.add(new GoalListItem("brew a potion of healing", "", 4, List.of(brew, nether), "brew_healing", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/healing.png")))); // todo icons
        items.add(new GoalListItem("brew a potion of harming", "", 4, List.of(brew, nether), "brew_harming", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/harming.png")))); // todo icons
        items.add(new GoalListItem("brew a potion of fire resistance", "", 4, List.of(brew, nether), "brew_fire_resistance", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/fire_resistance.png")))); // todo icons
        items.add(new GoalListItem("brew a potion of water breathing", "", 4, List.of(brew, nether), "brew_water_breathing", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/water_breathing.png"))));  // todo icons
        items.add(new GoalListItem("brew a potion of swiftness", "", 4, List.of(brew, nether), "brew_swiftness", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/swiftness.png"))));  // todo icons

        // inventory goals
        items.add(new GoalListBlockItem("fill inventory with unique items", "", 3, List.of(unique), "fill_inventory_unique", List.of(Items.CHEST.getDefaultStack()))); // todo icon
        items.add(new GoalListBlockItem("fill your inventory", "", 1, List.of(), "fill_inventory", List.of(Items.CHEST.getDefaultStack()))); // todo icon


        items.add(new GoalListItem("empty your hunger bar", "", 1, List.of(), "empty_hunger", List.of(Identifier.of("lockout-bingo:goalicon/effect/hunger.png"))));

        // more goals
        items.add(new GoalListItem("get more lvls than opponent", "", 1, List.of(more), "more_lvl", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))));
        items.add(new GoalListBlockItem("get more hoppers than opponent", "", 2, List.of(more), "more_hoppers", List.of(Items.HOPPER.getDefaultStack())));
        items.add(new GoalListBlockItem("get more dried kelp blocks than opponent", "", 3, List.of(more), "more_hoppers", List.of(Items.DRIED_KELP_BLOCK.getDefaultStack())));
        items.add(new GoalListBlockItem("get more white concrete than opponent", "", 3, List.of(more), "more_hoppers", List.of(Items.WHITE_CONCRETE.getDefaultStack())));

        // break goals
        


        LockoutLogger.log("Registered " + items.size() + " goals");
        int nether_count = (int) items.stream().filter(item -> item.tags.contains(nether)).count();
        int end_count = (int) items.stream().filter(item -> item.tags.contains(end)).count();
        LockoutLogger.log("Nether goals: " + nether_count);
        LockoutLogger.log("End goals: " + end_count);
    }

    public static GoalItemRegistry getInstance() {
        if (instance == null) {
            instance = new GoalItemRegistry();
        }
        return instance;
    }

    public static GoalListItem getGoal(String id) throws NoSuchElementException {
        GoalListItem goal = getInstance().items.stream().filter(item -> item.id.equals(id)).findFirst().orElse(null);
        if (goal == null) {
            throw new NoSuchElementException("Goal with id " + id + " not found");
        }
        return goal;
    }

    public static int goalCount() {
        return getInstance().items.size();
    }



}
