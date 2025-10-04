package net.abrikoos.lockout_bingo.client.gui.goalExplanations;

import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class GoalExplanationRegistry {

    public static GoalExplanation getGoalExplanation(String goalCraftingId) {
        switch (goalCraftingId) {
            case "obtain_end_crystal":
                return new GoalExplanation("obtain end crystal", "the only way to obtain an end crystal is by crafting it", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        Items.GLASS, Items.GLASS, Items.GLASS,
                        Items.GLASS, Items.ENDER_EYE,Items.GLASS,
                        Items.GLASS, Items.GHAST_TEAR, Items.GLASS
                        ), Items.END_CRYSTAL)
                ));
            case "obtain_calibrated_sculk_sensor":
                return new GoalExplanation("obtain calibrated sculk sensor", "the only way to obtain a calibrated sculk sensor is by crafting it. For the recipe you need a sculk sensor, which can be found in deep dark biomes and, ancient cities. ", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        null, Items.AMETHYST_SHARD, null,
                        Items.AMETHYST_SHARD, Items.SCULK_SENSOR, Items.AMETHYST_SHARD,
                        null, null, null
                ), Items.CALIBRATED_SCULK_SENSOR)
                ));
            case "trade_adv":
                return new GoalExplanation("What a deal", "Trade with a villager", List.of());
            case "no_water":
                return new GoalExplanation("Don't touch water", "just don't touch it", List.of());
            case "use_composter":
                return new GoalExplanation("use a composter", "A composter is a block that can be used to convert food and plant material into bone meal. 'Using' is defined as getting 1 bone meal from it. " +
                        " A composter can be found in villages or can be crafted with any type of wood slabs. \n\n\n\n\n\n\n You can use it by right clicking on it with any of the following items:\n\n" +
                        "- Wheat Seeds\n" +
                        "- Melon Seeds\n" +
                        "- Pumpkin Seeds\n" +
                        "- Beetroot Seeds\n" +
                        "- Carrots\n" +
                        "- Potatoes\n" +
                        "- Beetroot\n" +
                        "- Cactus Green\n" +
                        "- Dried Kelp\n" +
                        "- Sugar Cane\n" +
                        "- Bamboo\n" +
                        "- Hay Bale\n" +
                        "- Tall Grass\n" +
                        "- Fern\n" +
                        "- Dead Bush\n" +
                        "- Leaves (any type)\n" +
                        "- Vines\n" +
                        "- Sweet Berries\n" +
                        "- Glow Berries\n" +
                        "- Kelp\n" +
                        "- Seagrass",
                    List.of(new DrawCraftingGrid(10, 140, Arrays.asList(
                            Items.OAK_SLAB, null, Items.OAK_SLAB,
                            Items.OAK_SLAB, null, Items.OAK_SLAB,
                            Items.OAK_SLAB, Items.OAK_SLAB, Items.OAK_SLAB
                    ), Items.COMPOSTER)));
            case "obtain_bell":
                return new GoalExplanation("obtain a bell", "Bells can always be found in villages or with a 1.5% chance inside ruined portal chests. Bells can be mined with anything.", List.of());
            case "obtain_bottle_o_enchanting":
                return new GoalExplanation("obtain bottle o enchanting", "bottle o enchanting can't be crafted but can be found in Shipwreck chests, Pillager outpost chests and, Ancient city chests.", List.of());
            case "no_crafting_table":
                return new GoalExplanation("no crafting table", "you are not allowed to have a crafting table in your inventory. You can still use a crafting table you find or use the crafting grid in you inventory. \n Crafting tables can be found in the overworld at some houses in villages, pillager towers, witch huts and igloos.", List.of());
            case "eat_beetroot_soup":
                return new GoalExplanation("eat beetroot soup", "Beetroot soup can be crafted and can also be found in snowy village chests.", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        Items.BEETROOT, Items.BEETROOT, Items.BEETROOT,
                        Items.BEETROOT, Items.BOWL, Items.BEETROOT,
                        null, Items.BOWL, null
                ), Items.BEETROOT_SOUP)
                ));
            case "eat_rabbit_stew":
                return new GoalExplanation("eat rabbit stew", "Rabbit stew can be crafted using either mushroom and can also be traded for with lvl1 butchers.", List.of(new DrawCraftingGrid(10, 100, Arrays.asList(
                        null, Items.RABBIT, null,
                        Items.CARROT, Items.BAKED_POTATO, Items.RED_MUSHROOM,
                        null, Items.BOWL, null
                ), Items.RABBIT_STEW)
                ));
            case "frightening_adv":
                return new GoalExplanation("Very very frightening",
                        "Turn a villager into a witch by throwing a trident with channeling at it during a thunderstorm, striking them with lightning. \n\n" +
                                "Because of this goal the weather pattern is different. There will be a 7 to 12 minute clear weather followed by a minute of thunder.\n\n" +
                                "Tridents will drop from a drowned with a trident 33% of the time in this mod.\n The first time you enchant a trident with an enchantment table with lvl 30 it will always give you channeling.", List.of());
            case "breed_5":
                return new GoalExplanation("breed 5 unique animals", "breed 5 pairs of animals together: \n\n" +
                        "- tamed horses with golden apples/carrots.\n" +
                        "- tamed Donkeys with golden apples/carrots.\n" +
                        "- A Mule by breeding a horse with a donkey. \n" +
                        "- cows with wheat\n" +
                        "- pigs with carrots, potato's or beetroot\n" +
                        "- chickens with any type of seed, or pitcher pod\n" +
                        "- tamed wolves with any type of meat.\n" +
                        "- cats with raw cod or salmon\n" +
                        "- ocelots with raw cod or salmon\n" +
                        "- axolotls with buckets of tropical fish\n" +
                        "- tamed Llamas with hay bales.\n" +
                        "- rabbits with dandelions, carrots or golden carrots.\n" +
                        "- turtles with seagrass.\n" +
                        "- pandas with bamboo (they need at least eight bamboo plants in a radius of 5 blocks to breed)\n" +
                        "- foxes with sweet/glow berries.\n" +
                        "- bees with flowers.\n" +
                        "- striders with warped fungus\n" +
                        "- hoglins with crimson fungus.\n" +
                        "- frogs with slime balls.\n" +
                        "- camels with cactus\n" +
                        "- sniffers with torchflower seeds.\n" +
                        "- armadillos with spider eyes", List.of());
            case "breed_10":
                return new GoalExplanation("breed 10 unique animals", "breed 10 pairs of animals together: \n\n" +
                        "- tamed horses with golden apples/carrots.\n" +
                        "- tamed Donkeys with golden apples/carrots.\n" +
                        "- A Mule by breeding a horse with a donkey. \n" +
                        "- cows with wheat\n" +
                        "- pigs with carrots, potato's or beetroot\n" +
                        "- chickens with any type of seed, or pitcher pod\n" +
                        "- tamed wolves with any type of meat.\n" +
                        "- cats with raw cod or salmon\n" +
                        "- ocelots with raw cod or salmon\n" +
                        "- axolotls with buckets of tropical fish\n" +
                        "- tamed Llamas with hay bales.\n" +
                        "- rabbits with dandelions, carrots or golden carrots.\n" +
                        "- turtles with seagrass.\n" +
                        "- pandas with bamboo (they need at least eight bamboo plants in a radius of 5 blocks to breed)\n" +
                        "- foxes with sweet/glow berries.\n" +
                        "- bees with flowers.\n" +
                        "- striders with warped fungus\n" +
                        "- hoglins with crimson fungus.\n" +
                        "- frogs with slime balls.\n" +
                        "- camels with cactus\n" +
                        "- sniffers with torchflower seeds.\n" +
                        "- armadillos with spider eyes", List.of());
            case "breed_15":
                return new GoalExplanation("breed 15 unique animals", "breed 15 pairs of animals together: \n\n" +
                        "- tamed horses with golden apples/carrots.\n" +
                        "- tamed Donkeys with golden apples/carrots.\n" +
                        "- A Mule by breeding a horse with a donkey. \n" +
                        "- cows with wheat\n" +
                        "- pigs with carrots, potato's or beetroot\n" +
                        "- chickens with any type of seed, or pitcher pod\n" +
                        "- tamed wolves with any type of meat.\n" +
                        "- cats with raw cod or salmon\n" +
                        "- ocelots with raw cod or salmon\n" +
                        "- axolotls with buckets of tropical fish\n" +
                        "- tamed Llamas with hay bales.\n" +
                        "- rabbits with dandelions, carrots or golden carrots.\n" +
                        "- turtles with seagrass.\n" +
                        "- pandas with bamboo (they need at least eight bamboo plants in a radius of 5 blocks to breed)\n" +
                        "- foxes with sweet/glow berries.\n" +
                        "- bees with flowers.\n" +
                        "- striders with warped fungus\n" +
                        "- hoglins with crimson fungus.\n" +
                        "- frogs with slime balls.\n" +
                        "- camels with cactus\n" +
                        "- sniffers with torchflower seeds.\n" +
                        "- armadillos with spider eyes", List.of());
            case "kill_silverfish":
                return new GoalExplanation("kill a silverfish", "Kill a silverfish. Silverfish are small hostile mobs that infest stone and deepslate blocks. Silverfish spawn from broken infested blocks, which generate in strongholds, underground in mountains and windswept hills biomes, in igloo basements and, in woodland mansion false portal rooms. Silverfish spawners naturally generate in end portal rooms in strongholds.", List.of());
            case "kill_piglin_brute":
                return new GoalExplanation("kill piglin brute", "A piglin brute spawns in all types of bastion remnant. They do not trade, cannot be distracted by gold or golden armor and attack on sight when the player comes in close proximity of the brute. Nearby regular piglins may become hostile when you direcly damage the piglin brute.", List.of());
            case "kill_zoglin":
                return new GoalExplanation("kill a zoglin", "A zoglin or zombier hoglin is created when you bring a hoglin to the overworld. Watch out because hoglins are scared of nether portals.", List.of());
            case "kill_endermite":
                return new GoalExplanation("kill an endermite", "Endermite have a 5% chance to spawn when a player-thrown ender pearl lands. They spawn either at the players landing site or its original position. Endermite despawn after 2 minutes even when ina a minecart or boat ", List.of());
            case "kill_breeze":
                return new GoalExplanation("kill a breeze", "A breeze is a floating hostile mob similar to the blaze that spawns from trial spawners surrounded by chiseled tuff in trial chambers.", List.of());
            case "kill_elder_guardian":
                return new GoalExplanation("kill an elder guardian", "An elder guardian is a stronger and larger variant of the guardian. They can be found inside an ocean monument. There only spawn 3 in them. They give the mining fatigue effect. They are tough to take down without the proper preparations.", List.of());
            case "kill_ghast":
                return new GoalExplanation("kill a ghast", "Ghast can be found in the nether. They are flying which makes them difficult to hit. You can damage it with its own fireball by hitting it back or any other damaging way.", List.of());
            case "kill_zillager":
                return new GoalExplanation("kill a zombie villager", "when a zombie spawns there is a 5% chance that a zombie villager spawns instead. You can make a zombie villager yourself by having any type of zombie kill a villager.", List.of());
            case "kill_witch":
                return new GoalExplanation("kill a witch", "A witch has a very small chance to spawn naturally in the overworld. Every witch hut in a swamp biome always spawns a witch. Witches always spawn in raids as part of wave 3 or 4. A villager can turn into a witch when struck by lightning.", List.of());
            case "kill_snow_golem":
                return new GoalExplanation("kill a snow golem", "Murder Anna's bestest friend. Create a snow golem by placing a carved pumpkin on top of two snow blocks.", List.of());
            case "kill_stray":
                return new GoalExplanation("kill a stray","A stray is a variant of a skeleton that spawns in a few snow-covered biomes. A stray may spawn directly under the sky in snowy plains, ice spikes or frozen rivers, replacing 80% of skeletons. When a skeleton is kept inside powder snow for 7 seconds, it begins shaking. After another 15 seconds it becomes a stray.\n",List.of());
            case "kill_7":
                return new GoalExplanation("kill 7 unique hostile mobs", "Pay the goal's price in other creature's blood! kill 7 unique hostile mobs from the following list of 37 mobs:\n\n" +
                        "-Bogged                -Spider\n" +
                        "-Breeze                -Stray\n" +
                        "-Cave Spider           -Vex\n" +
                        "-Creaking              -Vindicator\n" +
                        "-Creeper               -Witch\n" +
                        "-Drowned               -Wither\n" +
                        "-Elder Guardian        -Zombie\n" +
                        "-Enderman              -Zombie Villager\n" +
                        "-Endermite             -Blaze\n" +
                        "-Evoker                -Ghast\n" +
                        "-Guardian              -Hoglin\n" +
                        "-Husk                  -Magma Cube\n" +
                        "-Phantom               -Piglin\n" +
                        "-Pillager              -Piglin Brute\n" +
                        "-Ravager               -Wither Skeleton\n" +
                        "-Silverfish            -Zoglin\n" +
                        "-Skeleton              -Zombified Piglin\n" +
                        "-Slime                 -Ender Dragon\n" +
                        "-Shulker",List.of());
            case "kill_15":
                return new GoalExplanation("kill 15 unique hostile mobs", "Pay the goal's price in other creature's blood! kill 15 unique hostile mobs from the following list of 37 mobs:\n\n" +
                        "-Bogged                -Spider\n" +
                        "-Breeze                -Stray\n" +
                        "-Cave Spider           -Vex\n" +
                        "-Creaking              -Vindicator\n" +
                        "-Creeper               -Witch\n" +
                        "-Drowned               -Wither\n" +
                        "-Elder Guardian        -Zombie\n" +
                        "-Enderman              -Zombie Villager\n" +
                        "-Endermite             -Blaze\n" +
                        "-Evoker                -Ghast\n" +
                        "-Guardian              -Hoglin\n" +
                        "-Husk                  -Magma Cube\n" +
                        "-Phantom               -Piglin\n" +
                        "-Pillager              -Piglin Brute\n" +
                        "-Ravager               -Wither Skeleton\n" +
                        "-Silverfish            -Zoglin\n" +
                        "-Skeleton              -Zombified Piglin\n" +
                        "-Slime                 -Ender Dragon\n" +
                        "-Shulker",List.of());
            case "kill_jeb_":
                new GoalExplanation("Kill a sheep named jeb_", "Name a sheep 'jeb_' with a nametag making it rainbow coloured. Now kill it.", List.of());
            case "kill_100":
                return new GoalExplanation("kill 100 hostile mobs", "Pay the goal's price in other creature's blood! kill 100 hostile mobs from the following list of 37 mobs:\n\n" +
                        "-Bogged                -Spider\n" +
                        "-Breeze                -Stray\n" +
                        "-Cave Spider           -Vex\n" +
                        "-Creaking              -Vindicator\n" +
                        "-Creeper               -Witch\n" +
                        "-Drowned               -Wither\n" +
                        "-Elder Guardian        -Zombie\n" +
                        "-Enderman              -Zombie Villager\n" +
                        "-Endermite             -Blaze\n" +
                        "-Evoker                -Ghast\n" +
                        "-Guardian              -Hoglin\n" +
                        "-Husk                  -Magma Cube\n" +
                        "-Phantom               -Piglin\n" +
                        "-Pillager              -Piglin Brute\n" +
                        "-Ravager               -Wither Skeleton\n" +
                        "-Silverfish            -Zoglin\n" +
                        "-Skeleton              -Zombified Piglin\n" +
                        "-Slime                 -Ender Dragon\n" +
                        "-Shulker",List.of());
            case "undead_30":
                return new GoalExplanation("kill 30 unique undead mobs", "Pay the goal's price in other creature's blood! kill 30 undead mobs from the following list of 11 mobs:\n\n" +
                        "-Drowned\n" +
                        "-Husk\n" +
                        "-Phantom\n" +
                        "-Skeleton\n" +
                        "-Stray\n" +
                        "-Wither\n" +
                        "-Wither Skeleton\n" +
                        "-Zombie\n" +
                        "-Zombie Villager\n" +
                        "-Zombified Piglin\n" +
                        "-Endermite",List.of());
            case "arthropod_20":
                return new GoalExplanation("kill 20 arthropod mobs", "Pay the goal's price in other creature's blood! kill 20 arthropod mobs from the following list of 5 mobs:\n\n" +
                        "-Spider\n" +
                        "-Cave Spider\n" +
                        "-Silverfish\n" +
                        "-Endermite\n" +
                        "-Bee" ,List.of());
            default:
                return new GoalExplanation("Lorem ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. \n \n Sed ut perspiciatis, unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam eaque ipsa, quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt, explicabo. Nemo enim ipsam voluptatem, quia voluptas sit, aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos, qui ratione voluptatem sequi nesciunt, neque porro quisquam est, qui dolorem ipsum, quia dolor sit amet consectetur adipisci[ng] velit, sed quia non numquam [do] eius modi tempora inci[di]dunt, ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum[d] exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? [D]Quis autem vel eum i[r]ure reprehenderit, qui in ea voluptate velit esse, quam nihil molestiae consequatur, vel illum, qui dolorem eum fugiat, quo voluptas nulla pariatur?", List.of());
        }

    }







}
