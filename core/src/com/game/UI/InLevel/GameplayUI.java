package com.game.UI.InLevel;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.StateUpdate.DrawUpdatable;
import com.game.StateUpdate.Updatable;

public class GameplayUI implements Updatable{
    Stage stage = new Stage();
    Skin skin = new Skin();
    BitmapFont font = new BitmapFont();

    public GameplayUI(){
        create();
    }

    /**
     * Buttons and stuff
     */
    private void create(){

    }
    /**
     * Buttons and stuff
     */

    @Override
    public void update() {
        stage.draw();
    }
}
