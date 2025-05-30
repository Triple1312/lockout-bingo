package net.abrikoos.lockout_bingo.client.gui;

import net.abrikoos.lockout_bingo.LockoutLogger;
import net.abrikoos.lockout_bingo.client.gui.screens.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class LockoutScreens {

    public static ScreenScreen completeFullScreen = new ScreenScreen();

    private static String currentScreen = "main";

    private static boolean _open = false;


//    private static


    private static LockoutScreens instance;

    private  LockoutScreens() {
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


    // returns if successfully opened
    public static boolean open() {
        LockoutLogger.log("Opening screen: " + currentScreen);
        MinecraftClient.getInstance().setScreen(completeFullScreen);
//        switch (currentScreen) {
//            case "main" -> {
//                MinecraftClient.getInstance().setScreen(completeFullScreen);
//                _open = true;
//                return true;
//            }
//            case "create" -> {
//                MinecraftClient.getInstance().setScreen(createScreen);
//                _open = true;
//                return true;
//            }
//            case "board" -> {
//                MinecraftClient.getInstance().setScreen(boardScreen);
//                _open = true;
//                return true;
//            }
//        }
//        if (!boardScreen.initialized()){return false;}
//        MinecraftClient.getInstance().setScreen(boardScreen);
//        return true;
        return false;
    }

    public static String getCurrentScreen() {
        return currentScreen;
    }

    // returns if successfully closed
    public static boolean close() {
        MinecraftClient.getInstance().setScreen(null);
        _open = false;
        return true;
    }

    public static void toggle() {

    }

    public static Void leaveScreen(Void unused) {
        MinecraftClient.getInstance().setScreen(null);
        return null;
    }

    public static void drawHud(DrawContext context) {
//        if (boardScreen.boardwidget == null) {
//            return;
//        }
//        boardScreen.boardwidget.drawHud(context);
    }

    public static void setState(String state) {
        currentScreen = state;
        open();
    }

    public static boolean isOpen() {
        return _open;
    }







}
