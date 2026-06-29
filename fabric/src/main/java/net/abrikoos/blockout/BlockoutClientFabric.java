package net.abrikoos.blockout;

import net.abrikoos.blockout.client.ClientGameStateV2;
import net.abrikoos.blockout.network.NetworkBridge;
import net.abrikoos.blockout.networkv2.compass.PlayersPositionPacket;
import net.abrikoos.blockout.networkv2.game.GameStartPacket;
import net.abrikoos.blockout.networkv2.game.GoalBoardUpdatePacket;
import net.abrikoos.blockout.networkv2.game.StructureLocationsPacket;
import net.abrikoos.blockout.networkv2.team.TeamRegV2;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class BlockoutClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Wire up client-to-server sender
        NetworkBridge.setToServer(payload -> ClientPlayNetworking.send(payload));

        // Register key binding
        KeyBinding boardKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Open Blockout Board",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "Blockout"
        ));

        // Register S2C receivers (payload types already registered in BlockoutFabric)
        ClientPlayNetworking.registerGlobalReceiver(PlayersPositionPacket.ID,
            (payload, ctx) -> { /* positions unused */ });

        ClientPlayNetworking.registerGlobalReceiver(GameStartPacket.ID,
            (packet, ctx) -> ctx.client().execute(() -> ClientGameStateV2.startGame(packet)));

        ClientPlayNetworking.registerGlobalReceiver(GoalBoardUpdatePacket.ID,
            (packet, ctx) -> ctx.client().execute(() -> ClientGameStateV2.updateBoard(packet)));

        ClientPlayNetworking.registerGlobalReceiver(TeamRegV2.ID,
            (packet, ctx) -> ctx.client().execute(() -> ClientGameStateV2.updateTeamReg(packet)));

        ClientPlayNetworking.registerGlobalReceiver(StructureLocationsPacket.ID,
            (packet, ctx) -> ClientGameStateV2.structures = packet);

        BlockoutClient.init();
        BlockoutClient.setBoardKeyChecker(boardKey::wasPressed);
        BlockoutClient.setBoardKeyMatcher(boardKey::matchesKey);
    }
}
