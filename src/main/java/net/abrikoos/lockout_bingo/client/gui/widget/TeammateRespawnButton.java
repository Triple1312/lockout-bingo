package net.abrikoos.lockout_bingo.client.gui.widget;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.networkv2.team.TeamData;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;


public class TeammateRespawnButton extends ButtonWidget {

    private static Text titleScreen = Text.of("Title Screen");
    private static Text respawn = Text.of("Respawn at Teammate");
    private static PressAction defaultOnPress;

    public TeammateRespawnButton(int x, int y, int width, int height, PressAction onPress) {
        super(x, y, width, height, titleScreen, onPresss, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        defaultOnPress = onPress;
    }

    private static final PressAction onPresss = button -> {
        try {
            String playerUUID = ClientGameStateV2.client.player.getUuidAsString();
            TeamData teamData = ClientGameStateV2.teamReg.getTeamDataByPlayerUUID(playerUUID);
            int c_connected = teamData.playerUUIDs.stream().map(puuid -> {
                try {
                    return ClientGameStateV2.teamReg.getPlayerDataByUUID(puuid);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).mapToInt(pdata -> pdata.connected ? 1 : 0).sum();

            if (ClientGameStateV2.teammateRespawnEnabled() && c_connected > 0) {
                ClientGameStateV2.client.setScreen(new net.abrikoos.lockout_bingo.client.gui.screens.TeamrespawnScreen());
            } else {
                defaultOnPress.onPress(button);
            }
        }
        catch (Exception ignored) {
            LockoutLogger.log("pressaction teammate respawn not working idk");
            defaultOnPress.onPress(button);
        }

    };

    @Override
    public Text getMessage() {
        if (ClientGameStateV2.teammateRespawnEnabled()) {
            return respawn;
        } else {
            return titleScreen;
        }
    }






}
