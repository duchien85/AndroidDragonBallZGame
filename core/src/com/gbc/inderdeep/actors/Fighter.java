package com.gbc.inderdeep.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gbc.inderdeep.base.ActorBeta;

public class Fighter extends ActorBeta {

    public Animation<TextureRegion> idle;
    public Animation<TextureRegion> run;
    public Animation<TextureRegion> punch;
    public Animation<TextureRegion> kick;

    public Animation<TextureRegion> rightIdle;
    public Animation<TextureRegion> rightRun;
    public Animation<TextureRegion> rightPunch;
    public Animation<TextureRegion> rightKick;

    public boolean isAlreadyAttacking = false;

    public int acceleration = 900;

    private int health = 100;

    Fighter() {
        super();
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
    }

    public void stopMovement(){
        this.setSpeed(0);
        this.applyPhysics(0);
    }

    public void resumeMovement(){
        this.setAcceleration(this.acceleration);
    }

    public void accelarateTo(float deltaX, float deltaY){
        Vector2 accVector = new Vector2(deltaX,deltaY);
        float angle = accVector.angle();
        this.accelerateAtAngle(angle);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}