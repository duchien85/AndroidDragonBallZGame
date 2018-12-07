package com.gbc.inderdeep.screens;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.gbc.inderdeep.base.ActorBeta;
import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.base.Scene;
import com.gbc.inderdeep.enumerations.Enumerations;
import com.gbc.inderdeep.managers.ScreenManager;
import com.gbc.inderdeep.utils.ImageNames;
import com.gbc.inderdeep.utils.SceneSegment;

public class SplashScreen extends BaseScreen {

    private final float fadeInTime = 3;

    private Scene splashScene;
    private ActorBeta logo;

    @Override
    protected void setupScene(){

        float logoX = screenWidth / 2;
        float logoY = screenHeight / 2;
        logo = new ActorBeta(logoX, logoY, mainStage);
        logo.loadTexture(ImageNames.splashScreenLogo);
        logo.setScale(1.0f);
        logo.setOpacity(0);

        logo.setX(logoX - logo.getWidth()/2);
        logo.setY(logoY - logo.getHeight()/2);

        ActorBeta.setWorldBounds(screenWidth, screenHeight);

        this.stage.addActor(this.table);

        splashScene = new Scene();
        mainStage.addActor(splashScene);

        splashScene.addSegment(new SceneSegment(logo, Actions.fadeIn(fadeInTime)));

        splashScene.start();
    }

    @Override
    public void update(float delta) {

        if(splashScene.isSceneFinished())
        {
            ScreenManager.getInstance().fadeInToScreen(Enumerations.Screen.STORY_SCREEN,0.5f);
        }

    }

}
