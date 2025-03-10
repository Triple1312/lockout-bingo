package net.abrikoos.lockout_bingo.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.client.gui.screens.tabscreen.LockoutTabManager;
import net.abrikoos.lockout_bingo.client.gui.screens.tabscreen.LockoutTabNavigationWidget;
import net.abrikoos.lockout_bingo.networkv2.game.StartGameRequestPacket;
import net.abrikoos.lockout_bingo.networkv2.team.Colors;
import net.abrikoos.lockout_bingo.networkv2.team.TeamData;
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

public class NewDropShuffleScreen extends Screen {
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
    ModifiersTab modifiersTab = new ModifiersTab();
    MarkedStructuresTab markedStructuresTab = new MarkedStructuresTab();
    public boolean modifiers = false;
    ButtonWidget startDropShuffleButton;
    ButtonWidget startDropShuffleSoloButton;


    protected NewDropShuffleScreen(Screen parent) {
        super(Text.of("New Lockout"));
        this.parent = parent;
        startDropShuffleButton = ButtonWidget.builder(Text.translatable("Start DropShuffle"), button -> {
            startDropShuffle();
        }).width(80).build();
        startDropShuffleSoloButton = ButtonWidget.builder(Text.translatable("Start DropShuffle Solo"), button -> {
        }).width(80).build();
        startDropShuffleSoloButton.active = false;
    }

    protected void init() {
        this.children().clear();
        List<Tab> tabs = new ArrayList<>();
        tabs.add(mainTab);
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
        directionalLayoutWidget.add(startDropShuffleButton);
        directionalLayoutWidget.add(startDropShuffleSoloButton);
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
            this.startDropShuffleButton.active = false;
        } else {
            this.startDropShuffleButton.active = true;
        }
    }

    public void startDropShuffle() {
        List<String> goalTypes = new ArrayList<>();
        List<String> modifiers = new ArrayList<>();
        int difficulty = mainTab.difficulty.getValue();
        int goalCount = mainTab.goalCount.getValue();

        ArrayList<String> teams = new ArrayList<>();
        teams.add(mainTab.team1.getValue().teamUUID);
        teams.add(mainTab.team2.getValue().teamUUID);

        StartGameRequestPacket packet = new StartGameRequestPacket("dropshuffle", teams, difficulty, goalCount, goalTypes, modifiers);

        ClientPlayNetworking.send(packet);
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
        NewDropShuffleScreen parent;
        CyclingButtonWidget<TeamData> team1;
        CyclingButtonWidget<TeamData> team2;
        CyclingButtonWidget<Integer> difficulty;
        CyclingButtonWidget<Integer> goalCount;

        public MainTab(NewDropShuffleScreen parent) {
            super(Text.of("Game"));
            this.parent = parent;
            GridWidget.Adder adder = this.grid.setRowSpacing(8).setColumnSpacing(10).createAdder(2);
            Positioner positioner = adder.copyPositioner();
            if (ClientGameStateV2.teamReg.teamCount() < 2) {
                adder.add(new TextWidget(Text.of("Not enough teams"), MinecraftClient.getInstance().textRenderer), 2, positioner.alignHorizontalCenter());
            } else {
                team1 = CyclingButtonWidget.<TeamData>builder(
                        team -> Text.literal(team.teamName).withColor(Colors.get(team.teamColor))).values(ClientGameStateV2.teamReg.teams).build(Text.of("Team 1"), (buttonWidget, val) -> {
                            updateUI();
                        }
                );
                team2 = CyclingButtonWidget.<TeamData>builder(
                        team -> Text.literal(team.teamName).withColor(Colors.get(team.teamColor))).values(ClientGameStateV2.teamReg.teams).build(Text.of("Team 2"), (buttonWidget, val) -> {
                            updateUI();
                        }
                );
                adder.add(team1);
                adder.add(team2);
            }

            difficulty = CyclingButtonWidget.<Integer>builder(i -> Text.of(i.toString())).values(Arrays.asList(3, 4, 5, 1, 2)).build(Text.of("Difficulty"), (buttonWidget, val) -> {
                return;
            });
            difficulty.active = false;
            goalCount = CyclingButtonWidget.<Integer>builder(i -> Text.of(i.toString())).values(Arrays.asList(25, 9, 49)).build(Text.of("Goal amount"), (buttonWidget, val) -> {
                return;
            });
//            goalCount.active = false;
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
            );
            cbw.setValue(false);
            cbw.active = false;
            CyclingButtonWidget<Boolean> cbw2 = adder.add(
                    CyclingButtonWidget.onOffBuilder(Text.of("ON"), Text.of("OFF"))
                            .build(Text.of("Marked Structures"), (buttonWidget, val) -> {
                                setEnableMarkedStructures(val);
                            })
            );
            cbw2.setValue(false);
            cbw2.active = false;


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