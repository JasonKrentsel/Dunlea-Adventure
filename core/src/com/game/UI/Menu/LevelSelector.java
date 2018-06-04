package com.game.UI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
    ArrayList<LevelDescriptor> levels = new ArrayList<LevelDescriptor>();
    ArrayList<ThemedTextButton> buttons = new ArrayList<ThemedTextButton>();
    /**
     * GUI
     */
    Stage stage;
    GameMain game;
    Skin skin = new Skin(new FileHandle("default/skin/uiskin.json"));
    //scroll planes
    ScrollPane levelList;
    Table table = new Table(skin);

    public LevelSelector(GameMain game){
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        levels.add(new LevelDescriptor("Test",new FileHandle("Levels/Tester/lvl.tmx")));
        levels.add(new LevelDescriptor("Tile test",new FileHandle("Levels/TileTest/map.tmx")));

        for (int x = 0; x < levels.size(); x++) {
            buttons.add(new ThemedTextButton(levels.get(x).name));
            buttons.get(x).getLabel().setFontScale(2,2);
            table.add(buttons.get(x)).width(buttons.get(x).getWidth()*2).height(buttons.get(x).getHeight()*2);
            table.row();
        }

        levelList = new ScrollPane(table, skin);
        levelList.setBounds(0,0, GameMain.realRes.x,GameMain.realRes.y);
        stage.addActor(levelList);
    }

    @Override
    public void render(float delta) {

        for(int x = 0 ; x < buttons.size() ; x++){
            if(buttons.get(x).isPressed()){
                game.setScreen(new Level(game,levels.get(x)));
            }
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
}