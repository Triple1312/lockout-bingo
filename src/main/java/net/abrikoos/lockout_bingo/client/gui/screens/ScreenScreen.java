package net.abrikoos.lockout_bingo.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.client.gui.screens.tabscreen.LockoutTabManager;
import net.abrikoos.lockout_bingo.client.gui.screens.tabscreen.LockoutTabNavigationWidget;
import net.abrikoos.lockout_bingo.client.gui.tabs.BoardTab3;
import net.abrikoos.lockout_bingo.client.gui.tabs.TeamsTab;
import net.abrikoos.lockout_bingo.networkv2.team.TeamRegV2;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ScreenScreen extends Screen {

    private LockoutTabNavigationWidget tabNavigation;
    private LockoutTabManager tabManager; // = new LockoutTabManager(this::addDrawableChild, this::remove);
    private ThreePartsLayoutWidget layout;
    public static final Identifier TAB_HEADER_BACKGROUND_TEXTURE = Identifier.ofVanilla("textures/gui/tab_header_background.png");
    public BoardTab3 boardTab = new BoardTab3();


    public ScreenScreen() {
        super(Text.of("Complete Full Screen"));
        init();
        ClientGameStateV2.subscribeToTeamsUpdate(this::teamsChanged);
    }

    public void init() {
        this.clearChildren();
        this.layout = new ThreePartsLayoutWidget(this);
        this.layout.setFooterHeight(0);
        this.tabManager = new LockoutTabManager(this::addDrawableChild, this::remove);
        this.tabManager.subscribeTabChangeEvent(this::tabchanged);
        if (ClientGameStateV2.isGameRunning()) {
            this.tabNavigation = LockoutTabNavigationWidget.builder(this.tabManager, this.width)
                    .tabs( new MainTab(), boardTab, new TeamsTab(this))
                    .build();
        }
        else {
            this.tabNavigation = LockoutTabNavigationWidget.builder(this.tabManager, this.width)
                    .tabs( new MainTab(), new TeamsTab(this))
                    .build();
        }
//        this.tabNavigation = LockoutTabNavigationWidget.builder(this.tabManager, this.width)
//                .tabs(new MainTab(), boardTab, new TeamsTab(this))
//                .build();
        this.addDrawableChild(this.tabNavigation);
        this.layout.forEachChild(child -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.tabNavigation.selectTab(CompleteFullScreenState.selectedTab, false);
        this.initTabNavigation();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_B) {
            if (ClientGameStateV2.gameHasStarted()) {
                if (this.tabManager.getCurrentTab().getTitle().getString().equals("TeamsTab")) {
                    return false;
                }
                this.close();
                return true;
            } else {
                return false;
            }
        }
        if (keyCode == GLFW.GLFW_KEY_P) {

            this.close();
            return true;

        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            if (ClientGameStateV2.gameHasStarted()) {
                return super.keyPressed(keyCode, scanCode, modifiers);
            } else {
                return false;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void open() {
        tabNavigation.selectTab(CompleteFullScreenState.selectedTab, false);
        assert client != null;
        client.setScreen(this);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        try {
            super.render(context, mouseX, mouseY, delta);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
        return this.width * p / 100;
    }

    public int vh(int p) {
        return this.height * p / 100;
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

    public void teamsChanged(TeamRegV2 reg) {
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
                    Text.of("Drop Shuffle"), btn -> {
                        client.setScreen(new NewDropShuffleScreen(ScreenScreen.this));
                    }
                ).width(120).build(),
                positioner
            );
//            lbbtn.active = false;

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
}
