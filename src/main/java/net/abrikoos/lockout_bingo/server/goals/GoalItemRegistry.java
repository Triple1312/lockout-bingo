package net.abrikoos.lockout_bingo.server.goals;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.util.BlockoutList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import java.util.List;

import static net.abrikoos.lockout_bingo.server.goals.LockoutGoalTag.*;

public class GoalItemRegistry {
    public BlockoutList<GoalListItem> items;

    private static GoalItemRegistry instance;

    public GoalItemRegistry() {
        items = new BlockoutList<>();

        items.add(new GoalListItem("Opponent obtains crafting table", "An enemy obtains a crafting table", 3, List.of(dont), "no_crafting_table", List.of(Identifier.of("lockout-bingo:goalicon/block/crafting_table.png"))));
        items.add(new GoalListItem("Opponent obtains obsidian", "opponent obtains obsidian", 2, List.of(dont), "no_obsidian", List.of(Identifier.of("lockout-bingo:goalicon/block/obsidian.png"))));
        items.add(new GoalListItem("Opponent obtains netherrack", "opponent obtains netherrack", 4, List.of(dont), "no_netherrack", List.of(Identifier.of("lockout-bingo:goalicon/block/netherrack.png"))));
        items.add(new GoalListItem("Opponent obtains seeds", "opponent obtains seeds", 2, List.of(dont), "no_seeds", List.of(Identifier.of("minecraft:textures/item/wheat_seeds.png"))));
        items.add(new GoalListItem("Opponent gets hit by a snowball", "opponent gets hit by a snowball", 2, List.of(opponent), "snowball_hit", List.of(Identifier.of("minecraft:textures/item/snowball.png"))));
        items.add(new GoalListItem("Opponent takes fall damage", "opponent takes fall damage", 2, List.of(dont), "fall_damage", List.of(Identifier.of("lockout-bingo:goalicon/block/stone.png")))); // todo icon
        items.add(new GoalListItem("Opponent dies", "", 2, List.of(opponent, die), "no_die", List.of(Identifier.of("lockout-bingo:goalicon/other/opponent.png")) ));
        items.add(new GoalListItem("Opponent dies 3 times", "", 2, List.of(opponent, die, C3), "no_die_3", List.of(Identifier.of("lockout-bingo:goalicon/other/opponent.png")) ));
        items.add(new GoalListItem("Opponent catches fire", "", 3, List.of(opponent), "no_fire", List.of(Identifier.of("lockout-bingo", "goalicon/other/fire.png"))));

        items.add(new GoalListItem("obtain end crystal", "", 3, List.of(nether), "obtain_end_crystal", List.of(Identifier.of("minecraft:textures/item/end_crystal.png"))));
        items.add(new GoalListItem("obtain bell", "", 2, List.of(village), "obtain_bell", List.of(Identifier.of("lockout-bingo:goalicon/block/bell.png"))));
        items.add(new GoalListItem("dont touch water", "dont touch water", 1, List.of(dont), "no_water", List.of(Identifier.of("lockout-bingo:goalicon/block/water.png")))); // todo icon
        items.add(new GoalListItem("obtain a bottle o enchanting", "obtain bottle o enchanting", 1, List.of(), "obtain_bottle_o_enchanting", List.of(Identifier.of("lockout-bingo:goalicon/item/bottle_o_enchanting.png"))));
        items.add(new GoalListItem("obtain a slime block", "", 1, List.of(), "obtain_slime_block", List.of(Identifier.of("lockout-bingo:goalicon/block/slime_block.png"))));
        items.add(new GoalListItem("obtain a cake", "", 3, List.of(), "obtain_cake", List.of(Identifier.of("minecraft:textures/item/cake.png"))));
        items.add(new GoalListItem("obtain a soul lantern", "", 2, List.of(nether), "obtain_soul_lantern", List.of(Identifier.of("lockout-bingo:goalicon/block/soul_lantern.png"))));
        items.add(new GoalListItem("obtain heart of the sea", "", 2, List.of(), "obtain_heart_of_the_sea", List.of(Identifier.of("minecraft:textures/item/heart_of_the_sea.png"))));
        items.add(new GoalListItem("obtain sponge", "", 4, List.of(), "obtain_sponge", List.of(Identifier.of("lockout-bingo:goalicon/block/sponge.png"))));
        items.add(new GoalListItem("obtain an end rod", "", 5, List.of(end), "obtain_end_rod", List.of(Identifier.of("lockout-bingo:goalicon/block/end_rod.png"))));
        items.add(new GoalListItem("obtain sea lantern", "", 4, List.of(silk_touch), "obtain_sea_lantern", List.of(Identifier.of("lockout-bingo:goalicon/block/sea_lantern.png"))));
        items.add(new GoalListItem("obtain mossy stone brick wall", "", 3, List.of(), "obtain_mossy_stone_brick_wall", List.of(Identifier.of("lockout-bingo:goalicon/block/mossy_stone_brick_wall.png"))));
        items.add(new GoalListItem("obtain crying obsidian", "", 2, List.of(nether), "obtain_crying_obsidian", List.of(Identifier.of("lockout-bingo:goalicon/block/crying_obsidian.png"))));
        items.add(new GoalListItem("obtain mushroom stem", "", 3, List.of(silk_touch), "obtain_mushroom_stem", List.of(Identifier.of("lockout-bingo:goalicon/block/mushroom_stem.png"))));
        items.add(new GoalListItem("obtain a bubble coral block", "", 3, List.of(silk_touch), "obtain_coral_block", List.of(Identifier.of("lockout-bingo:goalicon/block/bubble_coral_block.png"))));
        items.add(new GoalListItem("obtain a shulker box", "", 5, List.of(end), "obtain_shulker_box", List.of(Identifier.of("lockout-bingo:goalicon/block/shulker_box.png"))));
        items.add(new GoalListItem("obtain a bookshelf", "", 2, List.of(), "obtain_bookshelf", List.of(Identifier.of("lockout-bingo:goalicon/block/bookshelf.png"))));
        items.add(new GoalListItem("obtain flowering azelia", "", 3, List.of(), "obtain_flowering_azelia", List.of(Identifier.of("lockout-bingo:goalicon/block/flowering_azelia.png"))));
        items.add(new GoalListItem("obtain scaffolding", "", 3, List.of(), "obtain_scaffolding", List.of(Identifier.of("lockout-bingo:goalicon/block/scaffolding.png"))));
        items.add(new GoalListItem("obtain a bucket of powdered snow", "", 3, List.of(), "obtain_snow_bucket", List.of(Identifier.of("lockout-bingo:goalicon/item/powder_snow_bucket.png"))));
        items.add(new GoalListItem("obtain ancient debris", "", 5, List.of(nether), "obtain_ancient_debris", List.of(Identifier.of("lockout-bingo:goalicon/block/ancient_debris.png"))));
        items.add(new GoalListItem("obtain an ender chest", "", 4, List.of(nether), "obtain_ender_chest", List.of(Identifier.of("lockout-bingo:goalicon/block/ender_chest.png"))));
        items.add(new GoalListItem("obtain the dragon egg", "", 5, List.of(end), "obtain_dragon_egg", List.of(Identifier.of("lockout-bingo:goalicon/block/dragon_egg.png"))));
        //        items.add(new GoalListItem("obtain dragons breath", "", 1, List.of(), "obtain_dragons_breath", List.of(Identifier.of("minecraft:textures/item/dragons_breath.png"))));
        items.add(new GoalListItem("obtain TNT", "", 2, List.of(), "obtain_tnt", List.of(Identifier.of("lockout-bingo:goalicon/block/tnt.png"))));
        items.add(new GoalListItem("obtain mud brick wall", "", 3, List.of(), "obtain_mud_brick_wall", List.of(Identifier.of("lockout-bingo:goalicon/block/mud_brick_wall.png"))));
        items.add(new GoalListItem("obtain a calibrated sculk sensor", "", 4, List.of(), "obtain_calibrated_sculk_sensor", List.of(Identifier.of("lockout-bingo:goalicon/block/calibrated_sculk_sensor.png"))));
        items.add(new GoalListItem("obtain blue ice", "", 4, List.of(silk_touch), "obtain_blue_ice", List.of(Identifier.of("lockout-bingo:goalicon/block/blue_ice.png"))));
        items.add(new GoalListItem("obtain a cobweb", "", 2, List.of(), "obtain_cobweb", List.of(Identifier.of("minecraft:textures/block/cobweb.png"))));
        items.add(new GoalListItem("obtain goat horn", "", 3, List.of(), "obtain_goat_horn", List.of(Identifier.of("minecraft:textures/item/goat_horn.png"))));
        items.add(new GoalListItem("obtain a nautilus shell", "", 2, List.of(), "obtain_nautilus_shell", List.of(Identifier.of("minecraft:textures/item/nautilus_shell.png"))));
        items.add(new GoalListItem("obtain exposed cut copper stairs", "", 4, List.of(), "obtain_exposed_copper_stairs", List.of(Identifier.of("lockout-bingo:goalicon/block/exposed_cut_copper_stairs.png"))));
        items.add(new GoalListItem("obtain a copper bulb", "", 4, List.of(nether), "obtain_copper_bulb", List.of(Identifier.of("lockout-bingo:goalicon/block/lit_copper_bulb.png"))));
        items.add(new GoalListItem("obtain a daylight detector", "", 2, List.of(nether), "obtain_daylight_detector", List.of(Identifier.of("lockout-bingo:goalicon/block/daylight_detector.png"))));
        items.add(new GoalListItem("obtain a redstone repeater", "", 2, List.of(redstone), "obtain_repeater", List.of(Identifier.of("lockout-bingo:goalicon/block/repeater.png"))));
        items.add(new GoalListItem("obtain a piston", "", 2, List.of(redstone), "obtain_piston", List.of(Identifier.of("lockout-bingo:goalicon/block/piston.png"))));
        items.add(new GoalListItem("obtain a sticky piston", "", 3, List.of(redstone), "obtain_sticky_piston", List.of(Identifier.of("lockout-bingo:goalicon/block/sticky_piston.png"))));
        items.add(new GoalListItem("obtain a dispenser", "", 2, List.of(redstone), "obtain_dispenser", List.of(Identifier.of("lockout-bingo:goalicon/block/dispenser.png"))));
        items.add(new GoalListItem("obtain an activator rail", "", 2, List.of(redstone), "obtain_activator_rail", List.of(Identifier.of("minecraft:textures/block/activator_rail.png"))));
        items.add(new GoalListItem("obtain a detector rail", "", 2, List.of(redstone), "obtain_detector_rail", List.of(Identifier.of("minecraft:textures/block/detector_rail.png"))));
        items.add(new GoalListItem("obtain a powered rail", "", 3, List.of(redstone), "obtain_powered_rail", List.of(Identifier.of("minecraft:textures/block/powered_rail.png"))));
        items.add(new GoalListItem("obtain a clock", "", 1, List.of(redstone), "obtain_clock", List.of(Identifier.of("lockout-bingo:goalicon/item/clock.png"))));
        items.add(new GoalListItem("obtain a comparator", "", 2, List.of(redstone), "obtain_comparator", List.of(Identifier.of("lockout-bingo:goalicon/block/comparator.png"))));
        items.add(new GoalListItem("obtain an observer", "", 3, List.of(redstone), "obtain_observer", List.of(Identifier.of("lockout-bingo:goalicon/block/observer.png"))));
        items.add(new GoalListItem("obtain every type of raw ore block", "", 4, List.of(), "obtain_all_raw_ore_blocks", List.of(Identifier.of("minecraft:textures/block/raw_iron_block.png"), Identifier.of("minecraft:textures/block/raw_copper_block.png"), Identifier.of("minecraft:textures/block/raw_gold_block.png"))));

        items.add(new GoalListItem("obtain 15 unique types of stairs", "", 5, List.of(C15, unique), "stairs_15", List.of(Identifier.of("lockout-bingo:goalicon/block/stairs/oak_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/smooth_quartz_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/smooth_red_sandstone_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/deepslate_tile_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/cherry_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/stone_brick_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/cut_copper_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/polished_granite_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/bamboo_mosaic_stairs.png"),Identifier.of("lockout-bingo:goalicon/block/stairs/end_stone_brick_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/dark_prismarine_stairs.png"),Identifier.of("lockout-bingo:goalicon/block/stairs/mossy_cobblestone_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/weathered_cut_copper_stairs.png"))));
        items.add(new GoalListItem("obtain 5 unique types of stairs", "", 2, List.of(C5, unique), "stairs_5", List.of(Identifier.of("lockout-bingo:goalicon/block/stairs/oak_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/smooth_quartz_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/smooth_red_sandstone_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/deepslate_tile_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/cherry_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/stone_brick_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/cut_copper_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/polished_granite_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/bamboo_mosaic_stairs.png"),Identifier.of("lockout-bingo:goalicon/block/stairs/end_stone_brick_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/dark_prismarine_stairs.png"),Identifier.of("lockout-bingo:goalicon/block/stairs/mossy_cobblestone_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/weathered_cut_copper_stairs.png"))));
        items.add(new GoalListItem("obtain 10 unique types of stairs", "", 3, List.of(C10, unique), "stairs_10", List.of(Identifier.of("lockout-bingo:goalicon/block/stairs/oak_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/smooth_quartz_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/smooth_red_sandstone_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/deepslate_tile_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/cherry_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/stone_brick_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/cut_copper_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/polished_granite_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/bamboo_mosaic_stairs.png"),Identifier.of("lockout-bingo:goalicon/block/stairs/end_stone_brick_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/dark_prismarine_stairs.png"),Identifier.of("lockout-bingo:goalicon/block/stairs/mossy_cobblestone_stairs.png"), Identifier.of("lockout-bingo:goalicon/block/stairs/weathered_cut_copper_stairs.png"))));

        // tool goals
        items.add(new GoalListItem("obtain a full set of wooden tools", "", 1, List.of(tools), "obtain_wooden_toolset", List.of(Identifier.of("minecraft:textures/item/wooden_hoe.png"), Identifier.of("minecraft:textures/item/wooden_hoe.png"), Identifier.of("minecraft:textures/item/wooden_pickaxe.png"), Identifier.of("minecraft:textures/item/wooden_shovel.png"), Identifier.of("minecraft:textures/item/wooden_sword.png"))));
        items.add(new GoalListItem("obtain a full set of stone tools", "", 1, List.of(tools), "obtain_stone_toolset", List.of(Identifier.of("minecraft:textures/item/stone_hoe.png"), Identifier.of("minecraft:textures/item/stone_hoe.png"), Identifier.of("minecraft:textures/item/stone_pickaxe.png"), Identifier.of("minecraft:textures/item/stone_shovel.png"), Identifier.of("minecraft:textures/item/stone_sword.png"))));
        items.add(new GoalListItem("obtain a full set of iron tools", "", 3, List.of(tools), "obtain_iron_toolset", List.of(Identifier.of("minecraft:textures/item/iron_hoe.png"), Identifier.of("minecraft:textures/item/iron_hoe.png"), Identifier.of("minecraft:textures/item/iron_pickaxe.png"), Identifier.of("minecraft:textures/item/iron_shovel.png"), Identifier.of("minecraft:textures/item/iron_sword.png"))));
        items.add(new GoalListItem("obtain a full set of golden tools", "", 4, List.of(tools), "obtain_golden_toolset", List.of(Identifier.of("minecraft:textures/item/golden_hoe.png"), Identifier.of("minecraft:textures/item/golden_hoe.png"), Identifier.of("minecraft:textures/item/golden_pickaxe.png"), Identifier.of("minecraft:textures/item/golden_shovel.png"), Identifier.of("minecraft:textures/item/golden_sword.png"))));
        items.add(new GoalListItem("obtain a full set of diamond tools", "", 5, List.of(tools), "obtain_diamond_toolset", List.of(Identifier.of("minecraft:textures/item/diamond_hoe.png"), Identifier.of("minecraft:textures/item/diamond_hoe.png"), Identifier.of("minecraft:textures/item/diamond_pickaxe.png"), Identifier.of("minecraft:textures/item/diamond_shovel.png"), Identifier.of("minecraft:textures/item/diamond_sword.png"))));
        items.add(new GoalListItem("obtain every type of axe", "", 4, List.of(tools), "obtain_all_axes", List.of(Identifier.of("minecraft:textures/item/wooden_axe.png"), Identifier.of("minecraft:textures/item/stone_axe.png"), Identifier.of("minecraft:textures/item/iron_axe.png"), Identifier.of("minecraft:textures/item/golden_axe.png"), Identifier.of("minecraft:textures/item/diamond_axe.png"), Identifier.of("minecraft:textures/item/netherite_axe.png"))));
        items.add(new GoalListItem("obtain every type of hoe", "", 4, List.of(tools), "obtain_all_hoes", List.of(Identifier.of("minecraft:textures/item/wooden_hoe.png"), Identifier.of("minecraft:textures/item/stone_hoe.png"), Identifier.of("minecraft:textures/item/iron_hoe.png"), Identifier.of("minecraft:textures/item/golden_hoe.png"), Identifier.of("minecraft:textures/item/diamond_hoe.png"), Identifier.of("minecraft:textures/item/netherite_hoe.png"))));
        items.add(new GoalListItem("obtain every type of pickaxe", "", 4, List.of(tools), "obtain_all_pickaxes", List.of(Identifier.of("minecraft:textures/item/wooden_pickaxe.png"), Identifier.of("minecraft:textures/item/stone_pickaxe.png"), Identifier.of("minecraft:textures/item/iron_pickaxe.png"), Identifier.of("minecraft:textures/item/golden_pickaxe.png"), Identifier.of("minecraft:textures/item/diamond_pickaxe.png"), Identifier.of("minecraft:textures/item/netherite_pickaxe.png"))));
        items.add(new GoalListItem("obtain every type of shovel", "", 4, List.of(tools), "obtain_all_shovels", List.of(Identifier.of("minecraft:textures/item/wooden_shovel.png"), Identifier.of("minecraft:textures/item/stone_shovel.png"), Identifier.of("minecraft:textures/item/iron_shovel.png"), Identifier.of("minecraft:textures/item/golden_shovel.png"), Identifier.of("minecraft:textures/item/diamond_shovel.png"), Identifier.of("minecraft:textures/item/netherite_shovel.png"))));
        items.add(new GoalListItem("obtain every type of sword", "", 4, List.of(tools), "obtain_all_swords", List.of(Identifier.of("minecraft:textures/item/wooden_sword.png"), Identifier.of("minecraft:textures/item/stone_sword.png"), Identifier.of("minecraft:textures/item/iron_sword.png"), Identifier.of("minecraft:textures/item/golden_sword.png"), Identifier.of("minecraft:textures/item/diamond_sword.png"), Identifier.of("minecraft:textures/item/netherite_sword.png"))));
        //        items.add(new GoalListItem("obtain a full set of netherite tools", "", 1, List.of(tools), "obtain_netherite_toolset", List.of(Identifier.of("minecraft:textures/item/netherite_hoe.png"), Identifier.of("minecraft:textures/item/netherite_hoe.png"), Identifier.of("minecraft:textures/item/netherite_pickaxe.png"), Identifier.of("minecraft:textures/item/netherite_shovel.png"), Identifier.of("minecraft:textures/item/netherite_sword.png"))));

        // armor goals
        items.add(new GoalListItem("wear a piece of chainmail armor", "", 2, List.of(armor), "wear_chainmail", List.of(Identifier.of("minecraft:textures/item/chainmail_helmet.png"), Identifier.of("minecraft:textures/item/chainmail_chestplate.png"), Identifier.of("minecraft:textures/item/chainmail_leggings.png"), Identifier.of("minecraft:textures/item/chainmail_boots.png"))));
        items.add(new GoalListItem("wear a full set of leather armor", "", 1, List.of(armor), "wear_full_leather", List.of(Identifier.of("lockout-bingo:goalicon/item/armor/leather_cap.png"), Identifier.of("lockout-bingo:goalicon/item/armor/leather_chestplate.png"), Identifier.of("lockout-bingo:goalicon/item/armor/leather_pants.png"), Identifier.of("lockout-bingo:goalicon/item/armor/leather_boots.png"))));
        items.add(new GoalListItem("wear a full set of iron armor", "", 3, List.of(armor), "wear_full_iron", List.of(Identifier.of("minecraft:textures/item/iron_helmet.png"), Identifier.of("minecraft:textures/item/iron_chestplate.png"), Identifier.of("minecraft:textures/item/iron_leggings.png"), Identifier.of("minecraft:textures/item/iron_boots.png"))));
        items.add(new GoalListItem("wear a full set of golden armor", "", 4, List.of(armor), "wear_full_golden", List.of(Identifier.of("minecraft:textures/item/golden_helmet.png"), Identifier.of("minecraft:textures/item/golden_chestplate.png"), Identifier.of("minecraft:textures/item/golden_leggings.png"), Identifier.of("minecraft:textures/item/golden_boots.png"))));
        items.add(new GoalListItem("wear a full set of diamond armor", "", 5, List.of(armor), "wear_full_diamond", List.of(Identifier.of("minecraft:textures/item/diamond_helmet.png"), Identifier.of("minecraft:textures/item/diamond_chestplate.png"), Identifier.of("minecraft:textures/item/diamond_leggings.png"), Identifier.of("minecraft:textures/item/diamond_boots.png"))));
        items.add(new GoalListItem("wear a piece of netherite armor", "", 6, List.of(armor), "wear_netherite", List.of(Identifier.of("minecraft:textures/item/netherite_helmet.png"), Identifier.of("minecraft:textures/item/netherite_chestplate.png"), Identifier.of("minecraft:textures/item/netherite_leggings.png"), Identifier.of("minecraft:textures/item/netherite_boots.png"))));
        items.add(new GoalListItem("wear a piece of colored armor", "", 2, List.of(armor), "wear_colored", List.of(Identifier.of("lockout-bingo:goalicon/item/armor/red_leather_cap.png"), Identifier.of("lockout-bingo:goalicon/item/armor/purple_leather_boots.png"), Identifier.of("lockout-bingo:goalicon/item/armor/green_leather_pants.png"), Identifier.of("lockout-bingo:goalicon/item/armor/orange_leather_chestplate.png"))));
        items.add(new GoalListItem("wear a trimmed armor piece", "", 3, List.of(armor), "wear_trimmed", List.of(Identifier.of("lockout-bingo:goalicon/item/armor/emerald_trimmed_iron_helmet.png"), Identifier.of("lockout-bingo:goalicon/item/armor/copper_trimmed_diamond_leggings.png"),Identifier.of("lockout-bingo:goalicon/item/armor/redstone_trimmed_gold_chestplate.png"), Identifier.of("lockout-bingo:goalicon/item/armor/quartz_trimmed_netherite_boots.png")) ));
        items.add(new GoalListItem("wear a fully trimmed armor set", "", 3, List.of(armor, C4), "wear_trimmed_set", List.of(Identifier.of("lockout-bingo:goalicon/item/armor/emerald_trimmed_iron_helmet.png"), Identifier.of("lockout-bingo:goalicon/item/armor/copper_trimmed_diamond_leggings.png"),Identifier.of("lockout-bingo:goalicon/item/armor/redstone_trimmed_gold_chestplate.png"), Identifier.of("lockout-bingo:goalicon/item/armor/quartz_trimmed_netherite_boots.png")) ));
        items.add(new GoalListItem("wear a carved pumpkin for 1 minute", "", 3, List.of(armor), "wear_pumpkin", List.of(Identifier.of("lockout-bingo:goalicon/block/carved_pumpkin")))); // todo change time


        items.add(new GoalListItem("obtain 4 types of seeds", "", 3, List.of(C4, unique), "obtain_all_seeds", List.of(Identifier.of("minecraft:textures/item/wheat_seeds.png"), Identifier.of("minecraft:textures/item/beetroot_seeds.png"), Identifier.of("minecraft:textures/item/melon_seeds.png"), Identifier.of("minecraft:textures/item/pumpkin_seeds.png"), Identifier.of("minecraft:textures/item/torchflower_seeds.png"))));
        items.add(new GoalListItem("obtain 6 types of flowers", "", 3, List.of(C6, unique), "obtain_6_flowers", List.of(Identifier.of("minecraft:textures/block/poppy.png"), Identifier.of("minecraft:textures/block/dandelion.png"), Identifier.of("minecraft:textures/block/blue_orchid.png"), Identifier.of("minecraft:textures/block/allium.png"), Identifier.of("minecraft:textures/block/azure_bluet.png"), Identifier.of("minecraft:textures/block/red_tulip.png"), Identifier.of("minecraft:textures/block/orange_tulip.png"), Identifier.of("minecraft:textures/block/white_tulip.png"), Identifier.of("minecraft:textures/block/pink_tulip.png"), Identifier.of("minecraft:textures/block/oxeye_daisy.png"), Identifier.of("minecraft:textures/block/cornflower.png"), Identifier.of("minecraft:textures/block/lily_of_the_valley.png"), Identifier.of("minecraft:textures/block/wither_rose.png"))));
        items.add(new GoalListItem("obtain black glazed terracotta", "", 2, List.of(), "obtain_black_glazed_terracotta", List.of(Identifier.of("lockout-bingo:goalicon/block/black_glazed_terracotta.png"))));
        items.add(new GoalListItem("obtain all saplings", "", 4, List.of(), "obtain_all_saplings", List.of(Identifier.of("minecraft:textures/block/oak_sapling.png"), Identifier.of("minecraft:textures/block/spruce_sapling.png"), Identifier.of("minecraft:textures/block/birch_sapling.png"), Identifier.of("minecraft:textures/block/jungle_sapling.png"), Identifier.of("minecraft:textures/block/acacia_sapling.png"), Identifier.of("minecraft:textures/block/dark_oak_sapling.png"))));
        items.add(new GoalListItem("obtain 2 music disks", "", 3, List.of(C2), "obtain_2_music_disks", List.of(Identifier.of("minecraft:textures/item/music_disc_11.png"), Identifier.of("minecraft:textures/item/music_disc_13.png"), Identifier.of("minecraft:textures/item/music_disc_blocks.png"), Identifier.of("minecraft:textures/item/music_disc_cat.png"), Identifier.of("minecraft:textures/item/music_disc_chirp.png"), Identifier.of("minecraft:textures/item/music_disc_far.png"), Identifier.of("minecraft:textures/item/music_disc_mall.png"), Identifier.of("minecraft:textures/item/music_disc_mellohi.png"), Identifier.of("minecraft:textures/item/music_disc_stal.png"), Identifier.of("minecraft:textures/item/music_disc_strad.png"), Identifier.of("minecraft:textures/item/music_disc_wait.png"), Identifier.of("minecraft:textures/item/music_disc_ward.png"))));
        items.add(new GoalListItem("obtain all types of horse armor", "", 4, List.of(nether, unique), "obtain_all_harmor", List.of(Identifier.of("minecraft:textures/item/iron_horse_armor.png"), Identifier.of("minecraft:textures/item/golden_horse_armor.png"), Identifier.of("minecraft:textures/item/diamond_horse_armor.png"))));

        items.add(new GoalListItem("eat 5 unique foods", "", 1, List.of(C5, eat, unique), "eat_5", List.of(Identifier.of("minecraft:textures/item/apple.png"))));
        items.add(new GoalListItem("eat 10 unique foods", "", 2, List.of(C10, eat, unique), "eat_10", List.of(Identifier.of("minecraft:textures/item/apple.png"))));
        items.add(new GoalListItem("eat 15 unique foods", "", 3, List.of(C15, eat, unique), "eat_15", List.of(Identifier.of("minecraft:textures/item/apple.png"))));
        items.add(new GoalListItem("eat 20 unique foods", "", 4, List.of(C20, eat, unique), "eat_20", List.of(Identifier.of("minecraft:textures/item/apple.png"))));
        items.add(new GoalListItem("eat suspicious stew", "", 1, List.of(eat), "eat_suspicious_stew", List.of(Identifier.of("minecraft:textures/item/suspicious_stew.png"))));
        items.add(new GoalListItem("eat glow berries", "", 3, List.of(eat), "eat_glow_berries", List.of(Identifier.of("minecraft:textures/item/glow_berries.png"))));
        items.add(new GoalListItem("eat rabbit stew", "", 2, List.of(eat), "eat_rabbit_stew", List.of(Identifier.of("minecraft:textures/item/rabbit_stew.png"))));
        items.add(new GoalListItem("eat beetroot soup", "", 4, List.of(eat), "eat_beetroot_soup", List.of(Identifier.of("minecraft:textures/item/beetroot_soup.png"))));
        items.add(new GoalListItem("eat honey bottle", "", 4, List.of(eat), "eat_honey_bottle", List.of(Identifier.of("minecraft:textures/item/honey_bottle.png"))));
//        items.add(new GoalListItem("eat honeycomb", "", 1, List.of(), "eat_honeycomb", List.of(Identifier.of("minecraft:textures/item/honeycomb.png"))));
        items.add(new GoalListItem("eat golden apple", "", 3, List.of(eat), "eat_golden_apple", List.of(Identifier.of("minecraft:textures/item/golden_apple.png"))));
        items.add(new GoalListItem("eat a cookie", "", 2, List.of(eat), "eat_cookie", List.of(Identifier.of("minecraft:textures/item/cookie.png"))));
        items.add(new GoalListItem("eat pumpkin pie", "", 2, List.of(eat), "eat_pumpkin_pie", List.of(Identifier.of("minecraft:textures/item/pumpkin_pie.png"))));
        items.add(new GoalListItem("eat poisonous potato", "", 1, List.of(eat), "eat_poisonous_potato", List.of(Identifier.of("minecraft:textures/item/poisonous_potato.png"))));
        items.add(new GoalListItem("eat dried kelp", "", 1, List.of(eat), "eat_dried_kelp", List.of(Identifier.of("minecraft:textures/item/dried_kelp.png"))));
        items.add(new GoalListItem("eat chorus fruit", "", 4, List.of(eat, end), "eat_chorus_fruit", List.of(Identifier.of("minecraft:textures/item/chorus_fruit.png"))));

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
        items.add(new GoalListItem("kill a ghast", "", 2, List.of(kill), "kill_ghast", List.of(Identifier.of("lockout-bingo:goalicon/entity/ghast.png"))));
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


        items.add(new GoalListItem("enter the nether", "", 2, List.of(advancement), "nether_adv", List.of(Identifier.of("minecraft:textures/block/nether_portal.png"))));
        items.add(new GoalListItem("enter the end", "", 3, List.of(advancement, nether), "end_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/end_portal_frame.png"))));
        items.add(new GoalListItem("use an enchanting table", "", 3, List.of(advancement), "enchanter_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/enchanting_table.png"))));
        items.add(new GoalListItem("zombie doctor", "", 4, List.of(advancement), "zombie_doc_adv", List.of(Identifier.of("minecraft:textures/item/golden_apple.png"))));
        items.add(new GoalListItem("enter the stronghold", "", 3, List.of(advancement, nether), "eye_spy_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/eye_of_ender.png"))));
        items.add(new GoalListItem("kill a ghast with its own fireball", "", 3, List.of(advancement, nether), "return_to_sender_adv", List.of(Identifier.of("minecraft:textures/item/fire_charge.png"))));
        items.add(new GoalListItem("find a bastion remnant", "", 3, List.of(advancement, nether), "those_were_the_days_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/polished_blackstone_bricks.png")))); // This is a fictional texture path for a structure
        items.add(new GoalListItem("find a nether fortress", "", 3, List.of(advancement, nether), "terrible_fortress_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/nether_bricks.png"))));
        items.add(new GoalListItem("travel 1km in the nether", "", 4, List.of(advancement, nether), "subspace_bubble_adv", List.of(Identifier.of("minecraft:textures/item/map.png"))));
        items.add(new GoalListItem("distract a piglin with gold", "", 2, List.of(advancement, nether), "oh_shiny_adv", List.of(Identifier.of("minecraft:textures/item/gold_ingot.png"))));
        items.add(new GoalListItem("ride a strider with a warped fungus on a stick", "", 4, List.of(ride, advancement, nether), "this_boat_has_legs_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/strider.png"))));
        items.add(new GoalListItem("use a lodestone", "", 4, List.of(advancement, nether), "country_lode_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/lodestone.png"))));
        items.add(new GoalListItem("use a respawn anchor", "", 3, List.of(advancement, nether), "use_respawn_anchor_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/respawn_anchor.png"))));
        items.add(new GoalListItem("get a wither skeleton skull", "", 5, List.of(advancement, nether), "spooky_scary_skeletons_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/wither_skeleton_skull.png"))));
        items.add(new GoalListItem("travel to every nether biome", "", 3, List.of(advancement, nether), "hot_tourist_adv", List.of(Identifier.of("minecraft:textures/item/netherite_boots.png"))));
        items.add(new GoalListItem("great view from above", "", 5, List.of(advancement, end), "great_view_adv", List.of(Identifier.of("minecraft:textures/item/ender_pearl.png"))));
        items.add(new GoalListItem("what a deal", "", 1, List.of(advancement, village), "trade_adv", List.of(Identifier.of("minecraft:textures/item/emerald.png"))));
        items.add(new GoalListItem("slide in a honey block to break your fall", "", 5, List.of(advancement), "sticky_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/honey_block.png"))));
        items.add(new GoalListItem("kill a mob near a sculk catalyst", "", 3, List.of(advancement), "spreads_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/sculk_sensor.png"))));
        items.add(new GoalListItem("make a golem", "", 2, List.of(advancement), "hired_help_adv", List.of(Identifier.of("minecraft:textures/block/carved_pumpkin.png"))));
        items.add(new GoalListItem("arbalistic", "", 4, List.of(advancement), "arbalistic_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/crossbow.png"))));
        items.add(new GoalListItem("walk on powdered snow with leather boots", "", 3, List.of(advancement), "walk_snow_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/leather_boots.png"))));
        items.add(new GoalListItem("shoot a bullseye with a bow and arrow", "", 2, List.of(advancement, redstone), "bullseye_adv", List.of(Identifier.of("minecraft:textures/item/bow.png"))));
        items.add(new GoalListItem("total beelocation", "", 3, List.of(advancement, silk_touch), "beelocation_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/bee_nest.png"))));
        items.add(new GoalListItem("get a tadpole in a bucket", "", 4, List.of(breed, advancement), "tadpole_bucket_adv", List.of(Identifier.of("minecraft:textures/item/tadpole_bucket.png"))));
        items.add(new GoalListItem("obtain a sniffer egg", "", 3, List.of(advancement), "sniffer_egg_adv", List.of(Identifier.of("minecraft:textures/item/sniffer_egg.png"))));
        items.add(new GoalListItem("tame 4 unique types of cats", "", 3, List.of(C4, tame, unique), "catalogue_4_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(new GoalListItem("time 3 unique types of wolves", "", 4, List.of(C3, tame, unique), "whole_pack_3_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));
        items.add(new GoalListItem("tactical fishing", "", 1, List.of(advancement), "tactical_fishing_adv", List.of(Identifier.of("minecraft:textures/item/tropical_fish_bucket.png"))));
        items.add(new GoalListItem("scrape the wax off a copper block", "", 3, List.of(advancement), "wax_off_adv", List.of(Identifier.of("minecraft:textures/item/stone_axe.png"))));
        items.add(new GoalListItem("axolotl in a bucket", "", 3, List.of(advancement), "axolotl_bucket_adv", List.of(Identifier.of("minecraft:textures/item/axolotl_bucket.png"))));
        items.add(new GoalListItem("Crafters crafting crafters", "", 3, List.of(advancement, redstone), "crafters_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/crafter.png"))));
        items.add(new GoalListItem("use a trial key on a vault", "", 4, List.of(advancement), "trial_key_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/trial_key.png"))));
        items.add(new GoalListItem("use wolf armor on a wolf", "", 3, List.of(advancement), "wolf_armor_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/wolf_armor.png"))));
        items.add(new GoalListItem("Sound of music: play a music disk in a meadow", "", 3, List.of(advancement), "sound_of_music_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/jukebox.png"))));
        items.add(new GoalListItem("catch a fish", "", 1, List.of(advancement), "fishy_business_adv", List.of(Identifier.of("minecraft:textures/item/fishing_rod.png"))));
        items.add(new GoalListItem("kill the dragon", "", 5, List.of(advancement, kill), "dragon_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/ender_dragon.png"))));
        items.add(new GoalListItem("get the sinper duel advancement", "", 4, List.of(advancement), "sniper_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/skeleton.png"))));
        items.add(new GoalListItem("get any spyglass advancement", "", 3, List.of(advancement), "spyglass_adv", List.of(Identifier.of("minecraft:textures/item/spyglass.png"))));

        items.add(new GoalListItem("complete 15 advancements", "", 3, List.of(C15, advancement), "adv_15", List.of(Identifier.of("lockout-bingo:goalicon/advancement/challenge_done.png"))));
        items.add(new GoalListItem("complete 25 advancements", "", 3, List.of(C25, advancement), "adv_25", List.of(Identifier.of("lockout-bingo:goalicon/advancement/challenge_done.png"))));
        items.add(new GoalListItem("complete 35 advancements", "", 3, List.of(C35, advancement), "adv_35", List.of(Identifier.of("lockout-bingo:goalicon/advancement/challenge_done.png"))));
        // biome goals
        items.add(new GoalListItem("find an ice spikes biome", "", 3, List.of(biomes), "biome_ice_spikes", List.of(Identifier.of("lockout-bingo:goalicon/biome/ice_spikes.png"))));
        items.add(new GoalListItem("find a badlands biome", "", 3, List.of(biomes), "biome_badlands", List.of(Identifier.of("lockout-bingo:goalicon/biome/badlands.png"))));
        items.add(new GoalListItem("find 15 unique biomes", "", 2, List.of(C15, biomes, unique), "biomes_15", List.of(Identifier.of("minecraft:textures/item/diamond_boots.png"))));
        items.add(new GoalListItem("find 20 unique biomes", "", 3, List.of(C20, biomes, unique), "biomes_20", List.of(Identifier.of("minecraft:textures/item/diamond_boots.png"))));
        items.add(new GoalListItem("find 25 unique biomes", "", 4, List.of(C25, biomes, unique), "biomes_25", List.of(Identifier.of("minecraft:textures/item/diamond_boots.png"))));
        items.add(new GoalListItem("find 30 unique biomes", "", 5, List.of(C30, biomes, unique), "biomes_30", List.of(Identifier.of("minecraft:textures/item/diamond_boots.png"))));

        items.add(new GoalListItem("die by golem", "", 1, List.of(die), "die_golem", List.of(Identifier.of("lockout-bingo:goalicon/entity/iron_golem.png"))));
        items.add(new GoalListItem("die by llama", "", 2, List.of(die), "die_llama", List.of(Identifier.of("lockout-bingo:goalicon/entity/lama.png"))));
        items.add(new GoalListItem("die by bee sting", "", 1, List.of(die), "die_bee", List.of(Identifier.of("lockout-bingo:goalicon/entity/bee.png"))));

        items.add(new GoalListItem("die by trident", "", 2, List.of(die), "die_trident", List.of(Identifier.of("lockout-bingo:goalicon/item/trident.png"))));
        items.add(new GoalListItem("die by sweet berry bush", "", 2, List.of(die), "die_sweet_berry_bush", List.of(Identifier.of("lockout-bingo:goalicon/block/sweet_berry_bush.png"))));
        items.add(new GoalListItem("die by cactus", "", 3, List.of(die), "die_cactus", List.of(Identifier.of("lockout-bingo:goalicon/block/cactus.png"))));
        items.add(new GoalListItem("die by anvil", "", 3, List.of(die), "die_anvil", List.of(Identifier.of("lockout-bingo:goalicon/block/anvil.png"))));
        items.add(new GoalListItem("die by falling stalactite", "", 2, List.of(die), "die_stalactite", List.of(Identifier.of("lockout-bingo:goalicon/block/stalactite.png"))));
        items.add(new GoalListItem("die by falling on a stalagmite", "", 2, List.of(die), "die_stalagmite", List.of(Identifier.of("lockout-bingo:goalicon/block/stalagmite.png"))));
        items.add(new GoalListItem("die by fireworks", "", 3, List.of(die), "die_fireworks", List.of(Identifier.of("lockout-bingo:goalicon/item/firework.png"))));
        items.add(new GoalListItem("freeze to death", "", 2, List.of(die), "die_freeze", List.of(Identifier.of("lockout-bingo:goalicon/block/powdered_snow.png"))));
        items.add(new GoalListItem("die by intentional game design", "", 2, List.of(die), "die_bed", List.of(Identifier.of("lockout-bingo:goalicon/block/bedrock.png"))));
        items.add(new GoalListItem("die by tnt minecart", "", 3, List.of(die), "die_tnt_minecart", List.of(Identifier.of("lockout-bingo:goalicon/entity/tnt_minecart.png"))));

        // ride goals
        items.add(new GoalListItem("ride a pig", "", 2, List.of(ride), "ride_pig", List.of(Identifier.of("lockout-bingo:goalicon/entity/pig.png"))));
        items.add(new GoalListItem("ride a horse", "", 1, List.of(ride), "ride_horse", List.of(Identifier.of("lockout-bingo:goalicon/entity/horse.png"))));
        items.add(new GoalListItem("ride a minecart", "", 3, List.of(), "ride_minecart", List.of(Identifier.of("lockout-bingo:goalicon/item/minecart.png"))));

        // damage goals
        items.add(new GoalListItem("deal 200 damage", "", 2, List.of(C200), "damage_200", List.of(Identifier.of("minecraft:textures/item/diamond_sword.png"))));
        items.add(new GoalListItem("deal 500 damage", "", 3, List.of(C500), "damage_500", List.of(Identifier.of("minecraft:textures/item/diamond_sword.png"))));

        items.add(new GoalListItem("take 200 damage", "", 2, List.of(C200), "damage_take_200", List.of(Identifier.of("lockout-bingo:goalicon/other/damage.png"))));

        // use goals
        items.add(new GoalListItem("use a smithing table", "", 3, List.of(use), "use_smithing_table", List.of(Identifier.of("lockout-bingo:goalicon/block/smithing_table.png"))));
        items.add(new GoalListItem("use a grindstone", "", 2, List.of(use), "use_grindstone", List.of(Identifier.of("lockout-bingo:goalicon/block/grindstone.png"))));
        items.add(new GoalListItem("use a blast furnace", "", 2, List.of(use), "use_blast_furnace", List.of(Identifier.of("lockout-bingo:goalicon/block/blast_furnace.png"))));
        items.add(new GoalListItem("use a cartography table", "", 2, List.of(use), "use_cartography_table", List.of(Identifier.of("lockout-bingo:goalicon/block/cartography_table.png"))));
        items.add(new GoalListItem("use a loom", "", 3, List.of(use), "use_loom", List.of(Identifier.of("lockout-bingo:goalicon/block/loom.png"))));
        items.add(new GoalListItem("use a stonecutter", "", 1, List.of(use), "use_stonecutter", List.of(Identifier.of("lockout-bingo:goalicon/block/stonecutter.png"))));
        items.add(new GoalListItem("use an anvil", "", 3, List.of(use), "use_anvil", List.of(Identifier.of("lockout-bingo:goalicon/block/anvil.png"))));
        items.add(new GoalListItem("use a composter", "", 1, List.of(use), "use_composter", List.of(Identifier.of("lockout-bingo:goalicon/block/composter.png"))));


        // mine goals
        items.add(new GoalListItem("mine emerald ore", "", 5, List.of(), "mine_emerald", List.of(Identifier.of("lockout-bingo:goalicon/block/emerald_ore.png"))));
        items.add(new GoalListItem("mine diamond ore", "", 3, List.of(), "mine_diamond", List.of(Identifier.of("lockout-bingo:goalicon/block/diamond_ore.png"))));
        items.add(new GoalListItem("mine mob spawner", "", 3, List.of(), "mine_spawner", List.of(Identifier.of("lockout-bingo:goalicon/block/spawner.png"))));
        items.add(new GoalListItem("mine a turtle egg", "", 4, List.of(), "mine_turtle_egg", List.of(Identifier.of("lockout-bingo:goalicon/block/turtle_egg.png"))));

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
        items.add(new GoalListItem("have 3 active effects at once", "", 3, List.of(effect, C3), "effect_3", List.of(Identifier.of("minecraft:textures/item/milk_bucket.png"))));
        items.add(new GoalListItem("have 6 active effects at once", "", 4, List.of(effect, C6), "effect_6", List.of(Identifier.of("minecraft:textures/item/milk_bucket.png"))));
        items.add(new GoalListItem("dont get any effects", "", 2, List.of(effect, dont), "dont_effect", List.of(Identifier.of("minecraft:textures/item/milk_bucket.png"))));
        items.add(new GoalListItem("dont get glowing", "", 2, List.of(effect, dont), "dont_glowing", List.of(Identifier.of("lockout-bingo:goalicon/effect/glowing.png"))));
        items.add(new GoalListItem("remove all effects using milk", "", 2, List.of(effect), "milk_effect", List.of(Identifier.of("minecraft:textures/item/milk_bucket.png"))));



        items.add(new GoalListItem("break 5 pickaxes", "", 2, List.of(C5, broken), "break_5_pickaxes", List.of(Identifier.of("minecraft:textures/item/wooden_pickaxe.png"), Identifier.of("minecraft:textures/item/stone_pickaxe.png"), Identifier.of("minecraft:textures/item/diamond_pickaxe.png"), Identifier.of("minecraft:textures/item/golden_pickaxe.png"))));
        items.add(new GoalListItem("break a diamond pickaxe", "", 4, List.of(broken), "break_diamond", List.of(Identifier.of("minecraft:textures/item/diamond_pickaxe.png"))));


        items.add(new GoalListItem("get them in a bucket !!", "", 5, List.of(), "get_them_in_a_bucket", List.of(Identifier.of("minecraft:textures/item/pufferfish_bucket.png"))));

        // level goals
        items.add(new GoalListItem("reach level 15", "", 2, List.of(lvl, C15), "lvl_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))));
        items.add(new GoalListItem("reach level 30", "", 4, List.of(lvl, C30), "lvl_30", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))));


        // position goals
        items.add(new GoalListItem("reach bedrock", "", 1, List.of(), "reach_bedrock", List.of(Identifier.of("lockout-bingo:goalicon/block/bedrock.png"))));
        items.add(new GoalListItem("reach the sky limit", "", 1, List.of(), "reach_sky_limit", List.of(Identifier.of("lockout-bingo:goalicon/item/sun.png"))));
        items.add(new GoalListItem("fall for 300 blocks", "", 2, List.of(C300), "fall_300", List.of(Identifier.of("lockout-bingo:goalicon/effect/slow_falling.png"))));

        //wool goals
        items.add(new GoalListItem("obtain a stack of lime wool", "", 3, List.of(wool, C64), "64_lime_wool", List.of(Identifier.of("lockout-bingo:goalicon/block/wool/lime_wool.png"))));
        items.add(new GoalListItem("obtain a stack of gray wool", "", 3, List.of(wool, C64), "64_gray_wool", List.of(Identifier.of("lockout-bingo:goalicon/block/wool/gray_wool.png"))));
        items.add(new GoalListItem("obtain a stack of cyan wool", "", 3, List.of(wool, C64), "64_cyan_wool", List.of(Identifier.of("lockout-bingo:goalicon/block/wool/cyan_wool.png"))));
        items.add(new GoalListItem("obtain a stack of brown wool", "", 3, List.of(wool, C64), "64_brown_wool", List.of(Identifier.of("lockout-bingo:goalicon/block/wool/brown_wool.png"))));

        items.add(new GoalListItem("duplicate a smithing template", "", 3, List.of(C2), "duplicate_smithing_template", List.of(Identifier.of("lockout-bingo:goalicon/item/smithing_templates/silence_template.png")))); // todo nog bij voegen

        // movement goals
        items.add(new GoalListItem("sprint 1 km", "", 1, List.of(km1), "sprint_1km", List.of(Identifier.of("lockout-bingo:goalicon/effect/haste.png"))));
        items.add(new GoalListItem("swim 1 km", "", 3, List.of(km1), "swim_1km", List.of(Identifier.of("lockout-bingo:goalicon/effect/dolphins_grace.png"))));
        items.add(new GoalListItem("opponent sneaks", "", 2, List.of(dont), "dont_crouch", List.of(Identifier.of("lockout-bingo:goalicon/effect/invisibility.png"))));
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
        items.add(new GoalListItem("fill inventory with unique items", "", 3, List.of(unique), "fill_inventory_unique", List.of(Identifier.of("lockout-bingo:goalicon/block/chest.png")))); // todo icon
        items.add(new GoalListItem("fill your inventory", "", 1, List.of(), "fill_inventory", List.of(Identifier.of("lockout-bingo:goalicon/block/chest.png")))); // todo icon


        items.add(new GoalListItem("empty your hunger bar", "", 1, List.of(), "empty_hunger", List.of(Identifier.of("lockout-bingo:goalicon/effect/hunger.png"))));

        // more goals
        items.add(new GoalListItem("get more lvls than opponent", "", 1, List.of(more), "more_lvl", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))));
        items.add(new GoalListItem("get more hoppers than opponent", "", 2, List.of(more), "more_hoppers", List.of(Identifier.of("lockout-bingo:goalicon/block/hopper.png"))));
        items.add(new GoalListItem("get more dried kelp blocks than opponent", "", 3, List.of(more), "more_hoppers", List.of(Identifier.of("lockout-bingo:goalicon/block/dried_kelp_block.png"))));
        items.add(new GoalListItem("get more white concrete than opponent", "", 3, List.of(more), "more_hoppers", List.of(Identifier.of("lockout-bingo:goalicon/block/white_concrete.png"))));

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

    public static GoalListItem getGoal(String id) {
        return getInstance().items.stream().filter(item -> item.id.equals(id)).findFirst().orElse(null);
    }

    public static int goalCount() {
        return getInstance().items.size();
    }



}
