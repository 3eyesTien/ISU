package com.thgame.isu.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

public class Content {

    private static HashMap<String, TextureAtlas> atlases;

    public static Preferences pref;
    public static Sound correct;
    public static Sound incorrect;
    public static Sound back;
    //public static Music music;

    public static void init(){
        atlases = new HashMap<String, TextureAtlas>();
        pref = Gdx.app.getPreferences("ISU");

        //Load music and sounds here
        correct = Gdx.audio.newSound(Gdx.files.internal("correct.wav"));
        incorrect = Gdx.audio.newSound(Gdx.files.internal("incorrect.wav"));
        back = Gdx.audio.newSound(Gdx.files.internal("Bells_low.wav"));
        //music = Gdx.audio.newMusic(Gdx.files.internal("light_ride.wav"));

        if(!pref.contains("easyScore")){
            pref.putInteger("easyScore", 0);
        }

        if(!pref.contains("normalScore")){
            pref.putInteger("normalScore", 0);
        }

        if(!pref.contains("hardScore")){
            pref.putInteger("hardScore", 0);
        }
    }

    /*public Content(){
        atlases = new HashMap<String, TextureAtlas>();
    }*/

    public static void loadAtlas(String path, String key){
        atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));
    }

    public static void setEasyScore(int val){
        pref.putInteger("easyScore", val);
        pref.flush();   // Saves to the preferences
    }

    public static void setNormalScore(int val){
        pref.putInteger("normalScore", val);
        pref.flush();   // Saves to the preferences
    }

    public static void setHardScore(int val){
        pref.putInteger("hardScore", val);
        pref.flush();   // Saves to the preferences
    }

    public static int getEasyScore(){
        return pref.getInteger("easyScore");
    }

    public static int getNormalScore(){
        return pref.getInteger("normalScore");
    }

    public static int getHardScore(){
        return pref.getInteger("hardScore");
    }

    public static TextureAtlas getAtlas(String key) {
        return atlases.get(key);
    }

    public static void playCorrect(){
        correct.play(0.5f);
    }

    public static void playIncorrect(){
        incorrect.play(0.5f);
    }

    public static void playBack(){ back.play(0.5f);}
    /*
    public static void playMusic(){
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
    }
    */
    public void dispose(){
        correct.dispose();
        incorrect.dispose();
        //music.dispose();
    }
}

