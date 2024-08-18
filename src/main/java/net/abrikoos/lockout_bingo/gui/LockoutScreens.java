package net.abrikoos.lockout_bingo.gui;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.gui.screens.LockoutBoardScreen;
import net.abrikoos.lockout_bingo.gui.screens.LockoutCreateScreen;
import net.abrikoos.lockout_bingo.gui.screens.MainScreen;
import net.abrikoos.lockout_bingo.gui.widget.BoardWidget;
import net.abrikoos.lockout_bingo.modes.team.LockoutTeamDataClass;
import net.abrikoos.lockout_bingo.network.game.BlackoutStartGameInfo;
import net.abrikoos.lockout_bingo.network.game.LockoutUpdateBoardInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class LockoutScreens {

    private static LockoutCreateScreen createScreen;

    private static LockoutBoardScreen boardScreen = new LockoutBoardScreen(LockoutScreens::leaveScreen);

    public static MainScreen mainScreen = new MainScreen(LockoutScreens::leaveScreen);

    private static String currentScreen = "main";


//    private static


    private static LockoutScreens instance;

    private  LockoutScreens() {
        createScreen = new LockoutCreateScreen(new ArrayList<>());
    }


    public static LockoutScreens getInstance() {
        if (instance == null) {
            instance = new LockoutScreens();
        }
        return instance;
    }


//    public static LockoutCreateScreen createScreen() {
//
//    }

    public static void teamsupdate(List<LockoutTeamDataClass> teams) {

    }


    // returns if successfully opened
    public static boolean open() {
        LockoutLogger.log("Opening screen: " + currentScreen);
        switch (currentScreen) {
            case "main" -> {
                MinecraftClient.getInstance().setScreen(mainScreen);
                return true;
            }
            case "create" -> {
                MinecraftClient.getInstance().setScreen(createScreen);
                return true;
            }
            case "board" -> {
                MinecraftClient.getInstance().setScreen(boardScreen);
                return true;
            }
        }
//        if (!boardScreen.initialized()){return false;}
//        MinecraftClient.getInstance().setScreen(boardScreen);
//        return true;
        return false;
    }

    // returns if successfully closed
    public static boolean close() {
        MinecraftClient.getInstance().setScreen(null);
        return true;
    }

    public static void toggle() {

    }

    public static void setBoard(BlackoutStartGameInfo info) {
        boardScreen.setBoardwidget(new BoardWidget(info));
        currentScreen = "board";
        open();
    }

    public static void updateBoard(LockoutUpdateBoardInfo info) {
        boardScreen.boardwidget.setLubi(info);
    }

    public static Void leaveScreen(Void unused) {
        MinecraftClient.getInstance().setScreen(null);
        return null;
    }

    public static void drawHud(DrawContext context) {
        if (boardScreen.boardwidget == null) {
            return;
        }
        boardScreen.boardwidget.drawHud(context);
    }

    public static void setState(String state) {
        currentScreen = state;
        open();
    }







}
