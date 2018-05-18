package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.TesterLvl.TestLevel;

public class GameMain extends Game {
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final float PPM = 100;

	public SpriteBatch batch;

	public void create () {
		batch = new SpriteBatch();
		setScreen(new TestLevel(this));
	}

	public void render () {
		super.render();
	}
}
