package com.gbc.inderdeep.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontGenerator {

    private static final FontGenerator ourInstance = new FontGenerator();

    public static FontGenerator getInstance() {
        return ourInstance;
    }

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont anabelleFont;
    private FontGenerator() {

        generator = new FreeTypeFontGenerator(Gdx.files.internal("truetypefont/anabelle_script_light.otf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 240;
        parameter.borderWidth = 1;
        parameter.borderColor = parameter.color;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        anabelleFont = generator.generateFont(parameter);
        generator.dispose();

    }

    public BitmapFont getAnabelleFont(){
        return anabelleFont;
    }

}
