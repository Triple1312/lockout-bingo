package net.abrikoos.lockout_bingo.client.gui.tabs;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.client.gui.screens.ScreenScreen;
import net.abrikoos.lockout_bingo.network.team.LockoutAddTeamPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutJoinTeamPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutRemoveTeamPacket;
import net.abrikoos.lockout_bingo.team.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TeamsTab implements Tab {
    final ScreenScreen screen;
    protected SimplePositioningWidget wdgt = new SimplePositioningWidget();
    protected PlayerList playerList;
    protected List<ClickableWidget> children = new ArrayList<>();


    public TeamsTab(ScreenScreen screen) {
        this.screen = screen;
        this.playerList = new PlayerList(0, 24, screen.width/10, screen.height-48);
        TeamsTabWidget teamsTabWidget = new TeamsTabWidget(0, 0, screen.width, screen.height);
        this.children.add(teamsTabWidget);
        this.children.add(playerList);
        this.children.add(teamsTabWidget.teamNameField);
    }

    public void addSelectedToTeam(LockoutTeam team) {

    }



//    public void drawTab() {
//        Positioner positioner = this.wdgt.copyPositioner();
//        wdgt.add(playerList, positioner.alignLeft());
//        List<LockoutTeam> teams = TeamRegistry.getTeams();
//        for (int i = 0; i < teams.size(); i++) {
//            TeamCardWidget teamCardWidget = new TeamCardWidget(teams.get(i), screen.vw(15), screen.vh(35));
//            wdgt.add(teamCardWidget);
//            int x = (i % 4) * screen.vw(15) + (i+1) * screen.vw(5) + screen.vw(10);
//            int y = (i / 4) * screen.vh(35) + (i/4 + 1) * screen.vh(5) + 48;
//            SimplePositioningWidget.setPos(teamCardWidget, x, y, screen.vw(15), screen.vh(35) );
//        }
//        if(teams.size() < 8) {
//            NewTeamCardWidget newTeamCardWidget = new NewTeamCardWidget( screen.vw(15), screen.vh(35)   );
//            wdgt.add(newTeamCardWidget);
//            int x = (teams.size() % 4) * screen.vw(15) + (teams.size()+1) * screen.vw(5) + screen.vw(10);
//            int y = (teams.size() / 4) * screen.vh(35) + (teams.size()/4 + 1) * screen.vh(5) + 48;
//            SimplePositioningWidget.setPos(newTeamCardWidget, x, y, screen.vw(15), screen.vh(35) );
//        }
//
////        // 5 columns and 4 rows
////        GridWidget.Adder adder = this.grid.setRowSpacing(8).setColumnSpacing(10).createAdder(5);
////        LockoutLogger.log(screen.width + " " + screen.height);
////        LockoutLogger.log(this.grid.getWidth() + " " + this.grid.getHeight());
//////        this.grid.add(new PlayerList(), 0, 0, 4, 1);
////        int teamssize = TeamRegistry.getTeams().size();
////        for (int i = 0; i < 6; i++) {
////            int finalI = i;
////            this.grid.add(ButtonWidget.builder(Text.of("X"), (btn) -> {
////                ClientPlayNetworking.send(new LockoutRemoveTeamPacket(TeamRegistry.getTeams().get(finalI).name));
////            }).build(), i / 4, i % 4 + 1, 1, 1);
////        }
////        for (int i = 0; i < teamssize; i++) {
////            this.grid.add(new TeamCardWidget(TeamRegistry.getTeams().get(i)), i / 4, i % 4 + 1, 2, 2);
////        }
////        if (teamssize < 8) {
//////            this.grid.add(new NewTeamCardWidget(), teamssize / 4, teamssize % 4 + 1, 2, 1);
////        }
//    }
//
//    public void getSelectedPlayerOrNull() {
//
//    }
//
//    public void redraw() {
//        this.wdgt.forEachChild(this.screen::remove);
//        drawTab();
//    }



    @Override
    public Text getTitle() {
        return Text.of("TeamsTab");
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {
        children.forEach(consumer);
    }

    @Override
    public void refreshGrid(ScreenRect tabArea) {
        this.children.get(0).setDimensionsAndPosition(tabArea.width(), tabArea.height(), tabArea.position().x(), tabArea.position().y() + 40);
    }


    class PlayerList extends AlwaysSelectedEntryListWidget<PlayerList.PlayerEntry> {
        public PlayerList(int x, int y, int width, int height) {
            super(MinecraftClient.getInstance(), width, height, y, 15);
            for (TeamPlayer player : PlayerTeamRegistry.getAllPlayers()) {
                this.addEntry(new PlayerEntry(player));
            }
        }

        public static class PlayerEntry extends AlwaysSelectedEntryListWidget.Entry<PlayerEntry> {
            TeamPlayer player;


            public PlayerEntry(TeamPlayer player) {
                this.player = player;
            }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                int texture_width = 8;
                x = 2;
                try {
                    RenderSystem.enableBlend();
                    context.drawTexture(player.getPlayerListEntry().getSkinTextures().texture(), x, y, 0, 0, texture_width, texture_width);
                    RenderSystem.disableBlend();
                }
                catch (Exception ignored) {}
                context.drawText(
                        MinecraftClient.getInstance().textRenderer,
                        Text.of(player.getName()),
                        x + texture_width,
                        y,
                        player.teamIndex == 0? 0xFFFFFFFF : Colors.get(player.teamIndex),
                        false);

            }

            @Override
            public Text getNarration() {
                return Text.of(player.getName());
            }
        }
    }

//    class TeamCardWidget extends SimplePositioningWidget {
//        LockoutTeam team;
//
//        public TeamCardWidget(LockoutTeam team, int x, int y, int width, int height) {
//            super();
//            this.team = team;
//            this.width = width;
//            this.height = height;
//            this.setPosition(x, y);
//            this.init();
//        }
//
//        protected void init() {
////            GridWidget.Adder adder = this.setColumnSpacing(8).setRowSpacing(10).createAdder(1);
////            Positioner positioner = adder.copyPositioner();
//
//            adder.add(new TeamTitleWidget(team));
//            for (String playeruuid : team.playeruuids) {
//                TeamPlayer player = PlayerTeamRegistry.getPlayerByUUID(playeruuid);
//                adder.add(new TextWidget(Text.of(player.getName()), MinecraftClient.getInstance().textRenderer), positioner.alignLeft());
//            }
//
//
//        }
//        class TeamTitleWidget extends GridWidget {
//            LockoutTeam team;
//            public TeamTitleWidget(LockoutTeam team) {
//                super();
//                this.team = team;
//                this.init();
//            }
//
//            protected void init() {
//                GridWidget.Adder adder = this.setColumnSpacing(8).createAdder(4);
//                adder.add(
//                    ButtonWidget.builder(Text.of("X"), (btn) -> {
//                        ClientPlayNetworking.send(new LockoutRemoveTeamPacket(team.name));
//                    }).build()
//                );
//                adder.add(
//                        new TextWidget( Text.literal(team.name).withColor(Colors.get(team.teamId)), MinecraftClient.getInstance().textRenderer),
//                        2,
//                        adder.copyPositioner().alignHorizontalCenter()
//                );
//                adder.add(
//                        ButtonWidget.builder(Text.of("+"), (btn) -> {
//                            ClientPlayNetworking.send(new LockoutJoinTeamPacket(team.teamId));
//                        }).build()
//                );
//
//            }
//        }
//    }
//
//    class NewTeamCardWidget extends GridWidget {
//        TextFieldWidget tfw = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 120, 20, Text.of("Team Name"));
//        public NewTeamCardWidget(int width, int height) {
//            super();
//            this.width = width;
//            this.height = height;
//            Adder adder = this.setColumnSpacing(8).setRowSpacing(10).createAdder(1);
////            adder.add(
////                    ButtonWidget.builder(Text.of("Add"), (btn) -> {
////                        ClientPlayNetworking.send(new LockoutAddTeamPacket(tfw.getText()));
////                    }).width(500).build()
////            );
//            this.init();
//        }
//
//        protected void init() {
//            GridWidget.Adder adder = this.setColumnSpacing(8).setRowSpacing(10).createAdder(1);
//            Positioner positioner = adder.copyPositioner();
//            adder.add(tfw);
//            adder.add(
//                    ButtonWidget.builder(Text.of("Add"), (btn) -> {
//                        ClientPlayNetworking.send(new LockoutAddTeamPacket(tfw.getText()));
//                    }).build()
//            );
//        }
//    }

    class TeamsTabWidget extends ClickableWidget {
        final static int TeamCardWidth = 15;
        final static int TeamCardHeight = 45;
        public TextFieldWidget teamNameField;
        List<ClickableWidget> clickables = new ArrayList<>();
        public TeamsTabWidget(int x, int y, int width, int height) {
            super(x, y, width, height, Text.of("Teams"));
            this.teamNameField = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 120, 20, Text.of(""));
            this.teamNameField.setPlaceholder(Text.of("Team Name"));
            this.teamNameField.setHeight(12);
        }

        protected boolean clicked(double mouseX, double mouseY) {
            for (ClickableWidget clickable : clickables) {
                if (clickable.isMouseOver(mouseX, mouseY)) {
                    clickable.onClick((int) mouseX, (int) mouseY);
                    return true;
                }
            }
            return false;
        }

        public int getTeamCardWidth() {
            return TeamCardWidth * width /100;
        }

        public int getTeamCardHeight() {
            return TeamCardHeight * height / 100;
        }

        public void drawTeamCard(DrawContext context, int mouseX, int mouseY, float delta, int x, int y, int width, int height, LockoutTeam team) {
            context.fill(x, y, x + width, y + height, 0x66000000);
            List<TeamPlayer> players = PlayerTeamRegistry.getTeamPlayers(team.teamId);

            // header
            if (players.isEmpty()) {
                ButtonWidget btn = ButtonWidget.builder(Text.of("X"), (button) -> {
                    ClientPlayNetworking.send(new LockoutRemoveTeamPacket(team.name));
                }).dimensions(x+2, y+2, 10, 10).build();
                btn.render(context, mouseX, mouseY, delta);
                clickables.add(btn);
            }
            ButtonWidget btn2 = ButtonWidget.builder(Text.of("+"), (button) -> {
                ClientPlayNetworking.send(new LockoutJoinTeamPacket(team.teamId));
            }).dimensions(x+ width - 12, y+2, 10, 10).build();
            btn2.render(context, mouseX, mouseY, delta);
            clickables.add(btn2);
            TextWidget tw = new TextWidget( x, y+2, width, 10, Text.literal(team.name).withColor(Colors.get(team.teamId)), MinecraftClient.getInstance().textRenderer );
            tw.renderWidget(context, mouseX, mouseY, delta);
            context.fill(x, y+14, x + width, y + 16, 0x88FFFFFF);

            // names
            for (int i = 0; i < players.size(); i++) {
                TeamPlayer player = players.get(i);
                TextWidget tw2 = new TextWidget( x+2, y+20 + i*15, width - 4, 10, Text.of(player.getName()), MinecraftClient.getInstance().textRenderer );
                tw2.renderWidget(context, mouseX, mouseY, delta);
            }
        }

        public void drawNewTeamCard(DrawContext context, int mouseX, int mouseY, float delta, int x, int y, int width, int height) {
            context.fill(x, y, x + width, y + height, 0x66000000);
            TextWidget tw = new TextWidget( x+2, y+2, width - 4, 10, Text.of("New Team:"), MinecraftClient.getInstance().textRenderer );
            tw.renderWidget(context, mouseX, mouseY, delta);
            this.teamNameField.setWidth(width - 4);
            this.teamNameField.setX(x + 2);
            this.teamNameField.setY(y + 15);
            ButtonWidget btn = ButtonWidget.builder(Text.of("Add Team"), (button) -> {
                ClientPlayNetworking.send(new LockoutAddTeamPacket(teamNameField.getText()));
                teamNameField.setText("");
            }).dimensions(x + 2, y+ height -17, width -4 , 15).build();
            btn.render(context, mouseX, mouseY, delta);
            clickables.add(btn);
        }

        public int calculateCardX(int index) {
            return ((index % 4) * TeamCardWidth + ((index%4)+1) * 5 + 12) * width / 100;
        }

        public int calculateCardY(int index) {
            return ((index / 4) * TeamCardHeight + (index/4 + 1) * 5 + 7) * height / 100;
        }




        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            clickables.clear();
            List<LockoutTeam> teams = TeamRegistry.getTeams();
            for (int i = 0; i < teams.size(); i++) {
                drawTeamCard(context, mouseX, mouseY, delta, calculateCardX(i), calculateCardY(i), getTeamCardWidth(), getTeamCardHeight(), teams.get(i));
            }
            if(teams.size() < 8) {
                this.teamNameField.setVisible(true);
                drawNewTeamCard(context, mouseX, mouseY, delta, calculateCardX(teams.size()), calculateCardY(teams.size()), getTeamCardWidth(), getTeamCardHeight());
            }
            else {
                this.teamNameField.setVisible(false);
            }

        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {

        }
    }


}






















































