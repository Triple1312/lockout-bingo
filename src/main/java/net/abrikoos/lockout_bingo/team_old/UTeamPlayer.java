package net.abrikoos.lockout_bingo.team;

import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.codec.PacketCodec;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UTeamPlayer {
    public int teamIndex = 0;
    UUID puuid;
    public String name;
    boolean connected;

    public UTeamPlayer( UUID puuid, String name, int teamIndex, boolean connected) {
        this.puuid = puuid;
        this.teamIndex = teamIndex;
        this.connected = connected;
        this.name = name;
    }

    @Environment(EnvType.CLIENT)
    public static UTeamPlayer initClient(UUID puuid, String name, int teamIndex, boolean connected) {
        return new UTeamPlayer(
                puuid,
                name,
                teamIndex,
                connected
        );

    }

    public static UTeamPlayer initServer(UUID puuid, String name, int teamIndex, boolean connected) {
        return new UTeamPlayer(
                puuid,
                name,
                teamIndex,
                connected
        );
    }

    public String getName() {
        return name;
    }

    public boolean isConnected() {
        return connected;
    }

    public String puuidstr(){return puuid.toString();}

    public UUID puuid(){return puuid;}


    public static final PacketCodec<ByteBuf, UTeamPlayer> PACKET_CODEC = new PacketCodec<ByteBuf, UTeamPlayer>() {

        @Environment(EnvType.CLIENT) // only client should ever receive this packet
        @Override
        public UTeamPlayer decode(ByteBuf buf) {
            long mostsig = buf.readLong();
            long leastsig = buf.readLong();
            int namelength = buf.readByte();
            String name = buf.readCharSequence(namelength, StandardCharsets.UTF_8).toString();
            int teamindex = buf.readByte();
            boolean connected = buf.readBoolean();
            return initClient(new UUID(mostsig, leastsig), name, teamindex, connected);
        }

        @Override
        public void encode(ByteBuf buf, UTeamPlayer value) {
            buf.writeLong(value.puuid.getMostSignificantBits());
            buf.writeLong(value.puuid.getLeastSignificantBits());
            buf.writeByte(value.name.length());
            buf.writeCharSequence(value.name, StandardCharsets.UTF_8);
            buf.writeByte(value.teamIndex); // will always be smaller than 256
            buf.writeBoolean(value.connected);
        }
    };
}
