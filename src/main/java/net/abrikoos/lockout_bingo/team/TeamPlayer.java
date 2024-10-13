//package net.abrikoos.lockout_bingo.team;
//
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.DrawContext;
//import net.minecraft.client.network.PlayerListEntry;
//import net.minecraft.util.Identifier;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.UUID;
//
//public class TeamPlayer {
//    public int teamIndex = 0; // zero means no team
//    PlayerListEntry playerListEntry;
//
//    public TeamPlayer(PlayerListEntry playerListEntry, int teamIndex) {
//        this.playerListEntry = playerListEntry;
//        this.teamIndex = teamIndex;
//    }
//
//    public PlayerListEntry getPlayerListEntry() {
//        return playerListEntry;
//    }
//
//    public UUID getPlayerUUID() {
//        return playerListEntry.getProfile().getId();
//    }
//
//    public String getName() {
//        return playerListEntry.getProfile().getName();
//    }
//
//    public void drawHead(@NotNull DrawContext context, int x, int y, int size) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        Identifier skinTexture = client.getSkinProvider().getSkinTextures(playerListEntry.getProfile()).texture();
//        context.drawTexture(skinTexture, x, y, 8, 8, 8, 8, size, size);
//    }
//
//}
