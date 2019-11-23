package com.thgame.isu.stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.thgame.isu.GameManager;
import com.thgame.isu.ISU;
import com.thgame.isu.handler.Content;
import com.thgame.isu.ui.Expand;

public class TransitionStage extends Stage {

    public enum Type{
        BLACK_FADE,
        EXPAND;
    }

    private Stage prev;
    private Stage next;
    private Type type;

    private TextureRegion dark;

    // Black fading effect
    private float maxTime;  // Max amount of time for the loading transition
    private float timer = 0;
    private float alpha;

    // Expand transition
    private Expand[][] expands;
    private boolean doneExpanding;
    private boolean doneContracting;
    private float delayTimer = 0.06f;

    public TransitionStage(GameManager gm, Stage prev, Stage next, Type type){
        super(gm);
        this.prev = prev;
        this.next = next;
        this.type = type;

        dark = Content.getAtlas("pack").findRegion("dark");

        if(type == Type.BLACK_FADE){
            maxTime = 1;
        }
        else if(type == Type.EXPAND){
            maxTime = 1;
            int size = 80;

            int numRow = MathUtils.ceil(ISU.HEIGHT / size);
            int numCol = MathUtils.ceil(ISU.WIDTH / size);
            expands= new Expand[numRow][numCol];
            for(int row = 0; row < expands.length; row++){
                for(int col = 0; col < expands[0].length; col++){
                    expands[row][col] = new Expand(col * size + size / 2, row * size + size / 2, size, size); // Make new expanding tile at that location in grid
                    //expands[row][col].setMaxTime(0.75f);
                    expands[row][col].setTimer((-(expands.length - row) - col) * delayTimer);
                }
            }
        }
    }

    @Override
    public void handleInput(){} // Nothing here because it's a loading transition

    @Override
    public void update(float dt){
        timer += dt;
        if(type == Type.BLACK_FADE){
            if(timer >= maxTime){
                gm.setStage(next);
            }
        }
        else if(type == Type.EXPAND){
            if(!doneExpanding){
                boolean okay = true;
                for(int row = 0; row < expands.length; row++){
                    for(int col = 0; col < expands[0].length; col++){
                        expands[row][col].update(dt);
                        if(!expands[row][col].isDoneExpanding()){
                            okay = false;
                        }
                    }
                }
                if(okay && !doneExpanding){
                    doneExpanding = true;
                    for(int row = 0; row < expands.length; row++) {
                        for(int col = 0; col < expands[0].length; col++){
                            expands[row][col].setContracting((-(expands.length - row) + col) * 0.06f);
                        }
                    }
                }
            }
            else {
                boolean okay = true;
                for(int row = 0; row < expands.length; row++){
                    for(int col = 0; col < expands[0].length; col++){
                        expands[row][col].update(dt);
                        if(!expands[row][col].isDoneContracting())
                        {
                            okay = false;
                        }
                    }
                }
                if(okay && !doneContracting){
                    doneContracting = true;
                    gm.setStage(next);
                }
            }

        }
    }

    @Override
    public void render(SpriteBatch batch){

        if(type == Type.BLACK_FADE){
            if(timer == maxTime / 2){
                alpha = timer / (maxTime / 2);
                prev.render(batch);
            }
            else{
                alpha = 1 - (timer / (maxTime / 2));
                next.render(batch);
            }

            batch.setColor(0, 0, 0, alpha);
            batch.setProjectionMatrix(cam.combined);
            batch.begin();

            batch.draw(dark, 0, 0, ISU.WIDTH, ISU.HEIGHT);

            batch.end();

            batch.setColor(1, 1, 1, 1);
        }
        else if (type == Type.EXPAND){
            if(!doneExpanding){
                prev.render(batch);
            } else{
                next.render(batch);
            }

            batch.setProjectionMatrix(cam.combined);
            batch.begin();

            for(int row = 0; row < expands.length; row++){
                for(int col = 0; col < expands[0].length; col++){
                    expands[row][col].render(batch);
                }
            }
            batch.end();
        }
        resize(batch);
    }
}