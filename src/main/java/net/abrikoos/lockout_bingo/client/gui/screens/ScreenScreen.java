package net.abrikoos.lockout_bingo.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.gui.screens.tabscreen.LockoutTabManager;
import net.abrikoos.lockout_bingo.client.gui.screens.tabscreen.LockoutTabNavigationWidget;
import net.abrikoos.lockout_bingo.client.gui.tabs.BoardTab2;
import net.abrikoos.lockout_bingo.client.gui.tabs.BoardTab3;
import net.abrikoos.lockout_bingo.client.gui.tabs.TeamsTab;
import net.abrikoos.lockout_bingo.client.gui.widget.AddTeamWidget;
import net.abrikoos.lockout_bingo.client.TeamsChangeListener;
import net.abrikoos.lockout_bingo.client.modes.LockoutGame;
import net.abrikoos.lockout_bingo.team.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ScreenScreen extends Screen {

    private LockoutTabNavigationWidget tabNavigation;
    private LockoutTabManager tabManager; // = new LockoutTabManager(this::addDrawableChild, this::remove);
    private ThreePartsLayoutWidget layout;
    public static final Identifier TAB_HEADER_BACKGROUND_TEXTURE = Identifier.ofVanilla("textures/gui/tab_header_background.png");
    public BoardTab3 boardTab = new BoardTab3();


    public ScreenScreen() {
        super(Text.of("Complete Full Screen"));
        init();
        TeamsChangeListener.subscribe(this::teamsChanged);
    }

    public void init() {
        this.clearChildren();
        this.layout = new ThreePartsLayoutWidget(this);
        this.tabManager = new LockoutTabManager(this::addDrawableChild, this::remove);
        this.tabManager.subscribeTabChangeEvent(this::tabchanged);
        this.tabNavigation = LockoutTabNavigationWidget.builder(this.tabManager, this.width)
                .tabs(new MainTab(), boardTab, new TeamsTab(this))
                .build();
        this.addDrawableChild(this.tabNavigation);
        this.layout.forEachChild(child -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.tabNavigation.selectTab(CompleteFullScreenState.selectedTab, false);
        this.initTabNavigation();

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        RenderSystem.enableBlend();
        context.drawTexture(Screen.FOOTER_SEPARATOR_TEXTURE, 0, this.height - this.layout.getFooterHeight() - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
        RenderSystem.disableBlend();
    }

    @Override
    protected void renderDarkening(DrawContext context) {
        context.drawTexture(TAB_HEADER_BACKGROUND_TEXTURE, 0, 0, 0.0F, 0.0F, this.width, this.layout.getHeaderHeight(), 16, 16);
        this.renderDarkening(context, 0, this.layout.getHeaderHeight(), this.width, this.height);
    }

    @Override
    public void remove(Element child) {
        super.remove(child);
    }

    public int vw(int p) {
        return this.width *p / 100;
    }

    public int vh(int p) {
        return this.height *p / 100;
    }

    public void setTabIndex(int index) {
        this.tabNavigation.selectTab(index, false);
    }

    public void tabchanged(Tab tab) {
        CompleteFullScreenState.selectedTab = this.tabNavigation.getActiveTabIndex();
    }

    public void clearChildren() {
        super.clearChildren();
    }

    public void teamsChanged(List<LockoutTeam> teams) {
        LockoutLogger.log("Teams Changed");
//        teamTab.forEachChild(this::remove);
        this.init();
    }

    @Override
    public void initTabNavigation() {
        if (this.tabNavigation != null) {
            this.tabNavigation.setWidth(this.width);
            this.tabNavigation.init();
            int i = this.tabNavigation.getNavigationFocus().getBottom();
            ScreenRect screenRect = new ScreenRect(0, i, this.width, this.height - this.layout.getFooterHeight() - i);
            this.tabManager.setTabArea(screenRect);
            this.layout.setHeaderHeight(i);
            this.layout.refreshPositions();
        }
    }

    class MainTab extends GridScreenTab {
        private static final Text MAIN_TAB_TITLE = Text.of("Main");

        MainTab() {
            super(MAIN_TAB_TITLE);
            GridWidget.Adder adder = this.grid.setColumnSpacing(10).setRowSpacing(8).createAdder(1);
            Positioner positioner = adder.copyPositioner();
            MinecraftClient client = MinecraftClient.getInstance();
            adder.add(
                    new TextWidget(Text.of("Game Modes"), client.textRenderer),
                    positioner.alignVerticalCenter()
            );
            adder.add(
                    ButtonWidget.builder(
                            Text.of("Lockout"), btn -> {
                                client.setScreen(new NewLockoutScreen(ScreenScreen.this));
                            }
                    ).width(120).build(),
                    positioner
            );

            ButtonWidget lbbtn = adder.add(
                    ButtonWidget.builder(
                            Text.of("Lockout Bingo"), btn -> {

                            }
                    ).width(120).build(),
                    positioner
            );
            lbbtn.active = false;

            ButtonWidget arbtn = adder.add(
                    ButtonWidget.builder(
                            Text.of("Advancement Rush"), btn -> {

                            }
                    ).width(120).build(),
                    positioner
            );
            arbtn.active = false;

            ButtonWidget rbhbtn = adder.add(
                    ButtonWidget.builder(
                            Text.of("Random Block Hunt"), btn -> {

                            }
                    ).width(120).build(),
                    positioner
            );
            rbhbtn.active = false;

            ButtonWidget ccbtn = adder.add(
                    ButtonWidget.builder(
                            Text.of("Crafting Chaos"), btn -> {

                            }
                    ).width(120).build(),
                    positioner
            );
            ccbtn.active = false;

            ButtonWidget bbbtn = adder.add(
                    ButtonWidget.builder(
                            Text.of("Biome Blitz"), btn -> {

                            }
                    ).width(120).build(),
                    positioner
            );
            bbbtn.active = false;
        }

    }



    class TeamGTab implements Tab {
        List<ClickableWidget> children = new ArrayList<>();
        int TOP_MARGIN = 100;

        TeamGTab() {

        }

        public void redraw() {
            children.clear();
            TeamPlayer teamPlayer = PlayerTeamRegistry.getPlayerByUUID(MinecraftClient.getInstance().player.getUuidAsString());
            addTeamsWidgets();
            addPlayersWidgets();
        }

        public void addTeamsWidgets() {
            int X_MARGIN = ScreenScreen.this.width - 2 * 300 / 3;
            children.add(new TextWidget(X_MARGIN, TOP_MARGIN, Text.of("Teams"), MinecraftClient.getInstance().textRenderer));
            AddTeamWidget atw = new AddTeamWidget(); atw.setX(X_MARGIN); atw.setY(TOP_MARGIN + 20);
//            children.add(new AddTeamWidget(), atw);

        }

        public void addPlayersWidgets() {
            int X_MARGIN = (ScreenScreen.this.width - 2 * 300 / 3) * 2 + 300;

        }


        @Override
        public Text getTitle() {
            return Text.of("Teams");
        }

        @Override
        public void forEachChild(Consumer<ClickableWidget> consumer) {
            children.forEach(consumer);
        }

        @Override
        public void refreshGrid(ScreenRect tabArea) {

        }

        class PlayerTeamListWidget extends AlwaysSelectedEntryListWidget<ScreenScreen.TeamGTab.PlayerTeamListWidget.Entry> {

            public PlayerTeamListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l) {
                super(minecraftClient, i, j, k, l);
            }













            public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<Entry> {
                public Entry() {
                    super();
                }
            }

            public final class PlayerEntry extends Entry implements AutoCloseable {
                MinecraftClient client = MinecraftClient.getInstance();
                private final TeamPlayer player;



                public PlayerEntry(TeamPlayer player) {
                    super();
                    this.player = player;
                }

                @Override
                public void close() {

                }

                @Override
                public Text getNarration() {
                    return Text.of("vbdhjsidolhaldh,bdkjbmnbmsdiolasyiodya");
                }

                @Override
                public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                    context.fill(x, y, x + entryWidth, y + entryHeight, 0x80FF0000);
                    context.drawTexture(player.getPlayerListEntry().getSkinTextures().texture(), x, y, 8, 8, 8, 8, entryHeight, entryHeight);
                    context.drawText(client.textRenderer, player.getName(), x + entryHeight+2, y + 2, player.teamIndex ==0 ? 0xFFFFFFFF : Colors.get(player.teamIndex),false);
                    context.drawText(client.textRenderer, "Team: "+ (player.teamIndex == 0 ? "No" : TeamRegistry.getTeams().get(player.teamIndex).name), x + entryHeight+2, y + 12, 0x77FFFFFF,false);





                }


                @Override
                public boolean mouseClicked(double mouseX, double mouseY, int button) {
                    return false;
                }


            }
        }

    }
//            private static final Text TEAM_TAB_TITLE = Text.of("Teams");
//            private final TeamPlayer teamPlayer;
//            private static final MinecraftClient client = MinecraftClient.getInstance();
//            private final AddTeamWidget atw;
//
//            TeamGTab(AddTeamWidget atz) {
//                super(TEAM_TAB_TITLE);
//                this.teamPlayer = PlayerTeamRegistry.getPlayerByUUID(client.player.getUuidAsString());
//                this.atw = atz;
//                redraw();
//            }
//
//            public void redraw() {
//                GridWidget.Adder adder = this.grid.setRowSpacing(10).setColumnSpacing(40).createAdder(2);
//                Positioner positioner = adder.copyPositioner();
//                adder.add(new TeamListWidget(this.teamPlayer), positioner.alignLeft());
//                adder.add(new PlayerListTeamWidget(), positioner.alignRight());
//            }
//
//            class TeamListWidget extends GridWidget {
//                TeamListWidget(TeamPlayer teamPlayer) {
//                    super();
//                    GridWidget.Adder adder = this.setColumnSpacing(10).setRowSpacing(8).createAdder(1);
//                    adder.add(new TextWidget(Text.of("Teams"), MinecraftClient.getInstance().textRenderer));
//                    adder.add(atw);
//                    for (int i = 1; i <= TeamRegistry.getTeams().size(); i++) {
//                        adder.add(TeamSettingsWidget.builder(i, teamPlayer).build());
//                    }
//                }
//            }
//
//            class PlayerListTeamWidget extends GridWidget {
//                PlayerListTeamWidget() {
//                    super();
//                    GridWidget.Adder adder = this.setColumnSpacing(10).setRowSpacing(8).createAdder(1);
//                    Positioner positioner = adder.copyPositioner();
//                    TextWidget w = adder.add(new TextWidget(Text.of("Players"), MinecraftClient.getInstance().textRenderer), positioner.alignLeft());
//                    w.setWidth(200);
//                    for (int i = 0; i < PlayerTeamRegistry.getAllPlayers().size(); i++) {
//                        adder.add(new PlayerTeamSelectWidget(PlayerTeamRegistry.getAllPlayers().get(i)), positioner.alignLeft());
//                    }
////                    for (int i = 0; i < PlayerTeamRegistry.getAllPlayers().size(); i++) {
////                        adder.add(new PlayerTeamSelectWidget(PlayerTeamRegistry.getAllPlayers().get(i)), positioner.alignRight());
////                    }
//                }
//
//            }



}


//    class ScreenWrapper extends WrapperWidget {
//
//        public ScreenWrapper(int x, int y, int width, int height) {
//            super(x, y, width, height);
//        }
//
//        @Override
//        public void forEachElement(Consumer<Widget> consumer) {
//
//        }

