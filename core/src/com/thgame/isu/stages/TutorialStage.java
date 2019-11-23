package com.thgame.isu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.thgame.isu.GameManager;
import com.thgame.isu.ISU;
import com.thgame.isu.ui.TextImage;

public class TutorialStage extends Stage{

    private Array<TextImage> line1;
    private Array<TextImage> line2;
    private TextImage back;

    public TutorialStage(GameManager gm) {
        super(gm);

        String[] text1 = {"tap", "the", "lit"};
        String[] text2 = {"up", "tiles"};

        line1 = new Array<TextImage>();
        line2 = new Array<TextImage>();

        for(int i = 0; i < text1.length; i++){
            line1.add(new TextImage(text1[i], ISU.WIDTH / 2 - 50 * text1[i].length() + 150 * i, 600, 40));
        }
        for(int j = 0; j < text2.length; j++){
            line2.add(new TextImage(text2[j], ISU.WIDTH / 2 - 55 * text2[j].length() + 330 * j, 500, 40));
        }
        back = new TextImage("back", ISU.WIDTH / 2, 90, 40);
    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.x = Gdx.input.getX();
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse, gamePort.getScreenX(), gamePort.getScreenY(), gamePort.getScreenWidth(), gamePort.getScreenHeight());
            if(back.contains(mouse.x, mouse.y)){
                gm.setStage(new TransitionStage(gm, this, new MenuStage(gm), TransitionStage.Type.EXPAND));
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

        for(int i = 0; i < line1.size; i++) {
            line1.get(i).render(batch);
        }

        for(int j = 0; j < line2.size; j++){
            line2.get(j).render(batch);
        }

        back.render(batch);

        batch.end();
        resize(batch);
    }
}