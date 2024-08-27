package net.abrikoos.lockout_bingo.gui.widget;

import net.abrikoos.lockout_bingo.network.compass.CompassPlayerPosition;
import net.abrikoos.lockout_bingo.team.Colors;
import net.abrikoos.lockout_bingo.team.PlayerTeamRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import java.util.ArrayList;
import java.util.List;

public class CompassesWidget {

    private static boolean open = true;

    public static List<CompassPlayerPosition> positions = new ArrayList<>();

    public static void drawHud(DrawContext context) {
        if (!open) {
            return;
        }
        int screensizex = context.getScaledWindowWidth();
        int screensizey = context.getScaledWindowHeight();
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        int index = 0;
        for (CompassPlayerPosition p : positions) {
            if (p.uuid.equals(client.player.getUuidAsString())) {
                continue;
            }
            context.getMatrices().push();
            context.getMatrices().translate(8, 10 + index * 20, 5 + index * 10);
            context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotation((float) ( Math.atan2(p.z - client.player.getZ(), p.x - client.player.getX()) - (1 +client.player.headYaw/180) * 3.1416 )));
            context.drawTexture(Identifier.of("lockout-bingo:compass.png"), -6, -6, 0, 0, 12, 12, 12, 12);
            context.getMatrices().pop();
            try {
                context.drawText(client.textRenderer, PlayerTeamRegistry.getPlayerByUUID(p.uuid).getName(), 15, 5 + index * 20, Colors.getPlayerColor(p.uuid), true);
            }
            catch (Exception e) {
                context.drawText(client.textRenderer, "Unknown", 15, 5 + index * 20, 0xffffff, true);
            }
            index++;
        }
    }

    public static void toggle() {
        open = !open;
    }








}
