package com.game.TesterLvl;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.game.PlayerManager.Player;
import com.game.GameMain;
import com.game.LevelManagment.TileMap;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.game.StateUpdate.DrawUpdatable;
import com.game.StateUpdate.Updatable;

import java.util.ArrayList;

public class TestLevel implements Screen {

    OrthographicCamera camera = new OrthographicCamera(GameMain.WIDTH, GameMain.HEIGHT);
    SpriteBatch batch;
    World world;
    Player p;
    TileMap tileMap;

    ArrayList<DrawUpdatable> spriteList = new ArrayList<DrawUpdatable>();
    ArrayList<Updatable> updateList = new ArrayList<Updatable>();

    public TestLevel(SpriteBatch batch) {
        this.batch = batch;
        // setting camera up
        camera.position.set(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, 0);
        batch.setProjectionMatrix(camera.combined);
        // creating physics world and the tile map
        world = new World(new Vector2(0, -9.8f * 3f), true);
        tileMap = new TileMap("Levels/Tester/lvl.tmx", world);
        // creating player
        p = new Player(world, 300, 100);

        spriteList.add(p);
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

        updateCamera();

        tileMap.render(camera);
        batch.begin();

        for(DrawUpdatable sprite: spriteList){
            sprite.update(batch);
        }
        for(Updatable u: updateList){
            u.update();
        }

        // iterates the physics simulation
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    private void updateCamera(){
        if(p.getMidpoint().x > GameMain.WIDTH/2)
            pX = p.getMidpoint().x;
        else
            pX = GameMain.WIDTH/2;
        if(p.getMidpoint().y > GameMain.HEIGHT/2)
            pY = p.getMidpoint().y;
        else
            pY = GameMain.HEIGHT/2;
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
