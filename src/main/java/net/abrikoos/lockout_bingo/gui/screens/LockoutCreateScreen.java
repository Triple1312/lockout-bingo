package net.abrikoos.lockout_bingo.gui.screens;

import net.abrikoos.lockout_bingo.gui.LockoutScreens;
import net.abrikoos.lockout_bingo.modes.team.LockoutTeamDataClass;
import net.abrikoos.lockout_bingo.network.game.CreateLockoutPacket;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.abrikoos.lockout_bingo.team.TeamController;
import net.abrikoos.lockout_bingo.team.TeamRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class LockoutCreateScreen extends Screen {

    SimpleOption.OptionSliderWidgetImpl<Integer> slider;
    CheckboxWidget end;
    CheckboxWidget nether;
    List<CheckboxWidget> teams = new ArrayList<>();

    public LockoutCreateScreen(List<LockoutTeamDataClass> teams) {
        super(Text.of("Create new lockout"));
        try {
            drawInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }


    public void setTeams(List<LockoutTeamDataClass> teams) {

    }


    public void addTeam(LockoutTeamDataClass team) {

    }

    private void drawInit() {
        end = null;
        nether = null;
        teams.clear();
//        assert this.client != null;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.of("Back"), (btn) -> {
                            client.setScreen(LockoutScreens.mainScreen);
                        }
                ).dimensions(40, 40, 120, 20).build()
        );
        this.end = CheckboxWidget.builder(
                Text.of("End"), textRenderer
        ).pos(200, 40).checked(true).build();
        this.nether = CheckboxWidget.builder(
                Text.of("Nether"), textRenderer
        ).pos(200, 80).checked(true).build();
        this.addDrawableChild(
                end
        );
        this.addDrawableChild(
                nether
        );

        List<LockoutTeam> ts = TeamRegistry.getTeams();
        for (int i = 0; i < ts.size(); i++) {
            teams.add(
                    CheckboxWidget.builder(
                            Text.of(ts.get(i).name), textRenderer
                    ).pos(40, 100 + 40 *i).build()
            );
            this.addDrawableChild(
                    teams.get(i)
            );
        }
        this.addDrawableChild(
            ButtonWidget.builder(
                Text.of("Start"), (btn) -> {

                    CreateLockoutPacket packet = new CreateLockoutPacket(
                            List.of(TeamRegistry.getTeamString(this.teams.get(0).getMessage().getString()).teamId, TeamRegistry.getTeamString(this.teams.get(1).getMessage().getString()).teamId),
                            0,
                            end.isChecked(),
                            nether.isChecked()
                    );
                    ClientPlayNetworking.send(packet);
                }
            ).dimensions(300, 300, 120, 20).build()
        );

    }

}
