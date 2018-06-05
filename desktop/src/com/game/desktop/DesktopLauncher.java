package com.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.GameMain;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.backgroundFPS = 120;
		config.foregroundFPS = 120;
		config.width = (int) GameMain.realRes.x;
		config.height = (int) GameMain.realRes.y;
		config.fullscreen = false;
		config.vSyncEnabled = false;
		config.title = "Dunlea's Adventure";

		config.addIcon("icon128.png", Files.FileType.Internal);
		config.addIcon("icon64.png", Files.FileType.Internal);
		config.addIcon("icon32.png", Files.FileType.Internal);
		config.addIcon("icon16.png", Files.FileType.Internal);

		new LwjglApplication(new GameMain(), config);
	}
}
