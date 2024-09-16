package net.abrikoos.lockout_bingo.gui.screens;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.gui.LockoutScreens;
import net.abrikoos.lockout_bingo.gui.widget.ColorSelectButtonWidget;
import net.abrikoos.lockout_bingo.gui.widget.ColoredButton;
import net.abrikoos.lockout_bingo.gui.widget.TeamColorSelectWidget;
import net.abrikoos.lockout_bingo.modes.team.LockoutTeamDataClass;
import net.abrikoos.lockout_bingo.network.game.CreateBlackoutRequestPacket;
import net.abrikoos.lockout_bingo.network.team.ChangeTeamIdPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutAddTeamPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutJoinTeamPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutRemoveTeamPacket;
import net.abrikoos.lockout_bingo.team.Colors;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import net.abrikoos.lockout_bingo.team.TeamRegistry;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class MainScreen extends Screen {

    TextFieldWidget teamaddWidget;

    boolean toggleColorSelector = false;

    public MainScreen(Function<Void, Void> leaveScreen) {
        super(Text.of("main"));
//        TeamRegistry.subscribe(this::teamsupdate); // todo
        this.init();
    }

    protected void init() {
//        toggleColorSelector = false;
        // gamemodes buttons
        this.clearChildren();
        int colorDrawerY = 0;
        ButtonWidget nl = ButtonWidget.builder(
                Text.of("New Lockout"), (btn) -> {
                    assert this.client != null;
                    this.client.setScreen(new LockoutCreateScreen(null)); // todo pass teams
                }
        ).dimensions(40, 40, 120, 20).build();
        this.addDrawableChild(nl);
        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.of("New Blackout"), (btn) -> {
                            assert this.client != null;
                            ClientPlayNetworking.send(new CreateBlackoutRequestPacket(1));

//                            this.client.setScreen(new JoinServerScreen()); // todo no screen needed
                        }
                ).dimensions(200, 40, 120, 20).build()
        );

        if (this.client != null) {
            TextFieldWidget tfw = new TextFieldWidget(this.textRenderer, 40, 80, 120, 20, Text.of("Team Name"));
            this.addDrawableChild(tfw);
            this.addDrawableChild( ButtonWidget.builder(Text.of("Add"), (btn) -> {
                ClientPlayNetworking.send(new LockoutAddTeamPacket(tfw.getText()));
            }).dimensions(165, 80, 45, 20).build());
        }

        for (int i = 0; i < TeamRegistry.getTeams().size(); i++) {
            LockoutTeam lt = TeamRegistry.getTeams().get(i);
            ButtonWidget widg = ButtonWidget.builder(Text.literal(lt.name).withColor(Colors.get(lt.teamId)), (btn) -> {
                ClientPlayNetworking.send(new LockoutJoinTeamPacket(lt.teamId));
            }).dimensions(40, 120 + i * 30, 120, 20).build();
            ButtonWidget delete = ButtonWidget.builder(Text.of("X"), (btn) -> {
                ClientPlayNetworking.send(new LockoutRemoveTeamPacket(lt.name));
            }).dimensions(170, 120 + i * 30, 20, 20).build();
            assert this.client != null;
            if (lt.playeruuids.contains(this.client.player.getUuidAsString())) {
                if (!this.toggleColorSelector) {
                    this.addDrawableChild(
                        new ColoredButton(170, 120 + i * 30, 20, 20, Text.of(""), (btn) -> {
                            this.toggleColorSelector(Colors.get(lt.teamId));
                        }, Colors.get(lt.teamId))
                    );
                }
                else {
                    colorDrawerY = 100 + i * 30;
                }
            }
            String playerNames = "";
            for (String player : lt.playeruuids) {
                playerNames += player + "\n";
            }
            widg.setTooltip(Tooltip.of(Text.of(playerNames)));
            this.addDrawableChild(widg);
            if (lt.playeruuids.isEmpty()) {
                this.addDrawableChild(delete);
            }

        }
        if (this.toggleColorSelector) {
            this.drawColorSelect(200, colorDrawerY);
        }


    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (keyCode == GLFW.GLFW_KEY_B) {
            client.setScreen(null);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public Void teamsupdate(List<LockoutTeamDataClass> teams) {
        this.init();
        return null;
    }

    public void toggleColorSelector(int oldColor) {
        toggleColorSelector = !toggleColorSelector;
        LockoutLogger.log("toggleColorSelector: " + toggleColorSelector);
        MinecraftClient client = MinecraftClient.getInstance();
        assert client != null;
        this.init();
        LockoutScreens.open();
    }

    protected void colorSelected(int color) {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client != null;
        ClientPlayNetworking.send(new ChangeTeamIdPacket(Colors.getTeamIndex(Colors.getPlayerColor(client.player.getUuidAsString())), Colors.getTeamIndex(color)));
        this.toggleColorSelector(color);
        this.init();
        LockoutScreens.open();
    }

    public void drawColorSelect( int x, int y) {
        int widthheight = 30;
        int colorcount = 10;
        int padding = 10;

//        ctx.fill(x, y, x + 5*widthheight + 6*padding, y + 2*widthheight + 3*padding, Colors.get(0));
        for (int i = 0; i < colorcount; i++) {
            int xi = i % 5;
            int yi = i / 5;
            int tmp_color = Colors.get(i+1);
            ColoredButton bt = new ColoredButton(x + xi * (widthheight + padding) + padding, y + yi * (widthheight + padding) + padding, widthheight, widthheight, Text.of(""), (btn) -> {
                colorSelected(tmp_color);
            } , tmp_color);
            this.addDrawableChild(bt);
        }
    }
}
