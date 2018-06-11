package com.game.UI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.game.GameMain;
import com.game.LevelManager.Level;
import com.game.LevelManager.LevelDescriptor;
import com.game.UI.Menu.Actors.ThemedTextButton;

import java.util.ArrayList;

public class LevelSelector implements Screen {
    public static ArrayList<LevelDescriptor> levels = new ArrayList<LevelDescriptor>();
    public static int levelId = -1;
    static {
        levels.add(new LevelDescriptor("Tutorial", new FileHandle("Levels/tutorial/map.tmx")));
        levels.add(new LevelDescriptor("It Continues", new FileHandle("Levels/L1/map.tmx")));
        levels.add(new LevelDescriptor("Descent",new FileHandle("Levels/sub/map.tmx")));
        levels.add(new LevelDescriptor("Etheral", new FileHandle("Levels/Etheral/map.tmx")));
    }

    ArrayList<ThemedTextButton> buttons = new ArrayList<ThemedTextButton>();
    /**
     * GUI
     */
    Stage stage;
    GameMain game;
    Skin skin = new Skin(Gdx.files.internal("default/skin/uiskin.json"));
    //scroll planes
    ScrollPane levelList;
    Table table = new Table(skin);
    ThemedTextButton back = new ThemedTextButton("Return to MainMenu");

    public LevelSelector(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        for (int x = 0; x < levels.size(); x++) {
            buttons.add(new ThemedTextButton(levels.get(x).name));
            buttons.get(x).getLabel().setFontScale(2, 2);
            table.add(buttons.get(x)).width(buttons.get(x).getWidth() * 2).height(buttons.get(x).getHeight() * 2).padBottom(5);
            table.row();
        }

        levelList = new ScrollPane(table, skin);
        levelList.setBounds(0, 0, GameMain.realRes.x, GameMain.realRes.y);

        stage.addActor(levelList);

        Table t = new Table();
        t.setFillParent(true);
        t.top().right();
        t.add(back).width(back.getWidth() * 1.2f).height(back.getHeight() * 1.2f).padTop(10).padRight(10);
        stage.addActor(t);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.22f, .05f, .33f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int x = 0; x < buttons.size(); x++) {
            if (buttons.get(x).isPressed()) {
                levelId = x;
                game.setScreen(new Level(game, levels.get(x)));
            }
        }

        if (back.isPressed()) {
            game.setScreen(new MainMenuScreen(game));
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public static void incrementLevelId(){
        levelId=levelId+1;
    }
}
