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
import com.game.UI.Menu.Actors.ThemedTextButton;
import com.game.UI.Menu.LevelSelector;
import com.game.UI.Menu.MainMenuScreen;

public class GameplayUI implements Updatable{
    Stage stage;
    GameMain game;
    Skin skin = new Skin(new FileHandle("default/skin/uiskin.json"));
    BitmapFont font = new BitmapFont();

    private boolean isPaused;

    public GameplayUI(GameMain game,Stage UIstage){
        stage = UIstage;
        this.game = game;
        Gdx.input.setInputProcessor(stage);
        create();
    }

    /**
     * Buttons and stuff
     */
    Window pauseWindow = new Window("Paused",skin);
        ThemedTextButton resume = new ThemedTextButton("Resume");
        ThemedTextButton exit = new ThemedTextButton("Exit");
        ThemedTextButton levelselect = new ThemedTextButton("Levels");
        ThemedTextButton mainMenu = new ThemedTextButton("MainMenu");

    ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Menu/gear.png"))));

    private void create(){
        settingsButton.setPosition(GameMain.realRes.x-settingsButton.getWidth(), GameMain.realRes.y-settingsButton.getHeight());
        pauseWindow.setPosition((GameMain.realRes.x/2)-(pauseWindow.getWidth()/2),(GameMain.realRes.y/2)-(pauseWindow.getHeight()/2));
        pauseWindow.setVisible(false);
        pauseWindow.add(resume).padBottom(3);
        pauseWindow.row();
        pauseWindow.add(mainMenu).padBottom(3);
        pauseWindow.row();
        pauseWindow.add(levelselect).padBottom(3);
        pauseWindow.row();
        pauseWindow.add(exit).padBottom(3);
        pauseWindow.row();
        pauseWindow.setHeight(resume.getHeight()*5f);
        pauseWindow.setWidth(resume.getWidth());
        stage.addActor(settingsButton);
        stage.addActor(pauseWindow);
    }
    /**
     * Buttons and stuff
     */
    float elapsedUnpaused = 0;
    float elapsedPaused = 0;
    @Override
    public void update() {
        elapsedUnpaused += Gdx.graphics.getDeltaTime();
        if(settingsButton.isPressed() || (Gdx.input.isKeyPressed(Input.Keys.P) && elapsedUnpaused >.5)){
            isPaused = true;
            settingsButton.setVisible(false);
            pauseWindow.setVisible(true);
        }
        if(isPaused){
            elapsedUnpaused = 0;
            elapsedPaused += Gdx.graphics.getDeltaTime();
            if(resume.isPressed() || (Gdx.input.isKeyPressed(Input.Keys.P) && elapsedPaused >.5)){
                isPaused = false;
                settingsButton.setVisible(true);
                pauseWindow.setVisible(false);
            }
        }
        else {
            elapsedPaused = 0;
        }

        if(levelselect.isPressed()){
            game.setScreen(new LevelSelector(game));
        }

        if(exit.isPressed()){
            Gdx.app.exit();
        }

        if(mainMenu.isPressed()){
            game.setScreen(new MainMenuScreen(game));
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public boolean isPaused(){
        return isPaused;
    }
}
