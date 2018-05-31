package com.game.UI.Menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import java.util.ArrayList;

public class LevelSelector implements Screen{
    ArrayList<FileHandle> levelPaths = new ArrayList<FileHandle>();
    /**
     * GUI
     */
    Stage stage = new Stage();
    Skin skin = new Skin();
    //scroll planes
    ScrollPane levelList;
    Table table = new Table(skin);

    @Override
    public void show() {
        levelPaths.add(new FileHandle("Levels/Tester/lvl.tmx"));
        table.add();
        levelList = new ScrollPane(table,skin);
    }

    @Override
    public void render(float delta) {

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
