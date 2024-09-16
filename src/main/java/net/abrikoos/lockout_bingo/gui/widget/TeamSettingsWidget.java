package net.abrikoos.lockout_bingo.gui.widget;

import net.abrikoos.lockout_bingo.network.team.LockoutJoinTeamPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutRemoveTeamPacket;
import net.abrikoos.lockout_bingo.team.Colors;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.abrikoos.lockout_bingo.team.TeamPlayer;
import net.abrikoos.lockout_bingo.team.TeamRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.world.EditGameRulesScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.text.Text;

public class TeamSettingsWidget extends GridWidget {
    final boolean leftbutton;
    final int teamId;
    final TeamPlayer player;

    TeamSettingsWidget(TeamSettingsWidget.Builder builder) {
        super();
        this.leftbutton = builder.leftbutton;
        this.teamId = builder.teamId;
        this.player = builder.player;
        this.draw();
    }

    public void draw() {
        Adder adder = this.setColumnSpacing(4).createAdder(2);
        Positioner positioner = adder.copyPositioner();
        LockoutTeam team = TeamRegistry.getTeam(teamId);
        if (team == null) {
            return;
        }
        assert team != null;
        Text teamname = Text.literal(team.name).withColor(Colors.get(teamId));
        ButtonWidget teamNameButton = ButtonWidget.builder(
                teamname,
                btn -> {
                    ClientPlayNetworking.send(new LockoutJoinTeamPacket(teamId));
                }
        ).build();
        ButtonWidget bbtn;
        if (player.teamIndex == teamId) {
            teamNameButton.active = false;
            bbtn = ColoredButton.builderc(
                    Text.of("Leave"),
                    (ColoredButton.PressAction) btn -> {
                        ClientPlayNetworking.send(new LockoutJoinTeamPacket(0));
                    }
            ).color(teamId).build();
        }
        else {
            bbtn = ButtonWidget.builder(
                    Text.of("X"),
                    btn -> {
                        ClientPlayNetworking.send(new LockoutRemoveTeamPacket(team.name));
                    }
            ).width(20).build();
            if (team.playeruuids.size() != 0) {
                bbtn.active = false;
            }
        }

        if (leftbutton) {
            adder.add(bbtn, positioner.alignRight());
            adder.add(teamNameButton);
        }
        else {
            adder.add(teamNameButton);
            adder.add(bbtn, positioner.alignLeft());
        }

    }

    public static TeamSettingsWidget.Builder builder(int teamId, TeamPlayer player) {
        return new TeamSettingsWidget.Builder(teamId, player);
    }


    @Environment(EnvType.CLIENT)
    public static class Builder {
        private int teamId = 0;
        private final TeamPlayer player;
        private boolean leftbutton = false;

        public Builder(int teamId, TeamPlayer player) {
            this.teamId = teamId;
            this.player = player;
        }

        public TeamSettingsWidget.Builder teamId(int teamId) {
            this.teamId = teamId;
            return this;
        }

        public TeamSettingsWidget build() {
            return new TeamSettingsWidget(this);
        }



    }
}
