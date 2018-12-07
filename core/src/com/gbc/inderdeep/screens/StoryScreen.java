package com.gbc.inderdeep.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.gbc.inderdeep.actions.SceneActions;
import com.gbc.inderdeep.managers.SoundManager;
import com.gbc.inderdeep.ui.DialogBox;
import com.gbc.inderdeep.base.ActorBeta;
import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.base.Scene;
import com.gbc.inderdeep.enumerations.Enumerations;
import com.gbc.inderdeep.managers.ScreenManager;
import com.gbc.inderdeep.utils.ImageNames;
import com.gbc.inderdeep.utils.SceneSegment;

public class StoryScreen extends BaseScreen {

    Scene storyScene;

    ActorBeta continueKey;

    //Actors
    ActorBeta opponent;
    ActorBeta player;

    //Dialog Boxes
    DialogBox playerDialogBox;
    DialogBox opponentDialogBox;

    private void addBackgroundImage(){
        background = new ActorBeta(0, 0, mainStage);
        background.loadTexture(ImageNames.backgroundImage);
        background.setSize(screenWidth, screenHeight);
    }

    private void setupOpponent(){
        opponent = new ActorBeta(0, 0, mainStage);
        opponent.loadTexture(ImageNames.krillinStoryImage);
        opponent.setX(screenWidth - opponent.getWidth() - 100);
        opponent.setY(opponent.getHeight()/2);
        opponent.setScale(2.0f);
        opponent.setVisible(true);
    }

    private void setupPlayer() {
        player = new ActorBeta(100, 0, mainStage);
        player.loadTexture(ImageNames.gokuStoryImage);
        player.setY(player.getHeight()/2);
        player.setScale(2.0f);
        player.setVisible(true);
    }

    private void setupPlayerDialogBox(){
        playerDialogBox = new DialogBox(0,0, this.stage);
        playerDialogBox.setDialogSize(600, 300);
        playerDialogBox.setBackgroundColor( new Color(0.6f, 0.6f, 0.8f, 1) );
        playerDialogBox.setFontScale(2.75f);
        playerDialogBox.setVisible(false);
        playerDialogBox.setFontColor(Color.DARK_GRAY);
        this.table.add(playerDialogBox).expandX().expandY().bottom().padBottom(player.getHeight() + 200).padLeft(50);
    }

    private void setupOpponentDialogBox(){
        opponentDialogBox = new DialogBox(0, 0, this.stage);
        opponentDialogBox.setDialogSize(600, 300);
        opponentDialogBox.setBackgroundColor( new Color(0.6f, 0.6f, 0.8f, 1) );
        opponentDialogBox.setFontScale(2.75f);
        opponentDialogBox.setVisible(false);
        opponentDialogBox.setFontColor(Color.DARK_GRAY);
        this.table.add(opponentDialogBox).expandX().expandY().bottom().padBottom(opponent.getHeight() + 200).padRight(50);
    }

    private void setupContinueKey(){
        continueKey = new ActorBeta(0,0,this.stage);
        continueKey.loadTexture("badlogic.jpg");
        continueKey.setSize(0,0);
        continueKey.setVisible(false);

        mainStage.addActor(continueKey);
        continueKey.setPosition( playerDialogBox.getWidth() - continueKey.getWidth(), 0 );
    }

    private void setupSceneWithTextForDialog(DialogBox dialog, String text){
        storyScene.addSegment( new SceneSegment( dialog, Actions.show() ));

        storyScene.addSegment( new SceneSegment( dialog,
                SceneActions.setText(text) ));

        storyScene.addSegment( new SceneSegment( continueKey, Actions.show() ));
        storyScene.addSegment( new SceneSegment( continueKey, SceneActions.pause() ));
        storyScene.addSegment( new SceneSegment( dialog, Actions.hide() ));
        storyScene.addSegment( new SceneSegment( continueKey, Actions.hide() ));
    }

    @Override
    protected void setupScene() {

        SoundManager.getInstance().startBackGroundMusic();

        this.addBackgroundImage();

        this.setupOpponent();

        this.setupPlayer();

        this.setupPlayerDialogBox();
        this.setupOpponentDialogBox();

        this.stage.addActor(this.table);

        this.setupContinueKey();

        storyScene = new Scene();
        mainStage.addActor(storyScene);

        storyScene.addSegment(new SceneSegment(background,Actions.show()));

        this.setupSceneWithTextForDialog(playerDialogBox, "Are you ready for our dual, KRILLIN!!");
        this.setupSceneWithTextForDialog(opponentDialogBox, "I certainly am.. Hope you are ready for your defeat!");

        this.setupSceneWithTextForDialog(playerDialogBox, "Ha Ha!! \nDon't forget what happened in last tournament!");
        this.setupSceneWithTextForDialog(opponentDialogBox, "Oh I never forget.. I have been training entire year for this.");

        this.setupSceneWithTextForDialog(playerDialogBox, "I do hope you have some surprises this year!");
        this.setupSceneWithTextForDialog(opponentDialogBox, "Enough talking!! Lets see what you've got!!");

        storyScene.start();

    }

    @Override
    public void update(float delta) {
        if ( storyScene.isSceneFinished() ){
            ScreenManager.getInstance().fadeInToScreen(Enumerations.Screen.GAME_SCREEN,0.5f);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        storyScene.loadNextSegment();

        return true;
    }

}
