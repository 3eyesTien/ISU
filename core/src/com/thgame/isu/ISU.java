package com.thgame.isu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thgame.isu.handler.Content;
import com.thgame.isu.stages.MenuStage;

public class ISU extends ApplicationAdapter {
	public static final String TITLE = "ISU";

	// Game dimensions (portrait), not screen size
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	//public static Content resource;
	public static GameManager gm;
	public static SpriteBatch batch;

	@Override
	public void resume(){
		Content.init();
		Content.loadAtlas("pack.pack", "pack");
		super.resume();
	}

	/*@Override
	public void pause(){
		Save.save();
		super.pause();
	}*/

	@Override
	public void create() {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
		//Gdx.gl.glClearColor(0.86f, 0.85f, 0.75f, 1);	// Pastel Grey

		//Save.load();

		// Load assets here
		Content.init();
		Content.loadAtlas("pack.pack", "pack");
		//resource = new Content();
		//resource.loadAtlas("pack.pack", "pack");

		batch = new SpriteBatch();

		this.gm = new GameManager();
		gm.push(new MenuStage(gm));
		//setScreen(new GameScreen(gm));
	}

	@Override
	public void render(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gm.update(Gdx.graphics.getDeltaTime());
		gm.render(batch);
	}

	@Override
	public void dispose(){
		batch.dispose();
	}
}
