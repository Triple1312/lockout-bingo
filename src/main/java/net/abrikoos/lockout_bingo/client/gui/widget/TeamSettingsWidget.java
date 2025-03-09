package net.abrikoos.lockout_bingo.client.gui.widget;

import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.network.team.LockoutJoinTeamPacket;
import net.abrikoos.lockout_bingo.network.team.LockoutRemoveTeamPacket;
import net.abrikoos.lockout_bingo.networkv2.team.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.text.Text;

public class TeamSettingsWidget extends GridWidget {
    final boolean leftbutton;
    final TeamData team;
    final PlayerData player;

    TeamSettingsWidget(TeamSettingsWidget.Builder builder) {
        super();
        this.leftbutton = builder.leftbutton;
        this.team = builder.teamD;
        this.player = builder.player;
        this.draw();
    }

    public void draw() {
        Adder adder = this.setColumnSpacing(4).createAdder(2);
        Positioner positioner = adder.copyPositioner();
        if (team == null) {
            return;
        }
        assert team != null;
        Text teamname = Text.literal(team.teamName).withColor(Colors.get(team.teamColor));
        ButtonWidget teamNameButton = ButtonWidget.builder(
                teamname,
                btn -> {
                    ClientPlayNetworking.send(new AddPlayerToTeamV2(team.teamUUID, MinecraftClient.getInstance().player.getUuidAsString()));
                }
        ).build();
        ButtonWidget bbtn;

        bbtn = ButtonWidget.builder(
                Text.of("X"),
                btn -> {
                    ClientPlayNetworking.send(new RemoveTeamV2(team.teamUUID));
                }
        ).width(20).build();
        try {
            if (!ClientGameStateV2.teamReg.getPlayersDataByTeamUUID(team.teamUUID).isEmpty()) {
                bbtn.active = false;
            }
        }
        catch (Exception exception) {
            System.out.println("dude idk, iets met teambutton gemaakt worden ma kan team ni vinden idk");
        }

        if (team.playerUUIDs.contains(player.puuid)) {
            teamNameButton.active = false;
            bbtn = ColoredButton.builderc(
                    Text.of("Leave"),
                    (ColoredButton.PressAction) btn -> {
                        ClientPlayNetworking.send(new RemovePlayerFromTeamV2( player.puuid));
                    }
            ).color(team.teamColor).build();
        }
        else {
            bbtn = ButtonWidget.builder(
                    Text.of("X"),
                    btn -> {
                        ClientPlayNetworking.send(new RemoveTeamV2(team.teamUUID));
                    }
            ).width(20).build();
            try {
                if (ClientGameStateV2.teamReg.isTeamEmpty(team.teamUUID)) {
                    bbtn.active = false;
                }
            }
            catch (Exception exception) {
                System.out.println("team van teambutton bestaat ni in registry");
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

    public static TeamSettingsWidget.Builder builder(String teamUUID, PlayerData player) throws Exception {
        return new TeamSettingsWidget.Builder(teamUUID, player);
    }


    @Environment(EnvType.CLIENT)
    public static class Builder {
        private TeamData teamD;
        private final PlayerData player;
        private boolean leftbutton = false;

        public Builder(String teamUUID, PlayerData player) throws Exception {
            this.teamD = ClientGameStateV2.teamReg.getTeamDataByUUID(teamUUID);
            this.player = player;
        }

//        public TeamSettingsWidget.Builder teamId(int teamId) {
//            this.teamId = teamId;
//            return this;
//        }


        public TeamSettingsWidget build() {
            return new TeamSettingsWidget(this);
        }



    }
}
