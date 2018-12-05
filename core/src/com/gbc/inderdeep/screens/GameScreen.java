package com.gbc.inderdeep.screens;

import com.badlogic.gdx.Gdx;
import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.enumerations.Enumerations;
import com.gbc.inderdeep.utils.Textures;

import javax.xml.soap.Text;

public class GameScreen extends BaseScreen {

    @Override
    public void initializeVariables() {
        super.initializeVariables();
        this.setupScene();
    }

    private void setupScene() {
        this.backgroundImage = Textures.backgroundImageTexture;
    }

    @Override
    public void update(float delta) {

    }

}
