package net.abrikoos.lockout_bingo.item;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class LockoutModItems {

    public static final ComponentType<String> PLAYER_COMPASS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lockout-bingo", "player_compass_name"),
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );

    public static final ComponentType<LodestoneTrackerComponent> PLAYER_COMPASS_LOCATION = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lockout-bingo", "player_compass_location"),
            ComponentType.<LodestoneTrackerComponent>builder().codec(LodestoneTrackerComponent.CODEC).build()
    );

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of("lockout-bingo", id);
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static final Item PLAYER_TRACKING_COMPASS = register(new PlayerTrackingCompass(new Item.Settings().maxCount(1)), "player_tracking_compass");

    public static void initialize() {

    }


}
