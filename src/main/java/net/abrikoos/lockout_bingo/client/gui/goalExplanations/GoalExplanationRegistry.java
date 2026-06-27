package net.abrikoos.lockout_bingo.client.gui.goalExplanations;

import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class GoalExplanationRegistry {

    public static GoalExplanation getGoalExplanation(String goalCraftingId) {
        switch (goalCraftingId) {

            // === DONT GOALS ===
            case "no_crafting_table":
                return new GoalExplanation("No crafting table", "You are not allowed to have a crafting table in your inventory. You can still use a crafting table you find or use the 2x2 crafting grid in your inventory.\nCrafting tables can be found in villages, pillager towers, witch huts and igloos.", List.of());
            case "no_obsidian":
                return new GoalExplanation("No obsidian", "The opponent must not obtain obsidian. Obsidian forms where water flows onto lava source blocks. It can also be found in ruined portal chests and nether fortresses.", List.of());
            case "no_netherrack":
                return new GoalExplanation("No netherrack", "The opponent must not obtain netherrack. Netherrack makes up the bulk of the nether floor and walls.", List.of());
            case "no_seeds":
                return new GoalExplanation("No seeds", "The opponent must not obtain seeds. Wheat seeds drop from breaking grass. They can also be found in village farm plots and chests.", List.of());
            case "snowball_hit":
                return new GoalExplanation("Snowball hit", "Hit the opponent with a snowball. Snowballs are obtained by breaking snow blocks or layers with any tool (or by hand). Each snow block gives 4 snowballs.", List.of());
            case "no_fall":
                return new GoalExplanation("No fall damage", "The opponent must take fall damage. Fall from 4 or more blocks onto solid ground. Water, hay bales, slime blocks and honey blocks negate fall damage.", List.of());
            case "no_die":
                return new GoalExplanation("Opponent dies", "The opponent must die once during the game.", List.of());
            case "no_die_3":
                return new GoalExplanation("Opponent dies 3 times", "The opponent must die 3 times during the game.", List.of());
            case "no_fire":
                return new GoalExplanation("Opponent catches fire", "The opponent must catch fire. Lava, fire blocks, flint-and-steel, flame arrows, and blazes can all set a player on fire.", List.of());
            case "no_water":
                return new GoalExplanation("Don't touch water", "Do not touch any water block for the entire game. This includes rivers, oceans, and any placed water. Swim in lava instead.", List.of());
            case "dont_crouch":
                return new GoalExplanation("Opponent sneaks", "The opponent must press the sneak key (default: Shift) at any point during the game.", List.of());
            case "dont_jump":
                return new GoalExplanation("Opponent jumps", "The opponent must press the jump key (default: Space) at any point during the game.", List.of());
            case "dont_effect":
                return new GoalExplanation("No effects", "The opponent must not receive any status effect for the entire game. This includes positive effects like regeneration and negative ones like poison.", List.of());
            case "dont_glowing":
                return new GoalExplanation("No glowing", "The opponent must not receive the glowing effect. Glowing is applied by spectral arrows. Craft spectral arrows from 4 glowstone dust + 1 arrow.", List.of());

            // === OBTAIN GOALS ===
            case "obtain_end_crystal":
                return new GoalExplanation("Obtain end crystal", "The only way to obtain an end crystal is by crafting it. You need ghast tears (dropped by ghasts in the nether) and an eye of ender.", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        Items.GLASS, Items.GLASS, Items.GLASS,
                        Items.GLASS, Items.ENDER_EYE, Items.GLASS,
                        Items.GLASS, Items.GHAST_TEAR, Items.GLASS
                ), Items.END_CRYSTAL)));
            case "obtain_bell":
                return new GoalExplanation("Obtain a bell", "Bells can always be found in villages hanging near the center. They can also appear in ruined portal chests with a 1.5% chance. Bells can be mined with any tool.", List.of());
            case "obtain_bottle_o_enchanting":
                return new GoalExplanation("Obtain bottle o' enchanting", "Bottles o' enchanting cannot be crafted. They can be found in shipwreck treasure chests, pillager outpost chests, and ancient city chests. They can also be bought from librarian villagers.", List.of());
            case "obtain_slime_block":
                return new GoalExplanation("Obtain a slime block", "Slime blocks are crafted from 9 slimeballs. Slimeballs drop from slimes, which spawn in slime chunks below Y=40 and in swamp biomes at night. Baby pandas also drop slimeballs when they sneeze.", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        Items.SLIME_BALL, Items.SLIME_BALL, Items.SLIME_BALL,
                        Items.SLIME_BALL, Items.SLIME_BALL, Items.SLIME_BALL,
                        Items.SLIME_BALL, Items.SLIME_BALL, Items.SLIME_BALL
                ), Items.SLIME_BLOCK)));
            case "obtain_cake":
                return new GoalExplanation("Obtain a cake", "Cake is crafted using 3 milk buckets, sugar, an egg, and 3 wheat. The milk buckets are returned empty after crafting. Cake can also be found in village chests.", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        Items.MILK_BUCKET, Items.MILK_BUCKET, Items.MILK_BUCKET,
                        Items.SUGAR, Items.EGG, Items.SUGAR,
                        Items.WHEAT, Items.WHEAT, Items.WHEAT
                ), Items.CAKE)));
            case "obtain_soul_lantern":
                return new GoalExplanation("Obtain a soul lantern", "Soul lanterns are crafted from 8 iron nuggets and a soul torch. Soul torches are crafted from a stick, coal or charcoal, and soul sand or soul soil (found in the nether).", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.IRON_NUGGET, Items.IRON_NUGGET, Items.IRON_NUGGET,
                        Items.IRON_NUGGET, Items.SOUL_TORCH, Items.IRON_NUGGET,
                        Items.IRON_NUGGET, Items.IRON_NUGGET, Items.IRON_NUGGET
                ), Items.SOUL_LANTERN)));
            case "obtain_heart_of_the_sea":
                return new GoalExplanation("Obtain heart of the sea", "Hearts of the sea can only be found in buried treasure chests. Get a treasure map from a shipwreck map chest or ocean ruin chest, then follow the X on the map. Buried treasure always spawns beneath the beach surface.", List.of());
            case "obtain_sponge":
                return new GoalExplanation("Obtain a sponge", "Sponges are found inside ocean monuments. Each monument contains 1-2 wet sponge rooms. Elder guardians also have a chance to drop a wet sponge when killed. Dry the wet sponge in a furnace to get a dry sponge.", List.of());
            case "obtain_end_rod":
                return new GoalExplanation("Obtain an end rod", "End rods are crafted from a blaze rod and a popped chorus fruit. Popped chorus fruit is obtained by smelting chorus fruit in a furnace. Chorus fruit grows on the outer end islands.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        null, Items.POPPED_CHORUS_FRUIT, null,
                        null, Items.BLAZE_ROD, null,
                        null, null, null
                ), Items.END_ROD)));
            case "obtain_sea_lantern":
                return new GoalExplanation("Obtain a sea lantern", "Sea lanterns can only be collected with a silk touch tool. They are found inside ocean monuments. Without silk touch they drop prismarine crystals instead. Ocean monuments are large structures found in deep ocean biomes.", List.of());
            case "obtain_mossy_stone_brick_wall":
                return new GoalExplanation("Obtain mossy stone brick wall", "Mossy stone brick walls are crafted from mossy stone bricks arranged in a wall pattern. Mossy stone bricks can be crafted by combining stone bricks with vine or moss block. They also generate naturally in strongholds and jungle temples.", List.of());
            case "obtain_crying_obsidian":
                return new GoalExplanation("Obtain crying obsidian", "Crying obsidian cannot be crafted. It is found in ruined portal structures (common in both overworld and nether) and can be obtained by bartering with piglins using gold ingots (9.46% chance per barter).", List.of());
            case "obtain_mushroom_stem":
                return new GoalExplanation("Obtain mushroom stem", "Mushroom stems can only be collected with a silk touch tool. They form the trunk of huge mushrooms, which spawn in mushroom fields and dark forest biomes. Without silk touch the stem breaks without dropping.", List.of());
            case "obtain_coral_block":
                return new GoalExplanation("Obtain a bubble coral block", "Coral blocks can only be collected with a silk touch tool. Bubble coral blocks are found in warm ocean biomes (shallow coral reef structures). Without silk touch they drop nothing.", List.of());
            case "obtain_shulker_box":
                return new GoalExplanation("Obtain a shulker box", "Shulker boxes are crafted from 2 shulker shells and 1 chest. Shulker shells drop from shulkers, which are found inside end cities on the outer end islands. You must defeat the ender dragon first to access those islands.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        null, Items.SHULKER_SHELL, null,
                        null, Items.CHEST, null,
                        null, Items.SHULKER_SHELL, null
                ), Items.SHULKER_BOX)));
            case "obtain_bookshelf":
                return new GoalExplanation("Obtain a bookshelf", "Bookshelves are crafted from 6 planks and 3 books. Books require 3 paper and 1 leather to craft. Bookshelves also generate in village libraries and stronghold libraries.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.OAK_PLANKS, Items.OAK_PLANKS, Items.OAK_PLANKS,
                        Items.BOOK, Items.BOOK, Items.BOOK,
                        Items.OAK_PLANKS, Items.OAK_PLANKS, Items.OAK_PLANKS
                ), Items.BOOKSHELF)));
            case "obtain_flowering_azelia":
                return new GoalExplanation("Obtain flowering azalea", "Flowering azaleas generate on the surface above lush caves. Look for the rooted dirt patches and moss on the surface — the flowering azalea will be nearby. They can also be grown by bonemealing an azalea bush.", List.of());
            case "obtain_scaffolding":
                return new GoalExplanation("Obtain scaffolding", "Scaffolding is crafted from 6 bamboo and 1 string, producing 6 scaffolding. Bamboo grows in jungle biomes and can also be found in shipwrecks and jungle temple chests.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.BAMBOO, Items.STRING, Items.BAMBOO,
                        Items.BAMBOO, null, Items.BAMBOO,
                        Items.BAMBOO, null, Items.BAMBOO
                ), Items.SCAFFOLDING)));
            case "obtain_snow_bucket":
                return new GoalExplanation("Obtain a bucket of powdered snow", "Fill an empty bucket by right-clicking on a powdered snow block. Powdered snow forms naturally in cold biomes (snowy slopes, grove, frozen peaks) when it snows. It can also be collected with a cauldron during snowfall.", List.of());
            case "obtain_ancient_debris":
                return new GoalExplanation("Obtain ancient debris", "Ancient debris only generates deep in the nether between Y=8 and Y=22 (with most at Y=15). It cannot be destroyed by explosions. Mine it with at least a diamond pickaxe. It commonly generates in 1-3 block veins.", List.of());
            case "obtain_ender_chest":
                return new GoalExplanation("Obtain an ender chest", "Ender chests are crafted from 8 obsidian and 1 eye of ender. Eyes of ender are crafted from ender pearls (dropped by endermen) and blaze powder (crafted from blaze rods).", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.OBSIDIAN, Items.OBSIDIAN, Items.OBSIDIAN,
                        Items.OBSIDIAN, Items.ENDER_EYE, Items.OBSIDIAN,
                        Items.OBSIDIAN, Items.OBSIDIAN, Items.OBSIDIAN
                ), Items.ENDER_CHEST)));
            case "obtain_dragon_egg":
                return new GoalExplanation("Obtain the dragon egg", "After killing the ender dragon the egg appears on top of the exit portal. You cannot mine it directly — punching it teleports it. To collect it: place a torch below it, then break the block it sits on so it falls onto the torch, then pick it up.", List.of());
            case "obtain_tnt":
                return new GoalExplanation("Obtain TNT", "TNT is crafted from 5 gunpowder and 4 sand in an alternating pattern. Gunpowder drops from creepers, ghasts, and witches. TNT can also be found in desert temple traps and shipwreck chests.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.GUNPOWDER, Items.SAND, Items.GUNPOWDER,
                        Items.SAND, Items.GUNPOWDER, Items.SAND,
                        Items.GUNPOWDER, Items.SAND, Items.GUNPOWDER
                ), Items.TNT)));
            case "obtain_mud_brick_wall":
                return new GoalExplanation("Obtain mud brick wall", "Mud brick walls are crafted from 6 mud bricks. Mud bricks are made by smelting packed mud in a furnace. Packed mud is crafted from 1 mud block and 1 wheat. Mud forms when water is poured on dirt.", List.of());
            case "obtain_calibrated_sculk_sensor":
                return new GoalExplanation("Obtain calibrated sculk sensor", "Calibrated sculk sensors are crafted from 1 sculk sensor and 3 amethyst shards. Sculk sensors are found in deep dark biomes and ancient cities.", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        null, Items.AMETHYST_SHARD, null,
                        Items.AMETHYST_SHARD, Items.SCULK_SENSOR, Items.AMETHYST_SHARD,
                        null, null, null
                ), Items.CALIBRATED_SCULK_SENSOR)));
            case "obtain_blue_ice":
                return new GoalExplanation("Obtain blue ice", "Blue ice can be found naturally in icebergs and cold ocean biomes with a silk touch tool. It can also be crafted from 9 packed ice (each packed ice costs 9 regular ice blocks = 81 ice total). This is very expensive — silk touch is recommended.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        Items.PACKED_ICE, Items.PACKED_ICE, Items.PACKED_ICE,
                        Items.PACKED_ICE, Items.PACKED_ICE, Items.PACKED_ICE,
                        Items.PACKED_ICE, Items.PACKED_ICE, Items.PACKED_ICE
                ), Items.BLUE_ICE)));
            case "obtain_cobweb":
                return new GoalExplanation("Obtain a cobweb", "Cobwebs can only be collected with a silk touch tool or shears. They are found in abandoned mineshafts, dungeon corridors, and spider spawner rooms. Without silk touch or shears they drop string.", List.of());
            case "obtain_goat_horn":
                return new GoalExplanation("Obtain a goat horn", "Goat horns drop when a goat rams into a hard block (stone, coal ore, copper ore, iron ore, emerald ore, or any log). Not all goat rams drop a horn. They can also be found in pillager outpost chests.", List.of());
            case "obtain_nautilus_shell":
                return new GoalExplanation("Obtain a nautilus shell", "Nautilus shells are obtained by fishing (rare), trading with wandering traders, or killing drowned that are holding one (3% drop chance). They are used to craft conduits.", List.of());
            case "obtain_exposed_copper_stairs":
                return new GoalExplanation("Obtain exposed cut copper stairs", "Exposed cut copper stairs are crafted from 6 exposed cut copper blocks. Exposed cut copper is made by stonecutting exposed copper blocks (which have partially oxidized). Craft exposed copper in a stonecutter or let copper blocks oxidize naturally over time.", List.of());
            case "obtain_copper_bulb":
                return new GoalExplanation("Obtain a copper bulb", "Copper bulbs are found in trial chambers. They can be crafted using copper blocks and a blaze rod. They can also be obtained by breaking bulbs inside trial chambers with any pickaxe.", List.of());
            case "obtain_daylight_detector":
                return new GoalExplanation("Obtain a daylight detector", "Daylight detectors are crafted from 3 glass, 3 nether quartz, and 3 wood slabs. Nether quartz is mined from quartz ore in the nether.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.GLASS, Items.GLASS, Items.GLASS,
                        Items.QUARTZ, Items.QUARTZ, Items.QUARTZ,
                        Items.OAK_SLAB, Items.OAK_SLAB, Items.OAK_SLAB
                ), Items.DAYLIGHT_DETECTOR)));
            case "obtain_repeater":
                return new GoalExplanation("Obtain a redstone repeater", "Redstone repeaters are crafted from 3 stone, 2 redstone torches, and 1 redstone dust. They can also be found in jungle temple traps.", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        Items.REDSTONE_TORCH, Items.REDSTONE, Items.REDSTONE_TORCH,
                        Items.STONE, Items.STONE, Items.STONE,
                        null, null, null
                ), Items.REPEATER)));
            case "obtain_piston":
                return new GoalExplanation("Obtain a piston", "Pistons are crafted from 3 planks, 4 cobblestone, 1 iron ingot, and 1 redstone. They can also be found in some jungle temples.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.OAK_PLANKS, Items.OAK_PLANKS, Items.OAK_PLANKS,
                        Items.COBBLESTONE, Items.IRON_INGOT, Items.COBBLESTONE,
                        Items.COBBLESTONE, Items.REDSTONE, Items.COBBLESTONE
                ), Items.PISTON)));
            case "obtain_sticky_piston":
                return new GoalExplanation("Obtain a sticky piston", "Sticky pistons are crafted from 1 piston and 1 slimeball. Craft the piston first (3 planks + 4 cobblestone + 1 iron ingot + 1 redstone), then combine with a slimeball.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        null, Items.SLIME_BALL, null,
                        null, Items.PISTON, null,
                        null, null, null
                ), Items.STICKY_PISTON)));
            case "obtain_dispenser":
                return new GoalExplanation("Obtain a dispenser", "Dispensers are crafted from 7 cobblestone, 1 bow, and 1 redstone. The bow goes in the center and the redstone below it.", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        Items.COBBLESTONE, Items.COBBLESTONE, Items.COBBLESTONE,
                        Items.COBBLESTONE, Items.BOW, Items.COBBLESTONE,
                        Items.COBBLESTONE, Items.REDSTONE, Items.COBBLESTONE
                ), Items.DISPENSER)));
            case "obtain_activator_rail":
                return new GoalExplanation("Obtain an activator rail", "Activator rails are crafted from 6 iron ingots, 2 sticks, and 1 redstone torch, yielding 6 rails. They power TNT minecarts and eject passengers from minecarts.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.IRON_INGOT, Items.STICK, Items.IRON_INGOT,
                        Items.IRON_INGOT, Items.REDSTONE_TORCH, Items.IRON_INGOT,
                        Items.IRON_INGOT, Items.STICK, Items.IRON_INGOT
                ), Items.ACTIVATOR_RAIL)));
            case "obtain_detector_rail":
                return new GoalExplanation("Obtain a detector rail", "Detector rails are crafted from 6 iron ingots, 1 stone pressure plate, and 1 redstone, yielding 6 rails. They emit a redstone signal when a minecart passes over them.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.IRON_INGOT, null, Items.IRON_INGOT,
                        Items.IRON_INGOT, Items.STONE_PRESSURE_PLATE, Items.IRON_INGOT,
                        Items.IRON_INGOT, Items.REDSTONE, Items.IRON_INGOT
                ), Items.DETECTOR_RAIL)));
            case "obtain_powered_rail":
                return new GoalExplanation("Obtain a powered rail", "Powered rails are crafted from 6 gold ingots, 1 stick, and 1 redstone, yielding 6 rails. They accelerate or brake minecarts when powered.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.GOLD_INGOT, null, Items.GOLD_INGOT,
                        Items.GOLD_INGOT, Items.STICK, Items.GOLD_INGOT,
                        Items.GOLD_INGOT, Items.REDSTONE, Items.GOLD_INGOT
                ), Items.POWERED_RAIL)));
            case "obtain_clock":
                return new GoalExplanation("Obtain a clock", "Clocks are crafted from 4 gold ingots and 1 redstone. They display the time of day and work even when in your inventory.", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        null, Items.GOLD_INGOT, null,
                        Items.GOLD_INGOT, Items.REDSTONE, Items.GOLD_INGOT,
                        null, Items.GOLD_INGOT, null
                ), Items.CLOCK)));
            case "obtain_comparator":
                return new GoalExplanation("Obtain a redstone comparator", "Redstone comparators are crafted from 3 stone, 3 redstone torches, and 1 nether quartz. Nether quartz is mined in the nether.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        null, Items.REDSTONE_TORCH, null,
                        Items.REDSTONE_TORCH, Items.QUARTZ, Items.REDSTONE_TORCH,
                        Items.STONE, Items.STONE, Items.STONE
                ), Items.COMPARATOR)));
            case "obtain_observer":
                return new GoalExplanation("Obtain an observer", "Observers are crafted from 6 cobblestone, 2 redstone, and 1 nether quartz. They emit a redstone pulse when the block in front of them changes.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.COBBLESTONE, Items.COBBLESTONE, Items.COBBLESTONE,
                        Items.REDSTONE, Items.QUARTZ, Items.REDSTONE,
                        Items.COBBLESTONE, Items.COBBLESTONE, Items.COBBLESTONE
                ), Items.OBSERVER)));
            case "obtain_all_raw_ore_blocks":
                return new GoalExplanation("Obtain every type of raw ore block", "Collect a raw copper block, raw iron block, and raw gold block. These are crafted by placing 9 of the corresponding raw ore in a crafting grid. Raw ore drops from ore blocks without silk touch. Iron and gold are found at most depths; copper is most common between Y=48 and Y=96.", List.of());
            case "stairs_5":
                return new GoalExplanation("5 unique types of stairs", "Collect 5 different stair types. Stairs are crafted from 6 blocks of the same material in a stair pattern (yields 4 stairs). Common choices: oak stairs, stone brick stairs, cobblestone stairs, sandstone stairs, and nether brick stairs.", List.of());
            case "stairs_10":
                return new GoalExplanation("10 unique types of stairs", "Collect 10 different stair types. More exotic options include: deep slate tile stairs (crafted from deep slate tiles), cherry stairs (from cherry wood), bamboo mosaic stairs (from bamboo mosaics), and end stone brick stairs (from end stone bricks).", List.of());
            case "stairs_15":
                return new GoalExplanation("15 unique types of stairs", "Collect 15 different stair types. This requires exploring many biomes and the nether/end for exotic materials like smooth quartz, polished granite, dark prismarine, mossy cobblestone, and weathered cut copper stairs.", List.of());

            // === TOOL GOALS ===
            case "obtain_wooden_toolset":
                return new GoalExplanation("Full set of wooden tools", "Craft all 5 wooden tools: sword, pickaxe, axe, shovel, and hoe. Each uses 2 sticks and 2-3 wood planks. All 5 together need 10 sticks and 11 planks total.", List.of());
            case "obtain_stone_toolset":
                return new GoalExplanation("Full set of stone tools", "Craft all 5 stone tools: sword, pickaxe, axe, shovel, and hoe. Each uses 2 sticks and 2-3 cobblestone. Cobblestone is obtained by mining stone.", List.of());
            case "obtain_iron_toolset":
                return new GoalExplanation("Full set of iron tools", "Craft all 5 iron tools: sword, pickaxe, axe, shovel, and hoe. Each uses 2 sticks and 2-3 iron ingots. Smelt iron ore or raw iron in a furnace to get iron ingots.", List.of());
            case "obtain_golden_toolset":
                return new GoalExplanation("Full set of golden tools", "Craft all 5 golden tools: sword, pickaxe, axe, shovel, and hoe. Gold tools are fast but have very low durability. Smelt gold ore or raw gold to get gold ingots.", List.of());
            case "obtain_diamond_toolset":
                return new GoalExplanation("Full set of diamond tools", "Craft all 5 diamond tools: sword, pickaxe, axe, shovel, and hoe. Diamonds are found between Y=-58 and Y=-64 most commonly. You need 11 diamonds total for the full set.", List.of());
            case "obtain_all_axes":
                return new GoalExplanation("Every type of axe", "Collect one of each axe: wooden, stone, iron, golden, diamond, and netherite. Netherite axes require a diamond axe upgraded with a netherite upgrade smithing template and a netherite ingot at a smithing table.", List.of());
            case "obtain_all_hoes":
                return new GoalExplanation("Every type of hoe", "Collect one of each hoe: wooden, stone, iron, golden, diamond, and netherite. Hoes are used to till soil for farming and to mine some blocks faster.", List.of());
            case "obtain_all_pickaxes":
                return new GoalExplanation("Every type of pickaxe", "Collect one of each pickaxe: wooden, stone, iron, golden, diamond, and netherite. This requires progressing through the full material tier progression.", List.of());
            case "obtain_all_shovels":
                return new GoalExplanation("Every type of shovel", "Collect one of each shovel: wooden, stone, iron, golden, diamond, and netherite. Shovels dig dirt, gravel, sand, and snow quickly.", List.of());
            case "obtain_all_swords":
                return new GoalExplanation("Every type of sword", "Collect one of each sword: wooden, stone, iron, golden, diamond, and netherite. This requires full material progression including reaching the nether for netherite.", List.of());

            // === ARMOR GOALS ===
            case "wear_chainmail":
                return new GoalExplanation("Wear a piece of chainmail armor", "Chainmail armor cannot be crafted in survival. It is found in village chests, dungeon chests, and dropped by zombies/skeletons wearing it. It can also be bought from armorer villagers.", List.of());
            case "wear_full_leather":
                return new GoalExplanation("Wear a full set of leather armor", "Leather armor is crafted from leather, which is dropped by cows, horses, donkeys, mules, llamas, hoglins, and foxes. Craft each piece (helmet: 5, chestplate: 8, leggings: 7, boots: 4 leather).", List.of());
            case "wear_full_iron":
                return new GoalExplanation("Wear a full set of iron armor", "Iron armor requires 24 iron ingots total (helmet: 5, chestplate: 8, leggings: 7, boots: 4). Smelt iron ore or raw iron in a furnace. Iron can also be found in cave chests and village chests.", List.of());
            case "wear_full_golden":
                return new GoalExplanation("Wear a full set of golden armor", "Golden armor requires 24 gold ingots total. Gold armor has low durability but provides strong enchantability. Piglins won't attack you if you wear at least one piece.", List.of());
            case "wear_full_diamond":
                return new GoalExplanation("Wear a full set of diamond armor", "Diamond armor requires 24 diamonds total. Diamonds are most commonly found at Y=-58 to Y=-64. Diamond armor provides strong protection and has high durability.", List.of());
            case "wear_netherite":
                return new GoalExplanation("Wear a piece of netherite armor", "Upgrade any diamond armor piece to netherite at a smithing table using a netherite upgrade smithing template and 1 netherite ingot. Netherite ingots are crafted from 4 netherite scraps (smelted ancient debris) and 4 gold ingots.", List.of());
            case "wear_elytra":
                return new GoalExplanation("Wear an elytra", "Elytra are found in item frames inside end ships, which are attached to some end cities. You must defeat the ender dragon to access the outer end islands. Elytra goes in the chestplate slot.", List.of());
            case "wear_colored":
                return new GoalExplanation("Wear a piece of colored armor", "Dye any piece of leather armor using a dye in a crafting grid. Place the leather armor piece and any dye together. Leather is the only armor type that can be dyed.", List.of());
            case "wear_trimmed":
                return new GoalExplanation("Wear a trimmed armor piece", "Apply a smithing template to any armor piece at a smithing table using a smithing template, armor piece, and trim material. Smithing templates are found in structures like bastions, trails, and ancient cities.", List.of());
            case "wear_trimmed_set":
                return new GoalExplanation("Wear a fully trimmed armor set", "Apply a trim to all 4 armor pieces (helmet, chestplate, leggings, boots) at a smithing table. Each piece needs its own smithing template. The trim does not have to be the same type on each piece.", List.of());
            case "wear_pumpkin":
                return new GoalExplanation("Wear a carved pumpkin for 1 minute", "Place a carved pumpkin in your helmet slot and keep it there for 1 minute. Carved pumpkins are obtained by using shears on a pumpkin. They restrict your vision but prevent endermen from becoming hostile when you look at them.", List.of());

            // === SEED/PLANT/FOOD OBTAIN GOALS ===
            case "obtain_all_seeds":
                return new GoalExplanation("Obtain 4 types of seeds", "Collect wheat seeds (break grass), melon seeds (village chests/dungeon chests), pumpkin seeds (village chests/dungeon chests), and beetroot seeds (village chests/end city chests).", List.of());
            case "obtain_6_flowers":
                return new GoalExplanation("Obtain 6 types of flowers", "Collect 6 different flower types. Common flowers: dandelion, poppy (plains), blue orchid (swamp), allium (flower forest), azure bluet (plains), red/orange/white tulip (plains), oxeye daisy (plains), cornflower (plains), lily of the valley (flower forest). Wither rose drops from wither kills.", List.of());
            case "obtain_black_glazed_terracotta":
                return new GoalExplanation("Obtain black glazed terracotta", "Smelt black terracotta in a furnace to get black glazed terracotta. Black terracotta is found in badlands biomes or crafted by combining terracotta with black dye. Terracotta is obtained by smelting clay blocks.", List.of());
            case "obtain_all_saplings":
                return new GoalExplanation("Obtain all saplings", "Collect saplings from all 8 tree types. Saplings drop from leaves when broken: oak (plains), spruce (taiga), birch (birch forest), jungle (jungle), acacia (savanna), dark oak (dark forest), mangrove propagule (mangrove swamp), cherry (cherry grove).", List.of());
            case "obtain_2_music_disks":
                return new GoalExplanation("Obtain 2 music discs", "Music discs are found in dungeon and woodland mansion chests. Discs 13 and Cat also drop when a skeleton kills a creeper. Pigstep is only found in bastion remnant chests.", List.of());
            case "obtain_all_harmor":
                return new GoalExplanation("Obtain all types of horse armor", "Collect iron, golden, diamond, and leather horse armor. All types can be found in nether fortress chests, dungeon chests, and village chests. Leather horse armor can also be crafted.", List.of());

            // === EAT GOALS ===
            case "eat_5":
                return new GoalExplanation("Eat 5 unique foods", "Eat 5 different food items. Easy early options: bread (craft from 3 wheat), apple (drops from oak leaves), carrot, potato, cooked meat. Each unique food only counts once.", List.of());
            case "eat_10":
                return new GoalExplanation("Eat 10 unique foods", "Eat 10 different food items. Beyond basic foods, try: baked potato, melon slice, beetroot, mushroom stew, pumpkin pie, cookie, cooked chicken, cooked fish, and sweet berries.", List.of());
            case "eat_15":
                return new GoalExplanation("Eat 15 unique foods", "Eat 15 different food items. Less common options: rabbit, mutton, tropical fish, salmon, dried kelp, glow berries, honey bottle, suspicious stew, golden apple, and spider eye.", List.of());
            case "eat_20":
                return new GoalExplanation("Eat 20 unique foods", "Eat 20 different food items. Rare options needed: chorus fruit (end), beetroot soup, rabbit stew, pufferfish, poisonous potato, and cooked variants of more meats. Plan ahead!", List.of());
            case "eat_suspicious_stew":
                return new GoalExplanation("Eat suspicious stew", "Suspicious stew is crafted from a flower, a red mushroom, a brown mushroom, and a bowl. The flower used determines what effect you receive. It can also be found in shipwreck supply chests.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        Items.DANDELION, null, null,
                        Items.RED_MUSHROOM, Items.BROWN_MUSHROOM, null,
                        null, Items.BOWL, null
                ), Items.SUSPICIOUS_STEW)));
            case "eat_glow_berries":
                return new GoalExplanation("Eat glow berries", "Glow berries grow on cave vines in lush caves. Lush caves generate underground below azalea trees. Bonemealing dripleaf or moss blocks can cause lush cave vegetation to grow. You can also find them in mineshaft chests.", List.of());
            case "eat_rabbit_stew":
                return new GoalExplanation("Eat rabbit stew", "Rabbit stew is crafted from cooked rabbit, a baked potato, a carrot, a mushroom (red or brown), and a bowl. It can also be traded from level-1 butcher villagers.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        null, Items.RABBIT, null,
                        Items.CARROT, Items.BAKED_POTATO, Items.RED_MUSHROOM,
                        null, Items.BOWL, null
                ), Items.RABBIT_STEW)));
            case "eat_beetroot_soup":
                return new GoalExplanation("Eat beetroot soup", "Beetroot soup is crafted from 6 beetroot and 1 bowl. It can also be found in snowy village chests. Beetroot grows from beetroot seeds found in village farms and chests.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.BEETROOT, Items.BEETROOT, Items.BEETROOT,
                        Items.BEETROOT, Items.BEETROOT, Items.BEETROOT,
                        null, Items.BOWL, null
                ), Items.BEETROOT_SOUP)));
            case "obtain_honey_block":
                return new GoalExplanation("Obtain a honey block", "Honey blocks are crafted from 4 honey bottles arranged in a 2x2 shape. Fill honey bottles by right-clicking a full beehive or bee nest (honey level 5, indicated by dripping) with a glass bottle. Place a lit campfire below the hive to pacify bees.", List.of(new DrawCraftingGrid(10, 0, Arrays.asList(
                        Items.HONEY_BOTTLE, Items.HONEY_BOTTLE, null,
                        Items.HONEY_BOTTLE, Items.HONEY_BOTTLE, null,
                        null, null, null
                ), Items.HONEY_BLOCK)));
            case "eat_honey_bottle":
                return new GoalExplanation("Eat a honey bottle", "Right-click a bee nest or beehive at honey level 5 (dripping) with a glass bottle. Honey level fills as bees enter the hive with pollen. Don't anger the bees — use a campfire smoke below the hive to pacify them.", List.of());
            case "eat_golden_apple":
                return new GoalExplanation("Eat a golden apple", "Golden apples are crafted from 8 gold ingots and 1 apple. They can also be found in dungeon, mineshaft, and ruined portal chests. They grant absorption and regeneration effects.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT,
                        Items.GOLD_INGOT, Items.APPLE, Items.GOLD_INGOT,
                        Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT
                ), Items.GOLDEN_APPLE)));
            case "eat_cookie":
                return new GoalExplanation("Eat a cookie", "Cookies are crafted (shapeless) from 2 wheat and 1 cocoa beans, yielding 8 cookies. Cocoa beans grow on jungle logs in jungle biomes and can also be found in dungeon chests.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.WHEAT, Items.COCOA_BEANS, Items.WHEAT,
                        null, null, null,
                        null, null, null
                ), Items.COOKIE)));
            case "eat_pumpkin_pie":
                return new GoalExplanation("Eat pumpkin pie", "Pumpkin pie is crafted (shapeless) from 1 pumpkin, 1 sugar, and 1 egg. Pumpkins are found in grassy biomes. Sugar is crafted from sugar cane. Eggs are laid by chickens.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.PUMPKIN, Items.SUGAR, Items.EGG,
                        null, null, null,
                        null, null, null
                ), Items.PUMPKIN_PIE)));
            case "eat_poisonous_potato":
                return new GoalExplanation("Eat a poisonous potato", "Poisonous potatoes are a rare drop (2% chance) when harvesting potato crops. They restore 2 hunger but have a 60% chance to poison you. You cannot plant them.", List.of());
            case "eat_dried_kelp":
                return new GoalExplanation("Eat dried kelp", "Dried kelp is obtained by smelting kelp in a furnace or smoker. Kelp grows in most ocean biomes. Dried kelp restores only half a hunger shank but eats very quickly. 9 dried kelp can also be crafted into a dried kelp block.", List.of());
            case "eat_chorus_fruit":
                return new GoalExplanation("Eat chorus fruit", "Chorus fruit grows on chorus plants on the outer end islands. You must defeat the ender dragon to reach the outer islands. Eating chorus fruit teleports you to a random nearby location — useful to escape situations but unpredictable.", List.of());

            // === BREED GOALS ===
            case "breed_hoglins":
                return new GoalExplanation("Breed hoglins", "Breed two hoglins by feeding them crimson fungus. Hoglins spawn in crimson forests in the nether. Warning: they are hostile and will attack you. Keep them away from warped fungus or nether portals as they flee from those.", List.of());
            case "breed_striders":
                return new GoalExplanation("Breed striders", "Breed two striders by feeding them warped fungus. Striders spawn on the surface of lava in the nether. They are passive mobs.", List.of());
            case "breed_axolotls":
                return new GoalExplanation("Breed axolotls", "Breed two axolotls by feeding them buckets of tropical fish. Axolotls spawn in lush caves underground. You need two tamed (leashed or in a bucket) axolotls close together.", List.of());
            case "breed_bees":
                return new GoalExplanation("Breed bees", "Breed two bees by feeding each of them any type of flower. Bees spawn near bee nests in flower forests, plains, and sunflower plains. Alternatively, move a bee nest with silk touch.", List.of());
            case "breed_goats":
                return new GoalExplanation("Breed goats", "Breed two goats by feeding them wheat. Goats spawn on high mountain biomes (jagged peaks, frozen peaks, stony peaks). They sometimes ram you off cliffs, so be careful!", List.of());
            case "breed_foxes":
                return new GoalExplanation("Breed foxes", "Breed two foxes by feeding them sweet berries or glow berries. Foxes spawn in taiga, old growth taiga, and snowy taiga biomes. The baby fox will trust you and not flee.", List.of());
            case "breed_cats":
                return new GoalExplanation("Breed cats", "Breed two tamed cats by feeding them raw cod or raw salmon. Cats spawn as strays in villages. Tame one by offering raw fish until hearts appear. Then breed two tamed cats.", List.of());
            case "breed_wolves":
                return new GoalExplanation("Breed wolves", "Breed two tamed wolves by feeding them any type of meat (raw or cooked). Wolves spawn in taiga, old growth taiga, and other cold biomes. Tame with bones.", List.of());
            case "breed_rabbits":
                return new GoalExplanation("Breed rabbits", "Breed two rabbits by feeding them dandelions, carrots, or golden carrots. Rabbits spawn in deserts, snowy biomes, and flower forests.", List.of());
            case "breed_llamas":
                return new GoalExplanation("Breed llamas", "Breed two tamed llamas by feeding them hay bales. Llamas spawn in savanna and windswept hills biomes. Tame by riding them repeatedly until hearts appear.", List.of());
            case "breed_horses":
                return new GoalExplanation("Breed horses", "Breed two tamed horses by feeding them golden apples or golden carrots. Horses spawn in plains and savanna biomes. Tame by riding them repeatedly until hearts appear.", List.of());
            case "breed_pigs":
                return new GoalExplanation("Breed pigs", "Breed two pigs by feeding them carrots, potatoes, or beetroot. Pigs are common passive mobs found throughout the overworld.", List.of());
            case "breed_turtles":
                return new GoalExplanation("Breed turtles", "Breed two turtles by feeding them seagrass. Seagrass is found in ocean biomes. After breeding, the female turtle will return to its home beach to lay eggs.", List.of());
            case "breed_mule":
                return new GoalExplanation("Breed a mule", "A mule is created by breeding a horse with a donkey. You must tame both the horse and donkey first (ride them until hearts appear), then feed each a golden apple or golden carrot.", List.of());
            case "breed_chickens":
                return new GoalExplanation("Breed chickens", "Breed two chickens by feeding them any type of seed (wheat seeds, melon seeds, pumpkin seeds, beetroot seeds, or torchflower seeds). Chickens are common passive mobs found throughout the overworld.", List.of());
            case "breed_cows":
                return new GoalExplanation("Breed cows", "Breed two cows by feeding them wheat. Cows are common passive mobs found throughout the overworld in most grassy biomes.", List.of());
            case "breed_5":
                return new GoalExplanation("Breed 5 unique animals", "Breed 5 different species of animals. Each species only counts once regardless of how many times you breed them. See the full animal food list:\n\n" +
                        "- Horses/Donkeys: golden apple or golden carrot (must be tamed)\n" +
                        "- Cows/Mooshrooms: wheat\n" +
                        "- Pigs: carrot, potato, or beetroot\n" +
                        "- Chickens: any seed\n" +
                        "- Wolves: any meat (must be tamed)\n" +
                        "- Cats: raw cod or salmon (must be tamed)\n" +
                        "- Axolotls: bucket of tropical fish\n" +
                        "- Llamas: hay bale (must be tamed)\n" +
                        "- Rabbits: dandelion, carrot, or golden carrot\n" +
                        "- Turtles: seagrass\n" +
                        "- Foxes: sweet berries or glow berries\n" +
                        "- Bees: any flower\n" +
                        "- Striders: warped fungus\n" +
                        "- Hoglins: crimson fungus", List.of());
            case "breed_10":
                return new GoalExplanation("Breed 10 unique animals", "Breed 10 different species of animals. Each species only counts once. See the full animal food list:\n\n" +
                        "- Horses/Donkeys: golden apple or golden carrot (must be tamed)\n" +
                        "- Cows/Mooshrooms: wheat\n" +
                        "- Pigs: carrot, potato, or beetroot\n" +
                        "- Chickens: any seed\n" +
                        "- Wolves: any meat (must be tamed)\n" +
                        "- Cats: raw cod or salmon (must be tamed)\n" +
                        "- Axolotls: bucket of tropical fish\n" +
                        "- Llamas: hay bale (must be tamed)\n" +
                        "- Rabbits: dandelion, carrot, or golden carrot\n" +
                        "- Turtles: seagrass\n" +
                        "- Foxes: sweet berries or glow berries\n" +
                        "- Bees: any flower\n" +
                        "- Striders: warped fungus\n" +
                        "- Hoglins: crimson fungus", List.of());
            case "breed_15":
                return new GoalExplanation("Breed 15 unique animals", "Breed 15 different species of animals. Each species only counts once. See the full animal food list:\n\n" +
                        "- Horses/Donkeys: golden apple or golden carrot (must be tamed)\n" +
                        "- Cows/Mooshrooms: wheat\n" +
                        "- Pigs: carrot, potato, or beetroot\n" +
                        "- Chickens: any seed\n" +
                        "- Wolves: any meat (must be tamed)\n" +
                        "- Cats: raw cod or salmon (must be tamed)\n" +
                        "- Axolotls: bucket of tropical fish\n" +
                        "- Llamas: hay bale (must be tamed)\n" +
                        "- Rabbits: dandelion, carrot, or golden carrot\n" +
                        "- Turtles: seagrass\n" +
                        "- Foxes: sweet berries or glow berries\n" +
                        "- Bees: any flower\n" +
                        "- Striders: warped fungus\n" +
                        "- Hoglins: crimson fungus", List.of());

            // === KILL GOALS ===
            case "kill_piglin_brute":
                return new GoalExplanation("Kill a piglin brute", "Piglin brutes spawn in all types of bastion remnant. They do not trade, cannot be distracted by gold, and attack on sight. They deal high damage so come prepared. Nearby regular piglins may also become hostile when you attack the brute.", List.of());
            case "kill_zoglin":
                return new GoalExplanation("Kill a zoglin", "A zoglin is created when you bring a hoglin to the overworld or the end through a portal. Hoglins are scared of nether portals, so lure one away from the portal first. Zoglins are hostile and attack anything in sight.", List.of());
            case "kill_endermite":
                return new GoalExplanation("Kill an endermite", "Endermites have a 5% chance to spawn when a player-thrown ender pearl lands. Throw ender pearls until one spawns. They despawn after 2 minutes even inside minecarts or boats, so kill it quickly. They are small and deal minor damage.", List.of());
            case "kill_silverfish":
                return new GoalExplanation("Kill a silverfish", "Silverfish spawn from infested blocks in strongholds, igloo basements, and mountain biomes. Stronghold end portal rooms have a silverfish spawner. Breaking infested stone blocks releases them. They are small and fast but deal low damage.", List.of());
            case "kill_breeze":
                return new GoalExplanation("Kill a breeze", "Breezes are hostile mobs that spawn from trial spawners surrounded by chiseled tuff in trial chambers. They float and fire wind charges that knock you back. Find a trial chamber underground — they are marked by copper and tuff structures.", List.of());
            case "kill_elder_guardian":
                return new GoalExplanation("Kill an elder guardian", "Elder guardians are found inside ocean monuments — 3 per monument. They inflict Mining Fatigue III and deal high damage. Prepare with potions of water breathing, night vision, and fire resistance. Milk buckets can remove Mining Fatigue.", List.of());
            case "kill_ghast":
                return new GoalExplanation("Kill a ghast", "Ghasts are large flying mobs in the nether. They shoot fireballs and are easiest to kill by deflecting their own fireball back (hit or punch it). They can also be killed with a bow from a distance. Ghast tears drop for brewing.", List.of());
            case "kill_zillager":
                return new GoalExplanation("Kill a zombie villager", "Zombie villagers have a 5% spawn chance from normal zombie spawns. You can also create one by having a zombie kill a villager (hard difficulty always converts). They can be cured with a weakness potion and golden apple.", List.of());
            case "kill_witch":
                return new GoalExplanation("Kill a witch", "Witches spawn naturally in swamp biomes and at witch huts. They also appear in raids and form when lightning strikes a villager. They throw potions and can be tough — they drink fire resistance and healing potions. Get close and engage fast.", List.of());
            case "kill_stray":
                return new GoalExplanation("Kill a stray", "Strays spawn in snowy plains, ice spikes, and frozen river biomes replacing 80% of skeleton spawns. A regular skeleton caught in powdered snow for 7 seconds also becomes a stray after 15 more seconds. They shoot tipped arrows of slowness.", List.of());
            case "kill_snow_golem":
                return new GoalExplanation("Kill a snow golem", "Build a snow golem by placing a carved pumpkin on top of 2 snow blocks. Then kill it. Snow golems melt in warm or rainy biomes, so build it in a cold biome or indoors.", List.of());
            case "kill_blaze_snowball":
                return new GoalExplanation("Kill a blaze with a snowball", "Snowballs deal 3 damage (1.5 hearts) to blazes. Hit a blaze with 5 snowballs to kill it. Blazes spawn in nether fortresses. Stock up on snowballs by breaking snow blocks before entering the nether.", List.of());
            case "kill_7":
                return new GoalExplanation("Kill 7 unique hostile mobs", "Kill 7 different hostile mob species. Any of the following count:\n\n" +
                        "Bogged, Breeze, Cave Spider, Creaking, Creeper, Drowned, Elder Guardian, Enderman, Endermite, Evoker, Guardian, Husk, Phantom, Pillager, Ravager, Silverfish, Skeleton, Slime, Shulker, Spider, Stray, Vex, Vindicator, Witch, Wither, Zombie, Zombie Villager, Blaze, Ghast, Hoglin, Magma Cube, Piglin, Piglin Brute, Wither Skeleton, Zoglin, Zombified Piglin, Ender Dragon", List.of());
            case "kill_10":
                return new GoalExplanation("Kill 10 unique hostile mobs", "Kill 10 different hostile mob species. Any of the following count:\n\n" +
                        "Bogged, Breeze, Cave Spider, Creaking, Creeper, Drowned, Elder Guardian, Enderman, Endermite, Evoker, Guardian, Husk, Phantom, Pillager, Ravager, Silverfish, Skeleton, Slime, Shulker, Spider, Stray, Vex, Vindicator, Witch, Wither, Zombie, Zombie Villager, Blaze, Ghast, Hoglin, Magma Cube, Piglin, Piglin Brute, Wither Skeleton, Zoglin, Zombified Piglin, Ender Dragon", List.of());
            case "kill_15":
                return new GoalExplanation("Kill 15 unique hostile mobs", "Kill 15 different hostile mob species. Any of the following count:\n\n" +
                        "Bogged, Breeze, Cave Spider, Creaking, Creeper, Drowned, Elder Guardian, Enderman, Endermite, Evoker, Guardian, Husk, Phantom, Pillager, Ravager, Silverfish, Skeleton, Slime, Shulker, Spider, Stray, Vex, Vindicator, Witch, Wither, Zombie, Zombie Villager, Blaze, Ghast, Hoglin, Magma Cube, Piglin, Piglin Brute, Wither Skeleton, Zoglin, Zombified Piglin, Ender Dragon", List.of());
            case "kill_jeb_":
                return new GoalExplanation("Kill jeb_", "Name a sheep 'jeb_' using a name tag at an anvil. This makes its wool cycle through all colors. Now kill it. Name tags can be found in dungeon/mineshaft/woodland mansion chests or bought from librarian villagers.", List.of());
            case "kill_100":
                return new GoalExplanation("Kill 100 hostile mobs", "Kill 100 hostile mobs total. Any hostile mob counts — skeletons, zombies, spiders, and creepers are the easiest to farm. Any of the standard hostile mob list counts toward this total.", List.of());
            case "undead_30":
                return new GoalExplanation("Kill 30 undead mobs", "Kill 30 undead mobs total (same type can count multiple times). Undead mobs: Drowned, Husk, Phantom, Skeleton, Stray, Wither, Wither Skeleton, Zombie, Zombie Villager, Zombified Piglin, Bogged.", List.of());
            case "arthropod_20":
                return new GoalExplanation("Kill 20 arthropod mobs", "Kill 20 arthropod mobs total (same type can count multiple times). Arthropods: Spider, Cave Spider, Silverfish, Endermite, Bee. Cave spiders are found in abandoned mineshafts.", List.of());

            // === MISC KILL/USE ===
            case "shear_bogged":
                return new GoalExplanation("Shear a bogged", "Bogged are skeleton variants covered in mushrooms that spawn in swamps and mangrove swamps. Use shears on a bogged to remove its mushrooms. This drops brown and red mushrooms. The bogged becomes a regular skeleton after being sheared.", List.of());

            // === ADVANCEMENT GOALS ===
            case "nether_adv":
                return new GoalExplanation("Enter the nether", "Build a nether portal by placing obsidian in a 4x5 frame (minimum) and lighting it with flint and steel. Step through the purple portal to enter the nether.", List.of());
            case "end_adv":
                return new GoalExplanation("Enter the end", "Find a stronghold using eyes of ender (craft from ender pearls + blaze powder). Activate the end portal by placing eyes of ender in each portal frame. Step through to enter the end.", List.of());
            case "enchanter_adv":
                return new GoalExplanation("Use an enchanting table", "Place an enchanting table and right-click it. To get level 30 enchantments, surround it with 15 bookshelves placed 1 block away with a 1-block gap between the table and shelves. You also need lapis lazuli.", List.of());
            case "zombie_doc_adv":
                return new GoalExplanation("Zombie doctor", "Cure a zombie villager. First throw a splash potion of weakness at it, then feed it a golden apple. The villager will shake and cure after 2-5 minutes. This grants a massive trading discount on that villager.", List.of());
            case "eye_spy_adv":
                return new GoalExplanation("Enter the stronghold", "Throw eyes of ender (ender pearl + blaze powder) into the air and follow them. They float toward the nearest stronghold. Dig down at the landing spot. Enter the stronghold through any entrance.", List.of());
            case "return_to_sender_adv":
                return new GoalExplanation("Kill a ghast with its own fireball", "When a ghast shoots a fireball, hit it back with any attack (even punching or hitting with a sword). If the reflected fireball kills the ghast, this counts. Time the hit as the fireball comes close.", List.of());
            case "those_were_the_days_adv":
                return new GoalExplanation("Find a bastion remnant", "Enter any bastion remnant structure in the nether. Bastions generate in all nether biomes except basalt deltas. They are large dark stone structures with piglin inhabitants. Be careful — piglins inside are hostile by default.", List.of());
            case "terrible_fortress_adv":
                return new GoalExplanation("Find a nether fortress", "Enter a nether fortress. Fortresses are long brick corridor structures in the nether. They spawn blazes and wither skeletons and contain valuable loot. Look for them by flying or walking through open nether terrain.", List.of());
            case "subspace_bubble_adv":
                return new GoalExplanation("Travel 7km via nether", "Walk 875 blocks in the nether (each nether block = 8 overworld blocks). Build a second nether portal at least 875 blocks from your first in the nether, then use it to emerge 7km away in the overworld.", List.of());
            case "oh_shiny_adv":
                return new GoalExplanation("Distract a piglin with gold", "Drop a gold ingot near a piglin or throw it at one. The piglin will pick it up and examine it, pausing its hostility for a moment. This is the 'Oh Shiny' advancement.", List.of());
            case "this_boat_has_legs_adv":
                return new GoalExplanation("Ride a strider with warped fungus on a stick", "Tame a strider by putting a saddle on it (right-click). Then hold a warped fungus on a stick (craft: warped fungus + fishing rod) to steer it across lava.", List.of());
            case "country_lode_adv":
                return new GoalExplanation("Use a lodestone", "Place a lodestone (crafted from 1 netherite ingot and 8 chiseled stone bricks) and right-click it with a compass. The compass will now always point to that lodestone. This works in any dimension.", List.of());
            case "use_respawn_anchor_adv":
                return new GoalExplanation("Use a respawn anchor", "Craft a respawn anchor from 6 crying obsidian and 3 glowstone blocks. Place it in the nether and charge it with glowstone. Right-click it to set your respawn point. Warning: using it in the overworld or end causes an explosion.", List.of(new DrawCraftingGrid(10, 0, Arrays.asList(
                        Items.CRYING_OBSIDIAN, Items.CRYING_OBSIDIAN, Items.CRYING_OBSIDIAN,
                        Items.GLOWSTONE, Items.GLOWSTONE, Items.GLOWSTONE,
                        Items.CRYING_OBSIDIAN, Items.CRYING_OBSIDIAN, Items.CRYING_OBSIDIAN
                ), Items.RESPAWN_ANCHOR)));
            case "spooky_scary_skeletons_adv":
                return new GoalExplanation("Get a wither skeleton skull", "Wither skeletons in nether fortresses have a 2.5% chance to drop their skull (up to 5.5% with Looting III). This is one of the rarest drops in the game. Kill as many wither skeletons as possible in a nether fortress.", List.of());
            case "hot_tourist_adv":
                return new GoalExplanation("Travel to every nether biome", "Visit all 5 nether biomes: Nether Wastes, Crimson Forest, Warped Forest, Soul Sand Valley, and Basalt Deltas. Each has a distinct appearance. Use a map or coordinates to track explored areas.", List.of());
            case "great_view_adv":
                return new GoalExplanation("Great view from above", "Reach the outer end islands by throwing an ender pearl through the end fountain (or building a bridge). The outer islands are accessed from the central island after defeating the ender dragon.", List.of());
            case "trade_adv":
                return new GoalExplanation("What a deal", "Trade with a villager by right-clicking and completing any trade. Villagers are found in villages. Each profession offers different trades. You need to unlock at least 1 trade to complete this goal.", List.of());
            case "sticky_adv":
                return new GoalExplanation("Slide on a honey block to break a fall", "Fall from a height of 4+ blocks and land while touching the side of a honey block. The honey block reduces your fall speed and negates fall damage. Honey blocks also have a sticky surface.", List.of());
            case "spreads_adv":
                return new GoalExplanation("Kill a mob near a sculk catalyst", "Kill any mob within a few blocks of a sculk catalyst. The catalyst will bloat and grow sculk veins. Sculk catalysts are found in deep dark biomes and ancient cities.", List.of());
            case "hired_help_adv":
                return new GoalExplanation("Make a golem", "Build an iron golem by placing 4 iron blocks in a T-shape and putting a carved pumpkin (or jack-o-lantern) on top. It must be built in a specific order: 3 blocks side-by-side, 1 on top of the center, then the pumpkin.", List.of());
            case "arbalistic_adv":
                return new GoalExplanation("Arbalistic", "Kill 5 unique mobs with a single crossbow shot using the Multishot enchantment. Shoot into a dense crowd of different mob types. Pillager outposts or trial chambers work well for finding multiple mob types together.", List.of());
            case "walk_snow_adv":
                return new GoalExplanation("Walk on powdered snow with leather boots", "Equip leather boots (any quality) and walk onto a powdered snow block. Leather boots allow you to walk on the surface of powdered snow without sinking. Other boots will cause you to sink and take freeze damage.", List.of());
            case "bullseye_adv":
                return new GoalExplanation("Shoot a bullseye", "Place a target block and shoot its center with a bow and arrow. The target block emits a stronger redstone signal when hit closer to the center. Craft target from 4 redstone + 1 hay bale.", List.of());
            case "beelocation_adv":
                return new GoalExplanation("Total beelocation", "Move a bee nest using silk touch while bees are inside. Use a silk touch axe to break the nest — this keeps the bees inside. The advancement triggers when you pick up the nest with bees still in it.", List.of());
            case "tadpole_bucket_adv":
                return new GoalExplanation("Get a tadpole in a bucket", "Scoop up a tadpole with an empty bucket. Tadpoles spawn from frog eggs, which frogs lay after breeding in water. Breed frogs (using slimeballs) near water and wait for eggs to hatch.", List.of());
            case "sniffer_egg_adv":
                return new GoalExplanation("Obtain a sniffer egg", "Brush suspicious sand or suspicious gravel in trail ruins structures to find a sniffer egg. Trail ruins generate in taiga, old growth taiga, and snowy taiga biomes. Use a brush tool (crafted from 1 feather, 1 stick, 1 copper ingot).", List.of());
            case "catalogue_4_adv":
                return new GoalExplanation("Tame 4 unique types of cats", "Tame 4 different cat variants with raw cod or raw salmon. Cats have 11 variants. Strays spawn in villages — visit multiple villages to find different variants. Each variant counts once.", List.of());
            case "whole_pack_3_adv":
                return new GoalExplanation("Tame 3 unique types of wolves", "Tame 3 different wolf variants with bones. Different wolf variants spawn in specific biomes: pale wolf (taiga), black wolf (old growth pine taiga), red wolf (sparse jungle), etc. Travel to multiple biomes to find different types.", List.of());
            case "tactical_fishing_adv":
                return new GoalExplanation("Tactical fishing", "Catch a tropical fish in a bucket by right-clicking on one with an empty bucket. Tropical fish are found in warm ocean biomes. They are small and fast — get close to them before clicking.", List.of());
            case "wax_off_adv":
                return new GoalExplanation("Scrape wax off a copper block", "Right-click a waxed copper block with any axe. First wax a copper block (right-click with honeycomb), then scrape the wax off with an axe. The block returns to its unwaxed state.", List.of());
            case "axolotl_bucket_adv":
                return new GoalExplanation("Axolotl in a bucket", "Catch an axolotl with an empty bucket. Axolotls spawn in lush cave biomes underground, usually near water. Right-click directly on the axolotl with an empty bucket.", List.of());
            case "crafters_adv":
                return new GoalExplanation("Crafters crafting crafters", "Use a crafter to automatically craft something. Crafters are crafted from 5 iron ingots, 1 crafting table, 2 chests, and 1 redstone. Power the crafter with a redstone signal while it has ingredients inside to auto-craft.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        Items.IRON_INGOT, Items.CRAFTING_TABLE, Items.IRON_INGOT,
                        Items.CHEST, Items.REDSTONE, Items.CHEST,
                        Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT
                ), Items.CRAFTER)));
            case "trial_key_adv":
                return new GoalExplanation("Use a trial key on a vault", "Trial keys drop from trial spawners in trial chambers. Find a trial chamber underground, defeat the spawners, collect a trial key, then right-click a vault block to open it and claim the reward.", List.of());
            case "wolf_armor_adv":
                return new GoalExplanation("Use wolf armor on a wolf", "Craft wolf armor from 6 armadillo scutes (dropped by armadillos when brushed or when they grow). Right-click a tamed wolf with the wolf armor to equip it. Armadillos spawn in savanna and badlands biomes.", List.of());
            case "sound_of_music_adv":
                return new GoalExplanation("Sound of music", "Play a music disc in a jukebox while in a meadow biome. Meadows generate in mid-altitude areas near mountains. Jukeboxes are crafted from 8 planks and 1 diamond. Music discs are found in dungeon/mineshaft chests or dropped when a skeleton kills a creeper.", List.of());
            case "fishy_business_adv":
                return new GoalExplanation("Catch a fish", "Use a fishing rod to catch a fish. Cast the rod into any water (at least 1 block deep) and wait for the bobber to dip, then reel in. Fishing rods are crafted from 3 sticks and 2 string.", List.of());
            case "dragon_adv":
                return new GoalExplanation("Kill the ender dragon", "The ender dragon is found in the main end island. First destroy the end crystals on top of the obsidian pillars (shoot them with arrows). Then deal damage to the dragon when it hovers over the exit portal. The dragon regenerates health near the crystals.", List.of());
            case "sniper_adv":
                return new GoalExplanation("Sniper duel", "Kill a skeleton from 50 or more blocks away with a projectile (arrow, trident, etc.). Find a skeleton on flat ground, back up to 50+ blocks, and shoot it. Arrows drop over distance so aim higher.", List.of());
            case "spyglass_adv":
                return new GoalExplanation("Get any spyglass advancement", "Use a spyglass by holding it and right-clicking to zoom in. Spyglasses are crafted from 2 copper ingots and 1 amethyst shard. Copper is found in most underground biomes; amethyst shards grow in geodes.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        null, Items.AMETHYST_SHARD, null,
                        null, Items.COPPER_INGOT, null,
                        null, Items.COPPER_INGOT, null
                ), Items.SPYGLASS)));
            case "frightening_adv":
                return new GoalExplanation("Very very frightening", "Strike a villager with lightning from a trident with the Channeling enchantment during a thunderstorm, turning them into a witch.\n\nIn this mod the weather cycles differently: 7-12 minutes of clear weather followed by 1 minute of thunder.\n\nTridents drop from drowned with a 33% chance in this mod. The first enchantment at level 30 on a trident will always be Channeling.", List.of());
            case "adv_15":
                return new GoalExplanation("Complete 15 advancements", "Complete 15 advancements total. Advancements are tracked in the advancement screen (press L). Early advancements are easy: punch wood, make tools, enter structures, eat foods, and explore biomes.", List.of());
            case "adv_25":
                return new GoalExplanation("Complete 25 advancements", "Complete 25 advancements total. Beyond the early ones, target: nether entry, enchanting, trading, brewing, and exploration advancements.", List.of());
            case "adv_35":
                return new GoalExplanation("Complete 35 advancements", "Complete 35 advancements total. This requires significant progression into the nether and/or ocean monuments and diverse goals across multiple categories.", List.of());

            // === BIOME GOALS ===
            case "biome_ice_spikes":
                return new GoalExplanation("Find an ice spikes biome", "Ice spikes biomes are rare cold biomes featuring tall columns of packed ice. They generate in snowy plains regions. Use the F3 debug screen to see your current biome.", List.of());
            case "biome_badlands":
                return new GoalExplanation("Find a badlands biome", "Badlands are rare biomes with orange and red terracotta terrain and exposed gold ore at the surface. They generate in dry areas. Use F3 to check your biome name.", List.of());
            case "biomes_15":
                return new GoalExplanation("Find 15 unique biomes", "Visit 15 different biome types. Common early biomes: plains, forest, birch forest, taiga, swamp, river, ocean, beach, desert, savanna, jungle, flower forest, snowy plains, hills variants. Use F3 to see your current biome.", List.of());
            case "biomes_20":
                return new GoalExplanation("Find 20 unique biomes", "Visit 20 different biome types. You will need to explore less common variants: windswept hills, stony shore, grove, meadow, cherry grove, mushroom fields, or nether biomes.", List.of());
            case "biomes_25":
                return new GoalExplanation("Find 25 unique biomes", "Visit 25 different biome types. This requires significant exploration. Include nether biomes (nether wastes, crimson forest, warped forest, soul sand valley, basalt deltas) in your count.", List.of());
            case "biomes_30":
                return new GoalExplanation("Find 30 unique biomes", "Visit 30 different biome types. You will need rare biomes such as ice spikes, badlands variants, mushroom fields, and multiple nether biomes. Travel large distances in multiple directions.", List.of());

            // === DIE GOALS ===
            case "die_golem":
                return new GoalExplanation("Die by golem", "Let an iron golem kill you. Anger a village's iron golem by attacking a villager. Iron golems deal very high damage — don't wear good armor. Build one yourself (4 iron blocks + carved pumpkin) if no village is nearby.", List.of());
            case "die_llama":
                return new GoalExplanation("Die by llama", "Let a llama spit you to death. Llamas spit at players who attack them or try to ride them without taming. Each spit does 1 damage — you will need to anger many llamas simultaneously or have low health.", List.of());
            case "die_bee":
                return new GoalExplanation("Die by bee sting", "Let bees sting you to death. Anger bees by attacking them, destroying their hive/nest, or taking honey without a campfire below. Each sting does 0.5 damage and poisons you. Multiple bees are needed.", List.of());
            case "die_trident":
                return new GoalExplanation("Die by trident", "Let a drowned's thrown trident kill you. Drowned spawn in ocean biomes and rivers. They sometimes carry tridents and throw them at you. Go into an ocean at night with low health.", List.of());
            case "die_sweet_berry_bush":
                return new GoalExplanation("Die by sweet berry bush", "Walk through sweet berry bushes repeatedly until you die. Sweet berry bushes deal 1 damage per second when you walk through them. They grow in taiga biomes. This takes a while so come with low health.", List.of());
            case "die_cactus":
                return new GoalExplanation("Die by cactus", "Walk into a cactus repeatedly until you die. Cacti deal 1 damage per contact per second. Found in desert biomes. Stand against one with low health.", List.of());
            case "die_anvil":
                return new GoalExplanation("Die by anvil", "Have an anvil fall on you. Drop an anvil from a height by placing it on a piston that pushes it off a ledge, or build a tower and drop it. Anvils deal 2 damage per block fallen. Dropping from ~10 blocks kills most players.", List.of());
            case "die_stalactite":
                return new GoalExplanation("Die by falling stalactite", "Stand under a stalactite in a dripstone cave. Stalactites periodically drip and fall when their supporting block is removed. You can mine the block above a stalactite to make it fall. Each stalactite deals significant fall damage.", List.of());
            case "die_stalagmite":
                return new GoalExplanation("Die by falling on a stalagmite", "Fall onto a stalagmite (pointy dripstone facing up). Stalagmites deal double fall damage compared to a regular fall onto solid ground. Find dripstone caves and fall onto a cluster of stalagmites.", List.of());
            case "die_fireworks":
                return new GoalExplanation("Die by fireworks", "Die from a firework rocket explosion. While flying with an elytra, shooting a firework rocket directly at yourself deals explosion damage. Alternatively, load a dispenser with fireworks and stand in front of it.", List.of());
            case "die_freeze":
                return new GoalExplanation("Freeze to death", "Stand in powdered snow until you freeze to death. Powdered snow inflicts the Freezing effect after a few seconds. The screen edges turn blue as you freeze. Leather boots prevent this, so don't wear them. Powdered snow generates naturally in cold mountain biomes.", List.of());
            case "die_bed":
                return new GoalExplanation("Die by intentional game design", "Sleep in a bed while in the nether or end dimension. Beds explode violently in those dimensions. Place the bed, right-click it, and enjoy the blast. Alternatively, use a charged respawn anchor in the overworld or end.", List.of());
            case "die_tnt_minecart":
                return new GoalExplanation("Die by TNT minecart", "Get killed by a TNT minecart explosion. Place a TNT minecart on powered rail and activate it, then stand near it. Or use an activator rail to trigger the TNT minecart. Craft a TNT minecart from 1 TNT and 1 minecart.", List.of());
            case "die_ender_pearl":
                return new GoalExplanation("Die by ender pearl", "Throw an ender pearl and take enough fall damage on landing to die. Ender pearl teleportation always deals 2.5 hearts of damage. Throw it from a height to add extra fall damage, or throw multiple pearls in quick succession at low health.", List.of());
            case "die_explosion":
                return new GoalExplanation("Die by explosion", "Die from any explosion: creeper, TNT, TNT minecart, firework rocket, respawn anchor (in overworld/end), bed (in nether/end), or end crystal. Creepers are the most common — let one sneak up on you.", List.of());
            case "die_projectile":
                return new GoalExplanation("Die by projectile", "Die from a projectile: arrow, trident, firework rocket, or llama spit. Let a skeleton archer or drowned kill you. Go into an ocean or skeleton dungeon with low health.", List.of());

            // === RIDE GOALS ===
            case "ride_pig":
                return new GoalExplanation("Ride a pig", "Put a saddle on a pig (right-click with saddle). Then right-click the pig to mount it. Use a carrot on a stick (crafted from fishing rod + carrot) to steer and accelerate the pig.", List.of());
            case "ride_horse":
                return new GoalExplanation("Ride a horse", "Tame a horse first by right-clicking to mount it repeatedly until hearts appear. Then put a saddle on it (open its inventory while riding with E). Now you can ride it freely.", List.of());
            case "ride_minecart":
                return new GoalExplanation("Ride a minecart", "Place a minecart on a rail and right-click it to ride. Minecarts are crafted from 5 iron ingots. Rails are crafted from 6 iron ingots and 1 stick (yields 16 rails).", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.IRON_INGOT, null, Items.IRON_INGOT,
                        Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT,
                        null, null, null
                ), Items.MINECART)));

            // === DAMAGE GOALS ===
            case "damage_200":
                return new GoalExplanation("Deal 200 damage", "Deal 200 total damage to any mobs or players across the entire game. This accumulates passively — swords deal 5-10 damage per hit, so you need approximately 20-40 kills.", List.of());
            case "damage_500":
                return new GoalExplanation("Deal 500 damage", "Deal 500 total damage to any mobs or players. This requires consistent combat throughout the game. Power-enchanted bows and diamond swords help reach this quickly.", List.of());
            case "damage_take_200":
                return new GoalExplanation("Take 200 damage", "Take 200 total damage from any source. This accumulates across the whole game. Avoid armor to take damage faster, or intentionally take hits from hostile mobs.", List.of());

            // === USE GOALS ===
            case "use_smithing_table":
                return new GoalExplanation("Use a smithing table", "Craft a smithing table from 2 iron ingots and 4 planks, or find one in villages. Use it to upgrade diamond tools/armor to netherite, or to apply armor trims. Right-click to open its interface.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        Items.IRON_INGOT, Items.IRON_INGOT, null,
                        Items.OAK_PLANKS, Items.OAK_PLANKS, null,
                        Items.OAK_PLANKS, Items.OAK_PLANKS, null
                ), Items.SMITHING_TABLE)));
            case "use_grindstone":
                return new GoalExplanation("Use a grindstone", "Craft a grindstone from 2 sticks, 1 stone slab, and 2 planks, or find one in villages. Use it to disenchant items and repair tools/weapons. Right-click to open its interface.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        Items.STICK, Items.STONE_SLAB, Items.STICK,
                        Items.OAK_PLANKS, null, Items.OAK_PLANKS,
                        null, null, null
                ), Items.GRINDSTONE)));
            case "use_blast_furnace":
                return new GoalExplanation("Use a blast furnace", "Craft a blast furnace from 5 iron ingots, 1 furnace, and 3 smooth stone, or find one in villages. It smelts ores and metal items at twice the speed of a regular furnace. Right-click to use it.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT,
                        Items.IRON_INGOT, Items.FURNACE, Items.IRON_INGOT,
                        Items.SMOOTH_STONE, Items.SMOOTH_STONE, Items.SMOOTH_STONE
                ), Items.BLAST_FURNACE)));
            case "use_cartography_table":
                return new GoalExplanation("Use a cartography table", "Craft a cartography table from 2 paper and 4 planks, or find one in villages. Use it to clone, expand, or lock maps. Right-click to use it.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        Items.PAPER, Items.PAPER, null,
                        Items.OAK_PLANKS, Items.OAK_PLANKS, null,
                        Items.OAK_PLANKS, Items.OAK_PLANKS, null
                ), Items.CARTOGRAPHY_TABLE)));
            case "use_loom":
                return new GoalExplanation("Use a loom", "Craft a loom from 2 string and 2 planks, or find one in villages. Use it to apply banner patterns. Right-click to use it. You need at least 1 banner and 1 dye.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.STRING, Items.STRING, null,
                        Items.OAK_PLANKS, Items.OAK_PLANKS, null,
                        null, null, null
                ), Items.LOOM)));
            case "use_stonecutter":
                return new GoalExplanation("Use a stonecutter", "Craft a stonecutter from 3 stone and 1 iron ingot, or find one in villages. Use it to cut stone, stone bricks, and other stone-type blocks into various shapes more efficiently than a crafting table.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        null, null, null,
                        null, Items.IRON_INGOT, null,
                        Items.STONE, Items.STONE, Items.STONE
                ), Items.STONECUTTER)));
            case "use_anvil":
                return new GoalExplanation("Use an anvil", "Craft an anvil from 3 iron blocks and 4 iron ingots (expensive: 31 iron ingots total). Use it to rename items, combine enchantments, or repair tools. Right-click to open it.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        Items.IRON_BLOCK, Items.IRON_BLOCK, Items.IRON_BLOCK,
                        null, Items.IRON_INGOT, null,
                        Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT
                ), Items.ANVIL)));
            case "use_composter":
                return new GoalExplanation("Use a composter", "A composter converts food and plant material into bone meal. 'Using' means collecting 1 bone meal from it. Composters can be found in villages or crafted from 7 wood slabs.\n\nItems you can compost:\nWheat Seeds, Melon Seeds, Pumpkin Seeds, Beetroot Seeds, Carrots, Potatoes, Beetroot, Dried Kelp, Sugar Cane, Bamboo, Hay Bale, Tall Grass, Fern, Dead Bush, Leaves, Vines, Sweet Berries, Glow Berries, Kelp, Seagrass",
                        List.of(new DrawCraftingGrid(10, 200, Arrays.asList(
                                Items.OAK_SLAB, null, Items.OAK_SLAB,
                                Items.OAK_SLAB, null, Items.OAK_SLAB,
                                Items.OAK_SLAB, Items.OAK_SLAB, Items.OAK_SLAB
                        ), Items.COMPOSTER)));
            case "pot_plant":
                return new GoalExplanation("Pot a plant", "Place any plant or flower into a flower pot by right-clicking the pot while holding the plant. Flower pots are crafted from 3 bricks. Place the pot first, then right-click it with any flower, sapling, mushroom, fern, cactus, or bamboo.", List.of(new DrawCraftingGrid(10, 130, Arrays.asList(
                        Items.BRICK, null, Items.BRICK,
                        null, Items.BRICK, null,
                        null, null, null
                ), Items.FLOWER_POT)));

            // === MINE GOALS ===
            case "mine_emerald":
                return new GoalExplanation("Mine emerald ore", "Emerald ore is the rarest ore in the game. It generates only in mountain biomes between Y=-16 and Y=256 as single blocks (no veins). Use a silk touch pickaxe to get the ore block itself, or a fortune pickaxe for extra emerald drops.", List.of());
            case "mine_diamond":
                return new GoalExplanation("Mine diamond ore", "Diamond ore generates most commonly at Y=-58. Use an iron or better pickaxe to mine it. Use fortune to get extra diamonds, or silk touch to collect the ore block itself. Strip mining at Y=-58 or caving near bedrock level is most efficient.", List.of());
            case "mine_spawner":
                return new GoalExplanation("Mine a mob spawner", "Mob spawners are found in dungeons, mineshafts, strongholds, and nether fortresses. Mine one with a silk touch pickaxe to collect it. Without silk touch it drops nothing (it just breaks). Use the F3 screen to locate spawner rooms.", List.of());
            case "mine_turtle_egg":
                return new GoalExplanation("Mine a turtle egg", "Turtle eggs are laid on sandy beaches by turtles after breeding. Mine them with a silk touch tool — without silk touch they break and drop nothing. Be careful not to trample them (jumping on them cracks them).", List.of());

            // === TAME GOALS ===
            case "tame_cat":
                return new GoalExplanation("Tame a cat", "Approach a stray cat slowly (don't sprint or it will flee) holding raw cod or raw salmon. Right-click to feed it repeatedly until hearts appear. Cats are found near villages. They protect you from phantoms and creepers when tamed.", List.of());
            case "tame_wolf":
                return new GoalExplanation("Tame a wolf", "Right-click a wolf with bones until hearts appear. Each bone has a 1/3 chance to tame the wolf. Wolves spawn in taiga, old growth taiga, and other cold biomes. Different wolf variants exist in different biomes.", List.of());
            case "tame_horse":
                return new GoalExplanation("Tame a horse", "Right-click a horse to mount it (empty handed or with any non-food item). It will buck you off. Repeat until hearts appear — this can take several tries. The horse's temper increases each attempt. Feeding it golden apples or golden carrots speeds this up.", List.of());
            case "tame_llama":
                return new GoalExplanation("Tame a llama", "Right-click a llama to mount it. It will buck you off. Repeat until hearts appear. Llamas spawn in windswept hills and savanna biomes. Once tamed, attach a chest to it (right-click with a chest) and put a lead on it.", List.of());
            case "tame_parrot":
                return new GoalExplanation("Tame a parrot", "Right-click a parrot holding any type of seed (wheat seeds, melon seeds, pumpkin seeds, beetroot seeds, or torchflower seeds). Each seed has a chance to tame it. Parrots spawn in jungle biomes and are very rare.", List.of());

            // === EFFECT GOALS ===
            case "effect_poison":
                return new GoalExplanation("Get poisoned", "Get the poison status effect. Ways to get poisoned: spider/cave spider bite, bee sting, eating poisonous potato or pufferfish, being hit by a witch's poison splash potion, or drinking a poison potion. Poison turns your health bar olive-colored.", List.of());
            case "effect_weakness":
                return new GoalExplanation("Get weakness", "Get the weakness effect. Ways to get it: being attacked by a witch, drinking a weakness potion (brewed from fermented spider eye + water bottle), or being near a conduit with insufficient water coverage.", List.of());
            case "effect_jump_boost":
                return new GoalExplanation("Get jump boost", "Get the Jump Boost effect. Ways to get it: drink a Potion of Leaping (brewed from rabbit's foot + awkward potion), being in a beacon range with jump boost, or find a suspicious stew made with an allium flower.", List.of());
            case "effect_fire_resistance":
                return new GoalExplanation("Get fire resistance", "Get the Fire Resistance effect. Brew it: water bottle → awkward potion (nether wart) → fire resistance potion (magma cream). Magma cream is crafted from blaze powder and slimeball, or drops from magma cubes in the nether.", List.of());
            case "effect_infested":
                return new GoalExplanation("Get infested", "Get the Infested effect (new in 1.21). This is inflicted by bogged's tipped arrows or by certain trial chamber mechanics. Find a bogged (mushroom skeleton in swamps/mangroves) and let it hit you with an arrow.", List.of());
            case "effect_glowing":
                return new GoalExplanation("Get glowing", "Get the Glowing effect. The most common way is to be hit by a spectral arrow. Spectral arrows are crafted from 4 glowstone dust and 1 arrow (yields 2 spectral arrows). Shoot yourself with a dispenser or have someone else shoot you.", List.of());
            case "effect_nausea":
                return new GoalExplanation("Get nausea", "Get the Nausea effect. Ways to get it: eating pufferfish, being hit by an elder guardian's attack (which also gives Mining Fatigue), or drinking a nausea potion (brewed with a pufferfish).", List.of());
            case "effect_absorption":
                return new GoalExplanation("Get absorption", "Get the Absorption effect, which adds extra yellow hearts above your health bar. Ways to get it: eat a golden apple (Absorption II for 2 minutes), eat an enchanted golden apple (Absorption IV), or drink an absorption potion.", List.of());
            case "effect_mining_fatigue":
                return new GoalExplanation("Get mining fatigue", "Get Mining Fatigue. The only natural source is the elder guardian, which applies Mining Fatigue III to all nearby players. Ocean monuments contain 3 elder guardians. Prepare with milk buckets to cure the effect afterward.", List.of());
            case "effect_bad_omen":
                return new GoalExplanation("Get bad omen", "Get Bad Omen by killing a pillager raid captain — the pillager carrying a banner on their back. Raid captains spawn at pillager outposts and patrol groups. Getting Bad Omen then entering a village triggers a raid.", List.of());
            case "effect_3":
                return new GoalExplanation("Have 3 active effects at once", "Have 3 status effects simultaneously. Easy combination: eat a golden apple (Absorption + Regeneration), then drink a speed potion or get poisoned/poisoned by a spider. Milk buckets remove all effects.", List.of());
            case "effect_6":
                return new GoalExplanation("Have 6 active effects at once", "Have 6 status effects simultaneously. Plan a combination: golden apple (Absorption + Regen), speed potion, strength potion, resistance (beacon), and one more. Enchanted golden apple gives 4 effects at once.", List.of());
            case "milk_effect":
                return new GoalExplanation("Remove an effect using milk", "Drink a bucket of milk to remove all active status effects. First get any status effect (poison from spider, regeneration from golden apple, etc.), then drink milk. Milk is obtained by right-clicking a cow or mooshroom with an empty bucket.", List.of());

            // === BREAK GOALS ===
            case "break_5_pickaxes":
                return new GoalExplanation("Break 5 pickaxes", "Fully deplete the durability of 5 pickaxes of any material (can mix types). Wooden pickaxes have only 59 durability each — the fastest to break. Mine stone or ore to use up durability quickly.", List.of());
            case "break_diamond":
                return new GoalExplanation("Break a diamond pickaxe", "Fully deplete the durability of a diamond pickaxe (1561 durability). Mine stone or nether rack to use durability. Do NOT put Unbreaking on it. This takes significant time but diamond pickaxes mine everything fast.", List.of());

            // === SPECIAL GOALS ===
            case "get_them_in_a_bucket":
                return new GoalExplanation("Get a pufferfish in a bucket", "Right-click a pufferfish with an empty bucket while underwater. Pufferfish spawn in lukewarm and warm ocean biomes. When you approach, they puff up and deal poison damage — be careful. Get close and quickly right-click.", List.of());

            // === LEVEL GOALS ===
            case "lvl_15":
                return new GoalExplanation("Reach level 15", "Reach experience level 15. Kill mobs and mine ores to gain XP. Killing a zombie gives ~5 XP, a creeper ~5 XP, a blaze ~10 XP. Mining diamonds gives 3-7 XP. Level 15 requires approximately 255 total XP.", List.of());
            case "lvl_30":
                return new GoalExplanation("Reach level 30", "Reach experience level 30 to unlock the best enchantments. This requires 1395 total XP. Efficient XP farms: kill blazes in the nether, mine in caves, or use a mob grinder. Level 30 is needed for the best enchanting table results.", List.of());

            // === POSITION GOALS ===
            case "reach_bedrock":
                return new GoalExplanation("Reach bedrock", "Dig straight down to bedrock. In the new world height (since 1.18), bedrock starts at Y=-60 to Y=-64. The deepslate layer starts at Y=0. Digging straight down is risky — use a staircase or check for caves below.", List.of());
            case "reach_sky_limit":
                return new GoalExplanation("Reach the sky limit", "Build or climb to the maximum build height of Y=319. This requires significant amounts of blocks or finding a tall mountain (some peak at Y=240+). Scaffolding is efficient for building tall towers quickly.", List.of());
            case "fall_300":
                return new GoalExplanation("Fall for 300 blocks", "Fall 300 blocks in a single fall. Find or build a structure at Y=300+ and jump off. You must survive — use a water bucket to land safely, or a powder snow block at the bottom. An elytra glide that ends with a near-vertical descent also counts.", List.of());

            // === WOOL GOALS ===
            case "64_lime_wool":
                return new GoalExplanation("Obtain a stack of lime wool", "Collect 64 lime wool. Dye white wool with lime dye (crafted from green dye + white dye, or found as cactus green + bonemeal). White wool drops from white sheep. Lime sheep are rare but can be dyed.", List.of());
            case "64_gray_wool":
                return new GoalExplanation("Obtain a stack of gray wool", "Collect 64 gray wool. Dye white wool with gray dye (crafted from black dye + white dye / bonemeal). White wool drops from white sheep, which are the most common type.", List.of());
            case "64_cyan_wool":
                return new GoalExplanation("Obtain a stack of cyan wool", "Collect 64 cyan wool. Dye white wool with cyan dye (crafted from blue dye + green dye). Cyan sheep are rare but cyan dye can be crafted from lapis lazuli and cactus green.", List.of());
            case "64_brown_wool":
                return new GoalExplanation("Obtain a stack of brown wool", "Collect 64 brown wool. Brown sheep are rare (3% spawn rate). Dye white wool with brown dye (crafted from cocoa beans) to get more. Cocoa beans grow on jungle logs.", List.of());

            // === SMITHING TEMPLATE ===
            case "duplicate_smithing_template":
                return new GoalExplanation("Duplicate a smithing template", "Duplicate any smithing template by combining it with 7 diamonds and the corresponding base material (e.g., netherrack for nether-found templates, sandstone for coast/dune templates) in a crafting table. The template must match the base material of its origin structure.", List.of());

            // === MOVEMENT GOALS ===
            case "sprint_1km":
                return new GoalExplanation("Sprint 1 km", "Sprint 1000 blocks total. Hold the sprint key (default: Ctrl or double-tap W) while moving. Sprint distance accumulates across the whole game. Flat terrain is fastest.", List.of());
            case "swim_1km":
                return new GoalExplanation("Swim 1 km", "Swim 1000 blocks total in water. Find an ocean biome and swim along the surface. Depth Strider boots and Dolphins Grace effect speed this up significantly.", List.of());
            case "fly_elytra":
                return new GoalExplanation("Fly 1km with elytra", "Fly 1000 blocks using an elytra. Elytra is found in end ships. Use firework rockets to propel yourself forward. Fly between high points or build a launch ramp.", List.of());

            // === BREW GOALS ===
            case "brew_invis":
                return new GoalExplanation("Brew a potion of invisibility", "Fermented spider eye is crafted from spider eye + brown mushroom + sugar.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.GOLDEN_CARROT, Items.FERMENTED_SPIDER_EYE),
                        Arrays.asList("Awkward Potion", "Night Vision Potion", "Invisibility Potion"))));
            case "brew_regen":
                return new GoalExplanation("Brew a potion of regeneration", "Ghast tears are dropped by ghasts in the nether.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.GHAST_TEAR),
                        Arrays.asList("Awkward Potion", "Regeneration Potion"))));
            case "brew_oozing":
                return new GoalExplanation("Brew a potion of oozing", "Oozing is a new 1.21 effect that spawns slimes when you take damage.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.SLIME_BALL),
                        Arrays.asList("Awkward Potion", "Potion of Oozing"))));
            case "brew_weaving":
                return new GoalExplanation("Brew a potion of weaving", "Weaving is a new 1.21 effect that spawns cobwebs when you take damage.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.STRING),
                        Arrays.asList("Awkward Potion", "Potion of Weaving"))));
            case "brew_leaping":
                return new GoalExplanation("Brew a potion of leaping", "Rabbit's feet are a rare drop from rabbits. Leaping gives the Jump Boost effect.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.RABBIT_FOOT),
                        Arrays.asList("Awkward Potion", "Potion of Leaping"))));
            case "brew_healing":
                return new GoalExplanation("Brew a potion of healing", "Glistering melon slice is crafted from 1 melon slice and 8 gold nuggets.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.GLISTERING_MELON_SLICE),
                        Arrays.asList("Awkward Potion", "Potion of Healing"))));
            case "brew_harming":
                return new GoalExplanation("Brew a potion of harming", "Corrupt a poison potion with fermented spider eye. Fermented spider eye is crafted from spider eye + brown mushroom + sugar.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE),
                        Arrays.asList("Awkward Potion", "Potion of Poison", "Potion of Harming"))));
            case "brew_fire_resistance":
                return new GoalExplanation("Brew a potion of fire resistance", "Magma cream drops from magma cubes in the nether, or craft from blaze powder + slimeball.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.MAGMA_CREAM),
                        Arrays.asList("Awkward Potion", "Potion of Fire Resistance"))));
            case "brew_water_breathing":
                return new GoalExplanation("Brew a potion of water breathing", "Pufferfish are caught by fishing in warm oceans or by scooping them in a bucket.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.PUFFERFISH),
                        Arrays.asList("Awkward Potion", "Potion of Water Breathing"))));
            case "brew_swiftness":
                return new GoalExplanation("Brew a potion of swiftness", "Sugar is crafted from sugar cane, which grows near water. This is one of the simplest potions to brew.", List.of(new DrawBrewingRecipe(
                        Arrays.asList(Items.NETHER_WART, Items.SUGAR),
                        Arrays.asList("Awkward Potion", "Potion of Swiftness"))));

            // === INVENTORY GOALS ===
            case "fill_inventory":
                return new GoalExplanation("Fill your inventory", "Fill all 36 inventory slots (27 main + 9 hotbar) with any items. Each slot must contain at least 1 item. Different items in different slots, or stacks in each slot — both count.", List.of());
            case "fill_inventory_unique":
                return new GoalExplanation("Fill inventory with unique items", "Fill all 36 inventory slots with different item types — no two slots can have the same item. Collect diverse materials: wood types, stone types, ores, foods, tools, and materials from multiple biomes.", List.of());

            // === HUNGER GOAL ===
            case "empty_hunger":
                return new GoalExplanation("Empty your hunger bar", "Let your hunger bar reach 0 (all 10 shanks depleted). Stop eating and perform strenuous activities (sprint, jump, attack). On hard difficulty your health will drain once hunger hits 0. On normal you stop regenerating health.", List.of());

            // === MORE GOALS ===
            case "more_lvl":
                return new GoalExplanation("Get more levels than opponent", "Have more experience levels than your opponent at the time the goal is checked. Farm XP efficiently: kill mobs, mine ores, smelt in furnaces. Blazes give 10 XP each and are easy to farm in nether fortresses.", List.of());
            case "more_hoppers":
                return new GoalExplanation("Get more hoppers than opponent", "Have more hoppers than your opponent. Hoppers are crafted from 5 iron ingots and 1 chest. They transfer items between containers.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.IRON_INGOT, null, Items.IRON_INGOT,
                        Items.IRON_INGOT, Items.CHEST, Items.IRON_INGOT,
                        null, Items.IRON_INGOT, null
                ), Items.HOPPER)));
            case "more_dried_kelp_blocks":
                return new GoalExplanation("Get more dried kelp blocks than opponent", "Have more dried kelp blocks than your opponent. Dried kelp blocks are crafted from 9 dried kelp. Dry kelp by smelting it in a furnace. Kelp grows abundantly in most ocean biomes.", List.of(new DrawCraftingGrid(10, 120, Arrays.asList(
                        Items.DRIED_KELP, Items.DRIED_KELP, Items.DRIED_KELP,
                        Items.DRIED_KELP, Items.DRIED_KELP, Items.DRIED_KELP,
                        Items.DRIED_KELP, Items.DRIED_KELP, Items.DRIED_KELP
                ), Items.DRIED_KELP_BLOCK)));
            case "more_white_concrete":
                return new GoalExplanation("Get more white concrete than opponent", "Have more white concrete than your opponent. Craft white concrete powder from 4 sand + 4 gravel + 1 white dye (bonemeal), then place it and pour water on it to form concrete. White dye is made from bonemeal or lily of the valley.", List.of());

            default:
                return new GoalExplanation("Lorem ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", List.of());
        }
    }
}
