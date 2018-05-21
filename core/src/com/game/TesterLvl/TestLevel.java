package com.game.TesterLvl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.LevelManagment.CollisionManager;
import com.game.PlayerManager.Player;
import com.game.GameMain;
import com.game.LevelManagment.TileMap;

public class TestLevel implements Screen {

    OrthographicCamera camera = new OrthographicCamera(GameMain.WIDTH, GameMain.HEIGHT);
    GameMain game;
    SpriteBatch batch;
    World world;
    Player p;
    TileMap tileMap;

    CollisionManager cm;

    public TestLevel(GameMain g){
        game = g;
        batch = g.batch;
        camera.position.y += GameMain.HEIGHT/2;
        batch.setProjectionMatrix(camera.combined);
        world = new World(new Vector2(0,-98f),true);
        tileMap = new TileMap("Levels/lvl.tmx",world);
        p = new Player(world,GameMain.WIDTH/2+1,200);
        cm = new CollisionManager(p.state);
    }
    
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.3f,0.3f,.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(p.getX()>GameMain.WIDTH/2) {
            camera.position.x = p.getX();
            batch.setProjectionMatrix(camera.combined);
        }
        camera.update(true);

        tileMap.render(camera);

        batch.begin();
        p.draw(batch);
        batch.end();

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
