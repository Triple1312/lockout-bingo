package net.abrikoos.lockout_bingo.item;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PlayerTrackingCompass extends Item {

    public PlayerTrackingCompass(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            super.inventoryTick(stack, world, entity, slot, selected);
            return;
        }
        PlayerEntity player = null;
        String playername = stack.get(LockoutModItems.PLAYER_COMPASS);
        for (PlayerEntity p : world.getPlayers()) {
            if (p.getNameForScoreboard().equals(playername)) {
                player = p;
                break;
            }
        }
        if (player != null) {
            stack.set(DataComponentTypes.LODESTONE_TRACKER, new LodestoneTrackerComponent(Optional.of(GlobalPos.create(player.getEntityWorld().getRegistryKey(), player.getBlockPos())), true));
            LockoutLogger.log(Objects.requireNonNull(stack.get(DataComponentTypes.LODESTONE_TRACKER)).forWorld((ServerWorld) world).toString());
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            // Handle the player switching logic on the server side
            PlayerEntity serverPlayer = (ServerPlayerEntity) user;
            List<PlayerEntity> players = (List<PlayerEntity>) world.getPlayers();
            players.remove(serverPlayer); // Remove the current player from the list

            if (!players.isEmpty()) {
                String currentName = stack.get(LockoutModItems.PLAYER_COMPASS);
                int currentIndex = 0;
                if (currentName == null) {
                    stack.set(LockoutModItems.PLAYER_COMPASS, players.get(0).getNameForScoreboard());
                } else {

                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getUuidAsString().equals(currentName)) {
                            currentIndex = i;
                            break;
                        }
                    }
                }
                if (currentIndex == players.size() - 1) {
                    PlayerEntity player = players.get(0);
                    stack.set(LockoutModItems.PLAYER_COMPASS, player.getNameForScoreboard());
                    stack.set(DataComponentTypes.LODESTONE_TRACKER, new LodestoneTrackerComponent(Optional.of(GlobalPos.create(player.getEntityWorld().getRegistryKey(), player.getBlockPos())), true));
                } else {
                    PlayerEntity player = players.get(currentIndex + 1);
                    stack.set(DataComponentTypes.LODESTONE_TRACKER, new LodestoneTrackerComponent(Optional.of(GlobalPos.create(player.getEntityWorld().getRegistryKey(), player.getBlockPos())), true));
                    stack.set(LockoutModItems.PLAYER_COMPASS, player.getNameForScoreboard());
                }
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.of("Tracking: " + stack.get(LockoutModItems.PLAYER_COMPASS)));
            }
        }
        return TypedActionResult.success(stack, true);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

}
