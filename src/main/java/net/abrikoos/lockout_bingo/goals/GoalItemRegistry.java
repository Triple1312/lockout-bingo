package net.abrikoos.lockout_bingo.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import net.abrikoos.lockout_bingo.goals.LockoutGoalTag;

import static net.abrikoos.lockout_bingo.goals.LockoutGoalTag.*;

public class GoalItemRegistry {
    public List<GoalListItem> items;

    private static GoalItemRegistry instance;

    public GoalItemRegistry() {
        items = new ArrayList<>();

        items.add(new GoalListItem("Opponent obtains crafting table", "An enemy obtains a crafting table", 1, List.of(dont), "no_crafting_table", List.of(Identifier.of("lockout-bingo:goalicon/block/crafting_table.png"))));
        items.add(new GoalListItem("Opponent obtains obsidian", "opponent obtains obsidian", 1, List.of(dont), "no_obsidian", List.of(Identifier.of("lockout-bingo:goalicon/block/obsidian.png"))));
        items.add(new GoalListItem("obtain end crystal", "", 3, List.of(nether), "obtain_end_crystal", List.of(Identifier.of("minecraft:textures/item/end_crystal.png"))));
        items.add(new GoalListItem("obtain bell", "", 1, List.of(), "obtain_bell", List.of(Identifier.of("lockout-bingo:goalicon/block/bell.png"))));
        items.add(new GoalListItem("obtain a bottle o enchanting", "obtain bottle o enchanting", 1, List.of(), "obtain_bottle_o_enchanting", List.of(Identifier.of("lockout-bingo:goalicon/item/bottle_o_enchanting.png"))));
        items.add(new GoalListItem("obtain a slime block", "", 1, List.of(), "obtain_slime_block", List.of(Identifier.of("lockout-bingo:goalicon/block/slime_block.png"))));
        items.add(new GoalListItem("obtain a cake", "", 1, List.of(), "obtain_cake", List.of(Identifier.of("minecraft:textures/item/cake.png"))));
        items.add(new GoalListItem("obtain a soul lantern", "", 1, List.of(), "obtain_soul_lantern", List.of(Identifier.of("lockout-bingo:goalicon/block/soul_lantern.png"))));
        items.add(new GoalListItem("obtain heart of the sea", "", 1, List.of(), "obtain_heart_of_the_sea", List.of(Identifier.of("minecraft:textures/item/heart_of_the_sea.png"))));
        items.add(new GoalListItem("obtain a wither skeleton skull", "", 1, List.of(), "obtain_wither_skeleton_skull", List.of(Identifier.of("lockout-bingo:goalicon/item/wither_skeleton_skull.png"))));
        items.add(new GoalListItem("obtain sponge", "", 1, List.of(), "obtain_sponge", List.of(Identifier.of("lockout-bingo:goalicon/block/sponge.png"))));
        items.add(new GoalListItem("obtain an end rod", "", 1, List.of(), "obtain_end_rod", List.of(Identifier.of("lockout-bingo:goalicon/block/end_rod.png"))));
        items.add(new GoalListItem("obtain sea lantern", "", 1, List.of(silk_touch), "obtain_sea_lantern", List.of(Identifier.of("lockout-bingo:goalicon/block/sea_lantern.png"))));
        items.add(new GoalListItem("obtain mossy stone brick wall", "", 1, List.of(), "obtain_mossy_stone_brick_wall", List.of(Identifier.of("lockout-bingo:goalicon/block/mossy_stone_brick_wall.png"))));
        items.add(new GoalListItem("obtain crying obsidian", "", 1, List.of(), "obtain_crying_obsidian", List.of(Identifier.of("lockout-bingo:goalicon/block/crying_obsidian.png"))));
        items.add(new GoalListItem("obtain mushroom stem", "", 1, List.of(silk_touch), "obtain_mushroom_stem", List.of(Identifier.of("lockout-bingo:goalicon/block/mushroom_stem.png"))));
        items.add(new GoalListItem("obtain a bubble coral block", "", 1, List.of(silk_touch), "obtain_coral_block", List.of(Identifier.of("lockout-bingo:goalicon/block/bubble_coral_block.png"))));
//        items.add(new GoalListItem("obtain dragons breath", "", 1, List.of(), "obtain_dragons_breath", List.of(Identifier.of("minecraft:textures/item/dragons_breath.png"))));
        items.add(new GoalListItem("obtain TNT", "", 1, List.of(), "obtain_tnt", List.of(Identifier.of("lockout-bingo:goalicon/block/tnt.png"))));
        items.add(new GoalListItem("obtain mud brick wall", "", 1, List.of(), "obtain_mud_brick_wall", List.of(Identifier.of("lockout-bingo:goalicon/block/mud_brick_wall.png"))));
        items.add(new GoalListItem("obtain a calibrated sculk sensor", "", 1, List.of(), "obtain_calibrated_sculk_sensor", List.of(Identifier.of("lockout-bingo:goalicon/block/calibrated_sculk_sensor.png"))));
        items.add(new GoalListItem("obtain blue ice", "", 1, List.of(silk_touch), "obtain_blue_ice", List.of(Identifier.of("lockout-bingo:goalicon/block/blue_ice.png"))));
        items.add(new GoalListItem("obtain a cobweb", "", 1, List.of(), "obtain_cobweb", List.of(Identifier.of("minecraft:textures/block/cobweb.png"))));
        items.add(new GoalListItem("obtain goat horn", "", 1, List.of(), "obtain_goat_horn", List.of(Identifier.of("minecraft:textures/item/goat_horn.png"))));
        items.add(new GoalListItem("obtain a nautilus shell", "", 1, List.of(), "obtain_nautilus_shell", List.of(Identifier.of("minecraft:textures/item/nautilus_shell.png"))));
        items.add(new GoalListItem("obtain a copper bulb", "", 1, List.of(), "obtain_copper_bulb", List.of(Identifier.of("lockout-bingo:goalicon/block/lit_copper_bulb.png"))));
        items.add(new GoalListItem("obtain a daylight detector", "", 1, List.of(), "obtain_daylight_detector", List.of(Identifier.of("lockout-bingo:goalicon/block/daylight_detector.png"))));
        items.add(new GoalListItem("obtain a redstone repeater", "", 1, List.of(), "obtain_repeater", List.of(Identifier.of("lockout-bingo:goalicon/block/repeater.png"))));
//        items.add(new GoalListItem("obtain a redstone comparator", "", 1, List.of(), "obtain_comparator", List.of(Identifier.of("lockout-bingo:goalicon/block/comparator.png"))));
        items.add(new GoalListItem("obtain a piston", "", 1, List.of(redstone), "obtain_piston", List.of(Identifier.of("lockout-bingo:goalicon/block/piston.png"))));
        items.add(new GoalListItem("obtain a sticky piston", "", 1, List.of(redstone), "obtain_sticky_piston", List.of(Identifier.of("lockout-bingo:goalicon/block/sticky_piston.png"))));
        items.add(new GoalListItem("obtain a dispenser", "", 1, List.of(redstone), "obtain_dispenser", List.of(Identifier.of("lockout-bingo:goalicon/block/dispenser.png"))));
        items.add(new GoalListItem("obtain an activator rail", "", 1, List.of(redstone), "obtain_activator_rail", List.of(Identifier.of("minecraft:textures/block/activator_rail.png"))));
        items.add(new GoalListItem("obtain a detector rail", "", 1, List.of(redstone), "obtain_detector_rail", List.of(Identifier.of("minecraft:textures/block/detector_rail.png"))));
        items.add(new GoalListItem("obtain a powered rail", "", 1, List.of(redstone), "obtain_powered_rail", List.of(Identifier.of("minecraft:textures/block/powered_rail.png"))));

        items.add(new GoalListItem("obtain a full set of wooden tools", "", 1, List.of(tools), "obtain_wooden_toolset", List.of(Identifier.of("minecraft:textures/item/wooden_hoe.png"), Identifier.of("minecraft:textures/item/wooden_hoe.png"), Identifier.of("minecraft:textures/item/wooden_pickaxe.png"), Identifier.of("minecraft:textures/item/wooden_shovel.png"), Identifier.of("minecraft:textures/item/wooden_sword.png"))));
        items.add(new GoalListItem("obtain a full set of stone tools", "", 1, List.of(tools), "obtain_stone_toolset", List.of(Identifier.of("minecraft:textures/item/stone_hoe.png"), Identifier.of("minecraft:textures/item/stone_hoe.png"), Identifier.of("minecraft:textures/item/stone_pickaxe.png"), Identifier.of("minecraft:textures/item/stone_shovel.png"), Identifier.of("minecraft:textures/item/stone_sword.png"))));
        items.add(new GoalListItem("obtain a full set of iron tools", "", 1, List.of(tools), "obtain_iron_toolset", List.of(Identifier.of("minecraft:textures/item/iron_hoe.png"), Identifier.of("minecraft:textures/item/iron_hoe.png"), Identifier.of("minecraft:textures/item/iron_pickaxe.png"), Identifier.of("minecraft:textures/item/iron_shovel.png"), Identifier.of("minecraft:textures/item/iron_sword.png"))));
        items.add(new GoalListItem("obtain a full set of golden tools", "", 1, List.of(tools), "obtain_golden_toolset", List.of(Identifier.of("minecraft:textures/item/golden_hoe.png"), Identifier.of("minecraft:textures/item/golden_hoe.png"), Identifier.of("minecraft:textures/item/golden_pickaxe.png"), Identifier.of("minecraft:textures/item/golden_shovel.png"), Identifier.of("minecraft:textures/item/golden_sword.png"))));
        items.add(new GoalListItem("obtain a full set of diamond tools", "", 1, List.of(tools), "obtain_diamond_toolset", List.of(Identifier.of("minecraft:textures/item/diamond_hoe.png"), Identifier.of("minecraft:textures/item/diamond_hoe.png"), Identifier.of("minecraft:textures/item/diamond_pickaxe.png"), Identifier.of("minecraft:textures/item/diamond_shovel.png"), Identifier.of("minecraft:textures/item/diamond_sword.png"))));
//        items.add(new GoalListItem("obtain a full set of netherite tools", "", 1, List.of(tools), "obtain_netherite_toolset", List.of(Identifier.of("minecraft:textures/item/netherite_hoe.png"), Identifier.of("minecraft:textures/item/netherite_hoe.png"), Identifier.of("minecraft:textures/item/netherite_pickaxe.png"), Identifier.of("minecraft:textures/item/netherite_shovel.png"), Identifier.of("minecraft:textures/item/netherite_sword.png"))));

        items.add(new GoalListItem("obtain 4 types of seeds", "", 1, List.of(C4), "obtain_all_seeds", List.of(Identifier.of("minecraft:textures/item/wheat_seeds.png"), Identifier.of("minecraft:textures/item/beetroot_seeds.png"), Identifier.of("minecraft:textures/item/melon_seeds.png"), Identifier.of("minecraft:textures/item/pumpkin_seeds.png"), Identifier.of("minecraft:textures/item/torchflower_seeds.png"))));
        items.add(new GoalListItem("obtain 6 types of flowers", "", 1, List.of(C6), "obtain_6_flowers", List.of(Identifier.of("minecraft:textures/block/poppy.png"), Identifier.of("minecraft:textures/block/dandelion.png"), Identifier.of("minecraft:textures/block/blue_orchid.png"), Identifier.of("minecraft:textures/block/allium.png"), Identifier.of("minecraft:textures/block/azure_bluet.png"), Identifier.of("minecraft:textures/block/red_tulip.png"), Identifier.of("minecraft:textures/block/orange_tulip.png"), Identifier.of("minecraft:textures/block/white_tulip.png"), Identifier.of("minecraft:textures/block/pink_tulip.png"), Identifier.of("minecraft:textures/block/oxeye_daisy.png"), Identifier.of("minecraft:textures/block/cornflower.png"), Identifier.of("minecraft:textures/block/lily_of_the_valley.png"), Identifier.of("minecraft:textures/block/wither_rose.png"))));
        items.add(new GoalListItem("obtain black glazed terracotta", "", 1, List.of(), "obtain_black_glazed_terracotta", List.of(Identifier.of("lockout-bingo:goalicon/block/black_glazed_terracotta.png"))));
        items.add(new GoalListItem("obtain all saplings", "", 1, List.of(), "obtain_all_saplings", List.of(Identifier.of("minecraft:textures/block/oak_sapling.png"), Identifier.of("minecraft:textures/block/spruce_sapling.png"), Identifier.of("minecraft:textures/block/birch_sapling.png"), Identifier.of("minecraft:textures/block/jungle_sapling.png"), Identifier.of("minecraft:textures/block/acacia_sapling.png"), Identifier.of("minecraft:textures/block/dark_oak_sapling.png"))));
        items.add(new GoalListItem("obtain 2 music disks", "", 1, List.of(C2), "obtain_2_music_disks", List.of(Identifier.of("minecraft:textures/item/music_disc_11.png"), Identifier.of("minecraft:textures/item/music_disc_13.png"), Identifier.of("minecraft:textures/item/music_disc_blocks.png"), Identifier.of("minecraft:textures/item/music_disc_cat.png"), Identifier.of("minecraft:textures/item/music_disc_chirp.png"), Identifier.of("minecraft:textures/item/music_disc_far.png"), Identifier.of("minecraft:textures/item/music_disc_mall.png"), Identifier.of("minecraft:textures/item/music_disc_mellohi.png"), Identifier.of("minecraft:textures/item/music_disc_stal.png"), Identifier.of("minecraft:textures/item/music_disc_strad.png"), Identifier.of("minecraft:textures/item/music_disc_wait.png"), Identifier.of("minecraft:textures/item/music_disc_ward.png"))));
        items.add(new GoalListItem("obtain all types of horse armor", "", 1, List.of(), "obtain_all_harmor", List.of(Identifier.of("minecraft:textures/item/iron_horse_armor.png"), Identifier.of("minecraft:textures/item/golden_horse_armor.png"), Identifier.of("minecraft:textures/item/diamond_horse_armor.png"))));


        items.add(new GoalListItem("eat 5 unique foods", "", 1, List.of(C5, eat), "eat_5", List.of(Identifier.of("minecraft:textures/item/apple.png"))));
        items.add(new GoalListItem("eat 10 unique foods", "", 1, List.of(C10, eat), "eat_10", List.of(Identifier.of("minecraft:textures/item/apple.png"))));
        items.add(new GoalListItem("eat 15 unique foods", "", 1, List.of(C15, eat), "eat_15", List.of(Identifier.of("minecraft:textures/item/apple.png"))));
        items.add(new GoalListItem("eat 20 unique foods", "", 1, List.of(C20, eat), "eat_20", List.of(Identifier.of("minecraft:textures/item/apple.png"))));
        items.add(new GoalListItem("eat suspicious stew", "", 1, List.of(eat), "eat_suspicious_stew", List.of(Identifier.of("minecraft:textures/item/suspicious_stew.png"))));
        items.add(new GoalListItem("eat glow berries", "", 1, List.of(eat), "eat_glow_berries", List.of(Identifier.of("minecraft:textures/item/glow_berries.png"))));
        items.add(new GoalListItem("eat rabbit stew", "", 1, List.of(eat), "eat_rabbit_stew", List.of(Identifier.of("minecraft:textures/item/rabbit_stew.png"))));
        items.add(new GoalListItem("eat beetroot soup", "", 1, List.of(eat), "eat_beetroot_soup", List.of(Identifier.of("minecraft:textures/item/beetroot_soup.png"))));
        items.add(new GoalListItem("eat honey bottle", "", 1, List.of(eat), "eat_honey_bottle", List.of(Identifier.of("minecraft:textures/item/honey_bottle.png"))));
//        items.add(new GoalListItem("eat honeycomb", "", 1, List.of(), "eat_honeycomb", List.of(Identifier.of("minecraft:textures/item/honeycomb.png"))));
        items.add(new GoalListItem("eat golden apple", "", 1, List.of(eat), "eat_golden_apple", List.of(Identifier.of("minecraft:textures/item/golden_apple.png"))));
        items.add(new GoalListItem("eat a cookie", "", 1, List.of(eat), "eat_cookie", List.of(Identifier.of("minecraft:textures/item/cookie.png"))));
        items.add(new GoalListItem("eat pumpkin pie", "", 1, List.of(eat), "eat_pumpkin_pie", List.of(Identifier.of("minecraft:textures/item/pumpkin_pie.png"))));
        items.add(new GoalListItem("eat poisonous potato", "", 1, List.of(eat), "eat_poisonous_potato", List.of(Identifier.of("minecraft:textures/item/poisonous_potato.png"))));
        items.add(new GoalListItem("eat dried kelp", "", 1, List.of(eat), "eat_dried_kelp", List.of(Identifier.of("minecraft:textures/item/dried_kelp.png"))));
        items.add(new GoalListItem("eat chorus fruit", "", 1, List.of(eat), "eat_chorus_fruit", List.of(Identifier.of("minecraft:textures/item/chorus_fruit.png"))));

        items.add(new GoalListItem("breed hoglins", "", 1, List.of(breed), "breed_hoglins", List.of(Identifier.of("lockout-bingo:goalicon/entity/hoglin.png"))));
        items.add(new GoalListItem("breed striders", "", 1, List.of(breed), "breed_striders", List.of(Identifier.of("lockout-bingo:goalicon/entity/strider.png"))));
        items.add(new GoalListItem("breed axolotls", "", 1, List.of(breed), "breed_axolotls", List.of(Identifier.of("lockout-bingo:goalicon/entity/axolotl.png"))));
        items.add(new GoalListItem("breed bees", "", 1, List.of(breed), "breed_bees", List.of(Identifier.of("lockout-bingo:goalicon/entity/bee.png"))));
        items.add(new GoalListItem("breed goats", "", 1, List.of(breed), "breed_goats", List.of(Identifier.of("lockout-bingo:goalicon/entity/goat.png"))));
        items.add(new GoalListItem("breed foxes", "", 1, List.of(breed), "breed_foxes", List.of(Identifier.of("lockout-bingo:goalicon/entity/fox.png"))));
        items.add(new GoalListItem("breed cats", "", 1, List.of(breed), "breed_cats", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(new GoalListItem("breed wolves", "", 1, List.of(breed), "breed_wolves", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));
        items.add(new GoalListItem("breed parrots", "", 1, List.of(breed), "breed_parrots", List.of(Identifier.of("lockout-bingo:goalicon/entity/parrot.png"))));
        items.add(new GoalListItem("breed rabbits", "", 1, List.of(breed), "breed_rabbits", List.of(Identifier.of("lockout-bingo:goalicon/entity/rabbit.png"))));
        items.add(new GoalListItem("breed llamas", "", 1, List.of(breed), "breed_llamas", List.of(Identifier.of("lockout-bingo:goalicon/entity/lama.png"))));
        items.add(new GoalListItem("breed horses", "", 1, List.of(breed), "breed_horses", List.of(Identifier.of("lockout-bingo:goalicon/entity/horse.png"))));
        items.add(new GoalListItem("breed pigs", "", 1, List.of(breed), "breed_pigs", List.of(Identifier.of("lockout-bingo:goalicon/entity/pig.png"))));
        items.add(new GoalListItem("breed turtles", "", 1, List.of(breed), "breed_turtles", List.of(Identifier.of("lockout-bingo:goalicon/entity/turtle.png"))));
        items.add(new GoalListItem("breed a mule", "", 1, List.of(breed), "breed_mule", List.of(Identifier.of("lockout-bingo:goalicon/entity/mule.png"))));

        items.add(new GoalListItem("breed 5 unique animals", "", 1, List.of(breed, C5), "breed_5", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))));
        items.add(new GoalListItem("breed 10 unique animals", "", 1, List.of(breed, C10), "breed_10", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))));
        items.add(new GoalListItem("breed 15 unique animals", "", 1, List.of(breed, C15), "breed_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/cow.png"))));

        items.add(new GoalListItem("kill a piglin brute", "", 1, List.of(kill), "kill_piglin_brute", List.of(Identifier.of("lockout-bingo:goalicon/entity/piglin_brute.png"))));
        items.add(new GoalListItem("kill zoglin", "", 1, List.of(kill), "kill_zoglin", List.of(Identifier.of("lockout-bingo:goalicon/entity/zoglin.png"))));
        items.add(new GoalListItem("kill an endermite", "", 1, List.of(kill), "kill_endermite", List.of(Identifier.of("lockout-bingo:goalicon/entity/endermite.png"))));
        items.add(new GoalListItem("kill a silverfish", "", 1, List.of(kill), "kill_silverfish", List.of(Identifier.of("lockout-bingo:goalicon/entity/silverfish.png"))));
        items.add(new GoalListItem("kill a breeze", "", 1, List.of(kill), "kill_breeze", List.of(Identifier.of("lockout-bingo:goalicon/entity/breeze.png")))); // Assuming 'breeze' is a fictional entity for the example
        items.add(new GoalListItem("kill an elder guardian", "", 1, List.of(kill), "kill_elder_guardian", List.of(Identifier.of("lockout-bingo:goalicon/entity/elder_guardian.png"))));
        items.add(new GoalListItem("kill a ghast", "", 1, List.of(kill), "kill_ghast", List.of(Identifier.of("lockout-bingo:goalicon/entity/ghast.png"))));
        items.add(new GoalListItem("kill a zombie villager", "", 1, List.of(kill), "kill_zillager", List.of(Identifier.of("lockout-bingo:goalicon/entity/zillager.png"))));
        items.add(new GoalListItem("kill a witch", "", 1, List.of(kill), "kill_witch", List.of(Identifier.of("lockout-bingo:goalicon/entity/witch.png"))));
        items.add(new GoalListItem("kill 7 unique mobs", "", 1, List.of(kill, C7), "kill_7", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"))));
        items.add(new GoalListItem("kill 10 unique mobs", "", 1, List.of(kill, C10), "kill_10", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"))));
        items.add(new GoalListItem("kill 15 unique mobs", "", 1, List.of(kill, C15), "kill_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/zombie.png"))));
        items.add(new GoalListItem("kill jeb_", "", 1, List.of(kill), "kill_jeb_", List.of(Identifier.of("lockout-bingo:goalicon/entity/jeb.png"))));

        items.add(new GoalListItem("enter the nether", "", 1, List.of(advancement), "nether_adv", List.of(Identifier.of("minecraft:textures/block/nether_portal.png"))));
        items.add(new GoalListItem("enter the end", "", 1, List.of(advancement), "end_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/end_portal_frame.png"))));
        items.add(new GoalListItem("use an enchanting table", "", 1, List.of(advancement), "enchanter_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/enchanting_table.png"))));
        items.add(new GoalListItem("zombie doctor", "", 1, List.of(advancement), "zombie_doc_adv", List.of(Identifier.of("minecraft:textures/item/golden_apple.png"))));
        items.add(new GoalListItem("enter the stronghold", "", 1, List.of(advancement), "eye_spy_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/eye_of_ender.png"))));
        items.add(new GoalListItem("kill a ghast with its own fireball", "", 1, List.of(advancement), "return_to_sender_adv", List.of(Identifier.of("minecraft:textures/item/fire_charge.png"))));
        items.add(new GoalListItem("find a bastion remnant", "", 1, List.of(advancement), "those_were_the_days_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/polished_blackstone_bricks.png")))); // This is a fictional texture path for a structure
        items.add(new GoalListItem("find a nether fortress", "", 1, List.of(advancement), "terrible_fortress_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/nether_bricks.png"))));
        items.add(new GoalListItem("travel 1km in the nether", "", 1, List.of(advancement), "subspace_bubble_adv", List.of(Identifier.of("minecraft:textures/item/map.png"))));
        items.add(new GoalListItem("distract a piglin with gold", "", 1, List.of(advancement), "oh_shiny_adv", List.of(Identifier.of("minecraft:textures/item/gold_ingot.png"))));
        items.add(new GoalListItem("ride a strider with a warped fungus on a stick", "", 1, List.of(ride, advancement), "this_boat_has_legs_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/strider.png"))));
        items.add(new GoalListItem("use a lodestone", "", 1, List.of(advancement), "country_lode_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/lodestone.png"))));
        items.add(new GoalListItem("use a respawn anchor", "", 1, List.of(advancement), "use_respawn_anchor_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/respawn_anchor.png"))));
        items.add(new GoalListItem("get a wither skeleton skull", "", 1, List.of(advancement), "spooky_scary_skeletons_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/wither_skeleton_skull.png"))));
        items.add(new GoalListItem("travel to every nether biome", "", 1, List.of(advancement), "hot_tourist_adv", List.of(Identifier.of("minecraft:textures/item/netherite_boots.png"))));
        items.add(new GoalListItem("great view from above", "", 1, List.of(advancement), "great_view_adv", List.of(Identifier.of("minecraft:textures/item/ender_pearl.png"))));
        items.add(new GoalListItem("what a deal", "", 1, List.of(advancement), "trade_adv", List.of(Identifier.of("minecraft:textures/item/emerald.png"))));
        items.add(new GoalListItem("slide in a honey block to break your fall", "", 1, List.of(advancement), "sticky_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/honey_block.png"))));
        items.add(new GoalListItem("kill a mob near a sculk catalyst", "", 1, List.of(advancement), "spreads_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/sculk_sensor.png"))));
        items.add(new GoalListItem("make a golem", "", 1, List.of(advancement), "hired_help_adv", List.of(Identifier.of("minecraft:textures/block/carved_pumpkin.png"))));
        items.add(new GoalListItem("arbalistic", "", 1, List.of(advancement), "arbalistic_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/crossbow.png"))));
        items.add(new GoalListItem("walk on powdered snow with leather boots", "", 1, List.of(advancement), "walk_snow_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/leather_boots.png"))));
        items.add(new GoalListItem("shoot a bullseye with a bow and arrow", "", 1, List.of(advancement), "bullseye_adv", List.of(Identifier.of("minecraft:textures/item/bow.png"))));
        items.add(new GoalListItem("total beelocation", "", 1, List.of(advancement, silk_touch), "beelocation_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/bee_nest.png"))));
        items.add(new GoalListItem("get a tadpole in a bucket", "", 1, List.of(breed, advancement), "tadpole_bucket_adv", List.of(Identifier.of("minecraft:textures/item/tadpole_bucket.png"))));
        items.add(new GoalListItem("obtain a sniffer egg", "", 1, List.of(advancement), "sniffer_egg_adv", List.of(Identifier.of("minecraft:textures/item/sniffer_egg.png"))));
        items.add(new GoalListItem("tame 4 unique types of cats", "", 1, List.of(C4, tame), "catalogue_4_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(new GoalListItem("time 3 unique types of wolves", "", 1, List.of(C3, tame), "whole_pack_3_adv", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));
        items.add(new GoalListItem("tactical fishing", "", 1, List.of(advancement), "tactical_fishing_adv", List.of(Identifier.of("minecraft:textures/item/tropical_fish_bucket.png"))));
        items.add(new GoalListItem("scrape the wax off a copper block", "", 1, List.of(advancement), "wax_off_adv", List.of(Identifier.of("minecraft:textures/item/stone_axe.png"))));
        items.add(new GoalListItem("axolotl in a bucket", "", 1, List.of(advancement), "axolotl_bucket_adv", List.of(Identifier.of("minecraft:textures/item/axolotl_bucket.png"))));
        items.add(new GoalListItem("Crafters crafting crafters", "", 1, List.of(advancement), "crafters_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/crafter.png"))));
        items.add(new GoalListItem("use a trial key on a vault", "", 1, List.of(advancement), "trial_key_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/trial_key.png"))));
        items.add(new GoalListItem("use wolf armor on a wolf", "", 1, List.of(advancement), "wolf_armor_adv", List.of(Identifier.of("lockout-bingo:goalicon/item/wolf_armor.png"))));
        items.add(new GoalListItem("Sound of music: play a music disk in a meadow", "", 1, List.of(advancement), "sound_of_music_adv", List.of(Identifier.of("lockout-bingo:goalicon/block/jukebox.png"))));
        items.add(new GoalListItem("catch a fish", "", 1, List.of(advancement), "fishy_business_adv", List.of(Identifier.of("minecraft:textures/item/fishing_rod.png"))));

        // biome goals
        items.add(new GoalListItem("find 15 unique biomes", "", 1, List.of(C15, biomes), "biomes_15", List.of(Identifier.of("minecraft:textures/item/diamond_boots.png"))));
        items.add(new GoalListItem("find 20 unique biomes", "", 1, List.of(C20, biomes), "biomes_20", List.of(Identifier.of("minecraft:textures/item/diamond_boots.png"))));
        items.add(new GoalListItem("find 25 unique biomes", "", 1, List.of(C25, biomes), "biomes_25", List.of(Identifier.of("minecraft:textures/item/diamond_boots.png"))));
        items.add(new GoalListItem("find 30 unique biomes", "", 1, List.of(C30, biomes), "biomes_30", List.of(Identifier.of("minecraft:textures/item/diamond_boots.png"))));

        items.add(new GoalListItem("die by golem", "", 1, List.of(die), "die_golem", List.of(Identifier.of("lockout-bingo:goalicon/entity/iron_golem.png"))));
        items.add(new GoalListItem("die by llama", "", 1, List.of(die), "die_llama", List.of(Identifier.of("lockout-bingo:goalicon/entity/lama.png"))));
        items.add(new GoalListItem("die by bee sting", "", 1, List.of(die), "die_bee", List.of(Identifier.of("lockout-bingo:goalicon/entity/bee.png"))));

        items.add(new GoalListItem("die by trident", "", 1, List.of(die), "die_trident", List.of(Identifier.of("lockout-bingo:goalicon/item/trident.png"))));
        items.add(new GoalListItem("die by sweet berry bush", "", 1, List.of(die), "die_sweet_berry_bush", List.of(Identifier.of("lockout-bingo:goalicon/block/sweet_berry_bush.png"))));
        items.add(new GoalListItem("die by cactus", "", 1, List.of(die), "die_cactus", List.of(Identifier.of("lockout-bingo:goalicon/block/cactus.png"))));
        items.add(new GoalListItem("die by anvil", "", 1, List.of(die), "die_anvil", List.of(Identifier.of("lockout-bingo:goalicon/block/anvil.png"))));
        items.add(new GoalListItem("die by falling stalactite", "", 1, List.of(die), "die_stalactite", List.of(Identifier.of("lockout-bingo:goalicon/block/stalactite.png"))));
        items.add(new GoalListItem("die by falling on a stalagmite", "", 1, List.of(die), "die_stalagmite", List.of(Identifier.of("lockout-bingo:goalicon/block/stalagmite.png"))));
        items.add(new GoalListItem("die by fireworks", "", 1, List.of(die), "die_fireworks", List.of(Identifier.of("lockout-bingo:goalicon/item/firework.png"))));
        items.add(new GoalListItem("freeze to death", "", 1, List.of(die), "die_freeze", List.of(Identifier.of("lockout-bingo:goalicon/block/powdered_snow.png"))));

        // ride goals
        items.add(new GoalListItem("ride a pig", "", 1, List.of(ride), "ride_pig", List.of(Identifier.of("lockout-bingo:goalicon/entity/pig.png"))));
        items.add(new GoalListItem("ride a horse", "", 1, List.of(ride), "ride_horse", List.of(Identifier.of("lockout-bingo:goalicon/entity/horse.png"))));

        // damage goals
        items.add(new GoalListItem("deal 200 damage", "", 1, List.of(C200), "damage_200", List.of(Identifier.of("minecraft:textures/item/diamond_sword.png"))));
        items.add(new GoalListItem("deal 500 damage", "", 1, List.of(C500), "damage_500", List.of(Identifier.of("minecraft:textures/item/diamond_sword.png"))));

        // use goals
        items.add(new GoalListItem("use a smithing table", "", 1, List.of(use), "use_smithing_table", List.of(Identifier.of("lockout-bingo:goalicon/block/smithing_table.png"))));
        items.add(new GoalListItem("use a grindstone", "", 1, List.of(use), "use_grindstone", List.of(Identifier.of("lockout-bingo:goalicon/block/grindstone.png"))));
        items.add(new GoalListItem("use a blast furnace", "", 1, List.of(use), "use_blast_furnace", List.of(Identifier.of("lockout-bingo:goalicon/block/blast_furnace.png"))));
        items.add(new GoalListItem("use a cartography table", "", 1, List.of(use), "use_cartography_table", List.of(Identifier.of("lockout-bingo:goalicon/block/cartography_table.png"))));
        items.add(new GoalListItem("use a loom", "", 1, List.of(use), "use_loom", List.of(Identifier.of("lockout-bingo:goalicon/block/loom.png"))));
        items.add(new GoalListItem("use a stonecutter", "", 1, List.of(use), "use_stonecutter", List.of(Identifier.of("lockout-bingo:goalicon/block/stonecutter.png"))));
        items.add(new GoalListItem("use an anvil", "", 1, List.of(use), "use_anvil", List.of(Identifier.of("lockout-bingo:goalicon/block/anvil.png"))));

        items.add(new GoalListItem("mine emerald ore", "", 1, List.of(), "mine_emerald", List.of(Identifier.of("lockout-bingo:goalicon/block/emerald_ore.png"))));
        items.add(new GoalListItem("mine diamond ore", "", 1, List.of(), "mine_diamond", List.of(Identifier.of("lockout-bingo:goalicon/block/diamond_ore.png"))));

        items.add(new GoalListItem("tame a cat", "", 1, List.of(tame), "tame_cat", List.of(Identifier.of("lockout-bingo:goalicon/entity/cat.png"))));
        items.add(new GoalListItem("tame a wolf", "", 1, List.of(tame), "tame_wolf", List.of(Identifier.of("lockout-bingo:goalicon/entity/wolf.png"))));

        // effect goals
        items.add(new GoalListItem("get poisoned", "", 1, List.of(effect), "effect_poison", List.of(Identifier.of("lockout-bingo:goalicon/effect/poison.png"))));
        items.add(new GoalListItem("get weakness", "", 1, List.of(effect), "effect_weakness", List.of(Identifier.of("lockout-bingo:goalicon/effect/weakness.png"))));
        items.add(new GoalListItem("get jump boost", "", 1, List.of(effect), "effect_jump_boost", List.of(Identifier.of("lockout-bingo:goalicon/effect/jump_boost.png"))));
        items.add(new GoalListItem("get fire resistance", "", 1, List.of(effect), "effect_fire_resistance", List.of(Identifier.of("lockout-bingo:goalicon/effect/fire_resistance.png"))));
        items.add(new GoalListItem("get infested", "", 1, List.of(effect), "effect_infested", List.of(Identifier.of("lockout-bingo:goalicon/effect/infested.png"))));
        items.add(new GoalListItem("get glowing", "", 1, List.of(effect), "effect_glowing", List.of(Identifier.of("lockout-bingo:goalicon/effect/glowing.png"))));
        items.add(new GoalListItem("have 3 active effects at once", "", 1, List.of(effect, C3), "effect_3", List.of(Identifier.of("minecraft:textures/item/milk_bucket.png"))));
        items.add(new GoalListItem("have 6 active effects at once", "", 1, List.of(effect, C6), "effect_6", List.of(Identifier.of("minecraft:textures/item/milk_bucket.png"))));

        items.add(new GoalListItem("break 5 pickaxes", "", 1, List.of(C5, broken), "break_5_pickaxes", List.of(Identifier.of("minecraft:textures/item/wooden_pickaxe.png"))));
        items.add(new GoalListItem("get them in a bucket !!", "", 5, List.of(), "get_them_in_a_bucket", List.of(Identifier.of("minecraft:textures/item/pufferfish_bucket.png"))));

        // level goals
        items.add(new GoalListItem("reach level 15", "", 1, List.of(lvl, C15), "lvl_15", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))));
        items.add(new GoalListItem("reach level 30", "", 1, List.of(lvl, C30), "lvl_30", List.of(Identifier.of("lockout-bingo:goalicon/entity/experience_orb.png"))));


        // position goals
        items.add(new GoalListItem("reach bedrock", "", 1, List.of(), "reach_bedrock", List.of(Identifier.of("lockout-bingo:goalicon/block/bedrock.png"))));
        items.add(new GoalListItem("reach the sky limit", "", 1, List.of(), "reach_sky_limit", List.of(Identifier.of("lockout-bingo:goalicon/item/sun.png"))));
        items.add(new GoalListItem("fall for 300 blocks", "", 1, List.of(C300), "fall_300", List.of(Identifier.of("lockout-bingo:goalicon/item/feather.png"))));

        //wool goals
        items.add(new GoalListItem("obtain a stack of lime wool", "", 1, List.of(wool, C64), "64_lime_wool", List.of(Identifier.of("lockout-bingo:goalicon/block/wool/lime_wool.png"))));
        items.add(new GoalListItem("obtain a stack of gray wool", "", 1, List.of(wool, C64), "64_gray_wool", List.of(Identifier.of("lockout-bingo:goalicon/block/wool/gray_wool.png"))));
        items.add(new GoalListItem("obtain a stack of cyan wool", "", 1, List.of(wool, C64), "64_cyan_wool", List.of(Identifier.of("lockout-bingo:goalicon/block/wool/cyan_wool.png"))));
        items.add(new GoalListItem("obtain a stack of brown wool", "", 1, List.of(wool, C64), "64_brown_wool", List.of(Identifier.of("lockout-bingo:goalicon/block/wool/brown_wool.png"))));

        items.add(new GoalListItem("duplicate a smithing template", "", 1, List.of(C2), "duplicate_smithing_template", List.of(Identifier.of("lockout-bingo:goalicon/item/smithing_templates/silence_template.png")))); // todo nog bij voegen

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
