package net.abrikoos.lockout_bingo.client.gui.tabs;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.gui.screens.ScreenScreen;
import net.abrikoos.lockout_bingo.network.team.LockoutAddPlayerToTeamPacket;
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

    public void addSelectedToTeam(Integer teamId) {
        PlayerList.PlayerEntry selected = playerList.selected();
        if (selected != null) {
            ClientPlayNetworking.send(new LockoutAddPlayerToTeamPacket(teamId, selected.player.puuidstr()));
        }
        else {
            ClientPlayNetworking.send(new LockoutJoinTeamPacket(teamId));
        }
    }

    public TeamsTab(ScreenScreen screen) {
        this.screen = screen;
        this.playerList = new PlayerList(0, 24, screen.width/10, screen.height-48);
        TeamsTabWidget teamsTabWidget = new TeamsTabWidget(0, 0, screen.width, screen.height, this::addSelectedToTeam);
        this.children.add(teamsTabWidget);
        this.children.add(playerList);
        this.children.add(teamsTabWidget.teamNameField);
    }



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
        this.children.get(0).setDimensionsAndPosition(tabArea.width(), tabArea.height(), (int) (tabArea.position().x() * tabArea.width() * 0.85), tabArea.position().y() );
        playerList.setDimensionsAndPosition((int) (tabArea.width() * 0.15), tabArea.height(), tabArea.position().x(), tabArea.position().y());
    }

    class PlayerList extends AlwaysSelectedEntryListWidget<PlayerList.PlayerEntry> {
        public PlayerList(int x, int y, int width, int height) {
            super(MinecraftClient.getInstance(), width, height, y, 15);
            for (UTeamPlayer player : UnitedTeamRegistry.getPlayers()) {
                this.addEntry(new PlayerEntry(player));
            }
        }

        public PlayerEntry selected() {
            return this.getFocused();
        }

        public static class PlayerEntry extends AlwaysSelectedEntryListWidget.Entry<PlayerEntry> {
            UTeamPlayer player;


            public PlayerEntry(UTeamPlayer player) {
                this.player = player;
            }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                int texture_width = 8;
                x = 2;
                try {
                    RenderSystem.enableBlend();
//                    context.drawTexture(player.getPlayerListEntry().getSkinTextures().texture(), x, y, 0, 0, texture_width, texture_width); // todo
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


    class TeamsTabWidget extends ClickableWidget {
        final static int TeamCardWidth = 15;
        final static int TeamCardHeight = 47;
        final static int teamcardXpadding = 3;
        final static int teamcardYpadding = 2;
        public TextFieldWidget teamNameField;
        List<ClickableWidget> clickables = new ArrayList<>();

        Consumer<Integer> JoinTeam;

        public TeamsTabWidget(int x, int y, int width, int height, Consumer<Integer> JoinTeam) {
            super(x, y, width, height, Text.of("Teams"));
            this.teamNameField = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 120, 20, Text.of(""));
            this.teamNameField.setPlaceholder(Text.of("Team Name"));
            this.teamNameField.setHeight(12);
            this.JoinTeam = JoinTeam;
        }

        @Override
        public void setDimensionsAndPosition(int width, int height, int x, int y) {
            super.setDimensionsAndPosition(width, height, x, y);
            LockoutLogger.log("setDimensionsAndPosition");

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

        public void drawTeamCard(DrawContext context, int mouseX, int mouseY, float delta, int x, int y, int width, int height, UnitedTeamRegistry.Team team) {
            context.fill(x, y, x + width, y + height, 0x66000000);
            List<UTeamPlayer> players = UnitedTeamRegistry.getTeamPlayers(team.teamId());

            // header
            if (players.isEmpty()) {
                ButtonWidget btn = ButtonWidget.builder(Text.of("X"), (button) -> {
                    ClientPlayNetworking.send(new LockoutRemoveTeamPacket(team.teamName()));
                }).dimensions(x+2, y+2, 10, 10).build();
                btn.render(context, mouseX, mouseY, delta);
                clickables.add(btn);
            }
            ButtonWidget btn2 = ButtonWidget.builder(Text.of("+"), (button) -> {
                this.JoinTeam.accept(team.teamId());
//                ClientPlayNetworking.send(new LockoutJoinTeamPacket(team.teamId));
            }).dimensions(x+ width - 12, y+2, 10, 10).build();
            btn2.render(context, mouseX, mouseY, delta);
            clickables.add(btn2);
            TextWidget tw = new TextWidget( x, y+2, width, 10, Text.literal(team.teamName()).withColor(Colors.get(team.teamId())), MinecraftClient.getInstance().textRenderer );
            tw.renderWidget(context, mouseX, mouseY, delta);
            context.fill(x, y+14, x + width, y + 16, 0x88FFFFFF);

            // names
            for (int i = 0; i < players.size(); i++) {
                UTeamPlayer player = players.get(i);
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
            return this.getX() +((index % 4 + 1) * TeamCardWidth + ((index % 4 +1) * teamcardXpadding))  * width / 100;
        }

        public int calculateCardY(int index) {
            return this.getY() + ((index / 4) * TeamCardHeight + ((index / 4 +1) * teamcardYpadding)) * height / 100;
        }


        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            clickables.clear();
            try {
                List<UnitedTeamRegistry.Team> teams = UnitedTeamRegistry.getTeams();
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
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {

        }
    }


}






















































