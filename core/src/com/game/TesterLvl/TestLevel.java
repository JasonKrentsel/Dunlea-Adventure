package com.game.TesterLvl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.LevelManagment.CollisionManager;
import com.game.PlayerManager.Player;
import com.game.GameMain;
import com.game.LevelManagment.TileMap;

import java.awt.Checkbox;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TestLevel implements Screen {

    OrthographicCamera camera = new OrthographicCamera(GameMain.WIDTH, GameMain.HEIGHT);
    SpriteBatch batch;
    World world;
    Player p;
    TileMap tileMap;

    CollisionManager cm;

    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    Matrix4 matrixDebug = new Matrix4();

    Skin skin;
    CheckBox hitboxToggle;
    CheckBox sensorReport;
    CheckBox positionReport;
    Stage stage;
    com.badlogic.gdx.scenes.scene2d.ui.Window window;
    BitmapFont font = new BitmapFont();

    public TestLevel(SpriteBatch batch) {
        this.batch = batch;
        // setting camera up
        camera.position.set(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, 0);
        batch.setProjectionMatrix(camera.combined);
        // creating physics world and the tile map
        world = new World(new Vector2(0, -9.8f * 3f), true);
        tileMap = new TileMap("Levels/Tester/lvl.tmx", world);
        // creating player
        p = new Player(world, 200, 200);
        // creating global collision manager
        cm = new CollisionManager(p.sensorState);
        world.setContactListener(cm);


        skin = new Skin(new FileHandle("default/skin/uiskin.json"));
        stage = new Stage();
        hitboxToggle = new CheckBox("HitBoxes",skin);
        sensorReport = new CheckBox("Sensor Report",skin);
        positionReport = new CheckBox("Position Report",skin);
        window = new com.badlogic.gdx.scenes.scene2d.ui.Window("Debug",skin);
        window.setResizable(true);
        window.setMovable(true);
        window.add(hitboxToggle,sensorReport,positionReport);
        window.pack();
        window.setPosition(0,GameMain.HEIGHT-window.getHeight());
        stage.addActor(window);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }


    float pY;
    float pX;

    @Override
    public void render(float delta) {
        // needed to clear each frame and have a default background color
        Gdx.gl.glClearColor(.3f, 0.3f, .5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(p.getMidpoint().x > GameMain.WIDTH/2)
            pX = p.getMidpoint().x;
        else
            pX = GameMain.WIDTH/2;
        if(p.getMidpoint().y > GameMain.HEIGHT/2)
            pY = p.getMidpoint().y;
        else
            pY = GameMain.HEIGHT/2;
        camera.position.lerp(new Vector3(pX,pY,0),.05f);

        batch.setProjectionMatrix(camera.combined);
        camera.update(true);

        // render the tile map and player
        tileMap.render(camera);
        batch.begin();
        p.draw(batch);

        if(sensorReport.isChecked()){
            font.draw(batch,p.sensorState.toString(),camera.position.x-300,camera.position.y +300);
        }

        if(positionReport.isChecked()){
            font.draw(batch,"Player ("+p.getX()+","+p.getY()+")",camera.position.x-300,camera.position.y +250);
            font.draw(batch,"Body   "+p.body.getPosition().toString(),camera.position.x-300,camera.position.y +200);
        }

        batch.end();

        stage.draw();

        if(hitboxToggle.isChecked()) {
            matrixDebug.set(camera.combined);
            matrixDebug.scl(GameMain.PPM);
            debugRenderer.render(world, matrixDebug);
        }
        // iterates the physics simulation
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
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
