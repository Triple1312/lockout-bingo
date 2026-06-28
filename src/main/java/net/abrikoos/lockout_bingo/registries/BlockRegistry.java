package net.abrikoos.lockout_bingo.registries;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.abrikoos.lockout_bingo.registries.BlockGroups.*;

/**
 * Registry of block groups keyed by the constants in {@link BlockGroups}.
 * <p>
 * All block IDs are stored as bare strings (no namespace) and assumed to be
 * in the {@code minecraft:} namespace. IDs that do not exist in the running
 * game version are silently dropped by {@link #get} and {@link #getIdentifiers},
 * so adding future-version blocks is safe — they simply won't appear in older builds.
 */
public final class BlockRegistry {

    private BlockRegistry() {}

    // ──────────────────────────────── group data ──────────────────────────────

    private static final Map<String, List<String>> GROUPS = new LinkedHashMap<>();

    static {
        // helper for the 16 dye colours in vanilla order
        final List<String> DYES = List.of(
                "white", "orange", "magenta", "light_blue", "yellow", "lime",
                "pink", "gray", "light_gray", "cyan", "purple", "blue",
                "brown", "green", "red", "black"
        );

        // ── structural ──────────────────────────────────────────────────────
        GROUPS.put(STAIRS, List.of(
                "oak_stairs", "spruce_stairs", "birch_stairs", "jungle_stairs",
                "acacia_stairs", "dark_oak_stairs", "mangrove_stairs", "cherry_stairs",
                "bamboo_stairs", "bamboo_mosaic_stairs", "crimson_stairs", "warped_stairs",
                "pale_oak_stairs",                          // 1.21.4+
                "stone_stairs", "cobblestone_stairs", "mossy_cobblestone_stairs",
                "stone_brick_stairs", "mossy_stone_brick_stairs",
                "granite_stairs", "polished_granite_stairs",
                "diorite_stairs", "polished_diorite_stairs",
                "andesite_stairs", "polished_andesite_stairs",
                "cobbled_deepslate_stairs", "polished_deepslate_stairs",
                "deepslate_brick_stairs", "deepslate_tile_stairs",
                "brick_stairs", "sandstone_stairs", "smooth_sandstone_stairs",
                "red_sandstone_stairs", "smooth_red_sandstone_stairs",
                "prismarine_stairs", "prismarine_brick_stairs", "dark_prismarine_stairs",
                "nether_brick_stairs", "red_nether_brick_stairs",
                "blackstone_stairs", "polished_blackstone_stairs",
                "polished_blackstone_brick_stairs",
                "quartz_stairs", "smooth_quartz_stairs",
                "purpur_stairs", "end_stone_brick_stairs",
                "cut_copper_stairs", "exposed_cut_copper_stairs",
                "weathered_cut_copper_stairs", "oxidized_cut_copper_stairs",
                "waxed_cut_copper_stairs", "waxed_exposed_cut_copper_stairs",
                "waxed_weathered_cut_copper_stairs", "waxed_oxidized_cut_copper_stairs",
                "mud_brick_stairs",
                "tuff_stairs", "tuff_brick_stairs", "polished_tuff_stairs"
        ));

        GROUPS.put(SLABS, List.of(
                "oak_slab", "spruce_slab", "birch_slab", "jungle_slab",
                "acacia_slab", "dark_oak_slab", "mangrove_slab", "cherry_slab",
                "bamboo_slab", "bamboo_mosaic_slab", "crimson_slab", "warped_slab",
                "pale_oak_slab",                            // 1.21.4+
                "stone_slab", "smooth_stone_slab", "cobblestone_slab",
                "mossy_cobblestone_slab", "stone_brick_slab", "mossy_stone_brick_slab",
                "granite_slab", "polished_granite_slab",
                "diorite_slab", "polished_diorite_slab",
                "andesite_slab", "polished_andesite_slab",
                "cobbled_deepslate_slab", "polished_deepslate_slab",
                "deepslate_brick_slab", "deepslate_tile_slab",
                "brick_slab", "sandstone_slab", "smooth_sandstone_slab",
                "red_sandstone_slab", "smooth_red_sandstone_slab",
                "prismarine_slab", "prismarine_brick_slab", "dark_prismarine_slab",
                "nether_brick_slab", "red_nether_brick_slab",
                "blackstone_slab", "polished_blackstone_slab",
                "polished_blackstone_brick_slab",
                "quartz_slab", "smooth_quartz_slab",
                "purpur_slab", "end_stone_brick_slab",
                "cut_copper_slab", "exposed_cut_copper_slab",
                "weathered_cut_copper_slab", "oxidized_cut_copper_slab",
                "waxed_cut_copper_slab", "waxed_exposed_cut_copper_slab",
                "waxed_weathered_cut_copper_slab", "waxed_oxidized_cut_copper_slab",
                "mud_brick_slab", "petrified_oak_slab",
                "tuff_slab", "tuff_brick_slab", "polished_tuff_slab"
        ));

        GROUPS.put(FENCES, List.of(
                "oak_fence", "spruce_fence", "birch_fence", "jungle_fence",
                "acacia_fence", "dark_oak_fence", "mangrove_fence", "cherry_fence",
                "bamboo_fence", "crimson_fence", "warped_fence",
                "pale_oak_fence",                           // 1.21.4+
                "nether_brick_fence"
        ));

        GROUPS.put(FENCE_GATES, List.of(
                "oak_fence_gate", "spruce_fence_gate", "birch_fence_gate",
                "jungle_fence_gate", "acacia_fence_gate", "dark_oak_fence_gate",
                "mangrove_fence_gate", "cherry_fence_gate", "bamboo_fence_gate",
                "crimson_fence_gate", "warped_fence_gate",
                "pale_oak_fence_gate"                       // 1.21.4+
        ));

        GROUPS.put(WALLS, List.of(
                "cobblestone_wall", "mossy_cobblestone_wall",
                "stone_brick_wall", "mossy_stone_brick_wall",
                "granite_wall", "diorite_wall", "andesite_wall",
                "brick_wall", "sandstone_wall", "red_sandstone_wall",
                "prismarine_wall", "nether_brick_wall", "red_nether_brick_wall",
                "blackstone_wall", "polished_blackstone_wall",
                "polished_blackstone_brick_wall",
                "cobbled_deepslate_wall", "polished_deepslate_wall",
                "deepslate_brick_wall", "deepslate_tile_wall",
                "end_stone_brick_wall", "mud_brick_wall",
                "tuff_wall", "tuff_brick_wall", "polished_tuff_wall"
        ));

        GROUPS.put(DOORS, List.of(
                "oak_door", "spruce_door", "birch_door", "jungle_door",
                "acacia_door", "dark_oak_door", "mangrove_door", "cherry_door",
                "bamboo_door", "crimson_door", "warped_door",
                "pale_oak_door",                            // 1.21.4+
                "iron_door",
                "copper_door", "exposed_copper_door", "weathered_copper_door",
                "oxidized_copper_door", "waxed_copper_door",
                "waxed_exposed_copper_door", "waxed_weathered_copper_door",
                "waxed_oxidized_copper_door"
        ));

        GROUPS.put(TRAPDOORS, List.of(
                "oak_trapdoor", "spruce_trapdoor", "birch_trapdoor", "jungle_trapdoor",
                "acacia_trapdoor", "dark_oak_trapdoor", "mangrove_trapdoor",
                "cherry_trapdoor", "bamboo_trapdoor", "crimson_trapdoor",
                "warped_trapdoor",
                "pale_oak_trapdoor",                        // 1.21.4+
                "iron_trapdoor",
                "copper_trapdoor", "exposed_copper_trapdoor",
                "weathered_copper_trapdoor", "oxidized_copper_trapdoor",
                "waxed_copper_trapdoor", "waxed_exposed_copper_trapdoor",
                "waxed_weathered_copper_trapdoor", "waxed_oxidized_copper_trapdoor"
        ));

        // ── wood family ─────────────────────────────────────────────────────
        GROUPS.put(LOGS, List.of(
                "oak_log", "spruce_log", "birch_log", "jungle_log",
                "acacia_log", "dark_oak_log", "mangrove_log", "cherry_log",
                "crimson_stem", "warped_stem", "bamboo_block",
                "pale_oak_log"                              // 1.21.4+
        ));

        GROUPS.put(STRIPPED_LOGS, List.of(
                "stripped_oak_log", "stripped_spruce_log", "stripped_birch_log",
                "stripped_jungle_log", "stripped_acacia_log", "stripped_dark_oak_log",
                "stripped_mangrove_log", "stripped_cherry_log",
                "stripped_crimson_stem", "stripped_warped_stem",
                "stripped_bamboo_block",
                "stripped_pale_oak_log"                     // 1.21.4+
        ));

        GROUPS.put(WOOD, List.of(
                "oak_wood", "spruce_wood", "birch_wood", "jungle_wood",
                "acacia_wood", "dark_oak_wood", "mangrove_wood", "cherry_wood",
                "crimson_hyphae", "warped_hyphae",
                "pale_oak_wood"                             // 1.21.4+
        ));

        GROUPS.put(PLANKS, List.of(
                "oak_planks", "spruce_planks", "birch_planks", "jungle_planks",
                "acacia_planks", "dark_oak_planks", "mangrove_planks", "cherry_planks",
                "bamboo_planks", "bamboo_mosaic", "crimson_planks", "warped_planks",
                "pale_oak_planks"                           // 1.21.4+
        ));

        // ── interactive ─────────────────────────────────────────────────────
        GROUPS.put(BUTTONS, List.of(
                "oak_button", "spruce_button", "birch_button", "jungle_button",
                "acacia_button", "dark_oak_button", "mangrove_button", "cherry_button",
                "bamboo_button", "crimson_button", "warped_button",
                "pale_oak_button",                          // 1.21.4+
                "stone_button", "polished_blackstone_button"
        ));

        GROUPS.put(PRESSURE_PLATES, List.of(
                "oak_pressure_plate", "spruce_pressure_plate", "birch_pressure_plate",
                "jungle_pressure_plate", "acacia_pressure_plate",
                "dark_oak_pressure_plate", "mangrove_pressure_plate",
                "cherry_pressure_plate", "bamboo_pressure_plate",
                "crimson_pressure_plate", "warped_pressure_plate",
                "pale_oak_pressure_plate",                  // 1.21.4+
                "stone_pressure_plate", "polished_blackstone_pressure_plate",
                "light_weighted_pressure_plate", "heavy_weighted_pressure_plate"
        ));

        GROUPS.put(SIGNS, List.of(
                "oak_sign", "spruce_sign", "birch_sign", "jungle_sign",
                "acacia_sign", "dark_oak_sign", "mangrove_sign", "cherry_sign",
                "bamboo_sign", "crimson_sign", "warped_sign",
                "pale_oak_sign"                             // 1.21.4+
        ));

        GROUPS.put(HANGING_SIGNS, List.of(
                "oak_hanging_sign", "spruce_hanging_sign", "birch_hanging_sign",
                "jungle_hanging_sign", "acacia_hanging_sign", "dark_oak_hanging_sign",
                "mangrove_hanging_sign", "cherry_hanging_sign", "bamboo_hanging_sign",
                "crimson_hanging_sign", "warped_hanging_sign",
                "pale_oak_hanging_sign"                     // 1.21.4+
        ));

        // ── dye-coloured groups (generated from the 16-colour list) ─────────
        GROUPS.put(WOOL,                DYES.stream().map(c -> c + "_wool").collect(Collectors.toList()));
        GROUPS.put(CARPETS,             DYES.stream().map(c -> c + "_carpet").collect(Collectors.toList()));
        GROUPS.put(CONCRETE,            DYES.stream().map(c -> c + "_concrete").collect(Collectors.toList()));
        GROUPS.put(CONCRETE_POWDER,     DYES.stream().map(c -> c + "_concrete_powder").collect(Collectors.toList()));
        GROUPS.put(STAINED_GLASS,       DYES.stream().map(c -> c + "_stained_glass").collect(Collectors.toList()));
        GROUPS.put(STAINED_GLASS_PANES, DYES.stream().map(c -> c + "_stained_glass_pane").collect(Collectors.toList()));
        GROUPS.put(TERRACOTTA,          DYES.stream().map(c -> c + "_terracotta").collect(Collectors.toList()));
        GROUPS.put(GLAZED_TERRACOTTA,   DYES.stream().map(c -> c + "_glazed_terracotta").collect(Collectors.toList()));
        GROUPS.put(BEDS,                DYES.stream().map(c -> c + "_bed").collect(Collectors.toList()));
        GROUPS.put(BANNERS,             DYES.stream().map(c -> c + "_banner").collect(Collectors.toList()));
        GROUPS.put(SHULKER_BOXES,
                Stream.concat(Stream.of("shulker_box"), DYES.stream().map(c -> c + "_shulker_box"))
                      .collect(Collectors.toList()));
        GROUPS.put(CANDLES,
                Stream.concat(Stream.of("candle"), DYES.stream().map(c -> c + "_candle"))
                      .collect(Collectors.toList()));

        // ── ores ────────────────────────────────────────────────────────────
        GROUPS.put(ORES, List.of(
                "coal_ore", "iron_ore", "copper_ore", "gold_ore",
                "redstone_ore", "emerald_ore", "lapis_ore", "diamond_ore"
        ));

        GROUPS.put(DEEPSLATE_ORES, List.of(
                "deepslate_coal_ore", "deepslate_iron_ore", "deepslate_copper_ore",
                "deepslate_gold_ore", "deepslate_redstone_ore", "deepslate_emerald_ore",
                "deepslate_lapis_ore", "deepslate_diamond_ore"
        ));

        List<String> allOres = new ArrayList<>(GROUPS.get(ORES));
        allOres.addAll(GROUPS.get(DEEPSLATE_ORES));
        allOres.addAll(List.of("nether_gold_ore", "nether_quartz_ore", "ancient_debris"));
        GROUPS.put(ALL_ORES, Collections.unmodifiableList(allOres));

        // ── nature ──────────────────────────────────────────────────────────
        GROUPS.put(SAPLINGS, List.of(
                "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling",
                "acacia_sapling", "dark_oak_sapling", "mangrove_propagule",
                "cherry_sapling", "azalea", "flowering_azalea",
                "pale_oak_sapling"                          // 1.21.4+
        ));

        GROUPS.put(FLOWERS, List.of(
                "dandelion", "poppy", "blue_orchid", "allium", "azure_bluet",
                "red_tulip", "orange_tulip", "white_tulip", "pink_tulip",
                "oxeye_daisy", "cornflower", "lily_of_the_valley",
                "wither_rose", "sunflower", "lilac", "rose_bush", "peony",
                "spore_blossom", "torchflower", "pitcher_plant", "pink_petals",
                "closed_eyeblossom", "open_eyeblossom"      // 1.21.4+
        ));

        GROUPS.put(CORAL_BLOCKS, List.of(
                "tube_coral_block", "brain_coral_block", "bubble_coral_block",
                "fire_coral_block", "horn_coral_block",
                "dead_tube_coral_block", "dead_brain_coral_block",
                "dead_bubble_coral_block", "dead_fire_coral_block", "dead_horn_coral_block"
        ));

        // ── special ─────────────────────────────────────────────────────────
        GROUPS.put(COPPER_BLOCKS, List.of(
                "copper_block", "exposed_copper", "weathered_copper", "oxidized_copper",
                "waxed_copper_block", "waxed_exposed_copper",
                "waxed_weathered_copper", "waxed_oxidized_copper",
                "cut_copper", "exposed_cut_copper", "weathered_cut_copper", "oxidized_cut_copper",
                "waxed_cut_copper", "waxed_exposed_cut_copper",
                "waxed_weathered_cut_copper", "waxed_oxidized_cut_copper",
                "copper_bulb", "exposed_copper_bulb", "weathered_copper_bulb",
                "oxidized_copper_bulb", "waxed_copper_bulb", "waxed_exposed_copper_bulb",
                "waxed_weathered_copper_bulb", "waxed_oxidized_copper_bulb",
                "copper_grate", "exposed_copper_grate", "weathered_copper_grate",
                "oxidized_copper_grate", "waxed_copper_grate", "waxed_exposed_copper_grate",
                "waxed_weathered_copper_grate", "waxed_oxidized_copper_grate"
        ));

        GROUPS.put(MUSIC_DISCS, List.of(
                "music_disc_13", "music_disc_cat", "music_disc_blocks", "music_disc_chirp",
                "music_disc_far", "music_disc_mall", "music_disc_mellohi", "music_disc_stal",
                "music_disc_strad", "music_disc_ward", "music_disc_11", "music_disc_wait",
                "music_disc_otherside", "music_disc_5", "music_disc_pigstep",
                "music_disc_relic", "music_disc_precipice",
                "music_disc_creator", "music_disc_creator_music_box" // 1.21.4+
        ));

        // ── full blocks ──────────────────────────────────────────────────────
        // Every solid 1×1×1 cube block. Composed from already-defined groups
        // plus blocks that don't belong to any narrower category.
        List<String> fullBlocks = new ArrayList<>();

        // wood families (planks, logs, bark, stripped)
        fullBlocks.addAll(GROUPS.get(PLANKS));
        fullBlocks.addAll(GROUPS.get(LOGS));
        fullBlocks.addAll(GROUPS.get(WOOD));
        fullBlocks.addAll(GROUPS.get(STRIPPED_LOGS));
        fullBlocks.addAll(List.of(                              // stripped bark (no separate group yet)
                "stripped_oak_wood", "stripped_spruce_wood", "stripped_birch_wood",
                "stripped_jungle_wood", "stripped_acacia_wood", "stripped_dark_oak_wood",
                "stripped_mangrove_wood", "stripped_cherry_wood",
                "stripped_crimson_hyphae", "stripped_warped_hyphae",
                "stripped_pale_oak_wood"                        // 1.21.4+
        ));

        // coloured / dyed full blocks
        fullBlocks.addAll(GROUPS.get(WOOL));
        fullBlocks.addAll(GROUPS.get(CONCRETE));
        fullBlocks.addAll(GROUPS.get(CONCRETE_POWDER));
        fullBlocks.addAll(GROUPS.get(TERRACOTTA));
        fullBlocks.addAll(GROUPS.get(GLAZED_TERRACOTTA));
        fullBlocks.addAll(List.of("terracotta"));               // undyed terracotta
        fullBlocks.addAll(List.of(                              // plain + stained glass (full cube, transparent)
                "glass",
                "white_stained_glass", "orange_stained_glass", "magenta_stained_glass",
                "light_blue_stained_glass", "yellow_stained_glass", "lime_stained_glass",
                "pink_stained_glass", "gray_stained_glass", "light_gray_stained_glass",
                "cyan_stained_glass", "purple_stained_glass", "blue_stained_glass",
                "brown_stained_glass", "green_stained_glass", "red_stained_glass",
                "black_stained_glass"
        ));

        // ores
        fullBlocks.addAll(GROUPS.get(ALL_ORES));

        // copper full blocks (cut copper and raw copper blocks; stairs/slabs are excluded)
        fullBlocks.addAll(List.of(
                "copper_block", "exposed_copper", "weathered_copper", "oxidized_copper",
                "waxed_copper_block", "waxed_exposed_copper",
                "waxed_weathered_copper", "waxed_oxidized_copper",
                "cut_copper", "exposed_cut_copper", "weathered_cut_copper", "oxidized_cut_copper",
                "waxed_cut_copper", "waxed_exposed_cut_copper",
                "waxed_weathered_cut_copper", "waxed_oxidized_cut_copper",
                "raw_copper_block", "raw_iron_block", "raw_gold_block"
        ));

        // coral (full cube blocks only, not fans/plants)
        fullBlocks.addAll(GROUPS.get(CORAL_BLOCKS));

        // stone family
        fullBlocks.addAll(List.of(
                "stone", "granite", "polished_granite",
                "diorite", "polished_diorite",
                "andesite", "polished_andesite",
                "cobblestone", "mossy_cobblestone",
                "stone_bricks", "mossy_stone_bricks", "cracked_stone_bricks", "chiseled_stone_bricks",
                "bricks"
        ));

        // deepslate family
        fullBlocks.addAll(List.of(
                "deepslate", "cobbled_deepslate", "polished_deepslate", "chiseled_deepslate",
                "deepslate_bricks", "cracked_deepslate_bricks",
                "deepslate_tiles", "cracked_deepslate_tiles",
                "reinforced_deepslate"
        ));

        // tuff family (1.21+)
        fullBlocks.addAll(List.of(
                "tuff", "polished_tuff", "tuff_bricks", "chiseled_tuff", "chiseled_tuff_bricks"
        ));

        // sandstone family
        fullBlocks.addAll(List.of(
                "sandstone", "chiseled_sandstone", "cut_sandstone", "smooth_sandstone",
                "red_sandstone", "chiseled_red_sandstone", "cut_red_sandstone", "smooth_red_sandstone"
        ));

        // prismarine
        fullBlocks.addAll(List.of(
                "prismarine", "prismarine_bricks", "dark_prismarine", "sea_lantern"
        ));

        // nether family
        fullBlocks.addAll(List.of(
                "netherrack", "soul_sand", "soul_soil",
                "nether_bricks", "cracked_nether_bricks", "chiseled_nether_bricks", "red_nether_bricks",
                "basalt", "polished_basalt", "smooth_basalt",
                "blackstone", "polished_blackstone", "chiseled_polished_blackstone", "gilded_blackstone",
                "polished_blackstone_bricks", "cracked_polished_blackstone_bricks",
                "nether_wart_block", "warped_wart_block",
                "shroomlight", "magma_block", "glowstone"
        ));

        // end family
        fullBlocks.addAll(List.of(
                "end_stone", "end_stone_bricks",
                "purpur_block", "purpur_pillar"
        ));

        // mineral / gem blocks
        fullBlocks.addAll(List.of(
                "coal_block", "iron_block", "gold_block",
                "redstone_block", "lapis_block",
                "diamond_block", "emerald_block",
                "netherite_block", "amethyst_block",
                "quartz_block", "chiseled_quartz_block", "quartz_pillar", "smooth_quartz"
        ));

        // earth / dirt
        fullBlocks.addAll(List.of(
                "dirt", "coarse_dirt", "rooted_dirt", "podzol", "mycelium", "grass_block",
                "mud", "packed_mud", "mud_bricks",
                "clay", "sand", "red_sand", "gravel"
        ));

        // ice / snow
        fullBlocks.addAll(List.of(
                "ice", "packed_ice", "blue_ice", "snow_block"
        ));

        // misc natural full blocks
        fullBlocks.addAll(List.of(
                "obsidian", "crying_obsidian",
                "ancient_debris",
                "calcite", "dripstone_block",
                "moss_block",
                "sculk", "sculk_catalyst",
                "sponge", "wet_sponge",
                "melon", "pumpkin",
                "hay_block", "target",
                "bookshelf", "chiseled_bookshelf"
        ));

        GROUPS.put(FULL_BLOCKS, Collections.unmodifiableList(fullBlocks));
    }

    // ─────────────────────────────── public API ───────────────────────────────

    /**
     * Returns all blocks in the group that exist in the current game version.
     * Unknown IDs (future/removed blocks) are silently skipped.
     */
    public static List<Block> get(String group) {
        return getRawIds(group).stream()
                .filter(id -> Registries.BLOCK.containsId(Identifier.of("minecraft", id)))
                .map(id -> Registries.BLOCK.get(Identifier.of("minecraft", id)))
                .collect(Collectors.toList());
    }

    /**
     * Returns the {@link Identifier}s of all blocks in the group that exist
     * in the current game version.
     */
    public static List<Identifier> getIdentifiers(String group) {
        return getRawIds(group).stream()
                .map(id -> Identifier.of("minecraft", id))
                .filter(Registries.BLOCK::containsId)
                .collect(Collectors.toList());
    }

    /**
     * Returns the raw string IDs for a group exactly as they were registered,
     * without filtering for the current version.
     */
    public static List<String> getRawIds(String group) {
        return GROUPS.getOrDefault(group, List.of());
    }

    /** Returns true if the given bare ID (e.g. {@code "oak_stairs"}) exists in this version. */
    public static boolean exists(String id) {
        return Registries.BLOCK.containsId(Identifier.of("minecraft", id));
    }

    /** Returns an unmodifiable view of all registered group names. */
    public static Set<String> getGroupNames() {
        return Collections.unmodifiableSet(GROUPS.keySet());
    }
}
