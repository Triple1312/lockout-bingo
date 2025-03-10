package net.abrikoos.lockout_bingo.client.gui.hud;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.client.gui.LockoutUtils;
import net.abrikoos.lockout_bingo.networkv2.game.GoalInfoPacket;
import net.abrikoos.lockout_bingo.networkv2.team.Colors;
import net.abrikoos.lockout_bingo.server.goals.GoalItemRegistry;
//import net.abrikoos.lockout_bingo.team.UnitedTeamRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Environment(EnvType.CLIENT)
public class BoardHud {


    public static void drawHudNew(@NotNull DrawContext context, float delta) {
        if (ClientGameStateV2.isGameRunning()) {
            MinecraftClient client = MinecraftClient.getInstance();
            int goalCount = ClientGameStateV2.getGoalCount();
            int goalRowCount = (int) Math.pow(goalCount, 0.5);
            int screensizex = context.getScaledWindowWidth();
            int screensizey = context.getScaledWindowHeight();
            double width = 0.36 * screensizex;
            int topX = (int) (screensizex - width -5);
            int topY = 5;

            int goalWidthHeight = (int) (width / (goalRowCount + (double) (goalRowCount - 1) /4));

            int goalwidthheight = (int) (screensizey * 0.06);
            int goalpadding = (int) (screensizey * 0.01);
//            int topX = screensizex - 5 * (goalwidthheight + goalpadding) - goalpadding;
//            int topY = 5;
        }
    }

    public static void drawHud(@NotNull DrawContext context, float delta) {
        if (ClientGameStateV2.isGameRunning()) {
            MinecraftClient client = MinecraftClient.getInstance();
            int screensizex = context.getScaledWindowWidth();
            int screensizey = context.getScaledWindowHeight();
            int goalwidthheight = (int) (screensizey * 0.06);
            int goalpadding = (int) (screensizey * 0.01);
            int topX = screensizex - 5 * (goalwidthheight + goalpadding) - goalpadding;
            int topY = 5;

            List<GoalInfoPacket> goals = ClientGameStateV2.getGoals();
            for (int i = 0; i < goals.size(); i++) {
                int x = i % 5;
                int y = i / 5;
                int goalTopX = topX + x * (goalwidthheight + goalpadding);
                int goalTopY = topY + y * (goalwidthheight + goalpadding);
                int color = goals.get(i).color();

                context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, Colors.get(color) - 0x47000000);
                try {
                    ClientGameStateV2.goals.get(i).draw(context, delta, goalTopX, goalTopY, goalwidthheight, goalwidthheight);
//                    context.drawItemWithoutEntity(Items.STONE.getDefaultStack(), goalTopX, goalTopY);
                }
                catch (Exception ignored) {
                    LockoutLogger.log("Error drawing goal " + goals.get(i).goalName() + " at boardhud");
                }
            }

            int bottombarY = topY + 5 *(goalwidthheight + goalpadding);

            context.fill(topX, bottombarY, topX + goalwidthheight * 2 + goalpadding, bottombarY + goalwidthheight/2, Colors.get(0) - 0x47000000);
            context.fill(topX + 2*(goalwidthheight + goalpadding), bottombarY, topX + goalwidthheight * 5 + goalpadding*4, bottombarY + goalwidthheight/2, Colors.get(0) - 0x47000000);
            String timeString = "";
            long time = ClientGameStateV2.gameTimeLength();
            if (time > 3600000) { // hours
                timeString += time / 3600000 + ":";
            }
            if (time > 60000) { // minutes
                timeString += time / 60000 % 3600000 + ":";
            }
            if (time / 1000 % 60 < 10) {
                timeString += "0";
            }
            timeString += time / 1000 % 60; // seconds

            LockoutUtils.drawCenteredText(context, client.textRenderer, timeString,topX + (goalwidthheight * 3 + goalpadding * 3 + goalwidthheight/2),bottombarY + goalwidthheight/4, 0xffffffff, false  );

//            context.drawTextWithBackground(client.textRenderer, Text.of(String.valueOf(t1)), topX + goalwidthheight/2, bottombarY + goalpadding, 200, Colors.get(ClientGameState.getTeams().get(0).teamId));
//            context.drawTextWithBackground(client.textRenderer, Text.of(String.valueOf(t2)), topX + goalwidthheight + goalpadding + goalwidthheight/2, bottombarY + goalpadding, 200, Colors.get(ClientGameState.getTeams().get(1).teamId));

            try {
                LockoutUtils.drawCenteredText(context, client.textRenderer, String.valueOf(ClientGameStateV2.getBoard().scores().get(0)), topX + goalwidthheight/2, bottombarY+ goalwidthheight/4, Colors.get(ClientGameStateV2.getTeams().get(0).teamColor), false);
                LockoutUtils.drawCenteredText(context, client.textRenderer, String.valueOf(ClientGameStateV2.getBoard().scores().get(1)), topX + 3* goalwidthheight/2 + goalpadding, bottombarY + goalwidthheight/4, Colors.get(ClientGameStateV2.getTeams().get(1).teamColor), false);

            }
            catch (Exception e) {
                LockoutLogger.log("Error drawing scores in boardhud");
            }

//            CompassesWidget.drawHud(context);

        }


    }

}
