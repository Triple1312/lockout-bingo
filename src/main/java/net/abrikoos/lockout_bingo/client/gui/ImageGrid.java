package net.abrikoos.lockout_bingo.client.gui;

import net.abrikoos.lockout_bingo.server.goals.LockoutGoal;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ImageGrid {
    public static final int GRID_SIZE_X = 5;
    public static final int GRID_SIZE_Y = 5;
    public static final int PADDING = 4;
    private static final Identifier[] images = new Identifier[GRID_SIZE_X * GRID_SIZE_Y];

    void init(List<LockoutGoal> goals) {

    }



}
