package com.gbc.inderdeep.enumerations;

import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.screens.GameScreen;
import com.gbc.inderdeep.screens.SplashScreen;
import com.gbc.inderdeep.screens.StoryScreen;

public class Enumerations {

    public enum Screen {

        SPLASH_SCREEN {
            private BaseScreen screen;
            public BaseScreen getScreen(Object... params) {
                if (screen == null){
                    screen = new SplashScreen();
                }
                return screen;
            }
        },

        GAME_SCREEN {
            private BaseScreen screen;
            public BaseScreen getScreen(Object... params) {
                if (screen == null){
                    screen = new GameScreen();
                }
                return screen;
            }
        },

        STORY_SCREEN {
            private BaseScreen screen;
            public BaseScreen getScreen(Object... params) {
                if (screen == null){
                    screen = new StoryScreen();
                }
                return screen;
            }
        };
        public abstract BaseScreen getScreen(Object... params);
    }

    public enum AttackType {
        PUNCH,
        KICK,
        FINISHING_MOVE
    };

}

