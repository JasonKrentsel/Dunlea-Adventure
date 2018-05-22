package com.game.TesterLvl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.game.LevelManagment.CollisionManager;
import com.game.PlayerManager.Player;
import com.game.GameMain;
import com.game.LevelManagment.TileMap;
import com.game.PlayerManager.Side;

public class TestLevel implements Screen {

    OrthographicCamera camera = new OrthographicCamera(GameMain.WIDTH, GameMain.HEIGHT);
    GameMain game;
    SpriteBatch batch;
    World world;
    Player p;
    TileMap tileMap;

    CollisionManager cm;
    Box2DDebugRenderer debug;

    public TestLevel(GameMain g){
        game = g;
        batch = g.batch;
        camera.position.y += GameMain.HEIGHT/2;
        batch.setProjectionMatrix(camera.combined);
        world = new World(new Vector2(0,-9.8f*1.5f),true);
        tileMap = new TileMap("Levels/lvl.tmx",world);
        p = new Player(world,GameMain.WIDTH/2+1,200);

        cm = new CollisionManager(p.state);
        world.setContactListener(cm);
        debug = new Box2DDebugRenderer();
    }
    
    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.3f,0.3f,.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            camera.zoom = 1/GameMain.PPM;
            //camera.viewportHeight = GameMain.PPM;
            //camera.viewportWidth = GameMain.PPM;
            camera.position.set(0,0,0);
            camera.update();
        }
        else {
            if(p.getX()>GameMain.WIDTH/2) {
                camera.position.x = p.getX();
                batch.setProjectionMatrix(camera.combined);
            }
            camera.update(true);
            camera.zoom = 1;
        }
        tileMap.render(camera);

        batch.begin();
        p.draw(batch);
        batch.end();

        debug.render(world,camera.combined);
        world.step(Gdx.graphics.getDeltaTime(),6,2);
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
