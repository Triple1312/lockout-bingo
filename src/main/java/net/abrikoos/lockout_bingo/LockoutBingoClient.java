package net.abrikoos.lockout_bingo;

import net.abrikoos.lockout_bingo.goals.GoalItemRegistry;
import net.abrikoos.lockout_bingo.gui.LockoutScreens;
import net.abrikoos.lockout_bingo.gui.screens.ScreenScreen;
import net.abrikoos.lockout_bingo.gui.widget.CompassesWidget;
import net.abrikoos.lockout_bingo.item.CustomCompassRenderer;
import net.abrikoos.lockout_bingo.item.LockoutModItems;
import net.abrikoos.lockout_bingo.listeners.TickListener;
import net.abrikoos.lockout_bingo.network.compass.PlayersPositionPacket;
import net.abrikoos.lockout_bingo.network.game.*;
import net.abrikoos.lockout_bingo.network.team.AllTeamsPacket;
import net.abrikoos.lockout_bingo.team.PlayerTeamRegistry;
import net.abrikoos.lockout_bingo.team.TeamController;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.mixin.event.lifecycle.client.ClientWorldMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.WorldEvents;
import org.lwjgl.glfw.GLFW;

public class LockoutBingoClient implements ClientModInitializer {

    public static GoalItemRegistry goalItemRegistry = new GoalItemRegistry();

    public static boolean boardopen = false;

    private boolean hasJoinedWorld = false;

    @Override
    public void onInitializeClient() {


        Registry.register(Registries.SOUND_EVENT, Identifier.of("lockout-bingo", "goal_complete"), SoundEvent.of(Identifier.of("lockout-bingo", "goal_complete")));

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
                client.player.playSound(SoundEvent.of(Identifier.of("lockout-bingo:goal_complete")));
                LockoutScreens.updateBoard(boardInfo);
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
                MinecraftClient client = MinecraftClient.getInstance();
                client.setScreen(new ChatScreen("Lockout Bingo Goals"));
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
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "Lockout Bingo"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openBoardScreenKeyBind.isPressed()) {
                if (!boardopen) {
                    boardopen = LockoutScreens.open();
                } else {
                    boardopen = !LockoutScreens.close();
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(PlayersPositionPacket.ID, (payload, context) -> {
            CompassesWidget.positions = payload.positions();
        });

        BuiltinItemRendererRegistry.INSTANCE.register(LockoutModItems.PLAYER_TRACKING_COMPASS, new CustomCompassRenderer());



    }
}

