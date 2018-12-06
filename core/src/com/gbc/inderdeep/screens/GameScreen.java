package com.gbc.inderdeep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gbc.inderdeep.actors.Enemy;
import com.gbc.inderdeep.actors.Player;
import com.gbc.inderdeep.base.ActorBeta;
import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.utils.ImageNames;
import com.gbc.inderdeep.utils.SkinNames;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class GameScreen extends BaseScreen {

    Player player;
    Enemy enemy;

    Touchpad touchpad;

    Skin skin;
    Skin uiSkin;

    boolean shouldPlayerMove = false;

    private void setupBackground(){
        background = new ActorBeta(0, 0, mainStage);
        background.loadTexture(ImageNames.backgroundImage);
        background.setSize(screenWidth, screenHeight);
    }

    private void setupTouchPad(){
        touchpad = new Touchpad(40.0f, skin, "default");
        touchpad.setPosition(screenWidth / 5, screenHeight / 3);
        touchpad.setResetOnTouchUp(true);
        touchpad.getColor().a = 1.0f;

        this.table.add(touchpad).width(touchpad.getWidth() * 1.5f).height(touchpad.getHeight() * 1.5f).padRight(800).padTop(600);
    }

    private void setupPlayerActionButtons(){
        Button aButton = new Button(uiSkin, "red");
        Button bButton = new Button(uiSkin, "blue");
        aButton.getColor().a = 1.0f;
        bButton.getColor().a = 1.0f;

        aButton.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);

                if(!player.isAlreadyAttacking) {
                    player.isAlreadyAttacking = true;

                    if (player.isOnLeft) {
                        player.setAnimation(player.kick);
                    } else {
                        player.setAnimation(player.rightKick);
                    }

                    if (player.overlaps(enemy)) {
                        Gdx.app.log("Damage", "Damage done on kick");
                    }
                }
            }
        });

        bButton.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);

                if(!player.isAlreadyAttacking) {
                    player.isAlreadyAttacking = true;

                    if (player.isOnLeft){
                        player.setAnimation(player.punch);
                    }else{
                        player.setAnimation(player.rightPunch);
                    }

                    if (player.overlaps(enemy)){
                        Gdx.app.log("Damage","Damage done on punch");
                    }
                }

            }
        });

        this.table.padRight(50)
                .add(aButton)
                .width(aButton.getWidth() * 2.0f)
                .height(aButton.getHeight() * 2.0f)
                .bottom()
                .padRight(120);

        this.table.add(bButton)
                .width(bButton.getWidth() * 2.0f)
                .height(bButton.getHeight() * 2.0f)
                .bottom()
                .padBottom(120);
    }

    private void setupListeners(){

        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float deltaX = ((Touchpad) actor).getKnobPercentX();
                float deltaY = ((Touchpad) actor).getKnobPercentY();
                player.accelarateTo(deltaX,deltaY);
            }
        });

        touchpad.addListener(new ActorGestureListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                shouldPlayerMove = false;
                player.stopMovement();
            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                shouldPlayerMove = true;
                player.resumeMovement();
            }
        });
    }

    private void setupEnemy(){
        //CREATE PLAYER : KRILLIN
        enemy = new Enemy();
        enemy.setPosition(screenWidth - 600, screenHeight / 4);
        mainStage.addActor(enemy);
        enemy.setAnimation(enemy.idle);
    }

    private void setupPlayer(){
        //CREATE PLAYER : GOKU
        player = new Player();
        player.setPosition(100, screenHeight / 4);
        mainStage.addActor(player);
        player.setAnimation(player.idle);
    }

    @Override
    protected void setupScene() {

        this.setupBackground();

        ActorBeta.setWorldBounds(screenWidth, screenHeight - 80);

        skin = new Skin(Gdx.files.internal(SkinNames.pixelatedUIComponents));
        uiSkin = new Skin(Gdx.files.internal(SkinNames.arcadeUIComponents));

        this.stage.addActor(this.tableContainer);

        this.setupTouchPad();
        this.setupPlayerActionButtons();
        this.setupListeners();

        this.setupEnemy();
        this.setupPlayer();

    }

    private boolean isPlayerOnLeftOfEnemy(){

        boolean result = false;

        if(player.getX() < enemy.getX())
        {
            result = true;
        }

        return  result;

    }

    private void handlePlayerRotations(){
        player.isOnLeft = !this.isPlayerOnLeftOfEnemy();
        enemy.isOnLeft = this.isPlayerOnLeftOfEnemy();
    }

    @Override
    public void update(float delta) {

        if(enemy.isAnimationFinished()){
            player.isAlreadyAttacking = false;
            Animation animation = enemy.isOnLeft ? enemy.idle : enemy.rightIdle;
            enemy.setAnimation(animation);
        }

        if(player.isAnimationFinished()){
            player.isAlreadyAttacking = false;
            Animation animation = player.isOnLeft ? player.idle : player.rightIdle;
            player.setAnimation(animation);
        }

        this.handlePlayerRotations();

        touchpad.act(delta);

        if(shouldPlayerMove){
            player.act(delta);
        }

        player.boundToWorld();
        enemy.boundToWorld();
    }

}
