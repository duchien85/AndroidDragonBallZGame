package com.gbc.inderdeep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.gbc.inderdeep.actors.Enemy;
import com.gbc.inderdeep.actors.Player;
import com.gbc.inderdeep.base.ActorBeta;
import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.enumerations.Enumerations;
import com.gbc.inderdeep.ui.HealthBar;
import com.gbc.inderdeep.utils.ImageNames;
import com.gbc.inderdeep.utils.SkinNames;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class GameScreen extends BaseScreen {

    Player player;
    Enemy enemy;

    Touchpad touchpad;

    //HUDs
    Label timerLabel;
    ActorBeta timer;
    HealthBar playerHealthBar;
    HealthBar enemyHealthBar;

    private float playerInitialHealthBarWidth;
    private float enemyInitialHealthBarWidth;
    private float enemyInitialHealthBarX;

    private boolean timesUp;
    private boolean isEnemyDead = false;
    private boolean isPlayerDead = false;

    //Skins
    Skin skin;
    Skin uiSkin;
    Skin hudSkin;

    private int timeLeftMinutes;
    private float timeLeftSeconds;

    private boolean shouldPlayerMove = false;

    private final int hudImageDimensions = 150;

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

    private String getTimeLeft(){
        String format = "%d:%02d";
        String result = String.format(format.toLowerCase(),timeLeftMinutes,(int) timeLeftSeconds);
        return result;
    }

    private void setupTimerLabel(){

        timeLeftMinutes = 3;
        timeLeftSeconds = 1;
        timesUp = false;

        hudSkin = new Skin(Gdx.files.internal(SkinNames.quantumUIComponents));

        timerLabel = new Label(this.getTimeLeft(), hudSkin, "default");
        timerLabel.setWrap(true);
        timerLabel.setAlignment(Align.center);
        timerLabel.setColor(Color.WHITE);
        timerLabel.setFontScale(3.0f);
        timerLabel.setSize(200,60);

        timer = new ActorBeta(0, 0, mainStage);
        timer.setPosition(screenWidth/2 - timerLabel.getWidth()/2, screenHeight - timerLabel.getHeight());
        timer.setSize(timerLabel.getWidth(),timerLabel.getHeight());
        timer.addActor(timerLabel);
    }

    private void setupPlayerHUD(){
        float hudY = screenHeight - hudImageDimensions;
        ActorBeta playerHUD = new ActorBeta(0,hudY,mainStage);
        playerHUD.loadTexture(ImageNames.gokuFightSceneHUD);
        playerHUD.setSize(hudImageDimensions,hudImageDimensions);

        float healthBarX = playerHUD.getX() + playerHUD.getWidth();
        float healthBarWidth = timer.getX() - healthBarX;
        float healthBarHeight = hudImageDimensions / 2;
        float healthBarY = hudY + healthBarHeight;

        ActorBeta playerHealthHUD = new ActorBeta(healthBarX, hudY, mainStage);
        playerHealthHUD.setSize(healthBarWidth, hudImageDimensions / 2);

        playerHealthBar = new HealthBar();
        playerHealthBar.setColor(Color.GREEN);
        playerHealthBar.setPosition(healthBarX,healthBarY);
        playerHealthBar.setSize(healthBarWidth,healthBarHeight);

        playerHealthHUD.addActor(playerHealthBar);

        this.playerInitialHealthBarWidth = healthBarWidth;

    }

    private void setupEnemyHUD(){

        float hudY = screenHeight - hudImageDimensions;
        ActorBeta enemyHUD = new ActorBeta(screenWidth - hudImageDimensions,hudY,mainStage);
        enemyHUD.loadTexture(ImageNames.krillinFightSceneHUD);
        enemyHUD.setSize(hudImageDimensions,hudImageDimensions);

        float healthBarX = timer.getX() + timer.getWidth();
        float healthBarWidth = enemyHUD.getX() - healthBarX;
        float healthBarHeight = hudImageDimensions / 2;
        float healthBarY = hudY + healthBarHeight;

        ActorBeta playerHealthHUD = new ActorBeta(healthBarX, hudY, mainStage);
        playerHealthHUD.setSize(healthBarWidth, hudImageDimensions / 2);

        enemyHealthBar = new HealthBar();
        enemyHealthBar.setColor(Color.GREEN);
        enemyHealthBar.setPosition(healthBarX,healthBarY);
        enemyHealthBar.setSize(healthBarWidth,healthBarHeight);

        playerHealthHUD.addActor(enemyHealthBar);

        this.enemyInitialHealthBarWidth = healthBarWidth;
        this.enemyInitialHealthBarX = healthBarX;
    }

    private void updatePlayerHealthBar(){
        float healthWidthAfterReduction = this.playerInitialHealthBarWidth * (player.getHealth()/player.maxHealth);

        Gdx.app.log("PlayerHealthBar W",Float.toString(healthWidthAfterReduction));
        this.playerHealthBar.setWidth(healthWidthAfterReduction);

        if (player.getHealth() < player.maxHealth * 0.3f ){
            this.playerHealthBar.setColor(Color.RED);
        }
    }

    private void updateEnemyHealthBar(){
        float healthWidthAfterReduction = this.enemyInitialHealthBarWidth * ((float) enemy.getHealth() / enemy.maxHealth);
        float deltaOriginX = this.enemyInitialHealthBarX + this.enemyInitialHealthBarWidth - healthWidthAfterReduction;
        this.enemyHealthBar.setWidth(healthWidthAfterReduction);
        this.enemyHealthBar.setX(deltaOriginX);

        if (enemy.getHealth() < enemy.maxHealth * 0.3f){
            this.enemyHealthBar.setColor(Color.RED);
        }
    }

    private Animation<TextureRegion> getAnimation(Enumerations.AttackType attackType){

        Animation<TextureRegion> animation;
        if (attackType == Enumerations.AttackType.KICK) {
            animation = player.isOnLeft ? player.kick : player.rightKick;
        }
        else {
            animation = player.isOnLeft ? player.punch : player.rightPunch;
        }

        return animation;
    }

    private void performAttack(Enumerations.AttackType attackType) {

        if(!player.isAlreadyAttacking && !isEnemyDead && !isPlayerDead) {
            player.isAlreadyAttacking = true;

            player.setAnimation(this.getAnimation(attackType));

            if (player.overlaps(enemy)) {
                int oldHealth = enemy.getHealth();
                if(oldHealth > 0){
                    int damage = attackType == Enumerations.AttackType.PUNCH ? 5 : 10;
                    int healthAfterDamage = oldHealth - damage;

                    enemy.setHealth(healthAfterDamage);

                    this.updateEnemyHealthBar();
                    Gdx.app.log("Damage", "Damage done on " + attackType.name() + " " + Integer.toString(healthAfterDamage));
                }
            }
        }
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
                performAttack(Enumerations.AttackType.KICK);
            }
        });

        bButton.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                performAttack(Enumerations.AttackType.PUNCH);
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
        enemy = new Enemy(100);
        enemy.setPosition(screenWidth - 600, screenHeight / 4);
        mainStage.addActor(enemy);
        enemy.setAnimation(enemy.idle);
    }

    private void setupPlayer(){
        //CREATE PLAYER : GOKU
        player = new Player(100);
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
        this.setupTimerLabel();

        this.setupPlayerHUD();
        this.setupEnemyHUD();

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

    private void handleTimer(float delta){

        if(timeLeftMinutes == 0 && timeLeftSeconds < 30) {
            timerLabel.setColor(Color.RED);
        }

        if (timeLeftSeconds > 0){
            timeLeftSeconds -= delta;
        }
        else if(timeLeftMinutes == 0 && timeLeftSeconds < 1){
            Gdx.app.log("Timer","GameOver");
        }
        else {
            timeLeftMinutes = timeLeftMinutes != 0 ? --timeLeftMinutes : 0;
            timeLeftSeconds = 60;
        }

        this.timerLabel.setText(this.getTimeLeft());

    }

    private void removeAllLiseners(){
        for(EventListener listener : touchpad.getListeners()){
            touchpad.removeListener(listener);
        }
    }

    private void handleWinLooseConditions(){

        if(!timesUp){
            if(enemy.getHealth() <= 0){
                this.isEnemyDead = true;
                //TODO: Show player win text
                this.removeAllLiseners();
                Gdx.app.log("WinLooseStatus","Player Won!!");
            }
            else if(player.getHealth() <= 0) {
                this.isPlayerDead = true;
                //TODO: Show player loose text
                this.removeAllLiseners();
                Gdx.app.log("WinLooseStatus","Player Lost!!");
            }
        }else {
            //TODO: Show win - loose text
            if (player.getHealth() < enemy.getHealth()){
                Gdx.app.log("WinLooseStatus","Player Lost!!");
            }else {
                Gdx.app.log("WinLooseStatus","Player Won!!");
            }

        }

    }

    private void handleActorUpdates(float delta){

        touchpad.act(delta);

        if(shouldPlayerMove){
            player.act(delta);
        }

        player.boundToWorld();
        enemy.boundToWorld();
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

        this.handleTimer(delta);

        this.handleWinLooseConditions();

        if(!timesUp && !isEnemyDead && !isPlayerDead) {
            this.handleActorUpdates(delta);
        }

    }

}
