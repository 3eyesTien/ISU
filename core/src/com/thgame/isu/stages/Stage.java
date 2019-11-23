package com.thgame.isu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.thgame.isu.GameManager;
import com.thgame.isu.ISU;

public abstract class Stage{

    protected GameManager gm;

    protected OrthographicCamera cam;
    public OrthographicCamera getCam() { return cam; }

    protected Viewport gamePort;
    public Viewport getGamePort() { return gamePort; }

    protected Vector3 mouse;    // For touch/mouse coordinates

    Texture screenEdge;

    protected Stage(GameManager gm){

        this.gm = gm;

        cam = new OrthographicCamera(ISU.WIDTH, ISU.HEIGHT);
        cam.setToOrtho(false, ISU.WIDTH, ISU.HEIGHT);
        this.gamePort = new FitViewport(ISU.WIDTH, ISU.HEIGHT, cam);
        this.cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        this.cam.update();

        mouse = new Vector3();

        // Create black texture to fill in bars at the edge of the screen
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        screenEdge = new Texture(pixmap);
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);
    public void resize(SpriteBatch batch){

        if (gamePort instanceof ScalingViewport) {
            // This shows how to set the viewport to the whole screen and draw within the black bars.
            ScalingViewport scalingViewport = (ScalingViewport)gamePort;
            int screenWidth = Gdx.graphics.getWidth();
            int screenHeight = Gdx.graphics.getHeight();

            HdpiUtils.glViewport(0, 0, screenWidth, screenHeight);
            batch.getProjectionMatrix().idt().setToOrtho2D(0, 0, screenWidth, screenHeight);
            batch.getTransformMatrix().idt();
            batch.begin();

            float leftGutterWidth = scalingViewport.getLeftGutterWidth();
            if (leftGutterWidth > 0) {
                batch.draw(screenEdge, 0, 0, leftGutterWidth, screenHeight);
                batch.draw(screenEdge, scalingViewport.getRightGutterX(), 0, scalingViewport.getRightGutterWidth(), screenHeight);
            }
            float bottomGutterHeight = scalingViewport.getBottomGutterHeight();
            if (bottomGutterHeight > 0) {
                batch.draw(screenEdge, 0, 0, screenWidth, bottomGutterHeight);
                batch.draw(screenEdge, 0, scalingViewport.getTopGutterY(), screenWidth, scalingViewport.getTopGutterHeight());
            }
            batch.end();

            gamePort.update(screenWidth, screenHeight, true); // Restore viewport.
        }
    }

    public void dispose(){
        this.dispose();
    }
}