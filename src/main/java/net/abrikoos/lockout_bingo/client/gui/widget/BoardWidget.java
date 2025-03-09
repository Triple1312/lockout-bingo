//package net.abrikoos.lockout_bingo.client.gui.widget;
//
//import net.abrikoos.lockout_bingo.team.Colors;
//import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;
//import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
//import net.abrikoos.lockout_bingo.team.UnitedTeamRegistry;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.DrawContext;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.text.Text;
//
//import java.util.List;
//import java.util.UUID;
//
//
//public class BoardWidget {
//
//    public final BlackoutStartGameInfo lsgi;
//    public LockoutUpdateBoardInfo lubi;
//    public List<UnitedTeamRegistry.Team> teams;
//
//    public BoardWidget(BlackoutStartGameInfo lsgi) {
//        this.lsgi = lsgi;
//        this.lubi = new LockoutUpdateBoardInfo(new String[25]);
//    }
//
//    public void setLubi(LockoutUpdateBoardInfo lubi) {
//        this.lubi = lubi;
//    }
//
//    public void drawHud(DrawContext context) {
//        if (lsgi == null) {
//            return;
//        }
//        MinecraftClient client = MinecraftClient.getInstance();
//        int screensizex = context.getScaledWindowWidth();
//        int screensizey = context.getScaledWindowHeight();
//        int goalwidthheight = (int) (screensizey * 0.06);
//        int goalpadding = (int) (screensizey * 0.01);
//        int topX = screensizex - 5 * (goalwidthheight + goalpadding) - goalpadding;
//        int topY = 5;
//
//        int t1 = 0;
//        int t2 = 0;
//
//        for (int i = 0; i < this.lsgi.goals.length; i++) {
//            int x = i % 5;
//            int y = i / 5;
//            int goalTopX = topX + x * (goalwidthheight + goalpadding);
//            int goalTopY = topY + y * (goalwidthheight + goalpadding);
//            int color = Colors.getPlayerColor(lubi.goals[i]);
//            context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, color - 0x47000000);
//            lsgi.goals[i].draw(context, 0, goalTopX, goalTopY, goalwidthheight, goalwidthheight);
//            try {
//                if (lubi.goals[i] == null || lubi.goals[i].equals("00000000-0000-0000-0000-000000000000")) {
//
//                }
//                else if (UnitedTeamRegistry.getTeamPlayerByUUID(UUID.fromString(lubi.goals[i])).teamIndex == lsgi.teamIndexes.get(0)) {
//                    t1++;
//                }
//                else if (UnitedTeamRegistry.getTeamPlayerByUUID(UUID.fromString(lubi.goals[i])).teamIndex == lsgi.teamIndexes.get(1)) {
//                    t2++;
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace(
//                );
//            }
//
//        }
//
//        int bottombarY = topY + 5 *(goalwidthheight + goalpadding);
//
//        context.fill(topX, bottombarY, topX + goalwidthheight * 2 + goalpadding, bottombarY + goalwidthheight/2, Colors.get(0) - 0x47000000);
//        context.fill(topX + 2*(goalwidthheight + goalpadding), bottombarY, topX + goalwidthheight * 5 + goalpadding*4, bottombarY + goalwidthheight/2, Colors.get(0) - 0x47000000);
//
//        context.drawTextWithBackground(client.textRenderer, Text.of(String.valueOf(t1)), topX + goalwidthheight/2, bottombarY + goalpadding, 200, Colors.get(lsgi.teamIndexes.get(0)));
//        context.drawTextWithBackground(client.textRenderer, Text.of(String.valueOf(t2)), topX + goalwidthheight + goalpadding + goalwidthheight/2, bottombarY + goalpadding, 200, Colors.get(lsgi.teamIndexes.get(1)));
//        CompassesWidget.drawHud(context);
//    }
//
//    public void drawScreen(DrawContext context, Screen screen, int mouseX, int mouseY, float delta) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        int screensizex = screen.width;
//        int screensizey = screen.height;
//        int goalwidthheight = (int) (screensizey * 0.1);
//        int goalpadding = (int) (screensizey * 0.03);
//        int topX = screensizex / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;
//        int topY = screensizey / 2 - (goalwidthheight * 5 + goalpadding * 6) / 2;
//
//        for (int i = 0; i < this.lsgi.goals.length; i++) {
//            int x = i % 5;
//            int y = i/5;
//            int goalTopX = topX + x * (goalwidthheight + goalpadding);
//            int goalTopY = topY + y * (goalwidthheight + goalpadding);
//            int color = Colors.getPlayerColor((lubi.goals[i]));
//            context.fill(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, color - 0x47000000);
//
//            lsgi.goals[i].draw(context, delta, goalTopX, goalTopY, goalwidthheight, goalwidthheight);
//
//            if (intersect(goalTopX, goalTopY, goalTopX + goalwidthheight, goalTopY + goalwidthheight, mouseX, mouseY)) { // onhover
////                context.fill(goalTopX - goalpadding - 5, goalTopY - goalpadding - 5, goalTopX + goalwidthheight + goalpadding + 100, goalTopY + goalwidthheight + goalpadding + 15, 0x80000000);
//
//                context.drawTextWithBackground(client.textRenderer, Text.of(lsgi.goals[i].name), goalTopX, goalTopY - goalpadding/2, 200, 0xffffffff);
//            }
//        }
//    }
//
//    private boolean intersect(int x1, int y1, int x2, int y2, int mouseX, int mouseY) {
//        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
//    }
//
//}
