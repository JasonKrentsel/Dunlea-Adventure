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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.GameMain;
import com.game.LevelManager.EndScreen;
import com.game.LevelManager.Level;
import com.game.StateUpdate.DrawUpdatable;
import com.game.StateUpdate.Updatable;
import com.game.UI.Menu.Actors.ThemedTextButton;
import com.game.UI.Menu.LevelSelector;
import com.game.UI.Menu.MainMenuScreen;

public class GameplayUI implements Updatable {
    Stage stage;
    GameMain game;
    Skin skin = new Skin(Gdx.files.internal("default/skin/uiskin.json"));
    BitmapFont font = new BitmapFont();
    Level level;
    public boolean isPaused;

    public GameplayUI(GameMain game, Stage UIstage, Level lvl) {
        level = lvl;
        stage = UIstage;
        this.game = game;
        Gdx.input.setInputProcessor(stage);
        create();
    }

    /**
     * Buttons and stuff
     */
    Window pauseWindow = new Window("Paused", skin);
    ThemedTextButton resume = new ThemedTextButton("Resume");
    ThemedTextButton exit = new ThemedTextButton("Exit");
    ThemedTextButton levelselect = new ThemedTextButton("Levels");
    ThemedTextButton mainMenu = new ThemedTextButton("MainMenu");

    Table dead = new Table(skin);
    ThemedTextButton restart = new ThemedTextButton("Restart");
    ThemedTextButton goToLevelSelect = new ThemedTextButton("Level Select");

    Window endFrame = new Window("", skin);
    ThemedTextButton nextLevel = new ThemedTextButton("Next Level");
    ThemedTextButton finishLevelSelect = new ThemedTextButton("Level Select");
    ThemedTextButton finishExit = new ThemedTextButton("Exit");

    ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("Menu/gear.png"))));

    private void create() {
        settingsButton.setPosition(GameMain.realRes.x - settingsButton.getWidth(), GameMain.realRes.y - settingsButton.getHeight());
        pauseWindow.setPosition((GameMain.realRes.x / 2) - (pauseWindow.getWidth() / 2), (GameMain.realRes.y / 2) - (pauseWindow.getHeight() / 2));
        pauseWindow.setVisible(false);
        pauseWindow.add(resume).padBottom(3);
        pauseWindow.row();
        pauseWindow.add(mainMenu).padBottom(3);
        pauseWindow.row();
        pauseWindow.add(levelselect).padBottom(3);
        pauseWindow.row();
        pauseWindow.add(exit).padBottom(3);
        pauseWindow.row();
        pauseWindow.setHeight(resume.getHeight() * 5f);
        pauseWindow.setWidth(resume.getWidth());
        stage.addActor(settingsButton);
        stage.addActor(pauseWindow);


        restart.getLabel().setFontScale(3);
        goToLevelSelect.getLabel().setFontScale(3);

        dead.setFillParent(true);
        dead.add(restart).width(restart.getWidth() * 3).height(restart.getHeight() * 3).padTop(300);
        dead.row();
        dead.add(goToLevelSelect).width(restart.getWidth() * 3).height(restart.getHeight() * 3);
        dead.setVisible(false);
        stage.addActor(dead);

        if (LevelSelector.levelId < LevelSelector.levels.size()-1){
            endFrame.add(nextLevel).width(nextLevel.getWidth() * 3).height(nextLevel.getHeight() * 3).getActor().getLabel().setFontScale(3);
            endFrame.row();
        }
        if(LevelSelector.levelId == LevelSelector.levels.size()-1){
            nextLevel.getLabel().setText("Next Level ???");
            endFrame.add(nextLevel).width(nextLevel.getWidth() * 3).height(nextLevel.getHeight() * 3).getActor().getLabel().setFontScale(3);
            endFrame.row();
        }
        endFrame.add(finishLevelSelect).width(nextLevel.getWidth() * 3).height(nextLevel.getHeight() * 3).getActor().getLabel().setFontScale(3);
        endFrame.row();
        endFrame.add(finishExit).width(nextLevel.getWidth() * 3).height(nextLevel.getHeight() * 3).getActor().getLabel().setFontScale(3);
        endFrame.setVisible(false);
        endFrame.setResizable(false);
        endFrame.setWidth(finishLevelSelect.getWidth()*3.1f);
        endFrame.setResizable(false);
        endFrame.setHeight(endFrame.getCells().size*3*finishLevelSelect.getHeight()+10*endFrame.getCells().size);
        endFrame.setPosition(GameMain.realRes.x/2-endFrame.getWidth()/2,GameMain.realRes.y/2-endFrame.getHeight()/2);
        stage.addActor(endFrame);
    }

    /**
     * Buttons and stuff
     */
    float elapsedUnpaused = 0;
    float elapsedPaused = 0;

    @Override
    public void update() {
        elapsedUnpaused += Gdx.graphics.getDeltaTime();
        if (settingsButton.isPressed() || (Gdx.input.isKeyPressed(Input.Keys.P) && elapsedUnpaused > .5)) {
            isPaused = true;
            settingsButton.setVisible(false);
            pauseWindow.setVisible(true);
        }
        if (isPaused) {
            elapsedUnpaused = 0;
            elapsedPaused += Gdx.graphics.getDeltaTime();
            if (resume.isPressed() || (Gdx.input.isKeyPressed(Input.Keys.P) && elapsedPaused > .5)) {
                isPaused = false;
                settingsButton.setVisible(true);
                pauseWindow.setVisible(false);
            }
        } else {
            elapsedPaused = 0;
        }

        if (levelselect.isPressed()) {
            game.setScreen(new LevelSelector(game));
        }

        if (exit.isPressed() || finishExit.isPressed()) {
            Gdx.app.exit();
        }

        if (mainMenu.isPressed()) {
            game.setScreen(new MainMenuScreen(game));
        }

        if (restart.isPressed()) {
            game.setScreen(new Level(game, level.descriptor));
        }
        if (goToLevelSelect.isPressed() || finishLevelSelect.isPressed()) {
            game.setScreen(new LevelSelector(game));
        }

        if (nextLevel.isPressed() && nextLevel.getLabel().getText().toString().equals("Next Level")) {
            LevelSelector.incrementLevelId();
            game.setScreen(new Level(game, LevelSelector.levels.get(LevelSelector.levelId)));
        }

        if(nextLevel.isPressed() && nextLevel.getLabel().getText().toString().equals("Next Level ???")){
            game.setScreen(new EndScreen(game));
        }

        if (level.player.health <= 0)
            settingsButton.setVisible(false);
        if (level.player.health <= 0 && level.player.deadElapsed > 3) {
            dead.setVisible(true);
        }
        if (level.player.ended) {
            settingsButton.setVisible(false);
            endFrame.setVisible(true);
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public boolean isPaused() {
        return isPaused;
    }
}
