package net.abrikoos.lockout_bingo.client.gui.screens;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.networkv2.team.PlayerData;
import net.abrikoos.lockout_bingo.networkv2.team.TeammateRespawnRequestPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeamrespawnScreen extends Screen {

//    GridWidget grid = new GridWidget();


    public TeamrespawnScreen() {
        super(Text.of("Team Respawn"));
//        GridWidget.Adder adder = this.grid.setRowSpacing(8).setColumnSpacing(10).createAdder(2);
//        SimplePositioningWidget.setPos(this.grid, this.getNavigationFocus(), 0.5f, 0.1666666F);
        try {
            List<String> puuids = ClientGameStateV2.teamReg.getTeamDataByPlayerUUID(ClientGameStateV2.client.player.getUuidAsString()).playerUUIDs;
            List<PlayerData> players = new ArrayList<>(puuids.stream().map(puuid -> {
                try {
                    return ClientGameStateV2.teamReg.getPlayerDataByUUID(puuid);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).toList());
            players.removeIf(p -> !p.connected && !Objects.equals(p.puuid, ClientGameStateV2.client.player.getUuidAsString()));
            for (int i = 0; i < players.size(); i++) {
                var p = players.get(i);
                ButtonWidget p_w = ButtonWidget.builder(Text.of(p.name), button -> {
                    ClientPlayNetworking.send(new TeammateRespawnRequestPacket(p.puuid));
                    this.client.player.requestRespawn();
                }).width(150).build();
                p_w.setX(100);
                p_w.setY(40 + i * 30);
                this.addDrawableChild(p_w);
//                this.addDrawableChild(p_w);
            }

        } catch (Exception e) {
            LockoutLogger.log("Error building team respawn screen: " + e.getMessage());
        }
    }
}
