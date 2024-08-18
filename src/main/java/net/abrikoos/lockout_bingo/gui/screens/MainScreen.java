package net.abrikoos.lockout_bingo.gui.screens;

import net.abrikoos.lockout_bingo.modes.team.LockoutTeamDataClass;
import net.abrikoos.lockout_bingo.modes.team.TeamRegistry;
import net.abrikoos.lockout_bingo.network.game.CreateBlackoutRequestPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutAddTeamPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class MainScreen extends Screen {

    TextFieldWidget teamaddWidget;

    public MainScreen(Function<Void, Void> leaveScreen) {
        super(Text.of("main"));
        TeamRegistry.subscribe(this::teamsupdate);
        this.init();
    }

    protected void init() {
        // gamemodes buttons
        ButtonWidget nl = ButtonWidget.builder(
                Text.of("New Lockout"), (btn) -> {
                    assert this.client != null;
                    this.client.setScreen(new LockoutCreateScreen(null)); // todo pass teams
                }
        ).dimensions(40, 40, 120, 20).build();
        nl.active = false;
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

        // new team name input
//        this.teamaddWidget = new TextFieldWidget(this.textRenderer, 40, 100, 120, 20, Text.of("Team Name"));
//        this.teamaddWidget.setMaxLength(20);
//        this.addDrawableChild(this.teamaddWidget);
//        this.addSelectableChild(this.teamaddWidget);
//        this.addDrawableChild(ButtonWidget.builder(Text.of("Add"), (btn) -> {
//            ClientPlayNetworking.send(new LockoutAddTeamPacket(this.teamaddWidget.getText()));
//
//        }).dimensions(40, 10, 20, 20).build());
        MinecraftClient client = MinecraftClient.getInstance();


        List<LockoutTeamDataClass> teams = TeamRegistry.teams;
        // teams buttons
        for (int i = 0; i < teams.size(); i++) {
            ButtonWidget wdgt = ButtonWidget.builder(
                    Text.of("Join " + teams.get(i).name()), (btn) -> {
                        assert this.client != null;
                        this.client.setScreen(new LockoutCreateScreen(teams));
                    }
            ).dimensions(40, 40 + (i * 20), 120, 20).build();
            for ( String name: teams.get(i).playernames()) {
                assert client.player != null;
                if (Objects.equals(name, client.player.getName().toString())) {
                    wdgt.active = false;
                }
            }
            StringBuilder tooltip = new StringBuilder();
            tooltip.append("Players:\n");
            for (String name: teams.get(i).playernames()) {
                tooltip.append(name).append("\n");
            }
            wdgt.setTooltip(Tooltip.of(Text.of(tooltip.toString())));
            this.addDrawableChild(wdgt);
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

}
