package com.gbc.inderdeep.actors;

import com.gbc.inderdeep.utils.ImageNames;

public class Enemy extends Fighter{

    public Enemy(int maxHealth) {
        super(maxHealth);

        idle = loadAnimationFromFiles(ImageNames.krillinIdle, 0.2f, true);

        punch = loadAnimationFromFiles(ImageNames.krillinPunch,0.15f,false);

        kick = loadAnimationFromFiles(ImageNames.krillinKick,0.1f,false);

        rightIdle = loadAnimationFromFiles(ImageNames.krillinRightIdle, 0.2f, true);

        rightPunch = loadAnimationFromFiles(ImageNames.krillinRightPunch,0.15f,false);

        rightKick = loadAnimationFromFiles(ImageNames.krillinRightKick,0.1f,false);

        this.setBoundaryRectangle();

        setScale(2.5f);

        setMaxSpeed(900);

    }

}
