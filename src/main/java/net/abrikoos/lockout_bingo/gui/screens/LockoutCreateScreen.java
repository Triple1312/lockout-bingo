package net.abrikoos.lockout_bingo.gui.screens;

import net.abrikoos.lockout_bingo.modes.team.LockoutTeamDataClass;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class LockoutCreateScreen extends Screen {


    public LockoutCreateScreen(List<LockoutTeamDataClass> teams) {
        super(Text.of("Create new lockout"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }


    public void setTeams(List<LockoutTeamDataClass> teams) {

    }


    public void addTeam(LockoutTeamDataClass team) {

    }



}
