package com.gbc.inderdeep.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.gbc.inderdeep.utils.AudioNames;

public class SoundManager {

    // Singleton: unique instance
    private static SoundManager instance;
    private Music bgMusic;
    private Sound punchAudio;
    private Sound kickAudio;

    private long punchAudioID = -1;
    private long kickAudioID = -1;

    SoundManager(){
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal(AudioNames.backgroundMusicAudio));
        punchAudio = Gdx.audio.newSound(Gdx.files.internal(AudioNames.punchAudio));
        kickAudio = Gdx.audio.newSound(Gdx.files.internal(AudioNames.kickAudio));
    }

    // Singleton: retrieve instance
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void startBackGroundMusic(){
        bgMusic.play();
        bgMusic.setLooping(true);
    }

    public void playPunchSound(){

        if(punchAudioID != -1){
            punchAudio.stop(punchAudioID);
        }
        punchAudioID = punchAudio.play(1.0f);
    }

    public void playKickSound(){
        if(kickAudioID != -1){
            kickAudio.stop(kickAudioID);
        }
        kickAudioID = kickAudio.play(1.0f);
    }

}

