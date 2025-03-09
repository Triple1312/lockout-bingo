package net.abrikoos.lockout_bingo.client.gui.widget;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.networkv2.team.Colors;
import net.abrikoos.lockout_bingo.networkv2.team.PlayerData;
import net.abrikoos.lockout_bingo.networkv2.team.RemovePlayerFromTeamV2;
import net.abrikoos.lockout_bingo.networkv2.team.TeamData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.List;

public class PlayerTeamSelectWidget extends GridWidget {

    public PlayerTeamSelectWidget(PlayerData player) {
        super();
        GridWidget.Adder adder = this.setColumnSpacing(4).setRowSpacing(8).createAdder(ClientGameStateV2.teamReg.teamCount() +2);
        MinecraftClient client = MinecraftClient.getInstance();
        if (!ClientGameStateV2.teamReg.playerInTeam(player.puuid)) {
            adder.add(
                    new TextWidget(Text.of(player.name) , client.textRenderer).setTextColor(0xFFFFFF)
            );
        }
        else {
            try {
                adder.add(
                        new TextWidget(Text.of(player.name) , client.textRenderer).setTextColor(Colors.get(ClientGameStateV2.teamReg.getTeamDataByPlayerUUID(player.puuid).teamColor))
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


        // No team button
        ButtonWidget noteambtn = adder.add(
                ButtonWidget.builder(
                        Text.of(""), btn -> {
                            ClientPlayNetworking.send(new RemovePlayerFromTeamV2(player.puuid));
                        }
                ).width(20).build()
        );
        if (ClientGameStateV2.teamReg.playerInTeam(player.puuid)) {
            noteambtn.active = false;
        }


        List<TeamData> teams = ClientGameStateV2.teamReg.teams;
        // add all team buttons
        for (int i = 0; i < ClientGameStateV2.teamReg.teamCount(); i++) {
            int finalI = i;
            TeamData team = teams.get(i); // todo
//            ColoredButton tsbtn = adder.add(
//                    ColoredButton.builderc(
//                            Text.of(""), btn -> {
//                                ClientPlayNetworking.send(new ChangeTeamIdPacket(player.teamIndex, team.teamId()));
//                            }
//                    ).width(20).color(Colors.get(team.teamColor)).build()
//            );
            try {
//                if(i == ClientGameStateV2.teamReg.getTeamDataByPlayerUUID(player.puuid).teamColor) {
//                    tsbtn.active = false;
//                }
            }
            catch (Exception e) {
                // do nothing
                LockoutLogger.log("team van button bestaat ni");
            }
        }
    }
}
