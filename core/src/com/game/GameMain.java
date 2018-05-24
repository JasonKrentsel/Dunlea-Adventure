package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.TesterLvl.TestLevel;

public class GameMain extends Game {
	// static variables use through out the game
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
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
		setScreen(new TestLevel(batch));
	}

	public void render () {
		super.render();
	}
}
