package net.abrikoos.blockout;
import net.abrikoos.blockout.client.ClientGameStateV2;
import net.abrikoos.blockout.item.BlockoutModItems;
import net.abrikoos.blockout.networkv2.game.GameStartPacket;
import net.abrikoos.blockout.networkv2.game.GoalBoardUpdatePacket;
import net.abrikoos.blockout.networkv2.game.StructureLocationsPacket;
import net.abrikoos.blockout.networkv2.get.GetGameInfo;
import net.abrikoos.blockout.networkv2.get.GetTeamData;
import net.abrikoos.blockout.networkv2.team.TeamRegV2;
import net.abrikoos.blockout.server.builder.BlockDropChangeBuilder;
import net.abrikoos.blockout.server.builder.BlockSwapBuilder;
import net.abrikoos.blockout.server.goals.GoalItemRegistry;
import net.abrikoos.blockout.client.gui.BlockoutScreens;
import net.abrikoos.blockout.client.gui.screens.ScreenScreen;

import net.abrikoos.blockout.networkv2.compass.PlayersPositionPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.Random;
import java.util.UUID;

import static net.abrikoos.blockout.item.BlockoutModItems.PLAYER_COMPASS;

@Environment(EnvType.CLIENT)
public class BlockoutClient implements ClientModInitializer {

    public static GoalItemRegistry goalItemRegistry = new GoalItemRegistry();

    public static boolean boardopen = false;

    private boolean hasJoinedWorld = false;

    public static KeyBinding boardKey = null;

    @Override
    public void onInitializeClient() {


        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "goal_complete"), SoundEvent.of(Identifier.of("blockout", "goal_complete")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "goal_fail"), SoundEvent.of(Identifier.of("blockout", "goal_fail")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "goal_success"), SoundEvent.of(Identifier.of("blockout", "goal_success")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "finish_countdown"), SoundEvent.of(Identifier.of("blockout", "finish_countdown")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("blockout", "race_start"), SoundEvent.of(Identifier.of("blockout", "race_start")));



//        ClientPlayNetworking.registerGlobalReceiver(BlackoutStartGamePacket.ID, ((payload, context) -> {
//            context.client().execute(() -> {
//                MinecraftClient client = MinecraftClient.getInstance();
//                BlackoutStartGameInfo boardInfo = payload.goalboard();
//                BlockoutScreens.setBoard(boardInfo);
//
//            });
//        }));
//
//        ClientPlayNetworking.registerGlobalReceiver(UnitedTeamRegistry.ID, ((payload, context) -> {
//            context.client().execute(() -> {
//                UnitedTeamRegistry.update(payload);
//            });
//        }));
//
//        ClientPlayNetworking.registerGlobalReceiver(LockoutUpdateBoardPacket.ID, ((payload, context) -> {
//            context.client().execute(() -> {
//                MinecraftClient client = MinecraftClient.getInstance();
//                LockoutUpdateBoardInfo boardInfo = payload.goalboard();
//                assert client.player != null;
//                LockoutUpdateBoardInfo old = ClientGameState.latestUpdate();
//                BlockoutList<UnitedTeamRegistry.Team> teams = ClientGameState.getTeams();
//                int changedGoalIndex = 0;
//                String goalClompleter = "";
//                for (int i = 0; i < boardInfo.goals.length; i++) {
//                    if (!boardInfo.goals[i].equals(old.goals[i])) {
//                        changedGoalIndex = i;
//                        goalClompleter = boardInfo.goals[i];
//                        break;
//                    }
//                }
//                int t1old = ClientGameState.getTeamScore(teams.get(0).teamId());
//                int t2old = ClientGameState.getTeamScore(teams.get(1).teamId());
//                ClientGameState.updateBoard(boardInfo);
//                int t1new = ClientGameState.getTeamScore(teams.get(0).teamId());
//                int t2new = ClientGameState.getTeamScore(teams.get(1).teamId());
//                boolean t1goal = t1new > t1old;
//                boolean t2goal = t2new > t2old;
//                boolean team1 = UnitedTeamRegistry.getTeamPlayerByUUID(client.player.getUuid()).teamIndex == teams.get(0).teamId();
//                boolean team2 = UnitedTeamRegistry.getTeamPlayerByUUID(client.player.getUuid()).teamIndex == teams.get(1).teamId();
//                Text text1 = Text.literal("Goal \"" + ClientGameState.getGoals().get(changedGoalIndex).name + "\" completed by ");
//                Text text2 = Text.literal(UnitedTeamRegistry.getTeamPlayerByUUID(UUID.fromString(goalClompleter)).name).withColor(Colors.get(UnitedTeamRegistry.getTeamPlayerByUUID(UUID.fromString(goalClompleter)).teamIndex));
//                Text text3 = text1.copy().append(text2);
//
//                if (t1goal) {
//                    client.inGameHud.setOverlayMessage(text3, false);
//                    if (team1) {
//                        client.player.playSound(SoundEvent.of(Identifier.of("blockout:goal_success")));
//                    }
//                    else if (team2) {
//                        client.player.playSound(SoundEvent.of(Identifier.of("blockout:goal_fail")));
//                    }
//                }
//                else if (t2goal) {
//                    client.inGameHud.setOverlayMessage(text3, false);
//                    if (team2) {
//                        client.player.playSound(SoundEvent.of(Identifier.of("blockout:goal_success")));
//                    }
//                    else if (team1) {
//                        client.player.playSound(SoundEvent.of(Identifier.of("blockout:goal_fail")));
//                    }
//                }
//                else {
//                    client.player.playSound(SoundEvent.of(Identifier.of("blockout:goal_complete")));
//                }
//
//
//            });
//        }));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && !hasJoinedWorld) {
                hasJoinedWorld = true;
                try {
                    BlockoutLogger.log(client.currentScreen.getTitle().toString());
                    if (client.currentScreen instanceof ScreenScreen) {
                        client.setScreen(null);
                    }
                }
                catch (Exception ignored) {

                }
                ClientPlayNetworking.send(new GetGameInfo());
                ClientPlayNetworking.send(new GetTeamData());
            }
            else if (client.world == null && hasJoinedWorld) {
                BlockoutLogger.log(client.currentScreen != null ? client.currentScreen.getTitle().toString() : "no screen");
                hasJoinedWorld = false;
            }
        });


//        ClientPlayNetworking.registerGlobalReceiver(LockoutStartGamePacket.ID, ((payload, context) -> {
//            context.client().execute(() -> {
//                ClientGameState.startLockout(payload.info());
//
//            });
//        }));

        boardKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open Blockout Board",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "Blockout"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (boardKey.wasPressed()) {
                if (client.currentScreen instanceof ScreenScreen) {
                    client.setScreen(null);
                    return;
                }
                if (client.currentScreen == null) {
                    MinecraftClient.getInstance().setScreen(BlockoutScreens.completeFullScreen);

                }

            }
        });

        ClientPlayNetworking.registerGlobalReceiver(PlayersPositionPacket.ID, (payload, context) -> {
//            CompassesWidget.positions = payload.positions();
        });

        ClientPlayNetworking.registerGlobalReceiver(GameStartPacket.ID, (packet, context) ->
            ClientGameStateV2.startGame(packet)
        );

        ClientPlayNetworking.registerGlobalReceiver(GoalBoardUpdatePacket.ID, (packet, context) ->
            ClientGameStateV2.updateBoard(packet)
        );

        ClientPlayNetworking.registerGlobalReceiver(TeamRegV2.ID, (packet, context) ->
            ClientGameStateV2.updateTeamReg(packet)
        );

        ClientPlayNetworking.registerGlobalReceiver(StructureLocationsPacket.ID, (packet, context) ->
            ClientGameStateV2.structures = packet
        );













        ModelPredicateProviderRegistry.register(BlockoutModItems.PLAYER_TRACKING_COMPASS, Identifier.of("blockout", "angle"), new ClampedModelPredicateProvider() {

            private static int wobble_direction = 1;
            private static Random random = new Random();
            private static float wobble_angle = 0.0F;

            @Override
            public float unclampedCall(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed) {
                if (entity == null || world == null) {
                return wobble();
            }
            float val =  getAngle(stack, world, entity);
            return val;
            }


            private static float getAngle(ItemStack stack, World world, LivingEntity entity) {

                if (!(entity instanceof PlayerEntity)) {
                    return wobble();
                }
                String target = stack.get(PLAYER_COMPASS);
                if (target == null) {
                    return wobble();
                }

                PlayerEntity targetPlayer = world.getPlayerByUuid(UUID.fromString(target));
                if (targetPlayer == null || targetPlayer.isSpectator() || targetPlayer.isDead() || targetPlayer.isInvisible() || targetPlayer.getWorld().getRegistryKey() != ClientGameStateV2.client.world.getRegistryKey()) {
                    return wobble();
                }

                double targetAngle = calculateAngleToPlayer((PlayerEntity) entity, targetPlayer);
                double rotation = entity.getYaw() % 360.0D;
                double adjusted = Math.PI - ((rotation - 90.0D) * 0.01745329238474369D - targetAngle);

                return MathHelper.floorMod((float) (adjusted / (Math.PI * 2D)), 1.0F);// * 360) / 10) *10.0F;

            }

            private static double calculateAngleToPlayer(PlayerEntity player, PlayerEntity target) {
                double deltaX = target.getX() - player.getX();
                double deltaZ = target.getZ() - player.getZ();
                double angle = Math.atan2(deltaZ, deltaX);
                return angle;
            }

            private static float wobble() {
                if (random.nextDouble() < 0.002) {
                    wobble_direction = -wobble_direction;
                }
                wobble_angle += 0.005F * wobble_direction;
                return wobble_angle % 1.0F ;
            }

        });

//        ClientPlayNetworking.registerGlobalReceiver(GameStartPacket.ID, (packet, context) -> {
//            game = new LockoutGame(packet);
//        });
//
//        ClientPlayNetworking.registerGlobalReceiver(GoalBoardUpdatePacket.ID, (packet, context) -> {
//            game.updateBoard(packet);
//        });
//
//        ClientPlayNetworking.registerGlobalReceiver(TeamRegV2.ID, (packet, context) -> {
//            game.updateTeams(packet);
//        });
    }
}

