package com.thgame.isu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thgame.isu.GameManager;
import com.thgame.isu.ISU;
import com.thgame.isu.handler.Content;
import com.thgame.isu.handler.Save;
import com.thgame.isu.ui.Graphic;
import com.thgame.isu.ui.TextImage;
import com.thgame.isu.ui.Tile;

public class MenuStage extends Stage {

    private Graphic title;
    private TextImage play;
    private TextImage scores;
    private TextImage tutorial;

    private Tile playUnderline;
    private Tile scoresUnderline;
    private Tile tutorialUnderline;

    public MenuStage(GameManager gsm){
        super(gsm);

        title = new Graphic(
                Content.getAtlas("pack").findRegion("isu"),
                ISU.WIDTH / 2,
                ISU.HEIGHT / 2 + 150);

        play = new TextImage("play", ISU.WIDTH / 2, ISU.HEIGHT / 2 - 50, 40);
        scores = new TextImage("score", ISU.WIDTH / 2, ISU.HEIGHT / 2 - 150, 40);
        tutorial = new TextImage("tutorial", ISU.WIDTH / 2, ISU.HEIGHT / 2 - 250, 40);

        playUnderline = new Tile(play.getX() + 5, play.getY() - 25, play.getWidth() + 5, 10);
        playUnderline.setLitUp(true);
        playUnderline.setMaxTime(0.35f);

        scoresUnderline = new Tile(scores.getX() + 10 , scores.getY() - 25, scores.getWidth() + 15, 10);
        scoresUnderline.setLitUp(true);
        scoresUnderline.setMaxTime(0.35f);

        tutorialUnderline = new Tile(tutorial.getX() + 15, tutorial.getY() - 25, tutorial.getWidth() + 20, 10);
        tutorialUnderline.setLitUp(true);
        tutorialUnderline.setMaxTime(0.35f);
    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.x = Gdx.input.getX();
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse, gamePort.getScreenX(), gamePort.getScreenY(), gamePort.getScreenWidth(), gamePort.getScreenHeight());
            if(play.contains(mouse.x, mouse.y)){
                Content.playCorrect();
                gm.setStage(new TransitionStage(gm, this, new DifficultyStage(gm), TransitionStage.Type.BLACK_FADE));
            }

            if(scores.contains(mouse.x, mouse.y)) {
                Content.playCorrect();
                gm.setStage(new TransitionStage(gm, this, new HighScoreStage(gm, Save.getDifficultyScores()), TransitionStage.Type.BLACK_FADE));
            }
            if(tutorial.contains(mouse.x, mouse.y)){
                Content.playCorrect();
                gm.setStage(new TransitionStage(gm, this, new TutorialStage(gm), TransitionStage.Type.BLACK_FADE));
            }
        }
    }

    @Override
    public void update(float dt){
        handleInput();
        playUnderline.update(dt);
        scoresUnderline.update(dt);
        tutorialUnderline.update(dt);
    }

    @Override
    public void render(SpriteBatch batch){

        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        title.render(batch);
        play.render(batch);
        playUnderline.render(batch);
        scores.render(batch);
        scoresUnderline.render(batch);
        tutorial.render(batch);
        tutorialUnderline.render(batch);

        batch.end();
        resize(batch);
    }
}

