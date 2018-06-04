package com.game.UI.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.GameMain;
import com.game.LevelManager.Level;
import com.game.UI.Menu.Actors.ThemedTextButton;

public class MainMenuScreen implements Screen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    private ThemedTextButton playButton;
    private ThemedTextButton exitButton;
    Animation<Texture> background = new Animation<Texture>(.2f, new Texture("Menu/TitleScreen/F0.gif"), new Texture("Menu/TitleScreen/F1.gif"), new Texture("Menu/TitleScreen/F2.gif"), new Texture("Menu/TitleScreen/F3.gif"), new Texture("Menu/TitleScreen/F4.gif"), new Texture("Menu/TitleScreen/F5.gif"), new Texture("Menu/TitleScreen/F6.gif"), new Texture("Menu/TitleScreen/F7.gif"));
    GameMain game;

    public MainMenuScreen(GameMain game){
        this.batch = game.batch;
        this.game = game;
        atlas = new TextureAtlas("default/skin/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("default/skin/uiskin.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameMain.WIDTH, GameMain.HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }

    @Override
    public void show() {
        //Stage should control input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.bottom();

        //Create buttons
        playButton = new ThemedTextButton("Play");
        exitButton = new ThemedTextButton("Exit");

        //Add buttons to table
        playButton.getLabel().setFontScale(2);
        mainTable.add(playButton).padBottom(10).width(playButton.getWidth()*2).height(playButton.getHeight()*2);
        mainTable.row();
        exitButton.getLabel().setFontScale(2);
        mainTable.add(exitButton).padBottom(80).width(exitButton.getWidth()*2).height(exitButton.getHeight()*2);

        //Add to the stage
        stage.addActor(mainTable);

    }

    float elapsed = 0;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.22f, .05f, .33f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        elapsed+=Gdx.graphics.getDeltaTime();

        if(playButton.isPressed()){
            game.setScreen(new LevelSelector(game));
        }
        if(exitButton.isPressed()){
            Gdx.app.exit();
        }

        batch.begin();
        batch.draw(background.getKeyFrame(elapsed,true),0,0);
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        skin.dispose();
        atlas.dispose();
    }
}