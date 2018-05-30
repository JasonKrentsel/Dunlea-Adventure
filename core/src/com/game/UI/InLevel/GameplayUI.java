package com.game.UI.InLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.StateUpdate.DrawUpdatable;
import com.game.StateUpdate.Updatable;

public class GameplayUI implements Updatable{
    Stage stage;
    Skin skin = new Skin();
    BitmapFont font = new BitmapFont();

    public GameplayUI(Stage UIstage){
        stage = UIstage;
        Gdx.input.setInputProcessor(stage);
        create();
    }

    /**
     * Buttons and stuff
     */
    ImageButton a = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Menu/settings.png"))));
    private void create(){
        stage.addActor(a);
    }
    /**
     * Buttons and stuff
     */
    @Override
    public void update() {
        if(a.isPressed()){
            System.out.println("Pressed");
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
