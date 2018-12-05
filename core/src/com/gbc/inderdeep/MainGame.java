package com.gbc.inderdeep;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gbc.inderdeep.enumerations.Enumerations;
import com.gbc.inderdeep.managers.ScreenManager;

public class MainGame extends Game {

	private static final MainGame ourInstance = new MainGame();

	public static MainGame getInstance() {
		return ourInstance;
	}
	
	@Override
	public void create () {
		InputMultiplexer im = new InputMultiplexer();
		Gdx.input.setInputProcessor( im );
		ScreenManager.getInstance().fadeInToScreen(Enumerations.Screen.SPLASH_SCREEN,0.5f);
	}

}
