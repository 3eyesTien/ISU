package com.thgame.isu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.thgame.isu.GameManager;
import com.thgame.isu.ISU;
import com.thgame.isu.handler.Content;
import com.thgame.isu.ui.TextImage;

public class HighScoreStage extends Stage {

    private Array<TextImage> difficulties;
    private Array<TextImage> scores;
    private TextImage back;

    public HighScoreStage(GameManager gm, int[] difficultyScores){
        super(gm);

        String text[] = {"easy", "normal", "hard"};

        difficulties = new Array<TextImage>();
        for(int i = 0; i < text.length; i++){
            difficulties.add(new TextImage(text[i], ISU.WIDTH / 2, ISU.HEIGHT / 2 + 300 - 200 * i, 50));
        }

        scores = new Array<TextImage>();
        scores.add(new TextImage(Integer.toString(Content.getEasyScore()), ISU.WIDTH / 2, ISU.HEIGHT / 2 + 230, 50));
        scores.add(new TextImage(Integer.toString(Content.getNormalScore()), ISU.WIDTH / 2, ISU.HEIGHT / 2 + 230 - 200, 50));
        scores.add(new TextImage(Integer.toString(Content.getHardScore()), ISU.WIDTH / 2, ISU.HEIGHT / 2 + 230 - 400, 50));

        /*for(int i = 0; i < difficultyScores.length; i++){
            scores.add(new TextImage(Integer.toString(difficultyScores[i]), ISU.WIDTH / 2, ISU.HEIGHT / 2 + 230 - 200 * i, 50));
        }*/

        back = new TextImage("back", ISU.WIDTH / 2, 90, 40);
    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.x = Gdx.input.getX();
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse, gamePort.getScreenX(), gamePort.getScreenY(), gamePort.getScreenWidth(), gamePort.getScreenHeight());
            if(back.contains(mouse.x, mouse.y)){
                gm.setStage(new TransitionStage(gm, this, new MenuStage(gm), TransitionStage.Type.BLACK_FADE));
            }
        }
    }

    @Override
    public void update(float dt){
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch){

        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for(int i = 0; i < difficulties.size; i++){
            difficulties.get(i).render(batch);
        }

        for(int i = 0; i < scores.size; i++){
            scores.get(i).render(batch);
        }

        back.render(batch);

        batch.end();

        resize(batch);
    }
}