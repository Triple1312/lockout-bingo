package net.abrikoos.lockout_bingo.gui.screens;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.gui.screens.tabscreen.LockoutTabManager;
import net.abrikoos.lockout_bingo.gui.screens.tabscreen.LockoutTabNavigationWidget;
import net.abrikoos.lockout_bingo.gui.widget.AddTeamWidget;
import net.abrikoos.lockout_bingo.gui.widget.PlayerTeamSelectWidget;
import net.abrikoos.lockout_bingo.gui.widget.TeamSettingsWidget;
import net.abrikoos.lockout_bingo.listeners.TeamsChangeListener;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.abrikoos.lockout_bingo.team.PlayerTeamRegistry;
import net.abrikoos.lockout_bingo.team.TeamPlayer;
import net.abrikoos.lockout_bingo.team.TeamRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class CompleteFullScreen extends Screen {
    protected CompleteFullScreen(Text title) {
        super(title);
    }
    @Nullable
    private LockoutTabNavigationWidget tabNavigation;
    private LockoutTabManager tabManager = new LockoutTabManager(this::addDrawableChild, this::remove);
    private ThreePartsLayoutWidget layout;
    private MinecraftClient client;

    TeamTab teamTab = new TeamTab();
    MainTab mainTab = new MainTab();

    public CompleteFullScreen() {
        this(Text.of("Complete Full Screen"));
        this.client = MinecraftClient.getInstance();

        this.tabNavigation = LockoutTabNavigationWidget.builder(this.tabManager, this.width)
                .tabs(mainTab, teamTab)
                .build();
        this.layout = new ThreePartsLayoutWidget(this);

        this.tabNavigation.selectTab(CompleteFullScreenState.selectedTab, false);
        this.layout.forEachChild(child -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.initTabNavigation();
        this.tabManager.subscribeTabChangeEvent(this::tabchanged);
        this.init();
        TeamsChangeListener.subscribe(this::teamsChanged);
    }

    public void clearChildren() {
        super.clearChildren();
    }

    @Override
    protected void init() {
        this.clearChildren();
        this.addDrawableChild(this.tabNavigation);
        this.layout.forEachChild(child -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
    }

    protected void redraw() {

        this.init();
    }

    @Override
    public void initTabNavigation() {
        if (this.tabNavigation != null) {
            this.tabNavigation.setWidth(this.width);
            this.tabNavigation.init();
            int i = this.tabNavigation.getNavigationFocus().getBottom();
            ScreenRect screenRect = new ScreenRect(0, i, this.width, this.height - i - this.layout.getFooterHeight());
            this.tabManager.setTabArea(screenRect);
            this.layout.setHeaderHeight(i);
            this.layout.refreshPositions();
        }
    }

    public void tabchanged(Tab tab) {
        CompleteFullScreenState.selectedTab = this.tabNavigation.getActiveTabIndex();
    }

    public void teamsChanged(List<LockoutTeam> teams) {
        LockoutLogger.log("Teams Changed");
//        teamTab.forEachChild(this::remove);
        teamTab.redraw();
        int ctab = this.tabNavigation.getActiveTabIndex();
        if (ctab == 1) {
            this.tabManager.setCurrentTab(mainTab, false);
            this.tabManager.setCurrentTab(teamTab, false);
        }
//        this.addDrawableChild(this.tabNavigation);
//        teamTab.forEachChild(this::addDrawableChild);
        this.init();
//        int i = this.tabNavigation.getNavigationFocus().getBottom();
//        ScreenRect screenRect = new ScreenRect(0, i, this.width, this.height - i - this.layout.getFooterHeight());
//        teamTab.refreshGrid(screenRect);

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

    class TeamTab implements Tab {

        private final AddTeamWidget atw = new AddTeamWidget();
        private TeamGTab teamGTab = new TeamGTab(atw);


        @Override
        public Text getTitle() {
            return teamGTab.getTitle();
        }

        @Override
        public void forEachChild(Consumer<ClickableWidget> consumer) {
            teamGTab.forEachChild(consumer);
        }

        @Override
        public void refreshGrid(ScreenRect tabArea) {
            teamGTab.refreshGrid(tabArea);
        }

        public void redraw() {
            teamGTab.redraw();
        }

        class TeamGTab extends GridScreenTab {
            private static final Text TEAM_TAB_TITLE = Text.of("Teams");
            private final TeamPlayer teamPlayer;
            private static final MinecraftClient client = MinecraftClient.getInstance();
            private final AddTeamWidget atw;

            TeamGTab(AddTeamWidget atz) {
                super(TEAM_TAB_TITLE);
                this.teamPlayer = PlayerTeamRegistry.getPlayerByUUID(client.player.getUuidAsString());
                this.atw = atz;
            }

            public void redraw() {
                GridWidget.Adder adder = this.grid.setRowSpacing(10).setColumnSpacing(40).createAdder(2);
                adder.add(new TeamListWidget(this.teamPlayer));
                adder.add(new PlayerListTeamWidget());
            }

            class TeamListWidget extends GridWidget {
                TeamListWidget(TeamPlayer teamPlayer) {
                    super();
                    this.width = 300;
                    GridWidget.Adder adder = this.setColumnSpacing(10).setRowSpacing(8).createAdder(1);
                    adder.add(new TextWidget(Text.of("Teams"), MinecraftClient.getInstance().textRenderer));
                    adder.add(atw);
                    for (int i = 1; i <= TeamRegistry.getTeams().size()  ; i++) {
                        adder.add(TeamSettingsWidget.builder(TeamRegistry.getTeams().get(i).teamId,teamPlayer).build());
                    }
                }
            }

            class PlayerListTeamWidget extends GridWidget {
                PlayerListTeamWidget() {
                    super();
                    this.width = 300;
                    GridWidget.Adder adder = this.setColumnSpacing(10).setRowSpacing(8).createAdder(1);
                    Positioner positioner = adder.copyPositioner();
                    adder.add(new TextWidget(Text.of("Players"), MinecraftClient.getInstance().textRenderer), positioner.alignLeft());
                    for (int i = 0; i < PlayerTeamRegistry.getAllPlayers().size(); i++) {
                        adder.add(new PlayerTeamSelectWidget(PlayerTeamRegistry.getAllPlayers().get(i)), positioner.alignLeft());
                    }
                }

                public void redraw() {

                }

            }


        }
    }


    class NewLockoutTab extends GridScreenTab {
        private static final Text NEW_LOCKOUT_TITLE = Text.of("New Lockout");

        NewLockoutTab() {
            super(NEW_LOCKOUT_TITLE);
        }
    }

    class BoardTab extends GridScreenTab {

        BoardTab() {
            super(title);
        }
    }









}
