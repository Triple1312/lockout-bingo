package net.abrikoos.lockout_bingo.networkv2.team;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamData implements CustomPayload {

    public static final Id<TeamData> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "team_data_v2"));

    public String teamName;

    public String teamUUID;

    public int teamColor;

    public List<String> playerUUIDs;

    TeamData(String teamName, int teamColor, String teamUUID) {
        this.teamName = teamName;
        this.teamUUID = teamUUID;
        this.teamColor = teamColor;
        this.playerUUIDs = new ArrayList<>();
    }

    public void addPlayer(String puuid) {
        if (playerUUIDs.contains(puuid)) {
            return;
        }
        else {
            playerUUIDs.add(puuid);
        }
    }

    public void removePlayer(String puuid) {
        playerUUIDs.remove(puuid);
    }



    public static final PacketCodec<RegistryByteBuf, TeamData> CODEC = new PacketCodec<RegistryByteBuf, TeamData>() {
        @Override
        public TeamData decode(RegistryByteBuf buf) {
            int teamNameCharacterCount = buf.readByte();
            String teamName = buf.readCharSequence(teamNameCharacterCount, java.nio.charset.StandardCharsets.UTF_8).toString();
            String teamUUID = buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString();
            int teamColor = buf.readByte();
            int playerCount = buf.readByte();
            TeamData t =  new TeamData(teamName, teamColor, teamUUID);
            for (int i = 0; i < playerCount; i++) {
                t.playerUUIDs.add(buf.readCharSequence(36, java.nio.charset.StandardCharsets.UTF_8).toString());
            }
            return t;
        }

        @Override
        public void encode(RegistryByteBuf buf, TeamData value) {
            buf.writeByte(value.teamName.length());
            buf.writeCharSequence(value.teamName, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeCharSequence(value.teamUUID, java.nio.charset.StandardCharsets.UTF_8);
            buf.writeByte(value.teamColor);
            buf.writeByte(value.playerUUIDs.size());
            for (String puuid : value.playerUUIDs) {
                buf.writeCharSequence(puuid, java.nio.charset.StandardCharsets.UTF_8);
            }
        }
    };



    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }



}
