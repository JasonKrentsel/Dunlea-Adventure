package com.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.backgroundFPS = 120;
		config.foregroundFPS = 120;
		config.fullscreen = false;
		config.width = GameMain.WIDTH;
		config.height = GameMain.HEIGHT;

		new LwjglApplication(new GameMain(), config);
	}
}
