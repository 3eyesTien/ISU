package com.thgame.isu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.thgame.isu.GameManager;
import com.thgame.isu.ISU;
import com.thgame.isu.handler.Content;
import com.thgame.isu.ui.TextImage;

public class DifficultyStage extends Stage{

    private Array<TextImage> buttons;
    private TextImage back;

    public DifficultyStage(GameManager gm){
        super(gm);

        String[] text = {"easy", "normal", "hard"};
        buttons = new Array<TextImage>();
        for(int i = 0; i < text.length; i++){
            buttons.add(new TextImage(text[i], ISU.WIDTH / 2, ISU.HEIGHT / 2 + 150 - 130 * i, 50));
        }
        back = new TextImage("back", ISU.WIDTH / 2, 90, 40);
    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.x = Gdx.input.getX();
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse, gamePort.getScreenX(), gamePort.getScreenY(), gamePort.getScreenWidth(), gamePort.getScreenHeight());
            for(int i = 0; i < buttons.size; i++){
                if(buttons.get(i).contains(mouse.x, mouse.y)){
                    Content.playCorrect();
                    gm.setStage(new TransitionStage(gm, this, new PlayStage(gm, PlayStage.DIFFICULTY.values()[i]), TransitionStage.Type.EXPAND));
                }
            }
            if(back.contains(mouse.x, mouse.y)){
                Content.playBack();
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
        for(int i = 0; i < buttons.size; i++){
            buttons.get(i).render(batch); // <---- This line is causing an Array Out Of Bounds error for some reason
        }
        back.render(batch);
        batch.end();

        resize(batch);
    }
}

