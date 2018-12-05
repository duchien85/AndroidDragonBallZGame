package com.gbc.inderdeep.utils;

import com.badlogic.gdx.Gdx;

public class ScreenSettings {
    private static final ScreenSettings ourInstance = new ScreenSettings();

    public static ScreenSettings getInstance() {
        return ourInstance;
    }

    public int height;
    public int width;
    public float heightToWidthRatio;

    private ScreenSettings() {
        this.height = Gdx.graphics.getHeight();
        this.width = Gdx.graphics.getWidth();
        this.heightToWidthRatio = this.height/this.width;
    }

}

