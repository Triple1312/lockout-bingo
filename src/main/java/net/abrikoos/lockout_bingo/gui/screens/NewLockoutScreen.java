package net.abrikoos.lockout_bingo.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.gui.LockoutScreens;
import net.abrikoos.lockout_bingo.gui.screens.tabscreen.LockoutTabManager;
import net.abrikoos.lockout_bingo.gui.screens.tabscreen.LockoutTabNavigationWidget;
import net.abrikoos.lockout_bingo.gui.widget.LockoutSlider;
import net.abrikoos.lockout_bingo.team.Colors;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.abrikoos.lockout_bingo.team.TeamRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewLockoutScreen extends Screen {
    final Screen parent;
    private static final int field_42170 = 10;
    private static final int field_42171 = 8;
    private static final int field_42165 = 1;
    private static final int field_42166 = 210;
    public static final Identifier TAB_HEADER_BACKGROUND_TEXTURE = Identifier.ofVanilla("textures/gui/tab_header_background.png");
    LockoutTabNavigationWidget tabNavigation;
    LockoutTabManager tabManager = new LockoutTabManager(this::addDrawableChild, this::remove);
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    MainTab mainTab = new MainTab(this);
    GoalTypesTab goalTypesTab = new GoalTypesTab();
    ModifiersTab modifiersTab = new ModifiersTab();
    MarkedStructuresTab markedStructuresTab = new MarkedStructuresTab();
    public boolean modifiers = false;


    protected NewLockoutScreen(Screen parent) {
        super(Text.of("New Lockout"));
        this.parent = parent;
    }

    protected void init() {
        this.children().clear();
        List<Tab> tabs = new ArrayList<>();
        tabs.add(mainTab); tabs.add(goalTypesTab);
        if (modifiersTab != null) {
            tabs.add(modifiersTab);
        }
        if (markedStructuresTab != null) {
            tabs.add(markedStructuresTab);
        }
        this.tabNavigation = LockoutTabNavigationWidget.builder(this.tabManager, this.width)
                .tabs(tabs)
                .build();
        this.addDrawableChild(this.tabNavigation);
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(ButtonWidget.builder(Text.translatable("Start Lockout"), button -> {}).width(80).build());
        directionalLayoutWidget.add(ButtonWidget.builder(Text.translatable("Start Blackout"), button -> {}).width(80).build());
        directionalLayoutWidget.add(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.close()).width(80).build());
        this.layout.forEachChild(child -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.tabNavigation.selectTab(0, false);
        this.initTabNavigation();
    }

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

    public void setEnableModifiers(boolean enable) {
//        if (enable) {
//            this.modifiersTab = new ModifiersTab();
//        } else {
//            this.modifiersTab = null;
//        }
//        this.init();
    }

    public void setEnableMarkedStructures(boolean enable) {
//        if (enable) {
//            this.markedStructuresTab = new MarkedStructuresTab();
//        } else {
//            this.markedStructuresTab = null;
//        }
//        this.init();
    }



    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
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


    class MainTab extends GridScreenTab {
        NewLockoutScreen parent;
        CyclingButtonWidget<LockoutTeam> team1;
        CyclingButtonWidget<LockoutTeam> team2;


        public MainTab(NewLockoutScreen parent) {
            super(Text.of("Game"));
            this.parent = parent;

            team1 = CyclingButtonWidget.<LockoutTeam>builder(
                    team -> Text.literal(team.name).withColor(Colors.get(team.teamId))).values(TeamRegistry.getTeams()).build(Text.of("Team 1"), (buttonWidget, val) -> {}
            );
            team2 = CyclingButtonWidget.<LockoutTeam>builder(
                    team -> Text.literal(team.name).withColor(Colors.get(team.teamId))).values(TeamRegistry.getTeams()).build(Text.of("Team 2"), (buttonWidget, val) -> {}
            );


            GridWidget.Adder adder = this.grid.setRowSpacing(8).setColumnSpacing(10).createAdder(2);
            adder.add(team1);
            adder.add(team2);
            CyclingButtonWidget<Integer> cdiff = CyclingButtonWidget.<Integer>builder(i -> Text.of(i.toString())).values(Arrays.asList(3, 4, 5, 1, 2)).build(Text.of("Difficulty"), (buttonWidget, val) -> {
                        return;
            });
            CyclingButtonWidget<Integer> cgcount = CyclingButtonWidget.<Integer>builder(i -> Text.of(i.toString())).values(Arrays.asList(25, 49, 9)).build(Text.of("Goal amount"), (buttonWidget, val) -> {
                return;
            });
            adder.add(cdiff);
            adder.add(cgcount);


//            adder.add(new TextWidget(Text.of("Difficulty:"), MinecraftClient.getInstance().textRenderer));
//            adder.add(new TextWidget(Text.of("Goal Count:"), MinecraftClient.getInstance().textRenderer));
//            adder.add(LockoutSlider.<Integer>builder(Text.of("3"), e->{}).values(Arrays.asList(1, 2, 3, 4, 5)).defaultValue(3).build() );
//            adder.add(LockoutSlider.<Integer>builder(Text.of("25"), e->{}).values(Arrays.asList(9, 25, 49)).defaultValue(25).build());
            CyclingButtonWidget<Boolean> cbw = adder.add(
                    CyclingButtonWidget.onOffBuilder(Text.of("ON"), Text.of("OFF"))
                            .build(Text.of("Modifiers"), (buttonWidget, val) -> {
                                setEnableModifiers(val);
                            })
            ); cbw.setValue(false); cbw.active = false;
            CyclingButtonWidget<Boolean> cbw2 = adder.add(
                    CyclingButtonWidget.onOffBuilder(Text.of("ON"), Text.of("OFF"))
                            .build(Text.of("Marked Structures"), (buttonWidget, val) -> {
                                setEnableMarkedStructures(val);
                            })
            ); cbw2.setValue(false); cbw2.active = false;


        }



    }

    class GoalTypesTab extends GridScreenTab {

        public GoalTypesTab() {
            super(Text.of("Goals"));
            GridWidget.Adder adder = this.grid.setRowSpacing(8).setColumnSpacing(10).createAdder(2);
            CyclingButtonWidget<Boolean> cbd1 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("End goals"), (button, val) -> {}));
            CyclingButtonWidget<Boolean> cbd2 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Nether goals"), (button, val) -> {}));
            CyclingButtonWidget<Boolean> cbd3 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Redstone goals"), (button, val) -> {}));
            CyclingButtonWidget<Boolean> cbd4 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Death goals"), (button, val) -> {}));
            CyclingButtonWidget<Boolean> cbd5 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Opponent goals"), (button, val) -> {}));
            CyclingButtonWidget<Boolean> cbd6 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Biome goals"), (button, val) -> {}));
            CyclingButtonWidget<Boolean> cbd7 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Food goals"), (button, val) -> {}));
            CyclingButtonWidget<Boolean> cbd8 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Kill goals"), (button, val) -> {}));
            CyclingButtonWidget<Boolean> cbd9 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Move goals"), (button, val) -> {}));
        }
    }

    class ModifiersTab extends GridScreenTab {

        public ModifiersTab() {
            super(Text.of("Modifiers"));
            GridWidget.Adder adder = this.grid.createAdder(1);
            adder.add(new TextWidget(Text.of("Modifiers are not implemented yet"), MinecraftClient.getInstance().textRenderer));

        }
    }

    class MarkedStructuresTab extends GridScreenTab {

        public MarkedStructuresTab() {
            super(Text.of("Marked Structures"));
            GridWidget.Adder adder = this.grid.createAdder(1);
            adder.add(new TextWidget(Text.of("Marked structures is not implemented yet"), MinecraftClient.getInstance().textRenderer));

        }
    }
}
