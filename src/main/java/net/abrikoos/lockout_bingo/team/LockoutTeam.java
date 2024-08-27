package net.abrikoos.lockout_bingo.team;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LockoutTeam {
    public String name;
    public List<String> playeruuids = new ArrayList<>();
    public int teamId;

    public int packetSize() {
        return 2 + name.length() + 1 + 36 * playeruuids.size();
    }

    public LockoutTeam(String name, int teamId) {
        this.name = name;
        this.teamId = teamId;
    }

    public void addPlayerEntity(@NotNull ServerPlayerEntity player) {
        if (this.playeruuids.contains(player.getUuidAsString())) {
            return;
        }
        this.playeruuids.add(player.getUuidAsString());
    }

    public void addPlayer(String puuid) {
        if (this.playeruuids.contains(puuid)) {
            return;
        }
        this.playeruuids.add(puuid);
    }

    public void removePlayer(String puuid) {
        this.playeruuids.remove(puuid);
    }


    public static final PacketCodec<ByteBuf, LockoutTeam> PACKET_CODEC = new PacketCodec<ByteBuf, LockoutTeam>() {
        @Override
        public @NotNull LockoutTeam decode(@NotNull ByteBuf buf) {
            MinecraftClient client = MinecraftClient.getInstance();
            while (true) {
                if (client.getNetworkHandler() == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    break;
                }
            }
            ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
            assert networkHandler != null;
            Collection<PlayerListEntry> entries = networkHandler.getPlayerList();


            int teamId = buf.readByte();
            int nameLength = buf.readByte();
            String name = buf.readCharSequence(nameLength, Charset.defaultCharset()).toString();
            LockoutTeam ret = new LockoutTeam(name, teamId);
            int playerCount = buf.readByte();
            for (int i =0; i < playerCount; i++) {
                ret.playeruuids.add(buf.readCharSequence(36, Charset.defaultCharset()).toString());
            }
            return ret;
        };

        @Override
        public void encode(@NotNull ByteBuf buf, @NotNull LockoutTeam value) {
            buf.writeByte(value.teamId);
            buf.writeByte(value.name.length());
            buf.writeCharSequence(value.name, Charset.defaultCharset());
            buf.writeByte(value.playeruuids.size());
            for (String player : value.playeruuids) {
                buf.writeCharSequence(player, Charset.defaultCharset()); // todo technically I could do this better
            }
        }
    };
}
