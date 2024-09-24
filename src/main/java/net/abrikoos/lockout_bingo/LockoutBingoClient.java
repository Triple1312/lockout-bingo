package net.abrikoos.lockout_bingo;

import net.abrikoos.lockout_bingo.client.ClientGameState;
import net.abrikoos.lockout_bingo.item.LockoutModItems;
import net.abrikoos.lockout_bingo.server.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.client.gui.LockoutScreens;
import net.abrikoos.lockout_bingo.client.gui.screens.ScreenScreen;
import net.abrikoos.lockout_bingo.client.gui.widget.CompassesWidget;
import net.abrikoos.lockout_bingo.client.modes.LockoutGame;
import net.abrikoos.lockout_bingo.network.compass.PlayersPositionPacket;
import net.abrikoos.lockout_bingo.network.game.*;
import net.abrikoos.lockout_bingo.network.team.AllTeamsPacket;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.abrikoos.lockout_bingo.team.PlayerTeamRegistry;
import net.abrikoos.lockout_bingo.team.TeamController;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
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

import java.util.List;
import java.util.UUID;

import static net.abrikoos.lockout_bingo.item.LockoutModItems.PLAYER_COMPASS;

public class LockoutBingoClient implements ClientModInitializer {

    public static GoalItemRegistry goalItemRegistry = new GoalItemRegistry();

    public static boolean boardopen = false;

    private boolean hasJoinedWorld = false;

    LockoutGame game;

    @Override
    public void onInitializeClient() {


        Registry.register(Registries.SOUND_EVENT, Identifier.of("lockout-bingo", "goal_complete"), SoundEvent.of(Identifier.of("lockout-bingo", "goal_complete")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("lockout-bingo", "goal_fail"), SoundEvent.of(Identifier.of("lockout-bingo", "goal_fail")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("lockout-bingo", "goal_success"), SoundEvent.of(Identifier.of("lockout-bingo", "goal_success")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("lockout-bingo", "finish_countdown"), SoundEvent.of(Identifier.of("lockout-bingo", "finish_countdown")));
        Registry.register(Registries.SOUND_EVENT, Identifier.of("lockout-bingo", "race_start"), SoundEvent.of(Identifier.of("lockout-bingo", "race_start")));



        ClientPlayNetworking.registerGlobalReceiver(BlackoutStartGamePacket.ID, ((payload, context) -> {
            context.client().execute(() -> {
                MinecraftClient client = MinecraftClient.getInstance();
                BlackoutStartGameInfo boardInfo = payload.goalboard();
                LockoutScreens.setBoard(boardInfo);

            });
        }));

        ClientPlayNetworking.registerGlobalReceiver(LockoutUpdateBoardPacket.ID, ((payload, context) -> {
            context.client().execute(() -> {
                MinecraftClient client = MinecraftClient.getInstance();
                LockoutUpdateBoardInfo boardInfo = payload.goalboard();
                assert client.player != null;
                LockoutUpdateBoardInfo old = ClientGameState.latestUpdate();
                List<LockoutTeam> teams = ClientGameState.getTeams();
                int t1old = ClientGameState.getTeamScore(teams.get(0).teamId);
                int t2old = ClientGameState.getTeamScore(teams.get(1).teamId);
                ClientGameState.updateBoard(boardInfo);
                int t1new = ClientGameState.getTeamScore(teams.get(0).teamId);
                int t2new = ClientGameState.getTeamScore(teams.get(1).teamId);
                boolean t1goal = t1new > t1old;
                boolean t2goal = t2new > t2old;
                boolean team1 = PlayerTeamRegistry.getTeamIndex(client.player.getUuidAsString()) == teams.get(0).teamId;
                boolean team2 = PlayerTeamRegistry.getTeamIndex(client.player.getUuidAsString()) == teams.get(1).teamId;
                if (t1goal) {
                    if (team1) {
                        client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_success")));
                    }
                    else if (team2) {
                        client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_fail")));
                    }
                }
                else if (t2goal) {
                    if (team2) {
                        client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_success")));
                    }
                    else if (team1) {
                        client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_fail")));
                    }
                }
                else {
                    client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_complete")));
                }
            });
        }));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && !hasJoinedWorld) {
                hasJoinedWorld = true;
                LockoutLogger.log(client.currentScreen.getTitle().toString());
                if (client.currentScreen instanceof ScreenScreen) {
                    client.setScreen(null);
                }
            }
            else if (client.world == null && hasJoinedWorld) {
                LockoutLogger.log(client.currentScreen.getTitle().toString());
                hasJoinedWorld = false;
            }
        });


        ClientPlayNetworking.registerGlobalReceiver(LockoutStartGamePacket.ID, ((payload, context) -> {
            context.client().execute(() -> {
                ClientGameState.startLockout(payload.info());

            });
        }));

        ClientPlayNetworking.registerGlobalReceiver(AllTeamsPacket.ID,  ((payload, context) -> {
            context.client().execute(() -> {
                MinecraftClient client = MinecraftClient.getInstance();
                TeamController.setAllTeams(payload.teams());

//                LockoutScreens.setTeams(payload.teamnames()); // todo
            });
        }));

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            PlayerTeamRegistry.updatePlayers();
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            PlayerTeamRegistry.updatePlayers();
        });


        KeyBinding openBoardScreenKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open Lockout Board",
                InputUtil.Type.SCANCODE,
                GLFW.GLFW_KEY_B,
                "Lockout Bingo"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openBoardScreenKeyBind.wasPressed()) {
                MinecraftClient.getInstance().setScreen(LockoutScreens.completeFullScreen);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(PlayersPositionPacket.ID, (payload, context) -> {
            CompassesWidget.positions = payload.positions();
        });

        ModelPredicateProviderRegistry.register(LockoutModItems.PLAYER_TRACKING_COMPASS, Identifier.of("lockout-bingo", "angle"), new ClampedModelPredicateProvider() {
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
                if (targetPlayer == null || targetPlayer.isSpectator() || targetPlayer.isDead() || targetPlayer.isInvisible()) {
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
                return 0.0F;
            }

        });
    }
}

