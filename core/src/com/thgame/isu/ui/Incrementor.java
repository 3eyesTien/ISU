package com.thgame.isu.ui;

public class Incrementor extends TextImage{

    private int value;
    private int destValue;  // Destination score

    private int score;

    private float incrementSpeed = 60; //100;

    public Incrementor(float x, float y, float newSize){
        super("0", x, y, newSize);
    }

    public void incrementValue(int i){
        score = 0;
        destValue = value + i;
        if(destValue < 0) { destValue = 0;}
    }

    public void update(float dt){

        // Increase score to new score
        if(value < destValue){
            score = destValue;
            value += (int) (incrementSpeed * dt);
            if(value > destValue) { value = destValue;}
        }

        if(value > destValue){
            score = destValue;
            value -= (int) (incrementSpeed * dt);
            if(value < destValue) {value = destValue;}
        }



        setText(Integer.toString(value));
    }

    public int getScore() { return score;}
    public void setValue(int newValue){ this.value = newValue;}

    // Score is done being updated
    public boolean isDone() { return value == destValue;}
}
