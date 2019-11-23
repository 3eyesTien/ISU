package com.thgame.isu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.thgame.isu.stages.MenuStage;

public class GameScreen implements Screen {

    protected GameManager gm;

    public GameScreen(GameManager newGm){
        this.gm = newGm;
        gm.push(new MenuStage(gm));
    }

    @Override
    public void show() {
        //this.gm = new GameManager();
        //gm.push(new MenuStage(gm));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gm.update(delta);
        gm.render(ISU.batch);
    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void pause() {
        Gdx.app.log("Gamescreen", "pause called");
        //Save.save();
    }

    @Override
    public void resume() {
        //Save.load();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
