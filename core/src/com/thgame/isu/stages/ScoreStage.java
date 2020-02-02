package com.thgame.isu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thgame.isu.GameManager;
import com.thgame.isu.ISU;
import com.thgame.isu.handler.Content;
import com.thgame.isu.ui.TextImage;

public class ScoreStage extends Stage {

    private TextImage wording;  // "score" wording
    private TextImage score;    // score value
    private TextImage nextButton;

    public ScoreStage(GameManager gm, int scoreValue){
        super(gm);

        wording = new TextImage("score", ISU.WIDTH / 2, ISU.HEIGHT - (ISU.HEIGHT / 3), 50);
        score = new TextImage(Integer.toString(scoreValue), ISU.WIDTH / 2, ISU.HEIGHT / 2, 40);
        nextButton = new TextImage("next", ISU.WIDTH / 2, ISU.HEIGHT / 4, 50);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            mouse.x = Gdx.input.getX(); // Screen coordinates
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse, gamePort.getScreenX(), gamePort.getScreenY(), gamePort.getScreenWidth(), gamePort.getScreenHeight());

            if (nextButton.contains(mouse.x, mouse.y)) {
                Content.playCorrect();
                gm.setStage(new TransitionStage(gm, this, new MenuStage(gm), TransitionStage.Type.BLACK_FADE));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        wording.render(batch);
        score.render(batch);
        nextButton.render(batch);
        batch.end();

        resize(batch);
    }
}