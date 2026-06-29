package net.abrikoos.blockout;

import net.abrikoos.blockout.client.ClientGameStateV2;
import net.abrikoos.blockout.client.gui.BlockoutScreens;
import net.abrikoos.blockout.client.gui.screens.ScreenScreen;
import net.abrikoos.blockout.item.BlockoutModItems;
import net.abrikoos.blockout.network.NetworkBridge;
import net.abrikoos.blockout.networkv2.get.GetGameInfo;
import net.abrikoos.blockout.networkv2.get.GetTeamData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import static net.abrikoos.blockout.item.BlockoutModItems.PLAYER_COMPASS;

@Environment(EnvType.CLIENT)
public class BlockoutClient {

    private static Supplier<Boolean> boardKeyChecker = () -> false;
    private static BiPredicate<Integer, Integer> boardKeyMatcher = (k, s) -> false;
    private static boolean hasJoinedWorld = false;

    private static int compassWobbleDirection = 1;
    private static final Random compassRandom = new Random();
    private static float compassWobbleAngle = 0.0F;

    public static void setBoardKeyChecker(Supplier<Boolean> checker) {
        boardKeyChecker = checker;
    }

    public static void setBoardKeyMatcher(BiPredicate<Integer, Integer> matcher) {
        boardKeyMatcher = matcher;
    }

    public static boolean boardKeyMatches(int keyCode, int scanCode) {
        return boardKeyMatcher.test(keyCode, scanCode);
    }

    public static void init() {
        ModelPredicateProviderRegistry.register(
            BlockoutModItems.PLAYER_TRACKING_COMPASS,
            Identifier.of("blockout", "angle"),
            new ClampedModelPredicateProvider() {
                @Override
                public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
                    if (entity == null || world == null) return compassWobble();
                    return getCompassAngle(stack, world, entity);
                }
            }
        );
    }

    private static float getCompassAngle(ItemStack stack, World world, LivingEntity entity) {
        if (!(entity instanceof PlayerEntity player)) return compassWobble();
        String target = stack.get(PLAYER_COMPASS);
        if (target == null) return compassWobble();

        PlayerEntity targetPlayer = world.getPlayerByUuid(UUID.fromString(target));
        if (targetPlayer == null || targetPlayer.isSpectator() || targetPlayer.isDead() || targetPlayer.isInvisible()
                || targetPlayer.getWorld().getRegistryKey() != ClientGameStateV2.client.world.getRegistryKey()) {
            return compassWobble();
        }

        double deltaX = targetPlayer.getX() - player.getX();
        double deltaZ = targetPlayer.getZ() - player.getZ();
        double targetAngle = Math.atan2(deltaZ, deltaX);
        double rotation = entity.getYaw() % 360.0D;
        double adjusted = Math.PI - ((rotation - 90.0D) * 0.01745329238474369D - targetAngle);
        return MathHelper.floorMod((float) (adjusted / (Math.PI * 2D)), 1.0F);
    }

    private static float compassWobble() {
        if (compassRandom.nextDouble() < 0.002) {
            compassWobbleDirection = -compassWobbleDirection;
        }
        compassWobbleAngle += 0.005F * compassWobbleDirection;
        return compassWobbleAngle % 1.0F;
    }

    public static void onClientTick(MinecraftClient client) {
        if (client.world != null && !hasJoinedWorld) {
            hasJoinedWorld = true;
            try {
                BlockoutLogger.log(client.currentScreen.getTitle().toString());
                if (client.currentScreen instanceof ScreenScreen) {
                    client.setScreen(null);
                }
            } catch (Exception ignored) {}
            NetworkBridge.sendToServer(new GetGameInfo());
            NetworkBridge.sendToServer(new GetTeamData());
        } else if (client.world == null && hasJoinedWorld) {
            BlockoutLogger.log(client.currentScreen != null ? client.currentScreen.getTitle().toString() : "no screen");
            hasJoinedWorld = false;
        }

        if (boardKeyChecker.get()) {
            if (client.currentScreen instanceof ScreenScreen) {
                client.setScreen(null);
                return;
            }
            if (client.currentScreen == null) {
                MinecraftClient.getInstance().setScreen(BlockoutScreens.completeFullScreen);
            }
        }
    }
}
