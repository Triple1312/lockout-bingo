package net.abrikoos.lockout_bingo.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.client.gui.screens.tabscreen.LockoutTabManager;
import net.abrikoos.lockout_bingo.client.gui.screens.tabscreen.LockoutTabNavigationWidget;
import net.abrikoos.lockout_bingo.network.game.CreateLockoutPacket;
import net.abrikoos.lockout_bingo.team.Colors;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.abrikoos.lockout_bingo.team.TeamRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
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

@Environment(EnvType.CLIENT)
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
    ButtonWidget startLockoutButton;
    ButtonWidget startBlackoutButton;


    protected NewLockoutScreen(Screen parent) {
        super(Text.of("New Lockout"));
        this.parent = parent;
        startLockoutButton = ButtonWidget.builder(Text.translatable("Start Lockout"), button -> {startLockout();}).width(80).build();
        startBlackoutButton = ButtonWidget.builder(Text.translatable("Start Blackout"), button -> {}).width(80).build();
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
        directionalLayoutWidget.add(startLockoutButton);
        directionalLayoutWidget.add(startBlackoutButton);
        directionalLayoutWidget.add(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.close()).width(80).build());
        this.layout.forEachChild(child -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.tabNavigation.selectTab(0, false);
        this.initTabNavigation();
        updateUI();
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

    public void updateUI() {
        if (mainTab.team1 == null || mainTab.team2 == null || mainTab.team1.getValue() == mainTab.team2.getValue()) {
            this.startLockoutButton.active = false;
        }
        else {
            this.startLockoutButton.active = true;
        }
    }

    public void startLockout() {
        List<Integer> goalTypes = new ArrayList<>();
        List<Integer> modifiers = new ArrayList<>();
        int difficulty = mainTab.difficulty.getValue();
        int goalCount = mainTab.goalCount.getValue();

        if (!goalTypesTab.end.getValue()) {
            goalTypes.add(0);
        }
        if (!goalTypesTab.nether.getValue()) {
            goalTypes.add(1);
        }
        if (!goalTypesTab.redstone.getValue()) {
            goalTypes.add(2);
        }
        if (!goalTypesTab.death.getValue()) {
            goalTypes.add(3);
        }
        if (!goalTypesTab.opponent.getValue()) {
            goalTypes.add(4);
        }
        if (!goalTypesTab.biome.getValue()) {
            goalTypes.add(5);
        }
        if (!goalTypesTab.food.getValue()) {
            goalTypes.add(7);
        }
        if (!goalTypesTab.kill.getValue()) {
            goalTypes.add(8);
        }
        if (!goalTypesTab.move.getValue()) {
            goalTypes.add(9);
        }

        ClientPlayNetworking.send(new CreateLockoutPacket(
                Arrays.asList(mainTab.team1.getValue().teamId, mainTab.team2.getValue().teamId),
                difficulty,
                goalCount,
                goalTypes,
                modifiers
        ));
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
        CyclingButtonWidget<Integer> difficulty;
        CyclingButtonWidget<Integer> goalCount;

        public MainTab(NewLockoutScreen parent) {
            super(Text.of("Game"));
            this.parent = parent;
            GridWidget.Adder adder = this.grid.setRowSpacing(8).setColumnSpacing(10).createAdder(2);
            Positioner positioner = adder.copyPositioner();
            if (TeamRegistry.getTeams().size() < 2) {
                adder.add(new TextWidget(Text.of("Not enough teams"), MinecraftClient.getInstance().textRenderer), 2, positioner.alignHorizontalCenter() );
            }
            else {
                team1 = CyclingButtonWidget.<LockoutTeam>builder(
                        team -> Text.literal(team.name).withColor(Colors.get(team.teamId))).values(TeamRegistry.getTeams()).build(Text.of("Team 1"), (buttonWidget, val) -> {updateUI();}
                );
                team2 = CyclingButtonWidget.<LockoutTeam>builder(
                        team -> Text.literal(team.name).withColor(Colors.get(team.teamId))).values(TeamRegistry.getTeams()).build(Text.of("Team 2"), (buttonWidget, val) -> {updateUI();}
                );
                adder.add(team1);
                adder.add(team2);
            }

            difficulty = CyclingButtonWidget.<Integer>builder(i -> Text.of(i.toString())).values(Arrays.asList(3, 4, 5, 1, 2)).build(Text.of("Difficulty"), (buttonWidget, val) -> {
                        return;
            });
            goalCount = CyclingButtonWidget.<Integer>builder(i -> Text.of(i.toString())).values(Arrays.asList(25, 49, 9)).build(Text.of("Goal amount"), (buttonWidget, val) -> {
                return;
            });
            adder.add(difficulty);
            adder.add(goalCount);


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
        CyclingButtonWidget<Boolean> end;
        CyclingButtonWidget<Boolean> nether;
        CyclingButtonWidget<Boolean> redstone;
        CyclingButtonWidget<Boolean> death;
        CyclingButtonWidget<Boolean> opponent;
        CyclingButtonWidget<Boolean> biome;
        CyclingButtonWidget<Boolean> food;
        CyclingButtonWidget<Boolean> kill;
        CyclingButtonWidget<Boolean> move;
        CyclingButtonWidget<Boolean> breed;
        CyclingButtonWidget<Boolean> obtain;
        CyclingButtonWidget<Boolean> armor;
        CyclingButtonWidget<Boolean> tools;
        CyclingButtonWidget<Boolean> ride;

        public GoalTypesTab() {
            super(Text.of("Goals"));
            GridWidget.Adder adder = this.grid.setRowSpacing(8).setColumnSpacing(10).createAdder(3);
            end = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("End goals"), (button, val) -> {}));
            nether = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Nether goals"), (button, val) -> {}));
            redstone = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Redstone goals"), (button, val) -> {}));
            death = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Death goals"), (button, val) -> {}));
            opponent = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Opponent goals"), (button, val) -> {}));
            biome = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Biome goals"), (button, val) -> {}));
            food = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Food goals"), (button, val) -> {}));
            kill = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Kill goals"), (button, val) -> {}));
            move = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Move goals"), (button, val) -> {}));
            breed = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Breed goals"), (button, val) -> {}));
            obtain = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Obtain goals"), (button, val) -> {}));
            armor = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Armor goals"), (button, val) -> {}));
            tools = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Tool goals"), (button, val) -> {}));
            ride = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Ride goals"), (button, val) -> {}));
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
