package net.abrikoos.lockout_bingo.client.gui.screens;

import net.abrikoos.lockout_bingo.team.UTeamPlayer;
import net.abrikoos.lockout_bingo.team.UnitedTeamRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tab.Tab;

@Environment(EnvType.CLIENT)
public class CompleteFullScreenState {
    public static int selectedTab = 0;
    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static final UTeamPlayer teamPlayer = UnitedTeamRegistry.getTeamPlayerByUUID(client.player.getUuid());
}
