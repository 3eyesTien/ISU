package com.thgame.isu.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

public class Content {

    private static HashMap<String, TextureAtlas> atlases;

    public static Preferences pref;

    public static void init(){
        atlases = new HashMap<String, TextureAtlas>();
        pref = Gdx.app.getPreferences("ISU");

        //Load music here

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
}

