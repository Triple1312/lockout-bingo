package net.abrikoos.lockout_bingo.networkv2.team;


import java.util.Objects;
import java.util.UUID;


public class Colors {
    final static int[] colors = new int[] {
        0xFF4169E1, // royalblue
        0xFFDC143C, // crimson
        0xFF3CB371, // mediumseagreen
        0xFFF4A460, // sandybrown
        0xFFBA55D3, // mediumorchid
        0xFFDAA520, // goldenrod
        0xFF008080, // teal
        0xFFFA8072, // salmon
        0xFF708090, // slategray
        0xFF808000, // olive
    };

    public static int get(int index) {
        if (index == 0) {
            return 0xFF000000;
        }
        return colors[index -1];
    }

//    public static int getPlayerColor(String puuid) {
//        if (Objects.equals(puuid, "00000000-0000-0000-0000-000000000000") || puuid == null) {
//            return get(0);
//        }
//        if (puuid.equals("11111111-1111-1111-1111-111111111111")) {
//            return get(1);
//        }
//        if (puuid.equals("22222222-2222-2222-2222-222222222222")) {
//            return get(2);
//        }
//        UTeamPlayer player = UnitedTeamRegistry.getTeamPlayerByUUID(UUID.fromString(puuid));
//        if (player == null) {
//            return get(1);
//        }
//        return get(player.teamIndex);
//    }

    public static int getTeamIndex(int color) {
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == color) {
                return i + 1;
            }
        }
        return 0;
    }

}
