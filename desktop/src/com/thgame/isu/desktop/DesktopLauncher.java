package com.thgame.isu.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.thgame.isu.ISU;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = ISU.WIDTH;
		config.height = ISU.HEIGHT;
		config.title = ISU.TITLE;

		new LwjglApplication(new ISU(), config);
	}
}
