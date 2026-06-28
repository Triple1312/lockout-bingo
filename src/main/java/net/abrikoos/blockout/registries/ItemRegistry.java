package net.abrikoos.blockout.registries;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

import static net.abrikoos.blockout.registries.ItemGroups.*;

/**
 * Registry of item groups keyed by the constants in {@link ItemGroups}.
 * <p>
 * All item IDs are stored as bare strings (no namespace) and assumed to be in
 * the {@code minecraft:} namespace. IDs that do not exist in the running game
 * version are silently dropped by {@link #get} and {@link #getIdentifiers}.
 */
public final class ItemRegistry {

    private ItemRegistry() {}

    private static final Map<String, List<String>> GROUPS = new LinkedHashMap<>();

    static {
        GROUPS.put(SEEDS, List.of(
                "wheat_seeds",
                "beetroot_seeds",
                "melon_seeds",
                "pumpkin_seeds",
                "torchflower_seeds",   // 1.20+
                "pitcher_pod"          // 1.20+
        ));

        GROUPS.put(WOODEN_TOOLS,   List.of("wooden_axe",   "wooden_hoe",   "wooden_pickaxe",   "wooden_shovel",   "wooden_sword"));
        GROUPS.put(STONE_TOOLS,    List.of("stone_axe",    "stone_hoe",    "stone_pickaxe",    "stone_shovel",    "stone_sword"));
        GROUPS.put(IRON_TOOLS,     List.of("iron_axe",     "iron_hoe",     "iron_pickaxe",     "iron_shovel",     "iron_sword"));
        GROUPS.put(GOLDEN_TOOLS,   List.of("golden_axe",   "golden_hoe",   "golden_pickaxe",   "golden_shovel",   "golden_sword"));
        GROUPS.put(DIAMOND_TOOLS,  List.of("diamond_axe",  "diamond_hoe",  "diamond_pickaxe",  "diamond_shovel",  "diamond_sword"));
        GROUPS.put(NETHERITE_TOOLS, List.of("netherite_axe", "netherite_hoe", "netherite_pickaxe", "netherite_shovel", "netherite_sword"));
    }

    // ─────────────────────────────── public API ───────────────────────────────

    /**
     * Returns all items in the group that exist in the current game version.
     * Unknown IDs are silently skipped.
     */
    public static List<Item> get(String group) {
        return getRawIds(group).stream()
                .filter(id -> Registries.ITEM.containsId(Identifier.of("minecraft", id)))
                .map(id -> Registries.ITEM.get(Identifier.of("minecraft", id)))
                .collect(Collectors.toList());
    }

    /**
     * Returns the {@link Identifier}s of all items in the group that exist
     * in the current game version.
     */
    public static List<Identifier> getIdentifiers(String group) {
        return getRawIds(group).stream()
                .map(id -> Identifier.of("minecraft", id))
                .filter(Registries.ITEM::containsId)
                .collect(Collectors.toList());
    }

    /**
     * Returns the raw string IDs for a group without filtering for the current version.
     */
    public static List<String> getRawIds(String group) {
        return GROUPS.getOrDefault(group, List.of());
    }

    /** Returns true if the given bare ID (e.g. {@code "wheat_seeds"}) exists in this version. */
    public static boolean exists(String id) {
        return Registries.ITEM.containsId(Identifier.of("minecraft", id));
    }

    /** Returns an unmodifiable view of all registered group names. */
    public static Set<String> getGroupNames() {
        return Collections.unmodifiableSet(GROUPS.keySet());
    }
}
