package com.gbc.inderdeep.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HealthBar extends Actor {

    ShapeRenderer sr;

    private Color color;

    public HealthBar() {
        super();
        sr = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(this.color);
        sr.rect(getX(), getY(), getWidth(), getHeight());

        sr.end();

        batch.begin();

    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }


}
