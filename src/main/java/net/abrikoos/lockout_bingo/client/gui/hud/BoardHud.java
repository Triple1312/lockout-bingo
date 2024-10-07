package net.abrikoos.lockout_bingo.client.gui.hud;

import net.abrikoos.lockout_bingo.client.ClientGameState;
import net.abrikoos.lockout_bingo.client.gui.LockoutUtils;
import net.abrikoos.lockout_bingo.client.gui.widget.CompassesWidget;
import net.abrikoos.lockout_bingo.team.Colors;
import net.abrikoos.lockout_bingo.team.PlayerTeamRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class BoardHud {

    public static void drawHud(@NotNull DrawContext context, float delta) {
        if (ClientGameState.isGameRunning()) {
            MinecraftClient client = MinecraftClient.getInstance();
            int screensizex = context.getScaledWindowWidth();
            int screensizey = context.getScaledWindowHeight();
            int goalwidthheight = (int) (screensizey * 0.06);
            int goalpadding = (int) (screensizey * 0.01);
            int topX = screensizex - 5 * (goalwidthheight + goalpadding) - goalpadding;
            int topY = 5;

            int t1 = 0;
            int t2 = 0;

            for (int i = 0; i < ClientGameState.getGoals().size(); i++) {
                int x = i % 5;
                int y = i / 5;
                int goalTopX = topX + x * (goalwidthheight + goalpadding);
                int goalTopY = topY + y * (goalwidthheight + goalpadding);
                int color;
                if (ClientGameState.latestUpdate() == null) {
                    color = Colors.get(0);
                }
                else {
                    color = Colors.getPlayerColor(ClientGameState.latestUpdate().goals[i]);
                }
                context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, color - 0x47000000);
                ClientGameState.getGoals().get(i).draw(context, delta/3, goalTopX, goalTopY, goalwidthheight, goalwidthheight);
                if (ClientGameState.latestUpdate() == null) {
                    continue;
                }
                try {
                    if (ClientGameState.latestUpdate().goals[i] == null || ClientGameState.latestUpdate().goals[i].equals("00000000-0000-0000-0000-000000000000")) {

                    }
                    else if (PlayerTeamRegistry.getPlayerByUUID(ClientGameState.latestUpdate().goals[i]).teamIndex == ClientGameState.getTeams().get(0).teamId) {
                        t1++;
                    }
                    else if (PlayerTeamRegistry.getPlayerByUUID(ClientGameState.latestUpdate().goals[i]).teamIndex == ClientGameState.getTeams().get(1).teamId) {
                        t2++;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace(
                    );
                }

            }

            int bottombarY = topY + 5 *(goalwidthheight + goalpadding);

            context.fill(topX, bottombarY, topX + goalwidthheight * 2 + goalpadding, bottombarY + goalwidthheight/2, Colors.get(0) - 0x47000000);
            context.fill(topX + 2*(goalwidthheight + goalpadding), bottombarY, topX + goalwidthheight * 5 + goalpadding*4, bottombarY + goalwidthheight/2, Colors.get(0) - 0x47000000);
            String timeString = "";
            long time = ClientGameState.getPlayTime();
            if (time > 3600000) { // hours
                timeString += time / 3600000 + ":";
            }
            if (time > 60000) { // minutes
                timeString += time / 60000 + ":";
            }
            timeString += time / 1000 % 60; // seconds
            LockoutUtils.drawCenteredText(context, client.textRenderer, timeString,topX + (goalwidthheight * 3 + goalpadding * 3 + goalwidthheight/2),bottombarY + goalwidthheight/4, 0xffffffff, false  );

//            context.drawTextWithBackground(client.textRenderer, Text.of(String.valueOf(t1)), topX + goalwidthheight/2, bottombarY + goalpadding, 200, Colors.get(ClientGameState.getTeams().get(0).teamId));
//            context.drawTextWithBackground(client.textRenderer, Text.of(String.valueOf(t2)), topX + goalwidthheight + goalpadding + goalwidthheight/2, bottombarY + goalpadding, 200, Colors.get(ClientGameState.getTeams().get(1).teamId));
            LockoutUtils.drawCenteredText(context, client.textRenderer, String.valueOf(t1), topX + goalwidthheight/2, bottombarY+ goalwidthheight/4, Colors.get(ClientGameState.getTeams().get(0).teamId), false);
            LockoutUtils.drawCenteredText(context, client.textRenderer, String.valueOf(t2), topX + 3* goalwidthheight/2 + goalpadding, bottombarY + goalwidthheight/4, Colors.get(ClientGameState.getTeams().get(1).teamId), false);

            CompassesWidget.drawHud(context);







        }


    }

}
