package com.game.UI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.game.GameMain;
import com.game.UI.Menu.Actors.ThemedTextButton;
import java.util.ArrayList;

public class LevelSelector implements Screen {
    ArrayList<FileHandle> levelPaths = new ArrayList<FileHandle>();
    ArrayList<ThemedTextButton> buttons = new ArrayList<ThemedTextButton>();
    /**
     * GUI
     */
    Stage stage = new Stage();
    Skin skin = new Skin(new FileHandle("default/skin/uiskin.json"));
    BitmapFont font = new BitmapFont();
    //scroll planes
    ScrollPane levelList;
    Table table = new Table(skin);

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        levelPaths.add(new FileHandle("Levels/Tester/lvl.tmx"));
        for (int x = 0; x < 100; x++) {
            buttons.add(new ThemedTextButton("text",new ImageButton.ImageButtonStyle(),font));
            table.add(buttons.get(x));
            table.row();
        }
        levelList = new ScrollPane(table, skin);
        levelList.setBounds(0,0, GameMain.WIDTH,GameMain.HEIGHT);
        stage.addActor(levelList);
    }

    @Override
    public void render(float delta) {
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
}
