package com.gbc.inderdeep.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gbc.inderdeep.MainGame;
import com.gbc.inderdeep.managers.ScreenManager;
import com.gbc.inderdeep.managers.SoundManager;
import com.gbc.inderdeep.utils.ScreenSettings;

public class BaseScreen implements Screen, InputProcessor {

    protected Stage mainStage;
    public Stage stage;
    protected MainGame game;
    protected ScreenManager screenManager;
//    protected FontGenerator fontGenerator;
    protected SoundManager soundManager;

    protected float screenHeight;
    protected float screenWidth;

    public boolean isDisposed;

    //SKINS
    protected Skin skin;

    //Actors
    protected ActorBeta background;

    //TABLES
    protected Table table;
    protected Container<Table> tableContainer;

    //LABEL
    protected Label.LabelStyle labelStyle;

    //BUTTON
    protected Button.ButtonStyle buttonStyle;

    public BaseScreen(){
        this.initializeVariables();
        this.reset();
        this.setupScene();
    }

    protected void initialSetup() {
        //GET the InputMultiplexer
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();

        //Add InputProcessor to the screen
        im.addProcessor(this);

        //Add InputProcessor to the stage
        im.addProcessor(mainStage);
        im.addProcessor(stage);
    }

    public void initializeVariables() {

        this.isDisposed = false;

        screenHeight = ScreenSettings.getInstance().height;
        screenWidth = ScreenSettings.getInstance().width;

        skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));
    }

    public void reset(){
        ScreenViewport screenViewport = new ScreenViewport();
        mainStage = new Stage(screenViewport);
        stage = new Stage(screenViewport);
        table = new Table();
        game = MainGame.getInstance();
        screenManager = ScreenManager.getInstance();
//        fontGenerator = FontGenerator.getInstance();
        soundManager = SoundManager.getInstance();

        tableContainer = new Container<Table>();

        float cw = screenWidth * 0.5f;
        float ch = screenHeight * 1.0f;

        tableContainer.setSize(screenWidth, screenHeight);
        tableContainer.setPosition((screenWidth - cw) - (tableContainer.getWidth() / 2), (screenHeight - ch) / 2.0f);
        tableContainer.fillX();
//        tableContainer.setDebug(true);

        this.table.setSize(screenWidth, screenHeight);
        this.table.setBounds(0, 0, screenWidth, screenWidth);

        tableContainer.setActor(this.table);

        //INITIALIZE A DEFAULT BUTTON
        buttonStyle = new Button.ButtonStyle(skin.getDrawable("button-c"), skin.getDrawable("button-pressed-c"), skin.getDrawable("button-over-pressed-c"));

        //INITIALIZE A LABEL
        labelStyle = new Label.LabelStyle(skin.get(("title"), Label.LabelStyle.class));

    }

    protected void setupScene(){
        Gdx.app.log("BaseScreen","Setup screen called from BaseScreen");
    }


    @Override
    public void show() {
        this.initialSetup();
    }

    protected void update(float delta){
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.mainStage.act(delta);
        this.stage.act(delta);

        update(delta);

//        this.mainStage.setDebugAll(true);

        this.mainStage.draw();
        this.stage.draw();

//        this.table.setDebug(true);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        //Get InputProcessor
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();

        //Remove InputProcessor
        im.removeProcessor(this);
        im.removeProcessor(mainStage);
        im.removeProcessor(stage);

    }

    @Override
    public void dispose() {
//        System.out.println("MyClass diposed");
//
//        if ( !this.isDisposed ){
//            this.stage.dispose();
//            this.isDisposed = true;
//            this.table.clearListeners();
//            this.table = null;
//            this.stage = null;
//        }

    }


    @Override
    public boolean keyDown(int keycode) {

        Gdx.app.log("InKeyDown","InKeyDown");
        if(keycode == Input.Keys.BACK){
            Gdx.app.log("InBack","InBack");
//            Enumerations.Screen nextScreen;
//            if(screenManager.currentScreen != Enumerations.Screen.GAME_SCREEN){
//                nextScreen = screenManager.previousScreen;
//            }else{
//                nextScreen = Enumerations.Screen.PAUSE_SCREEN;
//            }
//            Gdx.app.log("InBack",nextScreen.name());
//            screenManager.fadeInToScreen(nextScreen,0.5f);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}

