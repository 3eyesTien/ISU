package com.thgame.isu;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thgame.isu.stages.Stage;

import java.util.Stack;


public class GameManager {

    private Stack<Stage> stages;

    public GameManager(){
        stages = new Stack<Stage>();
    }

    public void push(Stage s){
        stages.push(s);
    }

    public void pop(){

        stages.peek().dispose();
        stages.pop();
    }

    public void setStage(Stage s){
        stages.pop();
        stages.push(s);
    }

    public void update(float dt){
        stages.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        stages.peek().render(sb);
    }

}

