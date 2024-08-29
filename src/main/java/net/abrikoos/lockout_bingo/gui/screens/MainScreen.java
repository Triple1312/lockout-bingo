package net.abrikoos.lockout_bingo.gui.screens;

import net.abrikoos.lockout_bingo.modes.team.LockoutTeamDataClass;
import net.abrikoos.lockout_bingo.network.game.CreateBlackoutRequestPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutAddTeamPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutJoinTeamPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutRemoveTeamPacket;
import net.abrikoos.lockout_bingo.team.Colors;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import net.abrikoos.lockout_bingo.team.TeamRegistry;

import java.util.List;
import java.util.function.Function;

public class MainScreen extends Screen {

    TextFieldWidget teamaddWidget;

    public MainScreen(Function<Void, Void> leaveScreen) {
        super(Text.of("main"));
//        TeamRegistry.subscribe(this::teamsupdate); // todo
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
                widg.active = false;
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
