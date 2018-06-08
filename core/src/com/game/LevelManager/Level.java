package com.game.LevelManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.EntityManager.Enemy;
import com.game.EntityManager.Player;
import com.game.GameMain;
import com.game.StateUpdate.DrawUpdatable;
import com.game.StateUpdate.Updatable;
import com.game.UI.InLevel.GameplayUI;

import java.util.ArrayList;

import javax.print.attribute.standard.PrinterLocation;

public class Level implements Screen {

    OrthographicCamera camera = new OrthographicCamera(GameMain.WIDTH, GameMain.HEIGHT);
    SpriteBatch batch;
    public World world;
    Player p;
    public TileMap tileMap;
    GameMain game;

    public GameplayUI ui;

    ArrayList<DrawUpdatable> spriteList = new ArrayList<DrawUpdatable>();
    ArrayList<Updatable> updateList = new ArrayList<Updatable>();

    public Level(GameMain game, FileHandle mapTmx) {
        this.batch = game.batch;
        this.game = game;

        ui = new GameplayUI(game,new Stage());

        // setting camera up
        camera.position.set(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, 0);
        batch.setProjectionMatrix(camera.combined);
        // creating physics world and the tile map
        world = new World(new Vector2(0, -9.8f * 3f), true);
        tileMap = new TileMap(mapTmx.path(), world);
        // creating player
        p = new Player(this, tileMap.getPlayerPos().x, tileMap.getPlayerPos().y);

        spriteList.add(p);
        updateList.add(ui);
    }

    public Level(GameMain game, LevelDescriptor levelDescription){
        this.batch = game.batch;
        this.game = game;

        ui = new GameplayUI(game,new Stage());

        // setting camera up
        camera.position.set(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, 0);
        batch.setProjectionMatrix(camera.combined);
        // creating physics world and the tile map
        world = new World(new Vector2(0, -9.8f * 3f), true);
        tileMap = new TileMap(levelDescription.tmxLocation.path(), world);
        // creating player
        p = new Player(this, tileMap.getPlayerPos().x, tileMap.getPlayerPos().y);

        spriteList.add(p);
        updateList.add(ui);

        for(int x = 0 ; x < tileMap.getEnemyPositions().size() ; x++){
            spriteList.add(new Enemy(this,world,tileMap.getEnemyPositions().get(x).x,tileMap.getEnemyPositions().get(x).y));
        }
    }

    @Override
    public void show() {
        pX = p.getMidpoint().x;
        pY = p.getMidpoint().y;
        sr.setAutoShapeType(true);
    }

    ShapeRenderer sr = new ShapeRenderer();

    float pY;
    float pX;
    @Override
    public void render(float delta) {
        // needed to clear each frame and have a default background color
        Gdx.gl.glClearColor(.3f, 0.3f, .5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!p.isDead) {
            updateCamera();
            tileMap.render(camera);
            batch.begin();
            for (DrawUpdatable sprite : spriteList) {
                sprite.update(batch);
            }
            batch.end();
            for (Updatable u : updateList) {
                u.update();
            }

            // iterates the physics simulation
            if (!ui.isPaused()) {
                world.step(Gdx.graphics.getDeltaTime(), 6, 2);
                for (int y = spriteList.size() - 1; y >= 0; y--) {
                    if (spriteList.get(y) instanceof Enemy) {
                        if (((Enemy) spriteList.get(y)).isDead() && !((Enemy) spriteList.get(y)).killed) {
                            ((Enemy) spriteList.get(y)).killed = true;
                            ((Enemy) spriteList.get(y)).kill();
                            world.destroyBody(((Enemy) spriteList.get(y)).body);
                        }
                    }
                }
            }
        }else{
            tileMap.render(camera);
            batch.begin();
            for (DrawUpdatable sprite : spriteList) {
                if(!(sprite instanceof Player))
                    sprite.update(batch);
            }
            p.update(batch);
            batch.end();
        }
    }

    private void updateCamera(){
        if(p.getMidpoint().x < GameMain.WIDTH/2){
            pX = GameMain.WIDTH/2;
        }else if(p.getMidpoint().x > tileMap.mapSize.x-GameMain.WIDTH/2){
            pX = tileMap.mapSize.x-GameMain.WIDTH/2;
        }else{
            pX = p.getMidpoint().x;
        }

        if(p.getMidpoint().y < GameMain.HEIGHT/2){
            pY = GameMain.HEIGHT/2;
        }else if(p.getMidpoint().y > tileMap.mapSize.y-GameMain.HEIGHT/2){
            pY = tileMap.mapSize.y-GameMain.HEIGHT/2;
        }else{
            pY = p.getMidpoint().y;
        }

        camera.position.lerp(new Vector3(pX,pY,0),.1f);
        batch.setProjectionMatrix(camera.combined);
        camera.update(true);
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
