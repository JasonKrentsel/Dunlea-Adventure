package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.TesterLvl.Level;
import com.game.UI.Menu.MainMenuScreen;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class GameMain extends Game {
	// static variables use through out the game
	public static SpriteBatch batch;
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final Vector2 realRes = new Vector2();
	public static Screen mainMenu;
	static {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		realRes.set(gd.getDisplayMode().getWidth(),gd.getDisplayMode().getHeight());
	}
	/**
	 * PPM or pixels per inch:
	 * Box2d is not the best physics engine and each pixel is treated as a meter.
	 * With 9.8 m/s/s gravity it looks slow, even when scaling it up to 980 m/s/s (9.8 m/s/s * 100 cm).
	 * therefor we must use a factor to scale all physics simulations down, while keeping the textures normal
	 */
	public static final float PPM = 100;

	public void create () {
		batch = new SpriteBatch();
		mainMenu = new MainMenuScreen(this);
		setScreen(mainMenu);
	}

	public void render () {
		super.render();
	}
}
