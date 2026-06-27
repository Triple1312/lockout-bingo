package net.abrikoos.lockout_bingo.server.goals;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.util.BlockoutList;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import java.util.List;
import java.util.NoSuchElementException;

import static net.abrikoos.lockout_bingo.server.goals.LockoutGoalTag.*;

public class GoalItemRegistry {
    public BlockoutList<GoalListItemV2> items;

    private static GoalItemRegistry instance;

    public GoalItemRegistry() {
        items = new BlockoutList<>();

        items.add(GoalListItemV2.createStackedString("Opponent obtains crafting table", "An enemy obtains a crafting table", 3, List.of(dont), "no_crafting_table", List.of("crafting_table")));
        items.add(GoalListItemV2.createStackedString("Opponent obtains obsidian", "opponent obtains obsidian", 2, List.of(dont), "no_obsidian", List.of("obsidian")));
        items.add(GoalListItemV2.createStackedString("Opponent obtains netherrack", "opponent obtains netherrack", 4, List.of(dont), "no_netherrack", List.of("netherrack")));
        items.add(GoalListItemV2.createStackedString("Opponent obtains seeds", "opponent obtains seeds", 2, List.of(dont), "no_seeds", List.of("wheat_seeds"))); // todo mayba all seeds?
        items.add(GoalListItemV2.createStackedString("Opponent gets hit by a snowball", "opponent gets hit by a snowball", 2, List.of(dont), "snowball_hit", List.of("snowball")));
        items.add(GoalListItemV2.createImaged("Opponent takes fall damage", "opponent takes fall damage", 2, List.of(dont), "no_fall", List.of(Identifier.of("lockout-bingo:goalicon/other/fall_damage.png")))); // todo icon
        items.add(GoalListItemV2.createImaged("Opponent dies", "", 2, List.of(dont, die), "no_die", List.of(Identifier.of("lockout-bingo:goalicon/other/opponent.png")) ));
        items.add(GoalListItemV2.createImaged("Opponent dies 3 times", "", 2, List.of(dont, die, C3), "no_die_3", List.of(Identifier.of("lockout-bingo:goalicon/other/opponent.png"))).addBottomRight("3"));
        items.add(GoalListItemV2.createImaged("Opponent catches fire", "", 3, List.of(dont), "no_fire", List.of(Identifier.of("lockout-bingo", "goalicon/other/fire.png"))));

        items.add(GoalListItemV2.createStackedString("obtain end crystal", "", 3, List.of(nether, obtain), "obtain_end_crystal", List.of("end_crystal")));
        items.add(GoalListItemV2.createStackedString("obtain bell", "", 2, List.of(village, obtain), "obtain_bell", List.of("bell")));
        items.add(GoalListItemV2.createImaged("dont touch water", "dont touch water", 1, List.of(dont), "no_water", List.of(Identifier.of("lockout-bingo:goalicon/block/water.png")))); // todo icon
        items.add(GoalListItemV2.createStackedString("obtain a bottle o enchanting", "obtain bottle o enchanting", 1, List.of(obtain), "obtain_bottle_o_enchanting", List.of("experience_bottle")));
        items.add(GoalListItemV2.createStackedString("obtain a slime block", "", 1, List.of(obtain), "obtain_slime_block", List.of("slime_block")));
        items.add(GoalListItemV2.createStackedString("obtain a cake", "", 3, List.of( obtain), "obtain_cake", List.of("cake")));
        items.add(GoalListItemV2.createStackedString("obtain a soul lantern", "", 2, List.of(nether, obtain), "obtain_soul_lantern", List.of("soul_lantern")));
        items.add(GoalListItemV2.createStackedString("obtain heart of the sea", "", 2, List.of(obtain), "obtain_heart_of_the_sea", List.of("heart_of_the_sea")));
        items.add(GoalListItemV2.createStackedString("obtain sponge", "", 4, List.of(obtain), "obtain_sponge", List.of("sponge")));
        items.add(GoalListItemV2.createStackedString("obtain an end rod", "", 5, List.of(end, obtain), "obtain_end_rod", List.of("end_rod")));
        items.add(GoalListItemV2.createStackedString("obtain sea lantern", "", 4, List.of(silk_touch, obtain), "obtain_sea_lantern", List.of("sea_lantern")));
        items.add(GoalListItemV2.createStackedString("obtain mossy stone brick wall", "", 3, List.of( obtain), "obtain_mossy_stone_brick_wall", List.of("mossy_stone_brick_wall")));
        items.add(GoalListItemV2.createStackedString("obtain crying obsidian", "", 2, List.of(nether, obtain), "obtain_crying_obsidian", List.of("crying_obsidian")));
        items.add(GoalListItemV2.createStackedString("obtain mushroom stem", "", 3, List.of(silk_touch, obtain), "obtain_mushroom_stem", List.of("mushroom_stem")));
        items.add(GoalListItemV2.createStackedString("obtain a bubble coral block", "", 3, List.of(silk_touch, obtain), "obtain_coral_block", List.of("bubble_coral_block")));
        items.add(GoalListItemV2.createStackedString("obtain a shulker box", "", 5, List.of(end, obtain), "obtain_shulker_box", List.of("shulker_box")));
        items.add(GoalListItemV2.createStackedString("obtain a bookshelf", "", 2, List.of( obtain), "obtain_bookshelf", List.of("bookshelf")));
        items.add(GoalListItemV2.createStackedString("obtain flowering azelia", "", 3, List.of( obtain), "obtain_flowering_azelia", List.of("flowering_azalea")));
        items.add(GoalListItemV2.createStackedString("obtain scaffolding", "", 3, List.of( obtain), "obtain_scaffolding", List.of("scaffolding")));
        items.add(GoalListItemV2.createStackedString("obtain a bucket of powdered snow", "", 3, List.of( obtain), "obtain_snow_bucket", List.of("powder_snow_bucket")));
        items.add(GoalListItemV2.createStackedString("obtain ancient debris", "", 5, List.of(nether, obtain), "obtain_ancient_debris", List.of("ancient_debris")));
        items.add(GoalListItemV2.createStackedString("obtain an ender chest", "", 4, List.of(nether, obtain), "obtain_ender_chest", List.of("ender_chest")));
        items.add(GoalListItemV2.createStackedString("obtain the dragon egg", "", 5, List.of(end, obtain), "obtain_dragon_egg", List.of("dragon_egg")));
        //        items.add(GoalListItemV2.createImaged("obtain dragons breath", "", 1, List.of(), "obtain_dragons_breath", List.of(Identifier.of("minecraft:textures/item/dragons_breath.png"))));
        items.add(GoalListItemV2.createStackedString("obtain TNT", "", 2, List.of( obtain), "obtain_tnt", List.of("tnt")));
        items.add(GoalListItemV2.createStackedString("obtain mud brick wall", "", 3, List.of( obtain), "obtain_mud_brick_wall", List.of("mud_brick_wall")));
        items.add(GoalListItemV2.createStackedString("obtain a calibrated sculk sensor", "", 4, List.of( obtain), "obtain_calibrated_sculk_sensor", List.of("calibrated_sculk_sensor")));
        items.add(GoalListItemV2.createStackedString("obtain blue ice", "", 4, List.of(silk_touch, obtain), "obtain_blue_ice", List.of("blue_ice")));
        items.add(GoalListItemV2.createStackedString("obtain a cobweb", "", 2, List.of( obtain), "obtain_cobweb", List.of("cobweb")));
        items.add(GoalListItemV2.createStackedString("obtain goat horn", "", 3, List.of(obtain), "obtain_goat_horn", List.of("goat_horn")));
        items.add(GoalListItemV2.createStackedString("obtain a nautilus shell", "", 2, List.of(obtain), "obtain_nautilus_shell", List.of("nautilus_shell")));
        items.add(GoalListItemV2.createStackedString("obtain exposed cut copper stairs", "", 4, List.of(obtain), "obtain_exposed_copper_stairs", List.of("exposed_cut_copper_stairs")));
        items.add(GoalListItemV2.createStackedString("obtain a copper bulb", "", 4, List.of(nether, obtain), "obtain_copper_bulb", List.of("copper_bulb")));
        items.add(GoalListItemV2.createStackedString("obtain a daylight detector", "", 2, List.of(nether, obtain), "obtain_daylight_detector", List.of("daylight_detector")));
        items.add(GoalListItemV2.createStackedString("obtain a redstone repeater", "", 2, List.of(redstone, obtain), "obtain_repeater", List.of("repeater")));
        items.add(GoalListItemV2.createStackedString("obtain a piston", "", 2, List.of(redstone, obtain), "obtain_piston", List.of("piston")));
        items.add(GoalListItemV2.createStackedString("obtain a sticky piston", "", 3, List.of(redstone, obtain), "obtain_sticky_piston", List.of("sticky_piston")));
        items.add(GoalListItemV2.createStackedString("obtain a dispenser", "", 2, List.of(redstone, obtain), "obtain_dispenser", List.of("dispenser")));
        items.add(GoalListItemV2.createStackedString("obtain an activator rail", "", 2, List.of(redstone, obtain), "obtain_activator_rail", List.of("activator_rail")));
        items.add(GoalListItemV2.createStackedString("obtain a detector rail", "", 2, List.of(redstone, obtain), "obtain_detector_rail", List.of("detector_rail")));
        items.add(GoalListItemV2.createStackedString("obtain a powered rail", "", 3, List.of(redstone, obtain), "obtain_powered_rail", List.of("powered_rail")));
        items.add(GoalListItemV2.createStackedString("obtain a clock", "", 1, List.of(redstone, obtain), "obtain_clock", List.of("clock")));
        items.add(GoalListItemV2.createStackedString("obtain a comparator", "", 2, List.of(redstone, obtain), "obtain_comparator", List.of("comparator")));
        items.add(GoalListItemV2.createStackedString("obtain an observer", "", 3, List.of(redstone, obtain), "obtain_observer", List.of("observer")));
        items.add(GoalListItemV2.createStackedString("obtain every type of raw ore block", "", 4, List.of( obtain), "obtain_all_raw_ore_blocks", List.of("raw_copper_block", "raw_gold_block", "raw_iron_block")));

        items.add(GoalListItemV2.createStackedString("obtain 15 unique types of stairs", "", 5, List.of(C15, unique, obtain), "stairs_15", List.of("oak_stairs", "smooth_quartz_stairs", "smooth_red_sandstone_stairs", "deepslate_tile_stairs", "cherry_stairs", "stone_brick_stairs", "cut_copper_stairs", "polished_granite_stairs", "bamboo_mosaic_stairs", "end_stone_brick_stairs", "dark_prismarine_stairs", "mossy_cobblestone_stairs", "weathered_cut_copper_stairs")).addBottomRight("15"));
        items.add(GoalListItemV2.createStackedString("obtain 5 unique types of stairs", "", 2, List.of(C5, unique, obtain), "stairs_5", List.of("oak_stairs", "smooth_quartz_stairs", "smooth_red_sandstone_stairs", "deepslate_tile_stairs", "cherry_stairs", "stone_brick_stairs", "cut_copper_stairs", "polished_granite_stairs", "bamboo_mosaic_stairs", "end_stone_brick_stairs", "dark_prismarine_stairs", "mossy_cobblestone_stairs", "weathered_cut_copper_stairs")).addBottomRight("5"));
        items.add(GoalListItemV2.createStackedString("obtain 10 unique types of stairs", "", 3, List.of(C10, unique, obtain), "stairs_10", List.of("oak_stairs", "smooth_quartz_stairs", "smooth_red_sandstone_stairs", "deepslate_tile_stairs", "cherry_stairs", "stone_brick_stairs", "cut_copper_stairs", "polished_granite_stairs", "bamboo_mosaic_stairs", "end_stone_brick_stairs", "dark_prismarine_stairs", "mossy_cobblestone_stairs", "weathered_cut_copper_stairs")).addBottomRight("10"));

        // tool goals
        items.add(GoalListItemV2.createStackedString("obtain a full set of wooden tools", "", 1, List.of(tools, obtain), "obtain_wooden_toolset", List.of("wooden_hoe", "wooden_axe", "wooden_pickaxe", "wooden_shovel", "wooden_sword")));
        items.add(GoalListItemV2.createStackedString("obtain a full set of stone tools", "", 1, List.of(tools, obtain), "obtain_stone_toolset", List.of("stone_hoe", "stone_axe", "stone_pickaxe", "stone_shovel", "stone_sword")));
        items.add(GoalListItemV2.createStackedString("obtain a full set of iron tools", "", 3, List.of(tools, obtain), "obtain_iron_toolset", List.of("iron_hoe", "iron_axe", "iron_pickaxe", "iron_shovel", "iron_sword")));
        items.add(GoalListItemV2.createStackedString("obtain a full set of golden tools", "", 4, List.of(tools, obtain), "obtain_golden_toolset", List.of("golden_hoe", "golden_axe", "golden_pickaxe", "golden_shovel", "golden_sword")));
        items.add(GoalListItemV2.createStackedString("obtain a full set of diamond tools", "", 5, List.of(tools, obtain), "obtain_diamond_toolset", List.of("diamond_hoe", "diamond_axe", "diamond_pickaxe", "diamond_shovel", "diamond_sword")));
        // todo copper tools
        items.add(GoalListItemV2.createStackedString("obtain every type of axe", "", 4, List.of(tools, obtain), "obtain_all_axes", List.of("wooden_axe", "stone_axe", "iron_axe", "golden_axe", "diamond_axe", "netherite_axe"))); // todo copper axe
        items.add(GoalListItemV2.createStackedString("obtain every type of hoe", "", 4, List.of(tools, obtain), "obtain_all_hoes", List.of("wooden_hoe", "stone_hoe", "iron_hoe", "golden_hoe", "diamond_hoe", "netherite_hoe"))); // todo copper hoe
        items.add(GoalListItemV2.createStackedString("obtain every type of pickaxe", "", 4, List.of(tools, obtain), "obtain_all_pickaxes", List.of("wooden_pickaxe", "stone_pickaxe", "iron_pickaxe", "golden_pickaxe", "diamond_pickaxe", "netherite_pickaxe"))); // todo copper pickaxe
        items.add(GoalListItemV2.createStackedString("obtain every type of shovel", "", 4, List.of(tools, obtain), "obtain_all_shovels", List.of("wooden_shovel", "stone_shovel", "iron_shovel", "golden_shovel", "diamond_shovel", "netherite_shovel"))); // todo copper shovel
        items.add(GoalListItemV2.createStackedString("obtain every type of sword", "", 4, List.of(tools, obtain), "obtain_all_swords", List.of("wooden_sword", "stone_sword", "iron_sword", "golden_sword", "diamond_sword", "netherite_sword"))); // todo copper sword
        //        items.add(GoalListItemV2.createImaged("obtain a full set of netherite tools", "", 1, List.of(tools), "obtain_netherite_toolset", List.of(Identifier.of("minecraft:textures/item/netherite_hoe.png"), Identifier.of("minecraft:textures/item/netherite_hoe.png"), Identifier.of("minecraft:textures/item/netherite_pickaxe.png"), Identifier.of("minecraft:textures/item/netherite_shovel.png"), Identifier.of("minecraft:textures/item/netherite_sword.png"))));

        // armor goals
        items.add(GoalListItemV2.createStackedString("wear a piece of chainmail armor", "", 2, List.of(armor), "wear_chainmail", List.of("chainmail_helmet", "chainmail_chestplate", "chainmail_leggings", "chainmail_boots")));
        items.add(GoalListItemV2.createStackedString("wear a full set of leather armor", "", 1, List.of(armor), "wear_full_leather",List.of("leather_helmet", "leather_chestplate", "leather_leggings", "leather_boots")));
        items.add(GoalListItemV2.createStackedString("wear a full set of iron armor", "", 3, List.of(armor), "wear_full_iron", List.of("iron_helmet", "iron_chestplate", "iron_leggings", "iron_boots")));
        items.add(GoalListItemV2.createStackedString("wear a full set of golden armor", "", 4, List.of(armor), "wear_full_golden", List.of("golden_helmet", "golden_chestplate", "golden_leggings", "golden_boots")));
        items.add(GoalListItemV2.createStackedString("wear a full set of diamond armor", "", 5, List.of(armor), "wear_full_diamond", List.of("diamond_helmet", "diamond_chestplate", "diamond_leggings", "diamond_boots")));
        items.add(GoalListItemV2.createStackedString("wear a piece of netherite armor", "", 6, List.of(armor), "wear_netherite", List.of("netherite_helmet", "netherite_chestplate", "netherite_leggings", "netherite_boots")));
        items.add(GoalListItemV2.createStackedString("wear an elytra", "", 5, List.of(elytra, end), "wear_elytra", List.of("elytra")));

        items.add(GoalListItemV2.createStackedString("wear a piece of colored armor", "", 2, List.of(armor), "wear_colored", List.of())); //todo colors
        items.add(GoalListItemV2.createImaged("wear a trimmed armor piece", "", 3, List.of(armor), "wear_trimmed", List.of(Identifier.of("lockout-bingo:goalicon/item/armor/emerald_trimmed_iron_helmet.png"), Identifier.of("lockout-bingo:goalicon/item/armor/copper_trimmed_diamond_leggings.png"),Identifier.of("lockout-bingo:goalicon/item/armor/redstone_trimmed_gold_chestplate.png"), Identifier.of("lockout-bingo:goalicon/item/armor/quartz_trimmed_netherite_boots.png")) ));
        items.add(GoalListItemV2.createImaged("wear a fully trimmed armor set", "", 3, List.of(armor, C4), "wear_trimmed_set", List.of(Identifier.of("lockout-bingo:goalicon/item/armor/emerald_trimmed_iron_helmet.png"), Identifier.of("lockout-bingo:goalicon/item/armor/copper_trimmed_diamond_leggings.png"),Identifier.of("lockout-bingo:goalicon/item/armor/redstone_trimmed_gold_chestplate.png"), Identifier.of("lockout-bingo:goalicon/item/armor/quartz_trimmed_netherite_boots.png")) ));
        items.add(GoalListItemV2.createStackedString("wear a carved pumpkin for 1 minute", "", 3, List.of(armor), "wear_pumpkin", List.of("carved_pumpkin"))); // todo change time

        items.add(GoalListItemV2.createStackedString("obtain 4 types of seeds", "", 3, List.of(C4, unique, obtain), "obtain_all_seeds", List.of("wheat_seeds", "melon_seeds", "pumpkin_seeds", "beetroot_seeds", "torchflower_seeds")).addBottomRight("4"));
        items.add(GoalListItemV2.createStackedString("obtain 6 types of flowers", "", 3, List.of(C6, unique, obtain), "obtain_6_flowers", List.of("poppy", "dandelion", "blue_orchid", "allium", "azure_bluet", "red_tulip", "orange_tulip", "white_tulip",  "oxeye_daisy", "cornflower", "lily_of_the_valley", "wither_rose")).addBottomRight("6"));
        items.add(GoalListItemV2.createStackedString("obtain black glazed terracotta", "", 2, List.of( obtain), "obtain_black_glazed_terracotta", List.of("black_glazed_terracotta")));
        items.add(GoalListItemV2.createStackedString("obtain all saplings", "", 4, List.of(obtain), "obtain_all_saplings", List.of("oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling", "mangrove_propagule", "cherry_sapling")));
        items.add(GoalListItemV2.createStackedString("obtain 2 music disks", "", 3, List.of(C2, obtain), "obtain_2_music_disks", List.of("music_disc_13", "music_disc_cat", "music_disc_blocks", "music_disc_chirp", "music_disc_far", "music_disc_mall", "music_disc_mellohi", "music_disc_stal", "music_disc_strad", "music_disc_ward", "music_disc_11", "music_disc_wait")).addBottomRight("2"));
        items.add(GoalListItemV2.createStackedString("obtain all types of horse armor", "", 4, List.of(nether, unique, obtain), "obtain_all_harmor", List.of("iron_horse_armor", "golden_horse_armor", "diamond_horse_armor", "leather_horse_armor")));

        items.add(GoalListItemV2.createStackedString("eat 5 unique foods", "", 1, List.of(C5, eat, unique), "eat_5", List.of("apple")).addBottomRight("5"));
        items.add(GoalListItemV2.createStackedString("eat 10 unique foods", "", 2, List.of(C10, eat, unique), "eat_10", List.of("apple")).addBottomRight("10"));
        items.add(GoalListItemV2.createStackedString("eat 15 unique foods", "", 3, List.of(C15, eat, unique), "eat_15", List.of("apple")).addBottomRight("15"));
        items.add(GoalListItemV2.createStackedString("eat 20 unique foods", "", 4, List.of(C20, eat, unique), "eat_20", List.of("apple")).addBottomRight("20"));
        items.add(GoalListItemV2.createStackedString("eat suspicious stew", "", 1, List.of(eat), "eat_suspicious_stew", List.of("suspicious_stew")));
        items.add(GoalListItemV2.createStackedString("eat glow berries", "", 3, List.of(eat), "eat_glow_berries", List.of("glow_berries")));
        items.add(GoalListItemV2.createStackedString("eat rabbit stew", "", 2, List.of(eat), "eat_rabbit_stew", List.of("rabbit_stew")));
        items.add(GoalListItemV2.createStackedString("eat beetroot soup", "", 4, List.of(eat), "eat_beetroot_soup", List.of("beetroot_soup")));
        items.add(GoalListItemV2.createStackedString("eat honey bottle", "", 4, List.of(eat), "eat_honey_bottle", List.of("honey_bottle")));
//        items.add(GoalListItemV2.createImaged("eat honeycomb", "", 1, List.of(), "eat_honeycomb", List.of(Identifier.of("minecraft:textures/item/honeycomb.png"))));
        items.add(GoalListItemV2.createStackedString("eat golden apple", "", 3, List.of(eat), "eat_golden_apple", List.of("golden_apple")));
        items.add(GoalListItemV2.createStackedString("eat a cookie", "", 2, List.of(eat), "eat_cookie", List.of("cookie")));
        items.add(GoalListItemV2.createStackedString("eat pumpkin pie", "", 2, List.of(eat), "eat_pumpkin_pie", List.of("pumpkin_pie")));
        items.add(GoalListItemV2.createStackedString("eat poisonous potato", "", 1, List.of(eat), "eat_poisonous_potato", List.of("poisonous_potato")));
        items.add(GoalListItemV2.createStackedString("eat dried kelp", "", 1, List.of(eat), "eat_dried_kelp", List.of("dried_kelp")));
        items.add(GoalListItemV2.createStackedString("eat chorus fruit", "", 4, List.of(eat, end), "eat_chorus_fruit", List.of("chorus_fruit")));

        items.add(GoalListItemV2.createImaged("breed hoglins", "", 3, List.of(breed, nether), "breed_hoglins", List.of(Identifier.of("lockout-bingo:goalicon/entity/hoglin.png"))));
        items.add(GoalListItemV2.createImaged("breed striders", "", 3, List.of(breed, nether), "breed_striders", List.of(Identifier.of("lockout-bingo:goalicon/entity/strider.png"))));
        items.add(GoalListItemV2.createImaged("breed axolotls", "", 4, List.of(breed), "breed_axolotls", List.of(Identifier.of("lockout-bingo:goalicon/entity/axolotl.png"))));
        items.add(GoalListItemV2.createImaged("breed bees", "", 1, List.of(breed), "breed_bees", List.of(Identifier.of("lockout-bingo:goalicon/entity/bee.png"))));
        items.add(GoalListItemV2.createImaged("breed goats", "", 3, List.of(breed), "breed_goats", List.of(Identifier.of("lockout-bingo:goalicon/entity/goat.png"))));
        items.add(GoalListItemV2.createImaged("breed foxes", "", 3, List.of(breed), "breed_foxes", List.of(Identifier.of("lockout-bingo:goalicon/entity/fox.png"))));
        items.add(GoalListItemV2.createImaged("breed cats", "", 2, List.of(breed), "breed_cats", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(GoalListItemV2.createImaged("breed wolves", "", 2, List.of(breed), "breed_wolves", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));
//        items.add(GoalListItemV2.createImaged("breed parrots", "", 2, List.of(breed), "breed_parrots", List.of(Identifier.of("lockout-bingo:goalicon/entity/parrot.png"))));
        items.add(GoalListItemV2.createImaged("breed rabbits", "", 3, List.of(breed), "breed_rabbits", List.of(Identifier.of("lockout-bingo:goalicon/entity/rabbit.png"))));
        items.add(GoalListItemV2.createImaged("breed llamas", "", 3, List.of(breed), "breed_llamas", List.of(Identifier.of("lockout-bingo:goalicon/entity/lama.png"))));
        items.add(GoalListItemV2.createImaged("breed horses", "", 3, List.of(breed), "breed_horses", List.of(Identifier.of("lockout-bingo:goalicon/entity/horse.png"))));
        items.add(GoalListItemV2.createImaged("breed pigs", "", 1, List.of(breed), "breed_pigs", List.of(Identifier.of("lockout-bingo:goalicon/entity/pig.png"))));
        items.add(GoalListItemV2.createImaged("breed turtles", "", 2, List.of(breed), "breed_turtles", List.of(Identifier.of("lockout-bingo:goalicon/entity/turtle.png"))));
        items.add(GoalListItemV2.createImaged("breed a mule", "", 4, List.of(breed), "breed_mule", List.of(Identifier.of("lockout-bingo:goalicon/entity/mule.png"))));
        items.add(GoalListItemV2.createImaged("breed chickens", "", 1, List.of(breed), "breed_chickens", List.of(Identifier.of("lockout-bingo:goalicon/entity/chicken.png"))));
        items.add(GoalListItemV2.createImaged("breed cows", "", 1, List.of(breed), "breed_cows", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))));



        items.add(GoalListItemV2.createImaged("breed 5 unique animals", "", 1, List.of(breed, C5, unique), "breed_5", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))).addBottomRight("5"));
        items.add(GoalListItemV2.createImaged("breed 10 unique animals", "", 3, List.of(breed, C10, unique), "breed_10", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))).addBottomRight("10"));
        items.add(GoalListItemV2.createImaged("breed 15 unique animals", "", 4, List.of(breed, C15, unique), "breed_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))).addBottomRight("15"));

        items.add(GoalListItemV2.createImaged("kill a piglin brute", "", 4, List.of(kill, nether), "kill_piglin_brute", List.of(Identifier.of("lockout-bingo:goalicon/entity/piglin_brute.png"))));
        items.add(GoalListItemV2.createImaged("kill zoglin", "", 3, List.of(kill, nether), "kill_zoglin", List.of(Identifier.of("lockout-bingo:goalicon/entity/zoglin.png"))));
        items.add(GoalListItemV2.createImaged("kill an endermite", "", 4, List.of(kill), "kill_endermite", List.of(Identifier.of("lockout-bingo:goalicon/entity/endermite.png"))));
        items.add(GoalListItemV2.createImaged("kill a silverfish", "", 3, List.of(kill), "kill_silverfish", List.of(Identifier.of("lockout-bingo:goalicon/entity/silverfish.png"))));
        items.add(GoalListItemV2.createImaged("kill a breeze", "", 4, List.of(kill), "kill_breeze", List.of(Identifier.of("lockout-bingo:goalicon/entity/breeze.png")))); // Assuming 'breeze' is a fictional entity for the example
        items.add(GoalListItemV2.createImaged("kill an elder guardian", "", 3, List.of(kill), "kill_elder_guardian", List.of(Identifier.of("lockout-bingo:goalicon/entity/elder_guardian.png"))));
        items.add(GoalListItemV2.createImaged("kill a ghast", "", 2, List.of(kill, nether), "kill_ghast", List.of(Identifier.of("lockout-bingo:goalicon/entity/ghast.png"))));
        items.add(GoalListItemV2.createImaged("kill a zombie villager", "", 2, List.of(kill), "kill_zillager", List.of(Identifier.of("lockout-bingo:goalicon/entity/zillager.png"))));
        items.add(GoalListItemV2.createImaged("kill a witch", "", 3, List.of(kill), "kill_witch", List.of(Identifier.of("lockout-bingo:goalicon/entity/witch.png"))));
        items.add(GoalListItemV2.createImaged("kill a stray", "", 3, List.of(kill), "kill_stray", List.of(Identifier.of("lockout-bingo:goalicon/entity/stray.png"))));
        items.add(GoalListItemV2.createImaged("kill a snow golem", "", 3, List.of(kill), "kill_snow_golem", List.of(Identifier.of("lockout-bingo:goalicon/entity/snow_golem.png"))));
        items.add(GoalListItemV2.createImaged("kill a blaze with a snowball", "", 4, List.of(kill, nether), "kill_blaze_snowball", List.of(Identifier.of("lockout-bingo:goalicon/entity/blaze.png"))));
        items.add(GoalListItemV2.createImaged("kill 7 unique hostile mobs", "", 1, List.of(kill, C7, unique), "kill_7", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"))).addBottomRight("7"));
        items.add(GoalListItemV2.createImaged("kill 10 unique hostile mobs", "", 3, List.of(kill, C10, unique), "kill_10", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"))).addBottomRight("10"));
        items.add(GoalListItemV2.createImaged("kill 15 unique hostile mobs", "", 4, List.of(kill, C15, unique), "kill_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"))).addBottomRight("15"));
        items.add(GoalListItemV2.createImaged("kill jeb_", "", 4, List.of(kill), "kill_jeb_", List.of(Identifier.of("lockout-bingo:goalicon/entity/jeb.png"))));

        items.add(GoalListItemV2.createImaged("kill 100 hostile mobs", "", 4, List.of(kill, C100), "kill_100", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"), Identifier.of("lockout-bingo:goalicon/entity/spider.png"), Identifier.of("lockout-bingo:goalicon/entity/bee.png"), Identifier.of("lockout-bingo:goalicon/entity/sheep.png"), Identifier.of("lockout-bingo:goalicon/entity/drowned.png"), Identifier.of("lockout-bingo:goalicon/entity/enderman.png"))).addBottomRight("100"));
        items.add(GoalListItemV2.createImaged("kill 30 undead mobs", "", 3, List.of(kill, C30), "undead_30", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"), Identifier.of("lockout-bingo:goalicon/entity/zoglin.png"), Identifier.of("lockout-bingo:goalicon/entity/skeleton.png"), Identifier.of("lockout-bingo:goalicon/entity/zombified_piglin.png"), Identifier.of("lockout-bingo:goalicon/entity/phantom.png"), Identifier.of("lockout-bingo:goalicon/entity/bogged.png"), Identifier.of("lockout-bingo:goalicon/entity/zillager.png"))).addBottomRight("30"));
        items.add(GoalListItemV2.createImaged("kill 20 arthropods", "", 3, List.of(kill, C20), "arthropod_20", List.of(Identifier.of("lockout-bingo:goalicon/entity/spider.png"), Identifier.of("lockout-bingo:goalicon/entity/bee.png"), Identifier.of("lockout-bingo:goalicon/entity/endermite.png"), Identifier.of("lockout-bingo:goalicon/entity/cave_spider.png"), Identifier.of("lockout-bingo:goalicon/entity/silverfish.png"))).addBottomRight("20"));

        //use entity goals
        items.add(GoalListItemV2.createImaged("shear a bogged", "", 2, List.of(), "shear_bogged", List.of(Identifier.of("lockout-bingo:goalicon/entity/bogged.png"))));


        items.add(GoalListItemV2.createImaged("enter the nether", "", 2, List.of(advancement), "nether_adv", List.of(Identifier.of("lockout-bingo:goalicon/structures/nether_portal.png"))));
        items.add(GoalListItemV2.createStackedString("enter the end", "", 3, List.of(advancement, nether), "end_adv", List.of("end_portal_frame")));
        items.add(GoalListItemV2.createStackedString("use an enchanting table", "", 3, List.of(advancement), "enchanter_adv", List.of("enchanting_table")));
        items.add(GoalListItemV2.createStackedString("zombie doctor", "", 4, List.of(advancement), "zombie_doc_adv", List.of("golden_apple")));
        items.add(GoalListItemV2.createStackedString("enter the stronghold", "", 3, List.of(advancement, nether), "eye_spy_adv", List.of("ender_eye")));
        items.add(GoalListItemV2.createStackedString("kill a ghast with its own fireball", "", 3, List.of(advancement, nether), "return_to_sender_adv", List.of("fire_charge")));
        items.add(GoalListItemV2.createStackedString("find a bastion remnant", "", 3, List.of(advancement, nether), "those_were_the_days_adv", List.of("polished_blackstone_bricks"))); // This is a fictional texture path for a structure
        items.add(GoalListItemV2.createStackedString("find a nether fortress", "", 3, List.of(advancement, nether), "terrible_fortress_adv", List.of("nether_brick")));
        items.add(GoalListItemV2.createStackedString("use the nether to travel 7km in the overworld", "", 4, List.of(advancement, nether), "subspace_bubble_adv", List.of("filled_map")));
        items.add(GoalListItemV2.createStackedString("distract a piglin with gold", "", 2, List.of(advancement, nether), "oh_shiny_adv", List.of("gold_ingot")));
        items.add(GoalListItemV2.createImaged("ride a strider with a warped fungus on a stick", "", 4, List.of(ride, advancement, nether), "this_boat_has_legs_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/strider.png"))));
        items.add(GoalListItemV2.createStackedString("use a lodestone", "", 4, List.of(advancement, nether), "country_lode_adv", List.of("lodestone")));
        items.add(GoalListItemV2.createStackedString("use a respawn anchor", "", 3, List.of(advancement, nether), "use_respawn_anchor_adv", List.of("respawn_anchor")));
        items.add(GoalListItemV2.createStackedString("get a wither skeleton skull", "", 5, List.of(advancement, nether, obtain), "spooky_scary_skeletons_adv", List.of("wither_skeleton_skull")));
        items.add(GoalListItemV2.createStackedString("travel to every nether biome", "", 3, List.of(advancement, nether), "hot_tourist_adv", List.of("netherite_boots")));
        items.add(GoalListItemV2.createStackedString("great view from above", "", 5, List.of(advancement, end), "great_view_adv", List.of("ender_pearl")));
        items.add(GoalListItemV2.createStackedString("what a deal", "", 1, List.of(advancement, village), "trade_adv", List.of("emerald")));
        items.add(GoalListItemV2.createStackedString("slide in a honey block to break your fall", "", 5, List.of(advancement), "sticky_adv", List.of("honey_block")));
        items.add(GoalListItemV2.createStackedString("kill a mob near a sculk catalyst", "", 3, List.of(advancement), "spreads_adv", List.of("sculk_catalyst")));
        items.add(GoalListItemV2.createImaged("make a golem", "", 2, List.of(advancement), "hired_help_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/iron_golem.png"))));
        items.add(GoalListItemV2.createStackedString("arbalistic", "", 4, List.of(advancement), "arbalistic_adv", List.of("crossbow")));
        items.add(GoalListItemV2.createStackedString("walk on powdered snow with leather boots", "", 3, List.of(advancement), "walk_snow_adv", List.of("leather_boots")));
        items.add(GoalListItemV2.createStackedString("shoot a bullseye with a bow and arrow", "", 2, List.of(advancement, redstone), "bullseye_adv", List.of("target")));
        items.add(GoalListItemV2.createStackedString("total beelocation", "", 3, List.of(advancement, silk_touch), "beelocation_adv", List.of("bee_nest")));
        items.add(GoalListItemV2.createStackedString("get a tadpole in a bucket", "", 4, List.of(breed, advancement), "tadpole_bucket_adv", List.of("tadpole_bucket")));
        items.add(GoalListItemV2.createStackedString("obtain a sniffer egg", "", 3, List.of(advancement, obtain), "sniffer_egg_adv", List.of("sniffer_egg")));
        items.add(GoalListItemV2.createImaged("tame 4 unique types of cats", "", 3, List.of(C4, tame, unique), "catalogue_4_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(GoalListItemV2.createImaged("tame 3 unique types of wolves", "", 4, List.of(C3, tame, unique), "whole_pack_3_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));
        items.add(GoalListItemV2.createStackedString("tactical fishing", "", 1, List.of(advancement), "tactical_fishing_adv", List.of("tropical_fish_bucket")));
        items.add(GoalListItemV2.createStackedString("scrape the wax off a copper block", "", 3, List.of(advancement), "wax_off_adv", List.of("stone_axe")));
        items.add(GoalListItemV2.createStackedString("axolotl in a bucket", "", 3, List.of(advancement), "axolotl_bucket_adv", List.of("axolotl_bucket")));
        items.add(GoalListItemV2.createStackedString("Crafters crafting crafters", "", 3, List.of(advancement, redstone), "crafters_adv", List.of("crafter")));
        items.add(GoalListItemV2.createStackedString("use a trial key on a vault", "", 4, List.of(advancement), "trial_key_adv", List.of("trial_key")));
        items.add(GoalListItemV2.createImaged("use wolf armor on a wolf", "", 3, List.of(advancement), "wolf_armor_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/wolf_armor.png"))));
        items.add(GoalListItemV2.createStackedString("Sound of music: play a music disk in a meadow", "", 3, List.of(advancement), "sound_of_music_adv", List.of("jukebox")));
        items.add(GoalListItemV2.createStackedString("catch a fish", "", 1, List.of(advancement), "fishy_business_adv", List.of("fishing_rod")));
        items.add(GoalListItemV2.createImaged("kill the dragon", "", 5, List.of(advancement, kill), "dragon_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/ender_dragon.png"))));
        items.add(GoalListItemV2.createImaged("get the sniper duel advancement", "", 4, List.of(advancement), "sniper_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/skeleton.png"))));
        items.add(GoalListItemV2.createStackedString("get any spyglass advancement", "", 3, List.of(advancement), "spyglass_adv", List.of("spyglass")));
        items.add(GoalListItemV2.createStackedString("very very frightening", "", 5, List.of(advancement), "frightening_adv", List.of("trident")));

        items.add(GoalListItemV2.createImaged("complete 15 advancements", "", 3, List.of(C15, advancement), "adv_15", List.of(Identifier.of("lockout-bingo:goalicon/advancement/challenge_done.png"))).addBottomRight("15"));
        items.add(GoalListItemV2.createImaged("complete 25 advancements", "", 3, List.of(C25, advancement), "adv_25", List.of(Identifier.of("lockout-bingo:goalicon/advancement/challenge_done.png"))).addBottomRight("25"));
        items.add(GoalListItemV2.createImaged("complete 35 advancements", "", 3, List.of(C35, advancement), "adv_35", List.of(Identifier.of("lockout-bingo:goalicon/advancement/challenge_done.png"))).addBottomRight("35"));
        // biome goals
        items.add(GoalListItemV2.createImaged("find an ice spikes biome", "", 3, List.of(biomes), "biome_ice_spikes", List.of(Identifier.of("lockout-bingo:goalicon/biome/ice_spikes.png"))));
        items.add(GoalListItemV2.createImaged("find a badlands biome", "", 3, List.of(biomes), "biome_badlands", List.of(Identifier.of("lockout-bingo:goalicon/biome/badlands.png"))));
        items.add(GoalListItemV2.createStackedString("find 15 unique biomes", "", 2, List.of(C15, biomes, unique), "biomes_15", List.of("diamond_boots")).addBottomRight("15"));
        items.add(GoalListItemV2.createStackedString("find 20 unique biomes", "", 3, List.of(C20, biomes, unique), "biomes_20", List.of("diamond_boots")).addBottomRight("20"));
        items.add(GoalListItemV2.createStackedString("find 25 unique biomes", "", 4, List.of(C25, biomes, unique), "biomes_25", List.of("diamond_boots")).addBottomRight("25"));
        items.add(GoalListItemV2.createStackedString("find 30 unique biomes", "", 5, List.of(C30, biomes, unique), "biomes_30", List.of("diamond_boots")).addBottomRight("30"));

        items.add(GoalListItemV2.createImaged("die by golem", "", 1, List.of(die), "die_golem", List.of(Identifier.of("lockout-bingo:goalicon/entity/iron_golem.png"))));
        items.add(GoalListItemV2.createImaged("die by llama", "", 2, List.of(die), "die_llama", List.of(Identifier.of("lockout-bingo:goalicon/entity/lama.png"))));
        items.add(GoalListItemV2.createImaged("die by bee sting", "", 1, List.of(die), "die_bee", List.of(Identifier.of("lockout-bingo:goalicon/entity/bee.png"))));

        items.add(GoalListItemV2.createStackedString("die by trident", "", 2, List.of(die), "die_trident", List.of("trident")));
        items.add(GoalListItemV2.createStackedString("die by sweet berry bush", "", 2, List.of(die), "die_sweet_berry_bush", List.of("sweet_berries")));
        items.add(GoalListItemV2.createStackedString("die by cactus", "", 3, List.of(die), "die_cactus", List.of("cactus")));
        items.add(GoalListItemV2.createStackedString("die by anvil", "", 3, List.of(die), "die_anvil", List.of("anvil")));
        items.add(GoalListItemV2.createImaged("die by falling stalactite", "", 2, List.of(die), "die_stalactite", List.of(Identifier.of("lockout-bingo:goalicon/block/stalactite.png"))));
        items.add(GoalListItemV2.createImaged("die by falling on a stalagmite", "", 2, List.of(die), "die_stalagmite", List.of(Identifier.of("lockout-bingo:goalicon/block/stalagmite.png"))));
        items.add(GoalListItemV2.createStackedString("die by fireworks", "", 3, List.of(die), "die_fireworks", List.of("firework_rocket")));
        items.add(GoalListItemV2.createStackedString("freeze to death", "", 2, List.of(die), "die_freeze", List.of("powder_snow_bucket")));
        items.add(GoalListItemV2.createStackedString("die by intentional game design", "", 2, List.of(die), "die_bed", List.of("red_bed", "respawn_anchor")));
        items.add(GoalListItemV2.createStackedString("die by tnt minecart", "", 3, List.of(die), "die_tnt_minecart", List.of("tnt_minecart")));
        items.add(GoalListItemV2.createStackedString("die by ender pearl", "", 3, List.of(die), "die_ender_pearl", List.of("ender_pearl")));
        items.add(GoalListItemV2.createStackedString("die by explosion", "", 1, List.of(die), "die_explosion", List.of("tnt", "tnt_minecart", "firework_rocket", "respawn_anchor", "red_bed", "end_crystal"))); // todo entities like creeper do too
        items.add(GoalListItemV2.createStackedString("die by projectile", "", 1, List.of(die), "die_projectile", List.of("arrow", "trident", "firework_rocket"))); // todo llama spit

        // ride goals
        items.add(GoalListItemV2.createImaged("ride a pig", "", 2, List.of(ride), "ride_pig", List.of(Identifier.of("lockout-bingo:goalicon/entity/pig.png"))));
        items.add(GoalListItemV2.createImaged("ride a horse", "", 1, List.of(ride), "ride_horse", List.of(Identifier.of("lockout-bingo:goalicon/entity/horse.png"))));
        items.add(GoalListItemV2.createStackedString("ride a minecart", "", 3, List.of(), "ride_minecart", List.of("minecart")));

        // damage goals
        items.add(GoalListItemV2.createStackedString("deal 200 damage", "", 2, List.of(C200), "damage_200", List.of("diamond_sword")).addBottomRight("200"));
        items.add(GoalListItemV2.createStackedString("deal 500 damage", "", 3, List.of(C500), "damage_500", List.of("diamond_sword")).addBottomRight("500"));

        items.add(GoalListItemV2.createImaged("take 200 damage", "", 2, List.of(C200), "damage_take_200", List.of(Identifier.of("lockout-bingo:goalicon/other/damage.png"))));

        // use goals
        items.add(GoalListItemV2.createStackedString("use a smithing table", "", 3, List.of(use), "use_smithing_table", List.of("smithing_table")));
        items.add(GoalListItemV2.createStackedString("use a grindstone", "", 2, List.of(use), "use_grindstone", List.of("grindstone")));
        items.add(GoalListItemV2.createStackedString("use a blast furnace", "", 2, List.of(use), "use_blast_furnace", List.of("blast_furnace")));
        items.add(GoalListItemV2.createStackedString("use a cartography table", "", 2, List.of(use), "use_cartography_table", List.of("cartography_table")));
        items.add(GoalListItemV2.createStackedString("use a loom", "", 3, List.of(use), "use_loom", List.of("loom")));
        items.add(GoalListItemV2.createStackedString("use a stonecutter", "", 1, List.of(use), "use_stonecutter", List.of("stonecutter")));
        items.add(GoalListItemV2.createStackedString("use an anvil", "", 3, List.of(use), "use_anvil", List.of("anvil")));
        items.add(GoalListItemV2.createStackedString("use a composter", "", 1, List.of(use), "use_composter", List.of("composter")));
        items.add(GoalListItemV2.createStackedString("pot a plant", "", 1, List.of(use), "pot_plant", List.of("flower_pot")));


        // mine goals
        items.add(GoalListItemV2.createStackedString("mine emerald ore", "", 5, List.of(), "mine_emerald", List.of("emerald_ore")));
        items.add(GoalListItemV2.createStackedString("mine diamond ore", "", 3, List.of(), "mine_diamond", List.of("diamond_ore")));
        items.add(GoalListItemV2.createStackedString("mine mob spawner", "", 3, List.of(), "mine_spawner", List.of("spawner")));
        items.add(GoalListItemV2.createStackedString("mine a turtle egg", "", 4, List.of(), "mine_turtle_egg", List.of("turtle_egg")));

        // tame goals
        items.add(GoalListItemV2.createImaged("tame a cat", "", 2, List.of(tame), "tame_cat", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(GoalListItemV2.createImaged("tame a wolf", "", 3, List.of(tame), "tame_wolf", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));
        items.add(GoalListItemV2.createImaged("tame a horse", "", 1, List.of(tame), "tame_horse", List.of(Identifier.of("lockout-bingo:goalicon/entity/horse.png"))));
        items.add(GoalListItemV2.createImaged("tame a llama", "", 3, List.of(tame), "tame_llama", List.of(Identifier.of("lockout-bingo:goalicon/entity/lama.png"))));
        items.add(GoalListItemV2.createImaged("tame a parrot", "", 4, List.of(tame), "tame_parrot", List.of(Identifier.of("lockout-bingo:goalicon/entity/parrot.png"))));

        // effect goals
        items.add(GoalListItemV2.createImaged("get poisoned", "", 2, List.of(effect), "effect_poison", List.of(Identifier.of("lockout-bingo:goalicon/effect/poison.png"))));
        items.add(GoalListItemV2.createImaged("get weakness", "", 3, List.of(effect), "effect_weakness", List.of(Identifier.of("lockout-bingo:goalicon/effect/weakness.png"))));
        items.add(GoalListItemV2.createImaged("get jump boost", "", 3, List.of(effect), "effect_jump_boost", List.of(Identifier.of("lockout-bingo:goalicon/effect/jump_boost.png"))));
        items.add(GoalListItemV2.createImaged("get fire resistance", "", 4, List.of(effect, nether), "effect_fire_resistance", List.of(Identifier.of("lockout-bingo:goalicon/effect/fire_resistance.png"))));
        items.add(GoalListItemV2.createImaged("get infested", "", 3, List.of(effect, nether), "effect_infested", List.of(Identifier.of("lockout-bingo:goalicon/effect/infested.png"))));
        items.add(GoalListItemV2.createImaged("get glowing", "", 3, List.of(effect, nether), "effect_glowing", List.of(Identifier.of("lockout-bingo:goalicon/effect/glowing.png"))));
        items.add(GoalListItemV2.createImaged("get nausea", "", 3, List.of(effect), "effect_nausea", List.of(Identifier.of("lockout-bingo:goalicon/effect/nausea.png")))); // todo iconm
        items.add(GoalListItemV2.createImaged("get absorption", "", 3, List.of(effect), "effect_absorption", List.of(Identifier.of("lockout-bingo:goalicon/effect/absorption.png")))); // todo icon
        items.add(GoalListItemV2.createImaged("get mining fatigue", "", 3, List.of(effect), "effect_mining_fatigue", List.of(Identifier.of("lockout-bingo:goalicon/effect/mining_fatigue.png")))); // todo icon
        items.add(GoalListItemV2.createImaged("get bad omen", "", 3, List.of(effect), "effect_bad_omen", List.of(Identifier.of("lockout-bingo:goalicon/effect/bad_omen.png")))); // todo icon
        items.add(GoalListItemV2.createStackedString("have 3 active effects at once", "", 3, List.of(effect, C3), "effect_3", List.of("milk_bucket")).addBottomRight("3"));
        items.add(GoalListItemV2.createStackedString("have 6 active effects at once", "", 4, List.of(effect, C6), "effect_6", List.of("milk_bucket")).addBottomRight("6"));
        items.add(GoalListItemV2.createStackedString("dont get any effects", "", 2, List.of(effect, dont), "dont_effect", List.of("milk_bucket")));
        items.add(GoalListItemV2.createImaged("dont get glowing", "", 2, List.of(effect, dont), "dont_glowing", List.of(Identifier.of("lockout-bingo:goalicon/effect/glowing.png"))));
        items.add(GoalListItemV2.createStackedString("remove an effect using milk", "", 2, List.of(effect), "milk_effect", List.of("milk_bucket")));

        items.add(GoalListItemV2.createStackedString("break 5 pickaxes", "", 2, List.of(C5, broken), "break_5_pickaxes", List.of("wooden_pickaxe", "stone_pickaxe", "iron_pickaxe", "golden_pickaxe", "diamond_pickaxe", "netherite_pickaxe")).addBottomRight("5"));
        items.add(GoalListItemV2.createStackedString("break a diamond pickaxe", "", 4, List.of(broken), "break_diamond", List.of("diamond_pickaxe")));


        items.add(GoalListItemV2.createStackedString("get them in a bucket !!", "", 5, List.of(), "get_them_in_a_bucket", List.of("pufferfish_bucket")));

        // level goals
        items.add(GoalListItemV2.createImaged("reach level 15", "", 2, List.of(lvl, C15), "lvl_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))).addBottomRight("15"));
        items.add(GoalListItemV2.createImaged("reach level 30", "", 4, List.of(lvl, C30), "lvl_30", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))).addBottomRight("30"));


        // position goals
        items.add(GoalListItemV2.createStackedString("reach bedrock", "", 1, List.of(), "reach_bedrock", List.of("bedrock")));
        items.add(GoalListItemV2.createImaged("reach the sky limit", "", 1, List.of(), "reach_sky_limit", List.of(Identifier.of("lockout-bingo:goalicon/item/sun.png"))));
        items.add(GoalListItemV2.createImaged("fall for 300 blocks", "", 2, List.of(C300), "fall_300", List.of(Identifier.of("lockout-bingo:goalicon/other/fall.png"))).addBottomRight("300"));

        //wool goals
        items.add(GoalListItemV2.createStackedString("obtain a stack of lime wool", "", 3, List.of(wool, C64, obtain), "64_lime_wool", List.of("lime_wool")).addBottomRight("64"));
        items.add(GoalListItemV2.createStackedString("obtain a stack of gray wool", "", 3, List.of(wool, C64, obtain), "64_gray_wool", List.of("gray_wool")).addBottomRight("64"));
        items.add(GoalListItemV2.createStackedString("obtain a stack of cyan wool", "", 3, List.of(wool, C64, obtain), "64_cyan_wool", List.of("cyan_wool")).addBottomRight("64"));
        items.add(GoalListItemV2.createStackedString("obtain a stack of brown wool", "", 3, List.of(wool, C64, obtain), "64_brown_wool", List.of("brown_wool")).addBottomRight("64"));

        items.add(GoalListItemV2.createStackedString("duplicate a smithing template", "", 3, List.of(C2), "duplicate_smithing_template", List.of("dune_armor_trim_smithing_template", "coast_armor_trim_smithing_template", "wild_armor_trim_smithing_template", "ward_armor_trim_smithing_template", "eye_armor_trim_smithing_template", "vex_armor_trim_smithing_template", "tide_armor_trim_smithing_template", "snout_armor_trim_smithing_template", "rib_armor_trim_smithing_template", "spire_armor_trim_smithing_template", "wayfinder_armor_trim_smithing_template", "shaper_armor_trim_smithing_template", "silence_armor_trim_smithing_template", "raiser_armor_trim_smithing_template", "host_armor_trim_smithing_template", "flow_armor_trim_smithing_template", "bolt_armor_trim_smithing_template"))); // todo nog bij voegen

        // movement goals
        items.add(GoalListItemV2.createImaged("sprint 1 km", "", 1, List.of(km1), "sprint_1km", List.of(Identifier.of("lockout-bingo:goalicon/effect/speed.png"))).addBottomRight("1km"));
        items.add(GoalListItemV2.createImaged("swim 1 km", "", 3, List.of(km1), "swim_1km", List.of(Identifier.of("lockout-bingo:goalicon/effect/dolphins_grace.png"))).addBottomRight("1km"));
        items.add(GoalListItemV2.createImaged("opponent sneaks", "", 2, List.of(dont), "dont_crouch", List.of(Identifier.of("lockout-bingo:goalicon/other/crouch.png"))));
        items.add(GoalListItemV2.createImaged("opponent jumps", "", 3, List.of(dont), "dont_jump", List.of(Identifier.of("lockout-bingo:goalicon/other/jump.png"))));
        items.add(GoalListItemV2.createImaged("use elytra to fly 1km", "", 5, List.of(elytra, km1), "fly_elytra", List.of(Identifier.of("lockout-bingo:goalicon/item/elytra.png"))).addBottomRight("1km"));


        // brew goals
        items.add(GoalListItemV2.createImaged("brew a potion of invisibility", "", 4, List.of(brew, nether), "brew_invis", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/invis.png"))));
        items.add(GoalListItemV2.createImaged("brew a potion of regeneration", "", 4, List.of(brew, nether), "brew_regen", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/regen.png"))));
        items.add(GoalListItemV2.createImaged("brew a potion of oozing", "", 4, List.of(brew, nether), "brew_oozing", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/oozing.png"))));
        items.add(GoalListItemV2.createImaged("brew a potion of weaving", "", 4, List.of(brew, nether), "brew_weaving", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/weaving.png"))));
        items.add(GoalListItemV2.createImaged("brew a potion of leaping", "", 4, List.of(brew, nether), "brew_leaping", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/leaping.png"))));
        items.add(GoalListItemV2.createImaged("brew a potion of healing", "", 4, List.of(brew, nether), "brew_healing", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/healing.png")))); // todo icons
        items.add(GoalListItemV2.createImaged("brew a potion of harming", "", 4, List.of(brew, nether), "brew_harming", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/harming.png")))); // todo icons
        items.add(GoalListItemV2.createImaged("brew a potion of fire resistance", "", 4, List.of(brew, nether), "brew_fire_resistance", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/fire_resistance.png")))); // todo icons
        items.add(GoalListItemV2.createImaged("brew a potion of water breathing", "", 4, List.of(brew, nether), "brew_water_breathing", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/water_breathing.png"))));  // todo icons
        items.add(GoalListItemV2.createImaged("brew a potion of swiftness", "", 4, List.of(brew, nether), "brew_swiftness", List.of(Identifier.of("lockout-bingo:goalicon/item/potions/swiftness.png"))));  // todo icons

        // inventory goals
        items.add(GoalListItemV2.createStackedString("fill inventory with unique items", "", 3, List.of(unique), "fill_inventory_unique", List.of("chest"))); // todo icon
        items.add(GoalListItemV2.createStackedString("fill your inventory", "", 1, List.of(), "fill_inventory", List.of("chest"))); // todo icon


        items.add(GoalListItemV2.createImaged("empty your hunger bar", "", 1, List.of(), "empty_hunger", List.of(Identifier.of("lockout-bingo:goalicon/effect/hunger.png"))));

        // more goals
        items.add(GoalListItemV2.createImaged("get more lvls than opponent", "", 1, List.of(more), "more_lvl", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))));
        items.add(GoalListItemV2.createStackedString("get more hoppers than opponent", "", 2, List.of(more), "more_hoppers", List.of("hopper")));
        items.add(GoalListItemV2.createStackedString("get more dried kelp blocks than opponent", "", 3, List.of(more), "more_dried_kelp_blocks", List.of("dried_kelp_block")));
        items.add(GoalListItemV2.createStackedString("get more white concrete than opponent", "", 3, List.of(more), "more_white_concrete", List.of("white_concrete")));

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

    public static GoalListItemV2 getGoal(String id) throws NoSuchElementException {
        GoalListItemV2 goal = getInstance().items.stream().filter(item -> item.id.equals(id)).findFirst().orElse(null);
        if (goal == null) {
            throw new NoSuchElementException("Goal with id " + id + " not found");
        }
        return goal;
    }

    public static int goalCount() {
        return getInstance().items.size();
    }



}
