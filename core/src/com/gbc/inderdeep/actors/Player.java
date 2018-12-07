package com.gbc.inderdeep.actors;

import com.gbc.inderdeep.utils.ImageNames;

public class Player extends Fighter {

    public Player(int maxHealth) {

        super(maxHealth);

        idle = loadAnimationFromFiles(ImageNames.gokuIdle, 0.2f, true);

        kick = loadAnimationFromFiles(ImageNames.gokuKick,0.1f,false);

        punch = loadAnimationFromFiles(ImageNames.gokuPunch,0.15f,false);

        rightIdle = loadAnimationFromFiles(ImageNames.gokuRightIdle, 0.2f, true);

        rightKick = loadAnimationFromFiles(ImageNames.gokuRightKick,0.05f,false);

        rightPunch = loadAnimationFromFiles(ImageNames.gokuRightPunch,0.05f,false);

        this.setBoundaryRectangle();

        setScale(1.8f);

        setMaxSpeed(900);

    }

}
