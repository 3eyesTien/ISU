package com.thgame.isu.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thgame.isu.ISU;
import com.thgame.isu.handler.Content;

public class Tile extends Box {

    protected TextureAtlas atlas;
    protected TextureRegion dark;
    protected TextureRegion light;

    protected float totalWidth;
    protected float totalHeight;

    protected float timer = 0;
    protected float maxTime = 0.3f;
    protected float lifeSpan = 4;
    protected float errorTimer = 0; // Timer for how long a wrong tile shows up on the board

    private boolean isSolution;
    private boolean selected;
    private boolean litUp;
    private boolean errorSelection; // Boolean for if the tile selected was not a target on the game field

    public Tile(float x, float y, float width, float height){

        this.x = x;
        this.y = y;

        this.width = 0;
        this.height = 0;
        this.totalWidth = width - 8;    // The "-8" allows for padding space
        this.totalHeight = height - 8;

        dark = Content.getAtlas("pack").findRegion("dark");
        light = Content.getAtlas("pack").findRegion("light");

        isSolution = false;
        selected = false;
        litUp = false;
        errorSelection = false;
    }

    public void update(float dt){

        if(width < totalWidth && height < totalHeight) {

            timer += dt;
            width = (timer/maxTime) * totalWidth;
            height = (timer/maxTime) * totalHeight;

            if(width < 0) width = 0;
            if(width > totalWidth) width = totalWidth;
            if(height < 0) height = 0;
            if(height > totalHeight) height = totalHeight;
        }

        lifeSpan -= dt;
        if(lifeSpan <= 0) lifeSpan = 0;

        if(errorSelection){
            errorTimer += dt;
            if(errorTimer > 1){
                setErrorSelection(false);
                errorTimer = 0;
            }
        }
    }

    public void render(SpriteBatch batch) {
        if(litUp) {
            batch.draw(light, x - width / 2, y - height / 2, width, height);
        }
        else if(errorSelection){
            batch.setColor(0.5f, 0.3f, 0, 1);
            batch.draw(light, x - width / 2, y - height / 2, width, height);
            batch.setColor(1, 1, 1, 1);
        }
        else if(!litUp) {
            batch.draw(dark, x - width / 2, y - height / 2, width, height);
        }
    }

    // Setters
    public void setMaxTime(float t) {this.maxTime = t;}
    public void setTimer(float t) {this.timer = t;}
    public void setLitUp(boolean b) {this.litUp = b;}
    public void setSelected(boolean b) {this.selected = b;}
    public void setSolution(boolean b) {this.isSolution = b;}
    public void setLifeSpan(float span) {this.lifeSpan = span;}
    public void setErrorSelection(boolean b) {this.errorSelection = b;}

    // Getters
    public float getX() {return x;}
    public float getY() {return y;}
    public float getWidth() {return width;}
    public float getHeight() {return height;}
    public boolean isSelected() {return selected;}
    public boolean isSolution() {return isSolution;}
    public boolean isLitUp() {return litUp;}
    public float getTimer() {return timer;}
    public float getLifeSpan() {return lifeSpan;}
}
