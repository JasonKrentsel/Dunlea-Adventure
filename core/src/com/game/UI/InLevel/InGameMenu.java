package com.game.UI.InLevel;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class InGameMenu {
    SpriteBatch batch;
    Camera camera;
    Vector2 offset = new Vector2();

    InGameMenu(SpriteBatch batch, Camera cam){
        this.batch = batch;
        this.camera = cam;
    }
}
