//package net.abrikoos.lockout_bingo.team;
//
//import io.netty.buffer.ByteBuf;
//import net.abrikoos.lockout_bingo.util.BlockoutList;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//import net.fabricmc.loader.api.FabricLoader;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.network.codec.PacketCodec;
//import net.minecraft.network.packet.CustomPayload;
//import net.minecraft.server.network.ServerPlayerEntity;
//import net.minecraft.util.Identifier;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Objects;
//import java.util.UUID;
//import java.util.function.Consumer;
//
//public class UnitedTeamRegistry implements CustomPayload {
//
//    public final static Id<UnitedTeamRegistry> ID = new CustomPayload.Id<>(Identifier.of("lockout-bingo", "team_reg"));
//
//    private EnvType env;
//
//    private static UnitedTeamRegistry instance = null;
//
//    public BlockoutList<UTeamPlayer> players = new BlockoutList<>();
//
//    BlockoutList<UnitedTeamRegistry.Team> teams = new BlockoutList<>();
//
//    private static BlockoutList<Consumer<UnitedTeamRegistry>> listeners = new BlockoutList<>();
//
//    private UnitedTeamRegistry() {
//        env = FabricLoader.getInstance().getEnvironmentType();
//    }
//
//    private UnitedTeamRegistry(BlockoutList<UTeamPlayer> players, BlockoutList<Team> teams) {
//        env = FabricLoader.getInstance().getEnvironmentType();
//        this.players = players;
//        this.teams = teams;
//    }
//
//    private static UnitedTeamRegistry getInstance() {
//        if (instance == null) {
//            instance = new UnitedTeamRegistry();
//        }
//        return instance;
//    }
//
//    public static void sendState(ServerPlayerEntity p) {
//        ServerPlayNetworking.send(p, getInstance());
//    }
//
//    @Environment(EnvType.CLIENT)
//    private static void updateRegistry(UnitedTeamRegistry reg) {
//        instance = reg;
//       notifyListeners();
//    }
//
//    public static BlockoutList<UTeamPlayer> getPlayers() {
//        return getInstance().players;
//    }
//
//    // should only be used on server side
//    public static void addPlayer(PlayerEntity player, int teamIndex, boolean connected) {
//        UTeamPlayer p = UnitedTeamRegistry.getTeamPlayerByUUID(player.getUuid());
//        if (p == null) {
//            getInstance().players.add(new UTeamPlayer(player.getUuid(), player.getName().getString(), teamIndex, connected));
//            p = UnitedTeamRegistry.getTeamPlayerByUUID(player.getUuid());
//        }
//        p.connected = true;
//        notifyListeners();
//    }
//
//    // should only be used by server for new players
//    public static void addPlayer(PlayerEntity player) {
//        UTeamPlayer p = UnitedTeamRegistry.getTeamPlayerByUUID(player.getUuid());
//        if (p == null) {
//            getInstance().players.add(new UTeamPlayer(player.getUuid(),player.getName().getString(), 0, true));
//            p = UnitedTeamRegistry.getTeamPlayerByUUID(player.getUuid());
//        }
//        p.connected = true;
//        notifyListeners();
//    }
//
//    public static void disconnectPlayer(UUID puuid) {
//        getInstance().getTeamPlayerByUUID(puuid).connected = false;
//        notifyListeners();
//    }
//
//    public static UTeamPlayer getTeamPlayerByUUID(UUID puuid) {
//        return getInstance().players.firstWhere(p-> p.puuid().equals(puuid));
//    }
//
//    public static void addTeam(String teamName) throws Exception {
//        if (getInstance().teams.size() >=8) throw new Exception("tried to add a team when there are already 8 teams");
//        if (getInstance().teams.any(t-> teamName == t.name)) return;
//        int index = BlockoutList.generate(8, i -> i+1).firstWhere(j-> getInstance().teams.every(t-> t.teamIndex != j));
//        getInstance().teams.add(new Team(index, teamName));
//        notifyListeners();
//    }
//
//    public static void removeTeam(String teamName) {
//        Team team = getTeam(teamName);
//        if (team == null) return;
//        getInstance().players.where(p -> p.teamIndex == team.teamId()).forEach(p -> p.teamIndex = 0);
//        getInstance().teams.remove(team);
//        notifyListeners();
//    }
//
//    public static void subscribe(Consumer<UnitedTeamRegistry> listener) {
//        listeners.add(listener);
//    }
//
//    private static void notifyListeners() {
//        for (Consumer<UnitedTeamRegistry> listener : listeners) {
//            listener.accept(getInstance());
//        }
//    }
//
//    public static BlockoutList<UTeamPlayer> getTeamPlayers(int teamIndex) {
//        return getInstance().players.where(p-> p.teamIndex == teamIndex);
//    }
//
//    public static BlockoutList<UTeamPlayer> getTeamPlayers(String teamName) {
//        Team team = getInstance().getTeam(teamName);
//        if (team == null) return new BlockoutList<>();
//        return getTeamPlayers(team.teamIndex);
//    }
//
//    public static Team getTeam(int index) {
//        return getInstance().teams.firstWhere(t-> t.teamIndex == index);
//    }
//
//    public static Team getTeam(String name) {
//        return getInstance().teams.firstWhere(t-> t.name.equals(name));
//    }
//
//    public static BlockoutList<Team> getTeams() {
//        return getInstance().teams;
//    }
//
//    public static void playerJoinTeam(UUID p, int teamId) {
//        Objects.requireNonNull(getInstance().players.firstWhere(t -> t.puuid().equals(p))).teamIndex = teamId;
//        notifyListeners();
//    }
//
//    public static void changeTeamIndex(int index, int new_index) {
//        if (getInstance().teams.any(t -> t.teamIndex == new_index)) return;
//        Team team = getTeam(index);
//        if (team == null) return;
//        team.changeIndex(index);
//        notifyListeners();
//    }
//
//    public static void changeTeamName(String name, String new_name) {
//        if (getInstance().teams.any(t -> t.name == new_name)) return;
//        Team team = getTeam(name);
//        if (team == null) return;
//        team.changeName(new_name);
//        notifyListeners();
//    }
//
//    public static void changeTeamName(int index, String new_name) {
//        if (getInstance().teams.any(t-> t.name.equals(new_name))) return;
//        Team team = getTeam(index);
//        if (team == null) return;
//        team.changeName(new_name);
//        notifyListeners();
//    }
//
//    @Environment(EnvType.CLIENT)
//    public static void update(UnitedTeamRegistry reg) {
//        instance = reg;
//        notifyListeners();
//    }
//
//    // todo should not be used when envtype == client ????
//    public static final PacketCodec<ByteBuf, UnitedTeamRegistry> PACKET_CODEC = new PacketCodec<ByteBuf, UnitedTeamRegistry>() {
//
//        @Override
//        public UnitedTeamRegistry decode(ByteBuf buf) {
//            int player_count = buf.readByte();
//            BlockoutList<UTeamPlayer> players = new BlockoutList<>();
//            for (int i = 0; i < player_count; i++) {
//                try {
//                    players.add(UTeamPlayer.PACKET_CODEC.decode(buf));
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            BlockoutList<Team> teams = new BlockoutList<>();
//            int team_count = buf.readByte();
//            for (int i = 0; i < team_count; i++) {
//                int teamIndex = buf.readByte();
//                int char_count = buf.readByte();
//                String name = buf.readCharSequence(char_count, StandardCharsets.UTF_8).toString();
//                teams.add(new UnitedTeamRegistry.Team(teamIndex, name));
//            }
//            updateRegistry(new UnitedTeamRegistry(players, teams));
//            return instance;
//        }
//
//        @Override
//        public void encode(ByteBuf buf, UnitedTeamRegistry value) {
//            buf.writeByte(value.players.size());
//            for( UTeamPlayer p : value.players) {
//                UTeamPlayer.PACKET_CODEC.encode(buf, p);
//            }
//            buf.writeByte(value.teams.size());
//            for (Team t : value.teams) {
//                buf.writeByte(t.teamIndex);
//                buf.writeByte(t.name.length());
//                buf.writeCharSequence(t.name, StandardCharsets.UTF_8);
//            }
//        }
//    };
//
//
//    @Override
//    public Id<? extends CustomPayload> getId() {
//        return ID;
//    }
//
//    public static class Team {
//        int teamIndex;
//        String name;
//
//        private Team( int teamIndex, String name) {
//            this.teamIndex = teamIndex;
//            this.name = name;
//        }
//
//        public void changeName(String new_name) {
//            name = new_name;
//        }
//
//        public void changeIndex(int teamIndex) {
//            for (UTeamPlayer p: getInstance().players) {
//                if (p.teamIndex == this.teamIndex) {
//                    p.teamIndex = teamIndex;
//                }
//            }
//            this.teamIndex = teamIndex;
//        }
//
//        public int teamId() {
//            return teamIndex;
//        }
//
//        public String teamName() {
//            return name;
//        }
//    }
//
//}
