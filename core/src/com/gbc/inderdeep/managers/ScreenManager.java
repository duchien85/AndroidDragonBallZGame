package com.gbc.inderdeep.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.gbc.inderdeep.MainGame;
import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.enumerations.Enumerations;

public class ScreenManager {

    // Singleton: unique instance
    private static ScreenManager instance;

    // Reference to game
    private MainGame game;

    public Enumerations.Screen currentScreen;
    public Enumerations.Screen previousScreen;

    // Singleton: private constructor
    private ScreenManager() {
        super();
        game = MainGame.getInstance();
    }

    // Singleton: retrieve instance
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void showCurrentScreen(){
        // Get current screen to dispose it
//        BaseScreen currentScreen = (BaseScreen) game.getScreen();
        if(currentScreen != null){
            this.fadeInToScreen(currentScreen,1.0f);
        }
        else{
//            this.fadeInToScreen(Enumerations.Screen.MENU_SCREEN,1.0f);
        }

    }

    public void fadeInToScreen(Enumerations.Screen screenEnum, final float duration, Object... params) {

        if (this.currentScreen != screenEnum){
            this.previousScreen = this.currentScreen == null ? screenEnum : this.currentScreen;
            this.currentScreen = screenEnum;

            // Show new screen
            BaseScreen nextScreen = screenEnum.getScreen(params);
            nextScreen.stage.getRoot().getColor().a = 0;
            nextScreen.stage.getRoot().addAction(Actions.fadeIn(duration));
            Gdx.app.log("INFO","Moving to next screen " + screenEnum.name());

            this.game.setScreen(nextScreen);
        }

    }
}
