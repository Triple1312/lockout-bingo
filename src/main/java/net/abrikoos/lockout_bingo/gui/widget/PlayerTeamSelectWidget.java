package net.abrikoos.lockout_bingo.gui.widget;

import net.abrikoos.lockout_bingo.network.team.ChangeTeamIdPacket;
import net.abrikoos.lockout_bingo.team.Colors;
import net.abrikoos.lockout_bingo.team.LockoutTeam;
import net.abrikoos.lockout_bingo.team.TeamPlayer;
import net.abrikoos.lockout_bingo.team.TeamRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class PlayerTeamSelectWidget extends GridWidget {

    public PlayerTeamSelectWidget(TeamPlayer player) {
        super();
        GridWidget.Adder adder = this.setColumnSpacing(4).setRowSpacing(8).createAdder(TeamRegistry.getTeams().size() +2);
        MinecraftClient client = MinecraftClient.getInstance();
        if (player.teamIndex == 0) {
            adder.add(
                    new TextWidget(Text.of(player.getName()) , client.textRenderer).setTextColor(0xFFFFFF)
            );
        }
        else {
            adder.add(
                    new TextWidget(Text.of(player.getName()) , client.textRenderer).setTextColor(Colors.get(player.teamIndex))
            );
        }


        // No team button
        ButtonWidget noteambtn = adder.add(
                ButtonWidget.builder(
                        Text.of(""), btn -> {
                            ClientPlayNetworking.send(new ChangeTeamIdPacket(player.teamIndex, 0));
                        }
                ).width(20).build()
        );
        if (player.teamIndex == 0) {
            noteambtn.active = false;
        }



        // add all team buttons
        for (int i = 0; i < TeamRegistry.getTeams().size(); i++) {
            int finalI = i;
            LockoutTeam team = TeamRegistry.getTeams().get(i);
            ColoredButton tsbtn = adder.add(
                    ColoredButton.builderc(
                            Text.of(""), btn -> {
                                ClientPlayNetworking.send(new ChangeTeamIdPacket(player.teamIndex, team.teamId));
                            }
                    ).width(20).color(Colors.get(team.teamId)).build()
            );

            if(i == player.teamIndex) {
                tsbtn.active = false;
            }
        }
    }
}
