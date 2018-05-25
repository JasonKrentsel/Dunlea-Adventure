package com.game.TesterLvl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.game.LevelManagment.CollisionManager;
import com.game.PlayerManager.Player;
import com.game.GameMain;
import com.game.LevelManagment.TileMap;

public class TestLevel implements Screen {

    OrthographicCamera camera = new OrthographicCamera(GameMain.WIDTH, GameMain.HEIGHT);
    SpriteBatch batch;
    World world;
    Player p;
    TileMap tileMap;

    CollisionManager cm;

    public TestLevel(SpriteBatch batch) {
        this.batch = batch;

        // setting camera up
        camera.position.set(GameMain.WIDTH / 2,GameMain.HEIGHT/2,0);
        batch.setProjectionMatrix(camera.combined);

        // creating physics world and the tile map
        world = new World(new Vector2(0, -9.8f * 3f), true);
        tileMap = new TileMap("Levels/Map/map.tmx", world);
        // creating player
        p = new Player(world, 200, 200);
        // creating global collision manager
        cm = new CollisionManager(p.sensorState);
        world.setContactListener(cm);
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        // needed to clear each frame and have a default background color
        Gdx.gl.glClearColor(.3f, 0.3f, .5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (p.getMidpoint().x > GameMain.WIDTH / 2)
            camera.position.lerp(new Vector3(p.getMidpoint().x, GameMain.HEIGHT / 2, 0), .05f);
        else
            camera.position.lerp(new Vector3(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, 0), .05f);
        batch.setProjectionMatrix(camera.combined);

        camera.update(true);

        // render the tile map and player
        tileMap.render(camera);
        batch.begin();
        p.draw(batch);
        batch.end();

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
