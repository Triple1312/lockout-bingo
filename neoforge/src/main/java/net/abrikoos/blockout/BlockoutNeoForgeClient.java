package net.abrikoos.blockout;

import com.mojang.blaze3d.platform.InputConstants;
import net.abrikoos.blockout.network.NetworkBridge;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class BlockoutNeoForgeClient {

    private static KeyMapping boardKey;

    public static void init() {
        boardKey = new KeyMapping(
            "Open Blockout Board",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "Blockout"
        );

        NeoForge.EVENT_BUS.addListener(BlockoutNeoForgeClient::onRegisterKeyMappings);
        BlockoutClient.init();
        BlockoutClient.setBoardKeyChecker(boardKey::consumeClick);
        BlockoutClient.setBoardKeyMatcher(boardKey::matches);
    }

    private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(boardKey);
    }
}
