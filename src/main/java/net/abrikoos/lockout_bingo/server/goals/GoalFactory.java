package net.abrikoos.lockout_bingo.server.goals;

import net.abrikoos.lockout_bingo.server.goals.advancement.*;
import net.abrikoos.lockout_bingo.server.goals.armor.WearArmorSetGoal;
import net.abrikoos.lockout_bingo.server.goals.biome.BiomeGoal;
import net.abrikoos.lockout_bingo.server.goals.breed.BreedAnimalGoal;
import net.abrikoos.lockout_bingo.server.goals.breed.BreedMultiAnimalsGoal;
import net.abrikoos.lockout_bingo.server.goals.brew.BrewPotionGoal;
import net.abrikoos.lockout_bingo.server.goals.craft.DuplicateTemplateGoal;
import net.abrikoos.lockout_bingo.server.goals.damage.DealDamageGoal;
import net.abrikoos.lockout_bingo.server.goals.damage.DontFallDamageGoal;
import net.abrikoos.lockout_bingo.server.goals.damage.SnowBallHitGoal;
import net.abrikoos.lockout_bingo.server.goals.die.DieFromEntityGoal;
import net.abrikoos.lockout_bingo.server.goals.die.DieFromWeaponGoal;
import net.abrikoos.lockout_bingo.server.goals.eat.EatFoodGoal;
import net.abrikoos.lockout_bingo.server.goals.eat.EatMultiFoodGoal;
import net.abrikoos.lockout_bingo.server.goals.eat.EmptyHungerGoal;
import net.abrikoos.lockout_bingo.server.goals.effect.DontGetAnyEffects;
import net.abrikoos.lockout_bingo.server.goals.effect.DontGetEffectGoal;
import net.abrikoos.lockout_bingo.server.goals.effect.GetEffectGoal;
import net.abrikoos.lockout_bingo.server.goals.effect.GetMultiEffectGoal;
import net.abrikoos.lockout_bingo.server.goals.kill.KillEntityGoal;
import net.abrikoos.lockout_bingo.server.goals.kill.KillHostileEntityGoal;
import net.abrikoos.lockout_bingo.server.goals.kill.KillJeb;
import net.abrikoos.lockout_bingo.server.goals.kill.MultiKillHostilesGoal;
import net.abrikoos.lockout_bingo.server.goals.lvl.ReachLvlGoal;
import net.abrikoos.lockout_bingo.server.goals.mine.MineDiamondOre;
import net.abrikoos.lockout_bingo.server.goals.mine.MineEmeraldOre;
import net.abrikoos.lockout_bingo.server.goals.mine.MineGoal;
import net.abrikoos.lockout_bingo.server.goals.movement.DontCrouch;
import net.abrikoos.lockout_bingo.server.goals.movement.DontJumpGoal;
import net.abrikoos.lockout_bingo.server.goals.movement.SprintGoal;
import net.abrikoos.lockout_bingo.server.goals.movement.SwimGoal;
import net.abrikoos.lockout_bingo.server.goals.obtain.*;
import net.abrikoos.lockout_bingo.server.goals.position.FallGoal;
import net.abrikoos.lockout_bingo.server.goals.position.ReachBedrockGoal;
import net.abrikoos.lockout_bingo.server.goals.position.ReachSkyLimitGoal;
import net.abrikoos.lockout_bingo.server.goals.ride.RideHorseGoal;
import net.abrikoos.lockout_bingo.server.goals.ride.RideMinecartGoal;
import net.abrikoos.lockout_bingo.server.goals.ride.RidePigGoal;
import net.abrikoos.lockout_bingo.server.goals.tame.TameAnimalGoal;
import net.abrikoos.lockout_bingo.server.goals.tools.BreakXPickaxes;
import net.abrikoos.lockout_bingo.server.goals.use.UseBlockGoal;

import net.abrikoos.lockout_bingo.server.goals.use.UseEntityGoal;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.List;


public class GoalFactory {

    public static LockoutGoal buildGoal(String goalid, int id) {
        // the rule is that if it can be checked with an advancement, it will be checked with an advancement
        return switch (goalid) {
            // dont obtain
            case "no_crafting_table" -> new DontObtainItemGoal(id, Items.CRAFTING_TABLE);
            case "no_obsidian" -> new DontObtainItemGoal(id, Items.OBSIDIAN);
            case "no_netherrack" -> new DontObtainItemGoal(id, Items.NETHERRACK);
            case "no_seeds" -> new DontObtainItemGoal(id, Items.WHEAT_SEEDS);
            case "no_water" -> new AdvancementGoal(id, null, "enemy", "in_water");
            case "no_fall" -> new DontFallDamageGoal(id);
            case "snowball_hit" -> new SnowBallHitGoal(id);
            // obtain goals
            case "obtain_end_crystal" -> new ObtainItemGoal(id, Items.END_CRYSTAL);
            case "obtain_bell" -> new ObtainItemGoal(id, Items.BELL);
            case "obtain_bottle_o_enchanting" -> new ObtainItemGoal(id, Items.EXPERIENCE_BOTTLE);
            case "obtain_slime_block" -> new ObtainItemGoal(id, Items.SLIME_BLOCK);
            case "obtain_cake" -> new ObtainItemGoal(id, Items.CAKE);
            case "obtain_soul_lantern" -> new ObtainItemGoal(id, Items.SOUL_LANTERN);
            case "obtain_heart_of_the_sea" -> new ObtainItemGoal(id, Items.HEART_OF_THE_SEA);
            case "obtain_wither_skeleton_skull" -> new ObtainItemGoal(id, Items.WITHER_SKELETON_SKULL);
            case "obtain_sponge" -> new ObtainItemGoal(id, Items.SPONGE);
            case "obtain_end_rod" -> new ObtainItemGoal(id, Items.END_ROD);
            case "obtain_sea_lantern" -> new ObtainItemGoal(id, Items.SEA_LANTERN);
            case "obtain_mossy_stone_brick_wall" -> new ObtainItemGoal(id, Items.MOSSY_STONE_BRICK_WALL);
            case "obtain_crying_obsidian" -> new ObtainItemGoal(id, Items.CRYING_OBSIDIAN);
            case "obtain_mushroom_stem" -> new ObtainItemGoal(id, Items.MUSHROOM_STEM);
            case "obtain_coral_block" -> new ObtainItemGoal(id, Items.BUBBLE_CORAL_BLOCK);
            case "obtain_dragon_breath" -> new ObtainItemGoal(id, Items.DRAGON_BREATH);
            case "obtain_tnt" -> new ObtainItemGoal(id, Items.TNT);
            case "obtain_mud_brick_wall" -> new ObtainItemGoal(id, Items.MUD_BRICK_WALL);
            case "obtain_blue_ice" -> new ObtainItemGoal(id, Items.BLUE_ICE);
            case "obtain_cobweb" -> new ObtainItemGoal(id, Items.COBWEB);
            case "obtain_goat_horn" -> new ObtainItemGoal(id, Items.GOAT_HORN);
            case "obtain_heart_sea" -> new ObtainItemGoal(id, Items.HEART_OF_THE_SEA);
            case "obtain_calibrated_sculk_sensor" -> new ObtainItemGoal(id, Items.CALIBRATED_SCULK_SENSOR);
            case "obtain_nautilus_shell" -> new ObtainItemGoal(id, Items.NAUTILUS_SHELL);
            case "obtain_exposed_copper_stairs" -> new ObtainItemGoal(id, Items.EXPOSED_CUT_COPPER_STAIRS);
            case "obtain_copper_bulb" -> new ObtainItemGoal(id, Items.COPPER_BULB);
            case "obtain_daylight_detector" -> new ObtainItemGoal(id, Items.DAYLIGHT_DETECTOR);
            case "obtain_repeater" -> new ObtainItemGoal(id, Items.REPEATER);
            case "obtain_comparator" -> new ObtainItemGoal(id, Items.COMPARATOR);
            case "obtain_piston" -> new ObtainItemGoal(id, Items.PISTON);
            case "obtain_sticky_piston" -> new ObtainItemGoal(id, Items.STICKY_PISTON);
            case "obtain_dispenser" -> new ObtainItemGoal(id, Items.DISPENSER);
            case "obtain_activator_rail" -> new ObtainItemGoal(id, Items.ACTIVATOR_RAIL);
            case "obtain_detector_rail" -> new ObtainItemGoal(id, Items.DETECTOR_RAIL);
            case "obtain_powered_rail" -> new ObtainItemGoal(id, Items.POWERED_RAIL);
            case "obtain_clock" -> new ObtainItemGoal(id, Items.CLOCK);
            case "obtain_observer" -> new ObtainItemGoal(id, Items.OBSERVER);
            case "obtain_bookshelf" -> new ObtainItemGoal(id, Items.BOOKSHELF);
            case "obtain_shulker_box" -> new ObtainItemGoal(id, Items.SHULKER_BOX);
            case "obtain_flowering_azalea" -> new ObtainItemGoal(id, Items.FLOWERING_AZALEA);
            case "obtain_scaffolding" -> new ObtainItemGoal(id, Items.SCAFFOLDING);
            case "obtain_snow_bucket" -> new ObtainItemGoal(id, Items.POWDER_SNOW_BUCKET);
            case "obtain_ancient_debris" -> new ObtainItemGoal(id, Items.ANCIENT_DEBRIS);
            case "obtain_ender_chest" -> new ObtainItemGoal(id, Items.ENDER_CHEST);
            case "obtain_dragon_egg" -> new ObtainItemGoal(id, Items.DRAGON_EGG);
            case "obtain_all_raw_ore_blocks" -> new ObtainXofSetItemsGoal(id, List.of(Items.RAW_IRON_BLOCK, Items.RAW_COPPER_BLOCK, Items.RAW_GOLD_BLOCK), 3);

            // tool goals
            case "obtain_wooden_toolset" -> new ObtainToolSetGoal(id, "wooden");
            case "obtain_stone_toolset" -> new ObtainToolSetGoal(id, "stone");
            case "obtain_iron_toolset" -> new ObtainToolSetGoal(id, "iron");
            case "obtain_golden_toolset" -> new ObtainToolSetGoal(id, "golden");
            case "obtain_diamond_toolset" -> new ObtainToolSetGoal(id, "diamond");
            case "obtain_netherite_toolset" -> new ObtainToolSetGoal(id, "netherite"); // too hard
            case "obtain_all_axes" -> new ObtainXofSetItemsGoal(id, List.of(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE), 6);
            case "obtain_all_hoes" -> new ObtainXofSetItemsGoal(id, List.of(Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE), 6);
            case "obtain_all_pickaxes" -> new ObtainXofSetItemsGoal(id, List.of(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE), 6);
            case "obtain_all_shovels" -> new ObtainXofSetItemsGoal(id, List.of(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL), 6);
            case "obtain_all_swords" -> new ObtainXofSetItemsGoal(id, List.of(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD), 6);



            // armor goals
            case "wear_chainmail" -> new WearArmorSetGoal(id, List.of(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS), 1);
            case "wear_full_diamond" -> new WearArmorSetGoal(id, List.of(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS), 4);
            case "wear_full_gold" -> new WearArmorSetGoal(id, List.of(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS), 4);
            case "wear_full_iron" -> new WearArmorSetGoal(id, List.of(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS), 4);
            case "wear_full_leather" -> new WearArmorSetGoal(id, List.of(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS), 4);
            case "wear_netherite" -> new WearArmorSetGoal(id, List.of(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS), 1);

            case "obtain_all_seeds" -> new ObtainEverySeedGoal(id);
            case "obtain_6_flowers" -> new ObtainXFlowers(id, 6);
            case "obtain_black_glazed_terracotta" -> new ObtainItemGoal(id, Items.BLACK_GLAZED_TERRACOTTA);
            case "obtain_all_saplings" -> new ObtainXofSetItemsGoal(id, List.of(Items.OAK_SAPLING, Items.SPRUCE_SAPLING, Items.BIRCH_SAPLING, Items.JUNGLE_SAPLING, Items.ACACIA_SAPLING, Items.DARK_OAK_SAPLING, Items.CHERRY_SAPLING), 7); //todo make class
            case "obtain_2_music_disks" -> new ObtainXofSetItemsGoal(id, List.of(Items.MUSIC_DISC_11, Items.MUSIC_DISC_13, Items.MUSIC_DISC_OTHERSIDE, Items.MUSIC_DISC_PRECIPICE, Items.MUSIC_DISC_5, Items.MUSIC_DISC_CAT, Items.MUSIC_DISC_BLOCKS, Items.MUSIC_DISC_CHIRP, Items.MUSIC_DISC_FAR, Items.MUSIC_DISC_MALL, Items.MUSIC_DISC_MELLOHI, Items.MUSIC_DISC_PIGSTEP, Items.MUSIC_DISC_RELIC, Items.MUSIC_DISC_STAL, Items.MUSIC_DISC_STRAD, Items.MUSIC_DISC_WAIT, Items.MUSIC_DISC_BLOCKS, Items.MUSIC_DISC_WARD), 2);
            case "obtain_all_harmor" -> new ObtainXofSetItemsGoal(id, List.of(Items.DIAMOND_HORSE_ARMOR, Items.GOLDEN_HORSE_ARMOR, Items.IRON_HORSE_ARMOR, Items.LEATHER_HORSE_ARMOR), 4);

            // eat goals
            case "eat_5" -> new EatMultiFoodGoal(id, 5);
            case "eat_10" -> new EatMultiFoodGoal(id, 10);
            case "eat_15" -> new EatMultiFoodGoal(id, 15);
            case "eat_20" -> new EatMultiFoodGoal(id, 20);
            case "eat_suspicious_stew" -> new EatFoodGoal(id, Items.SUSPICIOUS_STEW);
            case "eat_glow_berries" -> new EatFoodGoal(id, Items.GLOW_BERRIES);
            case "eat_rabbit_stew" -> new EatFoodGoal(id, Items.RABBIT_STEW);
            case "eat_chorus_fruit" -> new EatFoodGoal(id, Items.CHORUS_FRUIT);
            case "eat_golden_apple" -> new EatFoodGoal(id, Items.GOLDEN_APPLE);
            case "eat_pumpkin_pie" -> new EatFoodGoal(id, Items.PUMPKIN_PIE);
            case "eat_honey_bottle" -> new EatFoodGoal(id, Items.HONEY_BOTTLE);
            case "eat_honeycomb" -> new EatFoodGoal(id, Items.HONEYCOMB);
            case "eat_cookie" -> new EatFoodGoal(id, Items.COOKIE);
            case "eat_poisonous_potato" -> new EatFoodGoal(id, Items.POISONOUS_POTATO);
            case "eat_dried_kelp" -> new EatFoodGoal(id, Items.DRIED_KELP);
            case "eat_beetroot_soup" -> new EatFoodGoal(id, Items.BEETROOT_SOUP);


            // breed goals
            case "breed_hoglins" -> new BreedAnimalGoal(id, "minecraft:hoglin");
            case "breed_striders" -> new BreedAnimalGoal(id, "minecraft:strider");
            case "breed_axolotls" -> new BreedAnimalGoal(id, "minecraft:axolotl");
            case "breed_foxes" -> new BreedAnimalGoal(id, "minecraft:fox");
            case "breed_cats" -> new BreedAnimalGoal(id, "minecraft:cat");
            case "breed_parrots" -> new BreedAnimalGoal(id, "minecraft:parrot");
            case "breed_rabbits" -> new BreedAnimalGoal(id, "minecraft:rabbit");
            case "breed_wolves" -> new BreedAnimalGoal(id, "minecraft:wolf");
            case "breed_horses" -> new BreedAnimalGoal(id, "minecraft:horse");
            case "breed_mule" -> new BreedAnimalGoal(id, "minecraft:mule");
            case "breed_llamas" -> new BreedAnimalGoal(id, "minecraft:llama");
            case "breed_goats" -> new BreedAnimalGoal(id, "minecraft:goat");
            case "breed_bees" -> new BreedAnimalGoal(id, "minecraft:bee");
            case "breed_pigs" -> new BreedAnimalGoal(id, "minecraft:pig");
            case "breed_turtles" -> new BreedAnimalGoal(id, "minecraft:turtle");
            case "breed_chickens" -> new BreedAnimalGoal(id, "minecraft:chicken");
            case "breed_cows" -> new BreedAnimalGoal(id, "minecraft:cow");
            case "breed_5" -> new BreedMultiAnimalsGoal(id, 5);
            case "breed_10" -> new BreedMultiAnimalsGoal(id, 10);
            case "breed_15" -> new BreedMultiAnimalsGoal(id, 15);

            // kill goals
            case "kill_piglin_brute" -> new KillHostileEntityGoal(id, "minercaft:piglin_brute");
            case "kill_zoglin" -> new KillHostileEntityGoal(id, "minecraft:zoglin");
            case "kill_endermite" -> new KillHostileEntityGoal(id, "minecraft:endermite");
            case "kill_silverfish" -> new KillHostileEntityGoal(id, "minecraft:silverfish");
            case "kill_breeze" -> new KillHostileEntityGoal(id, "minecraft:breeze");
            case "kill_elder_guardian" -> new KillHostileEntityGoal(id, "minecraft:elder_guardian");
            case "kill_ghast" -> new KillHostileEntityGoal(id, "minecraft:ghast");
            case "kill_zillager" -> new KillHostileEntityGoal(id, "minecraft:zombie_villager");
            case "kill_witch" -> new KillHostileEntityGoal(id, "minecraft:witch");
            case "kill_stray" -> new KillHostileEntityGoal(id, "minecraft:stray");
            case "kill_snow_golem" -> new KillEntityGoal(id, EntityType.SNOW_GOLEM);
            case "kill_7" -> new MultiKillHostilesGoal(id, 7);
            case "kill_10" -> new MultiKillHostilesGoal(id, 10);
            case "kill_15" -> new MultiKillHostilesGoal(id, 15);
            case "kill_jeb_" -> new KillJeb(id);

            // use entity goals
            case "shear_bogged" -> new UseEntityGoal(id, EntityType.BOGGED, Items.SHEARS);

            // advancement goals
            case "nether_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/root"));
            case "end_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "end/root"));
            case "enchanter_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "story/enchant_item"));
            case "zombie_doc_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "story/cure_zombie_villager"));
            case "eye_spy_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "story/follow_ender_eye"));
            case "return_to_sender_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/return_to_sender"));
            case "those_were_the_days_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/find_bastion"));
            case "subspace_bubble_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/fast_travel"));
            case "terrible_fortress_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/find_fortress"));
            case "oh_shiny_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/distract_piglin"));
            case "this_boat_has_legs_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/ride_strider"));
            case "country_lode_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/use_lodestone"));
            case "spooky_scary_skeletons_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/get_wither_skull"));
            case "use_respawn_anchor_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/charge_respawn_anchor"));
            case "hot_tourist_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "nether/explore_nether"));
            case "the_end_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "story/enter_the_end"));
            case "dragon_breath_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "end/dragon_breath"));
            case "great_view_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "end/levitate"));
            // spyglawss goals
            case "trade_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/trade"));
            case "sticky_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/honey_block_slide"));
            case "spreads_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/kill_mob_near_sculk_catalyst"));
            case "hired_help_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/summon_iron_golem"));
            case "arbalistic_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/arbalistic"));
            case "walk_snow_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/walk_on_powder_snow_with_leather_boots"));
            case "bullseye_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/bullseye"));
            case "beelocation_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "husbandry/silk_touch_nest"));
            case "tadpole_bucket_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "husbandry/tadpole_in_a_bucket"));
            case "sniffer_egg_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "husbandry/obtain_sniffer_egg"));
            case "catalogue_4_adv" -> new MultiCriterionAdvancementGoal(id, Identifier.of("minecraft", "husbandry/complete_catalogue"), 4);
//            case "tame_cat" -> new MultiAdvancementGoal(id, Identifier.of("minecraft", "husbandry/complete_catalogue"), 1);
            case "whole_pack_3_adv" -> new MultiCriterionAdvancementGoal(id, Identifier.of("minecraft", "husbandry/whole_pack"), 3);
//            case "tame_wolf" -> new MultiAdvancementGoal(id, Identifier.of("minecraft", "husbandry/whole_pack"), 1);
            case "tactical_fishing_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "husbandry/tactical_fishing"));
            case "wax_off_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "husbandry/wax_off"));
            case "axolotl_bucket_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "husbandry/axolotl_in_a_bucket"));
            case "crafters_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/crafters_crafting_crafters"));
            case "trial_key_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/under_lock_and_key"));
            case "wolf_armor_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/repair_wolf_armor"));
            case "sound_of_music_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/play_jukebox_in_meadows"));
            case "fishy_business_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "husbandry/fishy_business"));
            case "dragon_adv" -> new GetAdvancementGoal(id, Identifier.of("minecraft", "end/kill_dragon"));
            case "spyglass_adv" -> new MultiPossibilityAdvancementGoal(id, List.of(
                    new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/spyglass_at_parrot")),
                    new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/spyglass_at_ghast")),
                    new GetAdvancementGoal(id, Identifier.of("minecraft", "adventure/spyglass_at_dragon"))));

            case "adv_15" -> new AdvancementCountGoal(id, 15);
            case "adv_35" -> new AdvancementCountGoal(id, 35);
            case "adv_25" -> new AdvancementCountGoal(id, 25);

            // tame goals
            case "tame_cat" -> new TameAnimalGoal(id, EntityType.CAT);
            case "tame_wolf" -> new TameAnimalGoal(id, EntityType.WOLF);
            case "tame_parrot" -> new TameAnimalGoal(id, EntityType.PARROT);
            case "tame_horse" -> new TameAnimalGoal(id, EntityType.HORSE);
            case "tame_llama" -> new TameAnimalGoal(id, EntityType.LLAMA);


            // biome goals
            case "biome_ice_spikes" -> new BiomeGoal(id, "minecraft:ice_spikes");
            case "biome_badlands" -> new BiomeGoal(id, "minecraft:badlands");
            case "biomes_15" -> new MultiCriterionAdvancementGoal(id, Identifier.of("minecraft", "adventure/adventuring_time"), 15);
            case "biomes_20" -> new MultiCriterionAdvancementGoal(id, Identifier.of("minecraft", "adventure/adventuring_time"), 20);
            case "biomes_25" -> new MultiCriterionAdvancementGoal(id, Identifier.of("minecraft", "adventure/adventuring_time"), 25);
            case "biomes_30" -> new MultiCriterionAdvancementGoal(id, Identifier.of("minecraft", "adventure/adventuring_time"), 30);

            // die goals
            case "die_golem" -> new DieFromEntityGoal(id, EntityType.IRON_GOLEM);
            case "die_bee" -> new DieFromEntityGoal(id, EntityType.BEE);
            case "die_llama" -> new DieFromEntityGoal(id, EntityType.LLAMA);

            case "die_trident" -> new DieFromWeaponGoal(id, DamageTypes.TRIDENT);
            case "die_sweet_berry_bush" -> new DieFromWeaponGoal(id, DamageTypes.SWEET_BERRY_BUSH);
            case "die_cactus" -> new DieFromWeaponGoal(id, DamageTypes.CACTUS);
            case "die_anvil" -> new DieFromWeaponGoal(id, DamageTypes.FALLING_ANVIL);
            case "die_stalactite" -> new DieFromWeaponGoal(id, DamageTypes.FALLING_STALACTITE);
            case "die_stalagmite" -> new DieFromWeaponGoal(id, DamageTypes.STALAGMITE);
            case "die_fireworks" -> new DieFromWeaponGoal(id, DamageTypes.FIREWORKS);
            case "die_freeze" -> new DieFromWeaponGoal(id, DamageTypes.FREEZE);

            case "ride_pig" -> new RidePigGoal(id);
            case "ride_horse" -> new RideHorseGoal(id);
            case "ride_minecart" -> new RideMinecartGoal(id);

            //damage goals
            case "damage_200" -> new DealDamageGoal(id, 200);
            case "damage_500" -> new DealDamageGoal(id, 500);

            // use block goals
            case "use_smithing_table" -> new UseBlockGoal(id, ScreenHandlerType.SMITHING, 3);
            case "use_loom" -> new UseBlockGoal(id, ScreenHandlerType.LOOM, 3);
            case "use_cartography_table" -> new UseBlockGoal(id, ScreenHandlerType.CARTOGRAPHY_TABLE, 2);
            case "use_stonecutter" -> new UseBlockGoal(id, ScreenHandlerType.STONECUTTER, 1);
            case "use_blast_furnace" -> new UseBlockGoal(id, ScreenHandlerType.BLAST_FURNACE, 2);
            case "use_grindstone" -> new UseBlockGoal(id, ScreenHandlerType.GRINDSTONE, 2);
            case "use_anvil" -> new UseBlockGoal(id, ScreenHandlerType.ANVIL, 2);

            // mine goals
            case "mine_diamond" -> new MineDiamondOre(id);
            case "mine_emerald" -> new MineEmeraldOre(id);
            case "mine_turtle_egg" -> new MineGoal(id, Blocks.TURTLE_EGG, 1);
            case "mine_spawner" -> new MineGoal(id, Blocks.SPAWNER, 1);

            case "effect_poison" -> new GetEffectGoal(id, StatusEffects.POISON);
            case "effect_weakness" -> new GetEffectGoal(id, StatusEffects.WEAKNESS);
            case "effect_jump_boost" -> new GetEffectGoal(id, StatusEffects.JUMP_BOOST);
            case "effect_fire_resistance" -> new GetEffectGoal(id, StatusEffects.FIRE_RESISTANCE);
            case "effect_infested" -> new GetEffectGoal(id, StatusEffects.INFESTED);
            case "effect_glowing" -> new GetEffectGoal(id, StatusEffects.GLOWING);
            case "effect_nausea" -> new GetEffectGoal(id, StatusEffects.NAUSEA);
            case "effect_absorption" -> new GetEffectGoal(id, StatusEffects.ABSORPTION);
            case "effect_fatigue" -> new GetEffectGoal(id, StatusEffects.MINING_FATIGUE);
            case "effect_bad_omen" -> new GetEffectGoal(id, StatusEffects.BAD_OMEN);
            case "effect_3" -> new GetMultiEffectGoal(id, 3);
            case "effect_6" -> new GetMultiEffectGoal(id, 6);
            case "dont_effect" -> new DontGetAnyEffects(id);
            case "dont_glowing" -> new DontGetEffectGoal(id, StatusEffects.GLOWING);


            case "empty_hunger" -> new EmptyHungerGoal(id);

            case "get_them_in_a_bucket" -> new ObtainXofSetItemsGoal(id, List.of(Items.COD_BUCKET, Items.SALMON_BUCKET, Items.PUFFERFISH_BUCKET, Items.TROPICAL_FISH_BUCKET, Items.AXOLOTL_BUCKET, Items.TADPOLE_BUCKET), 6);

            case "break_5_pickaxes" -> new BreakXPickaxes(id, 5);

            case "lvl_15" -> new ReachLvlGoal(id, 15);
            case "lvl_30" -> new ReachLvlGoal(id, 30);

            case "reach_bedrock" -> new ReachBedrockGoal(id);
            case "reach_sky_limit" -> new ReachSkyLimitGoal(id);
            case "fall_300" -> new FallGoal(id, 300);

            case "64_lime_wool" -> new ObtainMultiCountItemGoal(id, Items.LIME_WOOL, 64);
            case "64_gray_wool" -> new ObtainMultiCountItemGoal(id, Items.GRAY_WOOL, 64);
            case "64_brown_wool" -> new ObtainMultiCountItemGoal(id, Items.BROWN_WOOL, 64);
            case "64_cyan_wool" -> new ObtainMultiCountItemGoal(id, Items.CYAN_WOOL, 64);

            case "duplicate_smithing_template" -> new DuplicateTemplateGoal(id);

            // movement goals
            case "sprint_1km" -> new SprintGoal(id, 100000);
            case "swim_1km" -> new SwimGoal(id, 100000);
            case "dont_jump" -> new DontJumpGoal(id);
            case "dont_crouch" -> new DontCrouch(id);

            // brew goals
            case "brew_regen" -> new BrewPotionGoal(id, Potions.REGENERATION);
            case "brew_weaving" -> new BrewPotionGoal(id, Potions.WEAVING);
            case "brew_oozing" -> new BrewPotionGoal(id, Potions.OOZING);
            case "brew_invis" -> new BrewPotionGoal(id, Potions.INVISIBILITY);
            case "brew_leaping" -> new BrewPotionGoal(id, Potions.LEAPING);
            case "brew_healing" -> new BrewPotionGoal(id, Potions.HEALING);
            case "brew_fire_res" -> new BrewPotionGoal(id, Potions.FIRE_RESISTANCE);
            case "brew_swiftness" -> new BrewPotionGoal(id, Potions.SWIFTNESS);
            case "brew_breathing" -> new BrewPotionGoal(id, Potions.WATER_BREATHING);
            case "brew_harming" -> new BrewPotionGoal(id, Potions.HARMING);

            default ->

            {
                throw new IllegalStateException("Unexpected value: " + goalid);
            }
        };

    }
}



























