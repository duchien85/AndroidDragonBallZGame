package com.gbc.inderdeep.screens;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.gbc.inderdeep.base.ActorBeta;
import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.base.Scene;
import com.gbc.inderdeep.utils.ImageNames;
import com.gbc.inderdeep.utils.SceneSegment;

public class ResultScreen extends BaseScreen {

    private final float fadeInTime = 3;

    private Scene scene;
    private ActorBeta logo;

    public static boolean PLAYER_WON = false;
    String winLooseImage;

    @Override
    protected void setupScene(){

        winLooseImage = PLAYER_WON ? ImageNames.winImage : ImageNames.looseImage;

        float logoX = screenWidth / 2;
        float logoY = screenHeight / 2;
        logo = new ActorBeta(logoX, logoY, mainStage);
        logo.loadTexture(winLooseImage);
        logo.setScale(1.0f);
        logo.setOpacity(0);

        logo.setX(logoX - logo.getWidth()/2);
        logo.setY(logoY - logo.getHeight()/2);

        ActorBeta.setWorldBounds(screenWidth, screenHeight);

        this.stage.addActor(this.table);

        scene = new Scene();
        mainStage.addActor(scene);

        scene.addSegment(new SceneSegment(logo, Actions.fadeIn(fadeInTime)));

        scene.start();
    }

    @Override
    public void update(float delta) {

//        if(scene.isSceneFinished())
//        {
//            ScreenManager.getInstance().fadeInToScreen(Enumerations.Screen.STORY_SCREEN,0.5f);
//        }

    }

}
