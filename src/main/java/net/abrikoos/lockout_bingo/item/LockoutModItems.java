package net.abrikoos.lockout_bingo.item;

import com.mojang.serialization.Codec;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class LockoutModItems {

    static float angle = 0.0F;

    public static final ComponentType<String> PLAYER_COMPASS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lockout-bingo", "player_compass_uuid"),
            ComponentType.<String>builder().codec(Codec.STRING).build()
    );

    public static final ComponentType<Float> PLAYER_COMPASS_ANGLE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of("lockout-bingo", "player_compass_angle"),
            ComponentType.<Float>builder().codec(Codec.FLOAT).build()
    );



//    public static final ComponentType<LodestoneTrackerComponent> PLAYER_COMPASS_LOCATION = Registry.register(
//            Registries.DATA_COMPONENT_TYPE,
//            Identifier.of("lockout-bingo", "player_compass_location"),
//            ComponentType.<LodestoneTrackerComponent>builder().codec(LodestoneTrackerComponent.CODEC).build()
//    );

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of("lockout-bingo", id);
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static final Item PLAYER_TRACKING_COMPASS = new Compass2(new Item.Settings().maxCount(1));
    public static final Item CUSTOM_RENDERED_COMPASS = new CustomRenderedCompass();

    public static void initialize() {
        register(PLAYER_TRACKING_COMPASS, "player_tracking_compass");
        register(CUSTOM_RENDERED_COMPASS, "custom_rendered_compass");

    }



}
