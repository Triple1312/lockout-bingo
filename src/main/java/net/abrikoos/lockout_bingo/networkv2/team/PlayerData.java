package net.abrikoos.lockout_bingo.networkv2.team;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class PlayerData implements CustomPayload {

    public static final Id<PlayerData> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "player_data_v2"));

    public String puuid;

    public String name;

    public boolean connected;

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public PlayerData(String puuid, String name, boolean connected) {
        this.puuid = puuid;
        this.name = name;
        this.connected = connected;
    }


    public final static PacketCodec<RegistryByteBuf, PlayerData> CODEC = new PacketCodec<RegistryByteBuf, PlayerData>() {
        @Override
        public PlayerData decode(RegistryByteBuf buf) {
            String puuid = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            int nameCharacterCount = buf.readByte();
            String name = buf.readCharSequence(nameCharacterCount, java.nio.charset.StandardCharsets.UTF_8).toString();
            boolean connected = buf.readBoolean();
            return new PlayerData(puuid, name, connected);
        }

        @Override
        public void encode(RegistryByteBuf buf, PlayerData value) {
            buf.writeCharSequence(value.puuid, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeByte(value.name.length());
            buf.writeCharSequence(value.name, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeBoolean(value.connected);
        }
    };




    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
