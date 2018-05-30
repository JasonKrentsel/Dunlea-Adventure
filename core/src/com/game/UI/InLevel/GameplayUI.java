package com.game.UI.InLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.GameMain;
import com.game.StateUpdate.DrawUpdatable;
import com.game.StateUpdate.Updatable;

public class GameplayUI implements Updatable{
    Stage stage;
    Skin skin = new Skin(new FileHandle("default/skin/uiskin.json"));
    BitmapFont font = new BitmapFont();

    private boolean isPaused;

    public GameplayUI(Stage UIstage){
        stage = UIstage;
        Gdx.input.setInputProcessor(stage);
        create();
    }

    /**
     * Buttons and stuff
     */
    Window pauseWindow = new Window("Paused",skin);
        TextButton resume = new TextButton("Resume",skin);
        TextButton exit = new TextButton("Exit",skin);

    ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Menu/settings.png"))));
    private void create(){
        settingsButton.setPosition(0, GameMain.HEIGHT-settingsButton.getHeight());
        pauseWindow.setPosition((GameMain.WIDTH/2)-(pauseWindow.getWidth()/2),(GameMain.HEIGHT/2)-(pauseWindow.getHeight()/2));
        pauseWindow.setVisible(false);
        pauseWindow.add(resume);
        pauseWindow.row();
        pauseWindow.add(exit);
        pauseWindow.row();
        stage.addActor(settingsButton);
        stage.addActor(pauseWindow);
    }
    /**
     * Buttons and stuff
     */
    @Override
    public void update() {
        if(settingsButton.isPressed()){
            isPaused = true;
            settingsButton.setVisible(false);
            pauseWindow.setVisible(true);
        }
        if(isPaused){
            if(resume.isPressed()){
                isPaused = false;
                settingsButton.setVisible(true);
                pauseWindow.setVisible(false);
            }
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public boolean isPaused(){
        return isPaused;
    }
}
