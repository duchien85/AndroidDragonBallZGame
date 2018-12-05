package com.gbc.inderdeep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gbc.inderdeep.base.ActorBeta;
import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.utils.ImageNames;
import com.gbc.inderdeep.utils.SceneSegment;
import com.gbc.inderdeep.utils.SkinNames;
import com.gbc.inderdeep.utils.Textures;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class GameScreen extends BaseScreen {

    Touchpad touchpad;

    Skin skin;
    Skin uiSkin;

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
                Gdx.app.log("TapEvent","Button A Tapped");
//                blueRanger.setAnimation(blueRanger.run);
            }
        });

        bButton.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                Gdx.app.log("TapEvent","Button B Tapped");
//                blueRanger.setAnimation(blueRanger.idle);
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

    private void setupListenenrs(){
        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float deltaX = ((Touchpad) actor).getKnobPercentX();
                float deltaY = ((Touchpad) actor).getKnobPercentY();

                Gdx.app.log("Delta X", "" + deltaX);
                Gdx.app.log("Delta Y", "" + deltaY);
            }
        });
    }

    @Override
    protected void setupScene() {

        this.setupBackground();

        ActorBeta.setWorldBounds(screenWidth, screenHeight);

        skin = new Skin(Gdx.files.internal(SkinNames.pixelatedUIComponents));
        uiSkin = new Skin(Gdx.files.internal(SkinNames.arcadeUIComponents));

        this.stage.addActor(this.tableContainer);

        this.setupTouchPad();
        this.setupPlayerActionButtons();
        this.setupListenenrs();

    }

    private void handleTouchPadOnUpdate(float delta){
        touchpad.act(delta);

        if(touchpad.getKnobPercentX() > 0.5 && touchpad.getKnobPercentX() < 0.9) {
            Gdx.app.log("Delta X", "Knob X is " + touchpad.getKnobPercentX());
        }

        if(touchpad.getKnobPercentY() > 0.5 && touchpad.getKnobPercentY() < 0.9) {
            Gdx.app.log("Delta Y", "Knob Y is " + touchpad.getKnobPercentX());
        }
    }

    @Override
    public void update(float delta) {
        this.handleTouchPadOnUpdate(delta);
    }

}
