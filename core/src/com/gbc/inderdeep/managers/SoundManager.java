package com.gbc.inderdeep.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.gbc.inderdeep.utils.AudioNames;

public class SoundManager {

    // Singleton: unique instance
    private static SoundManager instance;
    private long backgroundMusicID = -1;
    private boolean isBackgroundMusicRunning = false;
    private Sound backgroundMusic;

    // Singleton: private constructor
    private SoundManager() {
        this.backgroundMusic = Gdx.audio.newSound(Gdx.files.internal(AudioNames.backgroundMusicAudio));
    }

    // Singleton: retrieve instance
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void startBackGroundMusic(boolean looping){
        if(this.backgroundMusicID == -1){
            Gdx.app.log("SoundManager","Playing sound!");
            this.backgroundMusicID = this.backgroundMusic.loop();
//            this.backgroundMusic.setLooping(this.backgroundMusicID,looping);
            this.isBackgroundMusicRunning = true;
        }
    }

    public void pauseOrPlayBackgroundMusic(){
        if(this.backgroundMusicID != -1){
            if (this.isBackgroundMusicRunning) {
                Gdx.app.log("SoundManager","Pausing sound!");
                this.isBackgroundMusicRunning = false;
                this.backgroundMusic.pause(this.backgroundMusicID);
            }
            else {
                Gdx.app.log("SoundManager","Resuming sound!");
                this.isBackgroundMusicRunning = true;
                this.backgroundMusic.resume(this.backgroundMusicID);
            }
        }else {
            this.startBackGroundMusic(true);
        }
    }

    public void stopBackgroundMusic(){
        if(this.backgroundMusicID != -1){
            this.backgroundMusic.stop(this.backgroundMusicID);
            this.backgroundMusicID = -1;
        }
    }

}

