package net.abrikoos.lockout_bingo.item;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.server.gamestate.GameState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class Compass2 extends CompassItem {
    public Compass2(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        String target = stack.get(LockoutModItems.PLAYER_COMPASS);
        if (target == null) {
            return Text.of("No target");
        }
        if (GameState.teamRegistry == null) {
            try {
                return Text.of("Tracking: " + ClientGameStateV2.teamReg.getPlayerDataByUUID(target).name);
            } catch (Exception e) {
                return Text.of("Tracking: " + target + "dushadjlksak");
            }
        }
        else {
            try {
                return Text.of("Tracking: " + GameState.teamRegistry.getPlayerDataByUUID(target).name);
            } catch (Exception e) {
                return Text.of("Tracking: " + target + "dushadjlksak");
            }
        }
    }

    private void cycleTarget(ItemStack stack, PlayerEntity user, World world) {
        List<String> playerUUIDs = world.getPlayers().stream()
                .filter(p -> p != user && !p.isSpectator())
                .map(PlayerEntity::getUuid).map(UUID::toString)
                .toList();
        if (playerUUIDs.isEmpty()) {
            stack.set(LockoutModItems.PLAYER_COMPASS, null);
            return;
        }

        String currentTarget = stack.get(LockoutModItems.PLAYER_COMPASS);
        int index = -1;
        if (currentTarget != null) {
            index = playerUUIDs.indexOf(currentTarget);
        }
        index = (index + 1) % playerUUIDs.size();
        stack.set(LockoutModItems.PLAYER_COMPASS, playerUUIDs.get(index));
    }

    @Override
    public boolean hasGlint(ItemStack stack) { // todo could maybe do somthing more
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            cycleTarget(stack, user, world);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world instanceof ServerWorld serverWorld) {
            String tracked__uuid = stack.get(LockoutModItems.PLAYER_COMPASS);
            if (tracked__uuid == null) {
                List<String> playerUUIDs = world.getPlayers().stream()
                        .filter(p -> p != entity && !p.isSpectator())
                        .map(PlayerEntity::getUuid).map(UUID::toString)
                        .toList();
                if (playerUUIDs.isEmpty()) {
                    return;
                }
                cycleTarget(stack, (PlayerEntity) entity, world);
                tracked__uuid = stack.get(LockoutModItems.PLAYER_COMPASS);
            }
            try {
                if (ClientGameStateV2.teamReg.isPlayerConnected(tracked__uuid)) {
                    cycleTarget(stack, (PlayerEntity) entity, world);
                }
            }
            catch (Exception ignored) {
                LockoutLogger.log("");
            }

            try {
                String finalTracked__uuid = tracked__uuid;
                ServerPlayerEntity player = serverWorld.getPlayers().stream().filter(p -> p.getUuidAsString().equals(finalTracked__uuid)).toList().getFirst();
                RegistryKey<World> dimension = player.getWorld().getRegistryKey();
                BlockPos pos = player.getBlockPos();
                LodestoneTrackerComponent l2 = new LodestoneTrackerComponent(Optional.of(GlobalPos.create(dimension, pos)), true);
                stack.set(DataComponentTypes.LODESTONE_TRACKER, l2);
            }
            catch (Exception ignored) {

            }
        }
    }
}
