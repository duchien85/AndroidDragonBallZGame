package com.gbc.inderdeep.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.gbc.inderdeep.actors.Enemy;
import com.gbc.inderdeep.actors.Fighter;
import com.gbc.inderdeep.actors.Player;
import com.gbc.inderdeep.base.ActorBeta;
import com.gbc.inderdeep.base.BaseScreen;
import com.gbc.inderdeep.enumerations.Enumerations;
import com.gbc.inderdeep.managers.SoundManager;
import com.gbc.inderdeep.ui.HealthBar;
import com.gbc.inderdeep.utils.ImageNames;
import com.gbc.inderdeep.utils.SkinNames;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.Random;

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
    private boolean shouldEnemyMove = false;

    private final int hudImageDimensions = 150;

    Table overlayTable;
    Label label;
    TextButton restartButton;

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
        float healthWidthAfterReduction = this.playerInitialHealthBarWidth * ((float) player.getHealth()/player.maxHealth);

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

    private Animation<TextureRegion> getAnimationOnFighter(Fighter fighter, Enumerations.AttackType attackType){

        Animation<TextureRegion> animation;
        if (attackType == Enumerations.AttackType.KICK) {
            animation = fighter.isOnLeft ? fighter.kick : fighter.rightKick;
        }
        else if (attackType == Enumerations.AttackType.PUNCH){
            animation = fighter.isOnLeft ? fighter.punch : fighter.rightPunch;
        }else {
            animation = fighter.isOnLeft ? fighter.death : fighter.rightDeath;
        }

        return animation;
    }

    private void performAttack(Fighter fromFighter, Fighter onFighter, Enumerations.AttackType attackType) {

        if(!fromFighter.isAlreadyAttacking && !isEnemyDead && !isPlayerDead) {
            fromFighter.isAlreadyAttacking = true;

            fromFighter.setAnimation(this.getAnimationOnFighter(fromFighter,attackType));
            int oldHealth = onFighter.getHealth();
            if (fromFighter.overlaps(onFighter)) {
                if(oldHealth > 0){
                    SoundManager soundManager = SoundManager.getInstance();

                    int damage;
                    if(attackType == Enumerations.AttackType.PUNCH){
                        damage = 5;
                        soundManager.playPunchSound();
                    }
                    else {
                        damage = 10;
                        soundManager.playKickSound();
                    }

                    int healthAfterDamage = oldHealth - damage;
                    healthAfterDamage = healthAfterDamage < 0 ? 0 : healthAfterDamage;
                    onFighter.setHealth(healthAfterDamage);

                    float blowImpact = onFighter.isOnLeft ? 15 : -15;
                    onFighter.setX(onFighter.getX() + blowImpact);

                    this.updatePlayerHealthBar();
                    this.updateEnemyHealthBar();
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
                performAttack(player,enemy,Enumerations.AttackType.KICK);
            }
        });

        bButton.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                performAttack(player,enemy,Enumerations.AttackType.PUNCH);
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
                Gdx.app.log("X",Float.toString(deltaX));
                Gdx.app.log("Y",Float.toString(deltaY));
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
        enemy.acceleration = 400;
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

    private void setupWinLooseLabel(){
        float fontScale = 1.5f;

        label = new Label("You Won!", labelStyle);
        label.setFontScale(fontScale);

        overlayTable.row().padTop(screenHeight / 12).padBottom(screenHeight / 12);
        overlayTable.add(label).size(label.getWidth() * fontScale, label.getHeight() * fontScale).expandX();
    }

    private void setupWinLooseButton(){

        restartButton = new TextButton("Rematch", hudSkin.get(("default"), TextButton.TextButtonStyle.class));
        restartButton.setOrigin(Align.center);
        restartButton.setTransform(true);
        restartButton.setScale(3);

        overlayTable.row().padTop(screenHeight / 12).padBottom(screenHeight / 12);
        overlayTable.add(restartButton).size(restartButton.getWidth(), restartButton.getHeight()).expandX();

        restartButton.addListener(new ActorGestureListener() {
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                game.setScreen(new GameScreen());
            }
        });
    }

    private void setupWinLooseOverlay(){

        overlayTable = new Table();
        overlayTable.background(hudSkin.getDrawable("window-c"));

        float tableWidth = screenWidth/3;
        float tableHeight = 2*screenHeight/3;

        overlayTable.setSize(tableWidth, tableHeight);
        overlayTable.setPosition(screenWidth/2 - tableWidth/2,screenHeight/2 - tableHeight/2);
        overlayTable.setVisible(false);

        this.setupWinLooseLabel();

        this.setupWinLooseButton();

        mainStage.addActor(overlayTable);
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

        this.setupWinLooseOverlay();

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
            this.removeAllLiseners();

            if (player.getHealth() > enemy.getHealth()){
                this.removeAllLiseners();
                this.showResult("You Won!");
            }else {
                this.removeAllLiseners();
                this.showResult("You Lost!");
            }
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

    private void showResult(String text){
        table.setVisible(false);
        label.setText(text);
        overlayTable.setVisible(true);
    }

    private void performFinishingMoveAnimationOn(Fighter fighter){
        float blowDistance = this.isPlayerOnLeftOfEnemy() ? 60 : -60;
        float newEnemyX =  fighter.getX() + blowDistance;
        float newEnemyY = fighter.getY() - blowDistance;
        fighter.setPosition(newEnemyX,newEnemyY);
        fighter.setAnimation(this.getAnimationOnFighter(fighter,Enumerations.AttackType.FINISHING_MOVE));
    }

    private void handleWinLooseConditions(){

        if(!timesUp && !isEnemyDead && !isPlayerDead){
            if(enemy.getHealth() <= 0){
                this.isEnemyDead = true;
                this.shouldEnemyMove = false;
                this.performFinishingMoveAnimationOn(enemy);
                this.removeAllLiseners();

                this.showResult("You Won!");
            }
            else if(player.getHealth() <= 0) {
                this.isPlayerDead = true;
                this.shouldPlayerMove = false;
                this.performFinishingMoveAnimationOn(player);
                this.removeAllLiseners();
                this.showResult("You Lost!");
            }
        }

    }

    Random random = new Random(100);
    private void handleEnemyAI(){

        if(!isEnemyDead && random.nextInt() % 2 == 0) {
            this.shouldEnemyMove = true;
            Vector2 enemyVector = new Vector2(this.enemy.getX(),this.enemy.getY());
            Vector2 playerVector = new Vector2(this.player.getX(),this.player.getY());


            Vector2 resultantVector;
            if (enemy.getHealth() < 30 && enemy.getHealth() < player.getHealth()){
                //Run cause almost dead
                resultantVector = enemyVector.sub(playerVector);
            }
            else{
                //Follow and attack
                resultantVector = playerVector.sub(enemyVector);
            }

            this.enemy.resumeMovement();

            this.enemy.accelerateAtAngle(resultantVector.angle());
        }

        if(enemy.overlaps(player)){
            this.shouldEnemyMove = false;
            this.enemy.stopMovement();

            Enumerations.AttackType attackType = random.nextInt() % 2 == 0 ? Enumerations.AttackType.KICK : Enumerations.AttackType.PUNCH;
            this.performAttack(enemy,player, attackType);
        }

    }

    private void handleActorUpdates(float delta){

        touchpad.act(delta);

        if(shouldPlayerMove){
            player.act(delta);
        }

        if(shouldEnemyMove){
            enemy.act(delta);
        }

        player.boundToWorld();
        enemy.boundToWorld();
    }

    @Override
    public void update(float delta) {

        if(enemy.isAnimationFinished() && !isEnemyDead){
            enemy.isAlreadyAttacking = false;
            Animation animation = enemy.isOnLeft ? enemy.idle : enemy.rightIdle;
            enemy.setAnimation(animation);
        }

        if(player.isAnimationFinished() && !isPlayerDead){
            player.isAlreadyAttacking = false;
            Animation animation = player.isOnLeft ? player.idle : player.rightIdle;
            player.setAnimation(animation);
        }

        this.handlePlayerRotations();

        this.handleTimer(delta);

        this.handleWinLooseConditions();

        this.handleEnemyAI();

        this.handleActorUpdates(delta);

    }

}
