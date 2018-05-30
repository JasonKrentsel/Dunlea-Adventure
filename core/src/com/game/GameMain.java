package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.TesterLvl.TestLevel;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

public class GameMain extends Game {
	// static variables use through out the game
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final Vector2 realRes = new Vector2();
	public static final Screen mainMenu = null;
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

	// used for drawing every texture in the game, only 1 SpriteBatch object can exist
	public SpriteBatch batch;

	public void create () {
		batch = new SpriteBatch();
		setScreen(new TestLevel(this));
	}

	public void render () {
		super.render();
	}
}
